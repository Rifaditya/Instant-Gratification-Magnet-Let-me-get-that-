# ⚡ 瞬间拾取模式与包围盒扩展 (MC 26.1.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 特性信息栏 | 技术参数 |
| :--- | :--- |
| **系统注入类** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **启用游戏规则** | `ig:magnet_instant`（默认值：`false`） |
| **判定半径规则** | `ig:magnet_range`（默认值：`12`，范围：`1..64`） |
| **注入挂钩点** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **目标变量** | 玩家拾取碰撞包围盒（`AABB pickupArea`） |

---

## 📖 瞬间拾取模式概览

在 Minecraft 26.1.2 中，**瞬间拾取模式（Instant Pickup Mode）**通过在 `Player.aiStep()` 中扩展玩家的收集包围盒，彻底省去了物品的飞行时间，让掉落物瞬时无缝汇入玩家背包。

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
    Level level = player.level();
    
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    
    return pickupArea;
}
```

---

## ⚙️ 相关配置与游戏规则（GameRules）

| 游戏规则 Identifier | 类型 | 默认值 | 说明 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | 若为 true，物品将瞬间瞬移至背包，无需空中飞行。 |
| `ig:magnet_range` | Integer | `12` | 扩展拾取包围盒的方块半径。 |
| `ig:magnet_enabled` | Boolean | `true` | 全局模组主开关。 |

---

## 🔗 相关 Wiki 文档
* [[吸引移动与穿相物理|zh_cn-26.1.2-Vacuum-and-Phase-Shifting]]
* [[游戏规则完整参考|zh_cn-26.1.2-GameRules]]
* [[返回 MC 26.1.2 门户|zh_cn-26.1.2-Home]]
