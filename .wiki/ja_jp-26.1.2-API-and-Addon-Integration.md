# 🔌 API とアドオン連携 (MC 26.1.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| API インフォボックス | 技術パラメータ |
| :--- | :--- |
| **プレイヤー状態マネージャー** | `net.instantgratification.magnet.MagnetPlayerState` |
| **エンティティインターフェース** | `net.instantgratification.magnet.IMagnetEntity` |
| **コア移動ファサード** | `net.instantgratification.magnet.MagnetMovement` |
| **GameRule API** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **視線 API** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 他 MOD からの開発者向け連携

サードパーティ製 MOD や Instant Gratification コンパニオンアドオンは、Minecraft 26.1.2 において **Magnet, Let me get that!** と直接連携できます。

---

## 🧑‍💻 プレイヤー状態管理 (`MagnetPlayerState`)

静的ヘルパーメソッドを介して、プレイヤーの磁石設定を直接取得または変更できます：

```java
package net.instantgratification.magnet;

public class MagnetPlayerState {
    public static boolean isMagnetEnabled(Player player);
    public static void setMagnetEnabled(Player player, boolean enabled);
    public static boolean toggleMagnet(Player player);
}
```

### 使用例:
```java
// プレイヤーの磁石が有効か確認
if (MagnetPlayerState.isMagnetEnabled(player)) {
    // カスタムロジック...
}

// プログラムから磁石を無効化
MagnetPlayerState.setMagnetEnabled(player, false);
```

---

## 📦 エンティティ磁力化インターフェース (`IMagnetEntity`)

任意の `Entity` インスタンスを `IMagnetEntity` にキャストして、位相シフトやすり抜けフラグを操作できます：

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig$setMagnetNoClip();
    boolean ig$isMagnetNoClip();
    void ig$setMagnetized();
    boolean ig$isMagnetized();
}
```

---

## 🚀 静的移動ファサード (`MagnetMovement.pull`)

プログラムからプレイヤーに向かってアイテムや経験値オーブの引き寄せを発生させます：

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

---

## 🔗 関連 Wiki ドキュメント
* [[アーキテクチャと Mixin ターゲット|ja_jp-26.1.2-Architecture-and-Mixins]]
* [[開発環境セットアップとビルド|ja_jp-26.1.2-Developer-Setup-and-Building]]
* [[MC 26.1.2 ポータルに戻る|ja_jp-26.1.2-Home]]
