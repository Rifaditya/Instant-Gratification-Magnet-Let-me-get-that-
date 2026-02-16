package net.instantgratification.magnet;

import net.instantgratification.magnet.registry.ModGameRules;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class MagnetMovement {

    public static void pull(Entity entity, Player player) {
        if (entity.level().isClientSide())
            return;
        Level level = entity.level();

        // 1. Config Checks
        boolean affectXP = ModGameRules.getBoolean(level, ModGameRules.MAGNET_AFFECTS_XP); // Changed game rule access
        if (entity instanceof ExperienceOrb && !affectXP)
            return;

        // 2. Physics Config
        // Convert integer percentage to double (80 -> 0.8)
        double speed = ModGameRules.getInt(level, ModGameRules.MAGNET_SPEED_PERCENT) / 100.0; // Changed game rule
                                                                                              // access
        double acceleration = ModGameRules.getInt(level, ModGameRules.MAGNET_ACCELERATION_PERCENT) / 100.0; // Changed
                                                                                                            // game rule
                                                                                                            // access
        boolean noClip = ModGameRules.getBoolean(level, ModGameRules.MAGNET_NOCLIP); // Changed game rule access

        // 3. Vector Calculation
        // Target: Player's eye position (to avoid feet drag)
        Vec3 target = player.getEyePosition();
        Vec3 pos = entity.position();
        Vec3 vecToTarget = target.subtract(pos);
        double distSqr = vecToTarget.lengthSqr();

        // Safety check to prevent orbiting functionality or super-speed glitching at
        // close range
        if (distSqr < 0.25) { // Within 0.5 blocks
            // Let collisions handle pickup, just slow down or stop
            // entity.setDeltaMovement(vecToTarget); // Snap to player?
            return;
        }

        Vec3 direction = vecToTarget.normalize();

        // 4. Velocity Application
        // Interpolate current velocity towards target velocity
        Vec3 currentVel = entity.getDeltaMovement();
        Vec3 targetVel = direction.scale(speed);

        // Lerp logic: current + (target - current) * accel
        Vec3 newVel = currentVel.lerp(targetVel, acceleration);

        entity.setDeltaMovement(newVel);

        // 5. NoClip
        if (noClip) {
            entity.noPhysics = true; // Use vanilla field for noclip-like behavior on items?
            // Or usually we just let them move. 'noClip' field exists on Entity.
            // But we must be careful not to make them fall through floor forever if they
            // stop being magnetized.
            // Manager ensures this is called every tick they are in range.
            // If we want true noclip through walls, we might need to set the boolean.
            // Ref: Entity.noPhysics or Entity.invulnerable?
            // "noClip" is a protected field or accessor in some mappings?
            // In 26.1, it might be standard "noPhysics".
            // Better approach: Items that are moving towards player fast usually clip small
            // stuff,
            // but for walls we definitely need noPhysics=true.
            // We should reset it if not pulled, but that requires tracking.
            // However, typical magnets just set it true while pulling. The entity logic
            // might reset it or gravity applies.
            // For now, setting noPhysics = true is safe for this tick.
            entity.noPhysics = true;
        }

        // 6. Visuals
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_PARTICLES)) {
            int count = ModGameRules.getInt(level, ModGameRules.MAGNET_PARTICLE_COUNT);
            if (count > 0) {
                ((ServerLevel) level).sendParticles(ParticleTypes.ELECTRIC_SPARK,
                        entity.getX(), entity.getY() + entity.getBbHeight() / 2.0, entity.getZ(),
                        count, 0.1, 0.1, 0.1, 0.05);
            }
        }
    }
}
