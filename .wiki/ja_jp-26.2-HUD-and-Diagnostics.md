# 📊 HUD・視覚効果・診断 (MC 26.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 視覚機能インフォボックス | 技術パラメータ |
| :--- | :--- |
| **アクションバー API** | `client.gui.hud.setOverlayMessage(Component, boolean)` |
| **パーティクルタイプ** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **パーティクル間引き除算** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **デバッグログファイル** | `logs/ig_magnet_debug.log` |
| **ロガークラス** | `net.instantgratification.magnet.MagnetDebugLogger` |

---

## 🖥️ アクションバーオーバーレイ通知

プレイヤーがキーバインド（`\`）またはサーバーコマンド（`/magnet toggle`）で磁石状態を切り替えると、クライアント HUD はホットバーのすぐ上に邪魔にならないオーバーレイメッセージを即座に表示します：

* **有効化メッセージ**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **無効化メッセージ**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// 検証元: Hud.java (26.2+)
if (newState) {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ エレクトリックスパーク軌跡効果

引き寄せられている飛行中のアイテムおよび経験値オーブは、微細な `ParticleTypes.ELECTRIC_SPARK` の軌跡エフェクトを放出します：

```
[引き寄せアイテム]  --->  ✨  --->  ✨  --->  ✨  --->  [プレイヤー視線位置]
```

### パーティクル放出の抑制ルール:
1. **ソース数制限**: `ig:magnet_max_particle_sources`（デフォルト: `5`）により、露天掘り等で大量のドロップが発生しても過剰なパーティクル放出を抑えます。
2. **頻度分散**: 各エンティティは 4 ティックに 1 回のみパーティクルを生成し、エンティティ ID に応じてタイミングが分散されます: `(entity.tickCount + entity.getId()) % 4 == 0`。
3. **放出量調整**: ソースあたりの放出量は `ig:magnet_particle_count`（デフォルト: `1`）で制御されます。

---

## 📝 専用デバッグファイルロガー (`MagnetDebugLogger`)

視線判定の境界やネットワークパケットの挙動を調査するサーバー管理者や MOD パック開発者のために、MOD には `logs/ig_magnet_debug.log` へ書き出すスレッドセーフな非同期ファイルロガーが組み込まれています：

* **有効化**: ゲーム内で `/magnet debug log` を実行します。
* **出力フォーマット**: `[yyyy-MM-dd HH:mm:ss.SSS] [Context] Message`
* **ログサンプル**:
  ```text
  [2026-08-22 21:50:00.124] MagnetMod: Server received packet from Alex (e7b1a234-...) enabled=true
  [2026-08-22 21:50:00.130] MagnetMovement: Entity 592 is now magnetized by Alex (e7b1a234-...).
  [2026-08-22 21:50:01.450] PlayerMixin: Server tick for Alex (e7b1a234-...) isMagnetEnabled=true
  ```

---

## 🔗 関連 Wiki ドキュメント
* [[Brigadier コマンドとゲーム内診断|ja_jp-26.2-Commands]]
* [[経験値オーブの引き寄せ|ja_jp-26.2-Experience-Orb-Attraction]]
* [[MC 26.2 ポータルに戻る|ja_jp-26.2-Home]]
