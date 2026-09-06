# 🧲 Movimiento de Vacío y Cambio de Fase (MC 26.1.2)

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

En Minecraft 26.1.2, el motor de aspiración rastrea continuamente las instancias válidas de `ItemEntity` dentro del radio esférico del jugador y las atrae directamente hacia el nivel de los ojos mediante interpolación lerp.

Con el **Cambio de Fase (NoClip)** activo, los objetos atraviesan suavemente paredes y bloques sólidos, evitando que las recompensas queden atrapadas detrás de obstáculos durante la minería o el combate.

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

### 1. Vector Unitario Direccional
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|}$$

### 2. Interpolación de Velocidad
$$\text{Escalar de Velocidad } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0} = 0.8\text{ bloques/tick}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
$$\text{Factor de Aceleración } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0} = 0.10$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 3. Impulso Antifricción en Suelo
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 Cambio de Fase (Motor NoClip)

1. **Activación de Estado**: `((IMagnetEntity) entity).ig$setMagnetNoClip()` activa una cuenta regresiva de 2 ticks.
2. **Anulación de Físicas (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` guarda `originalNoPhysics` y establece `entity.noPhysics = true`.
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` restaura `entity.noPhysics = originalNoPhysics`.
3. **Prevención de Empuje**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` bloquea las fuerzas de eyección de bloques de vanilla.
4. **Cancelación de Gravedad en Pared**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` cancela la gravedad *únicamente* cuando el objeto se encuentra físicamente dentro de un vóxel de bloque.

---

## ⚙️ Configuración y GameRules Pertinentes

| GameRule | Tipo | Por Defecto | Descripción |
| :--- | :---: | :---: | :--- |
| `ig:magnet_enabled` | Booleano | `true` | Interruptor maestro para las mecánicas de atracción. |
| `ig:magnet_range` | Entero | `12` | Radio de atracción en bloques (1 a 64). |
| `ig:magnet_speed` | Entero | `80` | Porcentaje de velocidad terminal ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Entero | `10` | Porcentaje del factor de aceleración ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Booleano | `true` | Habilita el cambio de fase a través de bloques al atraer. |

---

## 🔗 Documentación Relacionada de la Wiki
* [[Línea de Visión y Penetración de Obstáculos|es_es-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Modo de Recogida Instantánea|es_es-26.1.2-Instant-Pickup-Mode]]
* [[Arquitectura e Implementaciones Mixin|es_es-26.1.2-Architecture-and-Mixins]]
* [[Volver al Portal de MC 26.1.2|es_es-26.1.2-Home]]
