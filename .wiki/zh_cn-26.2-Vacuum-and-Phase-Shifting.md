# 🧲 吸引移动与穿相逻辑 (MC 26.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 特性信息栏 | 技术参数 |
| :--- | :--- |
| **系统处理类** | `net.instantgratification.magnet.MagnetMovement` |
| **触发事件** | 服务端玩家 Tick（`PlayerMixin` $\rightarrow$ `MagnetManager.tick`） |
| **默认吸引半径** | `12` 格（`ig:magnet_range`） |
| **默认终端速度** | `80%` ($0.8\text{ 方块/tick} = 16.0\text{ m/s}$) |
| **默认加速度** | `10%` ($0.10\text{ lerp 插值因子/tick}$) |
| **虚位移穿相 (NoClip)** | 默认启用（`ig:magnet_noclip = true`） |
| **目标位置向量** | 玩家眼部坐标（`player.getEyePosition()`） |
| **地面位移抬升** | 实体处于地面时（`entity.onGround()`）在 Y 轴抬升 $+0.05\text{ m}$ |

---

## 📖 系统运行概览

**Magnet, Let me get that!** 的核心真空吸附机制会在每个服务端游戏刻扫描玩家所配置半径范围内的掉落物实体（`ItemEntity`），并通过平滑的非线性插值将其牵引至玩家的眼部高度。

为了防止物品卡在圆石台阶边缘、树冠树叶或矿脉岩缝之间，模组引入了**虚位移穿相（Phase Shifting / NoClip）**机制，使飞行中的掉落物能够无阻碍地穿透实体方块体素。

```
+-------------+      视线检测通过 (LOS OK)     +----------------------+      应用 Lerp 速度向量      +------------------+
| 掉落物实体  | -----------------------------> | 激活 NoClip (2 Ticks)| ---------------------------> |   玩家眼部坐标   |
+-------------+                                +----------------------+                              +------------------+
                                                          |
                                                          v
                                               [取消方块挤出碰撞]
                                               [绕过方块碰撞箱]
                                               [取消方块内重力]
```

---

## 🧮 物理与向量数学计算

在吸引物品时，运动轨迹完全在 3D 欧几里得空间中进行直接计算：

### 1. 指向目标的向量（Vector to Target）
设 $\vec{p}_{\text{entity}} = (x_e, y_e, z_e)$ 为物品当前坐标，$\vec{p}_{\text{eye}} = (x_p, y_p + h_{\text{eye}}, z_p)$ 为玩家眼部坐标。
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|} = \frac{\vec{v}_{\text{target}}}{\sqrt{\Delta x^2 + \Delta y^2 + \Delta z^2}}$$

### 2. 期望终端速度（Desired Terminal Velocity）
目标速度向量由单位方向 $\hat{d}$ 乘以配置的速度标量计算得出：
$$\text{速度标量 } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
*在默认设置（$80\%$）下，$s = 0.8\text{ 方块/tick}$。在 $20\text{ ticks/s}$ 的基准下，其终端飞行速度达到 $16.0\text{ m/s}$。*

### 3. 非线性加速度插值（Lerp）
速度向量依据配置的加速度因子 $a$ 通过线性插值（`Vec3.lerp`）逐刻平滑更新：
$$\text{加速度因子 } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0}$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 4. 地面抗摩擦抬升机制（Ground Anti-Friction Boost）
如果物品静止在方块表面上（`entity.onGround() == true`），将瞬时解除地面摩擦判定，避免物品在地面拖行卡顿：
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 虚位移穿相引擎（NoClip Engine）

当 `ig:magnet_noclip` 启用时，被吸附的物品将被标记一个持续 2 个游戏刻的 NoClip 时间窗口：

1. **状态激活**：`((IMagnetEntity) entity).ig_magnet$setMagnetNoClip()` 将 `noClipTicks` 设为 `2`。
2. **移动劫持 (`MixinEntity.java`)**：
   - `move(MoverType, Vec3)`：`@Inject(at = @At("HEAD"))` 缓存原有的 `originalNoPhysics` 并强制将 `entity.noPhysics` 设为 `true`。
   - `move(MoverType, Vec3)`：`@Inject(at = @At("RETURN"))` 恢复 `entity.noPhysics = originalNoPhysics`。
3. **方块推挤取消**：`@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` 阻止原版防卡方块的推挤算法剧烈将掉落物弹出方块。
4. **条件式重力消除**：`@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` **仅**当物品物理上正处于实体方块内部时（`!level.noCollision(...)`）取消重力下坠，保留其在开阔空气中的自然弧线飞行姿态。

---

## ⚙️ 相关配置与游戏规则（GameRules）

| 游戏规则 Identifier | 类型 | 默认值 | 取值范围 | 说明 |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | 控制整个吸附系统的全局主开关。 |
| `ig:magnet_range` | Integer | `12` | `1..64` 方块 | 最大球形吸引半径。 |
| `ig:magnet_speed` | Integer | `80` | `1..1000%` | 终端飞行速度百分比（$80 = 0.8\text{ 方块/tick}$）。 |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000%` | 吸引加速度百分比（$10 = 10\%\text{ lerp/tick}$）。 |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | 是否允许物品在被吸引时穿透实体方块。 |

---

## 🔗 相关 Wiki 文档
* [[视线检测与障碍机制|zh_cn-26.2-Line-of-Sight-and-Obstruction]]
* [[瞬间拾取模式|zh_cn-26.2-Instant-Pickup-Mode]]
* [[架构设计与 Mixin 实现|zh_cn-26.2-Architecture-and-Mixins]]
* [[返回 MC 26.2 门户|zh_cn-26.2-Home]]
