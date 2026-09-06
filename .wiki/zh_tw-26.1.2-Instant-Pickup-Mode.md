# ⚡ 瞬間拾取模式 (MC 26.1.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 功能資訊面板 | 技術參數 |
| :--- | :--- |
| **系統核心類別** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **啟用遊戲規則** | `ig:magnet_instant`（預設：`false`） |
| **半徑遊戲規則** | `ig:magnet_range`（預設：`12`，範圍：`1..64`） |
| **注入點** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **目標變數** | 玩家拾取碰撞盒 (`AABB pickupArea`) |

---

## 📖 瞬間拾取概覽

在 Minecraft 26.1.2 中，**瞬間拾取模式（Instant Pickup Mode）**透過在 `Player.aiStep()` 中擴展玩家原生拾取碰撞盒，消除了物品在空中的飛行耗時，直接將掉落物送入原版拾取處理管道。

```
+-----------------------------------------------------------------------------------+
|                            原版玩家 aiStep() 定時 Tick                            |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
                +---------------------------------------------------+
                |   PlayerMixin.ig_magnet$expandPickupArea()        |
                |   pickupArea = pickupArea.inflate(range);         |
                +---------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|               執行原版 ItemEntity.playerTouch(Player) 邏輯                        |
|   - 原生背包堆疊合併與部分拾取判定                                                |
|   - 原版拾取動畫與拾取音效 (item.pickup / entity.experience_orb)                   |
|   - 原版統計數據更新與成就進度觸發                                                |
|   - 容器已滿自動溢出與未拾取物品原地保留                                          |
+-----------------------------------------------------------------------------------+
```

---

## 🧩 架構實作 (`PlayerMixin.java`)

```java
@ModifyVariable(
        method = "aiStep",
        at = @At(value = "STORE"),
        ordinal = 0
)
private AABB ig_magnet$expandPickupArea(AABB pickupArea) {
    Player player = (Player) (Object) this;
    Level level = player.level();
    
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    
    return pickupArea;
}
```

---

## ⚙️ 相關設定與遊戲規則 GameRules

| 遊戲規則 Identifier | 類型 | 預設值 | 描述與效果 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | 若為 true，物品將立即傳送至玩家背包而無需飛行。 |
| `ig:magnet_range` | Integer | `12` | 擴展拾取碰撞盒的方塊半徑。 |
| `ig:magnet_enabled` | Boolean | `true` | 全模組功能的全域總開關。 |

---

## 🔗 相關 Wiki 文件
* [[🧲 吸引移動與穿相邏輯|zh_tw-26.1.2-Vacuum-and-Phase-Shifting]]
* [[⚙️ 遊戲規則 GameRules 參考|zh_tw-26.1.2-GameRules]]
* [[📖 返回 MC 26.1.2 文檔中心|zh_tw-26.1.2-Home]]
