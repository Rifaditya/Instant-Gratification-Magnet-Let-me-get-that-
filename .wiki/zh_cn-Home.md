# 🧲 物品磁铁 (Magnet, Let me get that!) — 官方 Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

欢迎查阅 **Magnet, Let me get that!**（模组 ID：`ig_magnet`）的官方技术与玩法 Wiki。这是一款专为现代 Fabric 架构 Minecraft 设计的原生拾取增强与经验吸附模组。

本模组基于**即时满足（Instant Gratification, IG）**设计哲学构建，彻底终结了“耻辱之行（walk of shame）”——即必须亲自走到 5 格开外去捡起刚刚挖掘的矿石或击杀掉落物的繁琐拖沓体验。只要能看见，就应当立即拥有。

---

## 🧭 多版本切换门户

选择您的目标 Minecraft 版本以查阅专属、独立的玩法指南、技术文档、游戏规则（GameRules）参考表与底层架构解析：

| Minecraft 版本 | 发布状态 | 当前版本构建 | 配置界面引擎 | 门户链接 |
| :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | 🟢 当前活跃现代版 | `1.3.9+26.2` | YACL v3 + ModMenu | [[MC 26.2 门户|zh_cn-26.2-Home]] |
| **Minecraft 26.1.2** | 🟢 现代基石版本 | `1.1.2+26.1.2` | Cloth Config + ModMenu | [[MC 26.1.2 门户|zh_cn-26.1.2-Home]] |

### 🚀 版本直达门户：
* 📦 **Minecraft 26.2**: [[👉 进入 Minecraft 26.2 文档门户|zh_cn-26.2-Home]]
* 📦 **Minecraft 26.1.2**: [[👉 进入 Minecraft 26.1.2 文档门户|zh_cn-26.1.2-Home]]

关于构建工具链、依赖版本矩阵、归档存储路径以及向后兼容性的深入解析，请参见[[跨时代版本兼容性矩阵|zh_cn-Version-Compatibility]]。

---

## ⚡ 核心特性结构图

```
                      +-----------------------------+
                      |       玩家真空吸引源        |
                      +--------------+--------------+
                                     |
              +----------------------+----------------------+
              |                                             |
              v                                             v
  +-----------------------+                     +-----------------------+
  |      标准吸引模式     |                     |      瞬间拾取模式     |
  +-----------+-----------+                     +-----------+-----------+
              |                                             |
      [视线检测 (LOS)]                              [AABB 包围盒扩展]
      [360° 球形射线检测]                           [零飞行延迟]
      [虚位移穿相 (NoClip)]                         [直接进入物品栏]
      [动态 Lerp 速度插值]                                  |
              |                                             |
              +----------------------+----------------------+
                                     |
                                     v
                      +-----------------------------+
                      |     物品与经验球完成捕获    |
                      +-----------------------------+
```

* **智能 360° 真空吸附**：在可配置的方块半径范围内（默认：12 格，最高可达 64 格）全方位吸引掉落物与经验球。
* **虚位移穿相（Phase Shifting / NoClip）**：被吸附的物品能够平滑穿透实体方块，防止掉落物永久卡死在爆破碎屑或矿道缝隙中。
* **视线感知（Line-of-Sight, LOS）**：基于 DasikLibrary 的 `PlayerVisionTracker` 提供原生 360° 全向视线光线追踪，并支持对透明方块（玻璃）、植被（高草丛、树叶）与方块实体（箱子）进行细粒度遮挡过滤。
* **动量延续（`keepMovingIfUnseen`）**：在视线范围内一旦被标记吸附，即使物品在飞行途中暂时绕入障碍物后方，仍会保持归巢动量继续飞向玩家。
* **瞬间拾取选项（Instant Pickup）**：扩展玩家的原生收集碰撞体积（AABB），以零飞行延迟瞬时将物品吸入物品栏。
* **玩家热键与指令控制**：客户端支持快捷键切换（26.2 默认为 `\`，26.1.2 默认为 `Ctrl+M`），服务端支持 `/magnet toggle` 指令控制。
* **零物品栏杂物**：100% 内生机制——无需合成特殊磁铁道具、饰品或消耗能量电池。

---

## 📚 百科全书式导航

### 🎮 玩家与管理员指南
* [[MC 26.2 概览|zh_cn-26.2-Home]] & [[MC 26.1.2 概览|zh_cn-26.1.2-Home]]
* [[MC 26.2 吸引移动与穿相逻辑|zh_cn-26.2-Vacuum-and-Phase-Shifting]] & [[MC 26.1.2 吸引移动与穿相逻辑|zh_cn-26.1.2-Vacuum-and-Phase-Shifting]]
* [[MC 26.2 视线检测机制|zh_cn-26.2-Line-of-Sight-and-Obstruction]] & [[MC 26.1.2 视线检测机制|zh_cn-26.1.2-Line-of-Sight-and-Obstruction]]
* [[MC 26.2 经验球吸附|zh_cn-26.2-Experience-Orb-Attraction]] & [[MC 26.1.2 经验球吸附|zh_cn-26.1.2-Experience-Orb-Attraction]]
* [[MC 26.2 瞬间拾取模式|zh_cn-26.2-Instant-Pickup-Mode]] & [[MC 26.1.2 瞬间拾取模式|zh_cn-26.1.2-Instant-Pickup-Mode]]
* [[MC 26.2 玩家开关与持久化存储|zh_cn-26.2-Player-Toggle-and-Persistence]] & [[MC 26.1.2 玩家开关与会话状态|zh_cn-26.1.2-Player-Toggle-and-Persistence]]
* [[MC 26.2 游戏规则 GameRules 参考|zh_cn-26.2-GameRules]] & [[MC 26.1.2 游戏规则 GameRules 参考|zh_cn-26.1.2-GameRules]]
* [[MC 26.2 Brigadier 指令套件|zh_cn-26.2-Commands]] & [[MC 26.1.2 服务端指令|zh_cn-26.1.2-Commands]]
* [[MC 26.2 进度树范围|zh_cn-26.2-Advancements]] & [[MC 26.1.2 进度树范围|zh_cn-26.1.2-Advancements]]
* [[MC 26.2 YACL 配置界面|zh_cn-26.2-Configuration]] & [[MC 26.1.2 Cloth Config 配置界面|zh_cn-26.1.2-Configuration]]
* [[MC 26.2 HUD 与诊断系统|zh_cn-26.2-HUD-and-Diagnostics]] & [[MC 26.1.2 HUD 与状态显示|zh_cn-26.1.2-HUD-and-Diagnostics]]

### 💻 开发者与贡献者文档
* [[MC 26.2 开发者配置与构建|zh_cn-26.2-Developer-Setup-and-Building]] & [[MC 26.1.2 开发者配置与构建|zh_cn-26.1.2-Developer-Setup-and-Building]]
* [[MC 26.2 架构设计与 Mixin 注入目标|zh_cn-26.2-Architecture-and-Mixins]] & [[MC 26.1.2 架构设计与 Mixin 注入目标|zh_cn-26.1.2-Architecture-and-Mixins]]
* [[MC 26.2 API 与附属模组集成|zh_cn-26.2-API-and-Addon-Integration]] & [[MC 26.1.2 API 与附属模组集成|zh_cn-26.1.2-API-and-Addon-Integration]]
* [[跨时代版本兼容性矩阵|zh_cn-Version-Compatibility]]

---

## ⚖️ 许可协议与版权归属

由 **Dasik (Rifaditya)** 基于 **GNU General Public License v3.0 (GPLv3)** 开源协议开发维护。完整条款与法律授权请参阅代码仓库中的 `LICENSE` 文件。
