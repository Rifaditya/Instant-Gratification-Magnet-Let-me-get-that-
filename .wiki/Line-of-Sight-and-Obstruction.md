# 👁️ Line of Sight & Obstruction Mechanics (MC 26.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Feature Infobox | Technical Parameters |
| :--- | :--- |
| **Primary Vision Engine** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **Secondary Vision Engine** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **Spherical Field of View** | $360.0^\circ$ (Full omnidirectional perception) |
| **Contact Distance Tolerance** | $0.3\text{ m}$ target contact threshold |
| **Memory Retention Tag** | `ig_magnet$isMagnetized()` / `ig_magnet$setMagnetized()` |
| **Momentum Rule** | `ig:magnet_keep_moving_if_unseen = true` |
| **Granular Filter Rules** | Transparent blocks, Flora, Block Entities |

---

## 📖 Dual-Pass Vision Pipeline

```
                                +---------------------------+
                                |    TARGET ITEM DETECTED   |
                                +-------------+-------------+
                                              |
                                              v
                              +-------------------------------+
                              |    PRIMARY PASS (360° LOS)    |
                              |   PlayerVisionTracker.canSee  |
                              +---------------+---------------+
                                              |
                        +---------------------+---------------------+
                        |                                           |
                   [VISIBLE]                                   [OBSTRUCTED]
                        |                                           |
                        v                                           v
      +-----------------------------------+               +--------------------+
      |     SECONDARY PASS (GRANULAR)     |               |   MOMENTUM CHECK   |
      |     SecondaryVisionCheck.canSee   |               | keepMovingIfUnseen |
      +-----------------+-----------------+               +---------+----------+
                        |                                           |
            +-----------+-----------+                     +---------+---------+
            |                       |                     |                   |
        [PASS]                   [BLOCKED]            [MAGNETIZED]      [UNMAGNETIZED]
            |                       |                     |                   |
            v                       v                     v                   v
    +---------------+       +---------------+     +---------------+   +---------------+
    |  PULL ITEM &  |       | REJECT / STOP |     | CONTINUE PULL |   | REJECT PULL   |
    | SET MAGNETIZED|       +---------------+     +---------------+   +---------------+
    +---------------+
```

---

## 🔍 Pass 1: Primary 360° Spherical Line-of-Sight Check

```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **Omnidirectional FOV**: $360.0^\circ$ field of view allows items to be pulled from all angles without turning the camera.
* **0.3m Contact Margin**: Prevents edge-case raycast rejection when items are nestled in corner blocks.

---

## 🌿 Pass 2: Granular Block State Filtering (`SecondaryVisionCheck`)

```java
public static boolean canSee(Player player, Entity target, boolean blockTransparent, boolean blockFlora, boolean blockEntities)
```

1. **Flora & Foliage (`ig:magnet_blocked_by_flora`)**: Checks `BushBlock` (grass, flowers, crops) and `LeavesBlock`.
2. **Interactive Block Entities (`ig:magnet_blocked_by_block_entities`)**: Checks `state.hasBlockEntity()` (Chests, Shulkers, Beds).
3. **Transparent Blocks (`ig:magnet_blocked_by_transparent`)**: Raycasts against `state.getVisualShape(...)` (Glass, Panes, Slabs).

---

## 🚀 Momentum Continuity (`keepMovingIfUnseen`)

* Once seen, `((IMagnetEntity) entity).ig_magnet$setMagnetized()` marks the entity.
* If `ig:magnet_keep_moving_if_unseen = true`, the item continues moving even if it temporarily loses line of sight.

---

## ⚙️ Relevant Configuration & GameRules

| GameRule | Type | Default | Description |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | Requires line-of-sight visibility to attract items. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | Continues pulling magnetized items if LOS is broken mid-flight. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | If true, glass and transparent blocks obstruct line of sight. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | If true, grass, leaves, and flowers obstruct line of sight. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | If true, chests, beds, and block entities obstruct line of sight. |

---

## 🔗 Related Wiki Documentation
* [[Vacuum & Phase Shifting Physics|Vacuum-and-Phase-Shifting]]
* [[GameRules Complete Reference|GameRules]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[Return to Home Portal|Home]]
