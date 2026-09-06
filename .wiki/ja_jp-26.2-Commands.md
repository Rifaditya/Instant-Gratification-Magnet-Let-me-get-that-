# 💻 Brigadier コマンドとゲーム内診断 (MC 26.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 機能インフォボックス | 技術パラメータ |
| :--- | :--- |
| **コマンドクラス** | `net.instantgratification.magnet.MagnetCommand` |
| **プライマリリテラル** | `/magnet` および `/ig_magnet` (同等のエイリアス) |
| **登録コールバック** | `CommandRegistrationCallback.EVENT` |
| **ログ出力ファイル** | `logs/ig_magnet_debug.log` |
| **対象権限レベル** | 全プレイヤー利用可能 (`toggle`) / OP レベル 2 (`debug`) |

---

## 📖 コマンドツリー構造

```
/magnet (または /ig_magnet)
  ├── toggle
  └── debug
        └── log
```

---

## 🛠️ サブコマンドリファレンス

### 1. `/magnet toggle`（または `/ig_magnet toggle`）
実行したプレイヤー個人の磁石機能のオン/オフを切り替えます。

* **使用構文**: `/magnet toggle`
* **実行処理**: `((IMagnetPlayer) player).ig_magnet$toggleMagnet()` を呼び出します。
* **出力フィードバック**:
  - 有効時: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
  - 無効時: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)
* **ユースケース**: クライアント MOD が導入されていないバニラクライアントからサーバーへ接続しているプレイヤーや、キーバインドが設定できない環境のプレイヤーが磁力を切り替える際に使用します。

---

### 2. `/magnet debug`（または `/ig_magnet debug`）
実行プレイヤーおよび周囲 10 ブロック以内のエンティティに対して、即座にワールド内診断スイープを実行します。

* **使用構文**: `/magnet debug`
* **出力情報例**:
  ```text
  §e--- Magnet Debug Info ---
  Player Name: Steve
  Player UUID: c0b59055-ec50-4653-979d-c3c92d6ca897
  Global Master Toggle: true
  Player Toggle State: true
  Range: 12
  Instant Pickup: false
  LOS Only: true
  Spectator: false
  Dead/Dying: false
  Debug File Logger: DISABLED (Toggle with /magnet debug log)
  Nearby Items (10 blocks): 3
  Nearest Item ID: 412
  PlayerVisionTracker.canSee: true
  Vanilla Raycast canSee: true
  §e-------------------------
  ```
* **ユースケース**: なぜアイテムが引き寄せられないのか（厳格な視線遮蔽、プレイヤーの死亡状態、グローバルな GameRule オーバーライドなど）を原因究明する際に活用します。

---

### 3. `/magnet debug log`（または `/ig_magnet debug log`）
ディスクへの詳細な診断ログ出力を有効または無効にします。

* **使用構文**: `/magnet debug log`
* **実行処理**: `MagnetDebugLogger.enabled = !MagnetDebugLogger.enabled` を反転させます。
* **出力先**: タイムスタンプ付きのティックイベント、パケット受信、リスポーンハンドシェイク、視線遮蔽判定拒否のログが `logs/ig_magnet_debug.log` に書き込まれます。
* **ログファイル出力例**:
  ```log
  [2026-08-22 21:49:15.102] MagnetMod: Server received packet from Steve (c0b59055-...) enabled=true
  [2026-08-22 21:49:15.105] MagnetMovement: Entity 412 is now magnetized by Steve (c0b59055-...).
  ```

---

## 🔗 関連 Wiki ドキュメント
* [[プレイヤートグル・永続化・ライフサイクル|ja_jp-26.2-Player-Toggle-and-Persistence]]
* [[HUD と診断システム|ja_jp-26.2-HUD-and-Diagnostics]]
* [[GameRules 完全リファレンス|ja_jp-26.2-GameRules]]
* [[MC 26.2 ポータルに戻る|ja_jp-26.2-Home]]
