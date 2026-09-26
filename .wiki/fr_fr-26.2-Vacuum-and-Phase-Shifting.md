# 🧲 Attraction & Traversée de Phase (MC 26.2)

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

Le mécanisme d'aspiration central de **Magnet, Let me get that!** analyse chaque tick serveur la présence d'instances de `ItemEntity` dans le rayon configuré du joueur et les attire vers le niveau de ses yeux à l'aide d'une interpolation non linéaire fluide.

Pour éviter que les objets ne restent bloqués contre les rebords de blocs, sous la canopée des arbres ou dans les crevasses de minerai, le mod active la **Traversée de Phase (NoClip)**, permettant aux objets en vol de traverser sans encombre les voxels des blocs solides.

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

Lors de l'attraction d'un objet, la trajectoire est calculée directement dans l'espace euclidien tridimensionnel :

### 1. Vecteur vers la Cible
Soit $\vec{p}_{\text{entity}} = (x_e, y_e, z_e)$ la position actuelle de l'objet, et $\vec{p}_{\text{eye}} = (x_p, y_p + h_{\text{eye}}, z_p)$ la position des yeux du joueur.
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|} = \frac{\vec{v}_{\text{target}}}{\sqrt{\Delta x^2 + \Delta y^2 + \Delta z^2}}$$

### 2. Vitesse Maximale Cible
Le vecteur de vitesse cible adapte la direction unitaire $\hat{d}$ selon le paramètre de vitesse configuré :
$$\text{Scalaire de Vitesse } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
*À la valeur par défaut ($80\%$), $s = 0.8\text{ bloc/tick}$. À $20\text{ ticks/s}$, la vitesse terminale est de $16.0\text{ m/s}$.*

### 3. Accélération Non Linéaire (Lerp)
La vitesse est mise à jour via une interpolation linéaire (`Vec3.lerp`) basée sur le facteur d'accélération $a$ :
$$\text{Facteur d'Accélération } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0}$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 4. Boost Anti-Frottement au Sol
Si l'objet repose sur une surface solide (`entity.onGround() == true`), la friction au sol est immédiatement rompue pour empêcher le traînage au sol :
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 Traversée de Phase (Moteur NoClip)

Lorsque `ig:magnet_noclip` est activé, les objets reçoivent une fenêtre NoClip de 2 ticks :

1. **Activation de l'État** : `((IMagnetEntity) entity).ig_magnet$setMagnetNoClip()` définit `noClipTicks = 2`.
2. **Détournement du Mouvement (`MixinEntity.java`)** :
   - `move(MoverType, Vec3)` : `@Inject(at = @At("HEAD"))` sauvegarde `originalNoPhysics` et force `entity.noPhysics = true`.
   - `move(MoverType, Vec3)` : `@Inject(at = @At("RETURN"))` rétablit `entity.noPhysics = originalNoPhysics`.
3. **Annulation de l'Expulsion** : `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` empêche l'algorithme vanilla d'éjecter violemment les objets hors des blocs.
4. **Annulation Conditionnelle de la Gravité** : `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` annule la gravité descendante *uniquement* lorsque l'objet intersecte physiquement un bloc solide (`!level.noCollision(...)`), préservant ainsi les trajectoires paraboliques naturelles en plein air.

---

## ⚙️ Configuration & GameRules Pertinentes

| GameRule | Type | Valeur par Défaut | Unité / Plage | Description |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_enabled` | Booléen | `true` | `true/false` | Bascule principale pour toute la logique d'aspiration. |
| `ig:magnet_range` | Entier | `12` | `1..64` blocs | Rayon sphérique maximal d'aspiration. |
| `ig:magnet_speed` | Entier | `80` | `1..1000%` | Pourcentage de la vitesse maximale ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Entier | `10` | `1..1000%` | Pourcentage d'accélération d'attraction ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Booléen | `true` | `true/false` | Active la traversée des blocs solides pendant le vol. |

---

## 🔗 Documentation Connexe
* [[Ligne de Visée & Pénétration des Obstacles|fr_fr-26.2-Line-of-Sight-and-Obstruction]]
* [[Mode de Ramassage Instantané|fr_fr-26.2-Instant-Pickup-Mode]]
* [[Architecture & Injections Mixin|fr_fr-26.2-Architecture-and-Mixins]]
* [[Retour au Portail MC 26.2|fr_fr-26.2-Home]]
