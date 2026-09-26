# 🧲 Magnet, Let me get that! — Minecraft 26.2 ポータル

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

**Magnet, Let me get that!**（ビルド `1.3.9+26.2`）の **Minecraft 26.2** ドキュメントポータルへようこそ。

このモダンエディションでは、NBT による永続的な状態保持、Fabric ライフサイクルによるリスポーン同期、DasikLibrary 1.8.23 による 360° 球状レイトレーシング、YetAnotherConfigLib v3 (YACL) GUI 設定画面が導入されています。

---

## 📋 Minecraft 26.2 クイックスペック

| 仕様項目 | ターゲット値 | 参照識別子 |
| :--- | :--- | :--- |
| **Minecraft 対象リリース** | `26.2` | `"minecraft": ">=26.2-"` |
| **アクティブサブプロジェクトビルド** | `1.3.9+26.2` | `mod_version=1.3.9+26.2` |
| **Java ツールチェーン** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.19.1` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.150.1+26.2` | `fabric_version=0.150.1+26.2` |
| **コア共有ライブラリ** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **デフォルトクライアントキーバインド** | `\` (バックスラッシュ) | `GLFW.GLFW_KEY_BACKSLASH` |
| **設定 GUI** | YetAnotherConfigLib v3 | `YaclScreenHelper` + ModMenu |
| **診断コマンド** | `/magnet toggle`, `/magnet debug`, `/magnet debug log` | `MagnetCommand.java` |

---

## ⚡ 主要機能ハイライト

* **360° 球状視線判定**: 固体の壁を挟んだ不正なアイテム回収を防ぎつつ、あらゆる角度から完全な視認引き寄せを実現。[[視線判定と障害物|ja_jp-26.2-Line-of-Sight-and-Obstruction]] を参照。
* **位相シフト (NoClip) 移動**: 引き寄せられたアイテムはブロックをスムーズにすり抜け、引っかかることなくプレイヤーの目の高さまで到達します。[[バキューム移動と位相シフト|ja_jp-26.2-Vacuum-and-Phase-Shifting]] を参照。
* **遅延ゼロのインスタント回収**: オプションのバウンディングボックス拡張により、ドロップアイテムを移動遅延なしで即座にインベントリへ吸い込みます。[[インスタント回収モード|ja_jp-26.2-Instant-Pickup-Mode]] を参照。
* **プレイヤー状態の永続化**: NBT（`ValueOutput`/`ValueInput`）および `ServerPlayerEvents.COPY_FROM` により、死亡、リスポーン、ディメンション移動、サーバー再起動をまたいでトグル状態を完全維持。[[プレイヤートグルと状態永続化|ja_jp-26.2-Player-Toggle-and-Persistence]] を参照。
* **診断コマンドスイート**: サーバー管理者向けにゲーム内診断ツール `/magnet debug` および `/magnet debug log` を内蔵。[[Brigadier コマンド|ja_jp-26.2-Commands]] および [[HUD と診断システム|ja_jp-26.2-HUD-and-Diagnostics]] を参照。

---

## 📑 26.2 ドキュメント目次

### 🎮 ゲームプレイ＆サーバー管理
* [[バキューム物理と位相シフト|ja_jp-26.2-Vacuum-and-Phase-Shifting]]
* [[視線判定と障害物貫通|ja_jp-26.2-Line-of-Sight-and-Obstruction]]
* [[経験値オーブ同期|ja_jp-26.2-Experience-Orb-Attraction]]
* [[インスタント回収モードと AABB|ja_jp-26.2-Instant-Pickup-Mode]]
* [[プレイヤートグル・永続化・ライフサイクル|ja_jp-26.2-Player-Toggle-and-Persistence]]
* [[GameRules リファレンスと境界値|ja_jp-26.2-GameRules]]
* [[Brigadier コマンドとゲーム内診断|ja_jp-26.2-Commands]]
* [[進捗ツリーとバニラ依存性|ja_jp-26.2-Advancements]]
* [[YACL 設定画面と ModMenu|ja_jp-26.2-Configuration]]
* [[アクションバー HUD と専用ロギング|ja_jp-26.2-HUD-and-Diagnostics]]

### 💻 開発者＆エンジニアリングリファレンス
* [[開発環境セットアップ・ツールチェーン・Gradle Loom|ja_jp-26.2-Developer-Setup-and-Building]]
* [[アーキテクチャ・パッケージ・Mixin 注入|ja_jp-26.2-Architecture-and-Mixins]]
* [[API ファサード・インターフェース・アドオンフック|ja_jp-26.2-API-and-Addon-Integration]]
* [[マルチバージョン互換性マトリックスに戻る|ja_jp-Version-Compatibility]]
