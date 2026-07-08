# Developer Hub

Welcome to the **IG: Magnet, Let me get that!** development documentation.

## Sections

- **[Getting Started](Develop/Getting-Started/index.md)**: Requirements, Building, and Project Structure.
- **[Architecture](doc.md)**: Technical overview of the magnet logic (previously Architecture.md).
- **[GameRules Reference](Develop/gamerules_reference.md)**: Complete reference for all GameRules.
- **[Changelogs](../CHANGELOG.md)**: Full release history.

## Architecture

| Component | Purpose |
| :--- | :--- |
| [ModGameRules](../src/main/java/net/instantgratification/magnet/registry/ModGameRules.java) | Registers GameRules under custom category |
| [MagnetManager](../src/main/java/net/instantgratification/magnet/MagnetManager.java) | Stateless logic: Scanning players for items/XP in range |
| [MagnetMovement](../src/main/java/net/instantgratification/magnet/MagnetMovement.java) | Physics: Velocity injection, No-clip logic, Particle effects |
| [IMagnetEntity](../src/main/java/net/instantgratification/magnet/IMagnetEntity.java) | Interface for magnetization state tracking |

## Key Design Decisions

1. **Player-Centric LOS**: Magnetism can be configured to respect Line of Sight, preventing "psychic" pulling of items hidden behind walls unless previously spotted.
2. **Phase Shifting (NoClip)**: Items temporarily ignore physics (`noPhysics = true`) once pulled, preventing them from getting stuck in blocks.
3. **Magnetization Lock**: Items carry a state flag (`magnetized`) when seen, allowing them to keep moving even if they briefly disappear behind cover.
4. **Velocity Injection**: Uses lerp for smooth acceleration, creating a "snap" effect without jarring teleportation.
