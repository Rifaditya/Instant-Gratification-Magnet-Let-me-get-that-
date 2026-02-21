package net.instantgratification.magnet.registry;

import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class ModGameRules {

    public static final GameRuleCategory MAGNET_CATEGORY = DynamicGameRuleManager
            .registerCategory(Identifier.parse("magnet:magnet_category"));

    public static GameRule<Integer> MAGNET_RANGE;
    public static GameRule<Boolean> MAGNET_NOCLIP;
    public static GameRule<Boolean> MAGNET_AFFECTS_XP;
    public static GameRule<Boolean> MAGNET_PARTICLES;
    public static GameRule<Integer> MAGNET_PARTICLE_COUNT;
    public static GameRule<Integer> MAGNET_SPEED_PERCENT;
    public static GameRule<Integer> MAGNET_ACCELERATION_PERCENT;
    public static GameRule<Boolean> MAGNET_INSTANT;

    public static void register() {
        MAGNET_RANGE = DynamicGameRuleManager.registerInteger("ig_magnet_range", MAGNET_CATEGORY, 12);
        MAGNET_NOCLIP = DynamicGameRuleManager.registerBoolean("ig_magnet_noclip", MAGNET_CATEGORY, true);
        MAGNET_AFFECTS_XP = DynamicGameRuleManager.registerBoolean("ig_magnet_affects_xp", MAGNET_CATEGORY, true);
        MAGNET_PARTICLES = DynamicGameRuleManager.registerBoolean("ig_magnet_particles", MAGNET_CATEGORY, true);
        MAGNET_PARTICLE_COUNT = DynamicGameRuleManager.registerInteger("ig_magnet_particle_count", MAGNET_CATEGORY, 1);
        MAGNET_SPEED_PERCENT = DynamicGameRuleManager.registerInteger("ig_magnet_speed", MAGNET_CATEGORY, 80);
        MAGNET_ACCELERATION_PERCENT = DynamicGameRuleManager.registerInteger("ig_magnet_acceleration", MAGNET_CATEGORY,
                10);
        MAGNET_INSTANT = DynamicGameRuleManager.registerBoolean("ig_magnet_instant", MAGNET_CATEGORY, false);
    }

    public static int getInt(Level level, GameRule<Integer> rule) {
        return DynamicGameRuleManager.getInt(level, rule);
    }

    public static boolean getBoolean(Level level, GameRule<Boolean> rule) {
        return DynamicGameRuleManager.getBoolean(level, rule);
    }
}
