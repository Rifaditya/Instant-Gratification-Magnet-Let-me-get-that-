# ⚙️ Referencia Completa de GameRules (MC 26.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Categoría | Detalles |
| :--- | :--- |
| **ID de Categoría** | `magnet:magnet_category` |
| **Título Traducido** | `Magnet, Let me get that!` (`gamerule.category.magnet.magnet_category`) |
| **Gestor Registrado** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **Clase de Registro** | `net.instantgratification.magnet.registry.ModGameRules` |
| **Reglas Totales Registradas** | `15` Reglas con Espacio de Nombres |

---

## 📖 Administración de GameRules en el Juego

Todas las mecánicas globales de **Magnet, Let me get that!** están reguladas por GameRules con espacio de nombres registradas bajo la categoría `magnet:magnet_category`.

Los administradores pueden configurar estas reglas en vivo dentro del juego mediante el comando `/gamerule <regla> <valor>` o a través de la pantalla de configuración de reglas de juego de vanilla al crear o editar un mundo.

```
/gamerule ig:magnet_enabled true
/gamerule ig:magnet_range 24
/gamerule ig:magnet_speed 120
/gamerule ig:magnet_instant true
```

---

## 📋 Tabla Completa de Referencia de GameRules

| Identificador de GameRule | Tipo | Por Defecto | Límites | Nombre Traducido | Descripción y Efecto en Juego |
| :--- | :---: | :---: | :---: | :--- | :--- |
| `ig:magnet_enabled` | Booleano | `true` | `true/false` | **Imán Habilitado** | Interruptor maestro que activa o desactiva globalmente el sistema de atracción de objetos. |
| `ig:magnet_range` | Entero | `12` | `1..64` | **Alcance del Imán** | Radio esférico en bloques desde el cual el jugador atrae objetos caídos. |
| `ig:magnet_noclip` | Booleano | `true` | `true/false` | **NoClip del Imán** | Habilita el cambio de fase, permitiendo que los objetos atraviesen libremente bloques sólidos. |
| `ig:magnet_affects_xp` | Booleano | `true` | `true/false` | **Atraer Orbes de XP** | Determina si los orbes de experiencia son atraídos junto a los objetos caídos. |
| `ig:magnet_particles` | Booleano | `true` | `true/false` | **Partículas del Imán** | Genera partículas de chispas eléctricas a lo largo de la trayectoria de atracción. |
| `ig:magnet_particle_count` | Entero | `1` | `0..100` | **Cantidad de Partículas** | Número de chispas de partículas emitidas por fuente activa por cada tick de partículas. |
| `ig:magnet_max_particle_sources` | Entero | `5` | `0..100` | **Máx. Fuentes de Partículas** | Cantidad máxima de entidades simultáneas autorizadas a emitir partículas para evitar lag. |
| `ig:magnet_speed` | Entero | `80` | `1..1000` | **Velocidad de Objetos** | Porcentaje de velocidad terminal ($80 = 0.8\text{ bloques/tick} = 16.0\text{ m/s}$). |
| `ig:magnet_acceleration` | Entero | `10` | `1..1000` | **Aceleración de Objetos** | Factor de interpolación de aceleración por tick ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_instant` | Booleano | `false` | `true/false` | **Recogida Instantánea** | Cuando es true, los objetos se teletransportan al inventario inflando la caja AABB sin vuelo. |
| `ig:magnet_los_only` | Booleano | `true` | `true/false` | **Solo Línea de Visión** | Requiere visibilidad directa; previene la recogida de objetos tras barreras impenetrables. |
| `ig:magnet_keep_moving_if_unseen` | Booleano | `true` | `true/false` | **Seguir Moviendo si No se Ve** | Permite a objetos vistos conservar la inercia si pierden visión en pleno vuelo. |
| `ig:magnet_blocked_by_transparent` | Booleano | `false` | `true/false` | **Bloqueado por Transparentes** | Si es true, el cristal, paneles de cristal, barras de hierro y translúcidos bloquean la visión. |
| `ig:magnet_blocked_by_flora` | Booleano | `false` | `true/false` | **Bloqueado por Flora** | Si es true, la hierba alta, cultivos, flores y hojas de árbol bloquean la visión. |
| `ig:magnet_blocked_by_block_entities` | Booleano | `false` | `true/false` | **Bloqueado por Entidades de Bloque** | Si es true, cofres, camas, barriles y cajas de shulker bloquean la visión. |

---

## ⚖️ Precedencia de GameRules sobre la Configuración JSON Global

> ⚠️ **Aviso Importante para Administradores**:  
> Las opciones del archivo de configuración JSON del cliente/servidor (`config/ig_magnet.json`) solo establecen los **valores iniciales por defecto para mundos de nueva creación**. Los mundos activos son gobernados estrictamente por sus GameRules. Editar `ig_magnet.json` no alterará los valores de una partida existente hasta modificarlos con `/gamerule`.

---

## 🔗 Documentación Relacionada de la Wiki
* [[Física de Vacío y Cambio de Fase|es_es-26.2-Vacuum-and-Phase-Shifting]]
* [[Línea de Visión y Penetración de Obstáculos|es_es-26.2-Line-of-Sight-and-Obstruction]]
* [[Interfaz de Configuración y Sincronización JSON|es_es-26.2-Configuration]]
* [[Volver al Portal de MC 26.2|es_es-26.2-Home]]
