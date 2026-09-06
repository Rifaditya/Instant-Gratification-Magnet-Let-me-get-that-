# 🧩 Architecture & Cibles Mixin (MC 26.1.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Architecture | Paramètres Techniques |
| :--- | :--- |
| **Paquet Racine** | `net.instantgratification.magnet` |
| **Configuration Mixin** | `src/main/resources/magnet.mixins.json` |
| **Access Widener** | `src/main/resources/magnet.accesswidener` |
| **Niveau de Compatibilité** | `JAVA_25` |
| **Nombre Total de Mixins** | `2` (`MixinEntity`, `PlayerMixin`) |

---

## 🌳 Arborescence de l'Architecture des Paquets

```text
net.instantgratification.magnet/
├── IMagnetEntity.java                  # Interface entité pour NoClip & magnétisation
├── MagnetCommand.java                  # Enregistrement des commandes serveur (/magnet toggle)
├── MagnetManager.java                  # Boucle de détection spatiale & exécution
├── MagnetMod.java                      # Initialiseur du mod & récepteurs de paquets
├── MagnetModClient.java                # Initialiseur client, raccourci Ctrl+M & toast overlay
├── MagnetMovement.java                 # Mathématiques vectorielles, vélocité lerp & particules
├── MagnetPlayerState.java              # Stockage de bascule joueur en ConcurrentHashMap thread-safe
├── MagnetTogglePayload.java            # Record de paquet réseau & StreamCodec composite
├── SecondaryVisionCheck.java           # Raycast granulaire (Flore, Entités de bloc, Verre)
├── config/
│   ├── ClothConfigScreenHelper.java    # Constructeur d'interface Cloth Config Fabric
│   ├── MagnetConfig.java               # Persistance de configuration JSON & POJO
│   └── ModMenuIntegration.java         # Point d'entrée d'API ModMenu sécurisé par réflexion
├── mixin/
│   ├── MixinEntity.java                # Injections NoClip et annulation de gravité dans Entity
│   └── PlayerMixin.java                # Injection de tick d'exécution & ramassage instantané dans Player
└── registry/
    └── ModGameRules.java               # Enregistrements DynamicGameRuleManager
```

---

## 📋 Analyse Détaillée des Injections Mixin

### 1. `net.instantgratification.magnet.mixin.MixinEntity`
Cible `net.minecraft.world.entity.Entity` et implémente `IMagnetEntity`.

| Méthode Injectée | Point d'Injection Cible | Action & Comportement |
| :--- | :--- | :--- |
| `ig_magnet$decrementNoClipTicks` | `@Inject(method = "tick", at = @At("HEAD"))` | Décrémente le compte à rebours `noClipTicks` de 1 par tick lorsqu'il est actif. |
| `ig_magnet$preventPushOut` | `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` | Annule l'éjection de bloc vanilla si `noClipTicks > 0` sur le serveur. |
| `ig_magnet$forceNoClipOnMove` | `@Inject(method = "move", at = @At("HEAD"))` | Mémorise `originalNoPhysics` et force `entity.noPhysics = true` si `noClipTicks > 0`. |
| `ig_magnet$restoreNoPhysicsAfterMove` | `@Inject(method = "move", at = @At("RETURN"))` | Rétablit `entity.noPhysics = originalNoPhysics` une fois le mouvement achevé. |
| `ig_magnet$conditionalCancelGravity` | `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` | Annule la gravité descendante *uniquement* lorsque l'objet est dans un bloc solide. |

---

### 2. `net.instantgratification.magnet.mixin.PlayerMixin`
Cible `net.minecraft.world.entity.player.Player`.

| Méthode Injectée | Point d'Injection Cible | Action & Comportement |
| :--- | :--- | :--- |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | Invoque `MagnetManager.tick(player)` sur le serveur à chaque tick de jeu. |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | Élargit la boîte de ramassage (`pickupArea.inflate(range)`) si `ig:magnet_instant` est actif. |

---

## 🔗 Documentation Connexe
* [[Physique d'Attraction & Traversée de Phase|fr_fr-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Configuration Développeur & Compilation|fr_fr-26.1.2-Developer-Setup-and-Building]]
* [[Retour au Portail MC 26.1.2|fr_fr-26.1.2-Home]]
