# ✨ Experience Orb Attraction (MC 26.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Feature Infobox | Technical Parameters |
| :--- | :--- |
| **Target Entity Class** | `net.minecraft.world.entity.ExperienceOrb` |
| **Manager Class** | `net.instantgratification.magnet.MagnetManager` |
| **Enabling GameRule** | `ig:magnet_affects_xp` (Default: `true`) |
| **Scanning Query** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **Particle Type** | `ParticleTypes.ELECTRIC_SPARK` |
| **Particle Throttle** | Shared with items via `ig:magnet_max_particle_sources` |

---

## 📖 Experience Vacuum Mechanics

Under the Instant Gratification philosophy, leaving experience orbs scattered on the ground disrupts gameplay flow. **Magnet, Let me get that!** provides synchronized attraction for `ExperienceOrb` entities.

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

1. **Phase Shifting (NoClip)**: Experience orbs clip through solid blocks when pulled.
2. **Dynamic Acceleration**: Experience orbs follow the same speed and acceleration lerp math.
3. **Line-of-Sight Filtering**: When `ig:magnet_los_only` is true, XP orbs must pass LOS visibility checks.

---

## 🛡️ Performance & Lag Prevention (Particle Caps)

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

* **Global Cap**: Governed by `ig:magnet_max_particle_sources` (default: `5`).
* **Tick Staggering**: Particles emit when `(entity.tickCount + entity.getId()) % 4 == 0`.

---

## ⚙️ Relevant Configuration & GameRules

| GameRule | Type | Default | Description |
| :--- | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | Whether experience orbs are pulled by the magnet. |
| `ig:magnet_particles` | Boolean | `true` | Master toggle for electric spark visual trails. |
| `ig:magnet_particle_count` | Integer | `1` | Number of spark particles spawned per active source. |
| `ig:magnet_max_particle_sources` | Integer | `5` | Max simultaneous entities allowed to emit particles. |

---

## 🔗 Related Wiki Documentation
* [[Vacuum & Phase Shifting Physics|Vacuum-and-Phase-Shifting]]
* [[GameRules Complete Reference|GameRules]]
* [[HUD, Visuals & Diagnostics|HUD-and-Diagnostics]]
* [[Return to Home Portal|Home]]
