# 👁️ Line of Sight & Obstruction Mechanics (MC 26.1.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Feature Infobox | Technical Parameters |
| :--- | :--- |
| **Primary Vision Engine** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **Secondary Vision Engine** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **Spherical Field of View** | $360.0^\circ$ (Full omnidirectional perception) |
| **Contact Distance Tolerance** | $0.3\text{ m}$ target contact threshold |
| **Memory Retention Tag** | `ig$isMagnetized()` / `ig$setMagnetized()` |
| **Momentum Rule** | `ig:magnet_keep_moving_if_unseen = true` |
| **Context Implementation** | `VisionContext` static record |

---

## 📖 Dual-Pass Vision Pipeline

In Minecraft 26.1.2, line of sight is evaluated through an allocation-free **Dual-Pass Vision Pipeline**:

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

* **Omnidirectional Field**: With a $360^\circ$ FOV angle, dropped items above, below, or behind the player are pulled smoothly without forcing camera turning.
* **Sub-Voxel Contact Tolerance**: The $0.3\text{m}$ margin prevents false rejections when items rest flush against solid walls.

---

## 🌿 Pass 2: Granular Block Traversal (`SecondaryVisionCheck`)

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

* **Flora (`ig:magnet_blocked_by_flora`)**: Checks for `BushBlock` and `LeavesBlock`.
* **Block Entities (`ig:magnet_blocked_by_block_entities`)**: Checks `state.hasBlockEntity()` (Chests, Shulker Boxes, Beds).
* **Transparent Blocks (`ig:magnet_blocked_by_transparent`)**: Tests `state.getVisualShape().clip(...)` against Glass, Panes, and Slabs.

---

## 🚀 Momentum Continuity (`keepMovingIfUnseen`)

* When first seen, `((IMagnetEntity) entity).ig$setMagnetized()` flags the entity.
* If `ig:magnet_keep_moving_if_unseen = true`, the item continues being pulled around obstacles as long as it was previously magnetized.

---

## ⚙️ Relevant Configuration & GameRules

| GameRule | Type | Default | Description |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | Requires line of sight to attract items. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | Continues pulling magnetized items if LOS is broken mid-flight. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | If true, glass and transparent blocks block line of sight. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | If true, grass and flowers block line of sight. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | If true, chests and block entities block line of sight. |

---

## 🔗 Related Wiki Documentation
* [[Vacuum & Phase Shifting Physics|Vacuum-and-Phase-Shifting]]
* [[GameRules Complete Reference|GameRules]]
* [[Return to Home Portal|Home]]
