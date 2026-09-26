# 🔄 Spieler-Umschaltung & Sitzungsstatus (MC 26.1.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Infobox | Technische Parameter |
| :--- | :--- |
| **Statusspeicher-Klasse** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Standard-Client-Hotkey** | `Strg+M` (`GLFW.GLFW_KEY_M` + `isControlDown()`) |
| **Tasten-Kategorie** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Netzwerk-Nutzlast** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **Actionbar-API** | `client.gui.setOverlayMessage(Component, boolean)` |

---

## 📖 Übersicht der Status-Architektur

In Minecraft 26.1.2 werden die Umschalteinstellungen des Spielers während der aktiven Server-Sitzung über `MagnetPlayerState` mit einer threadsicheren `ConcurrentHashMap` verwaltet:

```java
public class MagnetPlayerState {
    private static final Map<UUID, Boolean> playerStates = new ConcurrentHashMap<>();

    public static boolean isMagnetEnabled(Player player) {
        return playerStates.getOrDefault(player.getUUID(), true);
    }

    public static void setMagnetEnabled(Player player, boolean enabled) {
        playerStates.put(player.getUUID(), enabled);
    }

    public static boolean toggleMagnet(Player player) {
        boolean newState = !isMagnetEnabled(player);
        setMagnetEnabled(player, newState);
        return newState;
    }
}
```

---

## ⌨️ Client-Tastenbelegung (`Strg+M`)

* **Standard-Kombination**: `Strg+M` (`GLFW_KEY_M` + `isControlDown()`), mit plattformübergreifender Unterstützung für die Befehlstaste auf macOS (`GLFW_KEY_LEFT_SUPER`).
* **Visuelles Actionbar-Feedback**:
  - `chat.ig_magnet.enabled`: `"Item Magnet: Enabled"`
  - `chat.ig_magnet.disabled`: `"Item Magnet: Disabled"`

---

## 📡 Netzwerk-Synchronisations-Protokoll

```
[CLIENT]                                                           [SERVER]
Spieler drückt Strg+M
MagnetPlayerState.setMagnetEnabled(player, newState)
ClientPlayNetworking.send(new MagnetTogglePayload(newState))
                                               ------------------> Server-Empfänger
                                                                   MagnetPlayerState.setMagnetEnabled(player, payload.enabled())
```

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Server-Befehle|de_de-26.1.2-Commands]]
* [[Architektur & Mixins|de_de-26.1.2-Architecture-and-Mixins]]
* [[Zurück zum MC 26.1.2 Portal|de_de-26.1.2-Home]]
