# 👁️ 視線檢測與障礙機制 (MC 26.1.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 功能資訊面板 | 技術參數 |
| :--- | :--- |
| **主要視覺引擎** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **次要視覺引擎** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **球面視野角 (FOV)** | $360.0^\circ$（全方位無死角感應） |
| **接觸距離容差** | $0.3\text{ m}$ 目標接觸半徑閾值 |
| **記憶狀態標記** | `ig$isMagnetized()` / `ig$setMagnetized()` |
| **慣性動量規則** | `ig:magnet_keep_moving_if_unseen = true` |
| **上下文實作** | `VisionContext` 靜態 Record |

---

## 📖 雙階段視覺過濾管線

在 Minecraft 26.1.2 中，視線檢測透過無物件分配的**雙階段視覺過濾管線**執行：

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

```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **全方位無死角 FOV**：具有 $360^\circ$ 的球狀感應視角，身後、上方或腳底下的掉落物皆可直接吸引，無需轉動鏡頭視角。
* **次體積接觸容差**：$0.3\text{m}$ 的容差範圍能有效防止物品緊貼實心方塊時的誤判阻擋。

---

## 🌿 階段 2：細粒度方塊遍歷過濾 (`SecondaryVisionCheck`)

```java
private record VisionContext(
        Level level,
        boolean blockTransparent,
        boolean blockFlora,
        boolean blockEntities,
        CollisionContext collisionContext,
        Vec3 start,
        Vec3 end
) {}
```

* **植被 (`ig:magnet_blocked_by_flora`)**：檢查是否包含 `BushBlock` 與 `LeavesBlock`。
* **方塊實體 (`ig:magnet_blocked_by_block_entities`)**：檢查 `state.hasBlockEntity()`（箱子、界伏盒、床）。
* **透明方塊 (`ig:magnet_blocked_by_transparent`)**：對玻璃、玻璃片與半磚測試 `state.getVisualShape().clip(...)`。

---

## 🚀 動量慣性延續 (`keepMovingIfUnseen`)

* 初次在視野內看見物品時，`((IMagnetEntity) entity).ig$setMagnetized()` 會對實體進行標記。
* 若 `ig:magnet_keep_moving_if_unseen = true`，只要該實體先前已被磁吸，即便中途滑落至障礙物後方，仍能繞過阻礙持續被牽引進包。

---

## ⚙️ 相關設定與遊戲規則 GameRules

| 遊戲規則 Identifier | 類型 | 預設值 | 描述與效果 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | 是否要求必須在視線可及時才能吸引物品。 |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | 已吸取物品中途失去視線後，是否繼續牽引。 |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | 若為 true，玻璃與透明方塊將遮擋視線。 |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | 若為 true，草叢與花卉將遮擋視線。 |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | 若為 true，儲物箱與方塊實體將遮擋視線。 |

---

## 🔗 相關 Wiki 文件
* [[🧲 吸引移動與穿相邏輯|zh_tw-26.1.2-Vacuum-and-Phase-Shifting]]
* [[⚙️ 遊戲規則 GameRules 參考|zh_tw-26.1.2-GameRules]]
* [[📖 返回 MC 26.1.2 文檔中心|zh_tw-26.1.2-Home]]
