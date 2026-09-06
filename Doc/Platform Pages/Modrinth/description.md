<p align="center">
  <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
  <a href="https://modrinth.com/mod/dasik-library"><img src="https://img.shields.io/badge/Requires-Dasik_Library-8A2BE2?style=for-the-badge" alt="Requires Dasik Library"></a>
  <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License GPLv3">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

# 🧲 Magnet (Let Me Get That!)

> **"If You Can See It, You Should Have It. Pure Magnetic Suction at Your Command."**

> [!NOTE]
> **1 Jar 1 Version Policy:** I build **1 dedicated JAR for each Minecraft version** (e.g. MC 26.2, MC 26.3). Please download the exact build that matches your Minecraft installation.
> <br><br>
> **Dependency Requirement:** For modern Minecraft 26.x releases (26.1.2, 26.2, 26.3+), this mod requires both **Fabric API** and **Dasik Library** (`v1.7.4+`).

Tired of watching your mined diamonds tumble down a dark ravine into lava? Tired of awkwardly swimming through water streams, hopping over fences, or digging through dirt piles just to retrieve scattered saplings and mob drops? 

**Magnet (Let Me Get That!)** gives your player intrinsic magnetic attraction. It creates a seamless, configurable vacuum field that draws ground items and experience orbs straight to you. No bulky magnetic trinkets to equip, no battery power to charge, and no artificial inventory restrictions—just pure, effortless convenience.

Part of the **Instant Gratification Collection** — mods that respect the player's time.

---

## ✨ Features

### 🌪️ The Intrinsic Vacuum Field
Command the materials around you with surgical precision:

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.53.38.png" alt="Items being vacuumed to player" width="85%"><br>
  <em>Smooth aerodynamic suction drawing dropped items directly toward player</em><br><br>
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.53.41.png" alt="Items phasing through solid obstacles" width="85%"><br>
  <em>Noclip mode enabled: items effortlessly phase through solid blocks and walls to reach you</em>
</p>

- **No Equipment Needed**: Magnetism is an innate player capability. Keep your armor and off-hand free for shields, torches, and weapons.
- **Phase Shifting (NoClip)**: Items never get stuck behind trees, under stairs, or beneath ceilings. With noclip enabled, they glide directly through solid terrain straight into your inventory.
- **Line of Sight (LOS) Physics**: By default, magnetism respects Line of Sight. Items behind thick dungeon walls remain undisturbed until you explore and see them.
- **Smooth Terminal Velocity & Acceleration**: Items don't snap awkwardly—they accelerate along smooth curved parabolic trajectories towards your player.
- **Experience Orb Vacuum**: Experience orbs obey the exact same magnetic pull, ensuring zero lost XP after boss battles and farm harvests.

### 🎮 Personalized Player Toggles & Hotkeys
Full autonomy over your personal magnetic field:
- **Dedicated Keybinding**: Press **`\` (backslash)** or rebind to **`M`** (or any key of your choice) in the standard Minecraft Controls menu to toggle suction on or off in an instant.
- **Chat Commands**: Toggle your personal field anytime with `/magnet toggle` or `/ig_magnet toggle`.
- **NBT Save State Persistence**: Your personal toggle state is saved directly into player data (`level.dat`). It persists through player deaths, dimension transitions (Nether, End), and server reboots.
- **Action Bar Feedback**: Toggling triggers a clean HUD overlay notification confirming whether your personal magnet is active.

### ⚡ Instant Teleport Pickup Mode
Prefer zero delay? Enable `ig:magnet_instant true` to make items instantly teleport into your inventory the microsecond they enter your magnetic radius, completely bypassing travel time.

### 🎨 Cosmetic Particle Trails & Lag Guard
Customizable particle cues trace the flight path of magnetized loot:

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.32.18.png" alt="Cosmetic particle trails on magnetized items" width="85%"><br>
  <em>Luminescent particle trails follow attracted loot with per-tick source throttling</em>
</p>

- **Source Throttling**: The `ig:magnet_max_particle_sources` GameRule limits how many entities emit particles simultaneously, preventing visual clutter and GPU lag during massive quarry drops.

### 🔍 Advanced Line of Sight (LOS) Fine-Tuning
Take granular control over what blocks your view:
- **Transparent Blocks**: Choose whether glass, tinted glass, and water block suction (`ig:magnet_blocked_by_transparent`).
- **Flora & Foliage**: Configure whether tall grass, flowers, and leaves obstruct attraction (`ig:magnet_blocked_by_flora`).
- **Block Entities**: Toggle whether chests, barrels, and shulker boxes block line of sight (`ig:magnet_blocked_by_block_entities`).
- **Momentum Persistence**: If line of sight is broken mid-flight, items keep moving along their current momentum (`ig:magnet_keep_moving_if_unseen`).

### 🧩 Compatibility & HUD Integration
- **Server-Side Native**: 100% compatible with vanilla clients! Vanilla players can join dedicated servers running the mod without needing to install anything on their PC.
- **Item Clumps Synergy**: Automatically detected by Item Clumps to prevent item merging from interrupting active suction trajectories.
- **YetAnotherConfigLib (YACL) & ModMenu**: Optional in-game settings screen in singleplayer.

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.27.56.png" alt="Native Minecraft Game Rules Screen for Magnet" width="85%"><br>
  <em>Native in-game Edit Game Rules screen showing the complete Magnet category</em>
</p>

---

## 📊 Quick Reference & Mechanics Matrix

| Setting / Mechanic | Default Value | Valid Range | Operational Description |
| :--- | :---: | :---: | :--- |
| **Global Master Switch** | `true` | `true / false` | Central GameRule toggling the magnet system server-wide. |
| **Individual Player Toggle** | `true` | `true / false` | Per-player state toggled via hotkey (`\`) or `/magnet toggle`. |
| **Attraction Range** | **`12` blocks** | `1` to `64` blocks | Spherical detection radius centered on the player. |
| **NoClip (Phase Shift)** | `true` | `true / false` | When true, items fly directly through solid blocks. |
| **Experience Orbs** | `true` | `true / false` | When true, experience orbs are vacuumed alongside items. |
| **Suction Velocity** | `80` (80%) | `1` to `1000` | Base velocity at which items travel toward you. |
| **Suction Acceleration** | `10` (10%) | `1` to `1000` | Acceleration curve applied to flying items. |
| **Instant Pickup Mode** | `false` | `true / false` | Teleports items instantly to inventory instead of flying. |
| **Line of Sight (LOS)** | `true` | `true / false` | Requires unobstructed line of sight between eyes and item. |
| **Keep Moving if Unseen** | `true` | `true / false` | Continues momentum if line of sight breaks mid-travel. |

---

## 🚀 In-Game Commands & Quick Start

Use these commands in chat for instant control and live diagnostics:

```text
/magnet toggle                       → Toggle your personal magnet on or off (or press '\')
/ig_magnet toggle                    → Alternative alias for personal toggle
/magnet debug                        → View player UUID, personal toggle, range, and LOS raycast checks
/magnet debug log                    → Toggle persistent file logging to logs/ig_magnet_debug.log
```

---

## ⚙️ Configuration (Native GameRules)

> [!IMPORTANT]
> **💡 Config vs. In-Game GameRules:** The global configuration file (`config/ig_magnet.json`) only defines default values for newly created worlds. In existing worlds, change settings in-game via the **Edit Game Rules** UI screen or the `/gamerule` command.

| GameRule Name | Type | Default | Valid Range | Description |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_enabled` | `Boolean` | `true` | `true / false` | Master toggle for the entire item magnet feature. |
| `ig:magnet_range` | `Integer` | `12` | `1` to `64` | Block radius from which items are attracted. |
| `ig:magnet_noclip` | `Boolean` | `true` | `true / false` | Allows items to pass through solid blocks and terrain. |
| `ig:magnet_affects_xp` | `Boolean` | `true` | `true / false` | Attracts experience orbs in addition to items. |
| `ig:magnet_particles` | `Boolean` | `true` | `true / false` | Spawns cosmetic particle trails on flying items. |
| `ig:magnet_particle_count` | `Integer` | `1` | `0` to `100` | Particles spawned per item per tick. |
| `ig:magnet_max_particle_sources` | `Integer` | `5` | `0` to `100` | Maximum items emitting particles simultaneously to prevent lag. |
| `ig:magnet_speed` | `Integer` | `80` | `1` to `1000` | Speed percentage at which items fly toward player. |
| `ig:magnet_acceleration` | `Integer` | `10` | `1` to `1000` | Acceleration percentage curve for flying items. |
| `ig:magnet_instant` | `Boolean` | `false` | `true / false` | Teleports items immediately into inventory upon entering radius. |
| `ig:magnet_los_only` | `Boolean` | `true` | `true / false` | Requires line of sight between player eyes and item. |
| `ig:magnet_keep_moving_if_unseen` | `Boolean` | `true` | `true / false` | Flying items maintain momentum if LOS breaks mid-flight. |
| `ig:magnet_blocked_by_transparent`| `Boolean` | `false` | `true / false` | Glass and transparent blocks block line of sight. |
| `ig:magnet_blocked_by_flora` | `Boolean` | `false` | `true / false` | Grass and flowers block line of sight. |
| `ig:magnet_blocked_by_block_entities` | `Boolean` | `false` | `true / false` | Chests and block entities block line of sight. |

---

## 📖 In-Depth How-To & Operational Playbook

### 1. Drop-In Setup & Baseline Initialization
1. Place `Magnet-Let-me-get-that-*.jar` along with **Fabric API** and **Dasik Library** into your `mods` directory.
2. Launch Minecraft. On first startup, the mod creates `config/ig_magnet.json` with recommended defaults (`range: 12`, `noClip: true`, `losOnly: true`).

### 2. Live In-Game Tuning vs. Global Template
- **For New Worlds**: Tune baseline preferences in `config/ig_magnet.json` or via ModMenu + YACL.
- **For Existing Worlds**: Open **Edit Game Rules** &rarr; scroll to **Magnet**, or run `/gamerule ig:<rule> <value>` in chat.

### 3. Precision Mining & Nether Exploration Tactics
- When mining near lava lakes in the Nether or deepslate caves, keep Magnet enabled. Ores you mine are pulled instantly to your body before they can touch lava or fall into crevices.
- If you find items flying through solid ground distracting, toggle `ig:magnet_noclip false` so items follow open tunnels and corridors.

### 4. Customizing Keybindings & Server Play
- Open **Options** &rarr; **Controls** &rarr; **Key Binds** &rarr; **Magnet** section. By default, the toggle is bound to **`\` (Backslash)**. Rebind it to **`M`**, **`V`**, or any mouse thumb button.
- On multiplayer SMP servers, each player's toggle state is independent. Turning your magnet off does not disable magnetism for other players on the server.

### 5. Diagnostics & Troubleshooting
- Use `/magnet debug` to verify whether an item is within range and check whether line-of-sight raycasting passes.
- If you encounter unexpected behavior, run `/magnet debug log` to enable detailed tracing in `logs/ig_magnet_debug.log`.

---

## 🧩 Recommended Sister Mods

If you enjoy **Magnet (Let Me Get That!)**, these companion mods from the **Instant Gratification Collection** plug in seamlessly:

* 📦 [**Item Clumps**](https://modrinth.com/mod/ig-item-clumps): Aggressively condenses hundreds of scattered ground items into single lightweight mega-stacks, eliminating entity tick lag before the magnet vacuums them up.
* ⛏️ [**Ore Amplifier**](https://modrinth.com/mod/instant-gratification-ore-amplifier): Multiplies ore vein yields in newly generated chunks so your mining trips yield massive hauls with zero tedious searching.
* 📦 [**Stack Size Adjuster**](https://modrinth.com/mod/ig-stack-size-adjuster): Scale inventory slot limits up to 2.14 Billion, ensuring your inventory never runs out of room while vacuuming drops.

> 🌟 *Explore the full [**Instant Gratification Collection**](https://modrinth.com/collection/instant-gratification) for more high-convenience enhancements.*

---

## ☕ Support

If you enjoy the **Instant Gratification Collection**, consider fueling future development!

<p align="center">
  <a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Ko-fi"></a>
  <a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
  <a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

> [!NOTE]
> **🇮🇩 Indonesian Users:** SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!

> [!TIP]
> **Dedicated Server Hosting Partner:**
> Looking for a reliable server to play with friends? Check out **BisectHosting** for 1-click modpack installations, automated backups, and 24/7 dedicated customer support.

---

## 📜 Credits & Modpack Permissions

| Property | Information |
| :--- | :--- |
| **Creator / Author** | **Dasik** (Rifaditya) |
| **Collection** | Instant Gratification Collection |
| **License** | [GNU General Public License v3.0 (GPLv3)](https://www.gnu.org/licenses/gpl-3.0.html) |
| **Source Code** | [GitHub - Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-](https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-) |
| **Issue Tracker** | [GitHub Issues](https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/issues) |
| **Documentation / Wiki** | [GitHub Wiki](https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/wiki) |

> [!IMPORTANT]
> **📦 Modpack Permissions & Distribution:**<br>
> You are fully welcome to include this mod in any modpack on any platform! However, the mod file must be downloaded directly through official distribution channels (**Modrinth** or **CurseForge**). Re-uploading, mirroring, or redistributing the original mod JAR to third-party mirror sites, scraper portals, or unauthorized launchers is strictly prohibited.
> <br><br>
> **⚖️ License & Fork Guidelines (No Zero-Change Re-uploads):**<br>
> This project is open-source under the **GNU GPLv3**. You are fully encouraged to inspect the code, learn from it, and fork the repository to create genuine modifications, substantial feature expansions, or community ports—provided your project remains open-source under GPLv3 with proper attribution.<br>
> **However, straight 1:1 re-uploads, clone forks with no meaningful functional changes, or re-publishing identical builds under different project names (e.g. to farm downloads or rewards) are strictly forbidden.**

---

<p align="center">
  <strong>Made with ❤️ for the Minecraft community</strong><br>
  <em>Part of the Instant Gratification Collection</em>
</p>
