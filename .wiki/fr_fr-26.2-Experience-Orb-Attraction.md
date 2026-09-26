# ✨ Attraction des Orbes d'Expérience (MC 26.2)

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

Selon la philosophie Instant Gratification, laisser des orbes d'expérience éparpillés sur le sol ou coincés au plafond des cavernes nuit à la fluidité du jeu. **Magnet, Let me get that!** intègre une prise en charge de premier plan pour attirer les entités `ExperienceOrb` au même titre que les objets au sol.

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

## ⚡ Physique Synchronisée & Traversée de Phase

Lorsque `ig:magnet_affects_xp` est activé, tous les orbes d'expérience à portée bénéficient exactement des mêmes mécanismes de pointe que les objets lâchés :

1. **Traversée de Phase (NoClip)** : Les orbes traversent les blocs solides lors de leur déplacement, évitant de tourner en rond ou de rebondir indéfiniment contre les parois.
2. **Accélération Dynamique** : Les orbes d'expérience partagent les calculs d'interpolation de vitesse ($s = \text{speed}/100.0$) et d'accélération ($a = \text{accel}/100.0$).
3. **Filtrage de la Ligne de Visée** : Si `ig:magnet_los_only` est actif, les orbes d'expérience doivent réussir à la fois le raycast 360° primaire et les vérifications granulaires secondaires.

---

## 🛡️ Performances & Prévention du Lag (Plafond de Particules)

Les fermes à monstres intensives ou les affrontements contre l'Ender Dragon peuvent générer des centaines d'orbes simultanément. Créer des particules sur chaque orbe à chaque tick entraînerait une chute drastique du taux de rafraîchissement (FPS).

Le mod prévient toute baisse de performance via une **Régulation Globale des Sources de Particules** :

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

* **Plafond Global** : Seules les $N$ premières entités (configurées par `ig:magnet_max_particle_sources`, par défaut : `5`) ont le droit d'émettre des étincelles lors d'un tick donné.
* **Échelonnement Temporel** : Les particules ne sont émises que lorsque `(entity.tickCount + entity.getId()) % 4 == 0` (tous les 4 ticks = 5 fois par seconde).

---

## ⚙️ Configuration & GameRules Pertinentes

| GameRule | Type | Valeur par Défaut | Unité / Plage | Description |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Booléen | `true` | `true/false` | Détermine si les orbes d'expérience sont attirés. |
| `ig:magnet_particles` | Booléen | `true` | `true/false` | Bascule principale pour les traînées d'étincelles visuelles. |
| `ig:magnet_particle_count` | Entier | `1` | `0..100` | Nombre de particules émises par source active. |
| `ig:magnet_max_particle_sources` | Entier | `5` | `0..100` | Nombre maximal d'entités autorisées à émettre des particules. |

---

## 🔗 Documentation Connexe
* [[Physique d'Attraction & Traversée de Phase|fr_fr-26.2-Vacuum-and-Phase-Shifting]]
* [[Référence Complète des GameRules|fr_fr-26.2-GameRules]]
* [[HUD & Diagnostics Visuels|fr_fr-26.2-HUD-and-Diagnostics]]
* [[Retour au Portail MC 26.2|fr_fr-26.2-Home]]
