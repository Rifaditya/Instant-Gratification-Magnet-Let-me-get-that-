# 🔌 API & Intégration d'Addons (MC 26.1.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré d'API | Paramètres Techniques |
| :--- | :--- |
| **Gestionnaire d'État Joueur** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Interface Entité** | `net.instantgratification.magnet.IMagnetEntity` |
| **Façade de Mouvement Centrale** | `net.instantgratification.magnet.MagnetMovement` |
| **API des GameRules** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **API de Vision** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Intégration pour Développeurs de Mods Tiers

Les mods tiers et addons compagnons de la collection Instant Gratification peuvent s'intégrer directement avec **Magnet, Let me get that!** dans Minecraft 26.1.2.

---

## 🧑‍💻 Gestion de l'État des Joueurs (`MagnetPlayerState`)

Consultez ou modifiez les préférences de l'aimant d'un joueur via des méthodes statiques :

```java
package net.instantgratification.magnet;

public class MagnetPlayerState {
    public static boolean isMagnetEnabled(Player player);
    public static void setMagnetEnabled(Player player, boolean enabled);
    public static boolean toggleMagnet(Player player);
}
```

### Exemple d'Utilisation :
```java
// Vérifier si le joueur a son aimant actif
if (MagnetPlayerState.isMagnetEnabled(player)) {
    // Logique personnalisée...
}

// Désactiver l'aimant par programmation
MagnetPlayerState.setMagnetEnabled(player, false);
```

---

## 📦 Interface de Magnétisation d'Entité (`IMagnetEntity`)

Convertissez n'importe quelle instance de `Entity` en `IMagnetEntity` pour manipuler ses drapeaux de traversée de phase ou de magnétisation :

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

## 🚀 Façade Statique de Mouvement (`MagnetMovement.pull`)

Déclenchez par programmation l'attraction d'un objet ou d'expérience vers un joueur :

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

---

## 🔗 Documentation Connexe
* [[Architecture & Cibles Mixin|fr_fr-26.1.2-Architecture-and-Mixins]]
* [[Configuration Développeur & Compilation|fr_fr-26.1.2-Developer-Setup-and-Building]]
* [[Retour au Portail MC 26.1.2|fr_fr-26.1.2-Home]]
