# ⚡ Modo de Recogida Instantánea (MC 26.1.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Característica | Parámetros Técnicos |
| :--- | :--- |
| **Clase del Sistema** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **GameRule Habilitadora** | `ig:magnet_instant` (Por Defecto: `false`) |
| **GameRule de Radio** | `ig:magnet_range` (Por Defecto: `12`, Rango: `1..64`) |
| **Punto de Inyección** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **Variable Objetivo** | Caja Delimitadora de Recolección (`AABB pickupArea`) |

---

## 📖 Visión General de la Recogida Instantánea

En Minecraft 26.1.2, el **Modo de Recogida Instantánea** elimina el tiempo de vuelo de los objetos expandiendo la caja delimitadora de recolección en `Player.aiStep()`, introduciendo de inmediato los drops en el inventario mediante los controladores nativos de vanilla.

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
    Level level = player.level();
    
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    
    return pickupArea;
}
```

---

## ⚙️ Configuración y GameRules Pertinentes

| GameRule | Tipo | Por Defecto | Descripción |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Booleano | `false` | Si es true, los objetos se teletransportan al jugador sin tiempo de vuelo. |
| `ig:magnet_range` | Entero | `12` | Radio en bloques para la expansión del área de recolección. |
| `ig:magnet_enabled` | Booleano | `true` | Interruptor maestro para todo el mod. |

---

## 🔗 Documentación Relacionada de la Wiki
* [[Física de Vacío y Cambio de Fase|es_es-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Referencia Completa de GameRules|es_es-26.1.2-GameRules]]
* [[Volver al Portal de MC 26.1.2|es_es-26.1.2-Home]]
