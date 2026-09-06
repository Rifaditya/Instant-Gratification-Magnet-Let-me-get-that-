# 🧲 Magnet, Let me get that! — Official Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

Welcome to the official technical and gameplay wiki for **Magnet, Let me get that!** (`ig_magnet`), an intrinsic item and experience vacuum mod engineered for modern Minecraft on Fabric.

Built firmly upon the **Instant Gratification (IG)** design philosophy, this mod eliminates the "walk of shame"—the tedious friction of walking 5 blocks to pick up an item you just mined or killed. If you can see it, you should have it.

---

## 🧭 Multi-Version Switchboard Portal

Choose your targeted Minecraft version to access dedicated, isolated gameplay guides, technical documentation, GameRules tables, and architecture references:

| Minecraft Version | Release Status | Active Version Build | Configuration Engine | Portal Link |
| :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | 🟢 Active Modern | `1.3.9+26.2` | YACL v3 + ModMenu | [[26.2-Home]] |
| **Minecraft 26.1.2** | 🟢 Modern Anchor | `1.1.2+26.1.2` | Cloth Config + ModMenu | [[26.1.2-Home]] |

### 🚀 Direct Version Portals:
* 📦 **Minecraft 26.2**: [[👉 Enter Minecraft 26.2 Documentation Portal|26.2-Home]]
* 📦 **Minecraft 26.1.2**: [[👉 Enter Minecraft 26.1.2 Documentation Portal|26.1.2-Home]]

For an in-depth breakdown of toolchains, dependency matrices, archive storage locations, and backwards compatibility, see [[Version-Compatibility]].

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

* **Intelligent 360° Vacuum**: Attracts dropped items and experience orbs within a configurable block radius (default: 12 blocks, up to 64).
* **Phase Shifting (NoClip)**: Magnetized items pass effortlessly through solid walls, preventing drops from becoming permanently stuck inside blast debris or quarry crevices.
* **Line-of-Sight (LOS) Awareness**: Features primary 360° spherical raycasting via DasikLibrary's `PlayerVisionTracker` and optional granular filtering against transparent blocks (glass), flora (tall grass, leaves), and block entities (chests).
* **Momentum Continuity (`keepMovingIfUnseen`)**: Once magnetized in line of sight, items retain their homing momentum even if they temporarily swing behind obstacles.
* **Instant Pickup Option**: Expand the player's native collection bounding box to instantly absorb items with zero travel latency.
* **Player Hotkey & Command Control**: Toggle magnetism client-side via keybind (`\` on 26.2, `Ctrl+M` on 26.1.2) or server-side via `/magnet toggle`.
* **Zero Inventory Clutter**: 100% intrinsic functionality—no custom magnet items, baubles, or energy batteries required.

---

## 📚 Encyclopedic Navigation

### 🎮 Player & Administrator Guides
* [[MC 26.2 Overview|26.2-Home]] & [[MC 26.1.2 Overview|26.1.2-Home]]
* [[MC 26.2 Vacuum & Phase Shifting|26.2-Vacuum-and-Phase-Shifting]] & [[MC 26.1.2 Vacuum & Phase Shifting|26.1.2-Vacuum-and-Phase-Shifting]]
* [[MC 26.2 Line-of-Sight Checks|26.2-Line-of-Sight-and-Obstruction]] & [[MC 26.1.2 Line-of-Sight Checks|26.1.2-Line-of-Sight-and-Obstruction]]
* [[MC 26.2 Experience Orb Attraction|26.2-Experience-Orb-Attraction]] & [[MC 26.1.2 Experience Orb Attraction|26.1.2-Experience-Orb-Attraction]]
* [[MC 26.2 Instant Pickup Mode|26.2-Instant-Pickup-Mode]] & [[MC 26.1.2 Instant Pickup Mode|26.1.2-Instant-Pickup-Mode]]
* [[MC 26.2 Player Toggles & Persistence|26.2-Player-Toggle-and-Persistence]] & [[MC 26.1.2 Player Toggles & Persistence|26.1.2-Player-Toggle-and-Persistence]]
* [[MC 26.2 GameRules Reference|26.2-GameRules]] & [[MC 26.1.2 GameRules Reference|26.1.2-GameRules]]
* [[MC 26.2 Commands|26.2-Commands]] & [[MC 26.1.2 Commands|26.1.2-Commands]]
* [[MC 26.2 Advancements|26.2-Advancements]] & [[MC 26.1.2 Advancements|26.1.2-Advancements]]
* [[MC 26.2 Configuration GUI|26.2-Configuration]] & [[MC 26.1.2 Configuration GUI|26.1.2-Configuration]]
* [[MC 26.2 HUD & Diagnostics|26.2-HUD-and-Diagnostics]] & [[MC 26.1.2 HUD & Diagnostics|26.1.2-HUD-and-Diagnostics]]

### 💻 Developer & Contributor Documentation
* [[MC 26.2 Developer Setup & Building|26.2-Developer-Setup-and-Building]] & [[MC 26.1.2 Developer Setup & Building|26.1.2-Developer-Setup-and-Building]]
* [[MC 26.2 Architecture & Mixin Targets|26.2-Architecture-and-Mixins]] & [[MC 26.1.2 Architecture & Mixin Targets|26.1.2-Architecture-and-Mixins]]
* [[MC 26.2 API & Addon Integration|26.2-API-and-Addon-Integration]] & [[MC 26.1.2 API & Addon Integration|26.1.2-API-and-Addon-Integration]]
* [[Multi-Era Version Compatibility Matrix|Version-Compatibility]]

---

## ⚖️ License & Attribution

Developed by **Dasik (Rifaditya)** under the **GNU General Public License v3.0 (GPLv3)**. See `LICENSE` for complete terms and legal permissions.
