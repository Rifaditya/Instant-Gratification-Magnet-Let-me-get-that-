# 🧩 アーキテクチャと Mixin ターゲット (MC 26.1.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| アーキテクチャインフォボックス | 技術パラメータ |
| :--- | :--- |
| **ルートパッケージ** | `net.instantgratification.magnet` |
| **Mixin 設定ファイル** | `src/main/resources/magnet.mixins.json` |
| **Access Widener** | `src/main/resources/magnet.accesswidener` |
| **互換性レベル** | `JAVA_25` |
| **Mixin クラス総数** | `2` (`MixinEntity`, `PlayerMixin`) |

---

## 🌳 パッケージアーキテクチャツリー

```text
net.instantgratification.magnet/
├── IMagnetEntity.java                  # NoClip および磁力化フラグ用エンティティインターフェース
├── MagnetCommand.java                  # サーバーコマンド登録 (/magnet toggle)
├── MagnetManager.java                  # エンティティスキャンおよび空間処理ループ
├── MagnetMod.java                      # MOD 初期化およびパケット受信処理
├── MagnetModClient.java                # クライアント初期化、Ctrl+M キーバインドおよび通知
├── MagnetMovement.java                 # 軌道ベクトル計算、速度補間 (Lerp) およびパーティクル
├── MagnetPlayerState.java              # スレッドセーフな ConcurrentHashMap によるプレイヤー状態保持
├── MagnetTogglePayload.java            # ネットワークパケットレコードおよび StreamCodec
├── SecondaryVisionCheck.java           # 詳細ブロックレイキャスト (植物、ブロックエンティティ、ガラス)
├── config/
│   ├── ClothConfigScreenHelper.java    # Cloth Config Fabric GUI ビルダー
│   ├── MagnetConfig.java               # JSON 設定ストレージおよび POJO フィールド
│   └── ModMenuIntegration.java         # リフレクションセーフな ModMenu API エントリポイント
├── mixin/
│   ├── MixinEntity.java                # Entity への NoClip および重力キャンセル注入
│   └── PlayerMixin.java                # Player へのティック実行および即時回収処理の注入
└── registry/
    └── ModGameRules.java               # DynamicGameRuleManager によるゲームルール登録
```

---

## 📋 Mixin 注入ターゲット詳細

### 1. `net.instantgratification.magnet.mixin.MixinEntity`
`net.minecraft.world.entity.Entity` を対象とし、`IMagnetEntity` を実装します。

| 注入メソッド | 注入ターゲットポイント | 動作および挙動 |
| :--- | :--- | :--- |
| `ig_magnet$decrementNoClipTicks` | `@Inject(method = "tick", at = @At("HEAD"))` | アクティブ時、`noClipTicks` カウントダウンを毎ティック 1 減算。 |
| `ig_magnet$preventPushOut` | `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` | サーバー側で `noClipTicks > 0` の場合、バニラの押し出し速度をキャンセル。 |
| `ig_magnet$forceNoClipOnMove` | `@Inject(method = "move", at = @At("HEAD"))` | `noClipTicks > 0` の場合、`originalNoPhysics` をキャッシュして `entity.noPhysics = true` を強制。 |
| `ig_magnet$restoreNoPhysicsAfterMove` | `@Inject(method = "move", at = @At("RETURN"))` | 移動完了後、`entity.noPhysics = originalNoPhysics` を復元。 |
| `ig_magnet$conditionalCancelGravity` | `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` | アイテムが固体のブロック内部にある場合*のみ*重力加速度をキャンセル。 |

---

### 2. `net.instantgratification.magnet.mixin.PlayerMixin`
`net.minecraft.world.entity.player.Player` を対象とします。

| 注入メソッド | 注入ターゲットポイント | 動作および挙動 |
| :--- | :--- | :--- |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | サーバー側で毎ゲームティックごとに `MagnetManager.tick(player)` を呼び出し。 |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | `ig:magnet_instant` が true の場合、回収判定を拡張 (`pickupArea.inflate(range)`)。 |

---

## 🔗 関連 Wiki ドキュメント
* [[バキューム物理と位相シフト|ja_jp-26.1.2-Vacuum-and-Phase-Shifting]]
* [[開発環境セットアップとビルド|ja_jp-26.1.2-Developer-Setup-and-Building]]
* [[MC 26.1.2 ポータルに戻る|ja_jp-26.1.2-Home]]
