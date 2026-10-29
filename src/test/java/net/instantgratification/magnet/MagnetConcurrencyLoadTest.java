// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.magnet;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import net.instantgratification.magnet.config.MagnetConfig;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MagnetConcurrencyLoadTest {

    @BeforeAll
    static void setup() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("Pillar 2: 50 Worker Threads x 400 Ops (20,000 Ops) Concurrency Load Verification")
    void testConcurrentExecutionLoad() throws InterruptedException {
        int threads = 50;
        int opsPerThread = 400;
        int expectedTotalOps = threads * opsPerThread;

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch finishGate = new CountDownLatch(threads);
        AtomicInteger completedOps = new AtomicInteger(0);
        List<Throwable> exceptions = new CopyOnWriteArrayList<>();

        for (int i = 0; i < threads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    startGate.await();
                    Random random = new Random(0xDA51C263L ^ threadId);
                    for (int op = 0; op < opsPerThread; op++) {
                        // 1. Concurrent Config Queries
                        MagnetConfig config = MagnetConfig.get();
                        assertNotNull(config, "MagnetConfig singleton must not be null");
                        boolean enabled = config.isEnabled();
                        int range = config.getRange();
                        int speed = config.getSpeed();
                        int accel = config.getAcceleration();
                        boolean noClip = config.isNoClip();
                        boolean losOnly = config.isLosOnly();

                        // 2. Concurrent Kinematics Calculations
                        double px = (random.nextDouble() - 0.5) * 20000.0;
                        double py = (random.nextDouble() - 0.5) * 512.0;
                        double pz = (random.nextDouble() - 0.5) * 20000.0;

                        double ix = (random.nextDouble() - 0.5) * 20000.0;
                        double iy = (random.nextDouble() - 0.5) * 512.0;
                        double iz = (random.nextDouble() - 0.5) * 20000.0;

                        Vec3 target = new Vec3(px, py, pz);
                        Vec3 pos = new Vec3(ix, iy, iz);
                        Vec3 currentVel = new Vec3(
                                (random.nextDouble() - 0.5) * 10.0,
                                (random.nextDouble() - 0.5) * 10.0,
                                (random.nextDouble() - 0.5) * 10.0
                        );

                        Vec3 vecToTarget = target.subtract(pos);
                        Vec3 direction = vecToTarget.lengthSqr() == 0 ? Vec3.ZERO : vecToTarget.normalize();
                        Vec3 targetVel = direction.scale(speed / 100.0);
                        Vec3 newVel = currentVel.lerp(targetVel, accel / 100.0);

                        assertTrue(Double.isFinite(newVel.x));
                        assertTrue(Double.isFinite(newVel.y));
                        assertTrue(Double.isFinite(newVel.z));

                        // 3. Concurrent SecondaryVisionCheck Null-Safety & ThreadLocal Isolation
                        boolean transparent = random.nextBoolean();
                        boolean flora = random.nextBoolean();
                        boolean entities = random.nextBoolean();
                        boolean canSee = SecondaryVisionCheck.canSee(null, null, transparent, flora, entities);
                        assertFalse(canSee);

                        completedOps.incrementAndGet();
                    }
                } catch (Throwable t) {
                    exceptions.add(t);
                } finally {
                    finishGate.countDown();
                }
            });
        }

        startGate.countDown();
        boolean finishedInTime = finishGate.await(10, TimeUnit.SECONDS);
        executor.shutdown();
        boolean terminated = executor.awaitTermination(10, TimeUnit.SECONDS);

        assertTrue(finishedInTime, "All 50 worker threads must complete within 10 seconds");
        assertTrue(terminated, "Executor service must shut down cleanly within 10 seconds");
        assertTrue(exceptions.isEmpty(), "Zero exceptions expected during concurrent load. Encountered: " + exceptions);
        assertEquals(expectedTotalOps, completedOps.get(), "Must complete exactly 20,000 operations");
    }
}
