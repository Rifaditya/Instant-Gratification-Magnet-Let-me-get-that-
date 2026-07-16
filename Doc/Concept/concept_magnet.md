# Instant Gratification: Magnet, Let me get that

## Philosophy Fit

### "Let me get that."

The "Walk of Shame" (walking 5 blocks to pick up a block you just mined) is a cardinal sin of flow state. IG Magnet is not a utility; it's an extension of the player's will. If you can see it, you should have it.

Unlike vanilla magnets (tech mods) which often require energy or items, this is intrinsic to the IG experience. It is fast, aggressive, and ignores physical barriers.

## Mechanics

1. **The Vacuum Field**
   - **Description**: A constant 360-degree passive pull on all valid items.
   - **Implementation**: `PlayerTick` event scanning via `level.getEntitiesOfClass`.
   - **Behavior**: Items within **Range** awaken instantly and target the player's eye position.

2. **Phase Shifting (NoCLIP)**
   - **Description**: Items do not get stuck on walls. They phase through terrain to reach the player.
   - **Implementation**: Temporarily set `noClip = true` on the ItemEntity while it is being magnetized. Reset if it travels outside range (edge case).
   - **Configurable**: Can be toggled on/off via GameRules.

3. **Velocity Injection**
   - **Description**: Non-linear acceleration. Creates a "snap" effect where items start moving and quickly reach terminal velocity.
   - **Formula**: Interpolate current velocity towards `vec_to_player.normalize().scale(speed)` using `acceleration`.
   - **Configurable**: Both Speed (Terminal Velocity) and Acceleration (Snap factor) are configurable.

4. **Experience Orb Sync**
   - **Description**: XP Orbs must follow the same rules. Leaving XP behind is unacceptable.
   - **Implementation**: Apply same logic to `ExperienceOrb`.
   - **Configurable**: Can be toggled on/off via GameRules.

## Configuration (GameRules)

- `ig_magnet_range` (int, default 12, min 0, max 64): **Magnet Vacuum Range** - Radius in blocks to search and pull items.
  - **UI**: Must appear in the **"Misc" category** of the Vanilla `Edit Game Rules` screen.
- `ig_magnet_speed` (double, default 0.8, min 0.1, max 5.0): **Terminal Velocity** - Maximum speed in % items travel when pulled.
- `ig_magnet_acceleration` (double, default 0.1, min 0.01, max 1.0): **Snap Acceleration** - How quickly items reach maximum speed in %.
- `ig_magnet_noclip` (boolean, default true): **Phase Shifting** - Allows items to clip through solid blocks harmlessly.
  - **UI**: Must appear in the **"Misc" category** of the Vanilla `Edit Game Rules` screen.
- `ig_magnet_affects_xp` (boolean, default true): **Magnetize Experience** - Applies vacuum effect to XP orbs as well as items.
  - **UI**: Must appear in the **"Misc" category** of the Vanilla `Edit Game Rules` screen.
- `ig_magnet_particles` (boolean, default true): **Show Visual Trails** - Displays cosmetic particle effects tracking magnetized items.
  - **UI**: Must appear in the **"Misc" category** of the Vanilla `Edit Game Rules` screen.
- `ig_magnet_particle_count` (int, default 1, min 0, max 20): **Visual Trail Density** - Number of particles to spawn per item per tick.
  - **UI**: Must appear in the **"Misc" category** of the Vanilla `Edit Game Rules` screen.
- `ig_magnet_blocked_by_transparent` (boolean, default false): **Blocked by Transparent Blocks** - If enabled, the magnet will fail to pull items if the line of sight is obstructed by non-full blocks like Glass, Fences, Slabs, or Stairs.
  - **UI**: Must appear in the **"Misc" category** of the Vanilla `Edit Game Rules` screen.
- `ig_magnet_blocked_by_flora` (boolean, default false): **Blocked by Flora and Vegetation** - If enabled, the magnet will fail to pull items if the line of sight is obstructed by plant life such as Tall Grass, Leaves, Flowers, or Bushes.
  - **UI**: Must appear in the **"Misc" category** of the Vanilla `Edit Game Rules` screen.
- `ig_magnet_blocked_by_block_entities` (boolean, default false): **Blocked by Interactive Furniture** - If enabled, the magnet will fail to pull items if the line of sight is obstructed by block entities like Chests, Beds, Doors, Signs, or Shulker Boxes.
  - **UI**: Must appear in the **"Misc" category** of the Vanilla `Edit Game Rules` screen.

## Assets Needed

| ID | Type | Default | Range | Description |
| --- | --- | --- | --- | --- |
| `ig_magnet_range` | Integer | 12 | 0-64 | Radius in blocks. 0 = disabled. |
| `ig_magnet_speed` | Integer | 80 | 1-100 | % of terminal velocity (80 = 0.8 blocks/tick). |
| `ig_magnet_acceleration` | Integer | 10 | 1-100 | % acceleration per tick. |
| `ig_magnet_noclip` | Boolean | true | true/false | If true, items phase through blocks. |
| `ig_magnet_affects_xp` | Boolean | true | true/false | If true, XP orbs are pulled. |
| `ig_magnet_particles` | Boolean | true | true/false | If true, show particles. |
| `ig_magnet_particle_count` | Integer | 1 | 0-10 | Number of particles. |

## Implementation Checklist

- [x] **Core Logic**: Player Tick Handler scanning for `ItemEntity` and `ExperienceOrb`.
- [x] **Movement**: Velocity interpolation logic using `ig_magnet_speed` and `ig_magnet_acceleration`.
- [x] **Clipping**: `noClip` toggle logic contingent on `ig_magnet_noclip`.
- [x] **XP Sync**: Conditional check for `ig_magnet_affects_xp`.
- [x] **Visuals**: Particle spawning contingent on `ig_magnet_particles` and loop `ig_magnet_particle_count`.
- [x] **Config**: Register GameRules with Category (ensure UI visibility).
- [x] **Verification**: Test with items behind walls, items in water, items in lava.

## To Do

- [x] **Instant Teleport**: Option to instantly get item into player inventory via AABB bounding box expansion.


Todo;
add option to set the amount of item that have particles(becuase if every xp orb have particle the lag is unbearable)
