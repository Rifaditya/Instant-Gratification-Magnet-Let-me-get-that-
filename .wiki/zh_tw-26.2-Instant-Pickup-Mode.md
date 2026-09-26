# ⚡ 瞬間拾取模式 (MC 26.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 功能資訊面板 | 技術參數 |
| :--- | :--- |
| **系統核心類別** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **啟用遊戲規則** | `ig:magnet_instant`（預設：`false`） |
| **半徑遊戲規則** | `ig:magnet_range`（預設：`12`，範圍：`1..64`） |
| **注入點** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **目標變數** | 玩家拾取碰撞盒 (`AABB pickupArea`) |
| **背包處理邏輯** | 100% 原生原版 `Player.touch(ItemEntity)` 管道 |

---

## 📖 瞬間拾取概覽

標準牽引模式透過向量插值讓物品在空中平滑飛行，而**瞬間拾取模式（Instant Pickup Mode）**則徹底消除了任何飛行時間。啟用時，範圍內掉落的物品將在出現的同一微秒內瞬間被吸入玩家背包。

**Magnet, Let me get that!** 摒棄了容易引發崩潰且邏輯危險的自定義背包遍歷程式碼，而是採用非破壞性手段，在原版的 `aiStep()` 方法中動態擴展玩家原生拾取碰撞盒（Bounding Box）。

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
    if (!this.ig_magnet$isMagnetEnabled() || player.isDeadOrDying() || player.isSpectator()) {
        return pickupArea;
    }

    Level level = player.level();
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_ENABLED) && ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    return pickupArea;
}
```

### 關鍵工程保障：
1. **嚴密安全守衛**：若玩家處於死亡/瀕死狀態（`player.isDeadOrDying()`）、旁觀者模式（`player.isSpectator()`）或關閉了個人磁吸開關（`!isMagnetEnabled()`），拾取區域永遠不會擴展。
2. **伺服端主權判定**：拾取邏輯完全在邏輯伺服端執行（`!level.isClientSide()`），徹底杜絕客戶端幽靈物品與背包不同步問題。
3. **消除雙重移動衝突**：當 `ig:magnet_instant` 為 true 時，`MagnetMovement.pull()` 會自動放棄給予物理速度，防止常規飛行軌跡與瞬間入包相互衝突搶奪控制權。

---

## ⚙️ 相關設定與遊戲規則 GameRules

| 遊戲規則 Identifier | 類型 | 預設值 | 描述與效果 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | 啟用物品瞬間傳送進包模式，消除飛行耗時。 |
| `ig:magnet_range` | Integer | `12` | 拾取 AABB 碰撞盒擴展的方塊半徑。 |
| `ig:magnet_enabled` | Boolean | `true` | 全模組功能的全域總開關。 |

---

## 🔗 相關 Wiki 文件
* [[🧲 吸引移動與穿相邏輯|zh_tw-26.2-Vacuum-and-Phase-Shifting]]
* [[🔄 玩家開關與持久化存儲|zh_tw-26.2-Player-Toggle-and-Persistence]]
* [[🧩 架構設計與 Mixin 注入目標|zh_tw-26.2-Architecture-and-Mixins]]
* [[📖 返回 MC 26.2 文檔中心|zh_tw-26.2-Home]]
