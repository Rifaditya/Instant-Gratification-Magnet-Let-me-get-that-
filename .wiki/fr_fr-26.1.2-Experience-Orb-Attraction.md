# ✨ Attraction des Orbes d'Expérience (MC 26.1.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Technique | Paramètres Techniques |
| :--- | :--- |
| **Classe d'Entité Cible** | `net.minecraft.world.entity.ExperienceOrb` |
| **Classe Gestionnaire** | `net.instantgratification.magnet.MagnetManager` |
| **GameRule d'Activation** | `ig:magnet_affects_xp` (Par défaut : `true`) |
| **Requête d'Analyse** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **Type de Particule** | `ParticleTypes.ELECTRIC_SPARK` |
| **Régulation des Particules** | Partagée avec les objets via `ig:magnet_max_particle_sources` |

---

## 📖 Mécaniques d'Attraction de l'Expérience

Dans Minecraft 26.1.2, les orbes d'expérience (`ExperienceOrb`) bénéficient de la même logique d'aspiration instantanée que les objets au sol.

```
+------------------+    getEntitiesOfClass      +-----------------------+     MagnetMovement.pull     +--------------------+
| AABB Scan Joueur | -------------------------> | List<ExperienceOrb>   | --------------------------> | Collecte Joueur    |
+------------------+                            +-----------------------+                             +--------------------+
                                                            |
                                                            v
                                                [Applique NoClip Phase-Shift]
                                                [Applique Vecteur Vélocité Lerp]
                                                [Particules d'Étincelles Régulées]
```

---

## ⚡ Mouvement de Succion & Traversée de Phase

1. **Phase Shifting (NoClip)** : Les orbes d'expérience traversent les parois de roche ou de minerai grâce à `((IMagnetEntity) orb).ig$setMagnetNoClip()`.
2. **Interpolation de Vélocité** : Les orbes accélèrent de manière fluide vers la position des yeux du joueur selon les pourcentages de vitesse et d'accélération configurés.
3. **Filtrage Ligne de Visée** : Lorsque `ig:magnet_los_only` est actif, les orbes doivent être visibles par le joueur ou disposer de l'élan de magnétisation actif.

---

## 🛡️ Prévention du Lag & Mutualisation des Particules

```java
int maxParticleSources = ModGameRules.getInt(player.level(), ModGameRules.MAGNET_MAX_PARTICLE_SOURCES);
int particleSourceCount = 0;

for (ExperienceOrb orb : orbs) {
    boolean shouldSpawnParticles = false;
    if (particleSourceCount < maxParticleSources) {
        shouldSpawnParticles = true;
        particleSourceCount++;
    }
    MagnetMovement.pull(orb, player, shouldSpawnParticles);
}
```

* **Sources Max de Particules** : Seules les $N$ premières entités par tick génèrent des traînées visuelles.
* **Repli Sécurisé Client** : Dans `MagnetMovement.java`, si le niveau n'est pas un `ServerLevel`, l'émission se replie proprement sur `level.addParticle(...)`.

---

## ⚙️ Configuration & GameRules Pertinentes

| GameRule | Type | Valeur par Défaut | Description |
| :--- | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Booléen | `true` | Détermine si l'aimant attire les orbes d'expérience. |
| `ig:magnet_particles` | Booléen | `true` | Bascule principale pour les effets de particules. |
| `ig:magnet_particle_count` | Entier | `1` | Nombre d'étincelles émises par entité et par tick de particule. |
| `ig:magnet_max_particle_sources` | Entier | `5` | Nombre maximal d'entités autorisées à émettre simultanément. |

---

## 🔗 Documentation Connexe
* [[Physique d'Attraction & Traversée de Phase|fr_fr-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Référence Complète des GameRules|fr_fr-26.1.2-GameRules]]
* [[Retour au Portail MC 26.1.2|fr_fr-26.1.2-Home]]
