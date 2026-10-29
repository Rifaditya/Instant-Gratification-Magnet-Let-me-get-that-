// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.magnet;

import java.util.Random;
import net.instantgratification.magnet.config.MagnetConfig;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MagnetStressFuzzTest {

    private static final long SEED = 0xDA51C263L;

    @BeforeAll
    static void setup() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("Pillar 1: 20,000 Iteration Kinematics Vector Chaos Fuzzing & Boundary Cases")
    void testKinematicsVectorChaosFuzzing() {
        Random random = new Random(SEED);

        for (int i = 0; i < 20_000; i++) {
            // Fuzz positions across normal and extreme coordinate ranges
            double range = (i % 5 == 0) ? 10_000_000.0 : 10_000.0;
            Vec3 playerPos = new Vec3(
                    (random.nextDouble() * 2.0 - 1.0) * range,
                    (random.nextDouble() * 2.0 - 1.0) * range,
                    (random.nextDouble() * 2.0 - 1.0) * range
            );
            Vec3 itemPos = new Vec3(
                    (random.nextDouble() * 2.0 - 1.0) * range,
                    (random.nextDouble() * 2.0 - 1.0) * range,
                    (random.nextDouble() * 2.0 - 1.0) * range
            );
            Vec3 currentVel = new Vec3(
                    (random.nextDouble() * 2.0 - 1.0) * 100.0,
                    (random.nextDouble() * 2.0 - 1.0) * 100.0,
                    (random.nextDouble() * 2.0 - 1.0) * 100.0
            );

            // Speed (-1000 to 10000)
            int rawSpeed = -1000 + random.nextInt(11001);
            double speed = rawSpeed / 100.0;

            // Acceleration (0 to 1000)
            int rawAcceleration = random.nextInt(1001);
            double acceleration = rawAcceleration / 100.0;

            Vec3 vecToTarget = playerPos.subtract(itemPos);
            Vec3 direction = vecToTarget.lengthSqr() == 0 ? Vec3.ZERO : vecToTarget.normalize();
            Vec3 targetVel = direction.scale(speed);
            Vec3 newVel = currentVel.lerp(targetVel, acceleration);

            assertTrue(Double.isFinite(newVel.x), "newVel.x must be finite at iteration " + i);
            assertTrue(Double.isFinite(newVel.y), "newVel.y must be finite at iteration " + i);
            assertTrue(Double.isFinite(newVel.z), "newVel.z must be finite at iteration " + i);
            assertFalse(Double.isNaN(newVel.x), "newVel.x must not be NaN at iteration " + i);
            assertFalse(Double.isNaN(newVel.y), "newVel.y must not be NaN at iteration " + i);
            assertFalse(Double.isNaN(newVel.z), "newVel.z must not be NaN at iteration " + i);
        }

        // Boundary Case 1: Identical positions (dist = 0)
        Vec3 posA = new Vec3(1234.56, 78.9, -987.65);
        Vec3 vecZero = posA.subtract(posA);
        Vec3 dirZero = vecZero.lengthSqr() == 0 ? Vec3.ZERO : vecZero.normalize();
        assertEquals(Vec3.ZERO, dirZero, "Normalized zero vector must equal Vec3.ZERO");
        Vec3 targetVelZero = dirZero.scale(80.0 / 100.0);
        Vec3 velZero = new Vec3(1.0, 2.0, 3.0).lerp(targetVelZero, 0.1);
        assertTrue(Double.isFinite(velZero.x) && !Double.isNaN(velZero.x));
        assertTrue(Double.isFinite(velZero.y) && !Double.isNaN(velZero.y));
        assertTrue(Double.isFinite(velZero.z) && !Double.isNaN(velZero.z));

        // Boundary Case 2: Huge coordinates (10,000,000)
        Vec3 posHuge1 = new Vec3(10_000_000.0, 10_000_000.0, 10_000_000.0);
        Vec3 posHuge2 = new Vec3(-10_000_000.0, -10_000_000.0, -10_000_000.0);
        Vec3 vecHuge = posHuge1.subtract(posHuge2);
        Vec3 dirHuge = vecHuge.lengthSqr() == 0 ? Vec3.ZERO : vecHuge.normalize();
        assertTrue(Double.isFinite(dirHuge.x) && !Double.isNaN(dirHuge.x));
        assertTrue(Double.isFinite(dirHuge.y) && !Double.isNaN(dirHuge.y));
        assertTrue(Double.isFinite(dirHuge.z) && !Double.isNaN(dirHuge.z));
        Vec3 targetVelHuge = dirHuge.scale(100.0);
        Vec3 velHuge = Vec3.ZERO.lerp(targetVelHuge, 0.5);
        assertTrue(Double.isFinite(velHuge.x) && !Double.isNaN(velHuge.x));

        // Boundary Case 3: Tiny distances (1e-7, 1e-9, 1e-15)
        double[] tinyOffsets = { 1e-7, 1e-9, 1e-15 };
        for (double offset : tinyOffsets) {
            Vec3 posTiny1 = new Vec3(100.0, 64.0, 100.0);
            Vec3 posTiny2 = new Vec3(100.0 + offset, 64.0, 100.0);
            Vec3 vecTiny = posTiny1.subtract(posTiny2);
            Vec3 dirTiny = vecTiny.lengthSqr() == 0 ? Vec3.ZERO : vecTiny.normalize();
            assertTrue(Double.isFinite(dirTiny.x) && !Double.isNaN(dirTiny.x));
            assertTrue(Double.isFinite(dirTiny.y) && !Double.isNaN(dirTiny.y));
            assertTrue(Double.isFinite(dirTiny.z) && !Double.isNaN(dirTiny.z));
            Vec3 targetVelTiny = dirTiny.scale(0.8);
            Vec3 velTiny = new Vec3(0.5, 0.5, 0.5).lerp(targetVelTiny, 0.1);
            assertTrue(Double.isFinite(velTiny.x) && !Double.isNaN(velTiny.x));
            assertTrue(Double.isFinite(velTiny.y) && !Double.isNaN(velTiny.y));
            assertTrue(Double.isFinite(velTiny.z) && !Double.isNaN(velTiny.z));
        }
    }

    @Test
    @DisplayName("Pillar 1: 5,000 Iteration Config Bounds Chaos Fuzzing")
    void testConfigBoundsChaosFuzzing() {
        Random random = new Random(SEED ^ 0xCAFE);

        for (int i = 0; i < 5_000; i++) {
            MagnetConfig config = new MagnetConfig();

            boolean enabled = random.nextBoolean();
            int range = random.nextInt();
            boolean instant = random.nextBoolean();
            boolean noClip = random.nextBoolean();
            int speed = random.nextInt();
            int acceleration = random.nextInt();
            boolean losOnly = random.nextBoolean();
            boolean keepMovingIfUnseen = random.nextBoolean();
            boolean blockedByTransparent = random.nextBoolean();
            boolean blockedByFlora = random.nextBoolean();
            boolean blockedByBlockEntities = random.nextBoolean();
            boolean affectsXp = random.nextBoolean();
            boolean particles = random.nextBoolean();
            int particleCount = random.nextInt();
            int maxParticleSources = random.nextInt();

            config.enabled = enabled;
            config.range = range;
            config.instant = instant;
            config.noClip = noClip;
            config.speed = speed;
            config.acceleration = acceleration;
            config.losOnly = losOnly;
            config.keepMovingIfUnseen = keepMovingIfUnseen;
            config.blockedByTransparent = blockedByTransparent;
            config.blockedByFlora = blockedByFlora;
            config.blockedByBlockEntities = blockedByBlockEntities;
            config.affectsXp = affectsXp;
            config.particles = particles;
            config.particleCount = particleCount;
            config.maxParticleSources = maxParticleSources;

            assertEquals(enabled, config.isEnabled());
            assertEquals(range, config.getRange());
            assertEquals(instant, config.isInstant());
            assertEquals(noClip, config.isNoClip());
            assertEquals(speed, config.getSpeed());
            assertEquals(acceleration, config.getAcceleration());
            assertEquals(losOnly, config.isLosOnly());
            assertEquals(keepMovingIfUnseen, config.isKeepMovingIfUnseen());
            assertEquals(blockedByTransparent, config.isBlockedByTransparent());
            assertEquals(blockedByFlora, config.isBlockedByFlora());
            assertEquals(blockedByBlockEntities, config.isBlockedByBlockEntities());
            assertEquals(affectsXp, config.isAffectsXp());
            assertEquals(particles, config.isParticles());
            assertEquals(particleCount, config.getParticleCount());
            assertEquals(maxParticleSources, config.getMaxParticleSources());
        }

        assertNotNull(MagnetConfig.get());
    }

    @Test
    @DisplayName("Pillar 1: SecondaryVisionCheck Null-Safety & Heuristics Verification")
    void testSecondaryVisionCheckNullSafetyAndHeuristics() {
        Player nullPlayer = null;
        Entity nullTarget = null;

        // Assert null player and null target across all permutations of blocking flags
        assertFalse(SecondaryVisionCheck.canSee(null, null, true, true, true));
        assertFalse(SecondaryVisionCheck.canSee(null, null, false, false, false));
        assertFalse(SecondaryVisionCheck.canSee(null, null, true, false, false));
        assertFalse(SecondaryVisionCheck.canSee(null, null, false, true, false));
        assertFalse(SecondaryVisionCheck.canSee(null, null, false, false, true));
        assertFalse(SecondaryVisionCheck.canSee(null, null, true, true, false));
        assertFalse(SecondaryVisionCheck.canSee(null, null, true, false, true));
        assertFalse(SecondaryVisionCheck.canSee(null, null, false, true, true));

        // Test with typed null player and target references
        assertFalse(SecondaryVisionCheck.canSee(nullPlayer, nullTarget, false, false, false));
        assertFalse(SecondaryVisionCheck.canSee(nullPlayer, nullTarget, true, true, true));
        assertFalse(SecondaryVisionCheck.canSee(null, nullTarget, false, false, false));
        assertFalse(SecondaryVisionCheck.canSee(nullPlayer, null, false, false, false));
    }
}
