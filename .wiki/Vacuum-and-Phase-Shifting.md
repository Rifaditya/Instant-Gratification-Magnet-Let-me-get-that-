# 🧲 Vacuum Movement & Phase Shifting (MC 26.1.2)

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

In Minecraft 26.1.2, the vacuum engine continuously tracks valid `ItemEntity` instances within the player's spherical radius and pulls them directly toward eye level using lerp interpolation.

With **Phase Shifting (NoClip)** active, items phase smoothly through solid walls and blocks, preventing drops from becoming trapped behind obstacles during mining or combat.

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

### 1. Directional Unit Vector
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|}$$

### 2. Velocity Interpolation
$$\text{Speed Scalar } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0} = 0.8\text{ blocks/tick}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
$$\text{Acceleration Factor } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0} = 0.10$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 3. Ground Anti-Friction Boost
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 Phase Shifting (NoClip Engine)

1. **State Activation**: `((IMagnetEntity) entity).ig$setMagnetNoClip()` activates a 2-tick countdown.
2. **Physics Override (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` caches `originalNoPhysics` and sets `entity.noPhysics = true`.
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` restores `entity.noPhysics = originalNoPhysics`.
3. **Pushout Prevention**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` blocks vanilla wall ejection forces.
4. **In-Wall Gravity Cancellation**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` cancels gravity *only* when the item is physically inside a block voxel.

---

## ⚙️ Relevant Configuration & GameRules

| GameRule | Type | Default | Description |
| :--- | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | Master toggle for vacuum mechanics. |
| `ig:magnet_range` | Integer | `12` | Vacuum radius in blocks (1 to 64). |
| `ig:magnet_speed` | Integer | `80` | Terminal velocity percentage ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Integer | `10` | Acceleration factor percentage ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Boolean | `true` | Enables block phase shifting during pull. |

---

## 🔗 Related Wiki Documentation
* [[Line of Sight & Obstacles|Line-of-Sight-and-Obstruction]]
* [[Instant Pickup Mode|Instant-Pickup-Mode]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[Return to Home Portal|Home]]
