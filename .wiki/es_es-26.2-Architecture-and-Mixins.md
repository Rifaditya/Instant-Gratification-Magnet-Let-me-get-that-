# 🧩 Arquitectura y Objetivos Mixin (MC 26.2)

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
├── IMagnetPlayer.java                  # Interfaz de jugador para estado de alternancia y getters/setters
├── MagnetCommand.java                  # Árboles de comandos Brigadier (/magnet toggle, /magnet debug)
├── MagnetDebugLogger.java              # Registro en disco persistente y seguro para hilos
├── MagnetManager.java                  # Bucle de escaneo espacial y ejecución de entidades
├── MagnetMod.java                      # Punto de entrada universal/servidor y receptores de red
├── MagnetModClient.java                # Inicializador cliente, tecla rápida y aviso visual en actionbar
├── MagnetMovement.java                 # Matemáticas vectoriales de trayectoria, velocidad lerp y partículas
├── MagnetTogglePayload.java            # Registro compuesto de paquete de red y StreamCodec
├── SecondaryVisionCheck.java           # Trazado granular de bloques (Flora, Bloques con Entidad, Cristal)
├── config/
│   ├── MagnetConfig.java               # Almacenamiento de configuración JSON y campos POJO
│   ├── ModMenuIntegration.java         # Punto de entrada ModMenu API seguro para reflexión
│   └── YaclScreenHelper.java           # Constructor de interfaz YetAnotherConfigLib v3
├── mixin/
│   ├── MixinEntity.java                # Inyecta NoClip y cancelación de gravedad en Entity
│   └── PlayerMixin.java                # Inyecta persistencia NBT y recogida instantánea en Player
├── registry/
│   └── ModGameRules.java               # Registros con DynamicGameRuleManager
└── util/
    └── ModVersionGuard.java            # Verificación de integridad de Knot ClassLoader en tiempo de ejecución
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
Apunta a `net.minecraft.world.entity.player.Player` e implementa `IMagnetPlayer`.

| Método Inyectado | Punto de Inyección | Acción y Comportamiento |
| :--- | :--- | :--- |
| `ig_magnet$addAdditionalSaveData` | `@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))` | Serializa el booleano `ig_magnet_enabled` en el NBT del jugador vía `ValueOutput`. |
| `ig_magnet$readAdditionalSaveData` | `@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))` | Deserializa `ig_magnet_enabled` desde el NBT del jugador vía `ValueInput.getBooleanOr()`. |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | Invoca `MagnetManager.tick(player)` en el servidor en cada tick de juego. |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | Expande la caja delimitadora (`pickupArea.inflate(range)`) cuando `ig:magnet_instant` es true. |

---

## 🛡️ Comprobación de Knot ClassLoader (`ModVersionGuard`)

Para salvaguardar las partidas y el servidor de cargarse en versiones incompatibles, rotas o no soportadas de Minecraft:

```java
public final class ModVersionGuard {
    public static void checkClass(String modName, String requiredClassName) {
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        // Verifica la existencia de la clase requerida antes de proseguir la inicialización...
    }
}
```

Invocado en `MagnetMod.onInitialize()`:
```java
ModVersionGuard.checkClass("Magnet", "net.minecraft.world.entity.item.ItemEntity");
```

---

## 🔗 Documentación Relacionada de la Wiki
* [[Física de Vacío y Cambio de Fase|es_es-26.2-Vacuum-and-Phase-Shifting]]
* [[Alternancia, Persistencia y Ciclo de Vida del Jugador|es_es-26.2-Player-Toggle-and-Persistence]]
* [[API e Integración de Addons|es_es-26.2-API-and-Addon-Integration]]
* [[Volver al Portal de MC 26.2|es_es-26.2-Home]]
