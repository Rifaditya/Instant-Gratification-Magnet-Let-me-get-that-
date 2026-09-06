# 🗺️ Matriz de Compatibilidad de Versiones Multi-Era

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

Esta página documenta los lanzamientos compatibles de Minecraft, entornos de ejecución Java, dependencias de Fabric Loader y cadenas de herramientas de compilación para **Magnet, Let me get that!** (`ig_magnet`).

---

## 📊 Matriz de Ciclo de Vida Multi-Versión

| Minecraft Objetivo | Directorio del Subproyecto | Versión Activa del Mod | Versión Java | Fabric Loader | Fabric API | DasikLibrary | Proveedor GUI Config |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | `Magnet v26.2/magnet` | `1.3.9+26.2` | Java 25 (`release = 25`) | `>=0.19.1` | `0.150.1+26.2` | `1.8.23` | YetAnotherConfigLib v3 (`3.9.5+26.2-fabric`) + ModMenu |
| **Minecraft 26.1.2** | `Magnet v26.1/magnet` | `1.1.2+26.1.2` | Java 25 (`release = 25`) | `>=0.16.10` | `0.145.4+26.1.2` | `1.8.23` | Cloth Config Fabric (`26.1.154`) + ModMenu |

---

## 🔍 Desglose Detallado de Versiones

### 🟢 Minecraft 26.2 (`1.3.9+26.2`)
* **Estado**: Lanzamiento Moderno Principal
* **Ruta del Subproyecto**: `Magnet v26.2/magnet/`
* **Salida de Compilación**: `Magnet-Let-me-get-that-1.3.9+26.2.jar`
* **Directorio de Archivo Central**: `Archive Jar of all versions/MC 26.2/`
* **Límites de Dependencia (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.19.1",
      "minecraft": ">=26.2-",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **Características Arquitectónicas Clave**:
  * Almacenamiento NBT persistente usando códecs `ValueOutput` y `ValueInput` de Minecraft 26.2 dentro de `PlayerMixin`.
  * Retención automática del estado del jugador tras morir y reaparecer o teletransportarse entre dimensiones mediante los eventos de ciclo de vida de Fabric `ServerPlayerEvents.COPY_FROM` y `ServerPlayerEvents.AFTER_RESPAWN`.
  * Asignación de tecla rápida vinculada a `\` (`GLFW_KEY_BACKSLASH`) con método de respaldo dinámico `ig_magnet$getKeyboardType()`.
  * Comandos de diagnóstico en el juego `/magnet debug` y `/magnet debug log` con registro de archivo dedicado (`logs/ig_magnet_debug.log`).
  * `YaclScreenHelper` moderno que aprovecha YetAnotherConfigLib v3.

---

### 🟢 Minecraft 26.1.2 (`1.1.2+26.1.2`)
* **Estado**: Subproyecto de Anclaje Moderno
* **Ruta del Subproyecto**: `Magnet v26.1/magnet/`
* **Salida de Compilación**: `Magnet-Let-me-get-that-1.1.2+26.1.2.jar`
* **Directorio de Archivo Central**: `Archive Jar of all versions/MC 26.1.2/`
* **Límites de Dependencia (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.16.10",
      "minecraft": "*",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **Características Arquitectónicas Clave**:
  * Seguimiento del estado en sesiones concurrentes mediante `MagnetPlayerState` (`Map<UUID, Boolean>`).
  * Asignación de tecla rápida vinculada a `Ctrl+M` (`GLFW_KEY_M` + `isControlDown()`).
  * Notificación visual en superposición de actionbar nativa mediante `client.gui.setOverlayMessage(...)`.
  * Interfaz de configuración opcional segura contra fallos de reflexión mediante `ClothConfigScreenHelper`.

---

## 📦 Archivado Automatizado de Versiones y Despliegue en Lanzador

Ambos subproyectos integran archivado automatizado posterior a la compilación en sus scripts de compilación Gradle (`build.gradle`):

```bash
# Compilar y auto-archivar compilación de MC 26.2:
cd "Magnet v26.2/magnet"
./gradlew build --no-daemon

# Compilar y auto-archivar compilación de MC 26.1.2:
cd "Magnet v26.1/magnet"
./gradlew build --no-daemon
```

Cuando se ejecuta `./gradlew build`, la tarea `archiveReleaseJar` copia automáticamente el JAR compilado al directorio central de archivos (`Archive Jar of all versions/MC <Version>/`) y lo sincroniza con los perfiles locales activos de prueba del lanzador de Modrinth.

---

## 🔗 Documentación Relacionada de la Wiki
* [[Configuración y Herramientas de MC 26.2|es_es-26.2-Developer-Setup-and-Building]]
* [[Configuración y Herramientas de MC 26.1.2|es_es-26.1.2-Developer-Setup-and-Building]]
* [[Volver al Portal Central|es_es-Home]]
