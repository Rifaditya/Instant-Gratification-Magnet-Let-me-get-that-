# 🧲 Magnet, Let me get that! — Offizielles Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

Willkommen im offiziellen technischen und Gameplay-Wiki für **Magnet, Let me get that!** (`ig_magnet`), einer intrinsischen Gegenstands- und Erfahrungs-Vakuum-Mod, entwickelt für modernes Minecraft auf Fabric.

Fest verankert in der **Instant Gratification (IG)**-Designphilosophie beseitigt diese Mod den „Walk of Shame“ – das mühsame Zurücklegen von 5 Blöcken, nur um einen gerade abgebauten Gegenstand oder getöteten Mob-Drop aufzuheben. Wer es sehen kann, sollte es auch sofort besitzen.

---

## 🧭 Multi-Versions-Portal

Wähle deine Zielversion von Minecraft, um auf dedizierte, isolierte Gameplay-Anleitungen, technische Dokumentationen, GameRules-Tabellen und Architekturreferenzen zuzugreifen:

| Minecraft-Version | Release-Status | Aktiver Versions-Build | Konfigurations-Engine | Portal-Link |
| :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | 🟢 Aktives Modern | `1.3.9+26.2` | YACL v3 + ModMenu | [[26.2 Übersicht|de_de-26.2-Home]] |
| **Minecraft 26.1.2** | 🟢 Moderner Anker | `1.1.2+26.1.2` | Cloth Config + ModMenu | [[26.1.2 Übersicht|de_de-26.1.2-Home]] |

### 🚀 Direkte Versionsportale:
* 📦 **Minecraft 26.2**: [[👉 Minecraft 26.2 Dokumentationsportal betreten|de_de-26.2-Home]]
* 📦 **Minecraft 26.1.2**: [[👉 Minecraft 26.1.2 Dokumentationsportal betreten|de_de-26.1.2-Home]]

Für eine detaillierte Aufschlüsselung von Toolchains, Abhängigkeitsmatrizen, Archivspeicherorten und Abwärtskompatibilität siehe [[Versionsmatrix|de_de-Version-Compatibility]].

---

## ⚡ Kernfunktions-Matrix

```
                      +-----------------------------+
                      |    SPIELER-VAKUUM-EMITTER   |
                      +--------------+--------------+
                                     |
              +----------------------+----------------------+
              |                                             |
              v                                             v
  +-----------------------+                     +-----------------------+
  | STANDARD-ANZIEH-MODUS |                     |  SOFORT-AUFNAHME-MODUS|
  +-----------+-----------+                     +-----------+-----------+
              |                                             |
     [Sichtlinien-Prüfung]                         [AABB-Box-Inflation]
     [Sphärischer Raycast 360°]                    [Keine Flugzeit-Latenz]
     [Phasenverschiebung NoClip]                   [Direkt ins Inventar]
     [Dynamische Lerp-Geschwindigkeit]                      |
              |                                             |
              +----------------------+----------------------+
                                     |
                                     v
                      +-----------------------------+
                      |   ITEM & XP-ORB EINGESAMMELT|
                      +-----------------------------+
```

* **Intelligentes 360°-Vakuum**: Zieht gedroppte Gegenstände und Erfahrungskugeln innerhalb eines konfigurierbaren Blockradius an (Standard: 12 Blöcke, bis zu 64).
* **Phasenverschiebung (NoClip)**: Magnetisierte Gegenstände durchdringen feste Blöcke mühelos, wodurch verhindert wird, dass Drops in Bruchsteinkanten oder Minenspalten hängenbleiben.
* **Sichtlinien-Erkennung (LOS)**: Bietet primäres 360°-sphärisches Raycasting über den `PlayerVisionTracker` der DasikLibrary und optionale granulare Filterung gegen transparente Blöcke (Glas), Flora (hohes Gras, Laub) und Block-Entities (Truhen).
* **Schwung-Kontinuität (`keepMovingIfUnseen`)**: Einmal in Sichtlinie erfasst, behalten Gegenstände ihren Zielschwung bei, selbst wenn sie während des Flugs kurz hinter Hindernisse geraten.
* **Sofort-Aufnahme-Option**: Vergrößert die native Aufnahmekollisionsbox des Spielers, um Gegenstände sofort und ohne Fluglatenz einzusammeln.
* **Spieler-Hotkey & Befehlssteuerung**: Schalte die Magnetisierung clientseitig per Hotkey (`\` auf 26.2, `Strg+M` auf 26.1.2) oder serverseitig via `/magnet toggle` um.
* **Kein Inventar-Ballast**: 100% intrinsische Funktionalität – keine speziellen Magnet-Items, Schmuckstücke oder Batterien erforderlich.

---

## 📚 Enzyklopädische Navigation

### 🎮 Anleitungen für Spieler & Administratoren
* [[MC 26.2 Übersicht|de_de-26.2-Home]] & [[MC 26.1.2 Übersicht|de_de-26.1.2-Home]]
* [[MC 26.2 Vakuum & Phasenverschiebung|de_de-26.2-Vacuum-and-Phase-Shifting]] & [[MC 26.1.2 Vakuum & Phasenverschiebung|de_de-26.1.2-Vacuum-and-Phase-Shifting]]
* [[MC 26.2 Sichtlinien-Prüfung|de_de-26.2-Line-of-Sight-and-Obstruction]] & [[MC 26.1.2 Sichtlinien-Prüfung|de_de-26.1.2-Line-of-Sight-and-Obstruction]]
* [[MC 26.2 Erfahrungs-Orb-Anziehung|de_de-26.2-Experience-Orb-Attraction]] & [[MC 26.1.2 Erfahrungs-Orb-Anziehung|de_de-26.1.2-Experience-Orb-Attraction]]
* [[MC 26.2 Sofort-Aufnahme-Modus|de_de-26.2-Instant-Pickup-Mode]] & [[MC 26.1.2 Sofort-Aufnahme-Modus|de_de-26.1.2-Instant-Pickup-Mode]]
* [[MC 26.2 Spieler-Umschaltung & Persistenz|de_de-26.2-Player-Toggle-and-Persistence]] & [[MC 26.1.2 Spieler-Umschaltung & Status|de_de-26.1.2-Player-Toggle-and-Persistence]]
* [[MC 26.2 GameRules-Referenz|de_de-26.2-GameRules]] & [[MC 26.1.2 GameRules-Referenz|de_de-26.1.2-GameRules]]
* [[MC 26.2 Befehle|de_de-26.2-Commands]] & [[MC 26.1.2 Befehle|de_de-26.1.2-Commands]]
* [[MC 26.2 Fortschritte|de_de-26.2-Advancements]] & [[MC 26.1.2 Fortschritte|de_de-26.1.2-Advancements]]
* [[MC 26.2 Konfigurations-GUI|de_de-26.2-Configuration]] & [[MC 26.1.2 Konfigurations-GUI|de_de-26.1.2-Configuration]]
* [[MC 26.2 HUD & Diagnose|de_de-26.2-HUD-and-Diagnostics]] & [[MC 26.1.2 HUD & Diagnose|de_de-26.1.2-HUD-and-Diagnostics]]

### 💻 Dokumentation für Entwickler & Mitwirkende
* [[MC 26.2 Entwickler-Setup & Build|de_de-26.2-Developer-Setup-and-Building]] & [[MC 26.1.2 Entwickler-Setup & Build|de_de-26.1.2-Developer-Setup-and-Building]]
* [[MC 26.2 Architektur & Mixin-Ziele|de_de-26.2-Architecture-and-Mixins]] & [[MC 26.1.2 Architektur & Mixin-Ziele|de_de-26.1.2-Architecture-and-Mixins]]
* [[MC 26.2 API & Addon-Integration|de_de-26.2-API-and-Addon-Integration]] & [[MC 26.1.2 API & Addon-Integration|de_de-26.1.2-API-and-Addon-Integration]]
* [[Multi-Ären Versionskompatibilitätsmatrix|de_de-Version-Compatibility]]

---

## ⚖️ Lizenz & Danksagung

Entwickelt von **Dasik (Rifaditya)** unter der **GNU General Public License v3.0 (GPLv3)**. Siehe `LICENSE` für die vollständigen Bedingungen und rechtlichen Genehmigungen.
