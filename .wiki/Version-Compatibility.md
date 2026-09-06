# 🗺️ Multi-Era Version Compatibility Matrix

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

This page documents the supported Minecraft release drops, Java execution environments, Fabric Loader dependencies, and build toolchains for **Magnet, Let me get that!** (`ig_magnet`).

---

## 📊 Multi-Version Lifecycle Overview

| Target Minecraft | Subproject Folder | Active Mod Version | Java Target | Fabric Loader | Fabric API | DasikLibrary | Config GUI Provider |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | `Magnet v26.2/magnet` | `1.3.9+26.2` | Java 25 (`release = 25`) | `>=0.19.1` | `0.150.1+26.2` | `1.8.23` | YetAnotherConfigLib v3 (`3.9.5+26.2-fabric`) + ModMenu |
| **Minecraft 26.1.2** | `Magnet v26.1/magnet` | `1.1.2+26.1.2` | Java 25 (`release = 25`) | `>=0.16.10` | `0.145.4+26.1.2` | `1.8.23` | Cloth Config Fabric (`26.1.154`) + ModMenu |

---

## 🔍 Detailed Version Breakdown

### 🟢 Minecraft 26.2 (`1.3.9+26.2`)
* **Status**: Primary Modern Release
* **Subproject Path**: `Magnet v26.2/magnet/`
* **Archives Output**: `Magnet-Let-me-get-that-1.3.9+26.2.jar`
* **Central Archive Directory**: `Archive Jar of all versions/MC 26.2/`
* **Dependency Bounds (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.19.1",
      "minecraft": ">=26.2-",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **Key Architectural Features**:
  * Persistent NBT storage using Minecraft 26.2 `ValueOutput` and `ValueInput` codecs inside `PlayerMixin`.
  * Automatic player state retention across death and dimension teleportation via Fabric Lifecycle `ServerPlayerEvents.COPY_FROM` and `ServerPlayerEvents.AFTER_RESPAWN`.
  * Keybind toggle mapped to `\` (`GLFW_KEY_BACKSLASH`) with dynamic `ig_magnet$getKeyboardType()` fallback.
  * In-game `/magnet debug` and `/magnet debug log` diagnostic commands with dedicated file logging (`logs/ig_magnet_debug.log`).
  * Modern `YaclScreenHelper` utilizing YetAnotherConfigLib v3.

---

### 🟢 Minecraft 26.1.2 (`1.1.2+26.1.2`)
* **Status**: Modern Anchor Subproject
* **Subproject Path**: `Magnet v26.1/magnet/`
* **Archives Output**: `Magnet-Let-me-get-that-1.1.2+26.1.2.jar`
* **Central Archive Directory**: `Archive Jar of all versions/MC 26.1.2/`
* **Dependency Bounds (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.16.10",
      "minecraft": "*",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **Key Architectural Features**:
  * Concurrent session state tracking via `MagnetPlayerState` (`Map<UUID, Boolean>`).
  * Keybind toggle mapped to `Ctrl+M` (`GLFW_KEY_M` + `isControlDown()`).
  * Native actionbar overlay feedback via `client.gui.setOverlayMessage(...)`.
  * Reflection-safe optional config GUI via `ClothConfigScreenHelper`.

---

## 📦 Automated Release Archiving & Launcher Deployment

Both subprojects integrate automated post-compile archiving in their Gradle build scripts (`build.gradle`):

```bash
# Compile and auto-archive MC 26.2 build:
cd "Magnet v26.2/magnet"
./gradlew build --no-daemon

# Compile and auto-archive MC 26.1.2 build:
cd "Magnet v26.1/magnet"
./gradlew build --no-daemon
```

When `./gradlew build` is executed, the `archiveReleaseJar` task automatically copies the built JAR into the central archive folder (`Archive Jar of all versions/MC <Version>/`) and syncs it with active Modrinth local launcher test profiles.

---

## 🔗 Related Wiki Documentation
* [[MC 26.2 Setup & Tooling|26.2-Developer-Setup-and-Building]]
* [[MC 26.1.2 Setup & Tooling|26.1.2-Developer-Setup-and-Building]]
* [[Return to Central Switchboard Portal|Home]]
