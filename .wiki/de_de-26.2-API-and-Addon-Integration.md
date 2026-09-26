# 🔌 API & Addon-Integration (MC 26.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| API-Infobox | Technische Parameter |
| :--- | :--- |
| **Spieler-Schnittstelle** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Entitäts-Schnittstelle** | `net.instantgratification.magnet.IMagnetEntity` |
| **Zentrale Bewegungsfassade** | `net.instantgratification.magnet.MagnetMovement` |
| **GameRule-API** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **Sichtlinien-API** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Entwickler-Integration für Dritt-Mods

Dritt-Mods, Server-Dienstprogramme und Instant-Gratification-Erweiterungen können direkt mit **Magnet, Let me get that!** interagieren, um Spielerstatus abzufragen, programmgesteuerte Anziehungen auszulösen oder Hindernisse zu umgehen.

---

## 🧑‍💻 Spieler-Status-Schnittstelle (`IMagnetPlayer`)

Caste jede `Player`- oder `ServerPlayer`-Instanz zu `IMagnetPlayer`, um Magneteinstellungen abzufragen oder anzupassen:

```java
package net.instantgratification.magnet;

public interface IMagnetPlayer {
    boolean ig_magnet$isMagnetEnabled();
    void ig_magnet$setMagnetEnabled(boolean enabled);
    boolean ig_magnet$toggleMagnet();
}
```

### Anwendungsbeispiel:
```java
// Prüfen, ob der Spieler den Magneten aktiv hat
if (((IMagnetPlayer) player).ig_magnet$isMagnetEnabled()) {
    // Eigene Addon-Logik...
}

// Magnet programmgesteuert deaktivieren (z. B. beim Sitzen auf einem Thron oder in einem Minispiel)
((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(false);
```

---

## 📦 Entitäts-Magnetisierungs-Schnittstelle (`IMagnetEntity`)

Caste jede `Entity`-Instanz (wie Mob-Drops, Projektile oder XP-Orbs) zu `IMagnetEntity`:

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig_magnet$setMagnetNoClip();
    boolean ig_magnet$isMagnetNoClip();
    void ig_magnet$setMagnetized();
    boolean ig_magnet$isMagnetized();
}
```

### Anwendungsbeispiel:
```java
// Eigener Entität temporäre 2-Tick-Phasenverschiebung gewähren
((IMagnetEntity) customItem).ig_magnet$setMagnetNoClip();
```

---

## 🚀 Statische Bewegungsfassade (`MagnetMovement.pull`)

Addon-Mods können Entitäten unter Nutzung der integrierten Physik- und Sichtlinien-Engine manuell zu jedem Spieler heranziehen:

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

```java
// Zielentität mit Partikelspuren zum Spieler ziehen
MagnetMovement.pull(specialDrop, player, true);
```

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Architektur & Mixins|de_de-26.2-Architecture-and-Mixins]]
* [[Entwickler-Setup & Build|de_de-26.2-Developer-Setup-and-Building]]
* [[Zurück zum MC 26.2 Portal|de_de-26.2-Home]]
