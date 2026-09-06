# 🎨 YACL 設定介面與 ModMenu (MC 26.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 設定資訊面板 | 詳細資訊 |
| :--- | :--- |
| **設定檔路徑** | `config/ig_magnet.json` |
| **GUI 函式庫** | YetAnotherConfigLib v3 (`dev.isxander:yet-another-config-lib`) |
| **ModMenu 入口類別** | `net.instantgratification.magnet.config.ModMenuIntegration` |
| **GUI 畫面輔助類別** | `net.instantgratification.magnet.config.YaclScreenHelper` |
| **類別載入安全隔離** | 透過 `GuiHelper.getOptionalFactory` 隔離 |

---

## 📖 設定系統架構

**Magnet, Let me get that!** 提供了由 **YetAnotherConfigLib v3 (YACL)** 驅動且可透過 **ModMenu** 訪問的選用客戶端設定介面。

為確保專用伺服器在載入客戶端 GUI 類別時絕不發生崩潰，介面工廠透過**反射安全隔離載入機制**構建：

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
                "ig_magnet",
                "net.instantgratification.magnet.config.YaclScreenHelper",
                "createScreen"
        );
    }
}
```

---

## ⚠️ 設定優先順序重要警告

> ⚠️ **重要提示**：  
> 在 ModMenu 介面或 `config/ig_magnet.json` 中進行的修改**僅影響新創建世界的基準預設值**。  
> 若要修改當前已經創建並正在運行的世界設定，請使用遊戲內 [[⚙️ 遊戲規則 GameRules 參考|zh_tw-26.2-GameRules]]（透過 `/gamerule` 或原版遊戲規則編輯畫面）。

---

## 🗂️ 設定分類與可配置項目

```
YACL 設定畫面 ("Magnet, Let me get that! Configuration")
  ├── 常規設定 (General Settings)
  │     ├── 磁吸啟用 (Magnet Enabled, 預設: true)
  │     ├── 磁吸範圍 (Magnet Range, 預設: 12, 範圍: 1..64)
  │     ├── 瞬間拾取 (Instant Pickup, 預設: false)
  │     └── 穿相移動 (Magnet Noclip, 預設: true)
  ├── 速度與牽引參數 (Speeds & Pull Heuristics)
  │     ├── 物品速度 (Item Speed, 預設: 80%, 範圍: 1..1000)
  │     └── 物品加速度 (Item Acceleration, 預設: 10%, 範圍: 1..1000)
  ├── 視線檢測 (Line of Sight)
  │     ├── 僅限視線可及 (Line of Sight Only, 預設: true)
  │     ├── 失去視線保持牽引 (Keep Moving if Unseen, 預設: true)
  │     ├── 透明方塊阻擋 (Blocked by Transparent, 預設: false)
  │     ├── 植被草叢阻擋 (Blocked by Flora, 預設: false)
  │     └── 方塊實體阻擋 (Blocked by Block Entities, 預設: false)
  └── 視覺效果與效能 (Visuals & Performance)
        ├── 吸引經驗球 (Attract XP Orbs, 預設: true)
        ├── 磁吸粒子特效 (Magnet Particles, 預設: true)
        ├── 粒子發射數量 (Particle Count, 預設: 1, 範圍: 0..100)
        └── 最大粒子源數 (Max Particle Sources, 預設: 5, 範圍: 0..100)
```

---

## 📄 原始 JSON 結構範例 (`config/ig_magnet.json`)

```json
{
  "configVersion": 1,
  "enabled": true,
  "range": 12,
  "noClip": true,
  "affectsXp": true,
  "particles": true,
  "particleCount": 1,
  "maxParticleSources": 5,
  "speed": 80,
  "acceleration": 10,
  "instant": false,
  "losOnly": true,
  "keepMovingIfUnseen": true,
  "blockedByTransparent": false,
  "blockedByFlora": false,
  "blockedByBlockEntities": false
}
```

---

## 🔗 相關 Wiki 文件
* [[⚙️ 遊戲規則 GameRules 參考|zh_tw-26.2-GameRules]]
* [[🛠️ 開發者設定與建置|zh_tw-26.2-Developer-Setup-and-Building]]
* [[📖 返回 MC 26.2 文檔中心|zh_tw-26.2-Home]]
