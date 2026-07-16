// Verified against: LocalPlayer.java (26.2+)
package net.instantgratification.magnet;

import net.instantgratification.magnet.registry.ModGameRules;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class MagnetManager {

    public static void tick(Player player) {
        if (player.level().isClientSide() || player.isSpectator() || player.isDeadOrDying())
            return;

        // Player-specific toggle check
        if (!((IMagnetPlayer) player).ig_magnet$isMagnetEnabled())
            return;

        // 0. Master Toggle
        if (!ModGameRules.getBoolean(player.level(), ModGameRules.MAGNET_ENABLED))
            return;

        // Check range
        int range = ModGameRules.getInt(player.level(), ModGameRules.MAGNET_RANGE);

        // Link DasikLibrary PlayerVisionTracker
        if (ModGameRules.getBoolean(player.level(), ModGameRules.MAGNET_LOS_ONLY)) {
            net.dasik.social.api.vision.PlayerVisionTracker.registerListener(MagnetMod.MOD_ID, (double) range);
        } else {
            net.dasik.social.api.vision.PlayerVisionTracker.unregisterListener(MagnetMod.MOD_ID);
        }

        if (range <= 0)
            return;

        // 2. Scan Area
        AABB area = player.getBoundingBox().inflate(range);
        
        List<ItemEntity> items = player.level().getEntitiesOfClass(ItemEntity.class, area, Entity::isAlive);

        int maxParticleSources = ModGameRules.getInt(player.level(), ModGameRules.MAGNET_MAX_PARTICLE_SOURCES);
        int particleSourceCount = 0;

        // 3. Apply Magnet to items
        for (ItemEntity item : items) {
            boolean shouldSpawnParticles = false;
            if (particleSourceCount < maxParticleSources) {
                shouldSpawnParticles = true;
                particleSourceCount++;
            }

            MagnetMovement.pull(item, player, shouldSpawnParticles);
        }

        // Apply Magnet to XP Orbs if enabled
        if (ModGameRules.getBoolean(player.level(), ModGameRules.MAGNET_AFFECTS_XP)) {
            List<ExperienceOrb> orbs = player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive);
            for (ExperienceOrb orb : orbs) {
                boolean shouldSpawnParticles = false;
                if (particleSourceCount < maxParticleSources) {
                    shouldSpawnParticles = true;
                    particleSourceCount++;
                }

                MagnetMovement.pull(orb, player, shouldSpawnParticles);
            }
        }
    }
}
