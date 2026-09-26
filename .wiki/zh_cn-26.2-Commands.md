# 💻 Brigadier 命令与游戏内诊断 (MC 26.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 特性信息栏 | 技术参数 |
| :--- | :--- |
| **命令注册类** | `net.instantgratification.magnet.MagnetCommand` |
| **主命令字面量** | `/magnet` 与 `/ig_magnet`（完全等价的别名镜像） |
| **注册回调** | `CommandRegistrationCallback.EVENT` |
| **日志输出文件** | `logs/ig_magnet_debug.log` |
| **权限层级** | 所有玩家可用（`toggle`），OP 权限等级 2 可用（`debug`） |

---

## 📖 命令树结构

```
/magnet (或 /ig_magnet)
  ├── toggle
  └── debug
        └── log
```

---

## 🛠️ 子命令参考

### 1. `/magnet toggle` (或 `/ig_magnet toggle`)
切换当前执行指令玩家的个人磁铁吸附开关。

* **用法**：`/magnet toggle`
* **执行逻辑**：调用 `((IMagnetPlayer) player).ig_magnet$toggleMagnet()`。
* **反馈信息**：
  - 开启状态：`§a物品磁铁: 已开启`（`chat.ig_magnet.enabled`）
  - 禁用状态：`§c物品磁铁: 已禁用`（`chat.ig_magnet.disabled`）
* **适用场景**：允许仅安装了服务端模组的原版客户端玩家，或没有按键绑定快捷键的玩家自由切换磁铁状态。

---

### 2. `/magnet debug` (或 `/ig_magnet debug`)
对执行玩家及其周围 10 格内的实体即时执行空间诊断扫描。

* **用法**：`/magnet debug`
* **输出信息示例**：
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
* **适用场景**：精确排查为什么某件掉落物未被吸附（如受严格视线阻挡、处于旁观模式、或受到全局 GameRule 覆盖）。

---

### 3. `/magnet debug log` (或 `/ig_magnet debug log`)
切换持久化详细诊断日志写入磁盘的开关。

* **用法**：`/magnet debug log`
* **执行逻辑**：切换布尔值 `MagnetDebugLogger.enabled = !MagnetDebugLogger.enabled`。
* **输出目标**：向 `logs/ig_magnet_debug.log` 写入带有时间戳的每刻事件、数据包接收、重生握手与视线拦截记录。
* **日志格式示例**：
  ```log
  [2026-08-22 21:49:15.102] MagnetMod: Server received packet from Steve (c0b59055-...) enabled=true
  [2026-08-22 21:49:15.105] MagnetMovement: Entity 412 is now magnetized by Steve (c0b59055-...).
  ```

---

## 🔗 相关 Wiki 文档
* [[玩家切换、持久化与生命周期|zh_cn-26.2-Player-Toggle-and-Persistence]]
* [[HUD 与诊断系统|zh_cn-26.2-HUD-and-Diagnostics]]
* [[游戏规则完整参考|zh_cn-26.2-GameRules]]
* [[返回 MC 26.2 门户|zh_cn-26.2-Home]]
