# 🧲 Magnet, Let me get that! — Minecraft 26.1.2 導航門戶

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

歡迎查閱 **Magnet, Let me get that!**（構建版本 `1.1.2+26.1.2`）的 **Minecraft 26.1.2** 文件入口。

作為現代長期錨定版本，本版提供了完整的即時滿足掉落物與經驗球磁吸體驗，整合了 Cloth Config 圖形化介面、併發會話狀態管理以及 360° 球面射線視線檢測。

---

## 📋 Minecraft 26.1.2 快速規格表

| 規格項目 | 目標設定值 | 參考識別碼 |
| :--- | :--- | :--- |
| **Minecraft 目標版本** | `26.1.2` | `"minecraft": "*"` |
| **當前子專案構建** | `1.1.2+26.1.2` | `mod_version=1.1.2+26.1.2` |
| **Java 工具鏈** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.16.10` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` | `fabric_version=0.145.4+26.1.2` |
| **核心共用函式庫** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **預設客戶端快捷鍵** | `Ctrl+M` | `GLFW.GLFW_KEY_M` + `isControlDown()` |
| **設定介面引擎** | Cloth Config Fabric | `ClothConfigScreenHelper` + ModMenu |
| **伺服端指令** | `/magnet toggle`, `/ig_magnet toggle` | `MagnetCommand.java` |

---

## ⚡ 核心功能亮點

* **360° 球面視線檢測**：由 DasikLibrary 1.8.23 的 `PlayerVisionTracker` 提供強大支援。詳見 [[👁️ 視線檢測與障礙機制|zh_tw-26.1.2-Line-of-Sight-and-Obstruction]]。
* **穿相移動（NoClip）物理機制**：掉落物在被磁吸牽引時可流暢穿透實心方塊與地形。詳見 [[🧲 吸引移動與穿相邏輯|zh_tw-26.1.2-Vacuum-and-Phase-Shifting]]。
* **瞬間拾取模式**：選用的擴展拾取碰撞盒功能，實現 0 物理延遲瞬間進包。詳見 [[⚡ 瞬間拾取模式|zh_tw-26.1.2-Instant-Pickup-Mode]]。
* **併發會話開關**：透過 `MagnetPlayerState` 管理快捷鍵與指令狀態。詳見 [[🔄 玩家開關與會話狀態|zh_tw-26.1.2-Player-Toggle-and-Persistence]]。
* **Cloth Config 設定介面**：清爽乾淨的遊戲內設定畫面，具備分類警告提示。詳見 [[🎨 Cloth Config 設定介面|zh_tw-26.1.2-Configuration]]。

---

## 📑 26.1.2 文件目錄索引

### 🎮 玩法與伺服器管理
* [[🧲 吸引移動與穿相邏輯|zh_tw-26.1.2-Vacuum-and-Phase-Shifting]]
* [[👁️ 視線檢測與障礙機制|zh_tw-26.1.2-Line-of-Sight-and-Obstruction]]
* [[✨ 經驗球吸附|zh_tw-26.1.2-Experience-Orb-Attraction]]
* [[⚡ 瞬間拾取模式|zh_tw-26.1.2-Instant-Pickup-Mode]]
* [[🔄 玩家開關與會話狀態|zh_tw-26.1.2-Player-Toggle-and-Persistence]]
* [[⚙️ 遊戲規則 GameRules 參考|zh_tw-26.1.2-GameRules]]
* [[💻 服務端指令|zh_tw-26.1.2-Commands]]
* [[🏆 進度樹範圍|zh_tw-26.1.2-Advancements]]
* [[🎨 Cloth Config 設定介面|zh_tw-26.1.2-Configuration]]
* [[📊 HUD 與狀態顯示|zh_tw-26.1.2-HUD-and-Diagnostics]]

### 💻 開發者與工程技術參考
* [[🛠️ 開發者設定與建置|zh_tw-26.1.2-Developer-Setup-and-Building]]
* [[🧩 架構設計與 Mixin 注入目標|zh_tw-26.1.2-Architecture-and-Mixins]]
* [[🔌 API 與附屬模組整合|zh_tw-26.1.2-API-and-Addon-Integration]]
* [[🗺️ 返回多時代版本相容性矩陣|zh_tw-Version-Compatibility]]
