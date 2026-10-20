# 📋 Magnet, Let me get that! Release Queue & Backlog

This file tracks which built versions (from the central archive folder "E:\Minecraft Project\Instant Gratification Collection\Magnet Let me get that\Archive Jar of all versions") have been manually uploaded to Modrinth/CurseForge.
Open this file in your editor and change `[ ]` to `[x]` when you publish a version.

## 🚀 Published & Backlog Queue

- [ ] **1.3.17+26.3** (2026-10-20) - - **Velocity Synchronization**: Restored `entity.hurtMarked = true;` during item pull vectors. - - **Runtime Safety**: Purged `ModVersionGuard` reflection in compliance with Knot ClassLoader runtime safety. - - **Metadata & Build**: Aligned Modrinth metadata in `fabric.mod.json` and enabled 4-point archive sync.
- [x] **1.3.16+26.3** (2026-10-18) - SUPERSEDED (crashed on startup due to legacy ModVersionGuard reflection; superseded by 1.3.17+26.3)
- [x] **1.3.15+26.3** (2026-09-14) - Initial Minecraft 26.3 Release. - Ported Magnet, Let Me Get That! to Minecraft 26.3 snapshot-6, targeting Fabric Loader 0.19.3, Fabric API 0.156.1+26.3, and DasikLibrary 1.8.39.
