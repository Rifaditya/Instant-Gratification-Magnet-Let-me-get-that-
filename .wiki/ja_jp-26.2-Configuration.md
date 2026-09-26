# 🎨 YACL 設定画面と ModMenu (MC 26.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 設定インフォボックス | 詳細情報 |
| :--- | :--- |
| **設定ファイルパス** | `config/ig_magnet.json` |
| **GUI ライブラリ** | YetAnotherConfigLib v3 (`dev.isxander:yet-another-config-lib`) |
| **ModMenu エントリポイント** | `net.instantgratification.magnet.config.ModMenuIntegration` |
| **GUI 画面ヘルパー** | `net.instantgratification.magnet.config.YaclScreenHelper` |
| **クラスロード安全性** | `GuiHelper.getOptionalFactory` により完全隔離 |

---

## 📖 設定システムのアーキテクチャ

**Magnet, Let me get that!** は、**YetAnotherConfigLib v3 (YACL)** を採用し、**ModMenu** からアクセス可能なクライアント側グラフィカル設定画面を提供しています。

専任サーバー（Dedicated Server）環境においてクライアント側の GUI クラスが読み込まれてクラッシュするのを防ぐため、GUI ファクトリは **リフレクションセーフなクラスローディング隔離** を通じて呼び出されます：

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
                "ig_magnet",
                "net.instantgratification.magnet.config.YaclScreenHelper",
                "createScreen"
        );
    }
}
```

---

## ⚠️ 設定の優先順位に関する警告

> ⚠️ **重要なお知らせ**:  
> ModMenu の設定画面または `config/ig_magnet.json` で変更された設定内容は、**新しく作成されるワールドの初期値（デフォルト設定）にのみ反映**されます。  
> すでに作成され稼働しているワールドの設定を変更する場合は、`/gamerule` コマンドまたはバニラの GameRules 編集画面からゲーム内の [[GameRules リファレンス|ja_jp-26.2-GameRules]] を変更してください。

---

## 🗂️ 設定カテゴリとオプション構成

```
YACL 設定画面 ("Magnet, Let me get that! Configuration")
  ├── 一般設定 (General Settings)
  │     ├── Magnet Enabled (デフォルト: true)
  │     ├── Magnet Range (デフォルト: 12, 範囲: 1..64)
  │     ├── Instant Pickup (デフォルト: false)
  │     └── Magnet Noclip (デフォルト: true)
  ├── 速度と引き寄せ挙動 (Speeds & Pull Heuristics)
  │     ├── Item Speed (デフォルト: 80%, 範囲: 1..1000)
  │     └── Item Acceleration (デフォルト: 10%, 範囲: 1..1000)
  ├── 視線判定 (Line of Sight)
  │     ├── Line of Sight Only (デフォルト: true)
  │     ├── Keep Moving if Unseen (デフォルト: true)
  │     ├── Blocked by Transparent (デフォルト: false)
  │     ├── Blocked by Flora (デフォルト: false)
  │     └── Blocked by Block Entities (デフォルト: false)
  └── 視覚効果とパフォーマンス (Visuals & Performance)
        ├── Attract XP Orbs (デフォルト: true)
        ├── Magnet Particles (デフォルト: true)
        ├── Particle Count (デフォルト: 1, 範囲: 0..100)
        └── Max Particle Sources (デフォルト: 5, 範囲: 0..100)
```

---

## 📄 生 JSON 構造 (`config/ig_magnet.json`)

```json
{
  "configVersion": 1,
  "enabled": true,
  "range": 12,
  "noClip": true,
  "affectsXp": true,
  "particles": true,
  "particleCount": 1,
  "maxParticleSources": 5,
  "speed": 80,
  "acceleration": 10,
  "instant": false,
  "losOnly": true,
  "keepMovingIfUnseen": true,
  "blockedByTransparent": false,
  "blockedByFlora": false,
  "blockedByBlockEntities": false
}
```

---

## 🔗 関連 Wiki ドキュメント
* [[GameRules 完全リファレンス|ja_jp-26.2-GameRules]]
* [[開発環境セットアップとビルド|ja_jp-26.2-Developer-Setup-and-Building]]
* [[MC 26.2 ポータルに戻る|ja_jp-26.2-Home]]
