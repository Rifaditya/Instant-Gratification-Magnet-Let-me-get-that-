# 💻 Brigadier Commands & In-Game Diagnostics (MC 26.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Feature Infobox | Technical Parameters |
| :--- | :--- |
| **Command Class** | `net.instantgratification.magnet.MagnetCommand` |
| **Primary Literals** | `/magnet` and `/ig_magnet` (Identical alias mirrors) |
| **Registration Callback** | `CommandRegistrationCallback.EVENT` |
| **Log Output File** | `logs/ig_magnet_debug.log` |
| **Target Permissions** | Available to all players (`toggle`) and OP Level 2 (`debug`) |

---

## 📖 Command Tree Structure

```
/magnet (or /ig_magnet)
  ├── toggle
  └── debug
        └── log
```

---

## 🛠️ Subcommand Reference

### 1. `/magnet toggle` (or `/ig_magnet toggle`)
Toggles the executing player's personal magnet state on or off.

* **Usage**: `/magnet toggle`
* **Execution**: Calls `((IMagnetPlayer) player).ig_magnet$toggleMagnet()`.
* **Output Feedback**:
  - If enabled: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
  - If disabled: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

---

### 2. `/magnet debug` (or `/ig_magnet debug`)
Executes an immediate in-world diagnostic sweep of the executing player and surrounding entities within 10 blocks.

* **Usage**: `/magnet debug`
* **Output Information**:
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

---

### 3. `/magnet debug log` (or `/ig_magnet debug log`)
Toggles persistent verbose diagnostic logging to disk (`logs/ig_magnet_debug.log`).

* **Usage**: `/magnet debug log`
* **Execution**: Flips `MagnetDebugLogger.enabled = !MagnetDebugLogger.enabled`.

---

## 🔗 Related Wiki Documentation
* [[Player Toggle & Persistence|Player-Toggle-and-Persistence]]
* [[HUD, Visuals & Diagnostics|HUD-and-Diagnostics]]
* [[GameRules Complete Reference|GameRules]]
* [[Return to Home Portal|Home]]
