# 🔄 玩家開關與會話狀態 (MC 26.1.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 功能資訊面板 | 技術參數 |
| :--- | :--- |
| **狀態儲存類別** | `net.instantgratification.magnet.MagnetPlayerState` |
| **預設客戶端快捷鍵** | `Ctrl+M` (`GLFW.GLFW_KEY_M` + `isControlDown()`) |
| **按鍵分類** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **網路資料包** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **快捷列提示 API** | `client.gui.setOverlayMessage(Component, boolean)` |

---

## 📖 狀態架構概覽

在 Minecraft 26.1.2 中，玩家的開關偏好在活躍伺服器會話期間透過 `MagnetPlayerState` 內的執行緒安全 `ConcurrentHashMap` 進行管理：

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

## ⌨️ 客戶端快捷鍵 (`Ctrl+M`)

* **預設組合鍵**：`Ctrl+M`（`GLFW_KEY_M` + `isControlDown()`），在 macOS 上跨平台支援 Command 鍵（`GLFW_KEY_LEFT_SUPER`）。
* **視覺快捷列即時回饋**：
  - `chat.ig_magnet.enabled`：`"物品磁吸: 已開啟"`
  - `chat.ig_magnet.disabled`：`"物品磁吸: 已關閉"`

---

## 📡 網路同步協定

```
[客戶端]                                                           [伺服端]
玩家按下 Ctrl+M
MagnetPlayerState.setMagnetEnabled(player, newState)
ClientPlayNetworking.send(new MagnetTogglePayload(newState))
                                               ------------------> 伺服端接收監聽器
                                                                   MagnetPlayerState.setMagnetEnabled(player, payload.enabled())
```

---

## 🔗 相關 Wiki 文件
* [[💻 服務端指令|zh_tw-26.1.2-Commands]]
* [[🧩 架構設計與 Mixin 注入目標|zh_tw-26.1.2-Architecture-and-Mixins]]
* [[📖 返回 MC 26.1.2 文檔中心|zh_tw-26.1.2-Home]]
