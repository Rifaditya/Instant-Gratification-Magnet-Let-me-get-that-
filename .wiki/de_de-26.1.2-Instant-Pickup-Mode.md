# ⚡ Sofort-Aufnahme-Modus (MC 26.1.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Infobox | Technische Parameter |
| :--- | :--- |
| **Systemklasse** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **Aktivierende GameRule** | `ig:magnet_instant` (Standard: `false`) |
| **Radius-GameRule** | `ig:magnet_range` (Standard: `12`, Bereich: `1..64`) |
| **Injektionspunkt** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **Zielvariable** | Spieler-Sammel-Kollisionsbox (`AABB pickupArea`) |

---

## 📖 Übersicht zum Sofort-Aufnahme-Modus

In Minecraft 26.1.2 eliminiert der **Sofort-Aufnahme-Modus** jede Flugzeit von Gegenständen, indem die Sammel-Bounding-Box des Spielers in `Player.aiStep()` vergrößert wird. Drops werden über die regulären Vanilla-Aufnahme-Handler sofort direkt in das Inventar des Spielers geleitet.

```
+-----------------------------------------------------------------------------------+
|                           VANILLA SPIELER aiStep() TICK                           |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
                +---------------------------------------------------+
                |   PlayerMixin.ig_magnet$expandPickupArea()        |
                |   pickupArea = pickupArea.inflate(range);         |
                +---------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|               VANILLA ItemEntity.playerTouch(Player) AUSFÜHRUNG                   |
|   - Natives Inventar-Stacking & Teilaufnahmen                                     |
|   - Vanilla Aufnahme-Animation & Soundeffekte (item.pickup / experience_orb)      |
|   - Native Statistiken- & Fortschrittsauslöser                                    |
|   - Vollständiger Behälterüberlauf & Beibehaltung verbleibender Items             |
+-----------------------------------------------------------------------------------+
```

---

## 🧩 Architektonische Implementierung (`PlayerMixin.java`)

```java
@ModifyVariable(
        method = "aiStep",
        at = @At(value = "STORE"),
        ordinal = 0
)
private AABB ig_magnet$expandPickupArea(AABB pickupArea) {
    Player player = (Player) (Object) this;
    Level level = player.level();
    
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    
    return pickupArea;
}
```

---

## ⚙️ Relevante Konfiguration & GameRules

| GameRule | Typ | Standard | Beschreibung |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | Wenn true, teleportieren Gegenstände sofort zum Spieler, anstatt zu fliegen. |
| `ig:magnet_range` | Integer | `12` | Radius in Blöcken für den erweiterten Aufnahmebereich. |
| `ig:magnet_enabled` | Boolean | `true` | Hauptschalter für die gesamte Mod. |

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Vakuum & Phasenverschiebung|de_de-26.1.2-Vacuum-and-Phase-Shifting]]
* [[GameRules-Referenz|de_de-26.1.2-GameRules]]
* [[Zurück zum MC 26.1.2 Portal|de_de-26.1.2-Home]]
