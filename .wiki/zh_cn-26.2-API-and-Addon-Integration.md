# 🔌 API 门面、接口与附属集成 (MC 26.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| API 信息栏 | 技术参数 |
| :--- | :--- |
| **玩家接口** | `net.instantgratification.magnet.IMagnetPlayer` |
| **实体接口** | `net.instantgratification.magnet.IMagnetEntity` |
| **核心移动门面** | `net.instantgratification.magnet.MagnetMovement` |
| **游戏规则 API** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **视线检测 API** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 模组间开发者集成概述

第三方模组、服务端管理工具及即时满足（Instant Gratification）附属组件可以直接与 **Magnet, Let me get that!** 交互，以查询玩家的磁铁状态、通过程序化代码触发吸引，或控制穿墙机制。

---

## 🧑‍💻 玩家状态接口 (`IMagnetPlayer`)

将任意 `Player` 或 `ServerPlayer` 实例强转为 `IMagnetPlayer` 即可查询或修改磁铁偏好设置：

```java
package net.instantgratification.magnet;

public interface IMagnetPlayer {
    boolean ig_magnet$isMagnetEnabled();
    void ig_magnet$setMagnetEnabled(boolean enabled);
    boolean ig_magnet$toggleMagnet();
}
```

### 使用示例：
```java
// 检查玩家当前是否开启了物品磁铁
if (((IMagnetPlayer) player).ig_magnet$isMagnetEnabled()) {
    // 自定义附属模组逻辑...
}

// 通过代码禁用磁铁（例如玩家坐在王座上或参与特定小游戏时）
((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(false);
```

---

## 📦 实体吸附标记接口 (`IMagnetEntity`)

将任意 `Entity` 实例（如自定义生物掉落物、弹射物或经验球）强转为 `IMagnetEntity`：

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig_magnet$setMagnetNoClip();
    boolean ig_magnet$isMagnetNoClip();
    void ig_magnet$setMagnetized();
    boolean ig_magnet$isMagnetized();
}
```

### 使用示例：
```java
// 为自定义实体赋予临时的 2 刻穿相虚位移能力
((IMagnetEntity) customItem).ig_magnet$setMagnetNoClip();
```

---

## 🚀 静态移动门面 (`MagnetMovement.pull`)

附属模组可以直接调用内置的动力学与视线引擎，将实体手动牵引至指定玩家：

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

```java
// 将目标实体牵引至玩家，并生成电火花粒子尾迹
MagnetMovement.pull(specialDrop, player, true);
```

---

## 🔗 相关 Wiki 文档
* [[架构设计与 Mixin 注入目标|zh_cn-26.2-Architecture-and-Mixins]]
* [[开发者环境配置与构建|zh_cn-26.2-Developer-Setup-and-Building]]
* [[返回 MC 26.2 门户|zh_cn-26.2-Home]]
