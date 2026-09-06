# ⚙️ Référence Complète des GameRules (MC 26.1.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré de Catégorie | Détails |
| :--- | :--- |
| **Identifiant de Catégorie** | `magnet:magnet_category` |
| **Titre Localisé** | `Magnet, Let me get that!` (`gamerule.category.magnet.magnet_category`) |
| **Gestionnaire Enregistré** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **Classe de Registre** | `net.instantgratification.magnet.registry.ModGameRules` |
| **Nombre Total de Règles** | `15` Règles avec Espace de Noms |

---

## 📖 Administration des GameRules en Jeu

Toutes les mécaniques globales de **Magnet, Let me get that!** dans Minecraft 26.1.2 sont régies par des GameRules avec espace de noms, regroupées sous la catégorie `magnet:magnet_category`.

```
/gamerule ig:magnet_enabled true
/gamerule ig:magnet_range 12
/gamerule ig:magnet_speed 80
/gamerule ig:magnet_instant false
```

---

## 📋 Tableau de Référence Complet des GameRules

| Identifiant de GameRule | Type | Valeur par Défaut | Bornes | Nom Affiché Localisé | Description & Effet de Jeu |
| :--- | :---: | :---: | :---: | :--- | :--- |
| `ig:magnet_enabled` | Booléen | `true` | `true/false` | **Aimant Activé** | Bascule principale qui active ou désactive globalement le système d'aspiration. |
| `ig:magnet_range` | Entier | `12` | `1..64` | **Portée de l'Aimant** | Le rayon sphérique en blocs dans lequel le joueur attire les objets au sol. |
| `ig:magnet_noclip` | Booléen | `true` | `true/false` | **NoClip de l'Aimant** | Active la traversée de phase, permettant aux objets attirés de traverser les blocs solides. |
| `ig:magnet_affects_xp` | Booléen | `true` | `true/false` | **Attirer les Orbes d'XP** | Détermine si les orbes d'expérience sont attirés au même titre que les objets. |
| `ig:magnet_particles` | Booléen | `true` | `true/false` | **Particules de l'Aimant** | Génère des étincelles électriques le long de la trajectoire des entités attirées. |
| `ig:magnet_particle_count` | Entier | `1` | `0..100` | **Nombre de Particules** | Le nombre d'étincelles émises par source active à chaque tick de particule. |
| `ig:magnet_max_particle_sources` | Entier | `5` | `0..100` | **Sources Max de Particules** | Nombre maximal d'entités autorisées à émettre des particules pour préserver les FPS. |
| `ig:magnet_speed` | Entier | `80` | `1..1000` | **Vitesse des Objets** | Pourcentage de la vitesse maximale ($80 = 0.8\text{ bloc/tick} = 16.0\text{ m/s}$). |
| `ig:magnet_acceleration` | Entier | `10` | `1..1000` | **Accélération des Objets** | Facteur d'interpolation d'accélération par tick ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_instant` | Booléen | `false` | `true/false` | **Ramassage Instantané** | Si activé, les objets sont instantanément téléportés dans l'inventaire via expansion AABB. |
| `ig:magnet_los_only` | Booléen | `true` | `true/false` | **Ligne de Visée Uniquement** | Impose une visibilité directe ; empêche d'attirer des objets derrière des murs infranchissables. |
| `ig:magnet_keep_moving_if_unseen` | Booléen | `true` | `true/false` | **Conserver l'Élan Hors Vue** | Permet à un objet magnétisé en vue de conserver son élan si la vue est coupée en vol. |
| `ig:magnet_blocked_by_transparent` | Booléen | `false` | `true/false` | **Bloqué par les Transparents** | Si activé, le verre, les vitres, barreaux de fer et blocs translucides bloquent la visée. |
| `ig:magnet_blocked_by_flora` | Booléen | `false` | `true/false` | **Bloqué par la Flore** | Si activé, les herbes hautes, cultures, fleurs et feuilles bloquent la visée. |
| `ig:magnet_blocked_by_block_entities` | Booléen | `false` | `true/false` | **Bloqué par Entités de Bloc** | Si activé, les coffres, lits, tonneaux et boîtes de shulker bloquent la visée. |

---

## 🔗 Documentation Connexe
* [[Physique d'Attraction & Traversée de Phase|fr_fr-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Interface Cloth Config & Valeurs par Défaut|fr_fr-26.1.2-Configuration]]
* [[Retour au Portail MC 26.1.2|fr_fr-26.1.2-Home]]
