# 🗺️ 跨时代版本兼容性矩阵

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

本页面记录了 **Magnet, Let me get that!**（模组 ID：`ig_magnet`）所支持的 Minecraft 发布版本、Java 运行环境、Fabric Loader 依赖以及构建工具链。

---

## 📊 多版本生命周期概览

| 目标 Minecraft 版本 | 子项目目录 | 当前模组版本 | Java 目标 | Fabric Loader | Fabric API | DasikLibrary | 配置界面支持库 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | `Magnet v26.2/magnet` | `1.3.9+26.2` | Java 25 (`release = 25`) | `>=0.19.1` | `0.150.1+26.2` | `1.8.23` | YetAnotherConfigLib v3 (`3.9.5+26.2-fabric`) + ModMenu |
| **Minecraft 26.1.2** | `Magnet v26.1/magnet` | `1.1.2+26.1.2` | Java 25 (`release = 25`) | `>=0.16.10` | `0.145.4+26.1.2` | `1.8.23` | Cloth Config Fabric (`26.1.154`) + ModMenu |

---

## 🔍 版本详细解析

### 🟢 Minecraft 26.2 (`1.3.9+26.2`)
* **状态**：主要现代发布版
* **子项目路径**：`Magnet v26.2/magnet/`
* **归档产物名称**：`Magnet-Let-me-get-that-1.3.9+26.2.jar`
* **中央归档目录**：`Archive Jar of all versions/MC 26.2/`
* **依赖边界声明 (`fabric.mod.json`)**：
  ```json
  "depends": {
      "fabricloader": ">=0.19.1",
      "minecraft": ">=26.2-",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **核心架构特性**：
  * 在 `PlayerMixin` 中采用 Minecraft 26.2 的 `ValueOutput` 和 `ValueInput` Codec 机制实现持久化 NBT 存储。
  * 通过 Fabric 生命周期事件 `ServerPlayerEvents.COPY_FROM` 与 `ServerPlayerEvents.AFTER_RESPAWN`，实现玩家死亡重生与跨维度传送后的状态自动保留。
  * 默认快捷键绑定为 `\`（`GLFW_KEY_BACKSLASH`），并具有动态的 `ig_magnet$getKeyboardType()` 回退处理。
  * 内置游戏内 `/magnet debug` 与 `/magnet debug log` 诊断指令套件，支持专属独立文件日志记录（`logs/ig_magnet_debug.log`）。
  * 采用 YetAnotherConfigLib v3 的现代 `YaclScreenHelper` 配置界面。

---

### 🟢 Minecraft 26.1.2 (`1.1.2+26.1.2`)
* **状态**：现代基石版本子项目
* **子项目路径**：`Magnet v26.1/magnet/`
* **归档产物名称**：`Magnet-Let-me-get-that-1.1.2+26.1.2.jar`
* **中央归档目录**：`Archive Jar of all versions/MC 26.1.2/`
* **依赖边界声明 (`fabric.mod.json`)**：
  ```json
  "depends": {
      "fabricloader": ">=0.16.10",
      "minecraft": "*",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **核心架构特性**：
  * 基于 `MagnetPlayerState`（`Map<UUID, Boolean>`）的并发会话状态追踪。
  * 默认快捷键绑定为 `Ctrl+M`（`GLFW_KEY_M` + `isControlDown()`）。
  * 通过 `client.gui.setOverlayMessage(...)` 实现原生快捷栏上方悬浮文本提示。
  * 基于 `ClothConfigScreenHelper` 的反射安全型可选配置 GUI。

---

## 📦 自动发布归档与启动器部署

两个子项目的 Gradle 构建脚本（`build.gradle`）均深度集成了编译后的全自动归档功能：

```bash
# 编译并自动归档 MC 26.2 构建：
cd "Magnet v26.2/magnet"
./gradlew build --no-daemon

# 编译并自动归档 MC 26.1.2 构建：
cd "Magnet v26.1/magnet"
./gradlew build --no-daemon
```

当执行 `./gradlew build` 时，`archiveReleaseJar` 任务会自动将编译生成的 JAR 复制到中央归档目录（`Archive Jar of all versions/MC <Version>/`），并实时同步到活跃的 Modrinth 本地启动器测试配置目录中。

---

## 🔗 相关 Wiki 文档
* [[MC 26.2 开发者配置与工具链|zh_cn-26.2-Developer-Setup-and-Building]]
* [[MC 26.1.2 开发者配置与工具链|zh_cn-26.1.2-Developer-Setup-and-Building]]
* [[返回中央控制台门户|zh_cn-Home]]
