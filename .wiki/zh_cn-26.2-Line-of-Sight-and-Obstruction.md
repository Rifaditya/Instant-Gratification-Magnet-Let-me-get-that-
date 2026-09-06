# 👁️ 视线检测与障碍穿透 (MC 26.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 特性信息栏 | 技术参数 |
| :--- | :--- |
| **一级视线检测引擎** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **二级视线检测引擎** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **球形视场角 (FOV)** | $360.0^\circ$（全方位全向感知） |
| **贴角接触容差距离** | $0.3\text{ m}$ 目标接触判定阈值 |
| **内存记忆吸附标签** | `ig_magnet$isMagnetized()` / `ig_magnet$setMagnetized()` |
| **动量延续规则** | `ig:magnet_keep_moving_if_unseen = true` |
| **细粒度过滤规则** | 透明方块、植被树叶、方块实体 |

---

## 📖 双阶段视线检测架构（Dual-Pass Vision）

为了在保证零卡顿性能的同时，杜绝隔着坚固洞穴岩壁或基地防爆墙非法获取物品的漏洞，**Magnet, Let me get that!** 构建了高效的**双阶段视线管线**：

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

第一阶段直接调用 DasikLibrary 的高性能射线检测引擎：
```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **全向 FOV**：借助 $360.0^\circ$ 的球形全重视场，只要中间没有实体方块阻隔，玩家无需刻意转头，即可轻松吸引头顶、脚底或正后方的物品。
* **0.3m 边缘贴紧容差**：当物品紧贴方块拐角处时，利用 $0.3\text{m}$ 的亚体素容差射线判定，有效杜绝由于精度误差导致的错误拦截。

---

## 🌿 第二阶段：细粒度方块状态过滤 (`SecondaryVisionCheck`)

若第一阶段判定成功，模组会利用 `BlockGetter.traverseBlocks` 进一步评估可选的细粒度方块遮挡逻辑：

```java
public static boolean canSee(Player player, Entity target, boolean blockTransparent, boolean blockFlora, boolean blockEntities)
```

1. **植被与树叶过滤 (`ig:magnet_blocked_by_flora`)**：
   - 检查穿透路线上是否存在 `BushBlock`（高草丛、花朵、农作物、树苗）或 `LeavesBlock`（各类树叶）。
   - 若启用（`true`），茂密植被将视作阻隔吸引的障碍物。
2. **交互式方块实体 (`ig:magnet_blocked_by_block_entities`)**：
   - 检查遍历坐标是否满足 `state.hasBlockEntity()`。
   - 若启用（`true`），箱子、陷阱箱、木桶、潜影盒、床和发射器等方块实体将遮挡视线。
3. **透明及非完整方块 (`ig:magnet_blocked_by_transparent`)**：
   - 针对 `state.getVisualShape(...)` 进行射线求交。
   - 若启用（`true`），普通玻璃、玻璃板、铁栏杆、栅栏、台阶和楼梯均会阻隔吸附。

---

## 🚀 飞行途中动量延续 (`keepMovingIfUnseen`)

在快节奏采矿或激烈战斗中，掉落物被吸向玩家拐弯时往往会暂时脱离视线。为了避免物品停滞甚至掉入岩浆：

1. **吸附标记**：当掉落物在视线内被首次捕获时，`((IMagnetEntity) entity).ig_magnet$setMagnetized()` 会在内存中写入一个布尔标记。
2. **动量保留**：若掉落物在后续游戏刻暂时脱离视线：
   - 若 `ig:magnet_keep_moving_if_unseen = true` 且 `ig_magnet$isMagnetized() == true`：物品将继续保持航向飞向玩家。
   - 若 `ig:magnet_keep_moving_if_unseen = false`：一旦脱离视线立即停止吸附。
   - 若该物品**从未被看见过**（例如由于爆炸或发射器生成在墙壁另一侧）：则直接拒绝吸附。

---

## ⚙️ 相关配置与游戏规则（GameRules）

| 游戏规则 Identifier | 类型 | 默认值 | 说明 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | 仅在掉落物处于视线可见范围内时才执行吸附。 |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | 若已被吸附的物品在飞行途中脱离视线，继续保持牵引。 |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | 若为 true，玻璃等透明方块将阻隔视线。 |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | 若为 true，草丛、花朵与树叶将阻隔视线。 |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | 若为 true，箱子、床等方块实体将阻隔视线。 |

---

## 🔗 相关 Wiki 文档
* [[吸引移动与穿相物理|zh_cn-26.2-Vacuum-and-Phase-Shifting]]
* [[游戏规则完整参考|zh_cn-26.2-GameRules]]
* [[架构设计与 Mixin 注入目标|zh_cn-26.2-Architecture-and-Mixins]]
* [[返回 MC 26.2 门户|zh_cn-26.2-Home]]
