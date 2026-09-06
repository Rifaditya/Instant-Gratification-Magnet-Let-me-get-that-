# 👁️ 視線判定と障害物メカニクス (MC 26.1.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 機能インフォボックス | 技術パラメータ |
| :--- | :--- |
| **プライマリ視線エンジン** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **セカンダリ視線エンジン** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **球状視野角 (FOV)** | $360.0^\circ$ (完全な全方位知覚) |
| **接触許容距離** | $0.3\text{ m}$ ターゲット接触閾値 |
| **メモリ保持タグ** | `ig$isMagnetized()` / `ig$setMagnetized()` |
| **慣性保持ルール** | `ig:magnet_keep_moving_if_unseen = true` |
| **コンテキスト実装** | `VisionContext` static record |

---

## 📖 デュアルパス視線パイプライン

Minecraft 26.1.2 では、メモリ割り当てゼロ（allocation-free）の **デュアルパス視線パイプライン** を通じて視線判定が行われます：

```
                                +---------------------------+
                                |    TARGET ITEM DETECTED   |
                                +-------------+-------------+
                                              |
                                              v
                              +-------------------------------+
                              |    PRIMARY PASS (360° LOS)    |
                              |   PlayerVisionTracker.canSee  |
                              +---------------+---------------+
                                              |
                        +---------------------+---------------------+
                        |                                           |
                   [VISIBLE]                                   [OBSTRUCTED]
                        |                                           |
                        v                                           v
      +-----------------------------------+               +--------------------+
      |     SECONDARY PASS (GRANULAR)     |               |   MOMENTUM CHECK   |
      |     SecondaryVisionCheck.canSee   |               | keepMovingIfUnseen |
      +-----------------+-----------------+               +---------+----------+
                        |                                           |
            +-----------+-----------+                     +---------+---------+
            |                       |                     |                   |
        [PASS]                   [BLOCKED]            [MAGNETIZED]      [UNMAGNETIZED]
            |                       |                     |                   |
            v                       v                     v                   v
    +---------------+       +---------------+     +---------------+   +---------------+
    |  PULL ITEM &  |       | REJECT / STOP |     | CONTINUE PULL |   | REJECT PULL   |
    | SET MAGNETIZED|       +---------------+     +---------------+   +---------------+
    +---------------+
```

---

## 🔍 パス 1: プライマリ 360° 球状視線判定

```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **全方位視野**: $360^\circ$ の全方位 FOV 角度により、プレイヤーの頭上、足元、背後にあるドロップアイテムもカメラを向けることなくスムーズに引き寄せられます。
* **サブボクセル接触マージン**: $0.3\text{m}$ のマージンにより、アイテムが固体の壁に密着している場合の誤判定による除外を防ぎます。

---

## 🌿 パス 2: 詳細ブロック走査 (`SecondaryVisionCheck`)

```java
private record VisionContext(
        Level level,
        boolean blockTransparent,
        boolean blockFlora,
        boolean blockEntities,
        CollisionContext collisionContext,
        Vec3 start,
        Vec3 end
) {}
```

* **植物 (`ig:magnet_blocked_by_flora`)**: `BushBlock` および `LeavesBlock` の存在を確認します。
* **ブロックエンティティ (`ig:magnet_blocked_by_block_entities`)**: `state.hasBlockEntity()`（チェスト、シュルカーボックス、ベッド等）を確認します。
* **透明ブロック (`ig:magnet_blocked_by_transparent`)**: ガラス、板ガラス、ハーフブロック等に対して `state.getVisualShape().clip(...)` を検証します。

---

## 🚀 慣性継続 (`keepMovingIfUnseen`)

* 視線内で初めて視認された際、`((IMagnetEntity) entity).ig$setMagnetized()` によりエンティティにフラグが付与されます。
* `ig:magnet_keep_moving_if_unseen = true` の場合、過去に一度でも磁力化されていれば、障害物の角を曲がって視線が途切れてもアイテムの引き寄せが継続されます。

---

## ⚙️ 関連設定とゲームルール

| GameRule | 型 | デフォルト | 説明 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | アイテムを引き寄せるために視線（LOS）を要求。 |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | 飛行中に視線が途切れても、磁力化済みアイテムの引き寄せを継続。 |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | true の場合、ガラスや透明ブロックが視線を遮蔽。 |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | true の場合、草や花が視線を遮蔽。 |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | true の場合、チェストやブロックエンティティが視線を遮蔽。 |

---

## 🔗 関連 Wiki ドキュメント
* [[バキューム物理と位相シフト|ja_jp-26.1.2-Vacuum-and-Phase-Shifting]]
* [[GameRules 完全リファレンス|ja_jp-26.1.2-GameRules]]
* [[MC 26.1.2 ポータルに戻る|ja_jp-26.1.2-Home]]
