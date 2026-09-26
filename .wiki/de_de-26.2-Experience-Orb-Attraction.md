# ✨ Erfahrungs-Orb-Anziehung (MC 26.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Infobox | Technische Parameter |
| :--- | :--- |
| **Ziel-Entitätsklasse** | `net.minecraft.world.entity.ExperienceOrb` |
| **Manager-Klasse** | `net.instantgratification.magnet.MagnetManager` |
| **Aktivierende GameRule** | `ig:magnet_affects_xp` (Standard: `true`) |
| **Scan-Abfrage** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **Partikeltyp** | `ParticleTypes.ELECTRIC_SPARK` |
| **Partikel-Drosselung** | Geteilt mit Items über `ig:magnet_max_particle_sources` |

---

## 📖 Mechanik des Erfahrungs-Vakuums

Unter der Instant-Gratification-Philosophie stört das Zurücklassen verstreuter Erfahrungskugeln am Boden oder an Höhlendecken den Spielfluss. **Magnet, Let me get that!** bietet erstklassige Unterstützung für das Anziehen von `ExperienceOrb`-Entitäten parallel zu gedroppten Gegenständen.

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

Wenn `ig:magnet_affects_xp` aktiviert ist, übernehmen alle Erfahrungskugeln in Reichweite exakt dieselben hochentwickelten Mechaniken wie Gegenstände:

1. **Phasenverschiebung (NoClip)**: Erfahrungskugeln gleiten beim Heranziehen durch feste Blöcke, wodurch verhindert wird, dass sie ewig an Wänden abprallen oder kreisen.
2. **Dynamische Beschleunigung**: Erfahrungskugeln folgen denselben Lerp-Berechnungen für Geschwindigkeit ($s = \text{speed}/100.0$) und Beschleunigung ($a = \text{accel}/100.0$).
3. **Sichtlinien-Filterung**: Ist `ig:magnet_los_only` aktiv, müssen XP-Orbs sowohl den primären 360°-Raycast als auch die sekundären granularen Prüfungen bestehen.

---

## 🛡️ Performance & Lag-Prävention (Partikel-Obergrenzen)

Dichte Mob-Farmen oder Kämpfe gegen den Enderdrachen können hunderte Erfahrungskugeln gleichzeitig erzeugen. Das Spawnen von Partikeln an jeder einzelnen Kugel pro Tick würde zu massiven FPS-Einbrüchen führen.

Die Mod verhindert Partikel-Lag durch **globale Partikelquellen-Drosselung**:

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

* **Globale Obergrenze**: Nur die ersten $N$ Entitäten (konfiguriert über `ig:magnet_max_particle_sources`, Standard: `5`) dürfen in einem gegebenen Tick Funkenpartikel aussenden.
* **Tick-Staffelung**: Partikel werden nur erzeugt, wenn `(entity.tickCount + entity.getId()) % 4 == 0` (alle 4 Ticks = 5 Mal pro Sekunde).

---

## ⚙️ Relevante Konfiguration & GameRules

| GameRule | Typ | Standard | Einheit / Bereich | Beschreibung |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | Ob Erfahrungskugeln vom Magneten angezogen werden. |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | Hauptschalter für elektrische Funken-Spuren. |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | Anzahl der erzeugten Funkenpartikel pro aktiver Quelle. |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | Maximale Anzahl gleichzeitiger Entitäten, die Partikel aussenden dürfen. |

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Vakuum & Phasenverschiebung|de_de-26.2-Vacuum-and-Phase-Shifting]]
* [[GameRules-Referenz|de_de-26.2-GameRules]]
* [[HUD & Diagnose|de_de-26.2-HUD-and-Diagnostics]]
* [[Zurück zum MC 26.2 Portal|de_de-26.2-Home]]
