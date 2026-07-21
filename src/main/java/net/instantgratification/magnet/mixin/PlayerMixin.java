// Verified against: Player.java (26.2+)
package net.instantgratification.magnet.mixin;

import net.instantgratification.magnet.MagnetManager;
import net.instantgratification.magnet.registry.ModGameRules;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.instantgratification.magnet.IMagnetPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.instantgratification.magnet.MagnetTogglePayload;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(Player.class)
public class PlayerMixin implements IMagnetPlayer {

    @Unique
    private boolean ig_magnet$enabled = true;
    @Unique
    private int ig_magnet$tickCount = 0;

    @Override
    public boolean ig_magnet$isMagnetEnabled() {
        Player player = (Player) (Object) this;
        if (player.tickCount % 200 == 0) {
            net.instantgratification.magnet.MagnetDebugLogger.log("PlayerMixin: isMagnetEnabled for %s (%s) is %b", player.getScoreboardName(), player.getUUID(), this.ig_magnet$enabled);
        }
        return this.ig_magnet$enabled;
    }

    @Override
    public void ig_magnet$setMagnetEnabled(boolean enabled) {
        Player player = (Player) (Object) this;
        this.ig_magnet$enabled = enabled;
        net.instantgratification.magnet.MagnetDebugLogger.log("PlayerMixin: setMagnetEnabled for %s (%s) to %b", player.getScoreboardName(), player.getUUID(), enabled);
        this.ig_magnet$syncToClient();
    }

    @Override
    public boolean ig_magnet$toggleMagnet() {
        Player player = (Player) (Object) this;
        this.ig_magnet$enabled = !this.ig_magnet$enabled;
        net.instantgratification.magnet.MagnetDebugLogger.log("PlayerMixin: toggleMagnet for %s (%s) to %b", player.getScoreboardName(), player.getUUID(), this.ig_magnet$enabled);
        this.ig_magnet$syncToClient();
        return this.ig_magnet$enabled;
    }

    @Unique
    private void ig_magnet$syncToClient() {
        if ((Object) this instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.connection != null) {
                try {
                    net.instantgratification.magnet.MagnetDebugLogger.log("PlayerMixin: Syncing state to client %s (%s) value=%b", serverPlayer.getScoreboardName(), serverPlayer.getUUID(), this.ig_magnet$isMagnetEnabled());
                    ServerPlayNetworking.send(serverPlayer, new MagnetTogglePayload(this.ig_magnet$isMagnetEnabled()));
                } catch (Exception ignored) {}
            }
        }
    }

    // Verified against: Player.java (26.2+) - addAdditionalSaveData(ValueOutput)
    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void ig_magnet$addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
        output.putBoolean("ig_magnet_enabled", this.ig_magnet$enabled);
    }

    // Verified against: Player.java (26.2+) - readAdditionalSaveData(ValueInput)
    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void ig_magnet$readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        this.ig_magnet$enabled = input.getBooleanOr("ig_magnet_enabled", true);
        net.instantgratification.magnet.MagnetDebugLogger.log("PlayerMixin: readAdditionalSaveData for %s (%s) value=%b", player.getScoreboardName(), player.getUUID(), this.ig_magnet$enabled);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void ig_magnet$tick(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (!player.level().isClientSide()) {
            this.ig_magnet$tickCount++;
            if (this.ig_magnet$tickCount % 100 == 0) {
                net.instantgratification.magnet.MagnetDebugLogger.log("PlayerMixin: Server tick for %s (%s) isMagnetEnabled=%b", player.getScoreboardName(), player.getUUID(), this.ig_magnet$isMagnetEnabled());
            }
            MagnetManager.tick(player);
        }
    }

    @ModifyVariable(
            method = "aiStep",
            at = @At(value = "STORE"),
            ordinal = 0
    )
    private AABB ig_magnet$expandPickupArea(AABB pickupArea) {
        Player player = (Player) (Object) this;
        if (!this.ig_magnet$isMagnetEnabled() || player.isDeadOrDying() || player.isSpectator()) {
            return pickupArea;
        }

        Level level = player.level();
        
        if (!level.isClientSide()) {
            if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_ENABLED) && ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
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
