# 🔄 プレイヤートグル・永続化・ライフサイクル (MC 26.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 機能インフォボックス | 技術パラメータ |
| :--- | :--- |
| **インターフェースブリッジ** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Mixin 対象** | `net.minecraft.world.entity.player.Player` (`PlayerMixin.java`) |
| **デフォルトクライアントキーバインド** | `\` (バックスラッシュ) — `key.ig_magnet.toggle` |
| **キーカテゴリー** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **ネットワークペイロード** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **NBT コーデックストレージ** | `ValueOutput` / `ValueInput`（タグ名 `"ig_magnet_enabled"`） |
| **ライフサイクルイベント** | `ServerPlayConnectionEvents.JOIN`, `ServerPlayerEvents.COPY_FROM`, `ServerPlayerEvents.AFTER_RESPAWN` |

---

## 📖 状態アーキテクチャの概要

マルチプレイヤーサーバーやMODパック環境では、建築家は装飾中にアイテムの引き寄せを一時停止したいと考え、採掘者は最大の吸引力を求めるといったように、プレイヤーごとに異なるニーズが存在します。

**Magnet, Let me get that!** では、ワールドの再読み込み、死亡、リスポーン、ディメンション間テレポートを経ても100%維持される **プレイヤーごとの個別トグル状態** を実装しています。

```
                                [CLIENT ACTION]
                     Player Presses Toggle Keybind ('\')
                                      |
                                      v
                         [LOCAL STATE UPDATED]
                     client.player -> isEnabled = !isEnabled
                     Actionbar Overlay: "Item Magnet: Enabled/Disabled"
                                      |
                                      v
                         [C2S PACKET TRANSMISSION]
                     ClientPlayNetworking.send(MagnetTogglePayload)
                                      |
                                      v
                               [SERVER RECEIVER]
                     context.server().execute(() -> {
                         ((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(val);
                     })
                                      |
         +----------------------------+----------------------------+
         |                                                         |
         v                                                         v
 [NBT DATA PERSISTED]                                  [LIFECYCLE HOOKS WIRED]
 ValueOutput.putBoolean("ig_magnet_enabled")           - JOIN: S2C Sync
 ValueInput.getBooleanOr("ig_magnet_enabled", true)    - COPY_FROM: Retain on Death
                                                       - AFTER_RESPAWN: Fresh Entity S2C Sync
```

---

## ⌨️ クライアントキーバインドとアクションバーオーバーレイ

* **デフォルトキー**: `GLFW_KEY_BACKSLASH`（`\`）。一般的なマップ MOD やインベントリ便利ツールとのキー競合を回避しています。
* **動的キータイプヘルパー**: `ig_magnet$getKeyboardType()` を使用し、各種 Fabric Loader スナップショット間で `InputConstants.Type.KEYBOARD` を安全に解決しつつ `KEYSYM` へのフォールバックを保証します。
* **即時フィードバック**: トグル切り替え時にローカライズされたアクションバーメッセージが表示されます：
  - `chat.ig_magnet.enabled`: `"Item Magnet: Enabled"`（アイテム磁石: 有効）
  - `chat.ig_magnet.disabled`: `"Item Magnet: Disabled"`（アイテム磁石: 無効）

---

## 💾 NBT ストレージと Minecraft 26.2 コーデックシリアライズ

プレイヤーのトグル設定は、Minecraft 26.2 の `ValueOutput` および `ValueInput` データパイプラインを使用してプレイヤーの `.dat` セーブファイルに直接保存されます：

```java
// プレイヤー NBT への保存
@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
    output.putBoolean("ig_magnet_enabled", this.ig_magnet$enabled);
}

// プレイヤー NBT からの読み込み
@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
    this.ig_magnet$enabled = input.getBooleanOr("ig_magnet_enabled", true);
}
```

---

## 🧬 Fabric ライフサイクルイベントと死亡・リスポーンフロー

プレイヤーが死亡すると、Minecraft はリスポーン時に全く新しい `ServerPlayer` エンティティを生成します。本 MOD では以下の仕組みにより状態の完全維持を保証します：

1. **`ServerPlayerEvents.COPY_FROM`**: エンティティのクローン生成時に、`oldPlayer` から `newPlayer` へブーリアントグルを即座にコピーします。
2. **`ServerPlayerEvents.AFTER_RESPAWN`**: 新しいプレイヤーエンティティが接続された直後にクライアントへ S2C `MagnetTogglePayload` を自動送信し、クライアント HUD の同期を維持します。
3. **`ServerPlayConnectionEvents.JOIN`**: 専任サーバーまたは LAN ワールドに参加した際、保存されていたプレイヤーの NBT 状態をクライアントへ同期します。

---

## 🔗 関連 Wiki ドキュメント
* [[Brigadier コマンドとサーバートグル|ja_jp-26.2-Commands]]
* [[アーキテクチャと Mixin 実装|ja_jp-26.2-Architecture-and-Mixins]]
* [[HUD と診断システム|ja_jp-26.2-HUD-and-Diagnostics]]
* [[MC 26.2 ポータルに戻る|ja_jp-26.2-Home]]
