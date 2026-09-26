# 🧲 吸引移動與穿相邏輯 (MC 26.1.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 功能資訊面板 | 技術參數 |
| :--- | :--- |
| **系統核心類別** | `net.instantgratification.magnet.MagnetMovement` |
| **觸發事件** | 伺服端玩家 Tick (`PlayerMixin` $\rightarrow$ `MagnetManager.tick`) |
| **預設牽引半徑** | `12` 格 (`ig:magnet_range`) |
| **預設終端速度** | `80%` ($0.8\text{ 格/tick} = 16.0\text{ m/s}$) |
| **預設加速度** | `10%` ($0.10\text{ 插值係數/tick}$) |
| **穿相移動 (NoClip)** | 已啟用 (`ig:magnet_noclip = true`) |
| **目標向量** | 玩家眼部位置 (`player.getEyePosition()`) |
| **地面防摩擦高度加成** | 處於地面時 Y 軸 $+0.05\text{ m}$ (`entity.onGround()`) |

---

## 📖 系統概覽

在 Minecraft 26.1.2 中，磁吸牽引引擎在每個伺服器 Tick 持續追蹤玩家球面半徑內的有效 `ItemEntity` 實體，並透過動態插值將其拉向眼部高度。

在**穿相移動（NoClip）**機制啟用下，物品在飛行過程中能平滑穿透實心牆壁與方塊，徹底避免採礦或戰鬥掉落物卡在障礙物縫隙中。

```
+-------------+        視線檢查通過        +----------------------+        應用動態插值速度        +------------------+
|   掉落物    | -------------------------> | 設定 NoClip (2 Ticks)| ------------------------------> |  玩家眼部位置    |
+-------------+                            +----------------------+                                 +------------------+
                                                       |
                                                       v
                                             [取消方塊擠出推力]
                                             [繞過方塊碰撞檢測]
                                             [取消方塊內部重力]
```

---

## 🧮 物理與向量數學

### 1. 指向方向單位向量
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|}$$

### 2. 速度向量動態插值
$$\text{速度純量 } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0} = 0.8\text{ 格/tick}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
$$\text{加速度係數 } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0} = 0.10$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 3. 地面防摩擦阻力抬升
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 穿相移動（NoClip 引擎）

1. **狀態啟用**：`((IMagnetEntity) entity).ig$setMagnetNoClip()` 啟用 2-tick 倒數計時。
2. **物理覆寫 (`MixinEntity.java`)**：
   - `move(MoverType, Vec3)`：`@Inject(at = @At("HEAD"))` 快取 `originalNoPhysics` 並設定 `entity.noPhysics = true`。
   - `move(MoverType, Vec3)`：`@Inject(at = @At("RETURN"))` 還原 `entity.noPhysics = originalNoPhysics`。
3. **取消方塊擠出推力**：`@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` 阻止原版方塊向外擠壓推力。
4. **方塊內部重力抵消**：`@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` **僅**在物品物理上身處方塊內部時消除向下重力。

---

## ⚙️ 相關設定與遊戲規則 GameRules

| 遊戲規則 Identifier | 類型 | 預設值 | 說明與效果 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | 控制磁吸牽引機制的總開關。 |
| `ig:magnet_range` | Integer | `12` | 磁吸半徑方塊數 (1 到 64)。 |
| `ig:magnet_speed` | Integer | `80` | 終端飛行速度百分比（$80 = 0.8\text{ 格/tick}$）。 |
| `ig:magnet_acceleration` | Integer | `10` | 牽引加速度插值百分比（$10 = 10\%\text{ 插值/tick}$）。 |
| `ig:magnet_noclip` | Boolean | `true` | 允許被牽引物品在飛行中穿透方塊。 |

---

## 🔗 相關 Wiki 文件
* [[👁️ 視線檢測與障礙機制|zh_tw-26.1.2-Line-of-Sight-and-Obstruction]]
* [[⚡ 瞬間拾取模式|zh_tw-26.1.2-Instant-Pickup-Mode]]
* [[🧩 架構設計與 Mixin 注入目標|zh_tw-26.1.2-Architecture-and-Mixins]]
* [[📖 返回 MC 26.1.2 文檔中心|zh_tw-26.1.2-Home]]
