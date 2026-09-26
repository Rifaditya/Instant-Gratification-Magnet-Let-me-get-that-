# 🔄 Player Toggle, Persistence & Lifecycle (MC 26.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Feature Infobox | Technical Parameters |
| :--- | :--- |
| **Interface Bridge** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Mixin Target** | `net.minecraft.world.entity.player.Player` (`PlayerMixin.java`) |
| **Default Client Keybind** | `\` (Backslash) — `key.ig_magnet.toggle` |
| **Key Category** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Network Payload** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **NBT Codec Storage** | `ValueOutput` / `ValueInput` under tag `"ig_magnet_enabled"` |
| **Lifecycle Events** | `ServerPlayConnectionEvents.JOIN`, `ServerPlayerEvents.COPY_FROM`, `ServerPlayerEvents.AFTER_RESPAWN` |

---

## 📖 State Architecture Overview

**Magnet, Let me get that!** implements an **individualized, per-player toggle state** that is 100% persistent across world reloads, deaths, respawns, and dimension teleportation.

```
                                [CLIENT ACTION]
                     Player Presses Toggle Keybind ('\')
                                      |
                                      v
                         [LOCAL STATE UPDATED]
                     client.player -> isEnabled = !isEnabled
                     Actionbar Overlay: "Item Magnet: Enabled/Disabled"
                                      |
                                      v
                         [C2S PACKET TRANSMISSION]
                     ClientPlayNetworking.send(MagnetTogglePayload)
                                      |
                                      v
                               [SERVER RECEIVER]
                     context.server().execute(() -> {
                         ((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(val);
                     })
                                      |
         +----------------------------+----------------------------+
         |                                                         |
         v                                                         v
 [NBT DATA PERSISTED]                                  [LIFECYCLE HOOKS WIRED]
 ValueOutput.putBoolean("ig_magnet_enabled")           - JOIN: S2C Sync
 ValueInput.getBooleanOr("ig_magnet_enabled", true)    - COPY_FROM: Retain on Death
                                                       - AFTER_RESPAWN: Fresh Entity S2C Sync
```

---

## ⌨️ Client Keybinding & Actionbar Overlay

* **Default Key**: `GLFW_KEY_BACKSLASH` (`\`).
* **Dynamic Key Type Helper**: Uses `ig_magnet$getKeyboardType()` to safely resolve `InputConstants.Type.KEYBOARD` with fallback to `KEYSYM`.
* **Actionbar Toast**:
  - `chat.ig_magnet.enabled`: `"Item Magnet: Enabled"`
  - `chat.ig_magnet.disabled`: `"Item Magnet: Disabled"`

---

## 💾 NBT Storage & Codec Serialization

```java
// Saving to Player NBT
@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
    output.putBoolean("ig_magnet_enabled", this.ig_magnet$enabled);
}

// Loading from Player NBT
@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
    this.ig_magnet$enabled = input.getBooleanOr("ig_magnet_enabled", true);
}
```

---

## 🧬 Fabric Lifecycle Events

1. **`ServerPlayerEvents.COPY_FROM`**: Copies boolean state from `oldPlayer` to `newPlayer` upon death/respawn.
2. **`ServerPlayerEvents.AFTER_RESPAWN`**: Automatically transmits S2C packet to update client UI.
3. **`ServerPlayConnectionEvents.JOIN`**: Syncs saved NBT state on server join.

---

## 🔗 Related Wiki Documentation
* [[Brigadier Commands & In-Game Diagnostics|Commands]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[HUD, Visuals & Diagnostics|HUD-and-Diagnostics]]
* [[Return to Home Portal|Home]]
