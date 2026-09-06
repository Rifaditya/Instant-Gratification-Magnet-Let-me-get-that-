# 🧲 物品磁铁 (Magnet, Let me get that!) — Minecraft 26.1.2 门户

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

欢迎访问 **Magnet, Let me get that!** 的 **Minecraft 26.1.2** 文档门户（构建版本：`1.1.2+26.1.2`）。

作为坚实稳定的基石版本，该版本提供了完整的即时满足（Instant Gratification）物品与经验球吸附体验，具备 Cloth Config 图形配置、高并发会话状态管理以及 360° 球形视线光线追踪。

---

## 📋 Minecraft 26.1.2 快速规格表

| 规格项目 | 目标设定值 | 参考标识符 |
| :--- | :--- | :--- |
| **Minecraft 目标版本** | `26.1.2` | `"minecraft": "*"` |
| **当前子项目构建** | `1.1.2+26.1.2` | `mod_version=1.1.2+26.1.2` |
| **Java 工具链** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.16.10` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` | `fabric_version=0.145.4+26.1.2` |
| **底层核心通用库** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **客户端默认快捷键** | `Ctrl+M` | `GLFW.GLFW_KEY_M` + `isControlDown()` |
| **配置界面 GUI** | Cloth Config Fabric | `ClothConfigScreenHelper` + ModMenu |
| **服务端指令** | `/magnet toggle`, `/ig_magnet toggle` | `MagnetCommand.java` |

---

## ⚡ 核心特性亮点

* **360° 全向球形视线检测**：由 DasikLibrary 1.8.23 的 `PlayerVisionTracker` 驱动。参见[[视线检测与障碍机制|zh_cn-26.1.2-Line-of-Sight-and-Obstruction]]。
* **虚位移穿相（NoClip）物理**：吸附过程中掉落物能够自如穿透复杂地形方块。参见[[吸引移动与穿相逻辑|zh_cn-26.1.2-Vacuum-and-Phase-Shifting]]。
* **瞬间拾取模式**：可选的碰撞箱扩展逻辑，实现零延迟瞬时拾取。参见[[瞬间拾取模式|zh_cn-26.1.2-Instant-Pickup-Mode]]。
* **并发会话开关**：通过 `MagnetPlayerState` 高效管理玩家的热键与指令状态。参见[[玩家开关与会话状态|zh_cn-26.1.2-Player-Toggle-and-Persistence]]。
* **Cloth Config 配置界面**：清晰美观的游戏内设置菜单，并内置分类警告提示。参见[[Cloth Config 配置界面|zh_cn-26.1.2-Configuration]]。

---

## 📑 26.1.2 文档完整索引

### 🎮 玩法机制与管理配置
* [[真空吸附与虚位移物理|zh_cn-26.1.2-Vacuum-and-Phase-Shifting]]
* [[视线检测与障碍穿透|zh_cn-26.1.2-Line-of-Sight-and-Obstruction]]
* [[经验球吸附与同步|zh_cn-26.1.2-Experience-Orb-Attraction]]
* [[瞬间拾取模式与包围盒扩展|zh_cn-26.1.2-Instant-Pickup-Mode]]
* [[玩家切换、热键与状态存储|zh_cn-26.1.2-Player-Toggle-and-Persistence]]
* [[游戏规则参考与默认边界|zh_cn-26.1.2-GameRules]]
* [[服务端指令与原版客户端支持|zh_cn-26.1.2-Commands]]
* [[进度与原版机制依赖说明|zh_cn-26.1.2-Advancements]]
* [[Cloth Config 配置界面与 ModMenu|zh_cn-26.1.2-Configuration]]
* [[快捷栏 HUD 与视觉反馈|zh_cn-26.1.2-HUD-and-Diagnostics]]

### 💻 开发者与工程架构参考
* [[开发者环境配置、工具链与 Gradle Loom|zh_cn-26.1.2-Developer-Setup-and-Building]]
* [[架构、包结构与 Mixin 注入|zh_cn-26.1.2-Architecture-and-Mixins]]
* [[API 门面、接口与附属集成|zh_cn-26.1.2-API-and-Addon-Integration]]
* [[返回跨时代版本兼容性矩阵|zh_cn-Version-Compatibility]]
