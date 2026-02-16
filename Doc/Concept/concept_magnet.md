# Instant Gratification: Magnet, Let me get that!

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

- `igMagnetRange` (int, default 12, min 0, max 64): Radius of the vacuum.
  - **UI**: Must appear in the **"Misc" category** of the Vanilla `Edit Game Rules` screen.
- `igMagnetSpeed` (double, default 0.8, min 0.1, max 5.0): Terminal velocity multiplier.
- `igMagnetAcceleration` (double, default 0.1, min 0.01, max 1.0): How fast items reach top speed (1.0 = instant).
- `igMagnetNoClip` (boolean, default true): Whether items clip through blocks (Phase Shifting).
  - **UI**: Must appear in the **"Misc" category** of the Vanilla `Edit Game Rules` screen.
- `igMagnetAffectsXP` (boolean, default true): Whether XP Orbs are magnetized.
  - **UI**: Must appear in the **"Misc" category** of the Vanilla `Edit Game Rules` screen.
- `igMagnetParticles` (boolean, default true): Toggle visual trail effects.
  - **UI**: Must appear in the **"Misc" category** of the Vanilla `Edit Game Rules` screen.
- `igMagnetParticleCount` (int, default 1, min 0, max 20): Number of particles per tick per item.
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

- [ ] **Core Logic**: Player Tick Handler scanning for `ItemEntity` and `ExperienceOrb`.
- [ ] **Movement**: Velocity interpolation logic using `ig_magnet_speed` and `ig_magnet_acceleration`.
- [ ] **Clipping**: `noClip` toggle logic contingent on `ig_magnet_noclip`.
- [ ] **XP Sync**: Conditional check for `ig_magnet_affects_xp`.
- [ ] **Visuals**: Particle spawning contingent on `ig_magnet_particles` and loop `ig_magnet_particle_count`.
- [ ] **Config**: Register GameRules with Category (ensure UI visibility).
- [ ] **Verification**: Test with items behind walls, items in water, items in lava.
