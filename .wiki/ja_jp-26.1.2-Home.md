# 🧲 Magnet, Let me get that! — Minecraft 26.1.2 ポータル

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

**Magnet, Let me get that!**（ビルド `1.1.2+26.1.2`）の **Minecraft 26.1.2** ドキュメントポータルへようこそ。

このアンカーエディションは、Cloth Config との連携、スレッドセーフな同時セッション状態管理、および 360° 球状レイトレーシングを備え、完全な Instant Gratification アイテム・経験値バキューム機能を提供します。

---

## 📋 Minecraft 26.1.2 クイックスペック

| 仕様項目 | ターゲット値 | 参照識別子 |
| :--- | :--- | :--- |
| **Minecraft 対象リリース** | `26.1.2` | `"minecraft": "*"` |
| **アクティブサブプロジェクトビルド** | `1.1.2+26.1.2` | `mod_version=1.1.2+26.1.2` |
| **Java ツールチェーン** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.16.10` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` | `fabric_version=0.145.4+26.1.2` |
| **コア共有ライブラリ** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **デフォルトクライアントキーバインド** | `Ctrl+M` | `GLFW.GLFW_KEY_M` + `isControlDown()` |
| **設定 GUI** | Cloth Config Fabric | `ClothConfigScreenHelper` + ModMenu |
| **サーバーコマンド** | `/magnet toggle`, `/ig_magnet toggle` | `MagnetCommand.java` |

---

## ⚡ 主要機能ハイライト

* **360° 球状視線判定**: DasikLibrary 1.8.23 の `PlayerVisionTracker` を搭載。[[視線判定と障害物|ja_jp-26.1.2-Line-of-Sight-and-Obstruction]] を参照。
* **位相シフト (NoClip) 物理**: バキューム吸引中、アイテムは固体の地形ブロックをスムーズにすり抜けます。[[バキューム移動と位相シフト|ja_jp-26.1.2-Vacuum-and-Phase-Shifting]] を参照。
* **インスタント回収モード**: 遅延ゼロで即時回収するためのオプションのバウンディングボックス拡張。[[インスタント回収モード|ja_jp-26.1.2-Instant-Pickup-Mode]] を参照。
* **並行セッショントグル管理**: キーバインドおよびコマンドによる状態は `MagnetPlayerState` で管理されます。[[プレイヤートグルとセッション状態|ja_jp-26.1.2-Player-Toggle-and-Persistence]] を参照。
* **Cloth Config GUI**: カテゴリ別の注意メッセージを備えた分かりやすいゲーム内設定画面。[[Cloth Config 設定画面|ja_jp-26.1.2-Configuration]] を参照。

---

## 📑 26.1.2 ドキュメント目次

### 🎮 ゲームプレイ＆サーバー管理
* [[バキューム物理と位相シフト|ja_jp-26.1.2-Vacuum-and-Phase-Shifting]]
* [[視線判定と障害物貫通|ja_jp-26.1.2-Line-of-Sight-and-Obstruction]]
* [[経験値オーブ同期|ja_jp-26.1.2-Experience-Orb-Attraction]]
* [[インスタント回収モードとバウンディングボックス拡張|ja_jp-26.1.2-Instant-Pickup-Mode]]
* [[プレイヤートグル・ホットキー・状態保持|ja_jp-26.1.2-Player-Toggle-and-Persistence]]
* [[GameRules リファレンスとデフォルト境界値|ja_jp-26.1.2-GameRules]]
* [[サーバーコマンドとバニラクライアント対応|ja_jp-26.1.2-Commands]]
* [[進捗ツリーとバニラ依存性|ja_jp-26.1.2-Advancements]]
* [[Cloth Config 設定画面と ModMenu|ja_jp-26.1.2-Configuration]]
* [[アクションバー HUD と視覚効果|ja_jp-26.1.2-HUD-and-Diagnostics]]

### 💻 開発者＆エンジニアリングリファレンス
* [[開発環境セットアップ・ツールチェーン・Gradle Loom|ja_jp-26.1.2-Developer-Setup-and-Building]]
* [[アーキテクチャ・パッケージ・Mixin 注入|ja_jp-26.1.2-Architecture-and-Mixins]]
* [[API ファサード・インターフェース・アドオンフック|ja_jp-26.1.2-API-and-Addon-Integration]]
* [[マルチバージョン互換性マトリックスに戻る|ja_jp-Version-Compatibility]]
