# GameRules Reference

Complete reference for all GameRules registered by **IG: Magnet, Let me get that!**.

## Configuration

| Rule | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `ig:magnet_enabled` | Boolean | true | **Enable Item Magnetism:** Master toggle for the entire vacuum system. |
| `ig:magnet_range` | Integer | 12 | **Magnet Vacuum Range:** Radius in blocks to search and pull items. |
| `ig:magnet_los_only` | Boolean | false | **Require Line of Sight:** Only pull items the player can visually see unobstructed. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | true | **Maintain Momentum:** Items continue flying to player if line of sight breaks mid-flight. |
| `ig:magnet_noclip` | Boolean | true | **Phase Shifting:** Allows items to clip through solid blocks harmlessly. |
| `ig:magnet_affects_xp` | Boolean | true | **Magnetize Experience:** Applies vacuum effect to XP orbs as well as items. |
| `ig:magnet_particles` | Boolean | true | **Show Visual Trails:** Displays cosmetic particle effects tracking magnetized items. |
| `ig:magnet_particle_count` | Integer | 1 | **Visual Trail Density:** Number of particles to spawn per item per tick. |
| `ig:magnet_max_particle_sources` | Integer | 5 | **Max Particle Sources:** Maximum number of entities allowed to spawn particles simultaneously to prevent lag. |
| `ig:magnet_speed` | Integer | 80 | **Terminal Velocity:** Maximum speed in % items travel when pulled. |
| `ig:magnet_acceleration` | Integer | 10 | **Snap Acceleration:** How quickly items reach maximum speed in %. |
| `ig:magnet_instant` | Boolean | false | **Instant Teleport Mode:** Items warp directly to inventory with zero travel time. |
| `ig:magnet_blocked_by_transparent` | Boolean | false | **Blocked by Transparent Blocks:** If enabled, the magnet will fail to pull items if the line of sight is obstructed by non-full blocks like Glass, Fences, Slabs, or Stairs. |
| `ig:magnet_blocked_by_flora` | Boolean | false | **Blocked by Flora and Vegetation:** If enabled, the magnet will fail to pull items if the line of sight is obstructed by plant life such as Tall Grass, Leaves, Flowers, or Bushes. |
| `ig:magnet_blocked_by_block_entities` | Boolean | false | **Blocked by Interactive Furniture:** If enabled, the magnet will fail to pull items if the line of sight is obstructed by block entities like Chests, Beds, Doors, Signs, or Shulker Boxes. |

## Category

All rules appear under the **Miscellaneous** category in the vanilla *Edit Game Rules* screen.
