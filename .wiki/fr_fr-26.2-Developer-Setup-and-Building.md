# 🛠️ Configuration Développeur & Compilation Loom (MC 26.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Outils | Paramètres Techniques |
| :--- | :--- |
| **Dossier du Sous-Projet** | `Magnet v26.2/magnet/` |
| **Cible JDK Java** | **Java 25** (`release = 25`) |
| **Plugin Gradle Loom** | `net.fabricmc.fabric-loom` version `1.15.5` |
| **Version de Minecraft** | `26.2` |
| **Version de Fabric Loader** | `0.19.1` |
| **Version de Fabric API** | `0.150.1+26.2` |
| **Version de DasikLibrary** | `1.8.23` |
| **Version de YACL** | `3.9.5+26.2-fabric` |

---

## 💻 Prérequis & Configuration de l'Environnement

1. **Java Development Kit (JDK 25)** :
   - Minecraft 26.x moderne compile avec Java 25.
   - Configurez `JAVA_HOME` ou définissez `org.gradle.java.home=E:/JDK25` dans votre `gradle.properties`.
2. **Git & Clonage de l'Espace de Travail** :
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.2/magnet"
   ```

---

## ⚙️ Configuration des Propriétés (`gradle.properties`)

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

## 🔨 Commandes de Compilation Gradle

```bash
# Nettoyer le cache des compilations précédentes
./gradlew clean

# Lancer la suite de tests unitaires
./gradlew test

# Compiler le JAR de production et lancer l'archivage automatique
./gradlew build --no-daemon
```

---

## 📦 Archivage Automatisé des Versions & Synchronisation Modrinth

Le script `build.gradle` de MC 26.2 intègre une tâche personnalisée `archiveReleaseJar` enregistrée dans le cycle de vie :

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

Dès la compilation réussie, le fichier JAR généré (`Magnet-Let-me-get-that-1.3.9+26.2.jar`) est automatiquement dupliqué vers `Archive Jar of all versions/MC 26.2/` et déployé dans les profils de test actifs du lanceur local Modrinth.

---

## 🔗 Documentation Connexe
* [[Architecture & Implémentations Mixin|fr_fr-26.2-Architecture-and-Mixins]]
* [[API & Intégration d'Addons|fr_fr-26.2-API-and-Addon-Integration]]
* [[Retour au Portail MC 26.2|fr_fr-26.2-Home]]
