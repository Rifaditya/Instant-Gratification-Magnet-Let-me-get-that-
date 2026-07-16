// Verified against: Entity.java (26.2+)
package net.instantgratification.magnet.mixin;

import net.instantgratification.magnet.IMagnetEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntity implements IMagnetEntity {

    @Unique
    private int ig_magnet$noClipTicks = 0;

    @Unique
    private boolean ig_magnet$magnetized = false;

    @Unique
    private boolean ig_magnet$originalNoPhysics = false;

    @Override
    public void ig_magnet$setMagnetNoClip() {
        this.ig_magnet$noClipTicks = 2; // Lasts 2 ticks after being pulled
    }

    @Override
    public boolean ig_magnet$isMagnetNoClip() {
        return this.ig_magnet$noClipTicks > 0;
    }

    @Override
    public void ig_magnet$setMagnetized() {
        this.ig_magnet$magnetized = true;
    }

    @Override
    public boolean ig_magnet$isMagnetized() {
        return this.ig_magnet$magnetized;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void ig_magnet$decrementNoClipTicks(CallbackInfo ci) {
        if (this.ig_magnet$noClipTicks > 0) {
            this.ig_magnet$noClipTicks--;
        }
    }

    @Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)
    private void ig_magnet$preventPushOut(double x, double y, double z, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (entity.level().isClientSide()) return;
        if (this.ig_magnet$noClipTicks > 0) {
            ci.cancel();
        }
    }

    @Inject(method = "move", at = @At("HEAD"))
    private void ig_magnet$forceNoClipOnMove(MoverType type, Vec3 delta, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (entity.level().isClientSide()) return;
        this.ig_magnet$originalNoPhysics = entity.noPhysics;
        if (this.ig_magnet$noClipTicks > 0) {
            entity.noPhysics = true;
        }
    }

    @Inject(method = "move", at = @At("RETURN"))
    private void ig_magnet$restoreNoPhysicsAfterMove(MoverType type, Vec3 delta, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (entity.level().isClientSide()) return;
        entity.noPhysics = this.ig_magnet$originalNoPhysics;
    }

    @Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)
    private void ig_magnet$conditionalCancelGravity(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (entity.level().isClientSide()) return;
        if (this.ig_magnet$noClipTicks > 0) {
            // Only cancel gravity if the item is physically inside a block.
            // This preserves the natural clear-air arcs.
            if (!entity.level().noCollision(entity, entity.getBoundingBox().deflate(1.0E-7))) {
                ci.cancel();
            }
        }
    }
}
