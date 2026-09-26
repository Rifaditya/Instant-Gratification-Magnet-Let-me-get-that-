# 🔄 玩家切换与状态管理 (MC 26.1.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 特性信息栏 | 技术参数 |
| :--- | :--- |
| **状态存储管理类** | `net.instantgratification.magnet.MagnetPlayerState` |
| **默认客户端按键** | `Ctrl+M` (`GLFW.GLFW_KEY_M` + `isControlDown()`) |
| **按键分类组** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **网络数据包 Payload** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **快捷栏 API** | `client.gui.setOverlayMessage(Component, boolean)` |

---

## 📖 状态架构概览

在 Minecraft 26.1.2 中，玩家的开关偏好通过 `MagnetPlayerState` 中的线程安全型 `ConcurrentHashMap` 在活跃服务端会话期间进行管理：

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

## ⌨️ 客户端按键绑定 (`Ctrl+M`)

* **默认组合键**：`Ctrl+M`（`GLFW_KEY_M` + `isControlDown()`），并在 macOS 上跨平台兼容 Command 键（`GLFW_KEY_LEFT_SUPER`）。
* **操作栏悬浮反馈**：
  - `chat.ig_magnet.enabled`: `"物品磁铁: 已开启"`
  - `chat.ig_magnet.disabled`: `"物品磁铁: 已禁用"`

---

## 📡 网络同步协议

```
[客户端]                                                           [服务端]
玩家按下 Ctrl+M
MagnetPlayerState.setMagnetEnabled(player, newState)
ClientPlayNetworking.send(new MagnetTogglePayload(newState))
                                               ------------------> 服务端接收端
                                                                   MagnetPlayerState.setMagnetEnabled(player, payload.enabled())
```

---

## 🔗 相关 Wiki 文档
* [[服务端指令与原版客户端支持|zh_cn-26.1.2-Commands]]
* [[架构设计与 Mixin 注入目标|zh_cn-26.1.2-Architecture-and-Mixins]]
* [[返回 MC 26.1.2 门户|zh_cn-26.1.2-Home]]
