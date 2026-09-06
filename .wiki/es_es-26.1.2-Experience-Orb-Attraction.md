# ✨ Atracción de Orbes de Experiencia (MC 26.1.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Característica | Parámetros Técnicos |
| :--- | :--- |
| **Clase de Entidad Objetivo** | `net.minecraft.world.entity.ExperienceOrb` |
| **Clase Gestora** | `net.instantgratification.magnet.MagnetManager` |
| **GameRule Habilitadora** | `ig:magnet_affects_xp` (Por Defecto: `true`) |
| **Consulta de Escaneo** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **Tipo de Partícula** | `ParticleTypes.ELECTRIC_SPARK` |
| **Límite de Fuentes de Partículas** | `ig:magnet_max_particle_sources` (Por Defecto: `5`) |

---

## 📖 Mecánicas de Atracción de Experiencia

Bajo la filosofía Instant Gratification, dejar orbes de experiencia olvidados va en contra de la fluidez del juego. En Minecraft 26.1.2, las entidades `ExperienceOrb` son atraídas con idénticas físicas de velocidad y cambio de fase que los objetos caídos.

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

1. **Cambio de Fase (NoClip)**: Los orbes de experiencia atraviesan bloques sólidos para evitar quedar atrapados tras muros o en esquinas de techos.
2. **Interpolación de Velocidad**: Los orbes aceleran suavemente hacia los ojos del jugador utilizando los porcentajes configurados de velocidad y aceleración.
3. **Control por LOS**: Cuando `ig:magnet_los_only` es true, los orbes de XP deben ser visibles para el jugador o disponer de inercia de magnetización activa.

---

## 🛡️ Prevención de Lag y Agrupamiento de Partículas

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

* **Máximo de Fuentes de Partículas**: Solo las primeras $N$ entidades por tick generan estelas visuales.
* **Mecanismo de Respaldo Seguro para Cliente**: En `MagnetMovement.java`, si el nivel no es un `ServerLevel`, las partículas recurren de forma segura a `level.addParticle(...)`.

---

## ⚙️ Configuración y GameRules Pertinentes

| GameRule | Tipo | Por Defecto | Descripción |
| :--- | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Booleano | `true` | Determina si el imán atrae Orbes de Experiencia. |
| `ig:magnet_particles` | Booleano | `true` | Interruptor maestro para los efectos de partículas. |
| `ig:magnet_particle_count` | Entero | `1` | Partículas de chispa generadas por entidad por tick de partículas. |
| `ig:magnet_max_particle_sources` | Entero | `5` | Máximo de entidades autorizadas a emitir partículas simultáneamente. |

---

## 🔗 Documentación Relacionada de la Wiki
* [[Física de Vacío y Cambio de Fase|es_es-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Referencia Completa de GameRules|es_es-26.1.2-GameRules]]
* [[Volver al Portal de MC 26.1.2|es_es-26.1.2-Home]]
