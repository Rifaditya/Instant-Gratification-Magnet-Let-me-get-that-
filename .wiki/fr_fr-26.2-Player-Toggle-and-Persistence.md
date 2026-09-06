# 🔄 Bascule Joueur, Persistance & Cycle de Vie (MC 26.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Technique | Paramètres Techniques |
| :--- | :--- |
| **Interface Passerelle** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Cible Mixin** | `net.minecraft.world.entity.player.Player` (`PlayerMixin.java`) |
| **Touche Client par Défaut** | `\` (Anti-slash) — `key.ig_magnet.toggle` |
| **Catégorie de Touche** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Paquet Réseau** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **Stockage Codec NBT** | `ValueOutput` / `ValueInput` sous la balise `"ig_magnet_enabled"` |
| **Événements de Cycle de Vie** | `ServerPlayConnectionEvents.JOIN`, `ServerPlayerEvents.COPY_FROM`, `ServerPlayerEvents.AFTER_RESPAWN` |

---

## 📖 Vue d'Ensemble de l'Architecture d'État

Sur les serveurs multijoueurs et dans les modpacks, chaque joueur a ses propres préférences : les bâtisseurs préfèrent souvent suspendre l'attraction d'objets pendant leurs constructions décoratives, tandis que les mineurs souhaitent une aspiration maximale.

**Magnet, Let me get that!** met en place un **état de bascule individuel par joueur**, persistant à 100 % à travers les rechargements de monde, les morts, les réapparitions et les changements de dimension.

```
                                [ACTION CLIENT]
                     Le Joueur Appuie sur la Touche ('\')
                                      |
                                      v
                         [ÉTAT LOCAL MIS À JOUR]
                     client.player -> isEnabled = !isEnabled
                     Overlay Barre d'Action : "Item Magnet: Enabled/Disabled"
                                      |
                                      v
                         [ENVOI DU PAQUET C2S]
                     ClientPlayNetworking.send(MagnetTogglePayload)
                                      |
                                      v
                               [RÉCEPTEUR SERVEUR]
                     context.server().execute(() -> {
                         ((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(val);
                     })
                                      |
         +----------------------------+----------------------------+
         |                                                         |
         v                                                         v
 [DONNÉES NBT PERSISTÉES]                              [CROCHETS DE CYCLE DE VIE]
 ValueOutput.putBoolean("ig_magnet_enabled")           - JOIN: Synchro S2C
 ValueInput.getBooleanOr("ig_magnet_enabled", true)    - COPY_FROM: Préservé à la Mort
                                                       - AFTER_RESPAWN: Synchro S2C Nouvelle Entité
```

---

## ⌨️ Raccourci Clavier Client & Notification sur Barre d'Action

* **Touche par Défaut** : `GLFW_KEY_BACKSLASH` (`\`), prévenant tout conflit avec les mods de minicarte et d'inventaire populaires.
* **Assistant Dynamique de Type de Clavier** : Emploie `ig_magnet$getKeyboardType()` pour résoudre en toute sécurité `InputConstants.Type.KEYBOARD` avec un repli élégant sur `KEYSYM` selon les versions de Fabric Loader.
* **Retour Visuel Instantané** : Chaque bascule déclenche une notification localisée sur la barre d'action :
  - `chat.ig_magnet.enabled` : `"Item Magnet: Enabled"`
  - `chat.ig_magnet.disabled` : `"Item Magnet: Disabled"`

---

## 💾 Stockage NBT & Sérialisation Codec Minecraft 26.2

L'état de bascule est sauvegardé directement dans le fichier de sauvegarde `.dat` du joueur à l'aide des pipelines de données `ValueOutput` et `ValueInput` de Minecraft 26.2 :

```java
// Sauvegarde dans le NBT du joueur
@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
    output.putBoolean("ig_magnet_enabled", this.ig_magnet$enabled);
}

// Chargement depuis le NBT du joueur
@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
    this.ig_magnet$enabled = input.getBooleanOr("ig_magnet_enabled", true);
}
```

---

## 🧬 Événements de Cycle de Vie Fabric & Gestion Mort/Réapparition

Lorsqu'un joueur meurt dans Minecraft, le jeu génère une entité `ServerPlayer` totalement nouvelle lors de sa réapparition. Le mod garantit une conservation intégrale :

1. **`ServerPlayerEvents.COPY_FROM`** : Transfère le booléen depuis `oldPlayer` vers `newPlayer` dès le clonage de l'entité.
2. **`ServerPlayerEvents.AFTER_RESPAWN`** : Transmet automatiquement un paquet S2C `MagnetTogglePayload` au client dès la reconnexion de la nouvelle entité joueur pour garder l'interface synchronisée.
3. **`ServerPlayConnectionEvents.JOIN`** : Synchronise l'état NBT sauvegardé du joueur vers le client lors de la connexion à un serveur dédié ou un monde LAN.

---

## 🔗 Documentation Connexe
* [[Commandes Brigadier & Bascules Serveur|fr_fr-26.2-Commands]]
* [[Architecture & Implémentations Mixin|fr_fr-26.2-Architecture-and-Mixins]]
* [[HUD & Diagnostics|fr_fr-26.2-HUD-and-Diagnostics]]
* [[Retour au Portail MC 26.2|fr_fr-26.2-Home]]
