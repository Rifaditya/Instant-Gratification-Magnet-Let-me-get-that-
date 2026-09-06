# ✨ 经验球吸附与同步 (MC 26.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 特性信息栏 | 技术参数 |
| :--- | :--- |
| **目标实体类型** | `net.minecraft.world.entity.ExperienceOrb` |
| **管理执行类** | `net.instantgratification.magnet.MagnetManager` |
| **启用游戏规则** | `ig:magnet_affects_xp`（默认值：`true`） |
| **实体空间检索** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **粒子效果类型** | `ParticleTypes.ELECTRIC_SPARK`（电火花） |
| **粒子全局配额** | 通过 `ig:magnet_max_particle_sources` 与物品共享节流配额 |

---

## 📖 经验球吸附机制

依据即时满足（Instant Gratification）的设计理念，让经验球散落在地上或粘在洞穴天花板上会严重破坏流畅的游戏节奏。**Magnet, Let me get that!** 为经验球（`ExperienceOrb`）提供了与普通掉落物同等的一流真空吸附支持。

```
+------------------+     getEntitiesOfClass     +-----------------------+     MagnetMovement.pull     +--------------------+
| 玩家空间检测包围盒| -------------------------> | List<ExperienceOrb>   | --------------------------> |   玩家吸收收集     |
+------------------+                            +-----------------------+                             +--------------------+
                                                            |
                                                            v
                                                [应用 NoClip 虚位移穿相]
                                                [应用 Lerp 速度插值向量]
                                                [受限节流电火花粒子]
```

---

## ⚡ 物理机制与虚位移穿相同步

当 `ig:magnet_affects_xp` 启用时，吸引范围内的所有经验球均完全继承与物品相同的高级动力学：

1. **虚位移穿相（NoClip）**：经验球在被吸引时同样可以自如穿透实体方块，防止它们在墙壁上卡滞、来回弹跳或绕圈打转。
2. **动态加速度插值**：经验球严格遵循相同的速度（$s = \text{speed}/100.0$）与加速度（$a = \text{accel}/100.0$）Lerp 曲线。
3. **视线检测校验**：若 `ig:magnet_los_only` 为 true，经验球同样必须通过第一阶段 360° 射线检测与第二阶段细粒度遮挡检查。

---

## 🛡️ 性能调优与防掉帧保护（粒子上限）

在刷怪塔或击败末影龙的场景中，场上往往会瞬间爆出数百颗经验球。如果每个游戏刻都为每颗经验球生成粒子，会引发剧烈的客户端渲染帧率骤降。

模组通过**全局粒子发射源节流控制**杜绝了粒子过载：

```java
int maxParticleSources = ModGameRules.getInt(player.level(), ModGameRules.MAGNET_MAX_PARTICLE_SOURCES);
int particleSourceCount = 0;

for (ExperienceOrb orb : orbs) {
    boolean shouldSpawnParticles = false;
    if (particleSourceCount < maxParticleSources) {
        shouldSpawnParticles = true;
        particleSourceCount++;
    }
    MagnetMovement.pull(orb, player, shouldSpawnParticles);
}
```

* **全局发射源上限**：每个游戏刻仅允许前 $N$ 个活跃实体（由 `ig:magnet_max_particle_sources` 设定，默认：`5`）发射火花粒子。
* **交错时钟触发**：粒子仅在满足 `(entity.tickCount + entity.getId()) % 4 == 0` 的节拍时生成（每 4 刻生成一次，即每秒 5 次），多实体之间基于实体 ID 错开生成时钟。

---

## ⚙️ 相关配置与游戏规则（GameRules）

| 游戏规则 Identifier | 类型 | 默认值 | 取值范围 | 说明 |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | 是否同时吸附经验球。 |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | 电火花飞行轨迹粒子的总开关。 |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | 每次触发粒子时每个发射源生成的粒子数量。 |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | 允许同时生成粒子的最大实体源数量，防止掉帧。 |

---

## 🔗 相关 Wiki 文档
* [[吸引移动与穿相物理|zh_cn-26.2-Vacuum-and-Phase-Shifting]]
* [[游戏规则完整参考|zh_cn-26.2-GameRules]]
* [[HUD 与视觉诊断|zh_cn-26.2-HUD-and-Diagnostics]]
* [[返回 MC 26.2 门户|zh_cn-26.2-Home]]
