package net.instantgratification.magnet;

import net.fabricmc.api.ModInitializer;
import net.instantgratification.magnet.registry.ModGameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagnetMod implements ModInitializer {
    public static final String MOD_ID = "ig_magnet";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Magnet, Let me get that! Initializing...");
        ModGameRules.register();
    }
}
