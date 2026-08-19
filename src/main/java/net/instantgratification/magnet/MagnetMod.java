// Verified against: FabricLoader.java (26.1.2)
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
            MagnetPlayerState.setMagnetEnabled(context.player(), payload.enabled());
        });

        // Register Commands
        MagnetCommand.register();
    }
}

