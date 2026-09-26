# 🧲 Magnet, Let me get that! — Portal de Minecraft 26.2

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

Bienvenido al portal de documentación de **Minecraft 26.2** para **Magnet, Let me get that!** (Compilación `1.3.9+26.2`).

Esta edición moderna incorpora retención de estado NBT persistente, sincronización de reaparición mediante el ciclo de vida de Fabric, trazado de rayos esférico de 360° a través de DasikLibrary 1.8.23 y configuración gráfica con YetAnotherConfigLib v3 (YACL).

---

## 📋 Especificaciones Rápidas de Minecraft 26.2

| Especificación | Valor Objetivo | Identificador de Referencia |
| :--- | :--- | :--- |
| **Objetivo de Lanzamiento de Minecraft** | `26.2` | `"minecraft": ">=26.2-"` |
| **Compilación Activa del Subproyecto** | `1.3.9+26.2` | `mod_version=1.3.9+26.2` |
| **Cadena de Herramientas Java** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.19.1` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.150.1+26.2` | `fabric_version=0.150.1+26.2` |
| **Biblioteca Compartida Principal** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **Tecla Rápida por Defecto del Cliente** | `\` (Barra Invertida) | `GLFW.GLFW_KEY_BACKSLASH` |
| **Interfaz Gráfica de Configuración** | YetAnotherConfigLib v3 | `YaclScreenHelper` + ModMenu |
| **Comandos de Diagnóstico** | `/magnet toggle`, `/magnet debug`, `/magnet debug log` | `MagnetCommand.java` |

---

## ⚡ Características Clave Destacadas

* **Comprobaciones de Línea de Visión Esférica de 360°**: Evita la recogida ilegítima de objetos a través de paredes sólidas al tiempo que otorga una atracción de visibilidad total en todos los ángulos del jugador. Consulta [[Línea de Visión y Obstáculos|es_es-26.2-Line-of-Sight-and-Obstruction]].
* **Movimiento con Cambio de Fase NoClip**: Los objetos magnetizados atraviesan limpiamente los bloques para alcanzar el nivel de los ojos del jugador sin atascarse. Consulta [[Vacío y Cambio de Fase|es_es-26.2-Vacuum-and-Phase-Shifting]].
* **Recogida Instantánea con Cero Latencia**: El inflado opcional de la caja delimitadora aspira instantáneamente los objetos caídos al inventario con 0 retraso de físicas. Consulta [[Modo de Recogida Instantánea|es_es-26.2-Instant-Pickup-Mode]].
* **Estado Persistente del Jugador**: Las preferencias sobreviven a la muerte del jugador, reapariciones, cambios de dimensión y reinicios del servidor mediante NBT (`ValueOutput`/`ValueInput`) y `ServerPlayerEvents.COPY_FROM`. Consulta [[Alternancia y Persistencia del Jugador|es_es-26.2-Player-Toggle-and-Persistence]].
* **Suite de Comandos de Diagnóstico**: Herramientas de diagnóstico integradas `/magnet debug` y `/magnet debug log` para administradores del servidor. Consulta [[Comandos Brigadier|es_es-26.2-Commands]] y [[HUD y Diagnósticos|es_es-26.2-HUD-and-Diagnostics]].

---

## 📑 Índice de Documentación 26.2

### 🎮 Jugabilidad y Administración
* [[Física de Vacío y Cambio de Fase|es_es-26.2-Vacuum-and-Phase-Shifting]]
* [[Línea de Visión y Penetración de Obstáculos|es_es-26.2-Line-of-Sight-and-Obstruction]]
* [[Sincronización de Orbes de Experiencia|es_es-26.2-Experience-Orb-Attraction]]
* [[Modo de Recogida Instantánea y Caja Delimitadora AABB|es_es-26.2-Instant-Pickup-Mode]]
* [[Alternancia, Persistencia y Ciclo de Vida del Jugador|es_es-26.2-Player-Toggle-and-Persistence]]
* [[Referencia de GameRules y Límites Predeterminados|es_es-26.2-GameRules]]
* [[Comandos Brigadier y Diagnósticos en el Juego|es_es-26.2-Commands]]
* [[Progresos y Dependencia de Vanilla|es_es-26.2-Advancements]]
* [[Interfaz de Configuración YACL y ModMenu|es_es-26.2-Configuration]]
* [[HUD en Actionbar y Registro Dedicado|es_es-26.2-HUD-and-Diagnostics]]

### 💻 Referencia de Ingeniería y Desarrollo
* [[Configuración de Desarrollo, Herramientas y Gradle Loom|es_es-26.2-Developer-Setup-and-Building]]
* [[Arquitectura, Paquetes e Inyecciones Mixin|es_es-26.2-Architecture-and-Mixins]]
* [[Fachadas API, Interfaces y Ganchos para Addons|es_es-26.2-API-and-Addon-Integration]]
* [[Volver a la Matriz de Versiones Multi-Era|es_es-Version-Compatibility]]
