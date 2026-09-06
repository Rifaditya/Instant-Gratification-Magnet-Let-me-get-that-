# ⚙️ 遊戲規則 GameRules 完整參考 (MC 26.1.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 分類資訊面板 | 詳細資訊 |
| :--- | :--- |
| **類別 ID** | `magnet:magnet_category` |
| **在地化標題** | `Magnet, Let me get that!` (`gamerule.category.magnet.magnet_category`) |
| **註冊管理器** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **註冊類別** | `net.instantgratification.magnet.registry.ModGameRules` |
| **註冊規則總數** | `15` 個具命名空間規則 |

---

## 📖 遊戲內 GameRules 管理說明

在 Minecraft 26.1.2 中，**Magnet, Let me get that!** 的所有全域功能機制皆由註冊於 `magnet:magnet_category` 分類下的具命名空間遊戲規則進行精準控制。

```
/gamerule ig:magnet_enabled true
/gamerule ig:magnet_range 12
/gamerule ig:magnet_speed 80
/gamerule ig:magnet_instant false
```

---

## 📋 完整 GameRules 參考表

| 遊戲規則 Identifier | 類型 | 預設值 | 數值範圍 | 在地化顯示名稱 | 說明與遊戲效果 |
| :--- | :---: | :---: | :---: | :--- | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | **Magnet Enabled** | 全域啟用或關閉掉落物磁吸系統的總開關。 |
| `ig:magnet_range` | Integer | `12` | `1..64` | **Magnet Range** | 玩家吸引掉落物的球面方塊半徑。 |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | **Magnet Noclip** | 啟用穿相邏輯，允許被牽引的物品自由穿透實心方塊。 |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | **Attract XP Orbs** | 是否在吸引物品的同時吸引經驗球。 |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | **Magnet Particles** | 是否沿著被牽引實體的飛行軌跡生成電火花粒子。 |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | **Particle Count** | 每個活躍粒子源每次觸發時釋放的火花粒子數。 |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | **Max Particle Sources** | 允許同時發射粒子的實體上限，防止大量掉落物引發客戶端掉幀。 |
| `ig:magnet_speed` | Integer | `80` | `1..1000` | **Item Speed** | 終端飛行速度百分比（$80 = 0.8\text{ 格/tick} = 16.0\text{ m/s}$）。 |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000` | **Item Acceleration** | 牽引加速度插值百分比（$10 = 10\%\text{ 插值/tick}$）。 |
| `ig:magnet_instant` | Boolean | `false` | `true/false` | **Instant Pickup** | 啟用後物品透過擴展 AABB 立即進包，飛行耗時為 0。 |
| `ig:magnet_los_only` | Boolean | `true` | `true/false` | **Line of Sight Only** | 強制視線檢測；防止隔著不可穿透的牆壁吸取物品。 |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | `true/false` | **Keep Moving if Unseen** | 允許在視線內被磁吸的物品在飛行中暫時失去視線後繼續保持牽引。 |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | `true/false` | **Blocked by Transparent** | 若為 true，玻璃、玻璃片、鐵柵欄與半透明方塊將遮擋視線。 |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | `true/false` | **Blocked by Flora** | 若為 true，高草叢、農作物、花卉與樹葉將遮擋視線。 |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | `true/false` | **Blocked by Block Entities** | 若為 true，儲物箱、床、木桶與界伏盒將遮擋視線。 |

---

## 🔗 相關 Wiki 文件
* [[🧲 吸引移動與穿相邏輯|zh_tw-26.1.2-Vacuum-and-Phase-Shifting]]
* [[🎨 Cloth Config 設定介面|zh_tw-26.1.2-Configuration]]
* [[📖 返回 MC 26.1.2 文檔中心|zh_tw-26.1.2-Home]]
