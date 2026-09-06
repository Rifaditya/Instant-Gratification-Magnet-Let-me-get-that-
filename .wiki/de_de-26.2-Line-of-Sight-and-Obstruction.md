# 👁️ Sichtlinien- & Hindernismechanik (MC 26.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Infobox | Technische Parameter |
| :--- | :--- |
| **Primäre Sicht-Engine** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **Sekundäre Sicht-Engine** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **Sphärisches Sichtfeld** | $360.0^\circ$ (Vollständige omnidirektionale Wahrnehmung) |
| **Kontaktabstandstoleranz** | $0.3\text{ m}$ Zielkontaktschwelle |
| **Speicher-Aufbewahrungs-Tag** | `ig_magnet$isMagnetized()` / `ig_magnet$setMagnetized()` |
| **Schwung-Regel** | `ig:magnet_keep_moving_if_unseen = true` |
| **Granulare Filterregeln** | Transparente Blöcke, Flora, Block-Entities |

---

## 📖 Dual-Pass-Sichtarchitektur

Um unverdientes Einsammeln von Gegenständen durch Höhlenwände und gesicherte Basen zu verhindern und gleichzeitig verzögerungsfreie Leistung zu garantieren, setzt **Magnet, Let me get that!** eine hochperformante **Dual-Pass-Sicht-Pipeline** ein:

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

Der primäre Durchlauf ruft die optimierte Raycasting-Engine der DasikLibrary auf:
```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **Omnidirektionales Sichtfeld**: Mit einem $360.0^\circ$-Sichtfeldkegel kann der Spieler Gegenstände direkt hinter seinem Kopf, über sich oder unter seinen Füßen anziehen, solange keine massive Wand dazwischen liegt.
* **0.3m Kontakttoleranz**: Wenn Gegenstände dicht an Blockkanten liegen, stellt das Sub-Voxel-Raycasting mit einem Radius von $0.3\text{ m}$ sicher, dass Drops nicht fälschlicherweise abgewiesen werden.

---

## 🌿 Durchlauf 2: Granulare Blockstatus-Filterung (`SecondaryVisionCheck`)

Ist der primäre Durchlauf erfolgreich, prüft die Mod optionale granulare Hindernisregeln via `BlockGetter.traverseBlocks`:

```java
public static boolean canSee(Player player, Entity target, boolean blockTransparent, boolean blockFlora, boolean blockEntities)
```

1. **Flora- & Blattwerk-Filterung (`ig:magnet_blocked_by_flora`)**:
   - Prüft, ob ein durchquerter Block eine Instanz von `BushBlock` (hohes Gras, Blumen, Feldfrüchte, Setzlinge) oder `LeavesBlock` (Baumlaub) ist.
   - Wenn aktiviert (`true`), wirkt Blattwerk als undurchsichtige Barriere gegen das Anziehen von Gegenständen.
2. **Interaktive Block-Entities (`ig:magnet_blocked_by_block_entities`)**:
   - Prüft `state.hasBlockEntity()` entlang der durchquerten Koordinaten.
   - Wenn aktiviert (`true`), blockieren Truhen, Redstone-Truhen, Fässer, Shulker-Kisten, Betten und Spender die Sichtlinie.
3. **Transparente & nicht-volle Blöcke (`ig:magnet_blocked_by_transparent`)**:
   - Führt Raycasts gegen `state.getVisualShape(...)` durch.
   - Wenn aktiviert (`true`), blockieren Glas, Glasscheiben, Eisengitter, Zäune, Stufen und Treppen das Einsammeln.

---

## 🚀 Schwung-Kontinuität (`keepMovingIfUnseen`)

Beim schnellen Bergbau oder im Kampf brechen Gegenstände, die um eine Ecke gezogen werden, oft kurzzeitig die Sichtlinie. Anstatt einzufrieren oder in Lava zu stürzen:

1. **Magnetisierungs-Tag**: Sobald ein Gegenstand in Sichtlinie erfasst wird, setzt `((IMagnetEntity) entity).ig_magnet$setMagnetized()` ein In-Memory-Boolean-Flag.
2. **Schwung-Beibehaltung**: Verliert der Gegenstand in nachfolgenden Ticks die Sichtlinie:
   - Wenn `ig:magnet_keep_moving_if_unseen = true` UND `ig_magnet$isMagnetized() == true`: Der Gegenstand wird weiterhin zum Spieler gezogen.
   - Wenn `ig:magnet_keep_moving_if_unseen = false`: Das Anziehen stoppt sofort beim Verlust der Sichtlinie.
   - Wurde der Gegenstand **niemals gesehen** (z. B. durch eine Explosion oder einen Spender hinter einer Wand erzeugt): Das Anziehen wird sofort abgewiesen.

---

## ⚙️ Relevante Konfiguration & GameRules

| GameRule | Typ | Standard | Beschreibung |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | Erfordert Sichtlinien-Sichtbarkeit zum Anziehen von Gegenständen. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | Zieht magnetisierte Gegenstände weiter, wenn die Sichtlinie im Flug unterbrochen wird. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | Wenn true, behindern Glas und transparente Blöcke die Sichtlinie. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | Wenn true, behindern Gras, Laub und Blumen die Sichtlinie. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | Wenn true, behindern Truhen, Betten und Block-Entities die Sichtlinie. |

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Vakuum & Phasenverschiebung|de_de-26.2-Vacuum-and-Phase-Shifting]]
* [[GameRules-Referenz|de_de-26.2-GameRules]]
* [[Architektur & Mixins|de_de-26.2-Architecture-and-Mixins]]
* [[Zurück zum MC 26.2 Portal|de_de-26.2-Home]]
