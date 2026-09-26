# 🛠️ Configuración de Desarrollo y Compilación Loom (MC 26.1.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Herramientas | Parámetros Técnicos |
| :--- | :--- |
| **Directorio de Subproyecto** | `Magnet v26.1/magnet/` |
| **Versión JDK de Java** | **Java 25** (`release = 25`) |
| **Plugin Loom de Gradle** | `net.fabricmc.fabric-loom` versión `1.15.5` |
| **Versión de Minecraft** | `26.1.2` |
| **Versión de Fabric Loader** | `0.19.1` (Límite mín.: `>=0.16.10`) |
| **Versión de Fabric API** | `0.145.4+26.1.2` |
| **Versión de DasikLibrary** | `1.8.23` |
| **Versión de Cloth Config** | `26.1.154` |

---

## 💻 Configuración del Entorno y Cadenas de Herramientas

1. **Kit de Desarrollo Java (JDK 25)**:
   - Los proyectos modernos de Fabric en este repositorio compilan contra Java 25.
   - Configura la variable `JAVA_HOME` o establece `org.gradle.java.home=E:/JDK25` en `gradle.properties`.
2. **Clonación con Git**:
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.1/magnet"
   ```

---

## ⚙️ Configuración de Propiedades (`gradle.properties`)

```properties
org.gradle.parallel=false
org.gradle.java.home=E:/JDK25

# Propiedades del Mod
mod_name=Magnet, Let me get that!
mod_version=1.1.2+26.1.2
maven_group=net.instantgratification
archives_base_name=Magnet-Let-me-get-that

# Dependencias
minecraft_version=26.1.2
parchment_minecraft_version=26.1.2
parchment_version=2026.01.22

# Fabric
fabric_version=0.145.4+26.1.2
fabric_loader_version=0.19.1

# Loom
fabric.loom.suppressJavaCompatibilityChecks=true
loom.suppressJavaCompatibilityChecks=true

dasik_library_version=1.8.23
```

---

## 🔨 Comandos de Compilación Gradle

```bash
# Limpiar archivos de compilaciones previas
./gradlew clean

# Ejecutar pruebas automatizadas
./gradlew test

# Compilar JAR de producción y activar el auto-archivado
./gradlew build --no-daemon
```

---

## 📦 Archivado Automatizado de Versiones

El script `build.gradle` de MC 26.1.2 incorpora una tarea de ciclo de vida automatizada denominada `archiveReleaseJar`:

```groovy
tasks.register('archiveReleaseJar') {
    dependsOn 'build'
    doLast {
        def archiveDir = file("${project.rootDir}/../../Archive Jar of all versions/MC 26.1.2")
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

---

## 🔗 Documentación Relacionada de la Wiki
* [[Arquitectura e Implementaciones Mixin|es_es-26.1.2-Architecture-and-Mixins]]
* [[API e Integración de Addons|es_es-26.1.2-API-and-Addon-Integration]]
* [[Volver al Portal de MC 26.1.2|es_es-26.1.2-Home]]
