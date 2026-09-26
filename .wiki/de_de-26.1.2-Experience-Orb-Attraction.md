# ✨ Erfahrungs-Orb-Anziehung (MC 26.1.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Infobox | Technische Parameter |
| :--- | :--- |
| **Ziel-Entitätsklasse** | `net.minecraft.world.entity.ExperienceOrb` |
| **Manager-Klasse** | `net.instantgratification.magnet.MagnetManager` |
| **Aktivierende GameRule** | `ig:magnet_affects_xp` (Standard: `true`) |
| **Scan-Abfrage** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **Partikeltyp** | `ParticleTypes.ELECTRIC_SPARK` |
| **Partikelquellen-Obergrenze** | `ig:magnet_max_particle_sources` (Standard: `5`) |

---

## 📖 Mechanik des Erfahrungs-Vakuums

Unter der Instant-Gratification-Philosophie widerspricht das Zurücklassen von Erfahrungskugeln einem flüssigen Gameplay. In Minecraft 26.1.2 werden `ExperienceOrb`-Entitäten mit identischer Geschwindigkeits- und Phasenverschiebungsphysik wie fallengelassene Gegenstände angezogen.

```
+-------------------+     getEntitiesOfClass     +-----------------------+     MagnetMovement.pull     +--------------------+
| Spieler-Scan-AABB | -------------------------> | List<ExperienceOrb>   | --------------------------> | Spieler-Sammlung   |
+-------------------+                            +-----------------------+                             +--------------------+
                                                             |
                                                             v
                                                 [NoClip-Phasenverschiebung anwenden]
                                                 [Lerp-Geschwindigkeitsvektor anwenden]
                                                 [Gedrosselte Funken-Partikel]
```

---

## ⚡ Synchronisierte Physik & Phasenverschiebung

1. **Phasenverschiebung (NoClip)**: Erfahrungskugeln gleiten durch massive Blöcke, um zu vermeiden, dass sie hinter Wänden oder in Deckenecken festhängen.
2. **Geschwindigkeits-Interpolation**: Kugeln beschleunigen mittels konfigurierter Geschwindigkeits- und Beschleunigungsprozentsätze gleichmäßig zur Augenposition des Spielers.
3. **Sichtlinien-Gating**: Wenn `ig:magnet_los_only` true ist, müssen XP-Kugeln für den Spieler sichtbar sein oder über aktiven Magnetisierungs-Schwung verfügen.

---

## 🛡️ Lag-Prävention & Partikel-Pooling

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

* **Maximale Partikelquellen**: Nur die ersten $N$ Entitäten pro Tick erzeugen Partikelspuren.
* **Client-Sicherer Fallback**: Ist das Level in `MagnetMovement.java` kein `ServerLevel`, weichen Partikel sauber auf `level.addParticle(...)` aus.

---

## ⚙️ Relevante Konfiguration & GameRules

| GameRule | Typ | Standard | Beschreibung |
| :--- | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | Ob der Magnet Erfahrungskugeln anzieht. |
| `ig:magnet_particles` | Boolean | `true` | Hauptschalter für Partikeleffekte. |
| `ig:magnet_particle_count` | Integer | `1` | Pro Entität und Partikel-Tick erzeugte Funkenpartikel. |
| `ig:magnet_max_particle_sources` | Integer | `5` | Maximale Entitäten, die gleichzeitig Partikel emittieren dürfen. |

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Vakuum & Phasenverschiebung|de_de-26.1.2-Vacuum-and-Phase-Shifting]]
* [[GameRules-Referenz|de_de-26.1.2-GameRules]]
* [[Zurück zum MC 26.1.2 Portal|de_de-26.1.2-Home]]
