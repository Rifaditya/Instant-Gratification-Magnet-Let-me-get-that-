# 📋 Magnet, Let me get that! Release Queue & Backlog

This file tracks which built versions (from the central archive folder "E:\Minecraft Project\Instant Gratification Collection\Magnet Let me get that\Archive Jar of all versions") have been manually uploaded to Modrinth/CurseForge.
Open this file in your editor and change `[ ]` to `[x]` when you publish a version.

## 🚀 Published & Backlog Queue

- [x] **`1.0.0`** (2026-02-16) - - Initial Release for Minecraft 26.1 Snapshot 7. - - **Vacuum Field**: Items within range fly to the player.
- [x] **`1.0.0+build.1`** (2026-02-21) - - **Documentation**: Replaced "Architect" with "Creator" in Platform Page Author roles.
- [x] **`1.0.0+build.2`** (2026-02-21) - - **Refactor**: Replaced legacy `GameRulesInvoker` mixins and registry boilerplate with standard `DynamicGameRuleManager` calls. - - **Dependency**: Added `dasik-library` as a standalone compile dependency.
- [x] **`1.0.0+build.3`** (2026-02-27) - - **Instant Teleport Feature**: `ig_magnet_instant` gamerule now utilizes AABB area expansion on the player to natively capture items with 0 travel time, preserving vanilla pickup logic without duplicating inventory insertion logic. - - **Documentation**: Added comprehensive `doc.md` and synced translation keys for UI.
- [x] **`1.0.0+build.4`** (2026-02-27) - - **Regression**: Restored the core item vacuum functionality. `MixinPlayer` was unintentionally removed from mixin configuration during the Instant Teleport implementation. Both instant and pull mechanics now function concurrently.
- [x] **`1.0.0+build.5`** (2026-02-27) - - **Performance & Smoothness**: Restored client-side physics prediction to prevent visual stuttering and lag on item pulling. - - **Responsiveness**: Moved the magnetism hook to the start of the player tick (from tail to head) to eliminate a 1-tick delay upon items reaching the player's pickup radius.
- [x] **`1.0.0+build.6`** (2026-02-28) - - **Internal State**: Extended `IMagnetEntity` to support persistent magnetization tracking.
- [x] **`1.0.0+build.8`** (2026-02-28) - - **LOS-Aware Magnetism**: Integrated DasikLibrary's `PlayerVisionTracker`. - - **GameRules**: Added `ig_magnet_los_only` and `ig_magnet_keep_moving_if_unseen` for granular visibility control.
- [x] **`1.0.0+build.9`** (2026-02-28) - - **LOS State Tracking**: Replaced momentum heuristics with a proper `ig$isMagnetized()` flag via Mixin. This prevents the "false-pull" bug where items popped from dispensers or explosions were pulled through walls without being seen.
- [x] **`1.0.0+build.10`** (2026-02-28) - - **Master Toggle**: Added `ig_magnet_enabled` GameRule to allow players/servers to disable the magnet entirely.
- [x] **`1.0.1+A-26.1.2`** (2026-06-06) - - **Client-Side Crash**: Resolved a `ClassCastException` in `MagnetMovement.pull` by replacing the direct `ServerLevel` cast check with `instanceof` and providing a client-side particle fallback. - - **Lag Prevention Particle Limit**: Added `ig:magnet_max_particle_sources` GameRule to control the maximum number of entities allowed to spawn particles simultaneously per tick (Default: 5).
- [x] **`1.0.2+A-26.1.2`** (2026-06-06) - - **Mixin Consolidation**: Merged player mixin classes into `PlayerMixin.java` and cleaned up `magnet.mixins.json`.
- [x] **`1.0.3+A-26.1.2`** (2026-06-06) - - **Permanent NoClip Bug**: Resolved issue where magnetized items permanently lost gravity/physics and fell through blocks. We now save `noPhysics` on `move:HEAD` and restore it on `move:RETURN`.
- [x] **`1.0.4+A-26.1.2`** (2026-06-06) - - **Tick Gating**: Gated magnet ticking to the server-side to prevent redundant calculations and desync jitter on the client. - - **Class-Specific Scanning**: Upgraded generic entity scanning to class-specific `getEntitiesOfClass` queries, reducing complexity from $O(N)$ (scanning all entities) to $O(M)$ (scanning only items and XP orbs).
- [x] **`1.0.5+A-26.1.2`** (2026-06-06) - - **Lambda Allocation Elimination**: Refactored `SecondaryVisionCheck.java` to pass a static `VisionContext` record and use static method references during block traversal, eliminating heap closures and reducing GC collector pressure.
- [x] **`1.0.6+A-26.1.2`** (2026-06-06) - - **Optional Config GUI**: Added ModMenu + Cloth Config integration using reflection-safe isolated loading. - - **JSON Defaults Storage**: Implemented JSON default values storage in `ig_magnet.json` to configure baseline defaults for new worlds.
- [x] **`1.0.7+A-26.1.2`** (2026-06-06) - - **Config GUI Alignment**: Added the static warning text block at the top of all configuration categories (General, Speeds, Line of Sight, and Visuals & Performance) and removed individual setting tooltips.
- [x] **`1.0.8+A-26.1.2`** (2026-06-06) - - **Default Settings**: Changed the default value of Line of Sight Only (`losOnly`) from `false` to `true`.
- [x] **`1.0.10+B-26.1.2`** (2026-06-11) - - **Client-Side Visual Ghosting**: Gated all magnetism and movement mixin calls to the logical server, eliminating local client-side prediction desync when pulling items behind blocks. - - **Audit Compliance**: Cleaned up unused imports in `SecondaryVisionCheck.java` and `MagnetMovement.java`. Added missing decompile source citation to `MagnetMod.java`.
- [x] **`1.1.0+A-26.2`** (2026-06-19) - - **Minecraft 26.2 Port**: Restructured the codebase and dependencies to target Minecraft 26.2-pre-1. - - **Client Magnet Toggle Hotkey**: Added a client-side keybinding (`Ctrl+M` by default) to toggle the item magnet on/off for the player.
- [x] **`1.1.1+A-26.2`** (2026-06-25) - - **Client Magnet Toggle Hotkey**: Updated the default hotkey modifier pattern to require `Ctrl+Alt+Shift+M`. - - **Window API Migration**: Modified key state queries to use the current client window instance directly, preventing null pointer crashes during initialization.
- [x] **`1.1.2+A-26.2`** (2026-06-25) - - **Codebase Maintenance**: Removed an unused `Minecraft` import from `MagnetModClient.java`.
- [x] **`1.1.3+A-26.2`** (2026-07-03) - - **Ctrl+Alt+Shift+M Crash Fix**: Resolved `NoSuchMethodError` crash from legacy `Gui.setOverlayMessage()`. - - **NBT Toggle Persistence**: Replaced `MagnetPlayerState` with Mixin-injected `IMagnetPlayer` interface for persistent, NBT-backed individual magnet toggles. - - **Login State Sync**: Added `ServerPlayConnectionEvents.JOIN` listener for client state sync on login. - - **Memory Leak Elimination**: Removed `MagnetPlayerState.java` entirely.
- [x] **`1.1.4+A-26.2`** (2026-07-03) - - **Startup Crash Fix**: Fixed fatal `InvalidInjectionException` from `PlayerMixin` using legacy `CompoundTag` instead of the 26.2 `ValueOutput`/`ValueInput` storage API.
- [x] **`1.1.5+A-26.2`** (2026-07-03) - - **Magnet Stuck Off After Death**: Fixed toggle state lost on death/respawn via `ServerPlayerEvents.COPY_FROM` and `AFTER_RESPAWN` sync. Also covers dimension changes.
- [x] **`1.1.6+A-26.2`** (2026-07-05) - - **Hotkey Modifier Removal**: Removed hardcoded `Ctrl+Alt+Shift` hotkey modifier checks from client logic. - - **Default Keybind Shift**: Rebound default hotkey from `M` to `\` (backslash) to resolve map mod key conflict.
- [x] **`1.1.7+A-26.2`** (2026-07-05) - - **Respawn Sync Fix**: Resolved desync where player magnet is permanently disabled or untoggleable after death when respawning. - - **Thread-Safe Scheduling**: Scheduled C2S packets on the server thread (`context.server().execute()`) to avoid concurrency issues during respawn entity swap. - - **Direct Syncing**: Removed transient `canSend` gates and added automatic client synchronization inside the player's toggle setter.
- [x] **`1.1.8-26.2`** (2026-07-05) - - **Versioning Alignment**: Migrated version naming scheme from older alphanumeric `1.1.7+A-26.2` format to standard `1.1.8-26.2` layout.
- [x] **`1.1.9+26.2`** (2026-07-11) - - Standardized Config Warning. - - Appended gold warning notice to option descriptions inside en_us.json and wired them to Cloth Config screen tooltips to clarify config-only behavior.
- [x] **`1.2.0+26.2`** (2026-07-11) - - **YACL Migration**: Migrated config screen GUI from Cloth Config to YetAnotherConfigLib v3. Swapped build.gradle/fabric.mod.json dependencies.
- [x] **`1.3.0-26.2`** (2026-07-12) - - **Magnet Stuck Off After Death (Final Fix)**: Migrated toggle state to a static UUID-keyed ConcurrentHashMap. Removed COPY_FROM handler.
- [x] **`1.3.1-26.2`** (2026-07-14) - - **Instant Pickup Toggle Bypass**: Fixed toggle bypass in instant mode. Removed command double-sync and unused imports.
- [x] **`1.3.2-26.2`** (2026-07-15) - - **Master Toggle Bypass & Performance**: Checked global magnet enabled GameRule in instant pickup area logic. Optimized GC with a ThreadLocal vision context. Standardized mixin prefixing to ig_magnet$.
- [x] **`1.3.3-26.2`** (2026-07-15) - - **Debugging Command & Logs**: Added in-game /magnet debug command and precise logger outputs to resolve player respawn state desync.
- [x] **`1.3.4-26.2`** (2026-07-15) - - **Post-Death Vision Fix & Dedicated Debug Log**: Migrated vision check to vanilla raycast to fix magnet after death. Added dedicated log writer for ig_magnet_debug.log.
- [ ] **`1.3.5-26.2`** (2026-07-21) - - **Backend State Architecture Overhaul**: Replaced static Map with instance mixin fields. Integrated ServerPlayerEvents.COPY_FROM for leak-free, 100% reliable respawn state persistence. Added instant pickup safety gating for dead/spectator states.





