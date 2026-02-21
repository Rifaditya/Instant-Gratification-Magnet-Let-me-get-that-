# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0+build.2] - 2026-02-21

### Changed

- **Refactor**: Replaced legacy `GameRulesInvoker` mixins and registry boilerplate with standard `DynamicGameRuleManager` calls.
- **Dependency**: Added `dasik-library` as a standalone compile dependency.
- **Optimization**: Eliminated `magnet.mixins.json` and static language translations.

## [1.0.0+build.1] - 2026-02-21

### Changed

- **Documentation**: Replaced "Architect" with "Creator" in Platform Page Author roles.

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
