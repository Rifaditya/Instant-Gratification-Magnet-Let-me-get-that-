# 🔄 玩家切换、持久化与生命周期 (MC 26.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 特性信息栏 | 技术参数 |
| :--- | :--- |
| **接口桥接类** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Mixin 目标** | `net.minecraft.world.entity.player.Player` (`PlayerMixin.java`) |
| **默认客户端按键** | `\`（反斜杠）— `key.ig_magnet.toggle` |
| **按键分类组** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **网络数据包 Payload** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **NBT Codec 序列化** | `ValueOutput` / `ValueInput` 存储于标签 `"ig_magnet_enabled"` |
| **生命周期事件** | `ServerPlayConnectionEvents.JOIN`, `ServerPlayerEvents.COPY_FROM`, `ServerPlayerEvents.AFTER_RESPAWN` |

---

## 📖 状态架构概览

在多人服务器和模组整合包中，不同玩家的需求截然不同——建筑玩家在精细装饰时可能希望暂停吸物，而采矿玩家则希望保持最强吸力。

**Magnet, Let me get that!** 实现了**完全独立的每玩家独立开关状态**，并在世界重载、死亡、重生以及跨维度传送时提供 100% 可靠的数据持久化。

```
                                [客户端操作]
                       玩家按下切换快捷键 ('\')
                                      |
                                      v
                               [本地状态更新]
                       client.player -> isEnabled = !isEnabled
                       快捷栏上方提示: "物品磁铁: 已开启 / 已禁用"
                                      |
                                      v
                             [C2S 数据包发送]
                       ClientPlayNetworking.send(MagnetTogglePayload)
                                      |
                                      v
                                [服务端接收端]
                       context.server().execute(() -> {
                           ((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(val);
                       })
                                      |
          +---------------------------+---------------------------+
          |                                                       |
          v                                                       v
  [写入 NBT 持久化存储]                                   [挂钩 Fabric 生命周期]
  ValueOutput.putBoolean("ig_magnet_enabled")             - JOIN: 进服 S2C 状态同步
  ValueInput.getBooleanOr("ig_magnet_enabled", true)      - COPY_FROM: 死亡克隆保留
                                                          - AFTER_RESPAWN: 新实体 S2C 同步
```

---

## ⌨️ 客户端按键绑定与快捷栏 HUD 悬浮文本

* **默认按键**：`GLFW_KEY_BACKSLASH`（`\`），有效避开了主流小地图模组和背包整理工具的常用键位冲突。
* **动态按键类型解析器**：通过 `ig_magnet$getKeyboardType()` 优雅处理 `InputConstants.Type.KEYBOARD`，在不同 Fabric Loader 快照版本中平滑回退至 `KEYSYM`。
* **即时视觉反馈**：按键触发时在快捷栏正上方渲染悬浮提示：
  - `chat.ig_magnet.enabled`: `"物品磁铁: 已开启"`
  - `chat.ig_magnet.disabled`: `"物品磁铁: 已禁用"`

---

## 💾 NBT 存储与 Minecraft 26.2 Codec 序列化

玩家的磁铁开关设置直接通过 Minecraft 26.2 的 `ValueOutput` 与 `ValueInput` 机制保存至玩家的 `.dat` 存档文件中：

```java
// 保存至玩家 NBT
@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
    output.putBoolean("ig_magnet_enabled", this.ig_magnet$enabled);
}

// 从玩家 NBT 读取
@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
    this.ig_magnet$enabled = input.getBooleanOr("ig_magnet_enabled", true);
}
```

---

## 🧬 Fabric 生命周期事件与死亡重生流程

当玩家在 Minecraft 中死亡时，游戏在重生时会生成一个全新的 `ServerPlayer` 实体实例。模组通过以下事件保证状态零丢失：

1. **`ServerPlayerEvents.COPY_FROM`**：实体克隆时立即将布尔状态从 `oldPlayer` 复制到 `newPlayer`。
2. **`ServerPlayerEvents.AFTER_RESPAWN`**：新玩家实体建立连接后，向客户端主动发送 S2C `MagnetTogglePayload`，确保客户端 HUD 状态实时同步。
3. **`ServerPlayConnectionEvents.JOIN`**：当玩家加入专用服务器或局域网世界时，自动同步保存的 NBT 状态。

---

## 🔗 相关 Wiki 文档
* [[Brigadier 指令与服务端开关|zh_cn-26.2-Commands]]
* [[架构设计与 Mixin 实现|zh_cn-26.2-Architecture-and-Mixins]]
* [[HUD 与诊断系统|zh_cn-26.2-HUD-and-Diagnostics]]
* [[返回 MC 26.2 门户|zh_cn-26.2-Home]]
