# 🎨 Cloth Config 配置界面与 ModMenu (MC 26.1.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 配置信息栏 | 详细参数 |
| :--- | :--- |
| **配置文件路径** | `config/ig_magnet.json` |
| **GUI 支持库** | Cloth Config Fabric (`me.shedaniel.cloth:cloth-config-fabric:26.1.154`) |
| **ModMenu 入口点** | `net.instantgratification.magnet.config.ModMenuIntegration` |
| **GUI 界面构造器** | `net.instantgratification.magnet.config.ClothConfigScreenHelper` |
| **类加载安全隔离** | 通过 `GuiHelper.getOptionalFactory` 反射隔离 |

---

## 📖 配置系统架构

在 Minecraft 26.1.2 中，**Magnet, Let me get that!** 与 **Cloth Config Fabric** 以及 **ModMenu** 深度集成，提供游戏内图形化参数设置菜单。

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
                "ig_magnet",
                "net.instantgratification.magnet.config.ClothConfigScreenHelper",
                "createFactory"
        );
    }
}
```

---

## ⚠️ 配置生效优先级警告

> ⚠️ **重要提示**：  
> 在 ModMenu 界面或 `config/ig_magnet.json` 中的修改**仅影响新建世界时的基准默认值**。  
> 若要调整已创建世界中的参数，请查阅并在游戏内通过 `/gamerule` 使用[[游戏规则参考|zh_cn-26.1.2-GameRules]]。

---

## 🗂️ 配置分类与选项层级

```
Cloth Config 配置界面 ("Magnet, Let me get that! Configuration")
  ├── General Settings (常规设置)
  │     ├── Magnet Enabled (启用磁铁，默认: true)
  │     ├── Magnet Range (吸引半径，默认: 12，范围: 1..64)
  │     ├── Instant Pickup (瞬间拾取，默认: false)
  │     └── Magnet Noclip (虚位移穿相，默认: true)
  ├── Speeds & Pull Heuristics (速度与牵引算法)
  │     ├── Item Speed (物品速度，默认: 80，范围: 1..1000)
  │     └── Item Acceleration (吸引加速度，默认: 10，范围: 1..1000)
  ├── Line of Sight (LOS) (视线检测)
  │     ├── Line of Sight Only (仅限视线内，默认: true)
  │     ├── Keep Moving if Unseen (脱离视线保持移动，默认: true)
  │     ├── Blocked by Transparent (透明方块阻隔，默认: false)
  │     ├── Blocked by Flora (植被树叶阻隔，默认: false)
  │     └── Blocked by Block Entities (方块实体阻隔，默认: false)
  └── Visuals & Performance (视觉与性能)
        ├── Attract XP Orbs (吸附经验球，默认: true)
        ├── Magnet Particles (粒子效果，默认: true)
        ├── Particle Count (单源粒子数，默认: 1，范围: 0..100)
        └── Max Particle Sources (最大粒子源，默认: 5，范围: 0..100)
```

---

## 📄 原始 JSON 结构 (`config/ig_magnet.json`)

```json
{
  "configVersion": 1,
  "enabled": true,
  "range": 12,
  "noClip": true,
  "affectsXp": true,
  "particles": true,
  "particleCount": 1,
  "maxParticleSources": 5,
  "speed": 80,
  "acceleration": 10,
  "instant": false,
  "losOnly": true,
  "keepMovingIfUnseen": true,
  "blockedByTransparent": false,
  "blockedByFlora": false,
  "blockedByBlockEntities": false
}
```

---

## 🔗 相关 Wiki 文档
* [[游戏规则完整参考|zh_cn-26.1.2-GameRules]]
* [[开发者环境配置与构建|zh_cn-26.1.2-Developer-Setup-and-Building]]
* [[返回 MC 26.1.2 门户|zh_cn-26.1.2-Home]]
