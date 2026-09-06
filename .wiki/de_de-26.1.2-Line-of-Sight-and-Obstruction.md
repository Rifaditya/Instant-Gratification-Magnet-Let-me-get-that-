# 👁️ Sichtlinien- & Hindernismechanik (MC 26.1.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Infobox | Technische Parameter |
| :--- | :--- |
| **Primäre Sicht-Engine** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **Sekundäre Sicht-Engine** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **Sphärisches Sichtfeld** | $360.0^\circ$ (Vollständige omnidirektionale Wahrnehmung) |
| **Kontaktabstandstoleranz** | $0.3\text{ m}$ Zielkontaktschwelle |
| **Speicher-Aufbewahrungs-Tag** | `ig$isMagnetized()` / `ig$setMagnetized()` |
| **Schwung-Regel** | `ig:magnet_keep_moving_if_unseen = true` |
| **Kontext-Implementierung** | Statischer `VisionContext`-Record |

---

## 📖 Dual-Pass-Sicht-Pipeline

In Minecraft 26.1.2 wird die Sichtlinie über eine allokationsfreie **Dual-Pass-Sicht-Pipeline** ausgewertet:

```
                                +---------------------------+
                                |    ZIEL-ITEM ERKANNT      |
                                +-------------+-------------+
                                              |
                                              v
                              +-------------------------------+
                              |    PRIMÄRER DURCHLAUF (360° LOS) |
                              |   PlayerVisionTracker.canSee  |
                              +---------------+---------------+
                                              |
                        +---------------------+---------------------+
                        |                                           |
                   [SICHTBAR]                                  [VERDECKT]
                        |                                           |
                        v                                           v
      +-----------------------------------+               +--------------------+
      |    SEKUNDÄRER DURCHLAUF (GRANULAR)|               |   SCHWUNG-PRÜFUNG  |
      |     SecondaryVisionCheck.canSee   |               | keepMovingIfUnseen |
      +-----------------+-----------------+               +---------+----------+
                        |                                           |
            +-----------+-----------+                     +---------+---------+
            |                       |                     |                   |
       [BESTANDEN]             [BLOCKIERT]            [MAGNETISIERT]    [UNMAGNETISIERT]
            |                       |                     |                   |
            v                       v                     v                   v
    +---------------+       +---------------+     +---------------+   +---------------+
    |  ITEM ZIEHEN  |       | ABWEISEN /    |     | ZIEHEN WEITER |   | ZIEHEN        |
    | & MAGNETISIEREN|      | STOPPEN       |     | AUSFÜHREN     |   | ABWEISEN      |
    +---------------+       +---------------+     +---------------+   +---------------+
```

---

## 🔍 Durchlauf 1: Primäre 360° sphärische Sichtlinienprüfung

```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **Omnidirektionales Feld**: Mit einem $360^\circ$-Sichtfeldwinkel werden gedroppte Gegenstände oberhalb, unterhalb oder hinter dem Spieler reibungslos angezogen, ohne die Kamera drehen zu müssen.
* **Sub-Voxel-Kontakttoleranz**: Die Toleranz von $0.3\text{ m}$ verhindert Fehlweisungen, wenn Gegenstände bündig an soliden Wänden anliegen.

---

## 🌿 Durchlauf 2: Granulare Blockdurchquerung (`SecondaryVisionCheck`)

```java
private record VisionContext(
        Level level,
        boolean blockTransparent,
        boolean blockFlora,
        boolean blockEntities,
        CollisionContext collisionContext,
        Vec3 start,
        Vec3 end
) {}
```

* **Flora (`ig:magnet_blocked_by_flora`)**: Prüft auf `BushBlock` und `LeavesBlock`.
* **Block-Entities (`ig:magnet_blocked_by_block_entities`)**: Prüft `state.hasBlockEntity()` (Truhen, Shulker-Kisten, Betten).
* **Transparente Blöcke (`ig:magnet_blocked_by_transparent`)**: Testet `state.getVisualShape().clip(...)` gegen Glas, Scheiben und Stufen.

---

## 🚀 Schwung-Kontinuität (`keepMovingIfUnseen`)

* Sobald ein Gegenstand erstmals gesehen wird, markiert `((IMagnetEntity) entity).ig$setMagnetized()` die Entität.
* Wenn `ig:magnet_keep_moving_if_unseen = true`, wird das Item weiterhin um Hindernisse herumgezogen, solange es zuvor magnetisiert wurde.

---

## ⚙️ Relevante Konfiguration & GameRules

| GameRule | Typ | Standard | Beschreibung |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | Erfordert Sichtlinie, um Gegenstände anzuziehen. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | Zieht magnetisierte Gegenstände weiter, wenn die Sichtlinie im Flug abreißt. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | Wenn true, blockieren Glas und transparente Blöcke die Sichtlinie. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | Wenn true, blockieren Gras und Blumen die Sichtlinie. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | Wenn true, blockieren Truhen und Block-Entities die Sichtlinie. |

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Vakuum & Phasenverschiebung|de_de-26.1.2-Vacuum-and-Phase-Shifting]]
* [[GameRules-Referenz|de_de-26.1.2-GameRules]]
* [[Zurück zum MC 26.1.2 Portal|de_de-26.1.2-Home]]
