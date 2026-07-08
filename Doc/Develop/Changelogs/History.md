# Changelog History

## [1.1.0+A-26.1.2] - 2026-06-12

### Added
- **Client Magnet Toggle Hotkey**: Added a client-side keybinding (`Ctrl+M` by default) to toggle the item magnet on/off for the player.
- **Server Toggle Command**: Registered `/ig_magnet toggle` and `/magnet toggle` server-side commands, enabling vanilla clients (server-side only mod setup) to toggle their magnet state, while maintaining sync with modded clients.

## [1.0.10+B-26.1.2] - 2026-06-11

### Fixed
- **Client-Side Visual Ghosting**: Gated all magnetism and movement mixin calls to the logical server, eliminating local client-side prediction desync when pulling items behind blocks.

### Changed
- **Audit Compliance**: Cleaned up unused imports in `SecondaryVisionCheck.java` and `MagnetMovement.java`. Added missing decompile source citation to `MagnetMod.java`.

## [1.0.8+A-26.1.2] - 2026-06-06

### Changed
- **Default Settings**: Changed the default value of Line of Sight Only (`losOnly`) from `false` to `true`.

## [1.0.7+A-26.1.2] - 2026-06-06

### Changed
- **Config GUI Alignment**: Added the static warning text block at the top of all configuration categories (General, Speeds, Line of Sight, and Visuals & Performance) and removed individual setting tooltips.

## [1.0.6+A-26.1.2] - 2026-06-06

### Added
- **Optional Config GUI**: Added ModMenu + Cloth Config integration using reflection-safe isolated loading.
- **JSON Defaults Storage**: Implemented JSON default values storage in `ig_magnet.json` to configure baseline defaults for new worlds.

## [1.0.5+A-26.1.2] - 2026-06-06

### Optimized
- **Lambda Allocation Elimination**: Refactored `SecondaryVisionCheck.java` to pass a static `VisionContext` record and use static method references during block traversal, eliminating heap closures and reducing GC collector pressure.

## [1.0.4+A-26.1.2] - 2026-06-06

### Optimized
- **Tick Gating**: Gated magnet ticking to the server-side to prevent redundant calculations and desync jitter on the client.
- **Class-Specific Scanning**: Upgraded generic entity scanning to class-specific `getEntitiesOfClass` queries, reducing complexity from $O(N)$ (scanning all entities) to $O(M)$ (scanning only items and XP orbs).

## [1.0.3+A-26.1.2] - 2026-06-06

### Fixed
- **Permanent NoClip Bug**: Resolved issue where magnetized items permanently lost gravity/physics and fell through blocks. We now save `noPhysics` on `move:HEAD` and restore it on `move:RETURN`.

## [1.0.2+A-26.1.2] - 2026-06-06

### Changed
- **Mixin Consolidation**: Merged player mixin classes into `PlayerMixin.java` and cleaned up `magnet.mixins.json`.

## [1.0.1+A-26.1.2] - 2026-06-06

### Fixed
- **Client-Side Crash**: Resolved a `ClassCastException` in `MagnetMovement.pull` by replacing the direct `ServerLevel` cast check with `instanceof` and providing a client-side particle fallback.

### Added
- **Lag Prevention Particle Limit**: Added `ig:magnet_max_particle_sources` GameRule to control the maximum number of entities allowed to spawn particles simultaneously per tick (Default: 5).

### Changed
- **Dependency Alignment**: Bumped Loom version to `1.15.2`, fabric API to `0.145.4+26.1.2`, and dasik-library to `1.7.4`.
- **Localization Mismatch**: Restructured `en_us.json` keys to use correct dot-notation for namespaced GameRule keys (e.g. `gamerule.ig.magnet_range` and `.description`).
- **Mixin Compliance**: Renamed all internal Mixin members to use the standardized `ig_magnet$` prefix.
- **Documentation**: Fixed broken file links, sanitized codename references, and corrected markdown structures to be fully CurseForge HTML-compatible.
- **Source Verification**: Added source decompile citations to all Java files.

## [1.0.0+build.11] - Advanced Line of Sight Granularity

### Added
- **Advanced LOS GameRules**: Added three new GameRules to provide ultimate control over what blocks the magnet's line of sight.
    - `ig_magnet_blocked_by_transparent`: "Blocked by Transparent Blocks" (Glass, Fences, Slabs, Stairs).
    - `ig_magnet_blocked_by_flora`: "Blocked by Flora and Vegetation" (Tall Grass, Leaves, Flowers, Bushes).
    - `ig_magnet_blocked_by_block_entities`: "Blocked by Interactive Furniture" (Chests, Beds, Doors, Signs, Shulker Boxes).
- **SecondaryVisionCheck**: Implemented a highly optimized secondary micro-raycast (`BlockGetter.traverseBlocks`) that only triggers if the primary `PlayerVisionTracker` allows the pull. This ensures zero TPS impact when pulling through open air or when blocked by solid walls.

### Changed
- **Documentation Overhaul**: Performed an exhaustive sweep of `en_us.json`, `README.md`, `doc.md`, `gamerules_reference.md`, `concept_magnet.md`, Modrinth, and CurseForge platform pages. All GameRules now feature highly verbose, paragraph-style descriptions to maximize user clarity.
- **Dependency**: Still requires `DasikLibrary` v1.6.9+build.10+.

## [1.0.0+build.10] - 2026-02-28 (Snapshot 10)

### Added
- **Master Toggle**: Added `ig_magnet_enabled` GameRule to allow players/servers to disable the magnet entirely.

## [1.0.0+build.9] - 2026-02-28

### Fixed
- **LOS State Tracking**: Replaced momentum heuristics with a proper `ig$isMagnetized()` flag via Mixin. This prevents the "false-pull" bug where items popped from dispensers or explosions were pulled through walls without being seen.

## [1.0.0+build.8] - 2026-02-28

### Added
- **LOS-Aware Magnetism**: Integrated DasikLibrary's `PlayerVisionTracker`.
- **GameRules**: Added `ig_magnet_los_only` and `ig_magnet_keep_moving_if_unseen` for granular visibility control.

## [1.0.0+build.6] - 2026-02-28

### Changed
- **Internal State**: Extended `IMagnetEntity` to support persistent magnetization tracking.

## [1.0.0+build.5] - 2026-02-27

### Changed
- **Performance & Smoothness**: Restored client-side physics prediction to prevent visual stuttering and lag on item pulling.
- **Responsiveness**: Moved the magnetism hook to the start of the player tick (from tail to head) to eliminate a 1-tick delay upon items reaching the player's pickup radius.
- **Physics**: Removed the 0.5-block stalling safety check that caused items to abruptly slow down at the player's feet before being picked up.

## [1.0.0+build.4] - 2026-02-27

### Fixed
- **Regression**: Restored the core item vacuum functionality. `MixinPlayer` was unintentionally removed from mixin configuration during the Instant Teleport implementation. Both instant and pull mechanics now function concurrently.

## [1.0.0+build.3] - 2026-02-27

### Added

- **Instant Teleport Feature**: `ig_magnet_instant` gamerule now utilizes AABB area expansion on the player to natively capture items with 0 travel time, preserving vanilla pickup logic without duplicating inventory insertion logic.
- **Documentation**: Added comprehensive `doc.md` and synced translation keys for UI.

## [1.0.0+build.2] - 2026-02-21

### Changed

- **Refactor**: Replaced legacy `GameRulesInvoker` mixins and registry boilerplate with standard `DynamicGameRuleManager` calls.
- **Dependency**: Added `dasik-library` as a standalone compile dependency.
- **Optimization**: Eliminated `magnet.mixins.json` and static language translations.


## [1.0.0] - 2026-02-16

### Added

- Initial Release for Minecraft 26.1 Snapshot 7.
- **Vacuum Field**: Items within range fly to the player.
- **Phase Shifting**: Items clip through blocks to reach the player.
- **Velocity Injection**: Snappy, non-linear acceleration.
- **XP Magnet**: Experience orbs are also pulled.
- **Configuration**: GameRules for range, speed, acceleration, and blacklist.
  - `igMagnetRange`
  - `igMagnetSpeed`
  - `igMagnetAcceleration`
  - `igMagnetNoClip`
  - `igMagnetAffectsXP`
  - `igMagnetParticles`
