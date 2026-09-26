# 🔄 プレイヤートグルとセッション状態 (MC 26.1.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 機能インフォボックス | 技術パラメータ |
| :--- | :--- |
| **状態ストレージクラス** | `net.instantgratification.magnet.MagnetPlayerState` |
| **デフォルトクライアントキーバインド** | `Ctrl+M` (`GLFW.GLFW_KEY_M` + `isControlDown()`) |
| **キーカテゴリー** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **ネットワークペイロード** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **アクションバー API** | `client.gui.setOverlayMessage(Component, boolean)` |

---

## 📖 状態アーキテクチャの概要

Minecraft 26.1.2 において、プレイヤーのトグル設定はスレッドセーフな `ConcurrentHashMap` を用いた `MagnetPlayerState` によりサーバーセッション中に管理されます：

```java
public class MagnetPlayerState {
    private static final Map<UUID, Boolean> playerStates = new ConcurrentHashMap<>();

    public static boolean isMagnetEnabled(Player player) {
        return playerStates.getOrDefault(player.getUUID(), true);
    }

    public static void setMagnetEnabled(Player player, boolean enabled) {
        playerStates.put(player.getUUID(), enabled);
    }

    public static boolean toggleMagnet(Player player) {
        boolean newState = !isMagnetEnabled(player);
        setMagnetEnabled(player, newState);
        return newState;
    }
}
```

---

## ⌨️ クライアントキーバインド (`Ctrl+M`)

* **デフォルト組み合わせ**: `Ctrl+M`（`GLFW_KEY_M` + `isControlDown()`）。macOS の Command キー（`GLFW_KEY_LEFT_SUPER`）にもクロスプラットフォーム対応しています。
* **視覚的アクションバーフィードバック**:
  - `chat.ig_magnet.enabled`: `"Item Magnet: Enabled"`（アイテム磁石: 有効）
  - `chat.ig_magnet.disabled`: `"Item Magnet: Disabled"`（アイテム磁石: 無効）

---

## 📡 ネットワーク同期プロトコル

```
[クライアント]                                                       [サーバー]
プレイヤーが Ctrl+M を押下
MagnetPlayerState.setMagnetEnabled(player, newState)
ClientPlayNetworking.send(new MagnetTogglePayload(newState))
                                               ------------------> サーバー側受信処理
                                                                   MagnetPlayerState.setMagnetEnabled(player, payload.enabled())
```

---

## 🔗 関連 Wiki ドキュメント
* [[サーバーコマンドとトグル|ja_jp-26.1.2-Commands]]
* [[アーキテクチャと Mixin|ja_jp-26.1.2-Architecture-and-Mixins]]
* [[MC 26.1.2 ポータルに戻る|ja_jp-26.1.2-Home]]
