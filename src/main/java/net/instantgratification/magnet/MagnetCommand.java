package net.instantgratification.magnet;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import java.util.List;

public class MagnetCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("ig_magnet")
                .then(Commands.literal("toggle")
                    .executes(context -> toggleMagnet(context.getSource()))
                )
                .then(Commands.literal("debug")
                    .then(Commands.literal("log")
                        .executes(context -> toggleDebugLogging(context.getSource()))
                    )
                    .executes(context -> debugMagnet(context.getSource()))
                )
            );
            dispatcher.register(Commands.literal("magnet")
                .then(Commands.literal("toggle")
                    .executes(context -> toggleMagnet(context.getSource()))
                )
                .then(Commands.literal("debug")
                    .then(Commands.literal("log")
                        .executes(context -> toggleDebugLogging(context.getSource()))
                    )
                    .executes(context -> debugMagnet(context.getSource()))
                )
            );
        });
    }

    private static int toggleMagnet(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        boolean newState = ((IMagnetPlayer) player).ig_magnet$toggleMagnet();

        if (newState) {
            source.sendSuccess(() -> Component.translatable("chat.ig_magnet.enabled"), false);
        } else {
            source.sendSuccess(() -> Component.translatable("chat.ig_magnet.disabled"), false);
        }

        return 1;
    }

    private static int toggleDebugLogging(CommandSourceStack source) {
        net.instantgratification.magnet.MagnetDebugLogger.enabled = !net.instantgratification.magnet.MagnetDebugLogger.enabled;
        boolean newState = net.instantgratification.magnet.MagnetDebugLogger.enabled;
        source.sendSuccess(() -> Component.literal("§e[Magnet] Debug file logging is now " + (newState ? "§aENABLED" : "§cDISABLED")), true);
        return 1;
    }

    private static int debugMagnet(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        boolean masterEnabled = net.instantgratification.magnet.registry.ModGameRules.getBoolean(player.level(), net.instantgratification.magnet.registry.ModGameRules.MAGNET_ENABLED);
        boolean personalEnabled = ((IMagnetPlayer) player).ig_magnet$isMagnetEnabled();
        int range = net.instantgratification.magnet.registry.ModGameRules.getInt(player.level(), net.instantgratification.magnet.registry.ModGameRules.MAGNET_RANGE);
        boolean instant = net.instantgratification.magnet.registry.ModGameRules.getBoolean(player.level(), net.instantgratification.magnet.registry.ModGameRules.MAGNET_INSTANT);
        boolean losOnly = net.instantgratification.magnet.registry.ModGameRules.getBoolean(player.level(), net.instantgratification.magnet.registry.ModGameRules.MAGNET_LOS_ONLY);
        boolean logEnabled = net.instantgratification.magnet.MagnetDebugLogger.enabled;

        source.sendSuccess(() -> Component.literal("§e--- Magnet Debug Info ---"), false);
        source.sendSuccess(() -> Component.literal("Player Name: " + player.getScoreboardName()), false);
        source.sendSuccess(() -> Component.literal("Player UUID: " + player.getUUID().toString()), false);
        source.sendSuccess(() -> Component.literal("Global Master Toggle: " + masterEnabled), false);
        source.sendSuccess(() -> Component.literal("Player Toggle State: " + personalEnabled), false);
        source.sendSuccess(() -> Component.literal("Range: " + range), false);
        source.sendSuccess(() -> Component.literal("Instant Pickup: " + instant), false);
        source.sendSuccess(() -> Component.literal("LOS Only: " + losOnly), false);
        source.sendSuccess(() -> Component.literal("Spectator: " + player.isSpectator()), false);
        source.sendSuccess(() -> Component.literal("Dead/Dying: " + player.isDeadOrDying()), false);
        source.sendSuccess(() -> Component.literal("Debug File Logger: " + (logEnabled ? "§aENABLED" : "§cDISABLED") + " (Toggle with /magnet debug log)"), false);

        AABB area = player.getBoundingBox().inflate(10);
        List<net.minecraft.world.entity.item.ItemEntity> items = player.level().getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class, area, Entity::isAlive);
        source.sendSuccess(() -> Component.literal("Nearby Items (10 blocks): " + items.size()), false);
        if (!items.isEmpty()) {
            net.minecraft.world.entity.item.ItemEntity nearest = items.get(0);
            source.sendSuccess(() -> Component.literal("Nearest Item ID: " + nearest.getId()), false);
            if (losOnly) {
                boolean trackerCanSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, nearest);
                
                net.minecraft.world.phys.Vec3 start = player.getEyePosition();
                net.minecraft.world.phys.Vec3 end = new net.minecraft.world.phys.Vec3(nearest.getX(), nearest.getY() + nearest.getBbHeight() / 2.0, nearest.getZ());
                net.minecraft.world.level.ClipContext clipContext = new net.minecraft.world.level.ClipContext(
                    start, end, net.minecraft.world.level.ClipContext.Block.VISUAL, net.minecraft.world.level.ClipContext.Fluid.NONE, player
                );
                net.minecraft.world.phys.BlockHitResult hitResult = player.level().clip(clipContext);
                boolean vanillaCanSee = hitResult.getType() == net.minecraft.world.phys.HitResult.Type.MISS;

                source.sendSuccess(() -> Component.literal("PlayerVisionTracker.canSee: " + trackerCanSee), false);
                source.sendSuccess(() -> Component.literal("Vanilla Raycast canSee: " + vanillaCanSee), false);
            }
        }
        source.sendSuccess(() -> Component.literal("§e-------------------------"), false);

        return 1;
    }
}
