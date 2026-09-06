# 🗺️ マルチバージョン互換性マトリックス

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

このページでは、**Magnet, Let me get that!**（`ig_magnet`）がサポートする Minecraft リリースバージョン、Java 実行環境、Fabric Loader 依存関係、およびビルドツールチェーンについて解説します。

---

## 📊 マルチバージョン・ライフサイクル概要

| 対象 Minecraft | サブプロジェクトフォルダ | アクティブ MOD バージョン | Java ターゲット | Fabric Loader | Fabric API | DasikLibrary | 設定 GUI プロバイダー |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | `Magnet v26.2/magnet` | `1.3.9+26.2` | Java 25 (`release = 25`) | `>=0.19.1` | `0.150.1+26.2` | `1.8.23` | YetAnotherConfigLib v3 (`3.9.5+26.2-fabric`) + ModMenu |
| **Minecraft 26.1.2** | `Magnet v26.1/magnet` | `1.1.2+26.1.2` | Java 25 (`release = 25`) | `>=0.16.10` | `0.145.4+26.1.2` | `1.8.23` | Cloth Config Fabric (`26.1.154`) + ModMenu |

---

## 🔍 バージョン別詳細仕様

### 🟢 Minecraft 26.2 (`1.3.9+26.2`)
* **ステータス**: 主要モダンリリース (Primary Modern Release)
* **サブプロジェクトパス**: `Magnet v26.2/magnet/`
* **アーカイブ出力**: `Magnet-Let-me-get-that-1.3.9+26.2.jar`
* **中央アーカイブディレクトリ**: `Archive Jar of all versions/MC 26.2/`
* **依存関係の境界 (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.19.1",
      "minecraft": ">=26.2-",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **主要なアーキテクチャ機能**:
  * `PlayerMixin` 内で Minecraft 26.2 の `ValueOutput` および `ValueInput` コーデックを使用した永続的な NBT ストレージ。
  * Fabric ライフサイクルの `ServerPlayerEvents.COPY_FROM` および `ServerPlayerEvents.AFTER_RESPAWN` による、プレイヤーの死亡時およびディメンション間テレポート時の状態自動保持。
  * 動的 `ig_magnet$getKeyboardType()` フォールバックを備えた `\`（`GLFW_KEY_BACKSLASH`）キーバインドトグル。
  * 専用ファイルログ（`logs/ig_magnet_debug.log`）を備えたゲーム内 `/magnet debug` および `/magnet debug log` 診断コマンド。
  * YetAnotherConfigLib v3 を活用したモダンな `YaclScreenHelper`。

---

### 🟢 Minecraft 26.1.2 (`1.1.2+26.1.2`)
* **ステータス**: モダンアンカー・サブプロジェクト (Modern Anchor Subproject)
* **サブプロジェクトパス**: `Magnet v26.1/magnet/`
* **アーカイブ出力**: `Magnet-Let-me-get-that-1.1.2+26.1.2.jar`
* **中央アーカイブディレクトリ**: `Archive Jar of all versions/MC 26.1.2/`
* **依存関係の境界 (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.16.10",
      "minecraft": "*",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **主要なアーキテクチャ機能**:
  * `MagnetPlayerState`（`Map<UUID, Boolean>`）によるスレッドセーフな同時セッション状態追跡。
  * `Ctrl+M`（`GLFW_KEY_M` + `isControlDown()`）に割り当てられたキーバインドトグル。
  * `client.gui.setOverlayMessage(...)` を介したネイティブアクションバーオーバーレイフィードバック。
  * `ClothConfigScreenHelper` によるリフレクションセーフなオプショナル設定 GUI。

---

## 📦 自動リリースアーカイブとランチャー展開

両サブプロジェクトともに、Gradle ビルドスクリプト（`build.gradle`）内で自動コンパイル後アーカイブ処理を統合しています：

```bash
# MC 26.2 ビルドのコンパイルと自動アーカイブ:
cd "Magnet v26.2/magnet"
./gradlew build --no-daemon

# MC 26.1.2 ビルドのコンパイルと自動アーカイブ:
cd "Magnet v26.1/magnet"
./gradlew build --no-daemon
```

`./gradlew build` が実行されると、`archiveReleaseJar` タスクがビルドされた JAR を中央アーカイブフォルダ（`Archive Jar of all versions/MC <Version>/`）へ自動コピーし、アクティブな Modrinth ローカルランチャーテストプロファイルと同期します。

---

## 🔗 関連 Wiki ドキュメント
* [[MC 26.2 開発環境セットアップとビルド|ja_jp-26.2-Developer-Setup-and-Building]]
* [[MC 26.1.2 開発環境セットアップとビルド|ja_jp-26.1.2-Developer-Setup-and-Building]]
* [[中央スイッチボードポータルに戻る|ja_jp-Home]]
