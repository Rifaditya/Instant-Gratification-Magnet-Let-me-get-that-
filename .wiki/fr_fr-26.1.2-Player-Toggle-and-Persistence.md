# 🔄 Bascule Joueur & État de Session (MC 26.1.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Technique | Paramètres Techniques |
| :--- | :--- |
| **Classe de Stockage d'État** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Touche Client par Défaut** | `Ctrl+M` (`GLFW.GLFW_KEY_M` + `isControlDown()`) |
| **Catégorie de Touche** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Paquet Réseau** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **API Barre d'Action** | `client.gui.setOverlayMessage(Component, boolean)` |

---

## 📖 Vue d'Ensemble de l'Architecture d'État

Dans Minecraft 26.1.2, les préférences de bascule des joueurs sont gérées pendant la session active du serveur via `MagnetPlayerState` à l'aide d'une `ConcurrentHashMap` thread-safe :

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

## ⌨️ Raccourci Clavier Client (`Ctrl+M`)

* **Combinaison par Défaut** : `Ctrl+M` (`GLFW_KEY_M` + `isControlDown()`), avec prise en charge multiplateforme de la touche Commande sur macOS (`GLFW_KEY_LEFT_SUPER`).
* **Retour Visuel sur la Barre d'Action** :
  - `chat.ig_magnet.enabled` : `"Item Magnet: Enabled"`
  - `chat.ig_magnet.disabled` : `"Item Magnet: Disabled"`

---

## 📡 Protocole de Synchronisation Réseau

```
[CLIENT]                                                           [SERVEUR]
Le joueur appuie sur Ctrl+M
MagnetPlayerState.setMagnetEnabled(player, newState)
ClientPlayNetworking.send(new MagnetTogglePayload(newState))
                                               ------------------> Récepteur Serveur
                                                                   MagnetPlayerState.setMagnetEnabled(player, payload.enabled())
```

---

## 🔗 Documentation Connexe
* [[Commandes Serveur & Bascules|fr_fr-26.1.2-Commands]]
* [[Architecture & Mixins|fr_fr-26.1.2-Architecture-and-Mixins]]
* [[Retour au Portail MC 26.1.2|fr_fr-26.1.2-Home]]
