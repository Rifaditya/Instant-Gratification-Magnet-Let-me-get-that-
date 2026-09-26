# 🛠️ Entwickler-Setup & Loom-Build (MC 26.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Toolchain-Infobox | Technische Parameter |
| :--- | :--- |
| **Unterprojekt-Verzeichnis** | `Magnet v26.2/magnet/` |
| **Java-JDK-Ziel** | **Java 25** (`release = 25`) |
| **Gradle-Loom-Plugin** | `net.fabricmc.fabric-loom` Version `1.15.5` |
| **Minecraft-Version** | `26.2` |
| **Fabric-Loader-Version** | `0.19.1` |
| **Fabric-API-Version** | `0.150.1+26.2` |
| **DasikLibrary-Version** | `1.8.23` |
| **YACL-Version** | `3.9.5+26.2-fabric` |

---

## 💻 Voraussetzungen & Umgebungseinrichtung

1. **Java Development Kit (JDK 25)**:
   - Modernes Minecraft 26.x kompiliert gegen Java 25.
   - Konfiguriere `JAVA_HOME` oder setze `org.gradle.java.home=E:/JDK25` in `gradle.properties`.
2. **Git & Workspace-Klon**:
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.2/magnet"
   ```

---

## ⚙️ Properties-Konfiguration (`gradle.properties`)

```properties
org.gradle.parallel=false
org.gradle.java.home=E:/JDK25

# Mod Properties
mod_name=Magnet, Let me get that!
mod_version=1.3.9+26.2
maven_group=net.instantgratification
archives_base_name=Magnet-Let-me-get-that

# Dependencies
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

## 🔨 Gradle-Build-Befehle

```bash
# Vorherigen Build-Cache bereinigen
./gradlew clean

# Unit-Tests ausführen
./gradlew test

# Produktions-JAR kompilieren und automatische Archivierung auslösen
./gradlew build --no-daemon
```

---

## 📦 Automatische Release-Archivierung & Modrinth-Synchronisation

Das MC 26.2 `build.gradle`-Skript verfügt über einen registrierten `archiveReleaseJar`-Lebenszyklus-Task:

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

Nach erfolgreicher Kompilierung wird die erstelle JAR-Datei (`Magnet-Let-me-get-that-1.3.9+26.2.jar`) automatisch in `Archive Jar of all versions/MC 26.2/` gespiegelt und in aktive lokale Modrinth-Launcher-Testverzeichnisse übertragen.

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Architektur & Mixins|de_de-26.2-Architecture-and-Mixins]]
* [[API & Addon-Integration|de_de-26.2-API-and-Addon-Integration]]
* [[Zurück zum MC 26.2 Portal|de_de-26.2-Home]]
