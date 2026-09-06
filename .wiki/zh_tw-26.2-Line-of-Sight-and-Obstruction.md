# 👁️ 視線檢測與障礙機制 (MC 26.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 功能資訊面板 | 技術參數 |
| :--- | :--- |
| **主要視覺引擎** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **次要視覺引擎** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **球面視野角 (FOV)** | $360.0^\circ$（全方位無死角感應） |
| **接觸距離容差** | $0.3\text{ m}$ 目標接觸半徑閾值 |
| **記憶狀態標記** | `ig_magnet$isMagnetized()` / `ig_magnet$setMagnetized()` |
| **慣性動量規則** | `ig:magnet_keep_moving_if_unseen = true` |
| **細粒度過濾規則** | 透明方塊、植被、方塊實體 |

---

## 📖 雙階段視覺管線架構

為了防止玩家隔著厚重礦洞牆壁或安全基地作弊吸取物資，同時確保伺服器維持零延遲極致效能，**Magnet, Let me get that!** 採用了高效的**雙階段視覺過濾管線**：

```
                                +---------------------------+
                                |      偵測到目標掉落物     |
                                +-------------+-------------+
                                              |
                                              v
                              +-------------------------------+
                              |    第一階段（360° 球面視線）   |
                              |   PlayerVisionTracker.canSee  |
                              +---------------+---------------+
                                              |
                        +---------------------+---------------------+
                        |                                           |
                    [可見視野]                                  [存在遮擋]
                        |                                           |
                        v                                           v
      +-----------------------------------+               +--------------------+
      |       第二階段（細粒度過濾）      |               |     動量慣性檢查   |
      |     SecondaryVisionCheck.canSee   |               | keepMovingIfUnseen |
      +-----------------+-----------------+               +---------+----------+
                        |                                           |
            +-----------+-----------+                     +---------+---------+
            |                       |                     |                   |
        [通過判定]               [被阻擋]              [已磁吸實體]       [未磁吸實體]
            |                       |                     |                   |
            v                       v                     v                   v
    +---------------+       +---------------+     +---------------+   +---------------+
    | 執行磁吸牽引  |       | 拒絕 / 停止   |     | 繼續慣性牽引  |   | 拒絕磁吸牽引  |
    | 標記為已磁吸  |       +---------------+     +---------------+   +---------------+
    +---------------+
```

---

## 🔍 階段 1：主要 360° 球面視線檢測

主要視覺檢查調用 DasikLibrary 高度優化的射線檢測引擎：
```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **全方位無死角 FOV**：具備 $360.0^\circ$ 的球狀感應錐，只要沒有實心方塊阻隔，玩家正後方、頭頂上方或腳底正下方的掉落物均可直接被磁吸。
* **0.3m 接觸距離容差**：當物品緊貼方塊角落或邊界時，$0.3\text{m}$ 的次級體積公差能有效防止射線微小偏差造成的誤判阻擋。

---

## 🌿 階段 2：細粒度方塊狀態過濾 (`SecondaryVisionCheck`)

若主要射線檢測通過，模組會透過 `BlockGetter.traverseBlocks` 評估選用的細粒度障礙規則：

```java
public static boolean canSee(Player player, Entity target, boolean blockTransparent, boolean blockFlora, boolean blockEntities)
```

1. **植被與樹葉過濾 (`ig:magnet_blocked_by_flora`)**：
   - 檢查射線穿過的路徑是否包含 `BushBlock`（高草、花朵、農作物、樹苗）或 `LeavesBlock`（樹葉）。
   - 若啟用（`true`），茂密植被將視為不透明阻擋障礙。
2. **互動方塊實體 (`ig:magnet_blocked_by_block_entities`)**：
   - 檢查穿透路徑上方塊是否具備 `state.hasBlockEntity()`。
   - 若啟用（`true`），箱子、陷阱箱、木桶、界伏盒、床與發射器等方塊實體將遮擋視線。
3. **透明與非完整方塊 (`ig:magnet_blocked_by_transparent`)**：
   - 對方塊的 `state.getVisualShape(...)` 進行精準射線碰撞測試。
   - 若啟用（`true`），玻璃、玻璃片、鐵柵欄、欄杆、半磚與階梯將視為視線障礙物。

---

## 🚀 動量慣性延續 (`keepMovingIfUnseen`)

在節奏明快的挖礦或戰鬥中，被吸向玩家的物品常在拐角處暫時脫離玩家視野。為了防止物品驟停或跌落岩漿：

1. **磁吸狀態標記**：當物品在視線內初次被吸取時，`((IMagnetEntity) entity).ig_magnet$setMagnetized()` 會在記憶體中打上布林標記。
2. **慣性維持**：若物品在後續 Tick 中暫時失去視線：
   - 若 `ig:magnet_keep_moving_if_unseen = true` 且 `ig_magnet$isMagnetized() == true`：物品將不受視線中斷影響，繼續保持飛行並被吸入背包。
   - 若 `ig:magnet_keep_moving_if_unseen = false`：一旦失去視線，牽引立即中斷。
   - 若該物品**從未被玩家看見**（例如在密封牆壁後由爆炸或發射器噴出）：將立即被拒絕吸取。

---

## ⚙️ 相關設定與遊戲規則 GameRules

| 遊戲規則 Identifier | 類型 | 預設值 | 描述與效果 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | 是否必須在視線可及時才能吸引物品。 |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | 已吸取物品中途失去視線後，是否繼續牽引。 |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | 若為 true，玻璃與透明方塊將阻擋磁吸視線。 |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | 若為 true，草叢、樹葉與花卉將阻擋磁吸視線。 |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | 若為 true，儲物箱、床與容器將阻擋磁吸視線。 |

---

## 🔗 相關 Wiki 文件
* [[🧲 吸引移動與穿相邏輯|zh_tw-26.2-Vacuum-and-Phase-Shifting]]
* [[⚙️ 遊戲規則 GameRules 參考|zh_tw-26.2-GameRules]]
* [[🧩 架構設計與 Mixin 注入目標|zh_tw-26.2-Architecture-and-Mixins]]
* [[📖 返回 MC 26.2 文檔中心|zh_tw-26.2-Home]]
