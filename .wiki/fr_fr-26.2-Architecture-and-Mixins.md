# 🧩 Architecture & Cibles Mixin (MC 26.2)

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
├── IMagnetEntity.java                  # Interface d'entité pour NoClip & magnétisation
├── IMagnetPlayer.java                  # Interface joueur pour état de bascule & accesseurs
├── MagnetCommand.java                  # Commandes Brigadier (/magnet toggle, /magnet debug)
├── MagnetDebugLogger.java              # Journalisation persistante thread-safe sur disque
├── MagnetManager.java                  # Boucle de détection spatiale & exécution
├── MagnetMod.java                      # Point d'entrée serveur/universel & récepteurs réseau
├── MagnetModClient.java                # Initialiseur client, touches & notifications overlay
├── MagnetMovement.java                 # Trajectoire vectorielle, vélocité lerp & particules
├── MagnetTogglePayload.java            # Record de paquet réseau & StreamCodec composite
├── SecondaryVisionCheck.java           # Raycast granulaire (Flore, Entités de bloc, Verre)
├── config/
│   ├── MagnetConfig.java               # POJO & persistance de configuration JSON
│   ├── ModMenuIntegration.java         # Point d'entrée ModMenu sécurisé par réflexion
│   └── YaclScreenHelper.java           # Constructeur d'interface YetAnotherConfigLib v3
├── mixin/
│   ├── MixinEntity.java                # Injection NoClip et annulation de gravité dans Entity
│   └── PlayerMixin.java                # Injection persistance NBT & ramassage instantané dans Player
├── registry/
│   └── ModGameRules.java               # Enregistrements DynamicGameRuleManager
└── util/
    └── ModVersionGuard.java            # Vérification de sécurité au runtime Knot ClassLoader
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
Cible `net.minecraft.world.entity.player.Player` et implémente `IMagnetPlayer`.

| Méthode Injectée | Point d'Injection Cible | Action & Comportement |
| :--- | :--- | :--- |
| `ig_magnet$addAdditionalSaveData` | `@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))` | Sérialise le booléen `ig_magnet_enabled` dans le NBT joueur via `ValueOutput`. |
| `ig_magnet$readAdditionalSaveData` | `@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))` | Désérialise `ig_magnet_enabled` depuis le NBT joueur via `ValueInput.getBooleanOr()`. |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | Invoque `MagnetManager.tick(player)` sur le serveur à chaque tick de jeu. |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | Élargit la boîte de ramassage (`pickupArea.inflate(range)`) si `ig:magnet_instant` est actif. |

---

## 🛡️ Vérification de Cohérence Knot ClassLoader (`ModVersionGuard`)

Pour prémunir les serveurs et les mondes contre tout chargement sous des versions incompatibles de Minecraft :

```java
public final class ModVersionGuard {
    public static void checkClass(String modName, String requiredClassName) {
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        // Vérifie l'existence de la classe cible avant de poursuivre l'initialisation...
    }
}
```

Appelé dans `MagnetMod.onInitialize()` :
```java
ModVersionGuard.checkClass("Magnet", "net.minecraft.world.entity.item.ItemEntity");
```

---

## 🔗 Documentation Connexe
* [[Physique d'Attraction & Traversée de Phase|fr_fr-26.2-Vacuum-and-Phase-Shifting]]
* [[Bascule Joueur, Persistance & Cycle de Vie|fr_fr-26.2-Player-Toggle-and-Persistence]]
* [[API & Intégration d'Addons|fr_fr-26.2-API-and-Addon-Integration]]
* [[Retour au Portail MC 26.2|fr_fr-26.2-Home]]
