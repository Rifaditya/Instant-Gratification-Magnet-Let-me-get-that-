# 👁️ Ligne de Visée & Mécaniques d'Obstacle (MC 26.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Technique | Paramètres Techniques |
| :--- | :--- |
| **Moteur de Vision Primaire** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **Moteur de Vision Secondaire** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **Champ de Vision Sphérique** | $360.0^\circ$ (Perception omnidirectionnelle intégrale) |
| **Tolérance de Contact** | Seuil de contact cible de $0.3\text{ m}$ |
| **Balise de Rétention Mémorielle** | `ig_magnet$isMagnetized()` / `ig_magnet$setMagnetized()` |
| **Règle d'Élan** | `ig:magnet_keep_moving_if_unseen = true` |
| **Règles de Filtrage Granulaire** | Blocs transparents, Flore, Entités de bloc |

---

## 📖 Architecture de Vision à Double Passe

Afin d'empêcher la collecte abusive d'objets à travers les parois de grottes ou les bases sécurisées tout en maintenant des performances sans saccade, **Magnet, Let me get that!** utilise un **Pipeline de Vision à Double Passe** ultra-rapide :

```
                                +---------------------------+
                                |    OBJET CIBLE DÉTECTÉ    |
                                +-------------+-------------+
                                              |
                                              v
                              +-------------------------------+
                              |    PASSE PRIMAIRE (360° LOS)  |
                              |   PlayerVisionTracker.canSee  |
                              +---------------+---------------+
                                              |
                        +---------------------+---------------------+
                        |                                           |
                    [VISIBLE]                                   [OBSTRUÉ]
                        |                                           |
                        v                                           v
      +-----------------------------------+               +--------------------+
      |    PASSE SECONDAIRE (GRANULAIRE)  |               |  TEST CONTINUITÉ   |
      |     SecondaryVisionCheck.canSee   |               | keepMovingIfUnseen |
      +-----------------+-----------------+               +---------+----------+
                        |                                           |
            +-----------+-----------+                     +---------+---------+
            |                       |                     |                   |
        [SUCCÈS]                 [BLOQUÉ]             [MAGNÉTISÉ]       [NON MAGNÉTISÉ]
            |                       |                     |                   |
            v                       v                     v                   v
    +---------------+       +---------------+     +---------------+   +---------------+
    | ATTIRE OBJET  |       | REJETER/ARRÊT |     | CONTINUE VOL  |   | REJET VOL     |
    |  & MAGNÉTISE  |       +---------------+     +---------------+   +---------------+
    +---------------+
```

---

## 🔍 Passe 1 : Vérification de Ligne de Visée Sphérique Primaire à 360°

La passe primaire sollicite le moteur de raycasting optimisé de DasikLibrary :
```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **Champ de Vision Omnidirectionnel** : Avec un cône de vision de $360.0^\circ$, le joueur peut attirer des objets situés directement derrière sa tête, au-dessus de lui ou sous ses pieds, tant qu'aucun mur solide ne s'interpose.
* **Tolérance de Contact de 0.3m** : Lorsque des objets sont logés étroitement contre les coins de blocs, le raycasting sub-voxel doté d'un rayon de $0.3\text{m}$ évite les rejets erronés.

---

## 🌿 Passe 2 : Filtrage Granulaire de l'État des Blocs (`SecondaryVisionCheck`)

Si la passe primaire réussit, le mod évalue les règles d'obstruction granulaires facultatives via `BlockGetter.traverseBlocks` :

```java
public static boolean canSee(Player player, Entity target, boolean blockTransparent, boolean blockFlora, boolean blockEntities)
```

1. **Filtrage de la Flore & du Feuillage (`ig:magnet_blocked_by_flora`)** :
   - Vérifie si un bloc traversé hérite de `BushBlock` (herbes hautes, fleurs, cultures, pousses d'arbres) ou de `LeavesBlock` (feuilles d'arbres).
   - Si activé (`true`), la végétation constitue une barrière opaque bloquant l'attraction.
2. **Entités de Bloc Interactives (`ig:magnet_blocked_by_block_entities`)** :
   - Vérifie `state.hasBlockEntity()` sur l'ensemble des positions traversées.
   - Si activé (`true`), les Coffres, Coffres Piégés, Tonneaux, Boîtes de Shulker, Lits et Distributeurs obstruent la ligne de visée.
3. **Blocs Transparents & Non Pleins (`ig:magnet_blocked_by_transparent`)** :
   - Effectue un raycast sur `state.getVisualShape(...)`.
   - Si activé (`true`), le Verre, les Vitres, les Barreaux de fer, les Barrières, les Dalles et les Escaliers bloquent la collecte d'objets.

---

## 🚀 Continuité de l'Élan (`keepMovingIfUnseen`)

Lors d'extractions minières rapides ou de combats intenses, les objets attirés contournant un angle perdent fréquemment la ligne de visée directe. Plutôt que de les figer net ou de les laisser choir dans la lave :

1. **Balise de Magnétisation** : Dès qu'un objet est aperçu dans la ligne de visée, `((IMagnetEntity) entity).ig_magnet$setMagnetized()` active un drapeau booléen en mémoire.
2. **Conservation de l'Élan** : Si l'objet perd la ligne de visée au cours des ticks suivants :
   - Si `ig:magnet_keep_moving_if_unseen = true` ET `ig_magnet$isMagnetized() == true` : L'objet continue sa trajectoire vers le joueur.
   - Si `ig:magnet_keep_moving_if_unseen = false` : La traction s'interrompt immédiatement dès la rupture de visibilité.
   - Si l'objet n'a **jamais été vu** (par ex. apparu derrière un mur suite à une explosion ou un distributeur) : L'attraction est immédiatement refusée.

---

## ⚙️ Configuration & GameRules Pertinentes

| GameRule | Type | Valeur par Défaut | Description |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Booléen | `true` | Exige une visibilité directe pour attirer les objets. |
| `ig:magnet_keep_moving_if_unseen` | Booléen | `true` | Poursuit la traction d'un objet magnétisé si la vue est coupée en vol. |
| `ig:magnet_blocked_by_transparent` | Booléen | `false` | Si true, le verre et les blocs transparents bloquent la visibilité. |
| `ig:magnet_blocked_by_flora` | Booléen | `false` | Si true, les herbes, feuilles et fleurs bloquent la visibilité. |
| `ig:magnet_blocked_by_block_entities` | Booléen | `false` | Si true, les coffres, lits et conteneurs bloquent la visibilité. |

---

## 🔗 Documentation Connexe
* [[Physique d'Attraction & Traversée de Phase|fr_fr-26.2-Vacuum-and-Phase-Shifting]]
* [[Référence Complète des GameRules|fr_fr-26.2-GameRules]]
* [[Architecture & Mixins|fr_fr-26.2-Architecture-and-Mixins]]
* [[Retour au Portail MC 26.2|fr_fr-26.2-Home]]
