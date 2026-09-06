# 🧲 物品磁铁 (Magnet, Let me get that!) — Minecraft 26.2 门户

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

欢迎访问 **Magnet, Let me get that!** 的 **Minecraft 26.2** 文档门户（构建版本：`1.3.9+26.2`）。

这一现代版本带来了持久化 NBT 数据存储、Fabric 生命周期重生同步、基于 DasikLibrary 1.8.23 的 360° 球形射线检测，以及 YetAnotherConfigLib v3 (YACL) GUI 图形化配置界面。

---

## 📋 Minecraft 26.2 快速规格表

| 规格项目 | 目标设定值 | 参考标识符 |
| :--- | :--- | :--- |
| **Minecraft 目标版本** | `26.2` | `"minecraft": ">=26.2-"` |
| **当前子项目构建** | `1.3.9+26.2` | `mod_version=1.3.9+26.2` |
| **Java 工具链** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.19.1` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.150.1+26.2` | `fabric_version=0.150.1+26.2` |
| **底层核心通用库** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **客户端默认快捷键** | `\`（反斜杠） | `GLFW.GLFW_KEY_BACKSLASH` |
| **配置界面 GUI** | YetAnotherConfigLib v3 | `YaclScreenHelper` + ModMenu |
| **诊断指令** | `/magnet toggle`, `/magnet debug`, `/magnet debug log` | `MagnetCommand.java` |

---

## ⚡ 核心特性亮点

* **360° 全向球形视线检测**：在防止隔墙作弊吸物的同时，为玩家提供全方位 360° 视线无死角的吸附体验。参见[[视线检测与障碍机制|zh_cn-26.2-Line-of-Sight-and-Obstruction]]。
* **虚位移穿相（NoClip）物理**：被吸附的物品可平滑穿透障碍方块飞向玩家视线高度，绝不卡滞。参见[[吸引移动与穿相逻辑|zh_cn-26.2-Vacuum-and-Phase-Shifting]]。
* **零延迟瞬间拾取**：可选的碰撞包围盒扩展模式，物品生成微秒内瞬时吸入物品栏，零物理飞行耗时。参见[[瞬间拾取模式|zh_cn-26.2-Instant-Pickup-Mode]]。
* **持久化玩家状态**：开关状态通过 NBT（`ValueOutput`/`ValueInput`）与 `ServerPlayerEvents.COPY_FROM` 完美保留，跨越死亡、重生、维度传送及服务器重启。参见[[玩家开关与持久化存储|zh_cn-26.2-Player-Toggle-and-Persistence]]。
* **诊断指令套件**：为服务器管理员量身定制的内置 `/magnet debug` 与 `/magnet debug log` 实时排错工具。参见[[Brigadier 指令套件|zh_cn-26.2-Commands]] 与 [[HUD 与诊断系统|zh_cn-26.2-HUD-and-Diagnostics]]。

---

## 📑 26.2 文档完整索引

### 🎮 玩法机制与管理配置
* [[真空吸附与虚位移物理|zh_cn-26.2-Vacuum-and-Phase-Shifting]]
* [[视线检测与障碍机制|zh_cn-26.2-Line-of-Sight-and-Obstruction]]
* [[经验球吸附与同步|zh_cn-26.2-Experience-Orb-Attraction]]
* [[瞬间拾取模式与 AABB 包围盒|zh_cn-26.2-Instant-Pickup-Mode]]
* [[玩家切换、持久化与生命周期|zh_cn-26.2-Player-Toggle-and-Persistence]]
* [[游戏规则参考与默认边界|zh_cn-26.2-GameRules]]
* [[Brigadier 命令与游戏内诊断|zh_cn-26.2-Commands]]
* [[进度与原版机制依赖说明|zh_cn-26.2-Advancements]]
* [[YACL 配置界面与 ModMenu|zh_cn-26.2-Configuration]]
* [[快捷栏 HUD 与专属诊断日志|zh_cn-26.2-HUD-and-Diagnostics]]

### 💻 开发者与工程架构参考
* [[开发者环境配置、工具链与 Gradle Loom|zh_cn-26.2-Developer-Setup-and-Building]]
* [[架构、包结构与 Mixin 注入|zh_cn-26.2-Architecture-and-Mixins]]
* [[API 门面、接口与附属集成|zh_cn-26.2-API-and-Addon-Integration]]
* [[返回跨时代版本兼容性矩阵|zh_cn-Version-Compatibility]]
