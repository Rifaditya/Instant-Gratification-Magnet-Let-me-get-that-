# ⚙️ GameRules 完全リファレンス (MC 26.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| カテゴリインフォボックス | 詳細情報 |
| :--- | :--- |
| **カテゴリ ID** | `magnet:magnet_category` |
| **ローカライズ名** | `Magnet, Let me get that!` (`gamerule.category.magnet.magnet_category`) |
| **登録マネージャー** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **レジストリクラス** | `net.instantgratification.magnet.registry.ModGameRules` |
| **登録ルール総数** | `15` 個の名前空間付きルール |

---

## 📖 ゲーム内ゲームルール管理

**Magnet, Let me get that!** のすべてのグローバルメカニクスは、`magnet:magnet_category` ヘッダーの下に登録された名前空間付き Minecraft ゲームルール（GameRules）によって制御されます。

管理者は `/gamerule <rule> <value>` コマンドを使用するか、ワールド作成・編集時のバニラ「ゲームルールの編集」画面を通じてゲーム内でリアルタイムに設定を変更できます。

```
/gamerule ig:magnet_enabled true
/gamerule ig:magnet_range 24
/gamerule ig:magnet_speed 120
/gamerule ig:magnet_instant true
```

---

## 📋 ゲームルール完全一覧表

| ゲームルール識別子 | 型 | デフォルト | 有効範囲 | 表示名 | 説明およびゲームプレイ効果 |
| :--- | :---: | :---: | :---: | :--- | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | **Magnet Enabled** | アイテム吸引システム全体をグローバルに有効化または無効化するマスタースイッチ。 |
| `ig:magnet_range` | Integer | `12` | `1..64` | **Magnet Range** | プレイヤーがドロップアイテムを引き寄せる球状ブロック半径。 |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | **Magnet Noclip** | 位相シフトを有効化し、引き寄せられたアイテムが固体のブロックを自由にすり抜けられるようにする。 |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | **Attract XP Orbs** | ドロップアイテムと同時に経験値オーブも引き寄せるかどうか。 |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | **Magnet Particles** | 引き寄せ中のエンティティの飛行軌道上にエレクトリックスパークのパーティクルを生成。 |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | **Particle Count** | パーティクルティックごとにアクティブなソースから放出される火花粒子の数。 |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | **Max Particle Sources** | FPS 低下を防ぐため、同時にパーティクル放出を許可する最大エンティティ数。 |
| `ig:magnet_speed` | Integer | `80` | `1..1000` | **Item Speed** | 終端速度のパーセンテージ ($80 = 0.8\text{ blocks/tick} = 16.0\text{ m/s}$)。 |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000` | **Item Acceleration** | 1ティックあたりの加速度補間係数 ($10 = 10\%\text{ lerp/tick}$)。 |
| `ig:magnet_instant` | Boolean | `false` | `true/false` | **Instant Pickup** | true の場合、飛行時間をゼロにして AABB ボックス拡張によりアイテムをインベントリへ即時テレポート。 |
| `ig:magnet_los_only` | Boolean | `true` | `true/false` | **Line of Sight Only** | 視線（LOS）の可視性を必須とし、透過不能な障壁の後ろにあるアイテムの引き寄せを防止。 |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | `true/false` | **Keep Moving if Unseen** | 視線内で引き寄せが始まったアイテムについて、飛行中に視線が切れても引き寄せの勢いを維持。 |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | `true/false` | **Blocked by Transparent** | true の場合、ガラス、板ガラス、鉄格子、半透明ブロックが視線を遮蔽。 |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | `true/false` | **Blocked by Flora** | true の場合、背の高い草、作物、花、葉が視線を遮蔽。 |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | `true/false` | **Blocked by Block Entities** | true の場合、チェスト、ベッド、樽、シュルカーボックス等のブロックエンティティが視線を遮蔽。 |

---

## ⚖️ グローバル JSON 設定に対する GameRules の優先順位

> ⚠️ **サーバー管理者への重要事項**:  
> クライアント/サーバーの JSON 設定ファイル（`config/ig_magnet.json`）での設定は、**新規作成されたワールドの初期基準値（デフォルト値）**を定義するのみです。稼働中のワールドは常に GameRules によって厳格に制御されます。すでに存在するワールドにおいて `ig_magnet.json` を変更しても、`/gamerule` で更新されない限りゲームプレイ値は変更されません。

---

## 🔗 関連 Wiki ドキュメント
* [[バキューム物理と位相シフト|ja_jp-26.2-Vacuum-and-Phase-Shifting]]
* [[視線判定と障害物貫通|ja_jp-26.2-Line-of-Sight-and-Obstruction]]
* [[設定 GUI と JSON 同期|ja_jp-26.2-Configuration]]
* [[MC 26.2 ポータルに戻る|ja_jp-26.2-Home]]
