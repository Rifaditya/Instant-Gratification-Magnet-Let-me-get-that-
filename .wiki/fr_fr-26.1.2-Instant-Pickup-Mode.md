# ⚡ Mode de Ramassage Instantané (MC 26.1.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Technique | Paramètres Techniques |
| :--- | :--- |
| **Classe Système** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **GameRule d'Activation** | `ig:magnet_instant` (Par défaut : `false`) |
| **GameRule de Rayon** | `ig:magnet_range` (Défaut : `12`, Plage : `1..64`) |
| **Point d'Injection** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **Variable Cible** | Boîte de Collecte du Joueur (`AABB pickupArea`) |

---

## 📖 Vue d'Ensemble du Ramassage Instantané

Dans Minecraft 26.1.2, le **Mode de Ramassage Instantané** supprime le temps de vol des objets en élargissant la boîte de collecte du joueur dans `Player.aiStep()`, injectant immédiatement les objets au sol dans l'inventaire via les circuits de collecte vanilla.

```
+-----------------------------------------------------------------------------------+
|                            TICK VANILLA JOUEUR aiStep()                           |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
                +---------------------------------------------------+
                |   PlayerMixin.ig_magnet$expandPickupArea()        |
                |   pickupArea = pickupArea.inflate(range);         |
                +---------------------------------------------------+
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
    Level level = player.level();
    
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    
    return pickupArea;
}
```

---

## ⚙️ Configuration & GameRules Pertinentes

| GameRule | Type | Valeur par Défaut | Description |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Booléen | `false` | Si activé, les objets se téléportent directement vers le joueur au lieu de voler. |
| `ig:magnet_range` | Entier | `12` | Rayon en blocs pour la zone de collecte élargie. |
| `ig:magnet_enabled` | Booléen | `true` | Bascule principale du mod. |

---

## 🔗 Documentation Connexe
* [[Physique d'Attraction & Traversée de Phase|fr_fr-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Référence Complète des GameRules|fr_fr-26.1.2-GameRules]]
* [[Retour au Portail MC 26.1.2|fr_fr-26.1.2-Home]]
