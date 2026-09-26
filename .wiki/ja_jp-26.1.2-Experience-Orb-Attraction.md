# ✨ 経験値オーブの引き寄せ (MC 26.1.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般公開リリースビルドに先立つ最新の未リリースコミットや開発中の機能が含まれている場合があります。

| 機能インフォボックス | 技術パラメータ |
| :--- | :--- |
| **対象エンティティクラス** | `net.minecraft.world.entity.ExperienceOrb` |
| **マネージャークラス** | `net.instantgratification.magnet.MagnetManager` |
| **有効化ゲームルール** | `ig:magnet_affects_xp` (デフォルト: `true`) |
| **スキャンクエリ** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **パーティクルタイプ** | `ParticleTypes.ELECTRIC_SPARK` |
| **パーティクルソース上限** | `ig:magnet_max_particle_sources` (デフォルト: `5`) |

---

## 📖 経験値バキュームメカニクス

Instant Gratification の哲学において、経験値オーブを取り残すことは快適なゲームプレイの妨げになります。Minecraft 26.1.2 では、`ExperienceOrb` エンティティはドロップアイテムと完全に同一の速度および位相シフト物理によって引き寄せられます。

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

1. **位相シフト (NoClip)**: 経験値オーブは固体のブロックをすり抜け、壁の向こうや天井の角に挟まるのを防ぎます。
2. **速度補間**: 設定された速度および加速度のパーセンテージに従い、プレイヤーの目の位置へ滑らかに加速します。
3. **LOS 判定**: `ig:magnet_los_only` が true の場合、XP オーブはプレイヤーから視認できるか、すでに磁力慣性を保持している必要があります。

---

## 🛡️ ラグ防止とパーティクルプーリング

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

* **最大パーティクルソース数**: 視覚的な軌跡を生成できるのは、1ティックあたり先頭 $N$ 個のエンティティに制限されます。
* **クライアント安全フォールバック**: `MagnetMovement.java` では、ワールドが `ServerLevel` でない場合、安全に `level.addParticle(...)` へフォールバックします。

---

## ⚙️ 関連設定とゲームルール

| GameRule | 型 | デフォルト | 説明 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | 磁石が経験値オーブを引き寄せるかどうか。 |
| `ig:magnet_particles` | Boolean | `true` | パーティクル効果のマスター切り替え。 |
| `ig:magnet_particle_count` | Integer | `1` | エンティティごとに放出される火花パーティクル数。 |
| `ig:magnet_max_particle_sources` | Integer | `5` | 同時にパーティクル放出を許可する最大エンティティ数。 |

---

## 🔗 関連 Wiki ドキュメント
* [[バキューム物理と位相シフト|ja_jp-26.1.2-Vacuum-and-Phase-Shifting]]
* [[GameRules 完全リファレンス|ja_jp-26.1.2-GameRules]]
* [[MC 26.1.2 ポータルに戻る|ja_jp-26.1.2-Home]]
