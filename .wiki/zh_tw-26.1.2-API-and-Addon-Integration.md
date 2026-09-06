# 🔌 API 與附屬模組整合 (MC 26.1.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| API 資訊面板 | 技術參數 |
| :--- | :--- |
| **玩家狀態管理器** | `net.instantgratification.magnet.MagnetPlayerState` |
| **實體磁吸介面** | `net.instantgratification.magnet.IMagnetEntity` |
| **核心移動門面** | `net.instantgratification.magnet.MagnetMovement` |
| **遊戲規則 API** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **視覺檢測 API** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 模組間開發者整合導引

第三方模組與即時滿足（Instant Gratification）同系列附屬模組可以直接與 Minecraft 26.1.2 版本的 **Magnet, Let me get that!** 進行對接。

---

## 🧑‍💻 玩家狀態管理 (`MagnetPlayerState`)

直接透過靜態輔助方法查詢或修改玩家磁吸偏好：

```java
package net.instantgratification.magnet;

public class MagnetPlayerState {
    public static boolean isMagnetEnabled(Player player);
    public static void setMagnetEnabled(Player player, boolean enabled);
    public static boolean toggleMagnet(Player player);
}
```

### 呼叫範例：
```java
// 檢查玩家是否開啟了磁吸功能
if (MagnetPlayerState.isMagnetEnabled(player)) {
    // 自訂附屬邏輯...
}

// 透過程式碼強制關閉磁吸
MagnetPlayerState.setMagnetEnabled(player, false);
```

---

## 📦 實體磁吸介面 (`IMagnetEntity`)

可將任何 `Entity` 實體強轉為 `IMagnetEntity`，以手動調整其穿相移動或磁吸狀態標記：

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig$setMagnetNoClip();
    boolean ig$isMagnetNoClip();
    void ig$setMagnetized();
    boolean ig$isMagnetized();
}
```

---

## 🚀 靜態移動外觀 (`MagnetMovement.pull`)

透過程式碼觸發將掉落物品或經驗球牽引至目標玩家：

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

---

## 🔗 相關 Wiki 文件
* [[🧩 架構設計與 Mixin 注入目標|zh_tw-26.1.2-Architecture-and-Mixins]]
* [[🛠️ 開發者設定與建置|zh_tw-26.1.2-Developer-Setup-and-Building]]
* [[📖 返回 MC 26.1.2 文檔中心|zh_tw-26.1.2-Home]]
