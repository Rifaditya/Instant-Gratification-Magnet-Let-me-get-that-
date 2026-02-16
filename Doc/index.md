# Developer Hub

Welcome to the **IG: Magnet, Let me get that!** development documentation.

## Sections

- **[Getting Started](Getting-Started/index.md)**: Requirements, Building, and Project Structure.
- **[Architecture](Architecture/Architecture.md)**: System flow, module responsibilities, design decisions.
- **[GameRules Reference](gamerules_reference.md)**: Complete reference for all GameRules.
- **[Changelogs](Changelogs/History.md)**: Full release history.

## Architecture

| Component | Purpose |
| :--- | :--- |
| [ModGameRules](file:///e:/Minecraft%20Project/Instant%20Gratification%20Collection/magnet/src/main/java/net/instantgratification/magnet/registry/ModGameRules.java) | Registers GameRules under custom category |
| [MagnetManager](file:///e:/Minecraft%20Project/Instant%20Gratification%20Collection/magnet/src/main/java/net/instantgratification/magnet/MagnetManager.java) | Stateless logic: Scanning players for items/XP in range |
| [MagnetMovement](file:///e:/Minecraft%20Project/Instant%20Gratification%20Collection/magnet/src/main/java/net/instantgratification/magnet/MagnetMovement.java) | Physics: Velocity injection, No-clip logic, Particle effects |
| [MixinPlayer](file:///e:/Minecraft%20Project/Instant%20Gratification%20Collection/magnet/src/main/java/net/instantgratification/magnet/mixin/MixinPlayer.java) | Intercepts Player Tick to trigger magnet logic |

## Key Design Decisions

1. **Phase Shifting (NoClip)**: Items temporarily ignore physics (`noPhysics = true`) to pass through walls, preventing them from getting stuck or requiring line-of-sight.
2. **Velocity Injection**: Uses linear interpolation (lerp) to smooth acceleration, creating a "snap" effect where items quickly reach terminal velocity without instantaneous snapping.
3. **Intrinsic Logic**: No item or energy required. The ability is an extension of the player.
