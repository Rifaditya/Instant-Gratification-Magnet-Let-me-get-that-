# ✨ 经验球吸附与同步 (MC 26.1.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 特性信息栏 | 技术参数 |
| :--- | :--- |
| **目标实体类型** | `net.minecraft.world.entity.ExperienceOrb` |
| **管理执行类** | `net.instantgratification.magnet.MagnetManager` |
| **启用游戏规则** | `ig:magnet_affects_xp`（默认值：`true`） |
| **实体空间检索** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **粒子效果类型** | `ParticleTypes.ELECTRIC_SPARK`（电火花） |
| **粒子发射源上限** | `ig:magnet_max_particle_sources`（默认值：`5`） |

---

## 📖 经验球吸附机制

在即时满足的设计理念中，遗留经验球严重阻碍了沉浸体验。在 Minecraft 26.1.2 中，`ExperienceOrb` 享有与掉落物完全一致的飞行速度曲线与虚位移穿相特性。

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

1. **虚位移穿相（NoClip）**：经验球能够穿透实体方块，防止它们卡在墙壁后方或天花板角落。
2. **速度插值**：经验球依据配置的速度与加速度百分比平滑加速飞向玩家眼部位置。
3. **视线限制**：当 `ig:magnet_los_only` 为 true 时，经验球必须处于玩家可见范围内或持有有效吸附动量。

---

## 🛡️ 性能优化与粒子对象池机制

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

* **最大粒子发射源**：每个游戏刻仅有前 $N$ 个实体允许生成粒子轨迹。
* **客户端安全回退**：在 `MagnetMovement.java` 中，若当前世界并非 `ServerLevel`，粒子将安全回退为 `level.addParticle(...)`。

---

## ⚙️ 相关配置与游戏规则（GameRules）

| 游戏规则 Identifier | 类型 | 默认值 | 说明 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | 是否吸附经验球。 |
| `ig:magnet_particles` | Boolean | `true` | 粒子效果的总开关。 |
| `ig:magnet_particle_count` | Integer | `1` | 每个发射源在粒子刻生成的火花粒子数。 |
| `ig:magnet_max_particle_sources` | Integer | `5` | 允许同时生成粒子的最大实体源数。 |

---

## 🔗 相关 Wiki 文档
* [[吸引移动与穿相物理|zh_cn-26.1.2-Vacuum-and-Phase-Shifting]]
* [[游戏规则完整参考|zh_cn-26.1.2-GameRules]]
* [[返回 MC 26.1.2 门户|zh_cn-26.1.2-Home]]
