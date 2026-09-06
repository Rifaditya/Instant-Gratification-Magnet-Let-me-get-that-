# 🎨 Cloth-Config-GUI & ModMenu (MC 26.1.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Konfigurations-Infobox | Details |
| :--- | :--- |
| **Konfigurationsdateipfad** | `config/ig_magnet.json` |
| **GUI-Bibliothek** | Cloth Config Fabric (`me.shedaniel.cloth:cloth-config-fabric:26.1.154`) |
| **ModMenu-Einstiegspunkt** | `net.instantgratification.magnet.config.ModMenuIntegration` |
| **GUI-Screen-Helfer** | `net.instantgratification.magnet.config.ClothConfigScreenHelper` |
| **Classloading-Sicherheit** | Isoliert über `GuiHelper.getOptionalFactory` |

---

## 📖 Konfigurations-Architektur

In Minecraft 26.1.2 integriert **Magnet, Let me get that!** Unterstützung für **Cloth Config Fabric** und **ModMenu**, um einen grafischen Einstellungsbildschirm im Spiel bereitzustellen.

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
                "ig_magnet",
                "net.instantgratification.magnet.config.ClothConfigScreenHelper",
                "createFactory"
        );
    }
}
```

---

## ⚠️ Konfigurations-Vorrang-Warnung

> ⚠️ **Wichtiger Hinweis**:  
> Änderungen im ModMenu-GUI oder in `config/ig_magnet.json` **betreffen ausschließlich die Basis-Standardwerte für NEUE Welten**.  
> Um die Einstellungen einer aktiven, bereits existierenden Welt anzupassen, nutze die In-Game-[[GameRules-Referenz|de_de-26.1.2-GameRules]] via `/gamerule` oder den regulären Spielregeln-Bearbeitungsbildschirm.

---

## 🗂️ Kategorien & Optionen

```
Cloth-Config-Bildschirm ("Magnet, Let me get that! Configuration")
  ├── General Settings (Allgemeine Einstellungen)
  │     ├── Magnet Enabled (Standard: true)
  │     ├── Magnet Range (Standard: 12, Bereich: 1..64)
  │     ├── Instant Pickup (Standard: false)
  │     └── Magnet Noclip (Standard: true)
  ├── Speeds & Pull Heuristics (Geschwindigkeiten & Zieh-Heuristik)
  │     ├── Item Speed (Standard: 80, Bereich: 1..1000)
  │     └── Item Acceleration (Standard: 10, Bereich: 1..1000)
  ├── Line of Sight (LOS) (Sichtlinie)
  │     ├── Line of Sight Only (Standard: true)
  │     ├── Keep Moving if Unseen (Standard: true)
  │     ├── Blocked by Transparent (Standard: false)
  │     ├── Blocked by Flora (Standard: false)
  │     └── Blocked by Block Entities (Standard: false)
  └── Visuals & Performance (Grafik & Leistung)
        ├── Attract XP Orbs (Standard: true)
        ├── Magnet Particles (Standard: true)
        ├── Particle Count (Standard: 1, Bereich: 0..100)
        └── Max Particle Sources (Standard: 5, Bereich: 0..100)
```

---

## 📄 Rohe JSON-Struktur (`config/ig_magnet.json`)

```json
{
  "configVersion": 1,
  "enabled": true,
  "range": 12,
  "noClip": true,
  "affectsXp": true,
  "particles": true,
  "particleCount": 1,
  "maxParticleSources": 5,
  "speed": 80,
  "acceleration": 10,
  "instant": false,
  "losOnly": true,
  "keepMovingIfUnseen": true,
  "blockedByTransparent": false,
  "blockedByFlora": false,
  "blockedByBlockEntities": false
}
```

---

## 🔗 Verwandte Wiki-Dokumentation
* [[GameRules-Referenz|de_de-26.1.2-GameRules]]
* [[Entwickler-Setup & Build|de_de-26.1.2-Developer-Setup-and-Building]]
* [[Zurück zum MC 26.1.2 Portal|de_de-26.1.2-Home]]
