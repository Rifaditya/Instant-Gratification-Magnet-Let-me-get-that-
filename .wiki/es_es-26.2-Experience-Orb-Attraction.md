# ✨ Atracción de Orbes de Experiencia (MC 26.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Característica | Parámetros Técnicos |
| :--- | :--- |
| **Clase de Entidad Objetivo** | `net.minecraft.world.entity.ExperienceOrb` |
| **Clase Gestora** | `net.instantgratification.magnet.MagnetManager` |
| **GameRule Habilitadora** | `ig:magnet_affects_xp` (Predeterminado: `true`) |
| **Consulta de Escaneo** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **Tipo de Partícula** | `ParticleTypes.ELECTRIC_SPARK` |
| **Límite de Partículas** | Compartido con objetos mediante `ig:magnet_max_particle_sources` |

---

## 📖 Mecánicas de Atracción de Experiencia

Bajo la filosofía Instant Gratification, dejar orbes de experiencia esparcidos por el suelo o pegados en techos de cavernas rompe el ritmo del juego. **Magnet, Let me get that!** proporciona soporte de primer nivel para atraer entidades `ExperienceOrb` junto a los objetos caídos.

```
+------------------+     getEntitiesOfClass     +-----------------------+     MagnetMovement.pull     +--------------------+
| AABB Escaneo Jug.| -------------------------> | List<ExperienceOrb>   | --------------------------> | Recolección Jugador|
+------------------+                            +-----------------------+                             +--------------------+
                                                            |
                                                            v
                                                [Aplicar NoClip Fase]
                                                [Aplicar Vector Vel. Lerp]
                                                [Partículas Chispa Limit.]
```

---

## ⚡ Física Sincronizada y Cambio de Fase

Cuando `ig:magnet_affects_xp` está habilitado, todos los orbes de experiencia en el radio heredan exactamente las mismas mecánicas avanzadas que los objetos:

1. **Cambio de Fase (NoClip)**: Los orbes de experiencia atraviesan bloques sólidos al ser atraídos, impidiendo que orbiten o reboten infinitamente contra las paredes.
2. **Aceleración Dinámica**: Los orbes siguen los mismos cálculos lerp de velocidad ($s = \text{speed}/100.0$) y aceleración ($a = \text{accel}/100.0$).
3. **Filtrado por Línea de Visión**: Si `ig:magnet_los_only` es true, los orbes de XP deben superar tanto el trazado de rayos esférico en 360° primario como las comprobaciones secundarias granulares.

---

## 🛡️ Rendimiento y Prevención de Lag (Límites de Partículas)

Las granjas de criaturas masivas o los combates contra jefes como el Dragón del End pueden generar cientos de orbes de experiencia de manera simultánea. Generar partículas en cada orbe por tick provocaría severas caídas de fotogramas por segundo (FPS) en el cliente.

El mod previene la sobrecarga de partículas mediante **Limitación Global de Fuentes de Partículas**:

```java
int maxParticleSources = ModGameRules.getInt(player.level(), ModGameRules.MAGNET_MAX_PARTICLE_SOURCES);
int particleSourceCount = 0;

for (ExperienceOrb orb : orbs) {
    boolean shouldSpawnParticles = false;
    if (particleSourceCount < maxParticleSources) {
        shouldSpawnParticles = true;
        particleSourceCount++;
    }
    MagnetMovement.pull(orb, player, shouldSpawnParticles);
}
```

* **Límite Global**: Solo las primeras $N$ entidades (configuradas mediante `ig:magnet_max_particle_sources`, por defecto: `5`) tienen permiso para emitir partículas de chispas en un tick determinado.
* **Escalonamiento Temporal**: Las partículas solo se emiten cuando `(entity.tickCount + entity.getId()) % 4 == 0` (cada 4 ticks = 5 veces por segundo).

---

## ⚙️ Configuración y GameRules Pertinentes

| GameRule | Tipo | Por Defecto | Unidad / Rango | Descripción |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Booleano | `true` | `true/false` | Define si los orbes de experiencia son atraídos por el imán. |
| `ig:magnet_particles` | Booleano | `true` | `true/false` | Interruptor maestro para los rastros visuales de chispas eléctricas. |
| `ig:magnet_particle_count` | Entero | `1` | `0..100` | Cantidad de partículas de chispa generadas por fuente activa. |
| `ig:magnet_max_particle_sources` | Entero | `5` | `0..100` | Cantidad máxima de entidades simultáneas autorizadas a emitir partículas. |

---

## 🔗 Documentación Relacionada de la Wiki
* [[Física de Vacío y Cambio de Fase|es_es-26.2-Vacuum-and-Phase-Shifting]]
* [[Referencia Completa de GameRules|es_es-26.2-GameRules]]
* [[HUD y Diagnósticos Visuales|es_es-26.2-HUD-and-Diagnostics]]
* [[Volver al Portal de MC 26.2|es_es-26.2-Home]]
