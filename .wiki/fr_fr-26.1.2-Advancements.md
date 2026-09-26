# 🏆 Progrès & Respect du Fonctionnement Vanilla (MC 26.1.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré de Progression | Détails |
| :--- | :--- |
| **Fichiers JSON de Progrès Personnalisés** | `Aucun (Recours volontaire au système Vanilla)` |
| **Axe de Conception** | Instant Gratification (IG) |
| **Déclencheurs de Progrès** | 100 % Natif Vanilla `Player.touch(ItemEntity)` |
| **Prérequis de Déblocage** | Aucun (Zéro obstacle arbitraire) |

---

## 📖 Politique d'Absence & Philosophie de Conception

En accord strict avec la philosophie modulaire de **Instant Gratification (IG)**, **Magnet, Let me get that!** ne comporte délibérément **aucun arbre de progrès personnalisé ni accomplissement arbitraire**.

Le mod est conçu comme une amélioration ergonomique intrinsèque des interactions de survie fondamentales. Toutes les fonctionnalités sont disponibles immédiatement dès l'entrée dans le monde, sans nécessiter de quêtes fastidieuses, d'arbres technologiques ni de barrières de progression artificielles.

```
+-----------------------------------------------------------------------------------+
|                        PRINCIPE INSTANT GRATIFICATION                             |
|                                                                                   |
|  « La 'marche de la honte' (faire 5 blocs pour ramasser un minerai extrait) est  |
|   un péché contre la fluidité du jeu. IG Magnet n'est pas une récompense d'arbre  |
|   technologique ; c'est le prolongement du joueur. Si vous le voyez, prenez-le. » |
+-----------------------------------------------------------------------------------+
```

---

## 🎮 Compatibilité Totale avec les Progrès Vanilla

Puisque le vol d'aspiration classique et le [[Mode de Ramassage Instantané|fr_fr-26.1.2-Instant-Pickup-Mode]] utilisent les circuits d'absorption natifs de Minecraft `ItemEntity.playerTouch()` et `ExperienceOrb.playerTouch()` :

1. **Déclencheurs d'Étapes Vanilla** : Ramasser des diamants, des débris antiques ou des bâtons de blaze via l'aimant déclenche immédiatement les critères des progrès vanilla correspondants (par ex. *« Des diamants ! »*, *« Couvrez-moi de débris »*).
2. **Compatibilité avec les Mods de Quêtes** : Les mods de quêtes détectant l'obtention d'objets dans l'inventaire fonctionnent immédiatement sans nécessiter de ponts de compatibilité spécifiques.
3. **Suivi des Statistiques** : Les statistiques officielles de Minecraft (`stat.pickup.minecraft.*`) s'incrémentent avec une parfaite exactitude.

---

## 🔗 Documentation Connexe
* [[Mode de Ramassage Instantané|fr_fr-26.1.2-Instant-Pickup-Mode]]
* [[Référence Complète des GameRules|fr_fr-26.1.2-GameRules]]
* [[Retour au Portail MC 26.1.2|fr_fr-26.1.2-Home]]
