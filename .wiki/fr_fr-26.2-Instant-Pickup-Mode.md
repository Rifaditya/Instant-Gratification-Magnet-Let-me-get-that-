# ⚡ Mode de Ramassage Instantané (MC 26.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Technique | Paramètres Techniques |
| :--- | :--- |
| **Point d'Injection** | `net.minecraft.world.entity.player.Player.aiStep()` |
| **Cible Mixin** | `PlayerMixin.java` (`@ModifyVariable` sur `pickupArea`) |
| **GameRule d'Activation** | `ig:magnet_instant` (Par défaut : `false`) |
| **GameRule de Portée** | `ig:magnet_range` (Défaut : `12` blocs) |
| **Latence Physique** | $0\text{ ticks}$ (Absorption directe dans l'inventaire) |
| **Bypass de Ligne de Visée** | Oui (Expansion native de l'AABB Minecraft) |

---

## 📖 Vue d'Ensemble du Ramassage Instantané

Pour les joueurs recherchant un débit de ramassage maximal lors d'opérations de minage à grande échelle, de carottage ou d'excavations massives, **Magnet, Let me get that!** intègre un **Mode de Ramassage Instantané** optionnel.

Plutôt que d'attirer physiquement les objets dans les airs, ce mode étend directement la boîte de collision native de collecte (`AABB`) du joueur pour absorber immédiatement tout objet ou orbe d'expérience entrant dans le rayon configuré.

```
                                  [COMPARAISON DE DÉBIT]
                
   MODE ATTRACTION STANDARD                      MODE RAMASSAGE INSTANTANÉ
+----------------------------+                 +----------------------------+
|  Objet au Sol Détecté      |                 |  Objet au Sol Détecté      |
+--------------+-------------+                 +--------------+-------------+
               |                                              |
               v                                              v
+----------------------------+                 +----------------------------+
| Raycast Ligne de Visée     |                 | Expansion AABB du Joueur   |
| Trajectoire NoClip         |                 | Absorption Directe         |
| Vol de 10 à 30 ticks       |                 | Latence : 0 tick           |
+--------------+-------------+                 +--------------+-------------+
               |                                              |
               +----------------------+-----------------------+
                                      |
                                      v
+-----------------------------------------------------------------------------------+
|               EXÉCUTION VANILLA ItemEntity.playerTouch(Player)                    |
|   - Empilement d'inventaire natif & ramassages partiels                           |
|   - Sons et animations de collecte vanilla (item.pickup / entity.experience_orb) |
|   - Déclencheurs de statistiques & de progrès officiels                           |
|   - Conservation des objets excédentaires si inventaire plein                     |
+-----------------------------------------------------------------------------------+
```

---

## 🧩 Implémentation Architecturale (`PlayerMixin.java`)

```java
@ModifyVariable(
        method = "aiStep",
        at = @At(value = "STORE"),
        ordinal = 0
)
private AABB ig_magnet$expandPickupArea(AABB pickupArea) {
    Player player = (Player) (Object) this;
    if (!this.ig_magnet$isMagnetEnabled() || player.isDeadOrDying() || player.isSpectator()) {
        return pickupArea;
    }

    Level level = player.level();
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_ENABLED) && ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    return pickupArea;
}
```

### Garanties d'Ingénierie Clés :
1. **Verrous de Sécurité** : Si le joueur est mort ou mourant (`player.isDeadOrDying()`), en mode spectateur (`player.isSpectator()`), ou si son aimant personnel est désactivé (`!isMagnetEnabled()`), la boîte de collecte n'est jamais étendue.
2. **Autorité Côté Serveur** : La logique s'exécute strictement sur le serveur logique (`!level.isClientSide()`), éliminant tout dédoublement d'objets fantômes et toute désynchronisation d'inventaire.
3. **Absence de Conflit de Mouvement** : Lorsque `ig:magnet_instant` est actif, `MagnetMovement.pull()` interrompt automatiquement la mise à jour des vitesses afin que la physique et la collecte instantanée n'entrent pas en conflit.

---

## ⚙️ Configuration & GameRules Pertinentes

| GameRule | Type | Valeur par Défaut | Description |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Booléen | `false` | Active la téléportation instantanée dans l'inventaire sans vol physique. |
| `ig:magnet_range` | Entier | `12` | Le rayon (en blocs) dont la boîte de collision AABB est agrandie. |
| `ig:magnet_enabled` | Booléen | `true` | Bascule principale du mod. |

---

## 🔗 Documentation Connexe
* [[Physique d'Attraction & Traversée de Phase|fr_fr-26.2-Vacuum-and-Phase-Shifting]]
* [[Bascule Joueur & Persistance|fr_fr-26.2-Player-Toggle-and-Persistence]]
* [[Architecture & Mixins|fr_fr-26.2-Architecture-and-Mixins]]
* [[Retour au Portail MC 26.2|fr_fr-26.2-Home]]
