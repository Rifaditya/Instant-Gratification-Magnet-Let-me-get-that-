# 🧲 Magnet, Let me get that! — Minecraft 26.1.2 Portal

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

Willkommen im **Minecraft 26.1.2** Dokumentationsportal für **Magnet, Let me get that!** (Build `1.1.2+26.1.2`).

Diese Anker-Edition bietet das vollständige Instant-Gratification-Erlebnis für Gegenstands- und XP-Vakuum mit Cloth-Config-Integration, nebenläufiger Sitzungszustandsverwaltung und sphärischem 360°-Raycasting.

---

## 📋 Minecraft 26.1.2 Schnellspezifikationen

| Spezifikation | Zielwert | Referenz-Identifikator |
| :--- | :--- | :--- |
| **Minecraft Release-Ziel** | `26.1.2` | `"minecraft": "*"` |
| **Aktiver Unterprojekt-Build** | `1.1.2+26.1.2` | `mod_version=1.1.2+26.1.2` |
| **Java-Toolchain** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.16.10` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` | `fabric_version=0.145.4+26.1.2` |
| **Gemeinsame Kernbibliothek** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **Standard-Client-Hotkey** | `Strg+M` | `GLFW.GLFW_KEY_M` + `isControlDown()` |
| **Konfigurations-GUI** | Cloth Config Fabric | `ClothConfigScreenHelper` + ModMenu |
| **Server-Befehle** | `/magnet toggle`, `/ig_magnet toggle` | `MagnetCommand.java` |

---

## ⚡ Wichtigste Funktions-Highlights

* **Sphärische 360°-Sichtlinienprüfungen**: Angetrieben durch `PlayerVisionTracker` der DasikLibrary 1.8.23. Siehe [[Sichtlinie & Hindernisse|de_de-26.1.2-Line-of-Sight-and-Obstruction]].
* **Phasenverschiebung mit NoClip-Physik**: Gegenstände durchdringen während des Anziehens feste Blöcke. Siehe [[Vakuum & Phasenverschiebung|de_de-26.1.2-Vacuum-and-Phase-Shifting]].
* **Sofort-Aufnahme-Modus**: Optionale Bounding-Box-Erweiterung für Sammeln mit 0 Latenz. Siehe [[Sofort-Aufnahme-Modus|de_de-26.1.2-Instant-Pickup-Mode]].
* **Parallele Sitzungsumschaltung**: Hotkey- und Befehlsstatus verwaltet über `MagnetPlayerState`. Siehe [[Spieler-Umschaltung & Sitzungsstatus|de_de-26.1.2-Player-Toggle-and-Persistence]].
* **Cloth-Config-GUI**: Übersichtlicher In-Game-Einstellungsbildschirm mit Kategorie-Hinweisen. Siehe [[Cloth-Config-GUI|de_de-26.1.2-Configuration]].

---

## 📑 26.1.2 Dokumentations-Index

### 🎮 Gameplay & Administration
* [[Vakuum & Phasenverschiebungs-Physik|de_de-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Sichtlinie & Hindernisdurchdringung|de_de-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Erfahrungs-Orb-Synchronisation|de_de-26.1.2-Experience-Orb-Attraction]]
* [[Sofort-Aufnahme-Modus & Bounding-Box-Erweiterung|de_de-26.1.2-Instant-Pickup-Mode]]
* [[Spieler-Umschaltung, Hotkey & Zustandsspeicherung|de_de-26.1.2-Player-Toggle-and-Persistence]]
* [[GameRules-Referenz & Standardgrenzen|de_de-26.1.2-GameRules]]
* [[Server-Befehle & Vanilla-Client-Unterstützung|de_de-26.1.2-Commands]]
* [[Fortschritte & Vanilla-Vertrauen|de_de-26.1.2-Advancements]]
* [[Cloth-Config-GUI & ModMenu|de_de-26.1.2-Configuration]]
* [[Actionbar-HUD & Visuelle Effekte|de_de-26.1.2-HUD-and-Diagnostics]]

### 💻 Referenz für Entwickler & Technik
* [[Entwickler-Setup, Toolchains & Gradle Loom|de_de-26.1.2-Developer-Setup-and-Building]]
* [[Architektur, Pakete & Mixin-Injektionen|de_de-26.1.2-Architecture-and-Mixins]]
* [[API-Fassaden, Schnittstellen & Addon-Hooks|de_de-26.1.2-API-and-Addon-Integration]]
* [[Zurück zur Multi-Ären Versionsmatrix|de_de-Version-Compatibility]]
