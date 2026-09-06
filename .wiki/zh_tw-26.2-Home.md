# 🧲 Magnet, Let me get that! — Minecraft 26.2 導航門戶

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

歡迎查閱 **Magnet, Let me get that!**（構建版本 `1.3.9+26.2`）的 **Minecraft 26.2** 文件入口。

此現代主力版本引入了持久化 NBT 狀態保留、Fabric 生命週期重生同步、基於 DasikLibrary 1.8.23 的 360° 球面射線追蹤以及 YetAnotherConfigLib v3 (YACL) 圖形化設定介面。

---

## 📋 Minecraft 26.2 快速規格表

| 規格項目 | 目標設定值 | 參考識別碼 |
| :--- | :--- | :--- |
| **Minecraft 目標版本** | `26.2` | `"minecraft": ">=26.2-"` |
| **當前子專案構建** | `1.3.9+26.2` | `mod_version=1.3.9+26.2` |
| **Java 工具鏈** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.19.1` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.150.1+26.2` | `fabric_version=0.150.1+26.2` |
| **核心共用函式庫** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **預設客戶端快捷鍵** | `\` (反斜線) | `GLFW.GLFW_KEY_BACKSLASH` |
| **設定介面引擎** | YetAnotherConfigLib v3 | `YaclScreenHelper` + ModMenu |
| **診斷指令套件** | `/magnet toggle`, `/magnet debug`, `/magnet debug log` | `MagnetCommand.java` |

---

## ⚡ 核心功能亮點

* **360° 球面視線檢測**：防止隔牆作弊吸取物品，同時為玩家提供全視角、全方位的視野吸引能力。詳見 [[👁️ 視線檢測與障礙機制|zh_tw-26.2-Line-of-Sight-and-Obstruction]]。
* **穿相移動（NoClip）物理機制**：被磁吸的物品可流暢穿透實心方塊，直達玩家視線高度而不被卡滯。詳見 [[🧲 吸引移動與穿相邏輯|zh_tw-26.2-Vacuum-and-Phase-Shifting]]。
* **零延遲瞬間拾取**：選用的擴展拾取碰撞盒功能，可在物品出現的瞬間直接吸入背包，消除所有飛行延遲。詳見 [[⚡ 瞬間拾取模式|zh_tw-26.2-Instant-Pickup-Mode]]。
* **持久化玩家狀態**：透過 NBT（`ValueOutput`/`ValueInput`）與 `ServerPlayerEvents.COPY_FROM`，玩家的個人開關狀態在死亡、重生、跨維度傳送及伺服器重啟後依然完美保留。詳見 [[🔄 玩家開關與持久化存儲|zh_tw-26.2-Player-Toggle-and-Persistence]]。
* **診斷指令套件**：專為伺服器管理員打造的內建 `/magnet debug` 與 `/magnet debug log` 診斷工具。詳見 [[💻 Brigadier 指令套件|zh_tw-26.2-Commands]] 與 [[📊 HUD 與診斷系統|zh_tw-26.2-HUD-and-Diagnostics]]。

---

## 📑 26.2 文件目錄索引

### 🎮 玩法與伺服器管理
* [[🧲 吸引移動與穿相邏輯|zh_tw-26.2-Vacuum-and-Phase-Shifting]]
* [[👁️ 視線檢測與障礙機制|zh_tw-26.2-Line-of-Sight-and-Obstruction]]
* [[✨ 經驗球吸附|zh_tw-26.2-Experience-Orb-Attraction]]
* [[⚡ 瞬間拾取模式|zh_tw-26.2-Instant-Pickup-Mode]]
* [[🔄 玩家開關與持久化存儲|zh_tw-26.2-Player-Toggle-and-Persistence]]
* [[⚙️ 遊戲規則 GameRules 參考|zh_tw-26.2-GameRules]]
* [[💻 Brigadier 指令套件|zh_tw-26.2-Commands]]
* [[🏆 進度樹範圍|zh_tw-26.2-Advancements]]
* [[🎨 YACL 設定介面|zh_tw-26.2-Configuration]]
* [[📊 HUD 與診斷系統|zh_tw-26.2-HUD-and-Diagnostics]]

### 💻 開發者與工程技術參考
* [[🛠️ 開發者設定與建置|zh_tw-26.2-Developer-Setup-and-Building]]
* [[🧩 架構設計與 Mixin 注入目標|zh_tw-26.2-Architecture-and-Mixins]]
* [[🔌 API 與附屬模組整合|zh_tw-26.2-API-and-Addon-Integration]]
* [[🗺️ 返回多時代版本相容性矩陣|zh_tw-Version-Compatibility]]
