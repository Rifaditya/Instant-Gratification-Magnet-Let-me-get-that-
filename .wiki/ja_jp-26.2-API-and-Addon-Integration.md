# 🔌 API とアドオン連携 (MC 26.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| API インフォボックス | 技術パラメータ |
| :--- | :--- |
| **プレイヤーインターフェース** | `net.instantgratification.magnet.IMagnetPlayer` |
| **エンティティインターフェース** | `net.instantgratification.magnet.IMagnetEntity` |
| **コア移動ファサード** | `net.instantgratification.magnet.MagnetMovement` |
| **GameRule API** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **視線 API** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 他 MOD からの開発者向け連携

サードパーティ製 MOD、サーバーユーティリティ、および Instant Gratification companion アドオンは、**Magnet, Let me get that!** と直接連携して、プレイヤーの磁力状態の取得、プログラムによるアイテム引き寄せ、または障害物のバイパスを実行できます。

---

## 🧑‍💻 プレイヤー状態インターフェース (`IMagnetPlayer`)

任意の `Player` または `ServerPlayer` インスタンスを `IMagnetPlayer` にキャストして、磁石の設定を取得・変更できます：

```java
package net.instantgratification.magnet;

public interface IMagnetPlayer {
    boolean ig_magnet$isMagnetEnabled();
    void ig_magnet$setMagnetEnabled(boolean enabled);
    boolean ig_magnet$toggleMagnet();
}
```

### 使用例:
```java
// プレイヤーの磁石が有効か確認
if (((IMagnetPlayer) player).ig_magnet$isMagnetEnabled()) {
    // カスタムアドオン処理...
}

// プログラムから磁石を無効化（例: 玉座に着席中やミニゲーム中など）
((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(false);
```

---

## 📦 エンティティ磁力化インターフェース (`IMagnetEntity`)

任意の `Entity` インスタンス（カスタム Mob ドロップ、発射物、XP オーブ等）を `IMagnetEntity` にキャストできます：

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig_magnet$setMagnetNoClip();
    boolean ig_magnet$isMagnetNoClip();
    void ig_magnet$setMagnetized();
    boolean ig_magnet$isMagnetized();
}
```

### 使用例:
```java
// カスタムエンティティに一時的な 2 ティックの位相シフト（すり抜け）を付与
((IMagnetEntity) customItem).ig_magnet$setMagnetNoClip();
```

---

## 🚀 静的移動ファサード (`MagnetMovement.pull`)

アドオン MOD は、組み込みの物理・視線エンジンを使用して、エンティティを任意のプレイヤーに向けて手動で引き寄せることができます：

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

```java
// パーティクル軌跡を伴って対象エンティティをプレイヤーへ引き寄せる
MagnetMovement.pull(specialDrop, player, true);
```

---

## 🔗 関連 Wiki ドキュメント
* [[アーキテクチャと Mixin ターゲット|ja_jp-26.2-Architecture-and-Mixins]]
* [[開発環境セットアップとビルド|ja_jp-26.2-Developer-Setup-and-Building]]
* [[MC 26.2 ポータルに戻る|ja_jp-26.2-Home]]
