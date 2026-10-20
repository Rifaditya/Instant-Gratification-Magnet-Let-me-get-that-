// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.magnet.config;

import java.nio.file.Path;
import net.dasik.social.api.config.ConfigHelper;
import net.dasik.social.api.config.ConfigOption;
import net.instantgratification.magnet.MagnetMod;

public class MagnetConfig {
    public static final String MOD_ID = "ig_magnet";

    private static MagnetConfig INSTANCE = new MagnetConfig();
    private static Path CONFIG_PATH;

    public static final int VERSION = 1;
    public int configVersion = VERSION;

    // Config Fields (Matching GameRules)
    @ConfigOption(category = "general", order = 1, syncGameRule = true, gameRuleName = "ig:magnet_enabled")
    public boolean enabled = true;

    @ConfigOption(category = "general", min = 1, max = 64, slider = true, order = 2, syncGameRule = true, gameRuleName = "ig:magnet_range")
    public int range = 12;

    @ConfigOption(category = "general", order = 3, syncGameRule = true, gameRuleName = "ig:magnet_instant")
    public boolean instant = false;

    @ConfigOption(category = "general", order = 4, syncGameRule = true, gameRuleName = "ig:magnet_noclip")
    public boolean noClip = true;

    @ConfigOption(category = "speeds", min = 1, max = 1000, slider = true, order = 1, syncGameRule = true, gameRuleName = "ig:magnet_speed")
    public int speed = 80;

    @ConfigOption(category = "speeds", min = 1, max = 1000, slider = true, order = 2, syncGameRule = true, gameRuleName = "ig:magnet_acceleration")
    public int acceleration = 10;

    @ConfigOption(category = "los", order = 1, syncGameRule = true, gameRuleName = "ig:magnet_los_only")
    public boolean losOnly = true;

    @ConfigOption(category = "los", order = 2, syncGameRule = true, gameRuleName = "ig:magnet_keep_moving_if_unseen")
    public boolean keepMovingIfUnseen = true;

    @ConfigOption(category = "los", order = 3, syncGameRule = true, gameRuleName = "ig:magnet_blocked_by_transparent")
    public boolean blockedByTransparent = false;

    @ConfigOption(category = "los", order = 4, syncGameRule = true, gameRuleName = "ig:magnet_blocked_by_flora")
    public boolean blockedByFlora = false;

    @ConfigOption(category = "los", order = 5, syncGameRule = true, gameRuleName = "ig:magnet_blocked_by_block_entities")
    public boolean blockedByBlockEntities = false;

    @ConfigOption(category = "visuals", order = 1, syncGameRule = true, gameRuleName = "ig:magnet_affects_xp")
    public boolean affectsXp = true;

    @ConfigOption(category = "visuals", order = 2, syncGameRule = true, gameRuleName = "ig:magnet_particles")
    public boolean particles = true;

    @ConfigOption(category = "visuals", min = 0, max = 100, slider = true, order = 3, syncGameRule = true, gameRuleName = "ig:magnet_particle_count")
    public int particleCount = 1;

    @ConfigOption(category = "visuals", min = 0, max = 100, slider = true, order = 4, syncGameRule = true, gameRuleName = "ig:magnet_max_particle_sources")
    public int maxParticleSources = 5;

    public static synchronized void load(Path configDir) {
        CONFIG_PATH = configDir.resolve("ig_magnet.json");
        INSTANCE = ConfigHelper.load(
                CONFIG_PATH,
                INSTANCE,
                MagnetConfig.class,
                VERSION,
                cfg -> cfg.configVersion,
                (cfg, ver) -> cfg.configVersion = ver,
                null,
                MagnetMod.LOGGER
        );
    }

    public static synchronized void save() {
        if (CONFIG_PATH == null) return;
        ConfigHelper.save(CONFIG_PATH, INSTANCE, MagnetMod.LOGGER);
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
