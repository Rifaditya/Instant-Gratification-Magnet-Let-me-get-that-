# 👁️ 視線判定と障害物メカニクス (MC 26.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 機能インフォボックス | 技術パラメータ |
| :--- | :--- |
| **プライマリ視線エンジン** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **セカンダリ視線エンジン** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **球状視野角 (FOV)** | $360.0^\circ$ (完全な全方位知覚) |
| **接触許容距離** | $0.3\text{ m}$ ターゲット接触閾値 |
| **メモリ保持タグ** | `ig_magnet$isMagnetized()` / `ig_magnet$setMagnetized()` |
| **慣性保持ルール** | `ig:magnet_keep_moving_if_unseen = true` |
| **詳細フィルター設定** | 透明ブロック、植物、ブロックエンティティ |

---

## 📖 デュアルパス視線アーキテクチャ

洞窟の壁越しや防護された拠点内部からの不当なアイテム取得を防ぎつつ、ラグのないパフォーマンスを維持するため、**Magnet, Let me get that!** は高速な **デュアルパス視線パイプライン** を採用しています：

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

プライマリパスでは、DasikLibrary の最適化されたレイトレーシングエンジンを呼び出します：
```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **全方位 FOV**: $360.0^\circ$ の全方位コーンにより、固体の壁に遮られていない限り、頭上、足元、背後にあるアイテムであってもカメラを向けることなく引き寄せることができます。
* **0.3m の接触許容値**: アイテムがブロックの角にぴったり密着している場合でも、$0.3\text{m}$ 半径のサブボクセルレイキャストにより誤判定による除外を防ぎます。

---

## 🌿 パス 2: 詳細ブロック状態フィルター (`SecondaryVisionCheck`)

プライマリパスを通過した場合、MOD は `BlockGetter.traverseBlocks` を介して追加の詳細遮蔽ルールを評価します：

```java
public static boolean canSee(Player player, Entity target, boolean blockTransparent, boolean blockFlora, boolean blockEntities)
```

1. **植物・葉の遮蔽判定 (`ig:magnet_blocked_by_flora`)**:
   - 通過したブロックが `BushBlock`（背の高い草、花、作物、苗木）または `LeavesBlock`（木の葉）のインスタンスであるかを確認します。
   - 有効（`true`）の場合、植物はアイテム引き寄せを遮る不透明な壁として機能します。
2. **インタラクティブなブロックエンティティ (`ig:magnet_blocked_by_block_entities`)**:
   - 通過位置全体で `state.hasBlockEntity()` を確認します。
   - 有効（`true`）の場合、チェスト、トラップチェスト、樽、シュルカーボックス、ベッド、ディスペンサーが視線を遮蔽します。
3. **透明・非フルブロック (`ig:magnet_blocked_by_transparent`)**:
   - `state.getVisualShape(...)` に対してレイキャストを実行します。
   - 有効（`true`）の場合、ガラス、板ガラス、鉄格子、フェンス、ハーフブロック、階段がアイテム回収を遮断します。

---

## 🚀 慣性継続 (`keepMovingIfUnseen`)

激しい採掘や戦闘中、角を曲がって引き寄せられたアイテムは一時的に視線から外れることがよくあります。その瞬間にアイテムが停止したり溶岩に落ちたりするのを防ぐため：

1. **磁力タグ付与**: アイテムが視線内で視認された際、`((IMagnetEntity) entity).ig_magnet$setMagnetized()` によりメモリ内ブーリアンフラグが設定されます。
2. **慣性の維持**: 後続のティックでアイテムが視線外に出た場合：
   - `ig:magnet_keep_moving_if_unseen = true` かつ `ig_magnet$isMagnetized() == true` の場合: アイテムはプレイヤーへの引き寄せを維持します。
   - `ig:magnet_keep_moving_if_unseen = false` の場合: 視線を失った瞬間に引き寄せが停止します。
   - アイテムが**一度も視認されていない**場合（例: 爆発やディスペンサーによって壁の裏側にスポーンした等）: 引き寄せは即座に拒否されます。

---

## ⚙️ 関連設定とゲームルール

| GameRule | 型 | デフォルト | 説明 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | アイテムを引き寄せるために視線（LOS）の可視性を要求。 |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | 飛行中に視線が途切れても、すでに磁力化されたアイテムの引き寄せを継続。 |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | true の場合、ガラスや半透明ブロックが視線を遮蔽。 |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | true の場合、草、葉、花が視線を遮蔽。 |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | true の場合、チェスト、ベッド等のブロックエンティティが視線を遮蔽。 |

---

## 🔗 関連 Wiki ドキュメント
* [[バキューム物理と位相シフト|ja_jp-26.2-Vacuum-and-Phase-Shifting]]
* [[GameRules 完全リファレンス|ja_jp-26.2-GameRules]]
* [[アーキテクチャと Mixin|ja_jp-26.2-Architecture-and-Mixins]]
* [[MC 26.2 ポータルに戻る|ja_jp-26.2-Home]]
