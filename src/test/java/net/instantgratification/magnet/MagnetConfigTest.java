// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.magnet;

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
}