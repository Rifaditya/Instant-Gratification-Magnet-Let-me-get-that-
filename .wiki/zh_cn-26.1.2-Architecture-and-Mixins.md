# 🧩 架构设计与 Mixin 注入目标 (MC 26.1.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 架构信息栏 | 技术参数 |
| :--- | :--- |
| **根包路径** | `net.instantgratification.magnet` |
| **Mixin 配置文件** | `src/main/resources/magnet.mixins.json` |
| **Access Widener** | `src/main/resources/magnet.accesswidener` |
| **兼容性级别** | `JAVA_25` |
| **Mixin 类总数** | `2` (`MixinEntity`, `PlayerMixin`) |

---

## 🌳 源码包结构树

```text
net.instantgratification.magnet/
├── IMagnetEntity.java                  # 实体接口，用于 NoClip 与吸附状态标记
├── MagnetCommand.java                  # 服务端命令注册 (/magnet toggle)
├── MagnetManager.java                  # 实体扫描与空间运算执行主循环
├── MagnetMod.java                      # 模组初始化器与数据包接收端
├── MagnetModClient.java                # 客户端初始化器、Ctrl+M 按键绑定与悬浮提示
├── MagnetMovement.java                 # 轨迹向量计算、Lerp 速度插值与粒子发射
├── MagnetPlayerState.java              # 线程安全 ConcurrentHashMap 玩家开关存储
├── MagnetTogglePayload.java            # 网络数据包 Record 与 StreamCodec 复合体
├── SecondaryVisionCheck.java           # 细粒度方块射线检测（植被、方块实体、玻璃）
├── config/
│   ├── ClothConfigScreenHelper.java    # Cloth Config Fabric GUI 构建器
│   ├── MagnetConfig.java               # JSON 配置存储结构与 POJO 字段映射
│   └── ModMenuIntegration.java         # 反射安全的 ModMenu API 入口点
├── mixin/
│   ├── MixinEntity.java                # 向 Entity 注入 NoClip 状态与条件重力消除
│   └── PlayerMixin.java                # 向 Player 注入 Tick 运行与瞬间拾取逻辑
└── registry/
    └── ModGameRules.java               # DynamicGameRuleManager 游戏规则注册表
```

---

## 📋 Mixin 注入目标详细清单

### 1. `net.instantgratification.magnet.mixin.MixinEntity`
目标类为 `net.minecraft.world.entity.Entity` 并实现 `IMagnetEntity` 接口。

| 注入方法名 | 注入挂钩切入点 | 行为动作与功能逻辑 |
| :--- | :--- | :--- |
| `ig_magnet$decrementNoClipTicks` | `@Inject(method = "tick", at = @At("HEAD"))` | 激活时每个游戏刻将 `noClipTicks` 倒计时减 1。 |
| `ig_magnet$preventPushOut` | `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` | 服务端当 `noClipTicks > 0` 时拦截原版方块推挤挤出速度。 |
| `ig_magnet$forceNoClipOnMove` | `@Inject(method = "move", at = @At("HEAD"))` | 缓存 `originalNoPhysics`，在 `noClipTicks > 0` 时强制 `entity.noPhysics = true`。 |
| `ig_magnet$restoreNoPhysicsAfterMove` | `@Inject(method = "move", at = @At("RETURN"))` | 移动执行完成后恢复 `entity.noPhysics = originalNoPhysics`。 |
| `ig_magnet$conditionalCancelGravity` | `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` | **仅**当物品实体正处于方块体素内部时拦截重力下坠加速度。 |

---

### 2. `net.instantgratification.magnet.mixin.PlayerMixin`
目标类为 `net.minecraft.world.entity.player.Player`。

| 注入方法名 | 注入挂钩切入点 | 行为动作与功能逻辑 |
| :--- | :--- | :--- |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | 每个游戏刻在服务端调用 `MagnetManager.tick(player)` 执行吸附。 |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | 当 `ig:magnet_instant` 为 true 时扩展拾取包围盒（`pickupArea.inflate(range)`）。 |

---

## 🔗 相关 Wiki 文档
* [[吸引移动与穿相物理|zh_cn-26.1.2-Vacuum-and-Phase-Shifting]]
* [[开发者环境配置与构建|zh_cn-26.1.2-Developer-Setup-and-Building]]
* [[返回 MC 26.1.2 门户|zh_cn-26.1.2-Home]]
