// Verified against: ClipContext.java (26.2+)
package net.instantgratification.magnet;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SecondaryVisionCheck {

    private static final ThreadLocal<Context> CONTEXT = ThreadLocal.withInitial(Context::new);

    private static class Context {
        Level level;
        boolean blockTransparent;
        boolean blockFlora;
        boolean blockEntities;
        CollisionContext collisionContext;
        Vec3 start;
        Vec3 end;

        void set(Level level, boolean blockTransparent, boolean blockFlora, boolean blockEntities, CollisionContext collisionContext, Vec3 start, Vec3 end) {
            this.level = level;
            this.blockTransparent = blockTransparent;
            this.blockFlora = blockFlora;
            this.blockEntities = blockEntities;
            this.collisionContext = collisionContext;
            this.start = start;
            this.end = end;
        }
    }

    /**
     * Performs a granular block state check if the primary PlayerVisionTracker passes.
     * This avoids expensive raycasts against every solid wall, only checking when
     * the path is otherwise clear or contains non-visual solids (like glass).
     */
    public static boolean canSee(Player player, Entity target, boolean blockTransparent, boolean blockFlora, boolean blockEntities) {
        // If no granular blocking rules are active, skip the secondary check to save TPS
        if (!blockTransparent && !blockFlora && !blockEntities) {
            return true;
        }

        Level level = player.level();
        Vec3 start = player.getEyePosition();
        Vec3 end = new Vec3(target.getX(), target.getY() + target.getBbHeight() / 2.0, target.getZ());
        CollisionContext collisionContext = CollisionContext.of(player);

        Context context = CONTEXT.get();
        context.set(level, blockTransparent, blockFlora, blockEntities, collisionContext, start, end);

        Boolean result = BlockGetter.traverseBlocks(start, end, context, SecondaryVisionCheck::checkBlock, ctx -> true);

        // Clean up references to prevent memory leaks
        context.set(null, false, false, false, null, null, null);

        return result != null ? result : true;
    }

    private static Boolean checkBlock(Context ctx, BlockPos pos) {
        BlockState state = ctx.level.getBlockState(pos);

        // Skip air entirely
        if (state.isAir()) {
            return null; // continue traversing
        }

        // 1. Flora Check
        if (ctx.blockFlora) {
            if (state.getBlock() instanceof BushBlock || state.getBlock() instanceof LeavesBlock) {
                return false; // Path blocked
            }
        }

        // 2. Block Entity Check (Chests, Beds, Shulkers, Banners, etc.)
        if (ctx.blockEntities) {
            if (state.hasBlockEntity()) {
                return false; // Path blocked
            }
        }

        // 3. Transparent / Non-Full Block Check
        if (ctx.blockTransparent) {
            // If the visual shape is not empty, it could block.
            // Note: PlayerVisionTracker uses ClipContext.Block.VISUAL which IGNORES glass/water.
            // We want to manually test if the Visual shape interacts with the ray.
            VoxelShape visualShape = state.getVisualShape(ctx.level, pos, ctx.collisionContext);
            if (!visualShape.isEmpty()) {
                 // Need to see if our specific ray actually hits the shape, not just passes through the block's space.
                 if (visualShape.clip(ctx.start, ctx.end, pos) != null) {
                     return false; // Ray hit the non-full/transparent visual shape
                 }
            }
        }

        return null; // continue traversing
    }
}
