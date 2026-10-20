# Developer History Ledger: Magnet (Let Me Get That!)

## [1.3.17+26.1.2] - 2026-10-20 - Multi-Era Anchor Port to MC 26.1.2 [BL-MAG-007]

- **Ticket Reference**: [BL-MAG-007]
- **Scope**: Multi-Era Anchor Parity for Minecraft 26.1.2
- **Governance Mandate**: Multi-Era Anchor Parity Law & UniversalConfigScreen Invariant

### Engineering Changes
- **Scaffold & Ground-Zero Sync**: Cloned branch `26.1` and ported modern codebase from Sovereign MC 26.2.
- **Era Adaptation in Client**: Adapted `MagnetModClient` to use `client.gui.setOverlayMessage(...)` instead of `26.2+` `client.gui.hud.setOverlayMessage(...)`.
- **UniversalConfigScreen Migration**: Migrated configuration screen to DasikLibrary `UniversalConfigScreen` with `@ConfigOption` annotations, purging obsolete `YaclScreenHelper.java`.
- **Runtime Safety**: Purged `ModVersionGuard` reflection, satisfying Knot ClassLoader runtime safety.
- **Velocity Synchronization**: Restored `entity.hurtMarked = true;` during item pull vectors.
- **Toolchain & Distribution**: Bounded `minecraft` to `>=26.1.2- <26.2-` on Java 25, Fabric Loader 0.19.5, DasikLibrary 1.11.2, and 4-point archive distribution.

## [1.1.2+26.1.2] - 2026-08-19

### Modernized Raytracing & LOS Engine
* **What**: Upgraded to `dasik-library` 1.8.23 on-demand vision engine, removed obsolete background-polling listener registrations from `MagnetManager.tick()`, and enabled 360° spherical Line-Of-Sight raycasting (`PlayerVisionTracker.canSee(player, entity, range, 360.0)`).
* **Why**: To eliminate useless tick loop registrations and allow items in open rooms behind the player to be magnetized with 0.0001μs distance fast-failing.
* **How**: Updated `MagnetMovement.java`, `MagnetManager.java`, `SecondaryVisionCheck.java`, and `gradle.properties`.

## [1.1.1+26.1.2] - 2026-07-11

### Standardized Warning Notice Tooltips
* **What**: Added per-option `config.ig_magnet.<option>.description` translation keys inside `en_us.json` containing the `§6Notice:§r` warning notice. Updated `ClothConfigScreenHelper.java` to set option tooltips pointing to these new keys.
* **Why**: To clearly warn players that changing configuration settings only defines default values for newly generated worlds, and existing worlds must be changed in-game.
* **How**: Modified `ClothConfigScreenHelper.java` to invoke `.setTooltip(Component.translatable(...))` for all 14 config options. Added translations inside `en_us.json`.

## [1.1.0+A-26.1.2] - 2026-06-12

### Added
- **Client Magnet Toggle Hotkey**: Added a client-side keybinding (`Ctrl+M` by default) to toggle the item magnet on/off for the player.
- **Server Toggle Command**: Registered `/ig_magnet toggle` and `/magnet toggle` server-side commands, enabling vanilla clients (server-side only mod setup) to toggle their magnet state, while maintaining sync with modded clients.

## [1.0.10+B-26.1.2] - 2026-06-11

### Fixed
- **Client-Side Visual Ghosting**: Gated all magnetism and movement mixin calls to the logical server, eliminating local client-side prediction desync when pulling items behind blocks.

### Changed
- **Audit Compliance**: Cleaned up unused imports in `SecondaryVisionCheck.java` and `MagnetMovement.java`. Added missing decompile source citation to `MagnetMod.java`.
