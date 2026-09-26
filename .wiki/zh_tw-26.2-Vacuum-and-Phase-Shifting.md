# 🧲 吸引移動與穿相邏輯 (MC 26.2)

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

**Magnet, Let me get that!** 的核心磁吸機制會在每個伺服器 Tick 掃描玩家設定半徑內的掉落物 `ItemEntity` 實體，並使用平滑非線性插值將其拉向玩家的眼部位置。

為了防止掉落物卡在鵝卵石邊緣、樹冠頂部或礦脈裂縫中，本模組激活了**穿相移動（NoClip）**機制，使飛行中的物品可以無害地穿過實心方塊體積。

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

當牽引一個實體時，軌跡直接在三維歐幾里得空間中計算：

### 1. 指向目標的向量
設 $\vec{p}_{\text{entity}} = (x_e, y_e, z_e)$ 為物品的當前位置，$\vec{p}_{\text{eye}} = (x_p, y_p + h_{\text{eye}}, z_p)$ 為玩家的眼部位置。
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|} = \frac{\vec{v}_{\text{target}}}{\sqrt{\Delta x^2 + \Delta y^2 + \Delta z^2}}$$

### 2. 期望終端速度
目標速度向量將單位方向向量 $\hat{d}$ 乘以設定的速度純量參數：
$$\text{速度純量 } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
*在預設設定（$80\%$）下，$s = 0.8\text{ 格/tick}$。以 $20\text{ ticks/s}$ 計算，終端速度為 $16.0\text{ m/s}$。*

### 3. 非線性加速度插值 (Lerp)
速度依據加速度插值係數 $a$ 使用線性插值（`Vec3.lerp`）進行動態更新：
$$\text{加速度係數 } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0}$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 4. 地面防摩擦阻力抬升
若物品靜止在方塊表面（`entity.onGround() == true`），系統會立即解除其地面判定，防止貼地拖拽減速：
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 穿相移動（NoClip 引擎）

當 `ig:magnet_noclip` 啟用時，實體將被打上 2-tick 的 NoClip 狀態標記：

1. **狀態啟用**：`((IMagnetEntity) entity).ig_magnet$setMagnetNoClip()` 將倒數計時設為 `noClipTicks = 2`。
2. **移動邏輯劫持 (`MixinEntity.java`)**：
   - `move(MoverType, Vec3)`：`@Inject(at = @At("HEAD"))` 保存 `originalNoPhysics` 並強制設定 `entity.noPhysics = true`。
   - `move(MoverType, Vec3)`：`@Inject(at = @At("RETURN"))` 恢復 `entity.noPhysics = originalNoPhysics`。
3. **取消方塊擠出推力**：`@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` 阻止原版方塊推力程式碼將物品強行彈射出方塊。
4. **條件式內部重力消除**：`@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` **僅**在物品物理上處於實心方塊內部時（`!level.noCollision(...)`）消除向下重力，保留開闊空間中的自然拋物線軌跡。

---

## ⚙️ 相關設定與遊戲規則 GameRules

| 遊戲規則 Identifier | 類型 | 預設值 | 單位 / 範圍 | 描述與效果 |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | 控制所有磁吸牽引邏輯的全域總開關。 |
| `ig:magnet_range` | Integer | `12` | `1..64` 格 | 玩家吸引掉落物的最大球面方塊半徑。 |
| `ig:magnet_speed` | Integer | `80` | `1..1000%` | 終端飛行速度百分比（$80 = 0.8\text{ 格/tick}$）。 |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000%` | 牽引加速度插值百分比（$10 = 10\%\text{ 插值/tick}$）。 |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | 是否允許被牽引物品在飛行中穿透實心方塊。 |

---

## 🔗 相關 Wiki 文件
* [[👁️ 視線檢測與障礙機制|zh_tw-26.2-Line-of-Sight-and-Obstruction]]
* [[⚡ 瞬間拾取模式|zh_tw-26.2-Instant-Pickup-Mode]]
* [[🧩 架構設計與 Mixin 注入目標|zh_tw-26.2-Architecture-and-Mixins]]
* [[📖 返回 MC 26.2 文檔中心|zh_tw-26.2-Home]]
