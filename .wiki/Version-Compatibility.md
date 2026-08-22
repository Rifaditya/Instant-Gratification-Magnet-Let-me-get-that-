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

## 🔍 Minecraft 26.2 Specification Details

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

---

## 🔗 Related Wiki Documentation
* [[Developer Setup & Building|Developer-Setup-and-Building]]
* [[Architecture & Mixin Targets|Architecture-and-Mixins]]
* [[Return to Home Portal|Home]]
