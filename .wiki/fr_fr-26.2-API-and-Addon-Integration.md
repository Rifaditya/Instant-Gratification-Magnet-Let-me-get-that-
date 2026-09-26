# 🔌 API & Intégration d'Addons (MC 26.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré d'API | Paramètres Techniques |
| :--- | :--- |
| **Interface Joueur** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Interface Entité** | `net.instantgratification.magnet.IMagnetEntity` |
| **Façade de Mouvement Centrale** | `net.instantgratification.magnet.MagnetMovement` |
| **API des GameRules** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **API de Vision** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Intégration pour Développeurs de Mods Tiers

Les mods tiers, outils d'administration et addons de la collection Instant Gratification peuvent interagir directement avec **Magnet, Let me get that!** pour consulter l'état de l'aimant d'un joueur, déclencher des attractions programmées ou contourner des obstacles.

---

## 🧑‍💻 Interface d'État Joueur (`IMagnetPlayer`)

Convertissez n'importe quelle instance de `Player` ou `ServerPlayer` en `IMagnetPlayer` pour interroger ou modifier les préférences :

```java
package net.instantgratification.magnet;

public interface IMagnetPlayer {
    boolean ig_magnet$isMagnetEnabled();
    void ig_magnet$setMagnetEnabled(boolean enabled);
    boolean ig_magnet$toggleMagnet();
}
```

### Exemple d'Utilisation :
```java
// Vérifier si le joueur a activé son aimant
if (((IMagnetPlayer) player).ig_magnet$isMagnetEnabled()) {
    // Logique personnalisée de l'addon...
}

// Désactiver l'aimant par programmation (ex: assis sur un trône ou dans un mini-jeu)
((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(false);
```

---

## 📦 Interface de Magnétisation d'Entité (`IMagnetEntity`)

Convertissez n'importe quelle instance d'entité `Entity` (comme des butins spéciaux, projectiles ou orbes) en `IMagnetEntity` :

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig_magnet$setMagnetNoClip();
    boolean ig_magnet$isMagnetNoClip();
    void ig_magnet$setMagnetized();
    boolean ig_magnet$isMagnetized();
}
```

### Exemple d'Utilisation :
```java
// Octroyer une traversée NoClip temporaire de 2 ticks à une entité personnalisée
((IMagnetEntity) customItem).ig_magnet$setMagnetNoClip();
```

---

## 🚀 Façade Statique de Mouvement (`MagnetMovement.pull`)

Les addons peuvent attirer manuellement des entités vers n'importe quel joueur en utilisant le moteur physique et de détection de ligne de visée intégré :

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

```java
// Attirer l'entité cible vers le joueur avec émission de particules
MagnetMovement.pull(specialDrop, player, true);
```

---

## 🔗 Documentation Connexe
* [[Architecture & Cibles Mixin|fr_fr-26.2-Architecture-and-Mixins]]
* [[Configuration Développeur & Compilation|fr_fr-26.2-Developer-Setup-and-Building]]
* [[Retour au Portail MC 26.2|fr_fr-26.2-Home]]
