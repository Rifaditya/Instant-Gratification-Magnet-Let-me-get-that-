# 💻 Brigadier 指令套件與遊戲內診斷 (MC 26.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 功能資訊面板 | 技術參數 |
| :--- | :--- |
| **指令核心類別** | `net.instantgratification.magnet.MagnetCommand` |
| **主識別字** | `/magnet` 與 `/ig_magnet`（完全對稱的別名） |
| **註冊回調事件** | `CommandRegistrationCallback.EVENT` |
| **日誌輸出檔案** | `logs/ig_magnet_debug.log` |
| **權限等級** | 全體玩家皆可使用 (`toggle`)，管理員權限等級 2 (`debug`) |

---

## 📖 指令樹結構

```
/magnet (或 /ig_magnet)
  ├── toggle
  └── debug
        └── log
```

---

## 🛠️ 子指令完整手冊

### 1. `/magnet toggle` (或 `/ig_magnet toggle`)
切換當前執行玩家的個人磁吸狀態（開啟或關閉）。

* **語法**：`/magnet toggle`
* **底層執行**：呼叫 `((IMagnetPlayer) player).ig_magnet$toggleMagnet()`。
* **回饋訊息**：
  - 開啟時：`§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
  - 關閉時：`§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)
* **適用情境**：便於從原版客戶端連線至純伺服端模組伺服器的玩家，或未配置/衝突快速鍵的玩家進行開關切換。

---

### 2. `/magnet debug` (or `/ig_magnet debug`)
對執行玩家自身及周圍 10 格範圍內的實體執行即時遊戲內診斷掃描。

* **語法**：`/magnet debug`
* **診斷輸出範例**：
  ```text
  §e--- Magnet Debug Info ---
  Player Name: Steve
  Player UUID: c0b59055-ec50-4653-979d-c3c92d6ca897
  Global Master Toggle: true
  Player Toggle State: true
  Range: 12
  Instant Pickup: false
  LOS Only: true
  Spectator: false
  Dead/Dying: false
  Debug File Logger: DISABLED (Toggle with /magnet debug log)
  Nearby Items (10 blocks): 3
  Nearest Item ID: 412
  PlayerVisionTracker.canSee: true
  Vanilla Raycast canSee: true
  §e-------------------------
  ```
* **適用情境**：即時排查某個物品為何無法被吸引（例如：視線被隱蔽障礙物遮擋、玩家處於死亡狀態，或全域 GameRule 覆寫關閉）。

---

### 3. `/magnet debug log` (or `/ig_magnet debug log`)
切換是否將詳細的除錯追蹤日誌持續寫入硬碟檔案。

* **語法**：`/magnet debug log`
* **底層執行**：反轉布林值 `MagnetDebugLogger.enabled = !MagnetDebugLogger.enabled`。
* **輸出位置**：在 `logs/ig_magnet_debug.log` 中寫入帶有精確時間戳記的 Tick 事件、網路包接收記錄、重生握手與視線阻擋診斷。
* **日誌格式範例**：
  ```log
  [2026-08-22 21:49:15.102] MagnetMod: Server received packet from Steve (c0b59055-...) enabled=true
  [2026-08-22 21:49:15.105] MagnetMovement: Entity 412 is now magnetized by Steve (c0b59055-...).
  ```

---

## 🔗 相關 Wiki 文件
* [[🔄 玩家開關與持久化存儲|zh_tw-26.2-Player-Toggle-and-Persistence]]
* [[📊 HUD 與診斷系統|zh_tw-26.2-HUD-and-Diagnostics]]
* [[⚙️ 遊戲規則 GameRules 參考|zh_tw-26.2-GameRules]]
* [[📖 返回 MC 26.2 文檔中心|zh_tw-26.2-Home]]
