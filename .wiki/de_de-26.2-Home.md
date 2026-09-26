# 🧲 Magnet, Let me get that! — Minecraft 26.2 Portal

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

Willkommen im **Minecraft 26.2** Dokumentationsportal für **Magnet, Let me get that!** (Build `1.3.9+26.2`).

Diese moderne Edition bietet persistente NBT-Statusspeicherung, Synchronisierung beim Respawn über Fabric-Lifecycle-Events, sphärisches 360°-Raycasting über DasikLibrary 1.8.23 sowie eine YetAnotherConfigLib v3 (YACL) GUI-Konfiguration.

---

## 📋 Minecraft 26.2 Schnellspezifikationen

| Spezifikation | Zielwert | Referenz-Identifikator |
| :--- | :--- | :--- |
| **Minecraft Release-Ziel** | `26.2` | `"minecraft": ">=26.2-"` |
| **Aktiver Unterprojekt-Build** | `1.3.9+26.2` | `mod_version=1.3.9+26.2` |
| **Java-Toolchain** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.19.1` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.150.1+26.2` | `fabric_version=0.150.1+26.2` |
| **Gemeinsame Kernbibliothek** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **Standard-Client-Hotkey** | `\` (Backslash) | `GLFW.GLFW_KEY_BACKSLASH` |
| **Konfigurations-GUI** | YetAnotherConfigLib v3 | `YaclScreenHelper` + ModMenu |
| **Diagnose-Befehle** | `/magnet toggle`, `/magnet debug`, `/magnet debug log` | `MagnetCommand.java` |

---

## ⚡ Wichtigste Funktions-Highlights

* **Sphärische 360°-Sichtlinienprüfungen**: Verhindert das unfaire Aufsammeln von Gegenständen durch massive Wände, gewährt aber volle Sichtanziehung aus allen Winkeln. Siehe [[Sichtlinie & Hindernisse|de_de-26.2-Line-of-Sight-and-Obstruction]].
* **Phasenverschiebung mit NoClip-Bewegung**: Magnetisierte Gegenstände bewegen sich sauber durch Blöcke, um die Augenhöhe des Spielers ohne Verhaken zu erreichen. Siehe [[Vakuum & Phasenverschiebung|de_de-26.2-Vacuum-and-Phase-Shifting]].
* **Latenzfreie Sofort-Aufnahme**: Optionale Bounding-Box-Erweiterung saugt gedroppte Gegenstände mit 0 Physik-Verzögerung direkt ins Inventar ein. Siehe [[Sofort-Aufnahme-Modus|de_de-26.2-Instant-Pickup-Mode]].
* **Persistenter Spielerstatus**: Einstellungen überstehen Tod, Respawn, Dimensionswechsel und Server-Neustarts via NBT (`ValueOutput`/`ValueInput`) und `ServerPlayerEvents.COPY_FROM`. Siehe [[Spieler-Umschaltung & Zustandsspeicherung|de_de-26.2-Player-Toggle-and-Persistence]].
* **Diagnose-Befehlssuite**: Integrierte Diagnose-Werkzeuge `/magnet debug` und `/magnet debug log` für Server-Administratoren. Siehe [[Brigadier-Befehle|de_de-26.2-Commands]] und [[HUD & Diagnose|de_de-26.2-HUD-and-Diagnostics]].

---

## 📑 26.2 Dokumentations-Index

### 🎮 Gameplay & Administration
* [[Vakuum & Phasenverschiebungs-Physik|de_de-26.2-Vacuum-and-Phase-Shifting]]
* [[Sichtlinie & Hindernisdurchdringung|de_de-26.2-Line-of-Sight-and-Obstruction]]
* [[Erfahrungs-Orb-Synchronisation|de_de-26.2-Experience-Orb-Attraction]]
* [[Sofort-Aufnahme-Modus & AABB-Bounding-Box|de_de-26.2-Instant-Pickup-Mode]]
* [[Spieler-Umschaltung, Persistenz & Lebenszyklus|de_de-26.2-Player-Toggle-and-Persistence]]
* [[GameRules-Referenz & Standardgrenzen|de_de-26.2-GameRules]]
* [[Brigadier-Befehle & In-Game-Diagnose|de_de-26.2-Commands]]
* [[Fortschritte & Vanilla-Vertrauen|de_de-26.2-Advancements]]
* [[YACL-Konfigurations-GUI & ModMenu|de_de-26.2-Configuration]]
* [[Actionbar-HUD & Dedizierte Protokollierung|de_de-26.2-HUD-and-Diagnostics]]

### 💻 Referenz für Entwickler & Technik
* [[Entwickler-Setup, Toolchains & Gradle Loom|de_de-26.2-Developer-Setup-and-Building]]
* [[Architektur, Pakete & Mixin-Injektionen|de_de-26.2-Architecture-and-Mixins]]
* [[API-Fassaden, Schnittstellen & Addon-Hooks|de_de-26.2-API-and-Addon-Integration]]
* [[Zurück zur Multi-Ären Versionsmatrix|de_de-Version-Compatibility]]
