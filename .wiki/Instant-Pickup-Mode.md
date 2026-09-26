# ⚡ Instant Pickup Mode (MC 26.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Feature Infobox | Technical Parameters |
| :--- | :--- |
| **System Class** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **Enabling GameRule** | `ig:magnet_instant` (Default: `false`) |
| **Radius GameRule** | `ig:magnet_range` (Default: `12`, Range: `1..64`) |
| **Injection Point** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **Target Variable** | Player Collection Bounding Box (`AABB pickupArea`) |

---

## 📖 Instant Pickup Overview

**Instant Pickup Mode** eliminates item travel time by expanding the player's native collection bounding box in `Player.aiStep()`, instantly vacuuming items into inventory with 0 flight delay.

```
+-----------------------------------------------------------------------------------+
|                            VANILLA PLAYER aiStep() TICK                           |
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
|               VANILLA ItemEntity.playerTouch(Player) EXECUTION                    |
|   - Native Inventory Stacking & Partial Pickups                                   |
|   - Vanilla Pickup Animation & Sound Events                                       |
|   - Native Statistics & Advancements Triggers                                     |
|   - Full Container Overflow & Remaining Item Retention                            |
+-----------------------------------------------------------------------------------+
```

---

## 🧩 Architectural Implementation (`PlayerMixin.java`)

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

---

## ⚙️ Relevant Configuration & GameRules

| GameRule | Type | Default | Description |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | Enables instant teleportation into inventory. |
| `ig:magnet_range` | Integer | `12` | Radius in blocks for the expanded pickup area. |
| `ig:magnet_enabled` | Boolean | `true` | Master toggle for the entire mod. |

---

## 🔗 Related Wiki Documentation
* [[Vacuum & Phase Shifting Physics|Vacuum-and-Phase-Shifting]]
* [[Player Toggle & Persistence|Player-Toggle-and-Persistence]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[Return to Home Portal|Home]]
