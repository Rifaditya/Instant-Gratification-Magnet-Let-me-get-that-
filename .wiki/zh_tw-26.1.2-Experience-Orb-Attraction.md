# ✨ 經驗球吸附 (MC 26.1.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 功能資訊面板 | 技術參數 |
| :--- | :--- |
| **目標實體類別** | `net.minecraft.world.entity.ExperienceOrb` |
| **管理類別** | `net.instantgratification.magnet.MagnetManager` |
| **啟用遊戲規則** | `ig:magnet_affects_xp`（預設：`true`） |
| **掃描查詢** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **粒子效果類型** | `ParticleTypes.ELECTRIC_SPARK` |
| **粒子源上限** | `ig:magnet_max_particle_sources`（預設：`5`） |

---

## 📖 經驗值磁吸機制

在即時滿足（Instant Gratification）設計理念下，將經驗球遺落在原地與流暢的遊戲體驗背道而馳。在 Minecraft 26.1.2 中，`ExperienceOrb` 實體享有與掉落物品完全一致的速度向量與穿相物理運算。

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

1. **穿相移動（NoClip）**：經驗球在牽引過程中穿透實心方塊，避免卡在牆壁後方或天花板死角中。
2. **速度向量插值**：經驗球根據設定的速度與加速度百分比平滑加速飛向玩家眼部位置。
3. **視線門檻檢測**：當 `ig:magnet_los_only` 為 true 時，經驗球必須為玩家可見，或已具備磁吸慣性。

---

## 🛡️ 防卡頓保護與粒子池化

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

* **粒子源上限**：每個 Tick 僅允許前 $N$ 個實體發射飛行軌跡粒子。
* **客戶端安全降級**：在 `MagnetMovement.java` 中，若當前世界不是 `ServerLevel`，粒子效果會優雅降級回退調用 `level.addParticle(...)`。

---

## ⚙️ 相關設定與遊戲規則 GameRules

| 遊戲規則 Identifier | 類型 | 預設值 | 描述與效果 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | 是否允許磁吸功能吸引經驗球。 |
| `ig:magnet_particles` | Boolean | `true` | 粒子特效的總開關。 |
| `ig:magnet_particle_count` | Integer | `1` | 每個活躍實體每次釋放的電火花粒子數。 |
| `ig:magnet_max_particle_sources` | Integer | `5` | 同時允許釋放粒子的最大實體數。 |

---

## 🔗 相關 Wiki 文件
* [[🧲 吸引移動與穿相邏輯|zh_tw-26.1.2-Vacuum-and-Phase-Shifting]]
* [[⚙️ 遊戲規則 GameRules 參考|zh_tw-26.1.2-GameRules]]
* [[📖 返回 MC 26.1.2 文檔中心|zh_tw-26.1.2-Home]]
