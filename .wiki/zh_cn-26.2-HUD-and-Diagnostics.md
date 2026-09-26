# 📊 快捷栏 HUD、视觉效果与专属诊断日志 (MC 26.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 视觉信息栏 | 技术参数 |
| :--- | :--- |
| **快捷栏 API** | `client.gui.hud.setOverlayMessage(Component, boolean)` |
| **粒子效果类型** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **粒子节流取模** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **诊断日志文件** | `logs/ig_magnet_debug.log` |
| **日志记录器类** | `net.instantgratification.magnet.MagnetDebugLogger` |

---

## 🖥️ 快捷栏上方悬浮文本反馈

当玩家通过按键（`\`）或服务端指令（`/magnet toggle`）切换磁铁状态时，客户端 HUD 会在快捷栏正上方渲染简洁、无干扰的操作栏悬浮提示：

* **开启提示**：`§a物品磁铁: 已开启` (`chat.ig_magnet.enabled`)
* **关闭提示**：`§c物品磁铁: 已禁用` (`chat.ig_magnet.disabled`)

```java
// 验证于: Hud.java (26.2+)
if (newState) {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ 电火花飞行轨迹粒子

在掉落物与经验球被吸向玩家的过程中，它们会散发出细致优雅的 `ParticleTypes.ELECTRIC_SPARK` 电火花飞行拖尾：

```
[被吸附物品]  --->  ✨  --->  ✨  --->  ✨  --->  [玩家眼部高度]
```

### 粒子节流控制规则：
1. **发射源上限**：由 `ig:magnet_max_particle_sources`（默认值：`5`）严格限制，确保大型矿坑挖掘时掉落的数百件物品不会造成粒子爆炸式掉帧。
2. **频次交错**：每个独立实体仅在每 4 个游戏刻中的 1 刻生成粒子，并根据实体 ID 错开生成节奏：`(entity.tickCount + entity.getId()) % 4 == 0`。
3. **数量配置**：每个发射源由 `ig:magnet_particle_count`（默认值：`1`）精准控制。

---

## 📝 专属文件诊断日志器 (`MagnetDebugLogger`)

为了便于服务器管理员和整合包作者深入排查视线阻挡或网络数据包问题，模组内置了一个线程安全的异步文件日志系统，专门记录于 `logs/ig_magnet_debug.log`：

* **开启方式**：在游戏内执行 `/magnet debug log`。
* **日志格式**：`[yyyy-MM-dd HH:mm:ss.SSS] [Context] Message`
* **日志样例**：
  ```text
  [2026-08-22 21:50:00.124] MagnetMod: Server received packet from Alex (e7b1a234-...) enabled=true
  [2026-08-22 21:50:00.130] MagnetMovement: Entity 592 is now magnetized by Alex (e7b1a234-...).
  [2026-08-22 21:50:01.450] PlayerMixin: Server tick for Alex (e7b1a234-...) isMagnetEnabled=true
  ```

---

## 🔗 相关 Wiki 文档
* [[Brigadier 命令与游戏内诊断|zh_cn-26.2-Commands]]
* [[经验球吸附与同步|zh_cn-26.2-Experience-Orb-Attraction]]
* [[返回 MC 26.2 门户|zh_cn-26.2-Home]]
