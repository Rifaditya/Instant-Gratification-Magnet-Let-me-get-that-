# 🧲 Magnet, Let me get that! — 公式 Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

**Magnet, Let me get that!**（`ig_magnet`）の公式技術・ゲームプレイ Wiki へようこそ。本作はモダン Minecraft（Fabric）向けに設計された、プレイヤー自身がアイテムおよび経験値を引き寄せる軽量バキューム MOD です。

**Instant Gratification（IG）** の設計思想に基づいて構築された本 MOD は、「屈辱の歩行（Walk of Shame）」——採掘した鉱石や倒した Mob のドロップアイテムを拾うためだけに数ブロック歩かされるという無駄な摩擦を完全に排除します。「見えているなら、その手の中にあるべきだ」という理念を具現化しています。

---

## 🧭 マルチバージョン・スイッチボードポータル

お使いの Minecraft バージョンを選択して、専用のゲームプレイガイド、技術ドキュメント、GameRules 一覧表、アーキテクチャリファレンスを参照してください：

| Minecraft バージョン | リリースステータス | アクティブビルド | 設定エンジン | ポータルリンク |
| :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | 🟢 アクティブ・モダン | `1.3.9+26.2` | YACL v3 + ModMenu | [[26.2 概要|ja_jp-26.2-Home]] |
| **Minecraft 26.1.2** | 🟢 モダン・アンカー | `1.1.2+26.1.2` | Cloth Config + ModMenu | [[26.1.2 概要|ja_jp-26.1.2-Home]] |

### 🚀 バージョン別ダイレクトポータル:
* 📦 **Minecraft 26.2**: [[👉 Minecraft 26.2 ドキュメントポータルへ|ja_jp-26.2-Home]]
* 📦 **Minecraft 26.1.2**: [[👉 Minecraft 26.1.2 ドキュメントポータルへ|ja_jp-26.1.2-Home]]

ツールチェーン、依存関係マトリックス、アーカイブ保管場所、下位互換性の詳細については、[[バージョン互換性マトリックス|ja_jp-Version-Compatibility]] を参照してください。

---

## ⚡ 主要機能マトリックス

```
                      +-----------------------------+
                      |   PLAYER VACUUM EMITTER     |
                      +--------------+--------------+
                                     |
              +----------------------+----------------------+
              |                                             |
              v                                             v
  +-----------------------+                     +-----------------------+
  |  STANDARD PULL MODE   |                     |  INSTANT PICKUP MODE  |
  +-----------+-----------+                     +-----------+-----------+
              |                                             |
     [Line-of-Sight Check]                         [AABB Box Inflation]
     [Spherical Raycast 360°]                      [Zero Travel Latency]
     [Phase-Shift NoClip]                          [Direct Inventory]
     [Dynamic Lerp Velocity]                                |
              |                                             |
              +----------------------+----------------------+
                                     |
                                     v
                      +-----------------------------+
                      |   ITEM & XP ORB CAPTURED    |
                      +-----------------------------+
```

* **高機能 360° バキューム**: 設定可能なブロック半径（デフォルト: 12ブロック、最大64）内のドロップアイテムおよび経験値オーブを引き寄せます。
* **位相シフト (NoClip)**: 引き寄せ中のアイテムは固体の壁をスムーズにすり抜け、爆発の瓦礫や採掘の隙間にアイテムが挟まって回収不能になるのを防ぎます。
* **視線判定 (LOS) 認識**: DasikLibrary の `PlayerVisionTracker` による 360° 全方位レイトレーシングに加え、透明ブロック（ガラス）、植物（背の高い草・葉）、ブロックエンティティ（チェスト）に対するきめ細かな透過・遮蔽フィルタリングに対応。
* **慣性継続 (`keepMovingIfUnseen`)**: 一度視線内で引き寄せが開始されたアイテムは、途中で一時的に障害物の影に隠れても引き寄せの勢いを維持します。
* **インスタント回収オプション**: プレイヤー固有の回収バウンディングボックス（AABB）を拡張し、移動ラグゼロでアイテムをインベントリに即時吸収します。
* **キーバインドとコマンドによる操作**: クライアント側のホットキー（26.2 では `\`、26.1.2 では `Ctrl+M`）またはサーバー側の `/magnet toggle` コマンドで簡単に磁力機能を切り替え可能。
* **インベントリの圧迫ゼロ**: 100% プレイヤー組み込み機能であり、専用の磁石アイテム、アクセサリー、バッテリー等のスロット消費は一切不要です。

---

## 📚 百科全書的ナビゲーション

### 🎮 プレイヤー＆管理者向けガイド
* [[MC 26.2 概要|ja_jp-26.2-Home]] & [[MC 26.1.2 概要|ja_jp-26.1.2-Home]]
* [[MC 26.2 バキューム移動と位相シフト|ja_jp-26.2-Vacuum-and-Phase-Shifting]] & [[MC 26.1.2 バキューム移動と位相シフト|ja_jp-26.1.2-Vacuum-and-Phase-Shifting]]
* [[MC 26.2 視線判定と障害物|ja_jp-26.2-Line-of-Sight-and-Obstruction]] & [[MC 26.1.2 視線判定と障害物|ja_jp-26.1.2-Line-of-Sight-and-Obstruction]]
* [[MC 26.2 経験値オーブの引き寄せ|ja_jp-26.2-Experience-Orb-Attraction]] & [[MC 26.1.2 経験値オーブの引き寄せ|ja_jp-26.1.2-Experience-Orb-Attraction]]
* [[MC 26.2 インスタント回収モード|ja_jp-26.2-Instant-Pickup-Mode]] & [[MC 26.1.2 インスタント回収モード|ja_jp-26.1.2-Instant-Pickup-Mode]]
* [[MC 26.2 プレイヤートグルと状態永続化|ja_jp-26.2-Player-Toggle-and-Persistence]] & [[MC 26.1.2 プレイヤートグルとセッション状態|ja_jp-26.1.2-Player-Toggle-and-Persistence]]
* [[MC 26.2 GameRules リファレンス|ja_jp-26.2-GameRules]] & [[MC 26.1.2 GameRules リファレンス|ja_jp-26.1.2-GameRules]]
* [[MC 26.2 Brigadier コマンド|ja_jp-26.2-Commands]] & [[MC 26.1.2 サーバーコマンド|ja_jp-26.1.2-Commands]]
* [[MC 26.2 進捗ツリー|ja_jp-26.2-Advancements]] & [[MC 26.1.2 進捗ツリー|ja_jp-26.1.2-Advancements]]
* [[MC 26.2 YACL 設定画面|ja_jp-26.2-Configuration]] & [[MC 26.1.2 Cloth Config 設定画面|ja_jp-26.1.2-Configuration]]
* [[MC 26.2 HUD と診断システム|ja_jp-26.2-HUD-and-Diagnostics]] & [[MC 26.1.2 HUD とオーバーレイ表示|ja_jp-26.1.2-HUD-and-Diagnostics]]

### 💻 開発者＆コントリビューター向けドキュメント
* [[MC 26.2 開発環境セットアップと Loom ビルド|ja_jp-26.2-Developer-Setup-and-Building]] & [[MC 26.1.2 開発環境セットアップと Loom ビルド|ja_jp-26.1.2-Developer-Setup-and-Building]]
* [[MC 26.2 アーキテクチャと Mixin ターゲット|ja_jp-26.2-Architecture-and-Mixins]] & [[MC 26.1.2 アーキテクチャと Mixin ターゲット|ja_jp-26.1.2-Architecture-and-Mixins]]
* [[MC 26.2 API とアドオン連携|ja_jp-26.2-API-and-Addon-Integration]] & [[MC 26.1.2 API とアドオン連携|ja_jp-26.1.2-API-and-Addon-Integration]]
* [[マルチバージョン互換性マトリックス|ja_jp-Version-Compatibility]]

---

## ⚖️ ライセンスと帰属

本 MOD は **GNU General Public License v3.0 (GPLv3)** の下で **Dasik (Rifaditya)** により開発されています。利用条件および法的許諾の詳細は `LICENSE` ファイルを参照してください。
