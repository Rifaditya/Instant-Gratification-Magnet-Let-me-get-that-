# 🧲 Attraction & Traversée de Phase (MC 26.1.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Technique | Paramètres Techniques |
| :--- | :--- |
| **Classe Système** | `net.instantgratification.magnet.MagnetMovement` |
| **Événement Déclencheur** | Tick Joueur Serveur (`PlayerMixin` $\rightarrow$ `MagnetManager.tick`) |
| **Portée d'Attraction par Défaut** | `12` blocs (`ig:magnet_range`) |
| **Vitesse Maximale par Défaut** | `80%` ($0.8\text{ bloc/tick} = 16.0\text{ m/s}$) |
| **Accélération par Défaut** | `10%` (facteur d'interpolation de $0.10\text{ lerp/tick}$) |
| **Traversée de Phase (NoClip)** | Activée (`ig:magnet_noclip = true`) |
| **Vecteur Cible** | Position des Yeux du Joueur (`player.getEyePosition()`) |
| **Boost d'Élévation au Sol** | $+0.05\text{ m}$ sur l'axe Y lorsque `entity.onGround()` |

---

## 📖 Vue d'Ensemble du Système

Le système d'aspiration dans Minecraft 26.1.2 attire les entités `ItemEntity` vers le niveau des yeux du joueur en utilisant une vélocité vectorielle calculée chaque tick.

Pour éviter que les objets ne soient bloqués par les rebords des parois ou les parois de blocs, la **Traversée de Phase (NoClip)** permet aux objets en cours d'attraction de passer à travers les voxels de blocs solides.

```
+-------------+      Ligne de Visée OK     +----------------------+      Vélocité Lerp Appliquée     +------------------+
| Entité Obj. | -------------------------> | Définit NoClip (2 t) | ------------------------------> | Pos. Yeux Joueur |
+-------------+                            +----------------------+                                 +------------------+
                                                      |
                                                      v
                                           [Annule Éjection Bloc]
                                           [Ignore Collision Blocs]
                                           [Annule Gravité Murale]
```

---

## 🧮 Physique & Mathématiques Vectorielles

### 1. Vecteur Unitaire Directionnel
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|}$$

### 2. Interpolation de Vélocité
$$\text{Scalaire de Vitesse } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0} = 0.8\text{ bloc/tick}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
$$\text{Facteur d'Accélération } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0} = 0.10$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 3. Boost Anti-Frottement au Sol
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 Traversée de Phase (Moteur NoClip)

1. **Activation de l'État** : `((IMagnetEntity) entity).ig$setMagnetNoClip()` active un compte à rebours de 2 ticks.
2. **Contournement de la Physique (`MixinEntity.java`)** :
   - `move(MoverType, Vec3)` : `@Inject(at = @At("HEAD"))` sauvegarde `originalNoPhysics` et définit `entity.noPhysics = true`.
   - `move(MoverType, Vec3)` : `@Inject(at = @At("RETURN"))` rétablit `entity.noPhysics = originalNoPhysics`.
3. **Prévention de l'Expulsion** : `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` bloque les forces d'éjection des parois vanilla.
4. **Annulation de la Gravité en Mur** : `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` annule la gravité *uniquement* lorsque l'objet est physiquement à l'intérieur d'un bloc.

---

## ⚙️ Configuration & GameRules Pertinentes

| GameRule | Type | Valeur par Défaut | Description |
| :--- | :---: | :---: | :--- |
| `ig:magnet_enabled` | Booléen | `true` | Bascule principale pour la mécanique d'aspiration. |
| `ig:magnet_range` | Entier | `12` | Rayon d'aspiration en blocs (1 à 64). |
| `ig:magnet_speed` | Entier | `80` | Pourcentage de vitesse maximale ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Entier | `10` | Pourcentage du facteur d'accélération ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Booléen | `true` | Active la traversée des blocs pendant le vol. |

---

## 🔗 Documentation Connexe
* [[Ligne de Visée & Pénétration des Obstacles|fr_fr-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Mode de Ramassage Instantané|fr_fr-26.1.2-Instant-Pickup-Mode]]
* [[Architecture & Implémentations Mixin|fr_fr-26.1.2-Architecture-and-Mixins]]
* [[Retour au Portail MC 26.1.2|fr_fr-26.1.2-Home]]
