// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.magnet;

import java.lang.reflect.Field;
import java.util.Map;
import net.dasik.social.api.config.ConfigOption;
import net.instantgratification.magnet.config.MagnetConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MagnetConfigTest {

    @Test
    @DisplayName("Verify MagnetConfig default configuration values")
    void testDefaultValues() {
        MagnetConfig config = new MagnetConfig();

        assertEquals(1, config.configVersion);
        assertTrue(config.isEnabled());
        assertEquals(12, config.getRange());
        assertTrue(config.isNoClip());
        assertTrue(config.isAffectsXp());
        assertTrue(config.isParticles());
        assertEquals(1, config.getParticleCount());
        assertEquals(5, config.getMaxParticleSources());
        assertEquals(80, config.getSpeed());
        assertEquals(10, config.getAcceleration());
        assertFalse(config.isInstant());
        assertTrue(config.isLosOnly());
        assertTrue(config.isKeepMovingIfUnseen());
        assertFalse(config.isBlockedByTransparent());
        assertFalse(config.isBlockedByFlora());
        assertFalse(config.isBlockedByBlockEntities());
    }

    @Test
    @DisplayName("Verify singleton instance accessibility")
    void testSingletonInstance() {
        assertNotNull(MagnetConfig.get());
    }

    @Test
    @DisplayName("Verify MOD_ID constant")
    void testModId() {
        assertEquals("ig_magnet", MagnetConfig.MOD_ID);
    }

    @Test
    @DisplayName("Verify @ConfigOption annotations and metadata across all 15 config fields")
    void testConfigOptionAnnotations() throws NoSuchFieldException {
        record ExpectedOption(String category, int order, boolean syncGameRule, String gameRuleName,
                              double min, double max, boolean slider) {}

        Map<String, ExpectedOption> expectations = Map.ofEntries(
                Map.entry("enabled", new ExpectedOption("general", 1, true, "ig:magnet_enabled", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false)),
                Map.entry("range", new ExpectedOption("general", 2, true, "ig:magnet_range", 1.0, 64.0, true)),
                Map.entry("instant", new ExpectedOption("general", 3, true, "ig:magnet_instant", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false)),
                Map.entry("noClip", new ExpectedOption("general", 4, true, "ig:magnet_noclip", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false)),
                Map.entry("speed", new ExpectedOption("speeds", 1, true, "ig:magnet_speed", 1.0, 1000.0, true)),
                Map.entry("acceleration", new ExpectedOption("speeds", 2, true, "ig:magnet_acceleration", 1.0, 1000.0, true)),
                Map.entry("losOnly", new ExpectedOption("los", 1, true, "ig:magnet_los_only", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false)),
                Map.entry("keepMovingIfUnseen", new ExpectedOption("los", 2, true, "ig:magnet_keep_moving_if_unseen", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false)),
                Map.entry("blockedByTransparent", new ExpectedOption("los", 3, true, "ig:magnet_blocked_by_transparent", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false)),
                Map.entry("blockedByFlora", new ExpectedOption("los", 4, true, "ig:magnet_blocked_by_flora", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false)),
                Map.entry("blockedByBlockEntities", new ExpectedOption("los", 5, true, "ig:magnet_blocked_by_block_entities", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false)),
                Map.entry("affectsXp", new ExpectedOption("visuals", 1, true, "ig:magnet_affects_xp", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false)),
                Map.entry("particles", new ExpectedOption("visuals", 2, true, "ig:magnet_particles", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false)),
                Map.entry("particleCount", new ExpectedOption("visuals", 3, true, "ig:magnet_particle_count", 0.0, 100.0, true)),
                Map.entry("maxParticleSources", new ExpectedOption("visuals", 4, true, "ig:magnet_max_particle_sources", 0.0, 100.0, true))
        );

        assertEquals(15, expectations.size(), "Should have exactly 15 annotated config fields");

        for (Map.Entry<String, ExpectedOption> entry : expectations.entrySet()) {
            String fieldName = entry.getKey();
            ExpectedOption expected = entry.getValue();

            Field field = MagnetConfig.class.getDeclaredField(fieldName);
            ConfigOption annotation = field.getAnnotation(ConfigOption.class);

            assertNotNull(annotation, "Field " + fieldName + " must be annotated with @ConfigOption");
            assertEquals(expected.category, annotation.category(), "Category mismatch for field " + fieldName);
            assertEquals(expected.order, annotation.order(), "Order mismatch for field " + fieldName);
            assertEquals(expected.syncGameRule, annotation.syncGameRule(), "syncGameRule mismatch for field " + fieldName);
            assertEquals(expected.gameRuleName, annotation.gameRuleName(), "gameRuleName mismatch for field " + fieldName);
            assertEquals(expected.min, annotation.min(), "Min mismatch for field " + fieldName);
            assertEquals(expected.max, annotation.max(), "Max mismatch for field " + fieldName);
            assertEquals(expected.slider, annotation.slider(), "Slider mismatch for field " + fieldName);
        }
    }
}