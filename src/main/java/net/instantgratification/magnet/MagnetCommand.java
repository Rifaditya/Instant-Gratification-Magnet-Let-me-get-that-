package net.instantgratification.magnet;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class MagnetCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("ig_magnet")
                .then(Commands.literal("toggle")
                    .executes(context -> toggleMagnet(context.getSource()))
                )
            );
            dispatcher.register(Commands.literal("magnet")
                .then(Commands.literal("toggle")
                    .executes(context -> toggleMagnet(context.getSource()))
                )
            );
        });
    }

    private static int toggleMagnet(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        boolean newState = MagnetPlayerState.toggleMagnet(player);

        if (newState) {
            source.sendSuccess(() -> Component.translatable("chat.ig_magnet.enabled"), false);
        } else {
            source.sendSuccess(() -> Component.translatable("chat.ig_magnet.disabled"), false);
        }

        if (ServerPlayNetworking.canSend(player, MagnetTogglePayload.TYPE)) {
            ServerPlayNetworking.send(player, new MagnetTogglePayload(newState));
        }

        return 1;
    }
}
