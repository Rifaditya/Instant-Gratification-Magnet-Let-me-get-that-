# ⚡ インスタント回収モード (MC 26.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 機能インフォボックス | 技術パラメータ |
| :--- | :--- |
| **システムクラス** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **有効化ゲームルール** | `ig:magnet_instant` (デフォルト: `false`) |
| **範囲ゲームルール** | `ig:magnet_range` (デフォルト: `12`, 範囲: `1..64`) |
| **注入ポイント** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **対象変数** | プレイヤー回収バウンディングボックス (`AABB pickupArea`) |
| **インベントリロジック** | 100% バニラ `Player.touch(ItemEntity)` パイプライン |

---

## 📖 インスタント回収の概要

標準のバキュームモードが速度補間を用いてアイテムを空中から物理的に引き寄せるのに対し、**インスタント回収モード** は移動時間を完全にゼロにします。この機能を有効にすると、範囲内のドロップアイテムが出現した瞬間にプレイヤーのインベントリに直接吸収されます。

クラッシュの原因になりやすい危険なカスタムインベントリ挿入ループを実装する代わりに、**Magnet, Let me get that!** はバニラの `aiStep()` メソッド内でプレイヤー本来の回収バウンディングボックスを非破壊的に拡張することで、インスタント回収を実現しています。

```
+-----------------------------------------------------------------------------------+
|                            VANILLA PLAYER aiStep() TICK                           |
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
|               VANILLA ItemEntity.playerTouch(Player) EXECUTION                    |
|   - Native Inventory Stacking & Partial Pickups                                   |
|   - Vanilla Pickup Animation & Sound Events (item.pickup / entity.experience_orb) |
|   - Native Statistics & Advancements Triggers                                     |
|   - Full Container Overflow & Remaining Item Retention                            |
+-----------------------------------------------------------------------------------+
```

---

## 🧩 アーキテクチャ実装 (`PlayerMixin.java`)

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

### 主なエンジニアリング保証:
1. **安全ガード**: プレイヤーが死亡中（`player.isDeadOrDying()`）、スペクテイターモード中（`player.isSpectator()`）、または個人の磁石を無効にしている場合（`!isMagnetEnabled()`）、バウンディングボックスは絶対に拡張されません。
2. **サーバー主導の実行**: 回収処理は論理サーバー側（`!level.isClientSide()`）で厳格に実行され、アイテムのゴースト化やインベントリの非同期を防ぎます。
3. **二重移動競合の防止**: `ig:magnet_instant` が true の場合、`MagnetMovement.pull()` は速度更新を自動的に中止し、物理移動と即時回収が競合しないように制御されます。

---

## ⚙️ 関連設定とゲームルール

| GameRule | 型 | デフォルト | 説明 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | アイテムの飛行をなくし、インベントリへ即座に転送。 |
| `ig:magnet_range` | Integer | `12` | 回収 AABB を拡張するブロック半径。 |
| `ig:magnet_enabled` | Boolean | `true` | MOD 全体のマスター切り替え。 |

---

## 🔗 関連 Wiki ドキュメント
* [[バキューム物理と位相シフト|ja_jp-26.2-Vacuum-and-Phase-Shifting]]
* [[プレイヤートグルと状態永続化|ja_jp-26.2-Player-Toggle-and-Persistence]]
* [[アーキテクチャと Mixin|ja_jp-26.2-Architecture-and-Mixins]]
* [[MC 26.2 ポータルに戻る|ja_jp-26.2-Home]]
