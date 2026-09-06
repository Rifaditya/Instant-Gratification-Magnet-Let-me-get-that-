# 📊 HUD、視覺效果與診斷系統 (MC 26.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 視覺資訊面板 | 技術參數 |
| :--- | :--- |
| **快捷列提示 API** | `client.gui.hud.setOverlayMessage(Component, boolean)` |
| **粒子效果類型** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **粒子節流取模** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **除錯日誌檔案** | `logs/ig_magnet_debug.log` |
| **日誌管理器類別** | `net.instantgratification.magnet.MagnetDebugLogger` |

---

## 🖥️ 快捷列上方提示通知

當玩家透過快速鍵（`\`）或伺服器指令（`/magnet toggle`）切換磁吸狀態時，客戶端 HUD 會立即在快捷列上方顯示簡潔、非侵入式的在地化狀態提示：

* **開啟提示**：`§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **關閉提示**：`§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// 驗證自：Hud.java (26.2+)
if (newState) {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ 電火花飛行軌跡粒子

當物品與經驗球在空中被磁吸牽引時，會沿著移動路徑釋放細微的 `ParticleTypes.ELECTRIC_SPARK` 視覺軌跡：

```
[被牽引物品]  --->  ✨  --->  ✨  --->  ✨  --->  [玩家眼部位置]
```

### 粒子節流保護機制：
1. **發射源數量上限**：受 `ig:magnet_max_particle_sources`（預設：`5`）嚴格控制，確保大量掉落物聚集時不會造成粒子過載。
2. **頻率錯峰發射**：單個實體每 4 個 Tick 僅發射 1 次粒子，並依實體唯一 ID 錯開：`(entity.tickCount + entity.getId()) % 4 == 0`。
3. **密度配置**：由 `ig:magnet_particle_count`（預設：`1`）控制單次發射數量。

---

## 📝 專用除錯檔案日誌 (`MagnetDebugLogger`)

為方便伺服器管理員與模組包作者排查視線邊界或網路資料包問題，模組內建了寫入 `logs/ig_magnet_debug.log` 的非同步執行緒安全日誌器：

* **啟動方式**：在遊戲內執行 `/magnet debug log`。
* **記錄格式**：`[yyyy-MM-dd HH:mm:ss.SSS] [Context] Message`
* **日誌範例**：
  ```text
  [2026-08-22 21:50:00.124] MagnetMod: Server received packet from Alex (e7b1a234-...) enabled=true
  [2026-08-22 21:50:00.130] MagnetMovement: Entity 592 is now magnetized by Alex (e7b1a234-...).
  [2026-08-22 21:50:01.450] PlayerMixin: Server tick for Alex (e7b1a234-...) isMagnetEnabled=true
  ```

---

## 🔗 相關 Wiki 文件
* [[💻 Brigadier 指令套件|zh_tw-26.2-Commands]]
* [[✨ 經驗球吸附|zh_tw-26.2-Experience-Orb-Attraction]]
* [[📖 返回 MC 26.2 文檔中心|zh_tw-26.2-Home]]
