# Changelog

All notable changes to this project will be documented in this file.

## [1.3.18+26.3] - 2026-10-23

### Fixed
- **Player Initialization Crash**: Resolved fatal startup crash during player initialization by correcting the mixin injection target on `Player.aiStep` to `Level.getEntities(Entity, AABB)` descriptor.

## [1.3.17+26.3] - 2026-10-20

### Fixed
- **Velocity Synchronization**: Restored server-to-client velocity synchronization by asserting `entity.hurtMarked = true;` during item pull vectors.
- **Runtime Safety & Anti-Reflection**: Purged obsolete `ModVersionGuard` reflection and `Class.forName` invocations in compliance with the Knot ClassLoader Runtime Safety Standard.
- **Platform Metadata**: Aligned Modrinth project metadata and source links in `fabric.mod.json`.
- **Distribution Integrity**: Fixed release archive output paths and enabled automated External Vault sync.

## [1.3.16+26.3] - 2026-10-18

### Changed
- **UniversalConfigScreen Migration**: Migrated in-game configuration screen to DasikLibrary's `UniversalConfigScreen` and `@ConfigOption` architecture, purging 270 lines of custom YACL boilerplate.
- **Automated Creator Support**: Embedded Ko-fi and Discord creator support touchpoints into all configuration categories.
- **Live GameRule Synchronization**: Adjusting client GUI sliders now synchronizes in real time with active world GameRules.
- **Dependency Update**: Upgraded to DasikLibrary 1.11.2.

## [1.3.15+26.3] - 2026-09-14

### Added
- **Minecraft 26.3 Port**: Initial release supporting Minecraft 26.3 snapshot-6.
- **Dependency Upgrades**: Compiled against Fabric Loader 0.19.3, Fabric API 0.156.1+26.3, and DasikLibrary 1.8.39.
- **Java 25 Baseline**: Upgraded toolchain and execution target to Java 25.
