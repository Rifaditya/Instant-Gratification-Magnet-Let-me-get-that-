# ⚡ Modo de Recogida Instantánea (MC 26.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Característica | Parámetros Técnicos |
| :--- | :--- |
| **Clase del Sistema** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **GameRule Habilitadora** | `ig:magnet_instant` (Por Defecto: `false`) |
| **GameRule de Radio** | `ig:magnet_range` (Por Defecto: `12`, Rango: `1..64`) |
| **Punto de Inyección** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **Variable Objetivo** | Caja Delimitadora de Recolección (`AABB pickupArea`) |
| **Lógica de Inventario** | Flujo 100% Nativo `Player.touch(ItemEntity)` |

---

## 📖 Visión General de la Recogida Instantánea

Mientras que el modo de vacío estándar atrae los objetos físicamente por el aire usando interpolación de velocidad, el **Modo de Recogida Instantánea** elimina el tiempo de vuelo por completo. Cuando está habilitado, los objetos caídos en el radio se recogen en el inventario del jugador en el mismo microsegundo en que aparecen.

En lugar de programar bucles de inserción de inventario personalizados que puedan causar desincronizaciones o bloqueos, **Magnet, Let me get that!** implementa la recogida instantánea expandiendo limpiamente la caja delimitadora de recolección nativa del jugador dentro del método `aiStep()` de vanilla.

```
+-----------------------------------------------------------------------------------+
|                           TICK aiStep() DEL JUGADOR EN VANILLA                    |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
                +---------------------------------------------------+
                |   PlayerMixin.ig_magnet$expandPickupArea()        |
                |   pickupArea = pickupArea.inflate(range);         |
                +---------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|               EJECUCIÓN VANILLA ItemEntity.playerTouch(Player)                    |
|   - Apilamiento Nativo de Inventario y Recogidas Parciales                        |
|   - Animación de Recogida y Sonidos Vanilla (item.pickup / entity.experience_orb) |
|   - Disparadores Nativos de Estadísticas y Progresos                              |
|   - Retención de Excedentes ante Inventario Lleno                                 |
+-----------------------------------------------------------------------------------+
```

---

## 🧩 Implementación Arquitectónica (`PlayerMixin.java`)

```java
@ModifyVariable(
        method = "aiStep",
        at = @At(value = "STORE"),
        ordinal = 0
)
private AABB ig_magnet$expandPickupArea(AABB pickupArea) {
    Player player = (Player) (Object) this;
    if (!this.ig_magnet$isMagnetEnabled() || player.isDeadOrDying() || player.isSpectator()) {
        return pickupArea;
    }

    Level level = player.level();
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_ENABLED) && ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    return pickupArea;
}
```

### Garantías Clave de Ingeniería:
1. **Filtros de Seguridad**: Si el jugador ha muerto o está muriendo (`player.isDeadOrDying()`), está en modo espectador (`player.isSpectator()`) o tiene su imán personal desactivado (`!isMagnetEnabled()`), la caja delimitadora nunca se expande.
2. **Autoridad en el Servidor**: La lógica de recogida se ejecuta estrictamente en el servidor lógico (`!level.isClientSide()`), eliminando objetos fantasma y discrepancias en el inventario.
3. **Sin Conflictos de Doble Movimiento**: Cuando `ig:magnet_instant` es true, `MagnetMovement.pull()` aborta automáticamente la aplicación de velocidades para que la física y la recolección instantánea no entren en colisión.

---

## ⚙️ Configuración y GameRules Pertinentes

| GameRule | Tipo | Por Defecto | Descripción |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Booleano | `false` | Habilita la teletransportación instantánea al inventario sin tiempo de vuelo. |
| `ig:magnet_range` | Entero | `12` | Radio (en bloques) por el cual se infla la caja AABB de recolección. |
| `ig:magnet_enabled` | Booleano | `true` | Interruptor maestro para todo el mod. |

---

## 🔗 Documentación Relacionada de la Wiki
* [[Física de Vacío y Cambio de Fase|es_es-26.2-Vacuum-and-Phase-Shifting]]
* [[Alternancia y Persistencia del Jugador|es_es-26.2-Player-Toggle-and-Persistence]]
* [[Arquitectura y Mixins|es_es-26.2-Architecture-and-Mixins]]
* [[Volver al Portal de MC 26.2|es_es-26.2-Home]]
