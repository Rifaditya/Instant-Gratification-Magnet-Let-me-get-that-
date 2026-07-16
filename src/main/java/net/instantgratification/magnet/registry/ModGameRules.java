// Verified against: GameRules.java (26.2+)
package net.instantgratification.magnet.registry;

import net.instantgratification.magnet.config.MagnetConfig;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class ModGameRules {

    public static final GameRuleCategory MAGNET_CATEGORY = DynamicGameRuleManager
            .registerCategory(Identifier.parse("magnet:magnet_category"));

    public static GameRule<Boolean> MAGNET_ENABLED;
    public static GameRule<Integer> MAGNET_RANGE;
    public static GameRule<Boolean> MAGNET_AFFECTS_XP;
    public static GameRule<Boolean> MAGNET_NOCLIP;
    public static GameRule<Boolean> MAGNET_PARTICLES;
    public static GameRule<Integer> MAGNET_PARTICLE_COUNT;
    public static GameRule<Integer> MAGNET_MAX_PARTICLE_SOURCES;
    public static GameRule<Integer> MAGNET_SPEED_PERCENT;
    public static GameRule<Integer> MAGNET_ACCELERATION_PERCENT;
    public static GameRule<Boolean> MAGNET_INSTANT;
    public static GameRule<Boolean> MAGNET_LOS_ONLY;
    public static GameRule<Boolean> MAGNET_KEEP_MOVING_IF_UNSEEN;

    // Advanced LOS Configuration
    public static GameRule<Boolean> MAGNET_BLOCKED_BY_TRANSPARENT;
    public static GameRule<Boolean> MAGNET_BLOCKED_BY_FLORA;
    public static GameRule<Boolean> MAGNET_BLOCKED_BY_BLOCK_ENTITIES;

    public static void register() {
        MagnetConfig config = MagnetConfig.get();

        MAGNET_ENABLED = DynamicGameRuleManager.booleanRule("ig:magnet_enabled", MAGNET_CATEGORY, config.isEnabled())
                .name("Magnet Enabled")
                .description("Enables or disables the Item Magnet.")
                .register();
        MAGNET_RANGE = DynamicGameRuleManager.integerRule("ig:magnet_range", MAGNET_CATEGORY, config.getRange())
                .range(1, 64)
                .name("Magnet Range")
                .description("The block radius the magnet will attract items from.")
                .register();
        MAGNET_NOCLIP = DynamicGameRuleManager.booleanRule("ig:magnet_noclip", MAGNET_CATEGORY, config.isNoClip())
                .name("Magnet Noclip")
                .description("Allows attracted items to pass through solid blocks.")
                .register();
        MAGNET_AFFECTS_XP = DynamicGameRuleManager.booleanRule("ig:magnet_affects_xp", MAGNET_CATEGORY, config.isAffectsXp())
                .name("Attract XP Orbs")
                .description("Whether the magnet should also attract Experience Orbs.")
                .register();
        MAGNET_PARTICLES = DynamicGameRuleManager.booleanRule("ig:magnet_particles", MAGNET_CATEGORY, config.isParticles())
                .name("Magnet Particles")
                .description("Spawns particles on items while they are being attracted.")
                .register();
        MAGNET_PARTICLE_COUNT = DynamicGameRuleManager.integerRule("ig:magnet_particle_count", MAGNET_CATEGORY, config.getParticleCount())
                .range(0, 100)
                .name("Particle Count")
                .description("The number of particles to spawn per item per tick.")
                .register();
        MAGNET_MAX_PARTICLE_SOURCES = DynamicGameRuleManager.integerRule("ig:magnet_max_particle_sources", MAGNET_CATEGORY, config.getMaxParticleSources())
                .range(0, 100)
                .name("Max Particle Sources")
                .description("Maximum number of entities allowed to spawn particles simultaneously per tick to prevent lag.")
                .register();
        MAGNET_SPEED_PERCENT = DynamicGameRuleManager.integerRule("ig:magnet_speed", MAGNET_CATEGORY, config.getSpeed())
                .range(1, 1000)
                .name("Item Speed")
                .description("The speed at which items fly towards the player.")
                .register();
        MAGNET_ACCELERATION_PERCENT = DynamicGameRuleManager.integerRule("ig:magnet_acceleration", MAGNET_CATEGORY, config.getAcceleration())
                .range(1, 1000)
                .name("Item Acceleration")
                .description("How quickly items reach maximum speed.")
                .register();
        MAGNET_INSTANT = DynamicGameRuleManager.booleanRule("ig:magnet_instant", MAGNET_CATEGORY, config.isInstant())
                .name("Instant Pickup")
                .description("If true, items immediately teleport to the player instead of flying.")
                .register();
        MAGNET_LOS_ONLY = DynamicGameRuleManager.booleanRule("ig:magnet_los_only", MAGNET_CATEGORY, config.isLosOnly())
                .name("Line of Sight Only")
                .description("Requires line of sight to attract items.")
                .register();
        MAGNET_KEEP_MOVING_IF_UNSEEN = DynamicGameRuleManager.booleanRule("ig:magnet_keep_moving_if_unseen", MAGNET_CATEGORY, config.isKeepMovingIfUnseen())
                .name("Keep Moving if Unseen")
                .description("Items continue their momentum if line of sight is broken.")
                .register();

        // Advanced LOS Booleans
        MAGNET_BLOCKED_BY_TRANSPARENT = DynamicGameRuleManager.booleanRule("ig:magnet_blocked_by_transparent", MAGNET_CATEGORY, config.isBlockedByTransparent())
                .name("Blocked by Transparent")
                .description("If true, glass and other transparent blocks block line of sight.")
                .register();
        MAGNET_BLOCKED_BY_FLORA = DynamicGameRuleManager.booleanRule("ig:magnet_blocked_by_flora", MAGNET_CATEGORY, config.isBlockedByFlora())
                .name("Blocked by Flora")
                .description("If true, grass and flowers block line of sight.")
                .register();
        MAGNET_BLOCKED_BY_BLOCK_ENTITIES = DynamicGameRuleManager.booleanRule("ig:magnet_blocked_by_block_entities", MAGNET_CATEGORY, config.isBlockedByBlockEntities())
                .name("Blocked by Block Entities")
                .description("If true, chests and other block entities block line of sight.")
                .register();
    }

    public static int getInt(Level level, GameRule<Integer> rule) {
        return DynamicGameRuleManager.getInt(level, rule);
    }

    public static boolean getBoolean(Level level, GameRule<Boolean> rule) {
        return DynamicGameRuleManager.getBoolean(level, rule);
    }
}
