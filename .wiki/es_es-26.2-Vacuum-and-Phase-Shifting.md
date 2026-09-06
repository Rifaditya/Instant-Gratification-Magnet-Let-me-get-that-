# 🧲 Movimiento de Vacío y Cambio de Fase (MC 26.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Característica | Parámetros Técnicos |
| :--- | :--- |
| **Clase del Sistema** | `net.instantgratification.magnet.MagnetMovement` |
| **Evento Desencadenante** | Tick del Jugador en Servidor (`PlayerMixin` $\rightarrow$ `MagnetManager.tick`) |
| **Alcance de Atracción por Defecto** | `12` bloques (`ig:magnet_range`) |
| **Velocidad Terminal por Defecto** | `80%` ($0.8\text{ bloques/tick} = 16.0\text{ m/s}$) |
| **Aceleración por Defecto** | `10%` ($0.10\text{ factor lerp/tick}$) |
| **Cambio de Fase (NoClip)** | Habilitado (`ig:magnet_noclip = true`) |
| **Vector de Destino** | Posición de Ojos del Jugador (`player.getEyePosition()`) |
| **Impulso de Despegue en Suelo** | $+0.05\text{ m}$ en eje Y cuando `entity.onGround()` |

---

## 📖 Visión General del Sistema

La mecánica central de aspiración en **Magnet, Let me get that!** escanea en cada tick del servidor las instancias de `ItemEntity` caídas dentro del radio configurado del jugador y las atrae hacia el nivel de sus ojos mediante una interpolación suave y no lineal.

Para evitar que los objetos se enganchen en bordes de roca, copas de árboles o grietas de vetas minerales, el mod activa el **Cambio de Fase (NoClip)**, permitiendo que los objetos en vuelo atraviesen inofensivamente vóxeles de bloques sólidos.

```
+---------------+     Línea de Visión OK     +------------------------+     Velocidad Lerp Aplicada     +--------------------+
| Item Entity   | -------------------------> | Activar NoClip (2 Ticks)| ------------------------------> | Pos. Ojos Jugador  |
+---------------+                            +------------------------+                                 +--------------------+
                                                         |
                                                         v
                                              [Cancelar Empuje Pared]
                                              [Ignorar Colisión Bloque]
                                              [Cancelar Grav. en Pared]
```

---

## 🧮 Física y Matemáticas Vectoriales

Al atraer un objeto, la trayectoria se calcula directamente en el espacio euclidiano 3D:

### 1. Vector hacia el Objetivo
Sea $\vec{p}_{\text{entity}} = (x_e, y_e, z_e)$ la posición actual del objeto, y $\vec{p}_{\text{eye}} = (x_p, y_p + h_{\text{eye}}, z_p)$ la posición de los ojos del jugador.
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|} = \frac{\vec{v}_{\text{target}}}{\sqrt{\Delta x^2 + \Delta y^2 + \Delta z^2}}$$

### 2. Velocidad Terminal Deseada
El vector de velocidad objetivo escala la dirección unitaria $\hat{d}$ por el parámetro de velocidad configurado:
$$\text{Escalar de Velocidad } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
*Con la configuración por defecto ($80\%$), $s = 0.8\text{ bloques/tick}$. A $20\text{ ticks/s}$, la velocidad terminal es de $16.0\text{ m/s}$.*

### 3. Aceleración No Lineal (Lerp)
La velocidad se actualiza mediante interpolación lineal (`Vec3.lerp`) basada en el factor de aceleración $a$:
$$\text{Factor de Aceleración } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0}$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 4. Impulso Antifricción en Suelo
Si el objeto reposa sobre la superficie de un bloque (`entity.onGround() == true`), la fricción con el suelo se interrumpe al instante para evitar arrastres:
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 Cambio de Fase (Motor NoClip)

Cuando `ig:magnet_noclip` está habilitado, los objetos reciben una ventana de NoClip de 2 ticks:

1. **Activación de Estado**: `((IMagnetEntity) entity).ig_magnet$setMagnetNoClip()` establece `noClipTicks = 2`.
2. **Intercepción de Movimiento (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` guarda `originalNoPhysics` y fuerza `entity.noPhysics = true`.
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` restaura `entity.noPhysics = originalNoPhysics`.
3. **Cancelación de Empuje de Paredes**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` previene que la lógica de vanilla expulse agresivamente los objetos fuera de los bloques.
4. **Cancelación Condicional de Gravedad**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` cancela la gravedad descendente *únicamente* cuando el objeto intersecta físicamente un vóxel de bloque (`!level.noCollision(...)`), preservando trayectorias en arco naturales al aire libre.

---

## ⚙️ Configuración y GameRules Pertinentes

| GameRule | Tipo | Por Defecto | Unidad / Rango | Descripción |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_enabled` | Booleano | `true` | `true/false` | Interruptor maestro para toda la lógica de atracción. |
| `ig:magnet_range` | Entero | `12` | `1..64` bloques | Radio esférico máximo de atracción. |
| `ig:magnet_speed` | Entero | `80` | `1..1000%` | Porcentaje de velocidad terminal ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Entero | `10` | `1..1000%` | Porcentaje de aceleración de tiro ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Booleano | `true` | `true/false` | Habilita el cambio de fase a través de bloques al atraer. |

---

## 🔗 Documentación Relacionada de la Wiki
* [[Línea de Visión y Penetración de Obstáculos|es_es-26.2-Line-of-Sight-and-Obstruction]]
* [[Modo de Recogida Instantánea|es_es-26.2-Instant-Pickup-Mode]]
* [[Arquitectura e Implementaciones Mixin|es_es-26.2-Architecture-and-Mixins]]
* [[Volver al Portal de MC 26.2|es_es-26.2-Home]]
