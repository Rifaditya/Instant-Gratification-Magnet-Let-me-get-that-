# 💻 伺服端指令與原版客戶端支援 (MC 26.1.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 功能資訊面板 | 技術參數 |
| :--- | :--- |
| **指令核心類別** | `net.instantgratification.magnet.MagnetCommand` |
| **主識別字** | `/magnet` 與 `/ig_magnet`（完全對稱的別名） |
| **子指令** | `toggle` |
| **網路狀態同步** | `ServerPlayNetworking.send(player, new MagnetTogglePayload(newState))` |

---

## 📖 指令手冊

### `/magnet toggle` (或 `/ig_magnet toggle`)
切換執行指令玩家的物品磁吸狀態（開啟或關閉）。

* **指令語法**：`/magnet toggle`
* **底層執行邏輯**：
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
* **原版客戶端相容性**：連線至 Fabric 伺服器的原版客戶端玩家無需安裝客戶端模組，即可透過該指令直接切換磁吸開關。

---

## 🔗 相關 Wiki 文件
* [[🔄 玩家開關與會話狀態|zh_tw-26.1.2-Player-Toggle-and-Persistence]]
* [[⚙️ 遊戲規則 GameRules 參考|zh_tw-26.1.2-GameRules]]
* [[📖 返回 MC 26.1.2 文檔中心|zh_tw-26.1.2-Home]]
