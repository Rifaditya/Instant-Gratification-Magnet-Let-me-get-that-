# 📊 HUD・視覚効果・オーバーレイ (MC 26.1.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 視覚機能インフォボックス | 技術パラメータ |
| :--- | :--- |
| **アクションバー API** | `client.gui.setOverlayMessage(Component, boolean)` |
| **パーティクルタイプ** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **パーティクル間引き除算** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **パーティクルソース上限** | `ig:magnet_max_particle_sources` (デフォルト: `5`) |

---

## 🖥️ アクションバーオーバーレイ通知

プレイヤーが `Ctrl+M` ホットキーの組み合わせでアイテム磁石を切り替えると、クライアント GUI は即座にアクションバー通知を表示します：

* **有効化**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **無効化**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// 検証元: Gui.java (26.1.2)
if (newState) {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ エレクトリックスパーク軌跡効果

引き寄せられているアイテムおよび経験値オーブは火花の軌跡エフェクトを放出します：

```
[引き寄せアイテム / XP]  --->  ✨  --->  ✨  --->  ✨  --->  [プレイヤー視線位置]
```

* **ソース制限**: 同時にパーティクルを放出できるのは最大 5 つのソースに制限されます（`ig:magnet_max_particle_sources = 5`）。
* **ティック分散**: パーティクルは 4 ティックに 1 回放出されます（$5\text{ 回/秒}$）。
* **密度制御**: `ig:magnet_particle_count = 1`。

---

## 🔗 関連 Wiki ドキュメント
* [[プレイヤートグルと状態管理|ja_jp-26.1.2-Player-Toggle-and-Persistence]]
* [[経験値オーブの引き寄せ|ja_jp-26.1.2-Experience-Orb-Attraction]]
* [[MC 26.1.2 ポータルに戻る|ja_jp-26.1.2-Home]]
