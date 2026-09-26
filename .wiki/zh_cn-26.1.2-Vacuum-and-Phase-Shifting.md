# 🧲 吸引移动与穿相逻辑 (MC 26.1.2)

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

在 Minecraft 26.1.2 中，吸附引擎持续追踪玩家球形半径内的有效掉落物实体（`ItemEntity`），并通过 Lerp 线性插值将其直接牵引至玩家眼部高度。

当**虚位移穿相（NoClip）**激活时，物品能够平滑穿透地形实体方块，杜绝采矿或战斗中掉落物卡在障碍物后无法拾取的情况。

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

### 1. 方向单位向量
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|}$$

### 2. 速度插值计算
$$\text{速度标量 } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0} = 0.8\text{ 方块/tick}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
$$\text{加速度因子 } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0} = 0.10$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 3. 地面抗摩擦抬升
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 虚位移穿相引擎（NoClip Engine）

1. **状态激活**：`((IMagnetEntity) entity).ig$setMagnetNoClip()` 开启一个 2 刻的倒计时。
2. **物理覆写 (`MixinEntity.java`)**：
   - `move(MoverType, Vec3)`：`@Inject(at = @At("HEAD"))` 缓存 `originalNoPhysics` 并设置 `entity.noPhysics = true`。
   - `move(MoverType, Vec3)`：`@Inject(at = @At("RETURN"))` 恢复 `entity.noPhysics = originalNoPhysics`。
3. **防止方块挤出**：`@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` 拦截原版的方块推离逻辑。
4. **方块内重力消除**：`@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` **仅**当实体处于方块体素内部时取消重力。

---

## ⚙️ 相关配置与游戏规则（GameRules）

| 游戏规则 Identifier | 类型 | 默认值 | 说明 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | 控制真空吸附机制的主开关。 |
| `ig:magnet_range` | Integer | `12` | 吸引半径（1 至 64 格）。 |
| `ig:magnet_speed` | Integer | `80` | 终端速度百分比（$80 = 0.8\text{ 方块/tick}$）。 |
| `ig:magnet_acceleration` | Integer | `10` | 加速度百分比（$10 = 10\%\text{ lerp/tick}$）。 |
| `ig:magnet_noclip` | Boolean | `true` | 是否允许物品穿透方块。 |

---

## 🔗 相关 Wiki 文档
* [[视线检测与障碍穿透|zh_cn-26.1.2-Line-of-Sight-and-Obstruction]]
* [[瞬间拾取模式|zh_cn-26.1.2-Instant-Pickup-Mode]]
* [[架构设计与 Mixin 实现|zh_cn-26.1.2-Architecture-and-Mixins]]
* [[返回 MC 26.1.2 门户|zh_cn-26.1.2-Home]]
