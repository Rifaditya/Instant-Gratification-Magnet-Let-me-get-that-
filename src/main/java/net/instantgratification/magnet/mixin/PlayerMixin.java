package net.instantgratification.magnet.mixin;

import net.instantgratification.magnet.registry.ModGameRules;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public class PlayerMixin {

    @ModifyVariable(
            method = "aiStep",
            at = @At(value = "STORE"),
            ordinal = 0
    )
    private AABB magnet$expandPickupArea(AABB pickupArea) {
        Player player = (Player) (Object) this;
        Level level = player.level();
        
        if (!level.isClientSide()) {
            if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
                int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
                if (range > 0) {
                    // Inflate the pickup area uniformly by the magnet's configured range
                    // Note: The vanilla bounding box normally gets inflated by ~1.0. 
                    // This creates a massive scoop radius that instantly picks up items using vanilla logic.
                    return pickupArea.inflate(range);
                }
            }
        }
        
        return pickupArea;
    }
}
