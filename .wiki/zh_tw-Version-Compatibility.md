# 🗺️ 多時代版本相容性矩陣

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

本頁面記錄了 **Magnet, Let me get that!**（`ig_magnet`）所支援的 Minecraft 正式版本、Java 執行環境、Fabric Loader 相依性以及建置工具鏈。

---

## 📊 多版本生命週期概覽

| 目標 Minecraft | 子專案資料夾 | 當前模組版本 | Java 目標 | Fabric Loader | Fabric API | DasikLibrary | 設定介面庫 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | `Magnet v26.2/magnet` | `1.3.9+26.2` | Java 25 (`release = 25`) | `>=0.19.1` | `0.150.1+26.2` | `1.8.23` | YetAnotherConfigLib v3 (`3.9.5+26.2-fabric`) + ModMenu |
| **Minecraft 26.1.2** | `Magnet v26.1/magnet` | `1.1.2+26.1.2` | Java 25 (`release = 25`) | `>=0.16.10` | `0.145.4+26.1.2` | `1.8.23` | Cloth Config Fabric (`26.1.154`) + ModMenu |

---

## 🔍 版本詳細解析

### 🟢 Minecraft 26.2 (`1.3.9+26.2`)
* **狀態**：現代主力發布版本
* **子專案路徑**：`Magnet v26.2/magnet/`
* **產出 JAR 檔名**：`Magnet-Let-me-get-that-1.3.9+26.2.jar`
* **中央歸檔目錄**：`Archive Jar of all versions/MC 26.2/`
* **相依性範圍 (`fabric.mod.json`)**：
  ```json
  "depends": {
      "fabricloader": ">=0.19.1",
      "minecraft": ">=26.2-",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **核心架構特性**：
  * 在 `PlayerMixin` 內部使用 Minecraft 26.2 的 `ValueOutput` 和 `ValueInput` 編解碼器實現持久化 NBT 儲存。
  * 透過 Fabric 生命週期事件 `ServerPlayerEvents.COPY_FROM` 與 `ServerPlayerEvents.AFTER_RESPAWN`，在玩家死亡重生或跨維度傳送時自動保留並同步玩家開關狀態。
  * 快速鍵開關預設綁定為反斜線 `\`（`GLFW_KEY_BACKSLASH`），並具備動態 `ig_magnet$getKeyboardType()` 相容回退機制。
  * 內建 `/magnet debug` 與 `/magnet debug log` 遊戲內診斷指令，並支援寫入專用檔案日誌（`logs/ig_magnet_debug.log`）。
  * 採用基於 YetAnotherConfigLib v3 的現代化 `YaclScreenHelper` 設定介面。

---

### 🟢 Minecraft 26.1.2 (`1.1.2+26.1.2`)
* **狀態**：穩定錨定子專案版本
* **子專案路徑**：`Magnet v26.1/magnet/`
* **產出 JAR 檔名**：`Magnet-Let-me-get-that-1.1.2+26.1.2.jar`
* **中央歸檔目錄**：`Archive Jar of all versions/MC 26.1.2/`
* **相依性範圍 (`fabric.mod.json`)**：
  ```json
  "depends": {
      "fabricloader": ">=0.16.10",
      "minecraft": "*",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **核心架構特性**：
  * 透過 `MagnetPlayerState`（`Map<UUID, Boolean>`）追蹤伺服端併發會話狀態。
  * 快速鍵開關預設綁定為 `Ctrl+M`（`GLFW_KEY_M` + `isControlDown()`）。
  * 透過原生快捷列上方提示 `client.gui.setOverlayMessage(...)` 顯示開關狀態通知。
  * 透過 `ClothConfigScreenHelper` 實現反射安全、伺服端不崩潰的選用設定介面。

---

## 📦 自動化發布歸檔與啟動器部署

兩個子專案皆在其 Gradle 建置指令碼（`build.gradle`）中整合了編譯後的自動歸檔任務：

```bash
# 編譯並自動歸檔 MC 26.2 構建：
cd "Magnet v26.2/magnet"
./gradlew build --no-daemon

# 編譯並自動歸檔 MC 26.1.2 構建：
cd "Magnet v26.1/magnet"
./gradlew build --no-daemon
```

當執行 `./gradlew build` 時，`archiveReleaseJar` 任務會自動將編譯產出的 JAR 檔案複製到中央歸檔資料夾（`Archive Jar of all versions/MC <Version>/`），並同步至本機 Modrinth 啟動器的有效測試設定檔目錄中。

---

## 🔗 相關 Wiki 文件
* [[🛠️ MC 26.2 開發環境與工具配置|zh_tw-26.2-Developer-Setup-and-Building]]
* [[🛠️ MC 26.1.2 開發環境與工具配置|zh_tw-26.1.2-Developer-Setup-and-Building]]
* [[🏠 返回中央總覽門戶|zh_tw-Home]]
