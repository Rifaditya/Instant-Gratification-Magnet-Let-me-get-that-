# IG Magnet: Technical Architecture & Maintenance

## 1. Overview
IG Magnet is a core utility of the Instant Gratification collection. It operates fundamentally on an "Extrinsic Vacuum" principle—using velocity interception rather than direct inventory manipulation (unless Instant mode is enabled).

## 2. Core Mechanics

### Standard Pull (`MagnetMovement.java`)
- Hooked via a standard Tick Event (often `ServerTickEvents` iterating players or `PlayerTick`).
- Filters `Entity` within the configured range.
- Ignores players, projectiles, and specifically checks `ig_magnet_affects_xp` for `ExperienceOrb`.
- **Velocity Interpolation**: 
  - Instead of snapping the entity to the player, it uses `Vec3.lerp` to smoothly blend its current momentum with a vector targeting the player's eye level.
  - Controls: `ig_magnet_speed` (terminal velocity) and `ig_magnet_acceleration` (snap factor).
- **Line of Sight (LOS) Integration**:
  - Leverages `DasikLibrary`'s `PlayerVisionTracker` for high-performance visibility checks.
  - **Rules**: Controlled by `ig_magnet_los_only`. If enabled, the magnet only activates if the player has a visual path to the item.
  - **Magnetization State**: Uses an internal `magnet$magnetized` flag (injected into `Entity` via `MixinEntity`) to track if an item has been spotted. This allows `ig_magnet_keep_moving_if_unseen` to function, preventing items from stopping abruptly when passing behind cover mid-pull.
- **NoClip Override**:
  - Sets `entity.noPhysics = true` every active tick to allow items to phase through blocks, satisfying the IG "no walk of shame" philosophy.

### Instant Teleport (`PlayerMixin.java`)
- Triggered by `ig_magnet_instant`.
- Modifies `Player.aiStep()`.
- **Mechanism (AABB Expansion)**: Natively, the player searches for items in the world using a bounding box `pickupArea`. Instead of explicitly teleporting an item to the player inventory (which can bypass item drop stats or sound events), the Mixin simply inflates the player's `pickupArea` by the magnet's `RANGE` GameRule.
- This forces the vanilla inventory extraction loop to seamlessly capture the item instantly upon entering the radius.

## 3. Configuration System
- All configuration is bound to vanilla `GameRule` variables, registered under a custom Category via `DasikLibrary`'s `DynamicGameRuleManager`.
- This ensures native UI rendering without requiring ModMenu / ClothConfig.
- `ig_magnet_instant`: Optionally replaces velocity interpolation with Vanilla AABB collection mechanisms (Instant transfer).
- Advanced LOS Extensions (`ig_magnet_blocked_by_transparent`, `ig_magnet_blocked_by_flora`, `ig_magnet_blocked_by_block_entities`): Filters a secondary `BlockGetter.traverseBlocks` micro-raycast, letting transparent blocks, plants, or interactive furniture break lines of sight without modifying `PlayerVisionTracker`'s core broadphase.
- Translations are managed in `assets/magnet/lang/en_us.json`.

## 4. Dependencies
- Native reliance on `DasikLibrary` for GameRule injection.
- Standalone mod structure enforced. No Jar-In-Jar (`include`) allowed per Standard Mod Architecture.
