# 🧲 Magnet, Let me get that! — 官方技術與玩法 Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

歡迎查閱 **Magnet, Let me get that!**（`ig_magnet`）的官方技術與玩法 Wiki！這是一款專為現代 Fabric 架構 Minecraft 精心打造的原生掉落物與經驗球磁吸模組。

本模組嚴格恪守**即時滿足（Instant Gratification, IG）**設計哲學，旨在徹底消除「恥辱之行」——即在挖掘方塊或擊殺生物後，還必須額外走上 5 格距離去撿起掉落物的枯燥阻力。只要你能看見它，它就應該屬於你。

---

## 🧭 多版本導航門戶

選擇您的目標 Minecraft 版本，以查閱專屬的玩法手冊、技術文檔、GameRules 參考表與底層架構解析：

| Minecraft 版本 | 發布狀態 | 當前構建版本 | 設定介面引擎 | 傳送門連結 |
| :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | 🟢 現代主力版本 | `1.3.9+26.2` | YACL v3 + ModMenu | [[📖 26.2 概覽|zh_tw-26.2-Home]] |
| **Minecraft 26.1.2** | 🟢 穩定錨定版本 | `1.1.2+26.1.2` | Cloth Config + ModMenu | [[📖 26.1.2 概覽|zh_tw-26.1.2-Home]] |

### 🚀 版本直達通道：
* 📦 **Minecraft 26.2**：[[👉 進入 Minecraft 26.2 文檔中心|zh_tw-26.2-Home]]
* 📦 **Minecraft 26.1.2**：[[👉 進入 Minecraft 26.1.2 文檔中心|zh_tw-26.1.2-Home]]

如需深入了解工具鏈相依性、各版本相容性矩陣與歸檔存儲位置，請參閱 [[🗺️ 版本矩陣|zh_tw-Version-Compatibility]]。

---

## ⚡ 核心功能矩陣

```
                      +-----------------------------+
                      |       玩家磁吸發射源        |
                      +--------------+--------------+
                                     |
              +----------------------+----------------------+
              |                                             |
              v                                             v
  +-----------------------+                     +-----------------------+
  |     標準牽引模式      |                     |     瞬間拾取模式      |
  +-----------+-----------+                     +-----------+-----------+
              |                                             |
     [視線遮擋檢測]                                [擴展 AABB 碰撞盒]
     [360° 全向射線追蹤]                           [零延遲直接入包]
     [穿相移動 (NoClip)]                           [原生背包邏輯]
     [平滑動態插值速度]                                     |
              |                                             |
              +----------------------+----------------------+
                                     |
                                     v
                      +-----------------------------+
                      |      成功拾取物品與經驗     |
                      +-----------------------------+
```

* **智慧 360° 全向磁吸**：在可配置的方塊半徑範圍內（預設 12 格，最高可達 64 格）自動吸引掉落物與經驗球。
* **穿相移動（NoClip）**：處於磁吸狀態的物品可無障礙穿透實心方塊，防止掉落物永久卡在採石場縫隙或爆炸殘骸中。
* **視線（LOS）感知系統**：透過 DasikLibrary 的 `PlayerVisionTracker` 進行 360° 球面射線追蹤，並提供針對透明方塊（玻璃）、植被（草叢、樹葉）及方塊實體（箱子）的細粒度過濾開關。
* **動量慣性延續（`keepMovingIfUnseen`）**：在視線內被磁吸的物品，即使中途暫時滑落至障礙物後方，仍能保持前進慣性被吸入背包。
* **瞬間拾取模式**：動態擴展玩家原生拾取碰撞盒，實現零飛行時間、零物理延遲的瞬間進包體驗。
* **熱鍵與指令控制**：客戶端可透過自訂快速鍵（26.2 預設為 `\`，26.1.2 預設為 `Ctrl+M`）即時切換，或在伺服端執行 `/magnet toggle` 指令。
* **零背包負擔**：100% 內在能力機制——無需合成或佩戴任何額外的磁鐵飾品、吊墜或能量電池。

---

## 📚 百科全書式導航目錄

### 🎮 玩家與管理員手冊
* [[MC 26.2 概覽|zh_tw-26.2-Home]] & [[MC 26.1.2 概覽|zh_tw-26.1.2-Home]]
* [[MC 26.2 吸引移動與穿相邏輯|zh_tw-26.2-Vacuum-and-Phase-Shifting]] & [[MC 26.1.2 吸引移動與穿相邏輯|zh_tw-26.1.2-Vacuum-and-Phase-Shifting]]
* [[MC 26.2 視線檢測與障礙機制|zh_tw-26.2-Line-of-Sight-and-Obstruction]] & [[MC 26.1.2 視線檢測與障礙機制|zh_tw-26.1.2-Line-of-Sight-and-Obstruction]]
* [[MC 26.2 經驗球吸附|zh_tw-26.2-Experience-Orb-Attraction]] & [[MC 26.1.2 經驗球吸附|zh_tw-26.1.2-Experience-Orb-Attraction]]
* [[MC 26.2 瞬間拾取模式|zh_tw-26.2-Instant-Pickup-Mode]] & [[MC 26.1.2 瞬間拾取模式|zh_tw-26.1.2-Instant-Pickup-Mode]]
* [[MC 26.2 玩家開關與持久化存儲|zh_tw-26.2-Player-Toggle-and-Persistence]] & [[MC 26.1.2 玩家開關與會話狀態|zh_tw-26.1.2-Player-Toggle-and-Persistence]]
* [[MC 26.2 遊戲規則 GameRules 參考|zh_tw-26.2-GameRules]] & [[MC 26.1.2 遊戲規則 GameRules 參考|zh_tw-26.1.2-GameRules]]
* [[MC 26.2 Brigadier 指令套件|zh_tw-26.2-Commands]] & [[MC 26.1.2 服務端指令|zh_tw-26.1.2-Commands]]
* [[MC 26.2 進度樹範圍|zh_tw-26.2-Advancements]] & [[MC 26.1.2 進度樹範圍|zh_tw-26.1.2-Advancements]]
* [[MC 26.2 YACL 設定介面|zh_tw-26.2-Configuration]] & [[MC 26.1.2 Cloth Config 設定介面|zh_tw-26.1.2-Configuration]]
* [[MC 26.2 HUD 與診斷系統|zh_tw-26.2-HUD-and-Diagnostics]] & [[MC 26.1.2 HUD 與狀態顯示|zh_tw-26.1.2-HUD-and-Diagnostics]]

### 💻 開發者與貢獻者文檔
* [[MC 26.2 開發者設定與建置|zh_tw-26.2-Developer-Setup-and-Building]] & [[MC 26.1.2 開發者設定與建置|zh_tw-26.1.2-Developer-Setup-and-Building]]
* [[MC 26.2 架構設計與 Mixin 注入目標|zh_tw-26.2-Architecture-and-Mixins]] & [[MC 26.1.2 架構設計與 Mixin 注入目標|zh_tw-26.1.2-Architecture-and-Mixins]]
* [[MC 26.2 API 與附屬模組整合|zh_tw-26.2-API-and-Addon-Integration]] & [[MC 26.1.2 API 與附屬模組整合|zh_tw-26.1.2-API-and-Addon-Integration]]
* [[🗺️ 多時代版本相容性矩陣|zh_tw-Version-Compatibility]]

---

## ⚖️ 授權協議與署名

由 **Dasik (Rifaditya)** 基於 **GNU General Public License v3.0 (GPLv3)** 開發維護。請參閱源碼倉庫中的 `LICENSE` 檔案獲取完整的授權條款與法律許可。
