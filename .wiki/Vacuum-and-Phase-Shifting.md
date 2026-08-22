# 🧲 Vacuum Movement & Phase Shifting (MC 26.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Feature Infobox | Technical Parameters |
| :--- | :--- |
| **System Class** | `net.instantgratification.magnet.MagnetMovement` |
| **Trigger Event** | Server Player Tick (`PlayerMixin` $\rightarrow$ `MagnetManager.tick`) |
| **Default Pull Range** | `12` blocks (`ig:magnet_range`) |
| **Default Terminal Speed** | `80%` ($0.8\text{ blocks/tick} = 16.0\text{ m/s}$) |
| **Default Acceleration** | `10%` ($0.10\text{ lerp factor/tick}$) |
| **Phase Shifting (NoClip)** | Enabled (`ig:magnet_noclip = true`) |
| **Target Vector** | Player Eye Position (`player.getEyePosition()`) |
| **Ground Offset Boost** | $+0.05\text{ m}$ on Y-axis when `entity.onGround()` |

---

## 📖 System Overview

The core vacuum mechanic in **Magnet, Let me get that!** scans for dropped `ItemEntity` instances within the player's configured radius each server tick and pulls them toward the player's eye level using smooth non-linear interpolation.

To prevent items from snagging on cobblestone lip edges, tree canopies, or ore vein crevices, the mod activates **Phase Shifting (NoClip)**, allowing items in flight to pass harmlessly through solid block voxels.

```
+-------------+      Line-of-Sight OK      +----------------------+      Lerp Velocity Applied      +------------------+
| Item Entity | -------------------------> | Set NoClip (2 Ticks) | ------------------------------> | Player Eye Pos   |
+-------------+                            +----------------------+                                 +------------------+
                                                      |
                                                      v
                                           [Cancel Wall Pushout]
                                           [Bypass Block Collide]
                                           [Cancel In-Wall Grav]
```

---

## 🧮 Physics & Vector Mathematics

When pulling an item, the trajectory is computed directly in 3D Euclidean space:

### 1. Vector to Target
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|}$$

### 2. Desired Terminal Velocity
$$\text{Speed Scalar } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0} = 0.8\text{ blocks/tick}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
*At default setting ($80\%$), terminal velocity is $16.0\text{ m/s}$.*

### 3. Non-Linear Acceleration (Lerp)
$$\text{Acceleration Factor } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0} = 0.10$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 4. Ground Anti-Friction Boost
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 Phase Shifting (NoClip Engine)

When `ig:magnet_noclip` is enabled, items are tagged with a 2-tick NoClip window:

1. **State Activation**: `((IMagnetEntity) entity).ig_magnet$setMagnetNoClip()` sets `noClipTicks = 2`.
2. **Move Hijacking (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` saves `originalNoPhysics` and forces `entity.noPhysics = true`.
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` restores `entity.noPhysics = originalNoPhysics`.
3. **Pushout Cancellation**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` blocks vanilla block pushout forces.
4. **Conditional Gravity Cancellation**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` cancels downward gravity *only* when the item is physically intersecting a block voxel (`!level.noCollision(...)`).

---

## ⚙️ Relevant Configuration & GameRules

| GameRule | Type | Default | Description |
| :--- | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | Master toggle for all vacuum logic. |
| `ig:magnet_range` | Integer | `12` | Maximum spherical vacuum radius. |
| `ig:magnet_speed` | Integer | `80` | Terminal velocity percentage ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Integer | `10` | Pull acceleration percentage ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Boolean | `true` | Enables block phase shifting during pull. |

---

## 🔗 Related Wiki Documentation
* [[Line of Sight & Obstacles|Line-of-Sight-and-Obstruction]]
* [[Instant Pickup Mode|Instant-Pickup-Mode]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[Return to Home Portal|Home]]
