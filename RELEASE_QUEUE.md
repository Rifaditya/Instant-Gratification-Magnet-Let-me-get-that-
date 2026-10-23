# 📋 Magnet, Let me get that! Release Queue & Backlog

This file tracks which built versions (from the central archive folder "E:\Minecraft Project\Instant Gratification Collection\Magnet Let me get that\Archive Jar of all versions") have been manually uploaded to Modrinth/CurseForge.
Open this file in your editor and change `[ ]` to `[x]` when you publish a version.

## 🚀 Published & Backlog Queue

- [ ] **1.3.18+26.2** (2026-10-23) - - **Crash Fix**: Corrected PlayerMixin injection target descriptor on Level.getEntities to resolve startup player initialization crash.
- [x] **1.3.17+26.2** (SUPERSEDED) (2026-10-20) - - **Runtime Safety**: Purged `ModVersionGuard` reflection in compliance with Knot ClassLoader runtime safety. - - **Metadata & Build**: Aligned Modrinth metadata in `fabric.mod.json` and enabled 4-point archive sync.
- [x] **1.3.16+26.2** (SUPERSEDED) (2026-10-18) - - **UniversalConfigScreen Migration**: Migrated in-game config to DasikLibrary UniversalConfigScreen and @ConfigOption architecture (superseded by 1.3.17+26.2).
- [x] **1.3.14+26.2** (SUPERSEDED) (2026-09-05) - - **DasikLibrary 1.8.39 Alignment**: Upgraded to DasikLibrary 1.8.39 (superseded by 1.3.17+26.2).
- [x] **1.3.13+26.2** (SUPERSEDED) (2026-09-05) - - **Client Side-Safety Annotations**: Annotated YaclScreenHelper, ModMenuIntegration, and MagnetModClient with @Environment(EnvType.CLIENT) (superseded by 1.3.17+26.2).
- [x] **1.3.12+26.2** (SUPERSEDED) (2026-09-05) - - **Mixin Modernization**: Refactored brittle ordinal @ModifyVariable in PlayerMixin to targeted @ModifyArg on Level.getEntitiesOfClass(...) (superseded by 1.3.17+26.2).
- [x] **1.3.11+26.2** (2026-09-05) - - **Config Uniformity**: Added top-pinned Ko-fi creator support button across Speeds, LOS, and Visuals categories. - - **Localization Polish**: Reframed config warning and removed repetitive notice boilerplate from option descriptions in `en_us.json`.
- [x] **1.3.10+26.2** (2026-09-05) - - **Ko-fi Support Integration**: Injected top-pinned Ko-fi creator support button into YACL in-game configuration screen. Upgraded to DasikLibrary 1.8.38.
- [x] **1.3.9+26.2** (2026-08-19) - - **Modernized Raytracing & LOS Engine**: Upgraded to DasikLibrary 1.8.23 on-demand vision engine, removed obsolete per-tick listener registrations, and enabled 360° spherical line-of-sight checks.
- [x] **1.3.8+26.2** (2026-07-22) - - **ModVersionGuard**: Updated Knot ClassLoader resolution.
- [x] **1.3.7+26.2** (SUPERSEDED) (2026-07-22) - Forward Compatibility & Version Guard.
- [x] **1.3.6-26.2+** (SUPERSEDED) (2026-07-21) - - **Cross-Version InputConstants Crash**: Resolved NoSuchFieldError crash on Minecraft 26.3 Snapshot 4 by replacing static InputConstants.Type.KEYSYM references with a dynamic runtime lookup helper. Supports both Minecraft 26.2 and 26.3+.
- [x] **1.3.5-26.2** (2026-07-21) - - **Backend State Architecture Overhaul**: Replaced static Map with instance mixin fields. Integrated ServerPlayerEvents.COPY_FROM for leak-free, 100% reliable respawn state persistence. Added instant pickup safety gating for dead/spectator states.
- [x] **1.3.4-26.2** (2026-07-15) - - **Post-Death Vision Fix & Dedicated Debug Log**: Migrated vision check to vanilla raycast to fix magnet after death. Added dedicated log writer for ig_magnet_debug.log.
- [x] **1.3.3-26.2** (SUPERSEDED) (2026-07-15) - - **Debugging Command & Logs**: Added in-game /magnet debug command and precise logger outputs to resolve player respawn state desync.
- [x] **1.3.2-26.2** (SUPERSEDED) (2026-07-15) - - **Master Toggle Bypass & Performance**: Checked global magnet enabled GameRule in instant pickup area logic. Optimized GC with a ThreadLocal vision context. Standardized mixin prefixing to ig_magnet$.
- [x] **1.3.1-26.2** (SUPERSEDED) (2026-07-14) - - **Instant Pickup Toggle Bypass**: Fixed toggle bypass in instant mode. Removed command double-sync and unused imports.
- [x] **1.3.0-26.2** (SUPERSEDED) (2026-07-12) - - **Magnet Stuck Off After Death (Final Fix)**: Migrated toggle state to a static UUID-keyed ConcurrentHashMap. Removed COPY_FROM handler.
- [x] **1.2.0+26.2** (2026-07-11) - - **YACL Migration**: Migrated config screen GUI from Cloth Config to YetAnotherConfigLib v3. Swapped build.gradle/fabric.mod.json dependencies.
- [x] **1.1.9+26.2** (2026-07-11) - - Standardized Config Warning. - - Appended gold warning notice to option descriptions inside en_us.json and wired them to Cloth Config screen tooltips to clarify config-only behavior.
- [x] **1.1.8-26.2** (2026-07-05) - - **Versioning Alignment**: Migrated version naming scheme from older alphanumeric `1.1.7+A-26.2` format to standard `1.1.8-26.2` layout.
- [x] **1.1.7+A-26.2** (SUPERSEDED) (2026-07-05) - - **Respawn Sync Fix**: Resolved desync where player magnet is permanently disabled or untoggleable after death when respawning. - - **Thread-Safe Scheduling**: Scheduled C2S packets on the server thread (`context.server().execute()`) to avoid concurrency issues during respawn entity swap. - - **Direct Syncing**: Removed transient `canSend` gates and added automatic client synchronization inside the player's toggle setter.
- [x] **1.1.6+A-26.2** (SUPERSEDED) (2026-07-05) - - **Hotkey Modifier Removal**: Removed hardcoded `Ctrl+Alt+Shift` hotkey modifier checks from client logic. - - **Default Keybind Shift**: Rebound default hotkey from `M` to `\` (backslash) to resolve map mod key conflict.
- [x] **1.1.5+A-26.2** (SUPERSEDED) (2026-07-03) - - **Magnet Stuck Off After Death**: Fixed toggle state lost on death/respawn via `ServerPlayerEvents.COPY_FROM` and `AFTER_RESPAWN` sync. Also covers dimension changes.
- [x] **1.1.4+A-26.2** (SUPERSEDED) (2026-07-03) - - **Startup Crash Fix**: Fixed fatal `InvalidInjectionException` from `PlayerMixin` using legacy `CompoundTag` instead of the 26.2 `ValueOutput`/`ValueInput` storage API.
- [x] **1.1.3+A-26.2** (SUPERSEDED) (2026-07-03) - - **Ctrl+Alt+Shift+M Crash Fix**: Resolved `NoSuchMethodError` crash from legacy `Gui.setOverlayMessage()`. - - **NBT Toggle Persistence**: Replaced `MagnetPlayerState` with Mixin-injected `IMagnetPlayer` interface for persistent, NBT-backed individual magnet toggles. - - **Login State Sync**: Added `ServerPlayConnectionEvents.JOIN` listener for client state sync on login. - - **Memory Leak Elimination**: Removed `MagnetPlayerState.java` entirely.
- [x] **1.1.2+A-26.2** (SUPERSEDED) (2026-07-03) - - **Codebase Maintenance**: Removed an unused `Minecraft` import from `MagnetModClient.java`.
- [x] **1.1.1+A-26.2** (SUPERSEDED) (2026-06-25) - - **Client Magnet Toggle Hotkey**: Updated the default hotkey modifier pattern to require `Ctrl+Alt+Shift+M`. - - **Window API Migration**: Modified key state queries to use the current client window instance directly, preventing null pointer crashes during initialization.
- [x] **1.1.0+A-26.2** (SUPERSEDED) (2026-06-19) - - **Minecraft 26.2 Port**: Restructured the codebase and dependencies to target Minecraft 26.2-pre-1. - - **Client Magnet Toggle Hotkey**: Added a client-side keybinding (`Ctrl+M` by default) to toggle the item magnet on/off for the player.
