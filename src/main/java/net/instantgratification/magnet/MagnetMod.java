// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
// Verified against: FabricLoader.java (1.20.1)
package net.instantgratification.magnet;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.instantgratification.magnet.config.MagnetConfig;
import net.instantgratification.magnet.registry.ModGameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagnetMod implements ModInitializer {
    public static final String MOD_ID = "ig_magnet";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Magnet, Let me get that! Initializing...");
        MagnetConfig.load(FabricLoader.getInstance().getConfigDir());
        ModGameRules.register();

        // Register Server-side Packet Receiver (C2S)
        ServerPlayNetworking.registerGlobalReceiver(MagnetTogglePayload.TYPE, (packet, player, responseSender) -> {
            player.server.execute(() -> {
                MagnetDebugLogger.log("MagnetMod: Server received packet from %s (%s) enabled=%b", player.getScoreboardName(), player.getUUID(), packet.enabled());
                if (player instanceof IMagnetPlayer magnetPlayer) {
                    magnetPlayer.ig_magnet$setMagnetEnabled(packet.enabled());
                }
            });
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            boolean isEnabled = ((IMagnetPlayer) handler.player).ig_magnet$isMagnetEnabled();
            MagnetDebugLogger.log("MagnetMod: Player %s (%s) joined. Syncing enabled=%b", handler.player.getScoreboardName(), handler.player.getUUID(), isEnabled);
            ServerPlayNetworking.send(handler.player, new MagnetTogglePayload(isEnabled));
        });

        // Copy player toggle state across death, respawn, and dimension travel
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            boolean isEnabled = ((IMagnetPlayer) oldPlayer).ig_magnet$isMagnetEnabled();
            ((IMagnetPlayer) newPlayer).ig_magnet$setMagnetEnabled(isEnabled);
            MagnetDebugLogger.log("MagnetMod: COPY_FROM from %s to %s (alive=%b) value=%b", oldPlayer.getScoreboardName(), newPlayer.getScoreboardName(), alive, isEnabled);
        });

        // Sync magnet state to client after respawn (new player entity needs fresh sync)
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            boolean isEnabled = ((IMagnetPlayer) newPlayer).ig_magnet$isMagnetEnabled();
            MagnetDebugLogger.log("MagnetMod: AFTER_RESPAWN for %s (%s) enabled=%b", newPlayer.getScoreboardName(), newPlayer.getUUID(), isEnabled);
            ServerPlayNetworking.send(newPlayer, new MagnetTogglePayload(isEnabled));
        });

        // Register Commands
        MagnetCommand.register();
    }
}
