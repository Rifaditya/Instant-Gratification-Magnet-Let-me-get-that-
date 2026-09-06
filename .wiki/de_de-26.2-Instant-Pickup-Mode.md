# ⚡ Sofort-Aufnahme-Modus (MC 26.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Infobox | Technische Parameter |
| :--- | :--- |
| **Systemklasse** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **Aktivierende GameRule** | `ig:magnet_instant` (Standard: `false`) |
| **Radius-GameRule** | `ig:magnet_range` (Standard: `12`, Bereich: `1..64`) |
| **Injektionspunkt** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **Zielvariable** | Spieler-Sammel-Kollisionsbox (`AABB pickupArea`) |
| **Inventar-Logik** | 100% native Vanilla-Pipeline `Player.touch(ItemEntity)` |

---

## 📖 Übersicht zum Sofort-Aufnahme-Modus

Während der Standard-Vakuummodus Gegenstände mittels Geschwindigkeitsinterpolation physikalisch durch die Luft zieht, eliminiert der **Sofort-Aufnahme-Modus** jede Flugzeit. Ist er aktiviert, werden gedroppte Gegenstände in Reichweite in derselben Mikrosekunde ins Inventar aufgenommen, in der sie erscheinen.

Statt riskanter, absturzanfälliger eigener Inventar-Einfügungsroutinen implementiert **Magnet, Let me get that!** die Sofort-Aufnahme durch zerstörungsfreie Erweiterung der nativen Sammel-Bounding-Box des Spielers innerhalb der Vanilla-Methode `aiStep()`.

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
    if (!this.ig_magnet$isMagnetEnabled() || player.isDeadOrDying() || player.isSpectator()) {
        return pickupArea;
    }

    Level level = player.level();
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_ENABLED) && ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    return pickupArea;
}
```

### Zentrale technische Garantien:
1. **Sicherheits-Gating**: Wenn der Spieler tot ist oder stirbt (`player.isDeadOrDying()`), sich im Zuschauermodus befindet (`player.isSpectator()`) oder seinen persönlichen Magneten deaktiviert hat (`!isMagnetEnabled()`), wird die Bounding-Box niemals erweitert.
2. **Serverseitige Autorität**: Die Aufnahmelogik wird ausschließlich auf dem logischen Server (`!level.isClientSide()`) ausgeführt, wodurch Geister-Items und Inventardesynchronisationen verhindert werden.
3. **Kein Doppelbewegungs-Konflikt**: Wenn `ig:magnet_instant` aktiv ist, bricht `MagnetMovement.pull()` Geschwindigkeitsaktualisierungen automatisch ab, damit Physik und Sofortaufnahme nicht um die Kontrolle konkurrieren.

---

## ⚙️ Relevante Konfiguration & GameRules

| GameRule | Typ | Standard | Beschreibung |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | Aktiviert die sofortige Teleportation ins Inventar anstelle des Fliegens. |
| `ig:magnet_range` | Integer | `12` | Der Radius (in Blöcken), um den die Sammel-AABB vergrößert wird. |
| `ig:magnet_enabled` | Boolean | `true` | Hauptschalter für die gesamte Mod. |

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Vakuum & Phasenverschiebung|de_de-26.2-Vacuum-and-Phase-Shifting]]
* [[Spieler-Umschaltung & Zustandsspeicherung|de_de-26.2-Player-Toggle-and-Persistence]]
* [[Architektur & Mixins|de_de-26.2-Architecture-and-Mixins]]
* [[Zurück zum MC 26.2 Portal|de_de-26.2-Home]]
