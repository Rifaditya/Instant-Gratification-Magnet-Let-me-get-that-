# 🧲 Magnet, Let me get that! — Portal de Minecraft 26.1.2

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

Bienvenido al portal de documentación de **Minecraft 26.1.2** para **Magnet, Let me get that!** (Compilación `1.1.2+26.1.2`).

Esta edición de anclaje ofrece la experiencia completa de aspiración de objetos y XP de Instant Gratification con integración de Cloth Config, gestión de estado de sesiones concurrentes y trazado de rayos esférico en 360°.

---

## 📋 Especificaciones Rápidas de Minecraft 26.1.2

| Especificación | Valor Objetivo | Identificador de Referencia |
| :--- | :--- | :--- |
| **Objetivo de Lanzamiento de Minecraft** | `26.1.2` | `"minecraft": "*"` |
| **Compilación Activa del Subproyecto** | `1.1.2+26.1.2` | `mod_version=1.1.2+26.1.2` |
| **Cadena de Herramientas Java** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.16.10` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` | `fabric_version=0.145.4+26.1.2` |
| **Biblioteca Compartida Principal** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **Tecla Rápida por Defecto del Cliente** | `Ctrl+M` | `GLFW.GLFW_KEY_M` + `isControlDown()` |
| **Interfaz Gráfica de Configuración** | Cloth Config Fabric | `ClothConfigScreenHelper` + ModMenu |
| **Comandos de Servidor** | `/magnet toggle`, `/ig_magnet toggle` | `MagnetCommand.java` |

---

## ⚡ Características Clave Destacadas

* **Comprobaciones de Línea de Visión Esférica en 360°**: Desarrollado mediante `PlayerVisionTracker` de DasikLibrary 1.8.23. Consulta [[Línea de Visión y Obstáculos|es_es-26.1.2-Line-of-Sight-and-Obstruction]].
* **Física de Cambio de Fase NoClip**: Los objetos atraviesan el terreno sólido durante la atracción. Consulta [[Vacío y Cambio de Fase|es_es-26.1.2-Vacuum-and-Phase-Shifting]].
* **Modo de Recogida Instantánea**: Expansión opcional de la caja delimitadora para recolección con 0 latencia. Consulta [[Modo de Recogida Instantánea|es_es-26.1.2-Instant-Pickup-Mode]].
* **Alternancias en Sesiones Concurrentes**: Estado de teclas y comandos gestionado a través de `MagnetPlayerState`. Consulta [[Alternancia y Estado de Sesión|es_es-26.1.2-Player-Toggle-and-Persistence]].
* **Interfaz Cloth Config**: Pantalla de ajustes integrada en el juego con avisos de advertencia por categoría. Consulta [[Interfaz Cloth Config|es_es-26.1.2-Configuration]].

---

## 📑 Índice de Documentación 26.1.2

### 🎮 Jugabilidad y Administración
* [[Física de Vacío y Cambio de Fase|es_es-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Línea de Visión y Penetración de Obstáculos|es_es-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Sincronización de Orbes de Experiencia|es_es-26.1.2-Experience-Orb-Attraction]]
* [[Modo de Recogida Instantánea y Expansión de Caja Delimitadora|es_es-26.1.2-Instant-Pickup-Mode]]
* [[Alternancia del Jugador, Tecla Rápida y Almacenamiento de Estado|es_es-26.1.2-Player-Toggle-and-Persistence]]
* [[Referencia de GameRules y Límites Predeterminados|es_es-26.1.2-GameRules]]
* [[Comandos de Servidor y Soporte de Clientes Vanilla|es_es-26.1.2-Commands]]
* [[Progresos y Dependencia de Vanilla|es_es-26.1.2-Advancements]]
* [[Interfaz Gráfica Cloth Config y ModMenu|es_es-26.1.2-Configuration]]
* [[HUD en Actionbar y Efectos Visuales|es_es-26.1.2-HUD-and-Diagnostics]]

### 💻 Referencia de Ingeniería y Desarrollo
* [[Configuración de Desarrollo, Herramientas y Gradle Loom|es_es-26.1.2-Developer-Setup-and-Building]]
* [[Arquitectura, Paquetes e Inyecciones Mixin|es_es-26.1.2-Architecture-and-Mixins]]
* [[Fachadas API, Interfaces y Ganchos para Addons|es_es-26.1.2-API-and-Addon-Integration]]
* [[Volver a la Matriz de Versiones Multi-Era|es_es-Version-Compatibility]]
