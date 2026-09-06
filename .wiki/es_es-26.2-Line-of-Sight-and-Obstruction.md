# 👁️ Mecánicas de Línea de Visión y Obstáculos (MC 26.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Característica | Parámetros Técnicos |
| :--- | :--- |
| **Motor de Visión Primario** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **Motor de Visión Secundario** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **Campo de Visión Esférico** | $360.0^\circ$ (Percepción omnidireccional completa) |
| **Tolerancia de Distancia de Contacto** | Umbral de contacto con objetivo de $0.3\text{ m}$ |
| **Etiqueta de Retención en Memoria** | `ig_magnet$isMagnetized()` / `ig_magnet$setMagnetized()` |
| **Regla de Inercia** | `ig:magnet_keep_moving_if_unseen = true` |
| **Reglas de Filtrado Granular** | Bloques transparentes, Flora, Entidades de bloque |

---

## 📖 Arquitectura de Visión en Dos Pasos

Para evitar la recolección ilegítima de objetos a través de paredes de cuevas o bases protegidas mientras se mantiene un rendimiento sin lag, **Magnet, Let me get that!** implementa una canalización de **Visión en Dos Pasos** de alta velocidad:

```
                                +---------------------------+
                                |   OBJETO OBJETIVO DETECT. |
                                +-------------+-------------+
                                              |
                                              v
                              +-------------------------------+
                              |    PASE PRIMARIO (LOS 360°)   |
                              |   PlayerVisionTracker.canSee  |
                              +---------------+---------------+
                                              |
                        +---------------------+---------------------+
                        |                                           |
                   [VISIBLE]                                   [OBSTRUIDO]
                        |                                           |
                        v                                           v
      +-----------------------------------+               +--------------------+
      |     PASE SECUNDARIO (GRANULAR)    |               |  VERIF. DE INERCIA |
      |     SecondaryVisionCheck.canSee   |               | keepMovingIfUnseen |
      +-----------------+-----------------+               +---------+----------+
                        |                                           |
            +-----------+-----------+                     +---------+---------+
            |                       |                     |                   |
         [PASA]                [BLOQUEADO]           [MAGNETIZADO]     [NO MAGNETIZADO]
            |                       |                     |                   |
            v                       v                     v                   v
    +---------------+       +---------------+     +---------------+   +---------------+
    | ATRAER OBJETO |       | RECHAZAR /    |     | CONTINUAR     |   | RECHAZAR      |
    | Y MAGNETIZAR  |       | DETENER       |     | ATRACCIÓN     |   | ATRACCIÓN     |
    +---------------+       +---------------+     +---------------+   +---------------+
```

---

## 🔍 Paso 1: Comprobación Primaria de Línea de Visión Esférica en 360°

El pase primario utiliza el motor de trazado de rayos optimizado de DasikLibrary:
```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **Campo de Visión Omnidireccional**: Con un cono de FOV de $360.0^\circ$, el jugador atrae objetos situados directamente detrás de su espalda, arriba o bajo sus pies, siempre que no haya una pared sólida intermedia.
* **Tolerancia de Contacto de 0.3m**: Cuando los objetos se encuentran pegados a esquinas de bloques, el trazado de sub-vóxeles con un margen de $0.3\text{m}$ asegura que no se descarten por falso positivo.

---

## 🌿 Paso 2: Filtrado Granular de Estados de Bloque (`SecondaryVisionCheck`)

Si el pase primario tiene éxito, el mod evalúa reglas de obstrucción granular opcionales usando `BlockGetter.traverseBlocks`:

```java
public static boolean canSee(Player player, Entity target, boolean blockTransparent, boolean blockFlora, boolean blockEntities)
```

1. **Filtrado de Flora y Follaje (`ig:magnet_blocked_by_flora`)**:
   - Comprueba si algún bloque atravesado es una instancia de `BushBlock` (hierba alta, flores, cultivos, brotes) o `LeavesBlock` (hojas de árbol).
   - Si está activado (`true`), la vegetación actúa como barrera opaca frente a la atracción de objetos.
2. **Entidades de Bloque Interactivas (`ig:magnet_blocked_by_block_entities`)**:
   - Comprueba `state.hasBlockEntity()` en las posiciones atravesadas.
   - Si está activado (`true`), los cofres, cofres trampa, barriles, cajas de shulker, camas y dispensadores bloquean la línea de visión.
3. **Bloques Transparentes y No Completos (`ig:magnet_blocked_by_transparent`)**:
   - Realiza trazado de rayos contra `state.getVisualShape(...)`.
   - Si está activado (`true`), el cristal, paneles de cristal, barrotes de hierro, vallas, losas y escaleras bloquean la recolección.

---

## 🚀 Continuidad de Inercia (`keepMovingIfUnseen`)

Durante la minería rápida o el combate, los objetos atraídos que doblan una esquina suelen perder temporalmente la línea de visión. En lugar de congelarse o caer a la lava:

1. **Etiqueta de Magnetización**: Cuando un objeto es detectado en línea de visión, `((IMagnetEntity) entity).ig_magnet$setMagnetized()` activa una bandera booleana en memoria.
2. **Retención de Inercia**: Si el objeto interrumpe la línea de visión en ticks subsiguientes:
   - Si `ig:magnet_keep_moving_if_unseen = true` Y `ig_magnet$isMagnetized() == true`: El objeto continúa siendo atraído hacia el jugador.
   - Si `ig:magnet_keep_moving_if_unseen = false`: La atracción se detiene de inmediato al perder la línea de visión.
   - Si el objeto **nunca fue visto** (por ejemplo, generado detrás de una pared por una explosión o dispensador): La atracción se rechaza de inmediato.

---

## ⚙️ Configuración y GameRules Pertinentes

| GameRule | Tipo | Por Defecto | Descripción |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Booleano | `true` | Requiere visibilidad en línea de visión para atraer objetos. |
| `ig:magnet_keep_moving_if_unseen` | Booleano | `true` | Mantiene la atracción de objetos magnetizados si pierden visión en vuelo. |
| `ig:magnet_blocked_by_transparent` | Booleano | `false` | Si es true, el cristal y bloques translúcidos bloquean la línea de visión. |
| `ig:magnet_blocked_by_flora` | Booleano | `false` | Si es true, la hierba, hojas y flores bloquean la línea de visión. |
| `ig:magnet_blocked_by_block_entities` | Booleano | `false` | Si es true, cofres, camas y entidades de bloque bloquean la línea de visión. |

---

## 🔗 Documentación Relacionada de la Wiki
* [[Física de Vacío y Cambio de Fase|es_es-26.2-Vacuum-and-Phase-Shifting]]
* [[Referencia Completa de GameRules|es_es-26.2-GameRules]]
* [[Arquitectura y Mixins|es_es-26.2-Architecture-and-Mixins]]
* [[Volver al Portal de MC 26.2|es_es-26.2-Home]]
