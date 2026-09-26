# 📊 HUD, Visuels & Diagnostics (MC 26.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Visuel | Paramètres Techniques |
| :--- | :--- |
| **API Barre d'Action** | `client.gui.hud.setOverlayMessage(Component, boolean)` |
| **Type de Particule** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Modulo de Régulation des Particules** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **Fichier Journal de Débogage** | `logs/ig_magnet_debug.log` |
| **Classe de Journalisation** | `net.instantgratification.magnet.MagnetDebugLogger` |

---

## 🖥️ Retour Visuel sur la Barre d'Action (Actionbar)

Lorsque le joueur change l'état de son aimant via le raccourci clavier (`\`) ou la commande serveur (`/magnet toggle`), le HUD client affiche immédiatement un message d'information épuré et discret directement au-dessus de la barre d'inventaire :

* **Message d'Activation** : `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Message de Désactivation** : `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Vérifié d'après : Hud.java (26.2+)
if (newState) {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Traînées Visuelles d'Étincelles Électriques

Pendant que les objets et les orbes d'XP sont en cours de magnétisation en vol, ils émettent de subtiles traînées visuelles de particules `ParticleTypes.ELECTRIC_SPARK` :

```
[Objet Attiré]  --->  ✨  --->  ✨  --->  ✨  --->  [Yeux du Joueur]
```

### Règles de Régulation des Particules :
1. **Plafond de Sources** : Régulé par `ig:magnet_max_particle_sources` (par défaut : `5`), garantissant que les amas massifs de minerai ne surchargent pas le moteur graphique.
2. **Échelonnement Temporel** : Seul 1 tick sur 4 génère des particules pour une entité donnée, décalé selon son identifiant unique : `(entity.tickCount + entity.getId()) % 4 == 0`.
3. **Contrôle du Nombre** : Déterminé par source active via `ig:magnet_particle_count` (par défaut : `1`).

---

## 📝 Enregistreur de Fichier Dédié au Débogage (`MagnetDebugLogger`)

Pour les administrateurs serveur et les créateurs de modpacks inspectant les délimitations de visée ou les paquets réseau, le mod fournit un enregistreur sur disque asynchrone et thread-safe écrivant dans `logs/ig_magnet_debug.log` :

* **Activation** : Exécutez `/magnet debug log` en jeu.
* **Format** : `[yyyy-MM-dd HH:mm:ss.SSS] [Contexte] Message`
* **Exemple d'Extrait du Journal** :
  ```text
  [2026-08-22 21:50:00.124] MagnetMod: Server received packet from Alex (e7b1a234-...) enabled=true
  [2026-08-22 21:50:00.130] MagnetMovement: Entity 592 is now magnetized by Alex (e7b1a234-...).
  [2026-08-22 21:50:01.450] PlayerMixin: Server tick for Alex (e7b1a234-...) isMagnetEnabled=true
  ```

---

## 🔗 Documentation Connexe
* [[Commandes Brigadier & Diagnostics en Jeu|fr_fr-26.2-Commands]]
* [[Attraction des Orbes d'Expérience|fr_fr-26.2-Experience-Orb-Attraction]]
* [[Retour au Portail MC 26.2|fr_fr-26.2-Home]]
