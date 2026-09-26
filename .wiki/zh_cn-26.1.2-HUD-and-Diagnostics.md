# 📊 快捷栏 HUD、视觉效果与状态悬浮 (MC 26.1.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 视觉信息栏 | 技术参数 |
| :--- | :--- |
| **快捷栏 API** | `client.gui.setOverlayMessage(Component, boolean)` |
| **粒子效果类型** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **粒子节流取模** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **粒子发射源上限** | `ig:magnet_max_particle_sources`（默认值：`5`） |

---

## 🖥️ 快捷栏上方悬浮文本反馈

当玩家使用 `Ctrl+M` 热键切换磁铁状态时，客户端 GUI 会即时在快捷栏上方显示操作栏通知：

* **开启状态**：`§a物品磁铁: 已开启` (`chat.ig_magnet.enabled`)
* **关闭状态**：`§c物品磁铁: 已禁用` (`chat.ig_magnet.disabled`)

```java
// 验证于: Gui.java (26.1.2)
if (newState) {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ 电火花飞行轨迹粒子

飞行中的掉落物与经验球会产生电火花粒子尾迹：

```
[被吸引物品 / 经验球]  --->  ✨  --->  ✨  --->  ✨  --->  [玩家眼部高度]
```

* **源限制**：最多同时允许 5 个实体生成粒子（`ig:magnet_max_particle_sources = 5`）。
* **刻限制**：粒子每 4 个游戏刻生成一次（$5\text{ 次/秒}$）。
* **密度设定**：`ig:magnet_particle_count = 1`。

---

## 🔗 相关 Wiki 文档
* [[玩家切换与状态管理|zh_cn-26.1.2-Player-Toggle-and-Persistence]]
* [[经验球吸附与同步|zh_cn-26.1.2-Experience-Orb-Attraction]]
* [[返回 MC 26.1.2 门户|zh_cn-26.1.2-Home]]
