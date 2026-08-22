# 🧲 Magnet, Let me get that! — Minecraft 26.2 Wiki

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

Welcome to the standalone documentation for **Magnet, Let me get that!** on Minecraft 26.2 (Build `1.3.9+26.2`).

Built upon the **Instant Gratification (IG)** design philosophy, this mod eliminates the "walk of shame"—the friction of walking 5 blocks to pick up an item you just mined or killed. If you can see it, you should have it.

---

## 📋 Minecraft 26.2 Specifications

| Specification | Target Value | Reference Identifier |
| :--- | :--- | :--- |
| **Minecraft Release Target** | `26.2` | `"minecraft": ">=26.2-"` |
| **Active Subproject Build** | `1.3.9+26.2` | `mod_version=1.3.9+26.2` |
| **Java Toolchain** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.19.1` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.150.1+26.2` | `fabric_version=0.150.1+26.2` |
| **Core Shared Library** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **Default Client Keybind** | `\` (Backslash) | `GLFW.GLFW_KEY_BACKSLASH` |
| **Configuration GUI** | YetAnotherConfigLib v3 | `YaclScreenHelper` + ModMenu |
| **Diagnostic Commands** | `/magnet toggle`, `/magnet debug`, `/magnet debug log` | `MagnetCommand.java` |

---

## ⚡ Core Feature Matrix

```
                      +-----------------------------+
                      |   PLAYER VACUUM EMITTER     |
                      +--------------+--------------+
                                     |
              +----------------------+----------------------+
              |                                             |
              v                                             v
  +-----------------------+                     +-----------------------+
  |  STANDARD PULL MODE   |                     |  INSTANT PICKUP MODE  |
  +-----------+-----------+                     +-----------+-----------+
              |                                             |
     [Line-of-Sight Check]                         [AABB Box Inflation]
     [Spherical Raycast 360°]                      [Zero Travel Latency]
     [Phase-Shift NoClip]                          [Direct Inventory]
     [Dynamic Lerp Velocity]                                |
              |                                             |
              +----------------------+----------------------+
                                     |
                                     v
                      +-----------------------------+
                      |   ITEM & XP ORB CAPTURED    |
                      +-----------------------------+
```

* **Intelligent 360° Vacuum**: Attracts dropped items and experience orbs within a configurable block radius (default: 12 blocks, up to 64). See [[Vacuum-and-Phase-Shifting]].
* **Phase Shifting (NoClip)**: Magnetized items pass effortlessly through solid walls, preventing drops from becoming trapped. See [[Vacuum-and-Phase-Shifting]].
* **Line-of-Sight (LOS) Awareness**: Features primary 360° spherical raycasting via DasikLibrary's `PlayerVisionTracker` and optional granular filtering against transparent blocks, flora, and block entities. See [[Line-of-Sight-and-Obstruction]].
* **Momentum Continuity (`keepMovingIfUnseen`)**: Items retain pull momentum even if they temporarily swing behind obstacles. See [[Line-of-Sight-and-Obstruction]].
* **Experience Orb Attraction**: Fully synchronized XP orb vacuuming with global particle pooling. See [[Experience-Orb-Attraction]].
* **Instant Pickup Option**: Expand the player's collection bounding box to instantly absorb items with 0 travel delay. See [[Instant-Pickup-Mode]].
* **Persistent Player State**: Toggles survive deaths, respawns, dimension changes, and server restarts via NBT codecs. See [[Player-Toggle-and-Persistence]].
* **In-Game Command Suite**: Built-in `/magnet debug` and `/magnet debug log` diagnostic tools. See [[Commands]] and [[HUD-and-Diagnostics]].

---

## 📚 Encyclopedic Documentation Index

### 🎮 Player & Administrator Guides
* [[Vacuum & Phase Shifting Physics|Vacuum-and-Phase-Shifting]]
* [[Line of Sight & Obstacles|Line-of-Sight-and-Obstruction]]
* [[Experience Orb Attraction|Experience-Orb-Attraction]]
* [[Instant Pickup Mode|Instant-Pickup-Mode]]
* [[Player Toggle & Persistence|Player-Toggle-and-Persistence]]
* [[GameRules Complete Reference|GameRules]]
* [[Brigadier Commands & In-Game Diagnostics|Commands]]
* [[Advancements & Progression Scope|Advancements]]
* [[YACL Configuration GUI|Configuration]]
* [[HUD, Visuals & Diagnostics|HUD-and-Diagnostics]]

### 💻 Developer & Engineering Guides
* [[Setup & Loom Building|Developer-Setup-and-Building]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[API & Addon Integration|API-and-Addon-Integration]]
* [[Version Compatibility Matrix|Version-Compatibility]]

---

## ⚖️ License & Attribution

Developed by **Dasik (Rifaditya)** under the **GNU General Public License v3.0 (GPLv3)**.
