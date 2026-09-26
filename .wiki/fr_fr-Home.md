# 🧲 Magnet, Let me get that! — Wiki Officiel

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

Bienvenue sur le wiki technique et de gameplay officiel de **Magnet, Let me get that!** (`ig_magnet`), un mod d'attraction intrinsèque d'objets et d'expérience conçu pour les versions modernes de Minecraft sous Fabric.

Fidèle aux principes de conception de la collection **Instant Gratification (IG)**, ce mod élimine la « marche de la honte » — cette corvée fastidieuse consistant à devoir faire 5 pas pour ramasser un objet que vous venez d'extraire ou un monstre vaincu. Si vous pouvez le voir, vous devriez pouvoir le posséder.

---

## 🧭 Portail Multi-Versions

Sélectionnez votre version de Minecraft pour accéder aux guides de jeu dédiés, à la documentation technique, aux tableaux des GameRules et aux références d'architecture :

| Version de Minecraft | Statut de Publication | Version Active du Mod | Moteur de Configuration | Lien du Portail |
| :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | 🟢 Moderne Actif | `1.3.9+26.2` | YACL v3 + ModMenu | [[Aperçu 26.2\|fr_fr-26.2-Home]] |
| **Minecraft 26.1.2** | 🟢 Point d'Ancrage Moderne | `1.1.2+26.1.2` | Cloth Config + ModMenu | [[Aperçu 26.1.2\|fr_fr-26.1.2-Home]] |

### 🚀 Accès Direct aux Portails :
* 📦 **Minecraft 26.2** : [[👉 Accéder au Portail de Documentation Minecraft 26.2|fr_fr-26.2-Home]]
* 📦 **Minecraft 26.1.2** : [[👉 Accéder au Portail de Documentation Minecraft 26.1.2|fr_fr-26.1.2-Home]]

Pour une analyse détaillée des chaînes d'outils, des matrices de dépendances, des emplacements d'archives et de la rétrocompatibilité, consultez la [[Matrice des Versions|fr_fr-Version-Compatibility]].

---

## ⚡ Matrice des Fonctionnalités Principales

```
                      +-----------------------------+
                      | ÉMETTEUR DE SUCCION JOUEUR  |
                      +--------------+--------------+
                                     |
              +----------------------+----------------------+
              |                                             |
              v                                             v
  +-----------------------+                     +-----------------------+
  |  MODE ATTRACTION STD  |                     | MODE RAMASSAGE INST.  |
  +-----------+-----------+                     +-----------+-----------+
              |                                             |
    [Vérification de Vue]                         [Expansion de l'AABB]
    [Raycast Sphérique 360°]                      [Latence de Vol Nulle]
    [Traversée NoClip]                            [Inventaire Direct]
    [Vélocité Dynamique Lerp]                               |
              |                                             |
              +----------------------+----------------------+
                                     |
                                     v
                      +-----------------------------+
                      |   OBJET & ORBE EXP CAPTURÉ  |
                      +-----------------------------+
```

* **Attraction Intelligente à 360°** : Attire les objets lâchés et les orbes d'expérience dans un rayon de blocs configurable (par défaut : 12 blocs, jusqu'à 64).
* **Traversée de Phase (NoClip)** : Les objets magnétisés traversent sans entrave les blocs solides, empêchant le butin de se coincer dans les débris d'explosion ou les anfractuosités des mines.
* **Prise en Compte de la Ligne de Visée (LOS)** : Intègre un raycast sphérique primaire à 360° via le `PlayerVisionTracker` de DasikLibrary ainsi qu'un filtrage granulaire optionnel pour les blocs transparents (verre), la flore (herbes hautes, feuilles) et les entités de bloc (coffres).
* **Continuité de l'Élan (`keepMovingIfUnseen`)** : Une fois magnétisés dans le champ de vision, les objets conservent leur élan d'attraction même s'ils passent momentanément derrière un obstacle.
* **Option de Ramassage Instantané** : Élargit la boîte de collision native de collecte du joueur pour aspirer instantanément les objets avec une latence de vol nulle.
* **Raccourci Clavier & Commande Joueur** : Basculez l'aimant côté client via un raccourci clavier (`\` sur 26.2, `Ctrl+M` sur 26.1.2) ou côté serveur via `/magnet toggle`.
* **Zéro Encombrement d'Inventaire** : Fonctionnalité 100 % intrinsèque — aucun objet aimant, aucune babiole ni batterie d'énergie requise.

---

## 📚 Navigation Encyclopédique

### 🎮 Guides Joueur & Administrateur
* [[Aperçu MC 26.2|fr_fr-26.2-Home]] & [[Aperçu MC 26.1.2|fr_fr-26.1.2-Home]]
* [[Attraction & Traversée de Phase MC 26.2|fr_fr-26.2-Vacuum-and-Phase-Shifting]] & [[Attraction & Traversée de Phase MC 26.1.2|fr_fr-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Ligne de Visée & Obstacles MC 26.2|fr_fr-26.2-Line-of-Sight-and-Obstruction]] & [[Ligne de Visée & Obstacles MC 26.1.2|fr_fr-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Attraction des Orbes d'Expérience MC 26.2|fr_fr-26.2-Experience-Orb-Attraction]] & [[Attraction des Orbes d'Expérience MC 26.1.2|fr_fr-26.1.2-Experience-Orb-Attraction]]
* [[Mode de Ramassage Instantané MC 26.2|fr_fr-26.2-Instant-Pickup-Mode]] & [[Mode de Ramassage Instantané MC 26.1.2|fr_fr-26.1.2-Instant-Pickup-Mode]]
* [[Bascule Joueur & Persistance MC 26.2|fr_fr-26.2-Player-Toggle-and-Persistence]] & [[Bascule Joueur & État de Session MC 26.1.2|fr_fr-26.1.2-Player-Toggle-and-Persistence]]
* [[Référence des GameRules MC 26.2|fr_fr-26.2-GameRules]] & [[Référence des GameRules MC 26.1.2|fr_fr-26.1.2-GameRules]]
* [[Commandes Brigadier MC 26.2|fr_fr-26.2-Commands]] & [[Commandes Serveur MC 26.1.2|fr_fr-26.1.2-Commands]]
* [[Progrès MC 26.2|fr_fr-26.2-Advancements]] & [[Progrès MC 26.1.2|fr_fr-26.1.2-Advancements]]
* [[Interface de Configuration YACL MC 26.2|fr_fr-26.2-Configuration]] & [[Interface Cloth Config MC 26.1.2|fr_fr-26.1.2-Configuration]]
* [[HUD & Diagnostics MC 26.2|fr_fr-26.2-HUD-and-Diagnostics]] & [[HUD & Overlay MC 26.1.2|fr_fr-26.1.2-HUD-and-Diagnostics]]

### 💻 Documentation Développeur & Contributeur
* [[Configuration & Compilation Loom MC 26.2|fr_fr-26.2-Developer-Setup-and-Building]] & [[Configuration & Compilation Loom MC 26.1.2|fr_fr-26.1.2-Developer-Setup-and-Building]]
* [[Architecture & Mixins MC 26.2|fr_fr-26.2-Architecture-and-Mixins]] & [[Architecture & Mixins MC 26.1.2|fr_fr-26.1.2-Architecture-and-Mixins]]
* [[API & Intégration d'Addons MC 26.2|fr_fr-26.2-API-and-Addon-Integration]] & [[API & Intégration d'Addons MC 26.1.2|fr_fr-26.1.2-API-and-Addon-Integration]]
* [[Matrice des Versions|fr_fr-Version-Compatibility]]

---

## ⚖️ Licence & Attribution

Développé par **Dasik (Rifaditya)** sous licence **GNU General Public License v3.0 (GPLv3)**. Consultez le fichier `LICENSE` pour connaître l'intégralité des termes et autorisations légales.
