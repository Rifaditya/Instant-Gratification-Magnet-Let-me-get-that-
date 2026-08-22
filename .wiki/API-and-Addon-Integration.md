# 🔌 API & Addon Integration (MC 26.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| API Infobox | Technical Parameters |
| :--- | :--- |
| **Player Interface** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Entity Interface** | `net.instantgratification.magnet.IMagnetEntity` |
| **Core Movement Facade** | `net.instantgratification.magnet.MagnetMovement` |
| **GameRule API** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **Vision API** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Inter-Mod Developer Integration

Third-party mods, server utilities, and Instant Gratification addons can interface directly with **Magnet, Let me get that!** to query player magnet states, trigger programmatic pulls, or bypass obstacles.

---

## 🧑‍💻 Player State Interface (`IMagnetPlayer`)

Cast any `Player` or `ServerPlayer` instance to `IMagnetPlayer` to query or modify magnet preferences:

```java
package net.instantgratification.magnet;

public interface IMagnetPlayer {
    boolean ig_magnet$isMagnetEnabled();
    void ig_magnet$setMagnetEnabled(boolean enabled);
    boolean ig_magnet$toggleMagnet();
}
```

### Example Usage:
```java
// Check if player has magnet active
if (((IMagnetPlayer) player).ig_magnet$isMagnetEnabled()) {
    // Custom addon logic...
}

// Programmatically disable magnet
((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(false);
```

---

## 📦 Entity Magnetization Interface (`IMagnetEntity`)

Cast any `Entity` instance to `IMagnetEntity`:

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig_magnet$setMagnetNoClip();
    boolean ig_magnet$isMagnetNoClip();
    void ig_magnet$setMagnetized();
    boolean ig_magnet$isMagnetized();
}
```

---

## 🚀 Static Movement Facade (`MagnetMovement.pull`)

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

```java
// Pull target entity to player with particle trails
MagnetMovement.pull(specialDrop, player, true);
```

---

## 🔗 Related Wiki Documentation
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[Developer Setup & Building|Developer-Setup-and-Building]]
* [[Return to Home Portal|Home]]
