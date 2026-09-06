# 🔌 API & Addon-Integration (MC 26.1.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| API-Infobox | Technische Parameter |
| :--- | :--- |
| **Spielerstatus-Manager** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Entitäts-Schnittstelle** | `net.instantgratification.magnet.IMagnetEntity` |
| **Zentrale Bewegungsfassade** | `net.instantgratification.magnet.MagnetMovement` |
| **GameRule-API** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **Sichtlinien-API** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Entwickler-Integration für Dritt-Mods

Dritt-Mods und Instant-Gratification-Begleit-Addons können in Minecraft 26.1.2 direkt mit **Magnet, Let me get that!** interagieren.

---

## 🧑‍💻 Spielerstatus-Verwaltung (`MagnetPlayerState`)

Frage Spieler-Magneteinstellungen direkt über statische Hilfsmethoden ab oder passe sie an:

```java
package net.instantgratification.magnet;

public class MagnetPlayerState {
    public static boolean isMagnetEnabled(Player player);
    public static void setMagnetEnabled(Player player, boolean enabled);
    public static boolean toggleMagnet(Player player);
}
```

### Anwendungsbeispiel:
```java
// Prüfen, ob der Spieler den Magneten aktiv hat
if (MagnetPlayerState.isMagnetEnabled(player)) {
    // Eigene Logik...
}

// Magnet programmgesteuert deaktivieren
MagnetPlayerState.setMagnetEnabled(player, false);
```

---

## 📦 Entitäts-Magnetisierungs-Schnittstelle (`IMagnetEntity`)

Caste jede `Entity`-Instanz zu `IMagnetEntity`, um Phasenverschiebungs- oder Magnetisierungs-Flags zu steuern:

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

## 🚀 Statische Bewegungsfassade (`MagnetMovement.pull`)

Löse programmgesteuertes Heranziehen von Gegenständen oder XP zu einem Spieler aus:

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Architektur & Mixins|de_de-26.1.2-Architecture-and-Mixins]]
* [[Entwickler-Setup & Build|de_de-26.1.2-Developer-Setup-and-Building]]
* [[Zurück zum MC 26.1.2 Portal|de_de-26.1.2-Home]]
