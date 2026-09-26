# 💻 服务端指令与原版客户端支持 (MC 26.1.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 特性信息栏 | 技术参数 |
| :--- | :--- |
| **命令注册类** | `net.instantgratification.magnet.MagnetCommand` |
| **主命令字面量** | `/magnet` 与 `/ig_magnet`（完全等价的别名镜像） |
| **子命令** | `toggle` |
| **网络同步** | `ServerPlayNetworking.send(player, new MagnetTogglePayload(newState))` |

---

## 📖 命令参考

### `/magnet toggle` (或 `/ig_magnet toggle`)
切换当前执行指令玩家的个人磁铁吸附状态。

* **命令语法**：`/magnet toggle`
* **执行逻辑**：
  ```java
  private static int toggleMagnet(CommandSourceStack source) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      boolean newState = MagnetPlayerState.toggleMagnet(player);

      if (newState) {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.enabled"), false);
      } else {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.disabled"), false);
      }

      if (ServerPlayNetworking.canSend(player, MagnetTogglePayload.TYPE)) {
          ServerPlayNetworking.send(player, new MagnetTogglePayload(newState));
      }
      return 1;
  }
  ```
* **原版客户端兼容性**：允许连接到 Fabric 服务端的原版客户端玩家在不安装任何客户端模组的情况下自由切换磁铁。

---

## 🔗 相关 Wiki 文档
* [[玩家切换与状态管理|zh_cn-26.1.2-Player-Toggle-and-Persistence]]
* [[游戏规则完整参考|zh_cn-26.1.2-GameRules]]
* [[返回 MC 26.1.2 门户|zh_cn-26.1.2-Home]]
