package net.instantgratification.magnet.registry;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;

public class ModGameRules {



    public static GameRule<Integer> MAGNET_RANGE;
    public static GameRule<Boolean> MAGNET_NOCLIP;
    public static GameRule<Boolean> MAGNET_AFFECTS_XP;
    public static GameRule<Boolean> MAGNET_PARTICLES;
    public static GameRule<Integer> MAGNET_PARTICLE_COUNT;
    public static GameRule<Integer> MAGNET_SPEED_PERCENT;
    public static GameRule<Integer> MAGNET_ACCELERATION_PERCENT;

    public static void register() {
        MAGNET_RANGE = registerInteger("ig_magnet_range", GameRuleCategory.MISC, 12);
        MAGNET_NOCLIP = registerBoolean("ig_magnet_noclip", GameRuleCategory.MISC, true);
        MAGNET_AFFECTS_XP = registerBoolean("ig_magnet_affects_xp", GameRuleCategory.MISC, true);
        MAGNET_PARTICLES = registerBoolean("ig_magnet_particles", GameRuleCategory.MISC, true);
        MAGNET_PARTICLE_COUNT = registerInteger("ig_magnet_particle_count", GameRuleCategory.MISC, 1);
        MAGNET_SPEED_PERCENT = registerInteger("ig_magnet_speed", GameRuleCategory.MISC, 80);
        MAGNET_ACCELERATION_PERCENT = registerInteger("ig_magnet_acceleration", GameRuleCategory.MISC, 10);
    }

    // ==================== Accessors ====================

    public static int getInt(Level level, GameRule<Integer> rule) {
        if (level.isClientSide())
            return 0; // Default or handle client side logic if needed
        return ((ServerLevel) level).getGameRules().get(rule);
    }

    public static boolean getBoolean(Level level, GameRule<Boolean> rule) {
        if (level.isClientSide())
            return false;
        return ((ServerLevel) level).getGameRules().get(rule);
    }

    // ==================== Helpers ====================

    private static GameRule<Boolean> registerBoolean(String id, GameRuleCategory category, boolean defaultValue) {
        return Registry.register(BuiltInRegistries.GAME_RULE, id, new GameRule<>(
                category,
                GameRuleType.BOOL,
                BoolArgumentType.bool(),
                GameRuleTypeVisitor::visitBoolean,
                Codec.BOOL,
                b -> b ? 1 : 0,
                defaultValue,
                FeatureFlagSet.of()));
    }

    private static GameRule<Integer> registerInteger(String id, GameRuleCategory category, int defaultValue) {
        return Registry.register(BuiltInRegistries.GAME_RULE, id, new GameRule<>(
                category,
                GameRuleType.INT,
                IntegerArgumentType.integer(0),
                GameRuleTypeVisitor::visitInteger,
                Codec.INT,
                i -> i,
                defaultValue,
                FeatureFlagSet.of()));
    }
}
