# 🛠️ Configuración de Desarrollo y Compilación Loom (MC 26.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Herramientas | Parámetros Técnicos |
| :--- | :--- |
| **Directorio de Subproyecto** | `Magnet v26.2/magnet/` |
| **Versión JDK de Java** | **Java 25** (`release = 25`) |
| **Plugin Loom de Gradle** | `net.fabricmc.fabric-loom` versión `1.15.5` |
| **Versión de Minecraft** | `26.2` |
| **Versión de Fabric Loader** | `0.19.1` |
| **Versión de Fabric API** | `0.150.1+26.2` |
| **Versión de DasikLibrary** | `1.8.23` |
| **Versión de YACL** | `3.9.5+26.2-fabric` |

---

## 💻 Prerrequisitos y Configuración del Entorno

1. **Kit de Desarrollo Java (JDK 25)**:
   - Los proyectos modernos de Minecraft 26.x compilan con Java 25.
   - Configura la variable `JAVA_HOME` o define `org.gradle.java.home=E:/JDK25` en `gradle.properties`.
2. **Git y Clonación del Espacio de Trabajo**:
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.2/magnet"
   ```

---

## ⚙️ Configuración de Propiedades (`gradle.properties`)

```properties
org.gradle.parallel=false
org.gradle.java.home=E:/JDK25

# Propiedades del Mod
mod_name=Magnet, Let me get that!
mod_version=1.3.9+26.2
maven_group=net.instantgratification
archives_base_name=Magnet-Let-me-get-that

# Dependencias
minecraft_version=26.2
parchment_minecraft_version=26.2
parchment_version=2026.01.22

# Fabric
fabric_version=0.150.1+26.2
fabric_loader_version=0.19.1

# Loom
fabric.loom.suppressJavaCompatibilityChecks=true
loom.suppressJavaCompatibilityChecks=true

dasik_library_version=1.8.23
```

---

## 🔨 Comandos de Compilación Gradle

```bash
# Limpiar caché de compilaciones previas
./gradlew clean

# Ejecutar pruebas unitarias
./gradlew test

# Compilar JAR de producción y activar el auto-archivado
./gradlew build --no-daemon
```

---

## 📦 Archivado Automatizado de Versiones y Sincronización con Modrinth

El script `build.gradle` de MC 26.2 incorpora una tarea registrada de ciclo de vida denominada `archiveReleaseJar`:

```groovy
tasks.register('archiveReleaseJar') {
    dependsOn 'build'
    doLast {
        def archiveDir = file("${project.rootDir}/../../Archive Jar of all versions/MC 26.2")
        archiveDir.mkdirs()
        def jarFile = tasks.named('jar', Jar).get().archiveFile.get().asFile
        if (jarFile.exists()) {
            copy {
                from jarFile
                into archiveDir
            }
            println "[AUTO-ARCHIVE] Successfully copied ${jarFile.name} to central Archive directory: ${archiveDir.absolutePath}"
        }
    }
}

tasks.named('build') {
    finalizedBy 'archiveReleaseJar'
}
```

Tras compilar con éxito, el archivo JAR resultante (`Magnet-Let-me-get-that-1.3.9+26.2.jar`) se copia automáticamente a `Archive Jar of all versions/MC 26.2/` y se sincroniza con los perfiles locales activos de prueba del lanzador de Modrinth.

---

## 🔗 Documentación Relacionada de la Wiki
* [[Arquitectura e Implementaciones Mixin|es_es-26.2-Architecture-and-Mixins]]
* [[API e Integración de Addons|es_es-26.2-API-and-Addon-Integration]]
* [[Volver al Portal de MC 26.2|es_es-26.2-Home]]
