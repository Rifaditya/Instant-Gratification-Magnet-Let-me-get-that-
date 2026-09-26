# 🔌 API 與附屬模組整合 (MC 26.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| API 資訊面板 | 技術參數 |
| :--- | :--- |
| **玩家狀態介面** | `net.instantgratification.magnet.IMagnetPlayer` |
| **實體磁吸介面** | `net.instantgratification.magnet.IMagnetEntity` |
| **核心移動門面** | `net.instantgratification.magnet.MagnetMovement` |
| **遊戲規則 API** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **視覺檢測 API** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 模組間開發者整合導引

第三方模組、伺服器實用工具與即時滿足（Instant Gratification）附屬模組可以直接與 **Magnet, Let me get that!** 進行對接，以查詢玩家磁吸狀態、透過程式碼觸發牽引，或繞過障礙物限制。

---

## 🧑‍💻 玩家狀態介面 (`IMagnetPlayer`)

可將任何 `Player` 或 `ServerPlayer` 實體強轉為 `IMagnetPlayer` 以查詢或修改磁吸偏好：

```java
package net.instantgratification.magnet;

public interface IMagnetPlayer {
    boolean ig_magnet$isMagnetEnabled();
    void ig_magnet$setMagnetEnabled(boolean enabled);
    boolean ig_magnet$toggleMagnet();
}
```

### 呼叫範例：
```java
// 檢查玩家當前是否已啟用磁吸
if (((IMagnetPlayer) player).ig_magnet$isMagnetEnabled()) {
    // 附屬模組自訂邏輯...
}

// 透過程式碼強制關閉磁吸（例如坐在王座上或處於特定小遊戲區域）
((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(false);
```

---

## 📦 實體磁吸介面 (`IMagnetEntity`)

可將任何 `Entity` 實體（例如自訂怪物掉落物、投擲物或經驗球）強轉為 `IMagnetEntity`：

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig_magnet$setMagnetNoClip();
    boolean ig_magnet$isMagnetNoClip();
    void ig_magnet$setMagnetized();
    boolean ig_magnet$isMagnetized();
}
```

### 呼叫範例：
```java
// 為特定自訂實體授予 2-tick 的暫時穿相能力
((IMagnetEntity) customItem).ig_magnet$setMagnetNoClip();
```

---

## 🚀 靜態移動外觀 (`MagnetMovement.pull`)

附屬模組可以手動調用內建的物理與視線引擎，將實體牽引至任何玩家身邊：

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

```java
// 牽引目標實體至玩家身邊並產生電火花軌跡粒子
MagnetMovement.pull(specialDrop, player, true);
```

---

## 🔗 相關 Wiki 文件
* [[🧩 架構設計與 Mixin 注入目標|zh_tw-26.2-Architecture-and-Mixins]]
* [[🛠️ 開發者設定與建置|zh_tw-26.2-Developer-Setup-and-Building]]
* [[📖 返回 MC 26.2 文檔中心|zh_tw-26.2-Home]]
