# ✨ 經驗球吸附 (MC 26.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 功能資訊面板 | 技術參數 |
| :--- | :--- |
| **目標實體類別** | `net.minecraft.world.entity.ExperienceOrb` |
| **管理類別** | `net.instantgratification.magnet.MagnetManager` |
| **啟用遊戲規則** | `ig:magnet_affects_xp`（預設：`true`） |
| **掃描查詢** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **粒子效果類型** | `ParticleTypes.ELECTRIC_SPARK` |
| **粒子發射限制** | 透過 `ig:magnet_max_particle_sources` 與物品共用配額 |

---

## 📖 經驗值磁吸機制

在即時滿足（Instant Gratification）設計哲學下，擊敗怪物或開採礦石後將經驗球散落在地或任其飄向天花板，會打斷玩家流暢的遊玩節奏。**Magnet, Let me get that!** 對 `ExperienceOrb` 實體提供與掉落物同等規格的一流吸附支援。

```
+------------------+     getEntitiesOfClass     +-----------------------+     MagnetMovement.pull     +--------------------+
|  玩家範圍 AABB   | -------------------------> |  List<ExperienceOrb>  | --------------------------> | 玩家拾取吸收經驗   |
+------------------+                            +-----------------------+                             +--------------------+
                                                             |
                                                             v
                                                  [套用 NoClip 穿相機制]
                                                  [套用動態 Lerp 速度向量]
                                                  [受限電火花粒子特效]
```

---

## ⚡ 同步物理計算與穿相移動

當 `ig:magnet_affects_xp` 啟用時，範圍內的所有經驗球皆繼承與掉落物品完全相同的進階物理運算：

1. **穿相移動（NoClip）**：經驗球在被牽引時同樣具備穿透實心方塊的能力，避免其卡在石牆後或在死角中反覆彈跳。
2. **動態加速度**：經驗球嚴格遵循相同設定的速度（$s = \text{speed}/100.0$）與加速度插值（$a = \text{accel}/100.0$）運算。
3. **視線檢測過濾**：若 `ig:magnet_los_only` 為 true，經驗球必須通過 360° 球面射線以及細粒度障礙篩選。

---

## 🛡️ 效能保護與防卡頓（粒子上限機制）

在高效率刷怪場或終界龍決戰中，場地可能在瞬間爆發出數百顆經驗球。若每個 Tick 讓每顆經驗球都發射粒子，會導致客戶端 FPS 急遽下降。

模組透過**全域粒子源節流閥（Global Particle Source Throttling）**徹底杜絕粒子卡頓：

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

* **全域上限**：在任何給定 Tick 中，僅有前 $N$ 個實體（由 `ig:magnet_max_particle_sources` 設定，預設：`5`）獲准釋放電火花粒子。
* **Tick 錯峰發射**：粒子僅在 `(entity.tickCount + entity.getId()) % 4 == 0` 時釋放（即每 4 個 Tick 產生一次，相當於每秒 5 次），且依實體 ID 錯峰均攤。

---

## ⚙️ 相關設定與遊戲規則 GameRules

| 遊戲規則 Identifier | 類型 | 預設值 | 單位 / 範圍 | 描述與效果 |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | 是否允許磁吸功能吸引經驗球。 |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | 牽引軌跡電火花粒子特效的總開關。 |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | 每個活躍發射源每次觸發時發射的火花粒子數。 |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | 允許同時發射粒子的實體上限，防止客戶端掉幀。 |

---

## 🔗 相關 Wiki 文件
* [[🧲 吸引移動與穿相邏輯|zh_tw-26.2-Vacuum-and-Phase-Shifting]]
* [[⚙️ 遊戲規則 GameRules 參考|zh_tw-26.2-GameRules]]
* [[📊 HUD 與診斷系統|zh_tw-26.2-HUD-and-Diagnostics]]
* [[📖 返回 MC 26.2 文檔中心|zh_tw-26.2-Home]]
