# 🧩 Arquitectura y Objetivos Mixin (MC 26.1.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Arquitectura | Parámetros Técnicos |
| :--- | :--- |
| **Paquete Raíz** | `net.instantgratification.magnet` |
| **Configuración Mixin** | `src/main/resources/magnet.mixins.json` |
| **Access Widener** | `src/main/resources/magnet.accesswidener` |
| **Nivel de Compatibilidad** | `JAVA_25` |
| **Clases Mixin Totales** | `2` (`MixinEntity`, `PlayerMixin`) |

---

## 🌳 Árbol de Arquitectura de Paquetes

```text
net.instantgratification.magnet/
├── IMagnetEntity.java                  # Interfaz de entidad para banderas NoClip y de magnetización
├── MagnetCommand.java                  # Registro de comandos de servidor (/magnet toggle)
├── MagnetManager.java                  # Bucle de escaneo espacial y ejecución de entidades
├── MagnetMod.java                      # Inicializador del mod y receptores de paquetes
├── MagnetModClient.java                # Inicializador cliente, tecla rápida Ctrl+M y aviso visual
├── MagnetMovement.java                 # Matemáticas de trayectoria, velocidad lerp y partículas
├── MagnetPlayerState.java              # Almacenamiento concurrente del estado de alternancia en servidor
├── MagnetTogglePayload.java            # Registro compuesto de paquete de red y StreamCodec
├── SecondaryVisionCheck.java           # Trazado granular de bloques (Flora, Bloques con Entidad, Cristal)
├── config/
│   ├── ClothConfigScreenHelper.java    # Constructor de pantalla GUI Cloth Config Fabric
│   ├── MagnetConfig.java               # Almacenamiento de configuración JSON y campos POJO
│   └── ModMenuIntegration.java         # Punto de entrada ModMenu API seguro para reflexión
├── mixin/
│   ├── MixinEntity.java                # Inyecta NoClip y cancelación de gravedad en Entity
│   └── PlayerMixin.java                # Inyecta ejecución de tick y recogida instantánea en Player
└── registry/
    └── ModGameRules.java               # Registros con DynamicGameRuleManager
```

---

## 📋 Desglose de Inyecciones Mixin

### 1. `net.instantgratification.magnet.mixin.MixinEntity`
Apunta a `net.minecraft.world.entity.Entity` e implementa `IMagnetEntity`.

| Método Inyectado | Punto de Inyección | Acción y Comportamiento |
| :--- | :--- | :--- |
| `ig_magnet$decrementNoClipTicks` | `@Inject(method = "tick", at = @At("HEAD"))` | Reduce la cuenta regresiva de `noClipTicks` en 1 por tick cuando está activo. |
| `ig_magnet$preventPushOut` | `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` | Cancela el empuje fuera de bloques de vanilla si `noClipTicks > 0` en el servidor. |
| `ig_magnet$forceNoClipOnMove` | `@Inject(method = "move", at = @At("HEAD"))` | Almacena `originalNoPhysics` y fuerza `entity.noPhysics = true` si `noClipTicks > 0`. |
| `ig_magnet$restoreNoPhysicsAfterMove` | `@Inject(method = "move", at = @At("RETURN"))` | Restaura `entity.noPhysics = originalNoPhysics` tras completarse el movimiento. |
| `ig_magnet$conditionalCancelGravity` | `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` | Cancela la aceleración de gravedad *únicamente* cuando el ítem se halla dentro de un bloque sólido. |

---

### 2. `net.instantgratification.magnet.mixin.PlayerMixin`
Apunta a `net.minecraft.world.entity.player.Player`.

| Método Inyectado | Punto de Inyección | Acción y Comportamiento |
| :--- | :--- | :--- |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | Invoca `MagnetManager.tick(player)` en el servidor en cada tick de juego. |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | Expande la caja delimitadora (`pickupArea.inflate(range)`) cuando `ig:magnet_instant` es true. |

---

## 🔗 Documentación Relacionada de la Wiki
* [[Física de Vacío y Cambio de Fase|es_es-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Configuración y Compilación Loom|es_es-26.1.2-Developer-Setup-and-Building]]
* [[Volver al Portal de MC 26.1.2|es_es-26.1.2-Home]]
