# Instant Gratification: Magnet, Let me get that

> "If you can see it, you should have it."

The "Walk of Shame" (walking 5 blocks to pick up a block you just mined) is a cardinal sin of flow state. **Magnet, Let me get that!** is not a utility; it's an extension of the player's will.

An intrinsic, zero-setup item vacuum for the modern player. Fast, aggressive, and ignores physical barriers.

## Features

- **Vacuum Field**: items fly to you instantly.
- **LOS Awareness**: Magnetism respects Line of Sight. Optimized for zero impact on server ticks.
- **Phase Shifting**: Items clip through walls to reach you. No getting stuck.
- **Velocity Injection**: Non-linear acceleration for a snappy feel.
- **XP Sync**: Experience orbs obey the same laws.

## Configuration

Located in the **Misc** category of the Game Rules screen.

- `ig_magnet_enabled` (Default: true): **Enable Item Magnetism** - Master toggle for the entire vacuum system.
- `ig_magnet_range` (Default: 12): **Magnet Vacuum Range** - Radius in blocks to search and pull items.
- `ig_magnet_los_only` (Default: false): **Require Line of Sight** - Only pull items the player can visually see unobstructed.
- `ig_magnet_keep_moving_if_unseen` (Default: true): **Maintain Momentum** - Items continue flying to player if line of sight breaks mid-flight.
- `ig_magnet_speed` (Default: 80%): **Terminal Velocity** - Maximum speed in % items travel when pulled.
- `ig_magnet_acceleration` (Default: 10%): **Snap Acceleration** - How quickly items reach maximum speed in %.
- `ig_magnet_noclip` (Default: true): **Phase Shifting** - Allows items to clip through solid blocks harmlessly.
- `ig_magnet_affects_xp` (Default: true): **Magnetize Experience** - Applies vacuum effect to XP orbs as well as items.
- `ig_magnet_particles` (Default: true): **Show Visual Trails** - Displays cosmetic particle effects tracking magnetized items.
- `ig_magnet_particle_count` (Default: 1): **Visual Trail Density** - Number of particles to spawn per item per tick.
- `ig_magnet_instant` (Default: false): **Instant Teleport Mode** - Items warp directly to inventory with zero travel time.
- `ig_magnet_blocked_by_transparent` (Default: false): **Blocked by Transparent Blocks** - If enabled, the magnet will fail to pull items if the line of sight is obstructed by non-full blocks like Glass, Fences, Slabs, or Stairs.
- `ig_magnet_blocked_by_flora` (Default: false): **Blocked by Flora and Vegetation** - If enabled, the magnet will fail to pull items if the line of sight is obstructed by plant life such as Tall Grass, Leaves, Flowers, or Bushes.
- `ig_magnet_blocked_by_block_entities` (Default: false): **Blocked by Interactive Furniture** - If enabled, the magnet will fail to pull items if the line of sight is obstructed by block entities like Chests, Beds, Doors, Signs, or Shulker Boxes.

## 💖 Support the Project

Keeping this mod open-source, up-to-date, and completely free takes a massive amount of time and dedication. If you like the mod, please support me! Even if you build and use the latest code straight from this repository, downloading the mod on Modrinth or CurseForge generates crucial support that keeps this project alive.

You can also donate directly to help cover hosting and development costs. Every single download, share, and donation really helps me keep this mod open-source and active!

* **Download on Modrinth**: [Modrinth Page](https://modrinth.com/mod/instant-gratification-magnet,-let-me-get-that!)
* **Download on CurseForge**: [CurseForge Page](https://www.curseforge.com/minecraft/mc-mods/instant-gratification-magnet-let-me-get-that)
* **Donate Directly**:
  * [Ko-fi (Global)](https://ko-fi.com/dasikigaijin/tip)
  * [SocioBuzz (Indonesia)](https://sociabuzz.com/dasikigaijin/tribe)
  * [Saweria (Indonesia)](https://saweria.co/DasikIgaijinn)

## License

GPL-3.0-or-later
