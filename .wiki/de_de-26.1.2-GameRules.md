# ⚙️ Vollständige GameRules-Referenz (MC 26.1.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Kategorie-Infobox | Details |
| :--- | :--- |
| **Kategorie-ID** | `magnet:magnet_category` |
| **Lokalisierter Titel** | `Magnet, Let me get that!` (`gamerule.category.magnet.magnet_category`) |
| **Registrierter Manager** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **Registry-Klasse** | `net.instantgratification.magnet.registry.ModGameRules` |
| **Registrierte Regeln gesamt** | `15` Namespaced-Regeln |

---

## 📖 In-Game-Verwaltung über GameRules

Alle globalen Mechaniken von **Magnet, Let me get that!** in Minecraft 26.1.2 werden über namespaced GameRules gesteuert, die unter der Kategorie `magnet:magnet_category` registriert sind.

```
/gamerule ig:magnet_enabled true
/gamerule ig:magnet_range 12
/gamerule ig:magnet_speed 80
/gamerule ig:magnet_instant false
```

---

## 📋 Vollständige GameRules-Referenztabelle

| GameRule-Identifikator | Typ | Standard | Bereich | Lokalisierter Anzeigename | Beschreibung & Gameplay-Auswirkung |
| :--- | :---: | :---: | :---: | :--- | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | **Magnet Enabled** | Hauptschalter, der das Gegenstand-Vakuumsystem global aktiviert oder deaktiviert. |
| `ig:magnet_range` | Integer | `12` | `1..64` | **Magnet Range** | Der sphärische Blockradius, aus dem der Spieler gedroppte Gegenstände anzieht. |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | **Magnet Noclip** | Aktiviert Phasenverschiebung, sodass angezogene Items frei durch feste Blöcke gleiten. |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | **Attract XP Orbs** | Bestimmt, ob Erfahrungskugeln gemeinsam mit Gegenständen angezogen werden. |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | **Magnet Particles** | Erzeugt elektrische Funkenspuren entlang der Flugbahn angezogener Entitäten. |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | **Particle Count** | Die Anzahl der Partikelfunken, die pro aktiver Quelle pro Partikel-Tick erzeugt werden. |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | **Max Particle Sources** | Maximale Anzahl gleichzeitiger Entitäten, die Partikel aussenden dürfen (Lag-Schutz). |
| `ig:magnet_speed` | Integer | `80` | `1..1000` | **Item Speed** | Endgeschwindigkeit in Prozent ($80 = 0.8\text{ Blöcke/Tick} = 16.0\text{ m/s}$). |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000` | **Item Acceleration** | Beschleunigungs-Interpolationsfaktor pro Tick ($10 = 10\%\text{ Lerp/Tick}$). |
| `ig:magnet_instant` | Boolean | `false` | `true/false` | **Instant Pickup** | Wenn true, teleportieren Gegenstände mit 0 Flugzeit sofort via AABB-Erweiterung ins Inventar. |
| `ig:magnet_los_only` | Boolean | `true` | `true/false` | **Line of Sight Only** | Erzwingt Sichtlinien-Sichtbarkeit; verhindert das Ziehen von Items hinter massiven Wänden. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | `true/false` | **Keep Moving if Unseen** | Lässt in Sichtlinie magnetisierte Items ihren Zugimpuls behalten, wenn die Sicht abreißt. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | `true/false` | **Blocked by Transparent** | Wenn true, blockieren Glas, Glasscheiben, Eisengitter und transparente Blöcke die Sichtlinie. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | `true/false` | **Blocked by Flora** | Wenn true, blockieren hohes Gras, Feldfrüchte, Blumen und Laub die Sichtlinie. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | `true/false` | **Blocked by Block Entities** | Wenn true, blockieren Truhen, Betten, Fässer und Shulker-Kisten die Sichtlinie. |

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Vakuum & Phasenverschiebung|de_de-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Cloth-Config-GUI|de_de-26.1.2-Configuration]]
* [[Zurück zum MC 26.1.2 Portal|de_de-26.1.2-Home]]
