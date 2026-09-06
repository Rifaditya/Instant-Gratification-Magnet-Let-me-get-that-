# 🧲 Vakuum-Bewegung & Phasenverschiebung (MC 26.1.2)

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

In Minecraft 26.1.2 verfolgt die Vakuum-Engine kontinuierlich gültige `ItemEntity`-Instanzen innerhalb des sphärischen Radius des Spielers und zieht sie mittels Lerp-Interpolation direkt in Richtung der Augenhöhe.

Mit aktiver **Phasenverschiebung (NoClip)** gleiten Gegenstände geschmeidig durch feste Wände und Blöcke, wodurch verhindert wird, dass Drops beim Bergbau oder im Kampf hinter Hindernissen gefangen bleiben.

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

### 1. Richtungs-Einheitsvektor
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|}$$

### 2. Geschwindigkeits-Interpolation
$$\text{Geschwindigkeits-Skalar } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0} = 0.8\text{ Blöcke/Tick}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
$$\text{Beschleunigungs-Faktor } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0} = 0.10$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 3. Boden-Anti-Reibungs-Schub
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 Phasenverschiebung (NoClip-Engine)

1. **Status-Aktivierung**: `((IMagnetEntity) entity).ig$setMagnetNoClip()` aktiviert einen 2-Tick-Countdown.
2. **Physik-Überschreibung (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` sichert `originalNoPhysics` und setzt `entity.noPhysics = true`.
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` stellt `entity.noPhysics = originalNoPhysics` wieder her.
3. **Ausstoß-Verhinderung**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` blockiert Vanilla-Wandausstoßkräfte.
4. **Gravitationsaufhebung im Block**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` hebt die Schwerkraft *nur dann* auf, wenn sich das Item physisch innerhalb eines Block-Voxels befindet.

---

## ⚙️ Relevante Konfiguration & GameRules

| GameRule | Typ | Standard | Beschreibung |
| :--- | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | Hauptschalter für Vakuum-Mechaniken. |
| `ig:magnet_range` | Integer | `12` | Vakuum-Radius in Blöcken (1 bis 64). |
| `ig:magnet_speed` | Integer | `80` | Endgeschwindigkeit in Prozent ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Integer | `10` | Beschleunigungsfaktor in Prozent ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Boolean | `true` | Aktiviert Block-Phasenverschiebung während des Anziehens. |

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Sichtlinie & Hindernisse|de_de-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Sofort-Aufnahme-Modus|de_de-26.1.2-Instant-Pickup-Mode]]
* [[Architektur & Mixins|de_de-26.1.2-Architecture-and-Mixins]]
* [[Zurück zum MC 26.1.2 Portal|de_de-26.1.2-Home]]
