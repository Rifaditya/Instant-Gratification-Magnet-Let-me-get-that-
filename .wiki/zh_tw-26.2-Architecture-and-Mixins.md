# 🧩 架構設計與 Mixin 注入目標 (MC 26.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 架構資訊面板 | 技術參數 |
| :--- | :--- |
| **根套件路徑** | `net.instantgratification.magnet` |
| **Mixin 設定檔** | `src/main/resources/magnet.mixins.json` |
| **Access Widener** | `src/main/resources/magnet.accesswidener` |
| **Java 相容層級** | `JAVA_25` |
| **Mixin 類別總數** | `2` (`MixinEntity`, `PlayerMixin`) |

---

## 🌳 套件架構目錄樹

```text
net.instantgratification.magnet/
├── IMagnetEntity.java                  # 實體介面：NoClip 與已磁吸標記
├── IMagnetPlayer.java                  # 玩家介面：開關狀態與 Getter/Setter
├── MagnetCommand.java                  # Brigadier 指令樹 (/magnet toggle, /magnet debug)
├── MagnetDebugLogger.java              # 寫入硬碟的非同步執行緒安全日誌器
├── MagnetManager.java                  # 實體掃描與空間排程執行迴圈
├── MagnetMod.java                      # 伺服端/通用模組進入點與網路包監聽
├── MagnetModClient.java                # 客戶端初始化類別、快捷鍵與覆蓋提示
├── MagnetMovement.java                 # 軌跡向量數學、動態插值速度與粒子
├── MagnetTogglePayload.java            # 網路資料包 Record 與 StreamCodec 組合
├── SecondaryVisionCheck.java           # 細粒度方塊射線檢測 (植被、方塊實體、玻璃)
├── config/
│   ├── MagnetConfig.java               # JSON 設定存儲與 POJO 欄位定義
│   ├── ModMenuIntegration.java         # 反射安全的 ModMenu API 進入點
│   └── YaclScreenHelper.java           # YetAnotherConfigLib v3 GUI 構建器
├── mixin/
│   ├── MixinEntity.java                # 向 Entity 注入 NoClip 穿相與重力抵消
│   └── PlayerMixin.java                # 向 Player 注入 NBT 持久化與瞬間拾取
├── registry/
│   └── ModGameRules.java               # DynamicGameRuleManager 遊戲規則註冊
└── util/
    └── ModVersionGuard.java            # 運行時 Knot 類別載入器安全性檢查
```

---

## 📋 Mixin 注入目標深度解析

### 1. `net.instantgratification.magnet.mixin.MixinEntity`
目標類別為 `net.minecraft.world.entity.Entity`，實作了 `IMagnetEntity` 介面。

| 注入方法 | 注入錨點 | 執行動作與行為說明 |
| :--- | :--- | :--- |
| `ig_magnet$decrementNoClipTicks` | `@Inject(method = "tick", at = @At("HEAD"))` | 處於激活狀態時，每 Tick 將 `noClipTicks` 倒數計時器減 1。 |
| `ig_magnet$preventPushOut` | `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` | 若在伺服端檢測到 `noClipTicks > 0`，取消原版方塊向外擠壓推力。 |
| `ig_magnet$forceNoClipOnMove` | `@Inject(method = "move", at = @At("HEAD"))` | 若 `noClipTicks > 0`，快取 `originalNoPhysics` 並強制設定 `entity.noPhysics = true`。 |
| `ig_magnet$restoreNoPhysicsAfterMove` | `@Inject(method = "move", at = @At("RETURN"))` | 移動完成後，立即將 `entity.noPhysics` 還原為 `originalNoPhysics`。 |
| `ig_magnet$conditionalCancelGravity` | `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` | **僅**在物品物理上陷入實心方塊內部時消除向下重力加速度。 |

---

### 2. `net.instantgratification.magnet.mixin.PlayerMixin`
目標類別為 `net.minecraft.world.entity.player.Player`，實作了 `IMagnetPlayer` 介面。

| 注入方法 | 注入錨點 | 執行動作與行為說明 |
| :--- | :--- | :--- |
| `ig_magnet$addAdditionalSaveData` | `@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))` | 透過 `ValueOutput` 將布林值 `ig_magnet_enabled` 序列化至玩家 NBT。 |
| `ig_magnet$readAdditionalSaveData` | `@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))` | 透過 `ValueInput.getBooleanOr()` 從玩家 NBT 反序列化載入開關狀態。 |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | 在伺服端每 Game Tick 呼叫 `MagnetManager.tick(player)`。 |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | 當 `ig:magnet_instant` 為 true 時，動態擴展拾取碰撞盒 (`pickupArea.inflate(range)`)。 |

---

## 🛡️ Knot 類別載入器安全性檢查 (`ModVersionGuard`)

為了保護伺服器與世界存檔免於在不相容或未預期的 Minecraft 核心版本上運行：

```java
public final class ModVersionGuard {
    public static void checkClass(String modName, String requiredClassName) {
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        // 在模組初始化繼續前，驗證目標關鍵類別的存在性...
    }
}
```

在 `MagnetMod.onInitialize()` 中呼叫：
```java
ModVersionGuard.checkClass("Magnet", "net.minecraft.world.entity.item.ItemEntity");
```

---

## 🔗 相關 Wiki 文件
* [[🧲 吸引移動與穿相邏輯|zh_tw-26.2-Vacuum-and-Phase-Shifting]]
* [[🔄 玩家開關與持久化存儲|zh_tw-26.2-Player-Toggle-and-Persistence]]
* [[🔌 API 與附屬模組整合|zh_tw-26.2-API-and-Addon-Integration]]
* [[📖 返回 MC 26.2 文檔中心|zh_tw-26.2-Home]]
