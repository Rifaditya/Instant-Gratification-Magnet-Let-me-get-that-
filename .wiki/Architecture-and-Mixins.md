# 🧩 Architecture & Mixin Targets (MC 26.1.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Architecture Infobox | Technical Parameters |
| :--- | :--- |
| **Root Package** | `net.instantgratification.magnet` |
| **Mixin Configuration** | `src/main/resources/magnet.mixins.json` |
| **Access Widener** | `src/main/resources/magnet.accesswidener` |
| **Compatibility Level** | `JAVA_25` |
| **Total Mixin Classes** | `2` (`MixinEntity`, `PlayerMixin`) |

---

## 🌳 Package Architecture Tree

```text
net.instantgratification.magnet/
├── IMagnetEntity.java                  # Entity interface for NoClip & magnetization flags
├── MagnetCommand.java                  # Server command registration (/magnet toggle)
├── MagnetManager.java                  # Entity scanning & spatial execution loop
├── MagnetMod.java                      # Mod initializer & packet receivers
├── MagnetModClient.java                # Client initializer, Ctrl+M keybind & overlay toast
├── MagnetMovement.java                 # Trajectory vector math, lerp velocity & particles
├── MagnetPlayerState.java              # Thread-safe ConcurrentHashMap player toggle storage
├── MagnetTogglePayload.java            # Network packet record & StreamCodec composite
├── SecondaryVisionCheck.java           # Granular block raycasting (Flora, Block Entities, Glass)
├── config/
│   ├── ClothConfigScreenHelper.java    # Cloth Config Fabric GUI builder
│   ├── MagnetConfig.java               # JSON configuration storage & POJO fields
│   └── ModMenuIntegration.java         # Reflection-safe ModMenu API entrypoint
├── mixin/
│   ├── MixinEntity.java                # Injects NoClip and gravity cancellation into Entity
│   └── PlayerMixin.java                # Injects tick execution & instant pickup into Player
└── registry/
    └── ModGameRules.java               # DynamicGameRuleManager registrations
```

---

## 📋 Mixin Injection Target Breakdown

### 1. `net.instantgratification.magnet.mixin.MixinEntity`
Targets `net.minecraft.world.entity.Entity` and implements `IMagnetEntity`.

| Injected Method | Injection Target Point | Action & Behavior |
| :--- | :--- | :--- |
| `ig_magnet$decrementNoClipTicks` | `@Inject(method = "tick", at = @At("HEAD"))` | Decrements `noClipTicks` countdown by 1 per tick when active. |
| `ig_magnet$preventPushOut` | `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` | Cancels vanilla push-out velocity if `noClipTicks > 0` on server. |
| `ig_magnet$forceNoClipOnMove` | `@Inject(method = "move", at = @At("HEAD"))` | Caches `originalNoPhysics` and forces `entity.noPhysics = true` if `noClipTicks > 0`. |
| `ig_magnet$restoreNoPhysicsAfterMove` | `@Inject(method = "move", at = @At("RETURN"))` | Restores `entity.noPhysics = originalNoPhysics` after move completes. |
| `ig_magnet$conditionalCancelGravity` | `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` | Cancels gravity downward acceleration *only* when item is inside a solid block. |

---

### 2. `net.instantgratification.magnet.mixin.PlayerMixin`
Targets `net.minecraft.world.entity.player.Player`.

| Injected Method | Injection Target Point | Action & Behavior |
| :--- | :--- | :--- |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | Invokes `MagnetManager.tick(player)` on the server each game tick. |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | Expands pickup bounding box (`pickupArea.inflate(range)`) when `ig:magnet_instant` is true. |

---

## 🔗 Related Wiki Documentation
* [[Vacuum & Phase Shifting Physics|Vacuum-and-Phase-Shifting]]
* [[Developer Setup & Building|Developer-Setup-and-Building]]
* [[Return to Home Portal|Home]]
