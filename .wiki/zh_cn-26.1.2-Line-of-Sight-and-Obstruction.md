# 👁️ 视线检测与障碍机制 (MC 26.1.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 特性信息栏 | 技术参数 |
| :--- | :--- |
| **一级视线检测引擎** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **二级视线检测引擎** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **球形视场角 (FOV)** | $360.0^\circ$（全方位全向感知） |
| **贴角接触容差距离** | $0.3\text{ m}$ 目标接触判定阈值 |
| **内存记忆吸附标签** | `ig$isMagnetized()` / `ig$setMagnetized()` |
| **动量延续规则** | `ig:magnet_keep_moving_if_unseen = true` |
| **上下文结构** | `VisionContext` 静态 Record |

---

## 📖 双阶段视线检测流水线

在 Minecraft 26.1.2 中，视线检测通过零堆内存分配的**双阶段视线流水线**进行快速裁决：

```
                                +---------------------------+
                                |      检测到目标掉落物     |
                                +-------------+-------------+
                                              |
                                              v
                              +-------------------------------+
                              |    第一阶段 (360° 球形视线)   |
                              |   PlayerVisionTracker.canSee  |
                              +---------------+---------------+
                                              |
                        +---------------------+---------------------+
                        |                                           |
                    [可见]                                       [受阻挡]
                        |                                           |
                        v                                           v
      +-----------------------------------+               +--------------------+
      |       第二阶段 (细粒度遮挡)       |               |      动量延续检查  |
      |     SecondaryVisionCheck.canSee   |               | keepMovingIfUnseen |
      +-----------------+-----------------+               +---------+----------+
                        |                                           |
            +-----------+-----------+                     +---------+---------+
            |                       |                     |                   |
         [通过]                  [拦截]                [已吸附]           [未吸附]
            |                       |                     |                   |
            v                       v                     v                   v
    +---------------+       +---------------+     +---------------+   +---------------+
    |   执行吸引并  |       | 拒绝吸引 / 停滞 |    | 保持吸引动量  |   |    拒绝吸引   |
    |  标记为已吸附 |       +---------------+     +---------------+   +---------------+
    +---------------+
```

---

## 🔍 第一阶段：原生 360° 球形视线光线追踪

```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **全向视线区域**：拥有 $360^\circ$ 的球形视场，掉落物无论在玩家上方、下方或身后，都能顺畅吸引，无需频繁调整视角。
* **亚体素接触裕度**：$0.3\text{m}$ 的容差范围避免了掉落物紧贴墙壁平置时的误判拦截。

---

## 🌿 第二阶段：细粒度方块穿越检测 (`SecondaryVisionCheck`)

```java
private record VisionContext(
        Level level,
        boolean blockTransparent,
        boolean blockFlora,
        boolean blockEntities,
        CollisionContext collisionContext,
        Vec3 start,
        Vec3 end
) {}
```

* **植被树叶 (`ig:magnet_blocked_by_flora`)**：检测路线上的 `BushBlock` 与 `LeavesBlock`。
* **方块实体 (`ig:magnet_blocked_by_block_entities`)**：通过 `state.hasBlockEntity()` 检查箱子、潜影盒与床。
* **透明方块 (`ig:magnet_blocked_by_transparent`)**：针对玻璃、玻璃板与台阶执行 `state.getVisualShape().clip(...)` 碰撞裁剪。

---

## 🚀 飞行途中动量延续 (`keepMovingIfUnseen`)

* 首次被看见时，`((IMagnetEntity) entity).ig$setMagnetized()` 为实体打上内存标记。
* 若 `ig:magnet_keep_moving_if_unseen = true`，只要此前已被吸附，即便短暂绕入死角依然会继续飞向玩家。

---

## ⚙️ 相关配置与游戏规则（GameRules）

| 游戏规则 Identifier | 类型 | 默认值 | 说明 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | 仅吸引视线内的掉落物。 |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | 在飞行途中脱离视线时保持吸附。 |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | 若为 true，玻璃等透明方块将阻隔视线。 |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | 若为 true，草丛与花朵将阻隔视线。 |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | 若为 true，箱子等方块实体将阻隔视线。 |

---

## 🔗 相关 Wiki 文档
* [[吸引移动与穿相物理|zh_cn-26.1.2-Vacuum-and-Phase-Shifting]]
* [[游戏规则完整参考|zh_cn-26.1.2-GameRules]]
* [[返回 MC 26.1.2 门户|zh_cn-26.1.2-Home]]
