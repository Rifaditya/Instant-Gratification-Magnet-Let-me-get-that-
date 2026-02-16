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
        if (player.isSpectator() || player.isDeadOrDying() || player.level().isClientSide())
            return;

        // Check range
        int range = ModGameRules.getInt(player.level(), ModGameRules.MAGNET_RANGE);
        if (range <= 0)
            return;

        // 2. Scan Area
        AABB area = player.getBoundingBox().inflate(range);
        List<Entity> entities = player.level().getEntities(player, area,
                e -> (e instanceof ItemEntity || e instanceof ExperienceOrb) && e.isAlive());

        // 3. Apply Magnet
        for (Entity entity : entities) {
            // Optional: Check line of sight if needed (Philosophy says "If you can see it",
            // but "Phase Shifting" implies walls don't matter)
            // Concept says: "Phase Shifting (NoCLIP) - Items do not get stuck on walls." ->
            // No LoS check needed.

            // Check specific logic:
            // - ItemEntity: Pickup delay? Usually magnets ignore pickup delay to drag them,
            // but only player can pick them up when close.
            // If pickupDelay is high (just dropped), maybe don't pull?
            // "Vacuum Field... constant pull... awaken instantly"
            // Let's pull everything.

            MagnetMovement.pull(entity, player);
        }
    }
}
