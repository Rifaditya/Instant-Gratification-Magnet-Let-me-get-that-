# ✨ Experience Orb Attraction (MC 26.1.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Feature Infobox | Technical Parameters |
| :--- | :--- |
| **Target Entity Class** | `net.minecraft.world.entity.ExperienceOrb` |
| **Manager Class** | `net.instantgratification.magnet.MagnetManager` |
| **Enabling GameRule** | `ig:magnet_affects_xp` (Default: `true`) |
| **Scanning Query** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **Particle Type** | `ParticleTypes.ELECTRIC_SPARK` |
| **Particle Source Cap** | `ig:magnet_max_particle_sources` (Default: `5`) |

---

## 📖 Experience Vacuum Mechanics

Under the Instant Gratification philosophy, leaving experience orbs behind is counter to smooth gameplay. In Minecraft 26.1.2, `ExperienceOrb` entities are attracted with identical velocity and phase-shifting physics as dropped items.

```
+------------------+     getEntitiesOfClass     +-----------------------+     MagnetMovement.pull     +--------------------+
| Player Scan AABB | -------------------------> | List<ExperienceOrb>   | --------------------------> | Player Collection  |
+------------------+                            +-----------------------+                             +--------------------+
                                                            |
                                                            v
                                                [Apply NoClip Phase-Shift]
                                                [Apply Lerp Velocity Vector]
                                                [Throttled Spark Particles]
```

---

## ⚡ Synchronized Physics & Phase Shifting

1. **Phase Shifting (NoClip)**: Experience orbs pass through solid blocks to avoid getting trapped behind walls or inside ceiling corners.
2. **Velocity Interpolation**: Orbs accelerate smoothly toward player eye position using configured speed and acceleration percentages.
3. **LOS Gating**: When `ig:magnet_los_only` is true, XP orbs must be visible to the player or have active magnetization momentum.

---

## 🛡️ Lag Prevention & Particle Pooling

```java
int maxParticleSources = ModGameRules.getInt(player.level(), ModGameRules.MAGNET_MAX_PARTICLE_SOURCES);
int particleSourceCount = 0;

for (ExperienceOrb orb : orbs) {
    boolean shouldSpawnParticles = false;
    if (particleSourceCount < maxParticleSources) {
        shouldSpawnParticles = true;
        particleSourceCount++;
    }
    MagnetMovement.pull(orb, player, shouldSpawnParticles);
}
```

* **Max Particle Sources**: Only the first $N$ entities per tick spawn visual trails.
* **Client-Safe Fallback**: In `MagnetMovement.java`, if level is not a `ServerLevel`, particles fall back gracefully to `level.addParticle(...)`.

---

## ⚙️ Relevant Configuration & GameRules

| GameRule | Type | Default | Description |
| :--- | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | Whether the magnet attracts Experience Orbs. |
| `ig:magnet_particles` | Boolean | `true` | Master toggle for particle effects. |
| `ig:magnet_particle_count` | Integer | `1` | Spark particles spawned per entity per particle tick. |
| `ig:magnet_max_particle_sources` | Integer | `5` | Max entities allowed to emit particles simultaneously. |

---

## 🔗 Related Wiki Documentation
* [[Vacuum & Phase Shifting Physics|Vacuum-and-Phase-Shifting]]
* [[GameRules Complete Reference|GameRules]]
* [[Return to Home Portal|Home]]
