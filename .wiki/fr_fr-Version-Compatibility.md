# 🗺️ Matrice de Compatibilité Multi-Versions

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

Cette page documente les versions supportées de Minecraft, les environnements d'exécution Java, les dépendances de Fabric Loader et les chaînes de compilation pour **Magnet, Let me get that!** (`ig_magnet`).

---

## 📊 Vue d'Ensemble du Cycle de Vie Multi-Versions

| Minecraft Cible | Dossier du Sous-Projet | Version Active du Mod | Cible Java | Fabric Loader | Fabric API | DasikLibrary | Fournisseur GUI de Config |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | `Magnet v26.2/magnet` | `1.3.9+26.2` | Java 25 (`release = 25`) | `>=0.19.1` | `0.150.1+26.2` | `1.8.23` | YetAnotherConfigLib v3 (`3.9.5+26.2-fabric`) + ModMenu |
| **Minecraft 26.1.2** | `Magnet v26.1/magnet` | `1.1.2+26.1.2` | Java 25 (`release = 25`) | `>=0.16.10` | `0.145.4+26.1.2` | `1.8.23` | Cloth Config Fabric (`26.1.154`) + ModMenu |

---

## 🔍 Analyse Détaillée des Versions

### 🟢 Minecraft 26.2 (`1.3.9+26.2`)
* **Statut** : Version Moderne Principale
* **Chemin du Sous-Projet** : `Magnet v26.2/magnet/`
* **Sortie d'Archive** : `Magnet-Let-me-get-that-1.3.9+26.2.jar`
* **Répertoire Central d'Archive** : `Archive Jar of all versions/MC 26.2/`
* **Limites de Dépendance (`fabric.mod.json`)** :
  ```json
  "depends": {
      "fabricloader": ">=0.19.1",
      "minecraft": ">=26.2-",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **Fonctionnalités Architecturales Clés** :
  * Stockage NBT persistant à l'aide des codecs `ValueOutput` et `ValueInput` de Minecraft 26.2 dans `PlayerMixin`.
  * Conservation automatique de l'état du joueur après la mort et lors du changement de dimension via le cycle de vie Fabric `ServerPlayerEvents.COPY_FROM` et `ServerPlayerEvents.AFTER_RESPAWN`.
  * Touche de bascule assignée à `\` (`GLFW_KEY_BACKSLASH`) avec secours dynamique `ig_magnet$getKeyboardType()`.
  * Commandes de diagnostic en jeu `/magnet debug` et `/magnet debug log` avec journalisation dédiée sur disque (`logs/ig_magnet_debug.log`).
  * `YaclScreenHelper` moderne utilisant YetAnotherConfigLib v3.

---

### 🟢 Minecraft 26.1.2 (`1.1.2+26.1.2`)
* **Statut** : Sous-Projet Point d'Ancrage Moderne
* **Chemin du Sous-Projet** : `Magnet v26.1/magnet/`
* **Sortie d'Archive** : `Magnet-Let-me-get-that-1.1.2+26.1.2.jar`
* **Répertoire Central d'Archive** : `Archive Jar of all versions/MC 26.1.2/`
* **Limites de Dépendance (`fabric.mod.json`)** :
  ```json
  "depends": {
      "fabricloader": ">=0.16.10",
      "minecraft": "*",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **Fonctionnalités Architecturales Clés** :
  * Suivi simultané de l'état de session via `MagnetPlayerState` (`Map<UUID, Boolean>`).
  * Touche de bascule assignée à `Ctrl+M` (`GLFW_KEY_M` + `isControlDown()`).
  * Retour visuel natif sur la barre d'action via `client.gui.setOverlayMessage(...)`.
  * Interface de configuration optionnelle sécurisée par réflexion via `ClothConfigScreenHelper`.

---

## 📦 Archivage Automatisé & Déploiement sur Launcher

Les deux sous-projets intègrent un archivage post-compilation automatisé dans leurs scripts de compilation Gradle (`build.gradle`) :

```bash
# Compiler et archiver automatiquement la version MC 26.2 :
cd "Magnet v26.2/magnet"
./gradlew build --no-daemon

# Compiler et archiver automatiquement la version MC 26.1.2 :
cd "Magnet v26.1/magnet"
./gradlew build --no-daemon
```

Lors de l'exécution de `./gradlew build`, la tâche `archiveReleaseJar` copie automatiquement le JAR compilé dans le dossier d'archives central (`Archive Jar of all versions/MC <Version>/`) et le synchronise avec les profils de test actifs du lanceur Modrinth local.

---

## 🔗 Documentation Connexe
* [[Configuration & Compilation Loom MC 26.2|fr_fr-26.2-Developer-Setup-and-Building]]
* [[Configuration & Compilation Loom MC 26.1.2|fr_fr-26.1.2-Developer-Setup-and-Building]]
* [[Retour au Portail Central|fr_fr-Home]]
