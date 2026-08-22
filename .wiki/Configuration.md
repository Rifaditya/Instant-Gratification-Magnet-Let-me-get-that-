# 🎨 YACL Configuration GUI & ModMenu (MC 26.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Configuration Infobox | Details |
| :--- | :--- |
| **Config File Path** | `config/ig_magnet.json` |
| **GUI Library** | YetAnotherConfigLib v3 (`dev.isxander:yet-another-config-lib`) |
| **ModMenu Entrypoint** | `net.instantgratification.magnet.config.ModMenuIntegration` |
| **GUI Screen Helper** | `net.instantgratification.magnet.config.YaclScreenHelper` |
| **Classloading Safety** | Isolated via `GuiHelper.getOptionalFactory` |

---

## 📖 Configuration System Architecture

**Magnet, Let me get that!** provides an optional client-side configuration GUI powered by **YetAnotherConfigLib v3 (YACL)** and accessible through **ModMenu**.

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
                "ig_magnet",
                "net.instantgratification.magnet.config.YaclScreenHelper",
                "createScreen"
        );
    }
}
```

---

## ⚠️ Configuration Precedence Warning

> ⚠️ **Important Notice**:  
> Changes made in the ModMenu GUI or `config/ig_magnet.json` **only affect the baseline default values for NEW worlds**.  
> To modify the settings of an active, already-created world, use the in-game [[GameRules Complete Reference|GameRules]] via `/gamerule` or the vanilla GameRules edit screen.

---

## 🗂️ Configuration Categories & Options

```
YACL Configuration Screen ("Magnet, Let me get that! Configuration")
  ├── General Settings
  │     ├── Magnet Enabled (Default: true)
  │     ├── Magnet Range (Default: 12, Range: 1..64)
  │     ├── Instant Pickup (Default: false)
  │     └── Magnet Noclip (Default: true)
  ├── Speeds & Pull Heuristics
  │     ├── Item Speed (Default: 80%, Range: 1..1000)
  │     └── Item Acceleration (Default: 10%, Range: 1..1000)
  ├── Line of Sight (LOS)
  │     ├── Line of Sight Only (Default: true)
  │     ├── Keep Moving if Unseen (Default: true)
  │     ├── Blocked by Transparent (Default: false)
  │     ├── Blocked by Flora (Default: false)
  │     └── Blocked by Block Entities (Default: false)
  └── Visuals & Performance
        ├── Attract XP Orbs (Default: true)
        ├── Magnet Particles (Default: true)
        ├── Particle Count (Default: 1, Range: 0..100)
        └── Max Particle Sources (Default: 5, Range: 0..100)
```

---

## 📄 Raw JSON Structure (`config/ig_magnet.json`)

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

## 🔗 Related Wiki Documentation
* [[GameRules Complete Reference|GameRules]]
* [[Developer Setup & Building|Developer-Setup-and-Building]]
* [[Return to Home Portal|Home]]
