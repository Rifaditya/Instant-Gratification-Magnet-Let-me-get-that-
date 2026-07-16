// Verified against: BetterDogsConfig.java (26.2+)
package net.instantgratification.magnet.config;

import java.nio.file.Path;

public class MagnetConfig {
    private static MagnetConfig INSTANCE = new MagnetConfig();
    private static Path CONFIG_PATH;

    public static final int VERSION = 1;
    public int configVersion = VERSION;

    // Config Fields (Matching GameRules)
    public boolean enabled = true;
    public int range = 12;
    public boolean noClip = true;
    public boolean affectsXp = true;
    public boolean particles = true;
    public int particleCount = 1;
    public int maxParticleSources = 5;
    public int speed = 80;
    public int acceleration = 10;
    public boolean instant = false;
    public boolean losOnly = true;
    public boolean keepMovingIfUnseen = true;
    public boolean blockedByTransparent = false;
    public boolean blockedByFlora = false;
    public boolean blockedByBlockEntities = false;

    public static synchronized void load(Path configDir) {
        CONFIG_PATH = configDir.resolve("ig_magnet.json");
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("Magnet Mod Config");
        INSTANCE = net.dasik.social.api.config.ConfigHelper.load(
                CONFIG_PATH,
                INSTANCE,
                MagnetConfig.class,
                VERSION,
                config -> config.configVersion,
                (config, ver) -> config.configVersion = ver,
                "/ig_magnet.json",
                logger
        );
    }

    public static synchronized void save() {
        if (CONFIG_PATH == null) return;
        net.dasik.social.api.config.ConfigHelper.save(
                CONFIG_PATH,
                INSTANCE,
                org.slf4j.LoggerFactory.getLogger("Magnet Mod Config")
        );
    }

    public static MagnetConfig get() {
        return INSTANCE;
    }

    // Getters for registration
    public boolean isEnabled() { return enabled; }
    public int getRange() { return range; }
    public boolean isNoClip() { return noClip; }
    public boolean isAffectsXp() { return affectsXp; }
    public boolean isParticles() { return particles; }
    public int getParticleCount() { return particleCount; }
    public int getMaxParticleSources() { return maxParticleSources; }
    public int getSpeed() { return speed; }
    public int getAcceleration() { return acceleration; }
    public boolean isInstant() { return instant; }
    public boolean isLosOnly() { return losOnly; }
    public boolean isKeepMovingIfUnseen() { return keepMovingIfUnseen; }
    public boolean isBlockedByTransparent() { return blockedByTransparent; }
    public boolean isBlockedByFlora() { return blockedByFlora; }
    public boolean isBlockedByBlockEntities() { return blockedByBlockEntities; }
}
