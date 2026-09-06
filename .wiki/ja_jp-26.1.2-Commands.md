# 💻 サーバーコマンドとバニラクライアント対応 (MC 26.1.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 機能インフォボックス | 技術パラメータ |
| :--- | :--- |
| **コマンドクラス** | `net.instantgratification.magnet.MagnetCommand` |
| **プライマリリテラル** | `/magnet` および `/ig_magnet` (同等のエイリアス) |
| **サブコマンド** | `toggle` |
| **ネットワーク同期** | `ServerPlayNetworking.send(player, new MagnetTogglePayload(newState))` |

---

## 📖 コマンドリファレンス

### `/magnet toggle`（または `/ig_magnet toggle`）
実行したプレイヤーのアイテム磁石状態のオン/オフを切り替えます。

* **コマンド構文**: `/magnet toggle`
* **実行ロジック**:
  ```java
  private static int toggleMagnet(CommandSourceStack source) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      boolean newState = MagnetPlayerState.toggleMagnet(player);

      if (newState) {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.enabled"), false);
      } else {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.disabled"), false);
      }

      if (ServerPlayNetworking.canSend(player, MagnetTogglePayload.TYPE)) {
          ServerPlayNetworking.send(player, new MagnetTogglePayload(newState));
      }
      return 1;
  }
  ```
* **バニラクライアント互換性**: クライアント側に MOD を導入していないプレイヤーが Fabric サーバーに接続している場合でも、チャットコマンドから自身の磁力を問題なく切り替えることができます。

---

## 🔗 関連 Wiki ドキュメント
* [[プレイヤートグルと状態管理|ja_jp-26.1.2-Player-Toggle-and-Persistence]]
* [[GameRules 完全リファレンス|ja_jp-26.1.2-GameRules]]
* [[MC 26.1.2 ポータルに戻る|ja_jp-26.1.2-Home]]
