# 👁️ Mecánicas de Línea de Visión y Obstáculos (MC 26.1.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Característica | Parámetros Técnicos |
| :--- | :--- |
| **Motor de Visión Primario** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **Motor de Visión Secundario** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **Campo de Visión Esférico** | $360.0^\circ$ (Percepción omnidireccional completa) |
| **Tolerancia de Distancia de Contacto** | Umbral de contacto con objetivo de $0.3\text{ m}$ |
| **Etiqueta de Retención en Memoria** | `ig$isMagnetized()` / `ig$setMagnetized()` |
| **Regla de Inercia** | `ig:magnet_keep_moving_if_unseen = true` |
| **Implementación de Contexto** | Registro estático `VisionContext` |

---

## 📖 Canalización de Visión en Dos Pasos

En Minecraft 26.1.2, la línea de visión se evalúa mediante una canalización de **Visión en Dos Pasos** libre de asignaciones de memoria:

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

```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **Campo Omnidireccional**: Con un ángulo de campo de visión de $360^\circ$, los objetos caídos arriba, abajo o detrás del jugador son atraídos sin obligarlo a girar la cámara.
* **Tolerancia de Contacto Sub-Vóxel**: El margen de $0.3\text{m}$ evita descartes falsos cuando los objetos reposan directamente contra paredes sólidas.

---

## 🌿 Paso 2: Travesía Granular de Bloques (`SecondaryVisionCheck`)

```java
private record VisionContext(
        Level level,
        boolean blockTransparent,
        boolean blockFlora,
        boolean blockEntities,
        CollisionContext collisionContext,
        Vec3 start,
        Vec3 end
) {}
```

* **Flora (`ig:magnet_blocked_by_flora`)**: Comprueba instancias de `BushBlock` y `LeavesBlock`.
* **Entidades de Bloque (`ig:magnet_blocked_by_block_entities`)**: Comprueba `state.hasBlockEntity()` (Cofres, Cajas de Shulker, Camas).
* **Bloques Transparentes (`ig:magnet_blocked_by_transparent`)**: Evalúa `state.getVisualShape().clip(...)` frente a Cristal, Paneles y Losas.

---

## 🚀 Continuidad de Inercia (`keepMovingIfUnseen`)

* Al detectarse por primera vez, `((IMagnetEntity) entity).ig$setMagnetized()` marca la entidad.
* Si `ig:magnet_keep_moving_if_unseen = true`, el objeto continúa siendo atraído doblando obstáculos siempre que haya sido magnetizado previamente.

---

## ⚙️ Configuración y GameRules Pertinentes

| GameRule | Tipo | Por Defecto | Descripción |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Booleano | `true` | Requiere línea de visión para atraer objetos. |
| `ig:magnet_keep_moving_if_unseen` | Booleano | `true` | Mantiene la atracción de objetos magnetizados si pierden visión en vuelo. |
| `ig:magnet_blocked_by_transparent` | Booleano | `false` | Si es true, el cristal y bloques transparentes bloquean la visión. |
| `ig:magnet_blocked_by_flora` | Booleano | `false` | Si es true, la hierba y las flores bloquean la línea de visión. |
| `ig:magnet_blocked_by_block_entities` | Booleano | `false` | Si es true, los cofres y entidades de bloque bloquean la línea de visión. |

---

## 🔗 Documentación Relacionada de la Wiki
* [[Física de Vacío y Cambio de Fase|es_es-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Referencia Completa de GameRules|es_es-26.1.2-GameRules]]
* [[Volver al Portal de MC 26.1.2|es_es-26.1.2-Home]]
