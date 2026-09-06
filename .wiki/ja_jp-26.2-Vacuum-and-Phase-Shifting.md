# 🧲 バキューム移動と位相シフト (MC 26.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 機能インフォボックス | 技術パラメータ |
| :--- | :--- |
| **システムクラス** | `net.instantgratification.magnet.MagnetMovement` |
| **トリガーイベント** | サーバー側プレイヤータスク (`PlayerMixin` $\rightarrow$ `MagnetManager.tick`) |
| **デフォルト吸引範囲** | `12` ブロック (`ig:magnet_range`) |
| **デフォルト終端速度** | `80%` ($0.8\text{ blocks/tick} = 16.0\text{ m/s}$) |
| **デフォルト加速度** | `10%` ($0.10\text{ lerp factor/tick}$) |
| **位相シフト (NoClip)** | 有効 (`ig:magnet_noclip = true`) |
| **ターゲットベクトル** | プレイヤー視線位置 (`player.getEyePosition()`) |
| **地上オフセット加算** | `entity.onGround()` 時に Y 軸 $+0.05\text{ m}$ |

---

## 📖 システム概要

**Magnet, Let me get that!** のコアとなるバキュームメカニクスは、毎サーバーティックごとにプレイヤーの設定半径内にあるドロップされた `ItemEntity` インスタンスをスキャンし、滑らかな非線形補間（Lerp）を用いてプレイヤーの目の高さに向かって引き寄せます。

丸石の縁、樹木の葉、鉱脈の窪みなどにアイテムが引っかかるのを防ぐため、本 MOD では **位相シフト (NoClip)** をアクティブ化し、飛行中のアイテムが固体のブロックボクセルを無害にすり抜けられるようにします。

```
+-------------+      Line-of-Sight OK      +----------------------+      Lerp Velocity Applied      +------------------+
| Item Entity | -------------------------> | Set NoClip (2 Ticks) | ------------------------------> | Player Eye Pos   |
+-------------+                            +----------------------+                                 +------------------+
                                                      |
                                                      v
                                           [Cancel Wall Pushout]
                                           [Bypass Block Collide]
                                           [Cancel In-Wall Grav]
```

---

## 🧮 物理演算とベクトル数学

アイテムを引き寄せる際、軌道は 3D ユークリッド空間内で直接計算されます：

### 1. ターゲットへのベクトル
アイテムの現在位置を $\vec{p}_{\text{entity}} = (x_e, y_e, z_e)$、プレイヤーの視線位置を $\vec{p}_{\text{eye}} = (x_p, y_p + h_{\text{eye}}, z_p)$ とします。
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|} = \frac{\vec{v}_{\text{target}}}{\sqrt{\Delta x^2 + \Delta y^2 + \Delta z^2}}$$

### 2. 目標終端速度
目標速度ベクトルは、単位方向ベクトル $\hat{d}$ に設定された速度パラメータを乗算します：
$$\text{Speed Scalar } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
*デフォルト設定（$80\%$）では、$s = 0.8\text{ blocks/tick}$ となります。$20\text{ ticks/s}$ において、終端速度は $16.0\text{ m/s}$ に達します。*

### 3. 非線形加速度（Lerp）
速度は加速度係数 $a$ に基づく線形補間（`Vec3.lerp`）を用いて更新されます：
$$\text{Acceleration Factor } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0}$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 4. 接地時の摩擦防止ブースト
アイテムがブロック表面に接地している場合（`entity.onGround() == true`）、床の引きずりを防ぐために摩擦判定が即座に解除されます：
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 位相シフト (NoClip エンジン)

`ig:magnet_noclip` が有効な場合、アイテムには 2 ティックの NoClip ウィンドウが付与されます：

1. **状態のアクティブ化**: `((IMagnetEntity) entity).ig_magnet$setMagnetNoClip()` により `noClipTicks = 2` が設定されます。
2. **移動処理のハイジャック (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` で `originalNoPhysics` を退避し、`entity.noPhysics = true` を強制します。
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` で `entity.noPhysics = originalNoPhysics` を復元します。
3. **ブロック押し出しのキャンセル**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` により、バニラのブロック押し出しコードがアイテムを無理に弾き出すのを防止します。
4. **条件付き重力キャンセル**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` により、アイテムがブロックボクセルと物理的に交差している場合（`!level.noCollision(...)`）*のみ*下方向の重力をキャンセルし、空中での自然な放物線軌道を保護します。

---

## ⚙️ 関連設定とゲームルール

| GameRule | 型 | デフォルト | 単位 / 範囲 | 説明 |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | すべてのバキュームロジックのマスター切り替え。 |
| `ig:magnet_range` | Integer | `12` | `1..64` ブロック | 最大の球状バキューム半径。 |
| `ig:magnet_speed` | Integer | `80` | `1..1000%` | 終端速度のパーセンテージ ($80 = 0.8\text{ b/t}$)。 |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000%` | 引き寄せ加速度のパーセンテージ ($10 = 10\%\text{ lerp/tick}$)。 |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | 引き寄せ中のブロック位相シフト（すり抜け）を有効化。 |

---

## 🔗 関連 Wiki ドキュメント
* [[視線判定と障害物貫通|ja_jp-26.2-Line-of-Sight-and-Obstruction]]
* [[インスタント回収モード|ja_jp-26.2-Instant-Pickup-Mode]]
* [[アーキテクチャと Mixin 実装|ja_jp-26.2-Architecture-and-Mixins]]
* [[MC 26.2 ポータルに戻る|ja_jp-26.2-Home]]
