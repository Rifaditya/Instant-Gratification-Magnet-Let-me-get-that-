# Changelog History

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
