# 🔌 API 门面、接口与附属集成 (MC 26.1.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| API 信息栏 | 技术参数 |
| :--- | :--- |
| **玩家状态管理器** | `net.instantgratification.magnet.MagnetPlayerState` |
| **实体接口** | `net.instantgratification.magnet.IMagnetEntity` |
| **核心移动门面** | `net.instantgratification.magnet.MagnetMovement` |
| **游戏规则 API** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **视线检测 API** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 模组间开发者集成概述

在 Minecraft 26.1.2 中，第三方模组与即时满足伴侣附属能够直接与 **Magnet, Let me get that!** 协同工作。

---

## 🧑‍💻 玩家状态管理 (`MagnetPlayerState`)

直接通过静态辅助方法查询或修改玩家的磁铁偏好设置：

```java
package net.instantgratification.magnet;

public class MagnetPlayerState {
    public static boolean isMagnetEnabled(Player player);
    public static void setMagnetEnabled(Player player, boolean enabled);
    public static boolean toggleMagnet(Player player);
}
```

### 使用示例：
```java
// 检查玩家是否开启了磁铁
if (MagnetPlayerState.isMagnetEnabled(player)) {
    // 自定义业务逻辑...
}

// 通过代码禁用磁铁
MagnetPlayerState.setMagnetEnabled(player, false);
```

---

## 📦 实体吸附标记接口 (`IMagnetEntity`)

将任意 `Entity` 实例强转为 `IMagnetEntity`，即可控制其穿相或吸附状态标记：

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig$setMagnetNoClip();
    boolean ig$isMagnetNoClip();
    void ig$setMagnetized();
    boolean ig$isMagnetized();
}
```

---

## 🚀 静态移动门面 (`MagnetMovement.pull`)

通过程序化代码触发物品或经验球向玩家牵引：

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

---

## 🔗 相关 Wiki 文档
* [[架构设计与 Mixin 注入目标|zh_cn-26.1.2-Architecture-and-Mixins]]
* [[开发者环境配置与构建|zh_cn-26.1.2-Developer-Setup-and-Building]]
* [[返回 MC 26.1.2 门户|zh_cn-26.1.2-Home]]
