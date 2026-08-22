# 🔄 Player Toggle & State Management (MC 26.1.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Feature Infobox | Technical Parameters |
| :--- | :--- |
| **State Storage Class** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Default Client Keybind** | `Ctrl+M` (`GLFW.GLFW_KEY_M` + `isControlDown()`) |
| **Key Category** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Network Payload** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **Actionbar API** | `client.gui.setOverlayMessage(Component, boolean)` |

---

## 📖 State Architecture Overview

In Minecraft 26.1.2, player toggle preferences are managed during the active server session via `MagnetPlayerState` using a thread-safe `ConcurrentHashMap`:

```java
public class MagnetPlayerState {
    private static final Map<UUID, Boolean> playerStates = new ConcurrentHashMap<>();

    public static boolean isMagnetEnabled(Player player) {
        return playerStates.getOrDefault(player.getUUID(), true);
    }

    public static void setMagnetEnabled(Player player, boolean enabled) {
        playerStates.put(player.getUUID(), enabled);
    }

    public static boolean toggleMagnet(Player player) {
        boolean newState = !isMagnetEnabled(player);
        setMagnetEnabled(player, newState);
        return newState;
    }
}
```

---

## ⌨️ Client Keybinding (`Ctrl+M`)

* **Default Combination**: `Ctrl+M` (`GLFW_KEY_M` + `isControlDown()`), with cross-platform support for Command key on macOS (`GLFW_KEY_LEFT_SUPER`).
* **Visual Actionbar Feedback**:
  - `chat.ig_magnet.enabled`: `"Item Magnet: Enabled"`
  - `chat.ig_magnet.disabled`: `"Item Magnet: Disabled"`

---

## 📡 Network Synchronization Protocol

```
[CLIENT]                                                           [SERVER]
Player presses Ctrl+M
MagnetPlayerState.setMagnetEnabled(player, newState)
ClientPlayNetworking.send(new MagnetTogglePayload(newState))
                                               ------------------> Server Receiver
                                                                   MagnetPlayerState.setMagnetEnabled(player, payload.enabled())
```

---

## 🔗 Related Wiki Documentation
* [[Server Commands & Toggles|Commands]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[Return to Home Portal|Home]]
