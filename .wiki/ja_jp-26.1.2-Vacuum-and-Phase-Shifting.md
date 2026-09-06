# 🧲 バキューム移動と位相シフト (MC 26.1.2)

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

Minecraft 26.1.2 において、バキュームエンジンはプレイヤーの球状半径内にある有効な `ItemEntity` インスタンスを継続的に追跡し、線形補間（Lerp）を用いて目の高さへ直接引き寄せます。

**位相シフト (NoClip)** が有効な場合、アイテムは固体の壁やブロックを滑らかにすり抜け、採掘や戦闘中に障害物の裏側にドロップが取り残されるのを防止します。

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

### 1. 方向単位ベクトル
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|}$$

### 2. 速度補間
$$\text{Speed Scalar } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0} = 0.8\text{ blocks/tick}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
$$\text{Acceleration Factor } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0} = 0.10$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 3. 接地時の摩擦防止ブースト
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 位相シフト (NoClip エンジン)

1. **状態のアクティブ化**: `((IMagnetEntity) entity).ig$setMagnetNoClip()` により 2 ティックのカウントダウンが開始されます。
2. **物理演算の上書き (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` で `originalNoPhysics` をキャッシュし、`entity.noPhysics = true` を設定します。
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` で `entity.noPhysics = originalNoPhysics` を復元します。
3. **押し出し防止**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` により、バニラの壁押し出し力を遮断します。
4. **壁内重力キャンセル**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` により、アイテムが物理的にブロックボクセルの内側にある場合*のみ*重力をキャンセルします。

---

## ⚙️ 関連設定とゲームルール

| GameRule | 型 | デフォルト | 説明 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | バキュームメカニクスのマスター切り替え。 |
| `ig:magnet_range` | Integer | `12` | 吸引半径（ブロック単位、1〜64）。 |
| `ig:magnet_speed` | Integer | `80` | 終端速度のパーセンテージ ($80 = 0.8\text{ b/t}$)。 |
| `ig:magnet_acceleration` | Integer | `10` | 加速度補間係数のパーセンテージ ($10 = 10\%\text{ lerp/tick}$)。 |
| `ig:magnet_noclip` | Boolean | `true` | 引き寄せ中のブロック位相シフト（すり抜け）を有効化。 |

---

## 🔗 関連 Wiki ドキュメント
* [[視線判定と障害物貫通|ja_jp-26.1.2-Line-of-Sight-and-Obstruction]]
* [[インスタント回収モード|ja_jp-26.1.2-Instant-Pickup-Mode]]
* [[アーキテクチャと Mixin 実装|ja_jp-26.1.2-Architecture-and-Mixins]]
* [[MC 26.1.2 ポータルに戻る|ja_jp-26.1.2-Home]]
