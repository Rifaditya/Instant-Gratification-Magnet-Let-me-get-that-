# 🧲 Vakuum-Bewegung & Phasenverschiebung (MC 26.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Infobox | Technische Parameter |
| :--- | :--- |
| **Systemklasse** | `net.instantgratification.magnet.MagnetMovement` |
| **Auslösendes Ereignis** | Server Player Tick (`PlayerMixin` $\rightarrow$ `MagnetManager.tick`) |
| **Standard-Ziehreichweite** | `12` Blöcke (`ig:magnet_range`) |
| **Standard-Endgeschwindigkeit** | `80%` ($0.8\text{ Blöcke/Tick} = 16.0\text{ m/s}$) |
| **Standard-Beschleunigung** | `10%` ($0.10\text{ Lerp-Faktor/Tick}$) |
| **Phasenverschiebung (NoClip)** | Aktiviert (`ig:magnet_noclip = true`) |
| **Zielvektor** | Augenposition des Spielers (`player.getEyePosition()`) |
| **Boden-Offset-Schub** | $+0.05\text{ m}$ auf Y-Achse, wenn `entity.onGround()` |

---

## 📖 Systemübersicht

Die zentrale Vakuum-Mechanik in **Magnet, Let me get that!** scannt in jedem Server-Tick nach fallengelassenen `ItemEntity`-Instanzen innerhalb des konfigurierten Spieler-Radius und zieht diese mittels weicher nicht-linearer Interpolation in Richtung der Augenhöhe des Spielers.

Um zu verhindern, dass Gegenstände an Bruchstufen, Baumkronen oder Erzadern hängenbleiben, aktiviert die Mod **Phasenverschiebung (NoClip)**, wodurch fliegende Gegenstände gefahrlos durch massive Block-Voxel hindurchgleiten können.

```
+-------------+      Sichtlinie OK       +-----------------------+      Lerp-Geschwindigkeit angewendet     +------------------+
| Item-Entity | -----------------------> | Setze NoClip (2 Ticks)| ---------------------------------------> | Spieler-Augenpos |
+-------------+                          +-----------------------+                                          +------------------+
                                                     |
                                                     v
                                          [Wand-Ausstoßen abbrechen]
                                          [Block-Kollision umgehen]
                                          [Im-Block-Gravitation stoppen]
```

---

## 🧮 Physik & Vektormathematik

Beim Heranziehen eines Gegenstands wird die Flugbahn direkt im dreidimensionalen euklidischen Raum berechnet:

### 1. Vektor zum Ziel
Sei $\vec{p}_{\text{entity}} = (x_e, y_e, z_e)$ die aktuelle Position des Gegenstands und $\vec{p}_{\text{eye}} = (x_p, y_p + h_{\text{eye}}, z_p)$ die Augenposition des Spielers.
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|} = \frac{\vec{v}_{\text{target}}}{\sqrt{\Delta x^2 + \Delta y^2 + \Delta z^2}}$$

### 2. Gewünschte Endgeschwindigkeit
Der Ziel-Geschwindigkeitsvektor skaliert die Einheitsrichtung $\hat{d}$ mit dem konfigurierten Geschwindigkeitsparameter:
$$\text{Geschwindigkeits-Skalar } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
*Bei Standardeinstellung ($80\%$) gilt $s = 0.8\text{ Blöcke/Tick}$. Bei $20\text{ Ticks/s}$ beträgt die Endgeschwindigkeit $16.0\text{ m/s}$.*

### 3. Nicht-lineare Beschleunigung (Lerp)
Die Geschwindigkeit wird mittels linearer Interpolation (`Vec3.lerp`) basierend auf dem Beschleunigungsfaktor $a$ aktualisiert:
$$\text{Beschleunigungs-Faktor } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0}$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 4. Boden-Anti-Reibungs-Schub
Befindet sich der Gegenstand auf einer Blockoberfläche (`entity.onGround() == true`), wird die Bodenreibung sofort aufgehoben, um ein Schleifen über den Boden zu verhindern:
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 Phasenverschiebung (NoClip-Engine)

Wenn `ig:magnet_noclip` aktiviert ist, werden Gegenstände mit einem 2-Tick NoClip-Fenster markiert:

1. **Status-Aktivierung**: `((IMagnetEntity) entity).ig_magnet$setMagnetNoClip()` setzt `noClipTicks = 2`.
2. **Bewegungs-Hijacking (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` sichert `originalNoPhysics` und erzwingt `entity.noPhysics = true`.
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` stellt `entity.noPhysics = originalNoPhysics` wieder her.
3. **Ausstoßen abbrechen**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` verhindert, dass der Vanillacode Gegenstände aggressiv aus Blöcken herausdrückt.
4. **Bedingte Gravitationsaufhebung**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` bricht die Abwärtsbeschleunigung *nur dann* ab, wenn der Gegenstand physisch einen Block schneidet (`!level.noCollision(...)`), wodurch natürliche Wurfbahnen in freier Luft erhalten bleiben.

---

## ⚙️ Relevante Konfiguration & GameRules

| GameRule | Typ | Standard | Einheit / Bereich | Beschreibung |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | Hauptschalter für die gesamte Vakuum-Logik. |
| `ig:magnet_range` | Integer | `12` | `1..64` Blöcke | Maximaler sphärischer Vakuum-Radius. |
| `ig:magnet_speed` | Integer | `80` | `1..1000%` | Endgeschwindigkeit in Prozent ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000%` | Anzugsbeschleunigung in Prozent ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | Aktiviert Block-Phasenverschiebung während des Anziehens. |

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Sichtlinie & Hindernisse|de_de-26.2-Line-of-Sight-and-Obstruction]]
* [[Sofort-Aufnahme-Modus|de_de-26.2-Instant-Pickup-Mode]]
* [[Architektur & Mixins|de_de-26.2-Architecture-and-Mixins]]
* [[Zurück zum MC 26.2 Portal|de_de-26.2-Home]]
