# ✨ 経験値オーブの引き寄せ (MC 26.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 機能インフォボックス | 技術パラメータ |
| :--- | :--- |
| **対象エンティティクラス** | `net.minecraft.world.entity.ExperienceOrb` |
| **マネージャークラス** | `net.instantgratification.magnet.MagnetManager` |
| **有効化ゲームルール** | `ig:magnet_affects_xp` (デフォルト: `true`) |
| **スキャンクエリ** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **パーティクルタイプ** | `ParticleTypes.ELECTRIC_SPARK` |
| **パーティクル上限制御** | `ig:magnet_max_particle_sources` によりアイテムと共有 |

---

## 📖 経験値バキュームメカニクス

Instant Gratification（即時充足）の理念において、経験値オーブが地面に散らばったり洞窟の天井に張り付いたまま放置されることは、ゲームプレイのスムーズな流れを阻害します。**Magnet, Let me get that!** は、ドロップアイテムと並んで `ExperienceOrb` エンティティの引き寄せを標準サポートしています。

```
+------------------+     getEntitiesOfClass     +-----------------------+     MagnetMovement.pull     +--------------------+
| Player Scan AABB | -------------------------> | List<ExperienceOrb>   | --------------------------> | Player Collection  |
+------------------+                            +-----------------------+                             +--------------------+
                                                            |
                                                            v
                                                [Apply NoClip Phase-Shift]
                                                [Apply Lerp Velocity Vector]
                                                [Throttled Spark Particles]
```

---

## ⚡ 同期された物理挙動と位相シフト

`ig:magnet_affects_xp` が有効な場合、範囲内のすべての経験値オーブはドロップアイテムとまったく同一の高度な挙動を継承します：

1. **位相シフト (NoClip)**: 経験値オーブは引き寄せ中に固体のブロックをすり抜け、壁の向こう側で旋回し続けたり跳ね返り続けたりするのを防ぎます。
2. **動的加速度**: 経験値オーブは同じ速度（$s = \text{speed}/100.0$）および加速度（$a = \text{accel}/100.0$）の補間計算に従います。
3. **視線判定フィルタリング**: `ig:magnet_los_only` が true の場合、XP オーブはプライマリ 360° レイキャストおよびセカンダリ詳細チェックの両方を通過する必要があります。

---

## 🛡️ パフォーマンス保護とラグ防止（パーティクル制限）

高密度のトラップタワーやエンダードラゴン討伐戦では、数百個の経験値オーブが同時に発生することがあります。毎ティックすべてのオーブにパーティクルを生成すると、クライアントの著しい FPS 低下を招く危険性があります。

本 MOD では **グローバル・パーティクルソース制限** によりパーティクルラグを未然に防止します：

```java
int maxParticleSources = ModGameRules.getInt(player.level(), ModGameRules.MAGNET_MAX_PARTICLE_SOURCES);
int particleSourceCount = 0;

for (ExperienceOrb orb : orbs) {
    boolean shouldSpawnParticles = false;
    if (particleSourceCount < maxParticleSources) {
        shouldSpawnParticles = true;
        particleSourceCount++;
    }
    MagnetMovement.pull(orb, player, shouldSpawnParticles);
}
```

* **グローバル上限**: 指定されたティックにおいて火花パーティクルを放出できるのは、設定値（`ig:magnet_max_particle_sources`、デフォルト: `5`）で制限された先頭 $N$ 個のエンティティのみです。
* **ティック分散**: パーティクルは `(entity.tickCount + entity.getId()) % 4 == 0`（4 ティックに 1 回 = 毎秒 5 回）を満たす場合にのみ放出されます。

---

## ⚙️ 関連設定とゲームルール

| GameRule | 型 | デフォルト | 単位 / 範囲 | 説明 |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | 経験値オーブを磁石で引き寄せるかどうか。 |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | エレクトリックスパーク軌跡効果のマスター切り替え。 |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | アクティブなソースごとに放出される火花パーティクル数。 |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | パーティクル放出を許可する最大同時エンティティ数。 |

---

## 🔗 関連 Wiki ドキュメント
* [[バキューム物理と位相シフト|ja_jp-26.2-Vacuum-and-Phase-Shifting]]
* [[GameRules 完全リファレンス|ja_jp-26.2-GameRules]]
* [[HUD と視覚診断|ja_jp-26.2-HUD-and-Diagnostics]]
* [[MC 26.2 ポータルに戻る|ja_jp-26.2-Home]]
