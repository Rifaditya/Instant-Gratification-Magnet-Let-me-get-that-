# 🧲 Magnet, Let me get that!

<p align="left">
    <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
    <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
</p>

> **"If you can see it, you should have it."**

The "Walk of Shame" (walking 5 blocks to pick up a block you just mined) is a cardinal sin of flow state. **Magnet, Let me get that!** is not a utility; it's an extension of the player's will.

An intrinsic, zero-setup item vacuum for the modern player. Fast, aggressive, and ignores physical barriers. Part of the **Instant Gratification Collection**.

---

## ✨ Features

- **Vacuum Field**: Items and XP Orbs fly to you smoothly.
- **LOS Awareness**: Magnetism respects Line of Sight. Optimized via `DasikLibrary` `PlayerVisionTracker` for zero impact on server ticks.
- **Phase Shifting (NoClip)**: Items clip through walls to reach you. No getting stuck on blocks.
- **Velocity Injection**: Lerp acceleration creates a snappy, responsive feel.
- **Player Toggle**: Individual personal magnet toggle via keybind (`\` backslash) or `/magnet toggle`.
- **NBT Persistence**: Toggle preferences persist across death, respawn, and dimension travel.
- **Lag Hardened**: Capped particle sources (`ig_magnet_max_particle_sources`) to eliminate lag during massive item drops.

---

## ⚙️ Configuration (GameRules)

All settings are configured in-game via the vanilla **Edit Game Rules** screen under the **Magnet** category, or via `/gamerule` commands.

| GameRule | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `ig_magnet_enabled` | Boolean | `true` | Master toggle for the entire vacuum system. |
| `ig_magnet_range` | Integer | `12` | Radius in blocks to search and pull items (1-64). |
| `ig_magnet_noclip` | Boolean | `true` | Allows attracted items to pass through solid blocks. |
| `ig_magnet_affects_xp` | Boolean | `true` | Whether the magnet attracts Experience Orbs. |
| `ig_magnet_particles` | Boolean | `true` | Spawns visual particle trails tracking items. |
| `ig_magnet_particle_count` | Integer | `1` | Number of particles to spawn per item per tick. |
| `ig_magnet_max_particle_sources` | Integer | `5` | Maximum items allowed to spawn particles per tick (lag prevention). |
| `ig_magnet_speed` | Integer | `80` | Speed percentage items travel towards player (1-1000). |
| `ig_magnet_acceleration` | Integer | `10` | Acceleration percentage per tick (1-1000). |
| `ig_magnet_instant` | Boolean | `false` | Teleports items directly to inventory with zero travel time. |
| `ig_magnet_los_only` | Boolean | `true` | Requires line of sight to attract items. |
| `ig_magnet_keep_moving_if_unseen` | Boolean | `true` | Items continue pull momentum if line of sight breaks mid-flight. |
| `ig_magnet_blocked_by_transparent` | Boolean | `false` | Blocks line of sight through glass, slabs, stairs, etc. |
| `ig_magnet_blocked_by_flora` | Boolean | `false` | Blocks line of sight through tall grass, flowers, leaves. |
| `ig_magnet_blocked_by_block_entities` | Boolean | `false` | Blocks line of sight through chests, beds, shulker boxes. |

---

## 💬 Commands

- `/magnet toggle` (or `/ig_magnet toggle`): Toggle your personal magnet vacuum field.
- `/magnet debug`: Display in-game diagnostic information (UUID, GameRule states, nearby item counts, LOS check results).
- `/magnet debug log`: Toggle file logging to `logs/ig_magnet_debug.log`.

---

## 💖 Support the Project

Keeping this mod open-source, up-to-date, and completely free takes a massive amount of time and dedication. If you like the mod, please support me!

- **Download on Modrinth**: [Modrinth Page](https://modrinth.com/mod/instant-gratification-magnet,-let-me-get-that!)
- **Download on CurseForge**: [CurseForge Page](https://www.curseforge.com/minecraft/mc-mods/instant-gratification-magnet-let-me-get-that)
- **Donate Directly**:
  - [Ko-fi (Global)](https://ko-fi.com/dasikigaijin/tip)
  - [SocioBuzz (Indonesia)](https://sociabuzz.com/dasikigaijin/tribe)
  - [Saweria (Indonesia)](https://saweria.co/DasikIgaijinn)

---

## 📜 License

Licensed under **GNU General Public License v3.0** (`GPL-3.0-or-later`).
