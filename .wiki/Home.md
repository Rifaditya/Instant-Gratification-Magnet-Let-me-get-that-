# 🧲 Magnet, Let me get that! — Minecraft 26.1.2 Wiki

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

Welcome to the standalone documentation for **Magnet, Let me get that!** on Minecraft 26.1.2 (Build `1.1.2+26.1.2`).

Built firmly upon the **Instant Gratification (IG)** design philosophy, this mod eliminates the "walk of shame"—the tedious friction of walking 5 blocks to pick up an item you just mined or killed. If you can see it, you should have it.

---

## 📋 Minecraft 26.1.2 Specifications

| Specification | Target Value | Reference Identifier |
| :--- | :--- | :--- |
| **Minecraft Release Target** | `26.1.2` | `"minecraft": "*"` |
| **Active Subproject Build** | `1.1.2+26.1.2` | `mod_version=1.1.2+26.1.2` |
| **Java Toolchain** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.16.10` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` | `fabric_version=0.145.4+26.1.2` |
| **Core Shared Library** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **Default Client Keybind** | `Ctrl+M` | `GLFW.GLFW_KEY_M` + `isControlDown()` |
| **Configuration GUI** | Cloth Config Fabric | `ClothConfigScreenHelper` + ModMenu |
| **Server Commands** | `/magnet toggle`, `/ig_magnet toggle` | `MagnetCommand.java` |

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
* **Concurrent Session Toggles**: Keybind and command state managed via `MagnetPlayerState`. See [[Player-Toggle-and-Persistence]].
* **Server Command Support**: Toggle magnetism via `/magnet toggle` for vanilla client players. See [[Commands]].

---

## 📚 Encyclopedic Documentation Index

### 🎮 Player & Administrator Guides
* [[Vacuum & Phase Shifting Physics|Vacuum-and-Phase-Shifting]]
* [[Line of Sight & Obstacles|Line-of-Sight-and-Obstruction]]
* [[Experience Orb Attraction|Experience-Orb-Attraction]]
* [[Instant Pickup Mode|Instant-Pickup-Mode]]
* [[Player Toggle & State Management|Player-Toggle-and-Persistence]]
* [[GameRules Complete Reference|GameRules]]
* [[Server Commands & Vanilla Client Support|Commands]]
* [[Advancements & Progression Scope|Advancements]]
* [[Cloth Config GUI & ModMenu|Configuration]]
* [[HUD, Visuals & Overlay|HUD-and-Diagnostics]]

### 💻 Developer & Engineering Guides
* [[Setup & Loom Building|Developer-Setup-and-Building]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[API & Addon Integration|API-and-Addon-Integration]]
* [[Version Compatibility Matrix|Version-Compatibility]]

---

## ⚖️ License & Attribution

Developed by **Dasik (Rifaditya)** under the **GNU General Public License v3.0 (GPLv3)**.
