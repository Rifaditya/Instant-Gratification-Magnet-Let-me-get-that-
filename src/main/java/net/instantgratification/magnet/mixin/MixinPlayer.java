package net.instantgratification.magnet.mixin;

import net.instantgratification.magnet.MagnetManager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class MixinPlayer {

    @Inject(method = "tick", at = @At("TAIL"))
    private void magnet_tick(CallbackInfo ci) {
        MagnetManager.tick((Player) (Object) this);
    }
}
