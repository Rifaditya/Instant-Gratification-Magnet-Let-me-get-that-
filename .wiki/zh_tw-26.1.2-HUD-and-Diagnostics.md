# 📊 HUD、視覺效果與狀態顯示 (MC 26.1.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 視覺資訊面板 | 技術參數 |
| :--- | :--- |
| **快捷列提示 API** | `client.gui.setOverlayMessage(Component, boolean)` |
| **粒子效果類型** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **粒子節流取模** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **粒子源上限** | `ig:magnet_max_particle_sources`（預設：`5`） |

---

## 🖥️ 快捷列上方提示通知

當玩家透過 `Ctrl+M` 組合鍵切換物品磁吸狀態時，客戶端 GUI 會立即在快捷列上方顯示狀態提示：

* **開啟狀態**：`§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **關閉狀態**：`§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// 驗證自：Gui.java (26.1.2)
if (newState) {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ 電火花飛行軌跡粒子

處於飛行磁吸過程中的掉落物與經驗球會持續釋放電火花粒子軌跡：

```
[被牽引物品 / 經驗]  --->  ✨  --->  ✨  --->  ✨  --->  [玩家眼部位置]
```

* **發射源數量門檻**：同一個 Tick 內最多允許 5 個實體同時發射粒子（`ig:magnet_max_particle_sources = 5`）。
* **Tick 頻率節流**：粒子每 4 個 Tick 產生一次（相當於每秒 5 次）。
* **發射密度調節**：`ig:magnet_particle_count = 1`。

---

## 🔗 相關 Wiki 文件
* [[🔄 玩家開關與會話狀態|zh_tw-26.1.2-Player-Toggle-and-Persistence]]
* [[✨ 經驗球吸附|zh_tw-26.1.2-Experience-Orb-Attraction]]
* [[📖 返回 MC 26.1.2 文檔中心|zh_tw-26.1.2-Home]]
