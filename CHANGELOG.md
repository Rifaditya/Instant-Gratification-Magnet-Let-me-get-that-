# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [1.0.1+1.20.1] - 2026-10-23

### Fixed
- **Player Initialization Crash**: Resolved fatal startup crash during player initialization by correcting the mixin injection target on `Player.aiStep` to `Level.getEntities(Entity, AABB)` descriptor.

## [1.0.0+1.20.1] - 2026-10-20

### Added
- **Multi-Era Anchor Support**: Ground-zero initial release for Minecraft 1.20.1 (Java 17).
- **Intrinsic Item Magnetism**: Pull items and XP orbs toward the player with customizable velocity, acceleration, and LOS awareness.
- **Dynamic GameRules Suite**: Configurable via `/gamerule ig:magnet_*` with real-time GameRule synchronization.
- **UniversalConfigScreen Integration**: Full client configuration GUI powered by DasikLibrary and ModMenu.
- **LOS-Aware Line of Sight Raycasting**: Powered by DasikLibrary's 360° spherical raytracing engine.
- **Toggle Keybinding**: Client-side toggle keybinding (`\`) with instant status HUD overlay message.
