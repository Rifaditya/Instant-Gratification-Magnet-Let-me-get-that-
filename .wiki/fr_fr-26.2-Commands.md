# 💻 Commandes Brigadier & Diagnostics en Jeu (MC 26.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Technique | Paramètres Techniques |
| :--- | :--- |
| **Classe de Commande** | `net.instantgratification.magnet.MagnetCommand` |
| **Littéraux Principaux** | `/magnet` et `/ig_magnet` (Alias miroirs identiques) |
| **Rappel d'Enregistrement** | `CommandRegistrationCallback.EVENT` |
| **Fichier Journal de Sortie** | `logs/ig_magnet_debug.log` |
| **Permissions Cibles** | Accessible à tous les joueurs (`toggle`) et Niveau OP 2 (`debug`) |

---

## 📖 Structure de l'Arborescence des Commandes

```
/magnet (ou /ig_magnet)
  ├── toggle
  └── debug
        └── log
```

---

## 🛠️ Référence des Sous-Commandes

### 1. `/magnet toggle` (ou `/ig_magnet toggle`)
Bascule l'état personnel d'aimant du joueur exécutant entre activé et désactivé.

* **Utilisation** : `/magnet toggle`
* **Exécution** : Appelle `((IMagnetPlayer) player).ig_magnet$toggleMagnet()`.
* **Retour Utilisateur** :
  - Si activé : `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
  - Si désactivé : `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)
* **Cas d'Usage** : Permet aux joueurs se connectant depuis un client vanilla (sur les serveurs avec mod uniquement côté serveur) ou aux joueurs sans raccourci clavier de basculer leur aimant.

---

### 2. `/magnet debug` (ou `/ig_magnet debug`)
Exécute un audit diagnostique instantané du joueur exécutant et des entités environnantes dans un rayon de 10 blocs.

* **Utilisation** : `/magnet debug`
* **Données Affichées** :
  ```text
  §e--- Magnet Debug Info ---
  Player Name: Steve
  Player UUID: c0b59055-ec50-4653-979d-c3c92d6ca897
  Global Master Toggle: true
  Player Toggle State: true
  Range: 12
  Instant Pickup: false
  LOS Only: true
  Spectator: false
  Dead/Dying: false
  Debug File Logger: DISABLED (Toggle with /magnet debug log)
  Nearby Items (10 blocks): 3
  Nearest Item ID: 412
  PlayerVisionTracker.canSee: true
  Vanilla Raycast canSee: true
  §e-------------------------
  ```
* **Cas d'Usage** : Diagnostique pourquoi un objet ne serait pas attiré (par ex. obstruction stricte de la vue, état mort du joueur, ou dérogations globales par GameRules).

---

### 3. `/magnet debug log` (ou `/ig_magnet debug log`)
Active ou désactive la journalisation diagnostique détaillée sur le disque.

* **Utilisation** : `/magnet debug log`
* **Exécution** : Inverse `MagnetDebugLogger.enabled = !MagnetDebugLogger.enabled`.
* **Sortie** : Écrit les ticks horodatés, réceptions de paquets, synchronisations de réapparition et rejets de visibilité dans `logs/ig_magnet_debug.log`.
* **Exemple d'Entrée de Journal** :
  ```log
  [2026-08-22 21:49:15.102] MagnetMod: Server received packet from Steve (c0b59055-...) enabled=true
  [2026-08-22 21:49:15.105] MagnetMovement: Entity 412 is now magnetized by Steve (c0b59055-...).
  ```

---

## 🔗 Documentation Connexe
* [[Bascule Joueur, Persistance & Cycle de Vie|fr_fr-26.2-Player-Toggle-and-Persistence]]
* [[Système de HUD & Diagnostics|fr_fr-26.2-HUD-and-Diagnostics]]
* [[Référence Complète des GameRules|fr_fr-26.2-GameRules]]
* [[Retour au Portail MC 26.2|fr_fr-26.2-Home]]
