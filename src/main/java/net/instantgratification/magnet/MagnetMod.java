// Verified against: FabricLoader.java (26.2+)
package net.instantgratification.magnet;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
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

        // Register Networking Payloads
        PayloadTypeRegistry.serverboundPlay().register(MagnetTogglePayload.TYPE, MagnetTogglePayload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(MagnetTogglePayload.TYPE, MagnetTogglePayload.CODEC);

        // Register Server-side Packet Receiver (C2S)
        ServerPlayNetworking.registerGlobalReceiver(MagnetTogglePayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                MagnetDebugLogger.log("MagnetMod: Server received packet from %s (%s) enabled=%b", context.player().getScoreboardName(), context.player().getUUID(), payload.enabled());
                ((IMagnetPlayer) context.player()).ig_magnet$setMagnetEnabled(payload.enabled());
            });
        });

        net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            boolean isEnabled = ((IMagnetPlayer) handler.player).ig_magnet$isMagnetEnabled();
            MagnetDebugLogger.log("MagnetMod: Player %s (%s) joined. Syncing enabled=%b", handler.player.getScoreboardName(), handler.player.getUUID(), isEnabled);
            ServerPlayNetworking.send(handler.player, new MagnetTogglePayload(isEnabled));
        });

        // Sync magnet state to client after respawn (new player entity needs fresh sync)
        // UUID-based state map in PlayerMixin makes COPY_FROM unnecessary
        net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            boolean isEnabled = ((IMagnetPlayer) newPlayer).ig_magnet$isMagnetEnabled();
            MagnetDebugLogger.log("MagnetMod: AFTER_RESPAWN for %s (%s) enabled=%b", newPlayer.getScoreboardName(), newPlayer.getUUID(), isEnabled);
            ServerPlayNetworking.send(newPlayer, new MagnetTogglePayload(isEnabled));
        });

        // Register Commands
        MagnetCommand.register();
    }
}

