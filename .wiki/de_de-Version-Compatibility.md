# 🗺️ Multi-Ären Versionskompatibilitätsmatrix

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

Diese Seite dokumentiert die unterstützten Minecraft-Release-Drops, Java-Laufzeitumgebungen, Fabric-Loader-Abhängigkeiten und Build-Toolchains für **Magnet, Let me get that!** (`ig_magnet`).

---

## 📊 Multi-Versions-Lebenszyklus-Übersicht

| Ziel-Minecraft | Unterprojekt-Ordner | Aktive Mod-Version | Java-Ziel | Fabric Loader | Fabric API | DasikLibrary | Konfigurations-GUI-Provider |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | `Magnet v26.2/magnet` | `1.3.9+26.2` | Java 25 (`release = 25`) | `>=0.19.1` | `0.150.1+26.2` | `1.8.23` | YetAnotherConfigLib v3 (`3.9.5+26.2-fabric`) + ModMenu |
| **Minecraft 26.1.2** | `Magnet v26.1/magnet` | `1.1.2+26.1.2` | Java 25 (`release = 25`) | `>=0.16.10` | `0.145.4+26.1.2` | `1.8.23` | Cloth Config Fabric (`26.1.154`) + ModMenu |

---

## 🔍 Detaillierte Versionsaufschlüsselung

### 🟢 Minecraft 26.2 (`1.3.9+26.2`)
* **Status**: Primäres modernes Release
* **Unterprojekt-Pfad**: `Magnet v26.2/magnet/`
* **Archiv-Ausgabe**: `Magnet-Let-me-get-that-1.3.9+26.2.jar`
* **Zentrales Archiv-Verzeichnis**: `Archive Jar of all versions/MC 26.2/`
* **Abhängigkeitsgrenzen (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.19.1",
      "minecraft": ">=26.2-",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **Wichtigste Architekturmerkmale**:
  * Persistente NBT-Speicherung über Minecraft 26.2 `ValueOutput`- und `ValueInput`-Codecs innerhalb von `PlayerMixin`.
  * Automatische Beibehaltung des Spielerzustands bei Tod und Dimensionswechseln über die Fabric-Lifecycle-Events `ServerPlayerEvents.COPY_FROM` und `ServerPlayerEvents.AFTER_RESPAWN`.
  * Tastenkürzel-Umschaltung auf `\` (`GLFW_KEY_BACKSLASH`) mit dynamischem `ig_magnet$getKeyboardType()`-Fallback.
  * In-Game `/magnet debug` und `/magnet debug log` Diagnosebefehle mit dedizierter Datei-Protokollierung (`logs/ig_magnet_debug.log`).
  * Moderner `YaclScreenHelper` basierend auf YetAnotherConfigLib v3.

---

### 🟢 Minecraft 26.1.2 (`1.1.2+26.1.2`)
* **Status**: Modernes Anker-Unterprojekt
* **Unterprojekt-Pfad**: `Magnet v26.1/magnet/`
* **Archiv-Ausgabe**: `Magnet-Let-me-get-that-1.1.2+26.1.2.jar`
* **Zentrales Archiv-Verzeichnis**: `Archive Jar of all versions/MC 26.1.2/`
* **Abhängigkeitsgrenzen (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.16.10",
      "minecraft": "*",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **Wichtigste Architekturmerkmale**:
  * Thread-sichere Sitzungszustandsverwaltung über `MagnetPlayerState` (`Map<UUID, Boolean>`).
  * Tastenkürzel-Umschaltung auf `Strg+M` (`GLFW_KEY_M` + `isControlDown()`).
  * Natives Actionbar-Overlay-Feedback über `client.gui.setOverlayMessage(...)`.
  * Reflexionssichere optionale Konfigurations-GUI über `ClothConfigScreenHelper`.

---

## 📦 Automatische Release-Archivierung & Launcher-Bereitstellung

Beide Unterprojekte integrieren eine automatische Archivierung nach dem Kompilieren in ihren Gradle-Build-Skripten (`build.gradle`):

```bash
# Kompilieren und MC 26.2 Build automatisch archivieren:
cd "Magnet v26.2/magnet"
./gradlew build --no-daemon

# Kompilieren und MC 26.1.2 Build automatisch archivieren:
cd "Magnet v26.1/magnet"
./gradlew build --no-daemon
```

Wenn `./gradlew build` ausgeführt wird, kopiert der Task `archiveReleaseJar` die erstellte JAR-Datei automatisch in den zentralen Archivordner (`Archive Jar of all versions/MC <Version>/`) und synchronisiert sie mit aktiven lokalen Testprofilen des Modrinth-Launchers.

---

## 🔗 Verwandte Wiki-Dokumentation
* [[MC 26.2 Setup & Tooling|de_de-26.2-Developer-Setup-and-Building]]
* [[MC 26.1.2 Setup & Tooling|de_de-26.1.2-Developer-Setup-and-Building]]
* [[Zurück zum zentralen Switchboard-Portal|de_de-Home]]
