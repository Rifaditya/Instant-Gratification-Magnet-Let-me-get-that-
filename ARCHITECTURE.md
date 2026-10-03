# Architecture & Symbol Index: Magnet, Let me get that!

## 1. Mod Metadata & Entrypoint
- **Mod ID**: `ig_magnet`
- **Main Entrypoint**: `net.instantgratification.magnet.MagnetMod` (`net.fabricmc.api.ModInitializer`)
- **Client Entrypoint**: `net.instantgratification.magnet.MagnetModClient`

## 2. Bytecode Mixin Target Registry
| Target Vanilla Class | Mixin Class | Purpose |
| :--- | :--- | :--- |
| `Vanilla Class` | `net.instantgratification.magnet.mixin.PlayerMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.magnet.mixin.MixinEntity` | Core mixin hook |

## 3. Core Mechanics & Subsystems
- **Source Root**: `src/main/java/`
- **Resource Root**: `src/main/resources/`

## 4. Dynamic GameRules & Commands
- **GameRules / Commands**: Configured dynamically via namespaced keys (`ig_magnet:*`).

## 5. Configuration & Sidedness Isolation
- **Sidedness**: Server-safe logic in main, client isolated in `src/client/java` or client entrypoint.
