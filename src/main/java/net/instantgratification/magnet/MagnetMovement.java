// Verified against: Entity.java (26.2+)
package net.instantgratification.magnet;

import net.instantgratification.magnet.registry.ModGameRules;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class MagnetMovement {

    public static void pull(Entity entity, Player player, boolean shouldSpawnParticles) {
        if (entity.level().isClientSide())
            return;
        Level level = entity.level();

        // 1. Config Checks
        boolean affectXP = ModGameRules.getBoolean(level, ModGameRules.MAGNET_AFFECTS_XP);
        if (entity instanceof ExperienceOrb && !affectXP)
            return;

        // 1.5. Line of Sight (LOS) Check
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_LOS_ONLY)) {
            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                // Primary Pass: Solid Walls (Vanilla raycast)
                Vec3 start = serverPlayer.getEyePosition();
                Vec3 end = new Vec3(entity.getX(), entity.getY() + entity.getBbHeight() / 2.0, entity.getZ());
                net.minecraft.world.level.ClipContext clipContext = new net.minecraft.world.level.ClipContext(
                    start, end, net.minecraft.world.level.ClipContext.Block.VISUAL, net.minecraft.world.level.ClipContext.Fluid.NONE, serverPlayer
                );
                net.minecraft.world.phys.BlockHitResult hitResult = serverPlayer.level().clip(clipContext);
                boolean canSee = hitResult.getType() == net.minecraft.world.phys.HitResult.Type.MISS;
                
                // Secondary Pass: Granular Configuration (Glass, Flora, Entities)
                if (canSee) {
                    boolean blockTransparent = ModGameRules.getBoolean(level, ModGameRules.MAGNET_BLOCKED_BY_TRANSPARENT);
                    boolean blockFlora = ModGameRules.getBoolean(level, ModGameRules.MAGNET_BLOCKED_BY_FLORA);
                    boolean blockEntities = ModGameRules.getBoolean(level, ModGameRules.MAGNET_BLOCKED_BY_BLOCK_ENTITIES);
                    
                    if (blockTransparent || blockFlora || blockEntities) {
                        canSee = SecondaryVisionCheck.canSee(player, entity, blockTransparent, blockFlora, blockEntities);
                    }
                }

                if (!canSee) {
                    if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_KEEP_MOVING_IF_UNSEEN)) {
                        // If it's unseen, it MUST have been magnetized before to continue.
                        // Using the injected Mixin flag, not scoreboard tags (no such API in Snapshot 26.1).
                        if (!((IMagnetEntity) entity).ig_magnet$isMagnetized()) {
                            if (entity.tickCount % 40 == 0) {
                                MagnetDebugLogger.log("MagnetMovement: Pull rejected for %s (%s) on entity %d. Reason: LOS check failed (unmagnetized).", serverPlayer.getScoreboardName(), serverPlayer.getUUID(), entity.getId());
                            }
                            return;
                        }
                    } else {
                        if (entity.tickCount % 40 == 0) {
                            MagnetDebugLogger.log("MagnetMovement: Pull rejected for %s (%s) on entity %d. Reason: Strict LOS check failed.", serverPlayer.getScoreboardName(), serverPlayer.getUUID(), entity.getId());
                        }
                        return; // Strict LOS requirement
                    }
                } else {
                    // It is seen! Flag it so it can keep moving if it loses LOS later.
                    if (!((IMagnetEntity) entity).ig_magnet$isMagnetized()) {
                        MagnetDebugLogger.log("MagnetMovement: Entity %d is now magnetized by %s (%s).", entity.getId(), serverPlayer.getScoreboardName(), serverPlayer.getUUID());
                    }
                    ((IMagnetEntity) entity).ig_magnet$setMagnetized();
                }
            }
        } else {
            // Magnet is in normal mode, flag it anyway in case settings change mid-flight
            ((IMagnetEntity) entity).ig_magnet$setMagnetized();
        }

        // 2. Physics Config
        // Convert integer percentage to double (80 -> 0.8)
        double speed = ModGameRules.getInt(level, ModGameRules.MAGNET_SPEED_PERCENT) / 100.0;
        double acceleration = ModGameRules.getInt(level, ModGameRules.MAGNET_ACCELERATION_PERCENT) / 100.0;
        boolean noClip = ModGameRules.getBoolean(level, ModGameRules.MAGNET_NOCLIP);

        // 3. Instant Pickup Override
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            // The PlayerMixin will natively handle expanding the AABB pickup radius.
            // We just need to skip the visual physics/velocity here so we don't apply motion twice.
            return;
        }

        // 4. Vector Calculation
        // Target: Player's eye position (to avoid feet drag)
        Vec3 target = player.getEyePosition();
        Vec3 pos = entity.position();
        Vec3 vecToTarget = target.subtract(pos);
        double distSqr = vecToTarget.lengthSqr();

        // Safety check to prevent orbiting functionality or super-speed glitching at
        // close range
        // Removed close-range stall check to allow continuous pull until pickup

        Vec3 direction = vecToTarget.normalize();

        // 5. Velocity Application
        // Interpolate current velocity towards target velocity
        Vec3 currentVel = entity.getDeltaMovement();
        Vec3 targetVel = direction.scale(speed);

        // Lerp logic: current + (target - current) * accel
        Vec3 newVel = currentVel.lerp(targetVel, acceleration);

        entity.setDeltaMovement(newVel);
        entity.hurtMarked = true;
        if (entity.onGround()) {
            entity.setOnGround(false);
            entity.setPos(entity.position().add(0, 0.05, 0));
        }

        // 6. NoClip
        if (noClip) {
            // Only apply noPhysics if actually moving fast toward the player
            // to prevent falling through the floor indefinitely when stopped.
            if (distSqr > 1.0) {
                 ((IMagnetEntity) entity).ig_magnet$setMagnetNoClip();
            }
        }

        // 7. Visuals
        if (shouldSpawnParticles && ModGameRules.getBoolean(level, ModGameRules.MAGNET_PARTICLES) && (entity.tickCount + entity.getId()) % 4 == 0) {
            int count = ModGameRules.getInt(level, ModGameRules.MAGNET_PARTICLE_COUNT);
            if (count > 0) {
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                            entity.getX(), entity.getY() + entity.getBbHeight() / 2.0, entity.getZ(),
                            count, 0.1, 0.1, 0.1, 0.05);
                } else {
                    for (int i = 0; i < count; i++) {
                        level.addParticle(ParticleTypes.ELECTRIC_SPARK,
                                entity.getRandomX(0.2), entity.getRandomY() + entity.getBbHeight() / 2.0, entity.getRandomZ(0.2),
                                0.0, 0.05, 0.0);
                    }
                }
            }
        }
    }
}
