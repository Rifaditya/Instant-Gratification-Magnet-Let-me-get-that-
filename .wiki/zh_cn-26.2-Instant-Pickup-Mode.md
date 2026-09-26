# ⚡ 瞬间拾取模式与 AABB 包围盒 (MC 26.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 特性信息栏 | 技术参数 |
| :--- | :--- |
| **系统注入类** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **启用游戏规则** | `ig:magnet_instant`（默认值：`false`） |
| **判定半径规则** | `ig:magnet_range`（默认值：`12`，范围：`1..64`） |
| **注入挂钩点** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **目标变量** | 玩家拾取碰撞包围盒（`AABB pickupArea`） |
| **物品栏处理逻辑** | 100% 委托原版 `Player.touch(ItemEntity)` 流水线 |

---

## 📖 瞬间拾取模式概览

标准真空吸附模式通过速度插值在空中物理牵引掉落物，而**瞬间拾取模式（Instant Pickup Mode）**则彻底消除了飞行等待时间。一旦启用，周围有效范围内的掉落物将在生成后的一瞬间瞬时收入玩家背包。

**Magnet, Let me get that!** 并没有编写容易崩溃、漏洞百出的自定义物品栏塞入循环，而是选择在原版 `Player.aiStep()` 方法内部非破坏性地直接扩展玩家原生的物品碰撞拾取盒（AABB）。

```
+-----------------------------------------------------------------------------------+
|                            原版玩家 aiStep() TICK 流程                            |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
                +---------------------------------------------------+
                |   PlayerMixin.ig_magnet$expandPickupArea()        |
                |   pickupArea = pickupArea.inflate(range);         |
                +---------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|               原版 ItemEntity.playerTouch(Player) 核心逻辑执行                    |
|   - 原生背包堆叠与部分拾取合并                                                   |
|   - 原版拾取动画与音效广播 (item.pickup / entity.experience_orb)                  |
|   - 原版统计数据 (Statistics) 与成就进度 (Advancements) 触发                      |
|   - 背包已满时自然保留剩余物品与防溢出保护                                        |
+-----------------------------------------------------------------------------------+
```

---

## 🧩 架构实现细节 (`PlayerMixin.java`)

```java
@ModifyVariable(
        method = "aiStep",
        at = @At(value = "STORE"),
        ordinal = 0
)
private AABB ig_magnet$expandPickupArea(AABB pickupArea) {
    Player player = (Player) (Object) this;
    if (!this.ig_magnet$isMagnetEnabled() || player.isDeadOrDying() || player.isSpectator()) {
        return pickupArea;
    }

    Level level = player.level();
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_ENABLED) && ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    return pickupArea;
}
```

### 关键工程保证：
1. **严格的安全守卫**：若玩家处于死亡/濒死状态（`player.isDeadOrDying()`）、旁观模式（`player.isSpectator()`）或手动关闭了个人的磁铁开关（`!isMagnetEnabled()`），包围盒绝不会被扩展。
2. **服务端权威性**：拾取逻辑严格在逻辑服务端执行（`!level.isClientSide()`），从源头上消除了物品幽灵残留（Ghost Items）与物品栏不同步问题。
3. **互斥物理保护**：当 `ig:magnet_instant` 为 true 时，`MagnetMovement.pull()` 会自动终止速度插值，防止物理牵引与瞬间拾取产生争抢冲突。

---

## ⚙️ 相关配置与游戏规则（GameRules）

| 游戏规则 Identifier | 类型 | 默认值 | 说明 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | 启用后物品将瞬间瞬移收入背包，无需空中飞行。 |
| `ig:magnet_range` | Integer | `12` | 拾取包围盒（AABB）向外膨胀的方块半径。 |
| `ig:magnet_enabled` | Boolean | `true` | 全局模组主开关。 |

---

## 🔗 相关 Wiki 文档
* [[吸引移动与穿相物理|zh_cn-26.2-Vacuum-and-Phase-Shifting]]
* [[玩家切换与数据持久化|zh_cn-26.2-Player-Toggle-and-Persistence]]
* [[架构设计与 Mixin 注入目标|zh_cn-26.2-Architecture-and-Mixins]]
* [[返回 MC 26.2 门户|zh_cn-26.2-Home]]
