# 🔄 玩家開關與持久化存儲 (MC 26.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 功能資訊面板 | 技術參數 |
| :--- | :--- |
| **介面橋接** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Mixin 目標** | `net.minecraft.world.entity.player.Player` (`PlayerMixin.java`) |
| **預設客戶端快捷鍵** | `\` (反斜線) — `key.ig_magnet.toggle` |
| **按鍵分類** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **網路資料包** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **NBT Codec 儲存** | `ValueOutput` / `ValueInput` 儲存於標籤 `"ig_magnet_enabled"` |
| **生命週期事件** | `ServerPlayConnectionEvents.JOIN`, `ServerPlayerEvents.COPY_FROM`, `ServerPlayerEvents.AFTER_RESPAWN` |

---

## 📖 狀態架構概覽

在多人伺服器與模組包環境中，玩家常有不同偏好——建築師希望在裝飾精細結構時暫停吸物，而礦工則需要全功率運轉。

**Magnet, Let me get that!** 實作了**獨立的個別玩家開關狀態**，在存檔重載、玩家死亡、重生以及維度傳送時具備 100% 的持久化保障。

```
                                [客戶端操作]
                       玩家按下切換快捷鍵 ('\')
                                      |
                                      v
                             [本地狀態即時更新]
                      client.player -> isEnabled = !isEnabled
                      快捷列上方提示: "物品磁吸: 已開啟/已關閉"
                                      |
                                      v
                             [發送 C2S 網路資料包]
                      ClientPlayNetworking.send(MagnetTogglePayload)
                                      |
                                      v
                                [伺服端接收處理]
                      context.server().execute(() -> {
                          ((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(val);
                      })
                                      |
          +---------------------------+---------------------------+
          |                                                       |
          v                                                       v
  [NBT 資料持久化寫入]                                    [掛接生命週期鉤子]
  ValueOutput.putBoolean("ig_magnet_enabled")            - JOIN: S2C 狀態同步
  ValueInput.getBooleanOr("ig_magnet_enabled", true)     - COPY_FROM: 死亡無損轉移
                                                        - AFTER_RESPAWN: 重生新實體同步
```

---

## ⌨️ 客戶端快捷鍵與快捷列提示

* **預設按鍵**：`GLFW_KEY_BACKSLASH`（`\`），有效避免與常見小地圖模組或背包整理工具的鍵位衝突。
* **動態鍵盤類型輔助**：透過 `ig_magnet$getKeyboardType()` 安全解析 `InputConstants.Type.KEYBOARD`，並在不同 Fabric Loader 快照版本中自動降級回退至 `KEYSYM`。
* **即時視覺回饋**：切換時會在快捷列上方跳出在地化快顯通知：
  - `chat.ig_magnet.enabled`：`"物品磁吸: 已開啟"`
  - `chat.ig_magnet.disabled`：`"物品磁吸: 已關閉"`

---

## 💾 NBT 儲存與 Minecraft 26.2 編解碼序列化

玩家開關設定透過 Minecraft 26.2 的 `ValueOutput` 與 `ValueInput` 管道，直接寫入玩家 `.dat` 世界存檔中：

```java
// 保存至玩家 NBT
@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
    output.putBoolean("ig_magnet_enabled", this.ig_magnet$enabled);
}

// 從玩家 NBT 讀取
@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
    this.ig_magnet$enabled = input.getBooleanOr("ig_magnet_enabled", true);
}
```

---

## 🧬 Fabric 生命週期事件與死亡重生流程

當玩家在 Minecraft 中死亡時，遊戲會在重生時創建全新的 `ServerPlayer` 實體。本模組保證狀態零丟失：

1. **`ServerPlayerEvents.COPY_FROM`**：在實體複製瞬間，立即將布林開關值從 `oldPlayer` 拷貝至 `newPlayer`。
2. **`ServerPlayerEvents.AFTER_RESPAWN`**：在新生實體連線完成後，自動發送 S2C `MagnetTogglePayload` 同步包，使客戶端 HUD 保持完全同步。
3. **`ServerPlayConnectionEvents.JOIN`**：在玩家加入專用伺服器或區域網路世界時，自動將保存的 NBT 狀態同步至客戶端。

---

## 🔗 相關 Wiki 文件
* [[💻 Brigadier 指令套件|zh_tw-26.2-Commands]]
* [[🧩 架構設計與 Mixin 注入目標|zh_tw-26.2-Architecture-and-Mixins]]
* [[📊 HUD 與診斷系統|zh_tw-26.2-HUD-and-Diagnostics]]
* [[📖 返回 MC 26.2 文檔中心|zh_tw-26.2-Home]]
