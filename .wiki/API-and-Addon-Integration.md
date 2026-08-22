# 🔌 API & Addon Integration (MC 26.1.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| API Infobox | Technical Parameters |
| :--- | :--- |
| **Player State Manager** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Entity Interface** | `net.instantgratification.magnet.IMagnetEntity` |
| **Core Movement Facade** | `net.instantgratification.magnet.MagnetMovement` |
| **GameRule API** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **Vision API** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Inter-Mod Developer Integration

Third-party mods and Instant Gratification companion addons can interface directly with **Magnet, Let me get that!** in Minecraft 26.1.2.

---

## 🧑‍💻 Player State Management (`MagnetPlayerState`)

Query or modify player magnet preferences directly via static helper methods:

```java
package net.instantgratification.magnet;

public class MagnetPlayerState {
    public static boolean isMagnetEnabled(Player player);
    public static void setMagnetEnabled(Player player, boolean enabled);
    public static boolean toggleMagnet(Player player);
}
```

### Example Usage:
```java
// Check if player has magnet active
if (MagnetPlayerState.isMagnetEnabled(player)) {
    // Custom logic...
}

// Programmatically disable magnet
MagnetPlayerState.setMagnetEnabled(player, false);
```

---

## 📦 Entity Magnetization Interface (`IMagnetEntity`)

Cast any `Entity` instance to `IMagnetEntity`:

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig$setMagnetNoClip();
    boolean ig$isMagnetNoClip();
    void ig$setMagnetized();
    boolean ig$isMagnetized();
}
```

---

## 🚀 Static Movement Facade (`MagnetMovement.pull`)

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

---

## 🔗 Related Wiki Documentation
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[Developer Setup & Building|Developer-Setup-and-Building]]
* [[Return to Home Portal|Home]]
