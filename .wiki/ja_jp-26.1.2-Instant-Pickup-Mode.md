# ⚡ インスタント回収モード (MC 26.1.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 機能インフォボックス | 技術パラメータ |
| :--- | :--- |
| **システムクラス** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **有効化ゲームルール** | `ig:magnet_instant` (デフォルト: `false`) |
| **範囲ゲームルール** | `ig:magnet_range` (デフォルト: `12`, 範囲: `1..64`) |
| **注入ポイント** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **対象変数** | プレイヤー回収バウンディングボックス (`AABB pickupArea`) |

---

## 📖 インスタント回収の概要

Minecraft 26.1.2 において、**インスタント回収モード** は `Player.aiStep()` 内でプレイヤーの回収バウンディングボックスを拡張することにより、アイテムの移動時間をゼロにし、バニラの回収ハンドラーを通じてドロップをプレイヤーのインベントリへ即時に格納します。

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

## ⚙️ 関連設定とゲームルール

| GameRule | 型 | デフォルト | 説明 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | true の場合、アイテムは飛翔せずプレイヤーへ即座にテレポート回収されます。 |
| `ig:magnet_range` | Integer | `12` | 拡張される回収エリアのブロック半径。 |
| `ig:magnet_enabled` | Boolean | `true` | MOD 全体のマスター切り替え。 |

---

## 🔗 関連 Wiki ドキュメント
* [[バキューム物理と位相シフト|ja_jp-26.1.2-Vacuum-and-Phase-Shifting]]
* [[GameRules 完全リファレンス|ja_jp-26.1.2-GameRules]]
* [[MC 26.1.2 ポータルに戻る|ja_jp-26.1.2-Home]]
