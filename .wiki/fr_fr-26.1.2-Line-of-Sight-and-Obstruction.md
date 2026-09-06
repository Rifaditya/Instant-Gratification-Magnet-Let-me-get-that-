# 👁️ Ligne de Visée & Mécaniques d'Obstacle (MC 26.1.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Technique | Paramètres Techniques |
| :--- | :--- |
| **Moteur de Vision Primaire** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **Moteur de Vision Secondaire** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **Champ de Vision Sphérique** | $360.0^\circ$ (Perception omnidirectionnelle intégrale) |
| **Tolérance de Contact** | Seuil de contact cible de $0.3\text{ m}$ |
| **Balise de Rétention Mémorielle** | `ig$isMagnetized()` / `ig$setMagnetized()` |
| **Règle d'Élan** | `ig:magnet_keep_moving_if_unseen = true` |
| **Implémentation du Contexte** | Record statique `VisionContext` |

---

## 📖 Pipeline de Vision à Double Passe

Dans Minecraft 26.1.2, la ligne de visée est évaluée via un **Pipeline de Vision à Double Passe** optimisé et sans allocation mémoire superflue :

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

```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **Champ Omnidirectionnel** : Avec un angle de champ de vision de $360^\circ$, les objets lâchés au-dessus, au-dessous ou derrière le joueur sont attirés de manière fluide sans forcer de rotation de caméra.
* **Tolérance Sub-Voxel** : La marge de $0.3\text{m}$ évite les faux rejets lorsque les objets reposent à fleur de mur solide.

---

## 🌿 Passe 2 : Traversée Granulaire des Blocs (`SecondaryVisionCheck`)

```java
private record VisionContext(
        Level level,
        boolean blockTransparent,
        boolean blockFlora,
        boolean blockEntities,
        CollisionContext collisionContext,
        Vec3 start,
        Vec3 end
) {}
```

* **Flore (`ig:magnet_blocked_by_flora`)** : Vérifie la présence de `BushBlock` et `LeavesBlock`.
* **Entités de Bloc (`ig:magnet_blocked_by_block_entities`)** : Vérifie `state.hasBlockEntity()` (Coffres, Boîtes de Shulker, Lits).
* **Blocs Transparents (`ig:magnet_blocked_by_transparent`)** : Teste `state.getVisualShape().clip(...)` contre le Verre, les Vitres et les Dalles.

---

## 🚀 Continuité de l'Élan (`keepMovingIfUnseen`)

* Dès la première détection, `((IMagnetEntity) entity).ig$setMagnetized()` balise l'entité.
* Si `ig:magnet_keep_moving_if_unseen = true`, l'objet continue d'être attiré même en contournant des obstacles, tant qu'il a été préalablement magnétisé.

---

## ⚙️ Configuration & GameRules Pertinentes

| GameRule | Type | Valeur par Défaut | Description |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Booléen | `true` | Exige une visibilité directe pour attirer les objets. |
| `ig:magnet_keep_moving_if_unseen` | Booléen | `true` | Poursuit l'attraction si la vue est coupée pendant le vol. |
| `ig:magnet_blocked_by_transparent` | Booléen | `false` | Si true, le verre et les blocs transparents bloquent la visibilité. |
| `ig:magnet_blocked_by_flora` | Booléen | `false` | Si true, l'herbe et les fleurs bloquent la visibilité. |
| `ig:magnet_blocked_by_block_entities` | Booléen | `false` | Si true, les coffres et entités de bloc bloquent la visibilité. |

---

## 🔗 Documentation Connexe
* [[Physique d'Attraction & Traversée de Phase|fr_fr-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Référence Complète des GameRules|fr_fr-26.1.2-GameRules]]
* [[Retour au Portail MC 26.1.2|fr_fr-26.1.2-Home]]
