# 🧲 Magnet, Let me get that! — Portail Minecraft 26.2

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

Bienvenue sur le portail de documentation **Minecraft 26.2** de **Magnet, Let me get that!** (Version `1.3.9+26.2`).

Cette édition moderne apporte la persistance d'état NBT, la synchronisation de réapparition via le cycle de vie Fabric, le raycasting sphérique à 360° optimisé par DasikLibrary 1.8.23 et une interface de configuration graphique avec YetAnotherConfigLib v3 (YACL).

---

## 📋 Spécifications Rapides de Minecraft 26.2

| Spécification | Valeur Cible | Identifiant de Référence |
| :--- | :--- | :--- |
| **Version Cible de Minecraft** | `26.2` | `"minecraft": ">=26.2-"` |
| **Version Active du Sous-Projet** | `1.3.9+26.2` | `mod_version=1.3.9+26.2` |
| **Chaîne d'Outils Java** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.19.1` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.150.1+26.2` | `fabric_version=0.150.1+26.2` |
| **Bibliothèque Partagée Centrale** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **Touche Client par Défaut** | `\` (Anti-slash) | `GLFW.GLFW_KEY_BACKSLASH` |
| **Interface GUI de Configuration** | YetAnotherConfigLib v3 | `YaclScreenHelper` + ModMenu |
| **Commandes de Diagnostic** | `/magnet toggle`, `/magnet debug`, `/magnet debug log` | `MagnetCommand.java` |

---

## ⚡ Points Forts des Fonctionnalités

* **Vérification de Ligne de Visée Sphérique à 360°** : Empêche la triche à travers les murs solides tout en offrant une attraction omnidirectionnelle sur tous les angles de vue du joueur. Voir [[Ligne de Visée & Obstacles|fr_fr-26.2-Line-of-Sight-and-Obstruction]].
* **Mouvement de Traversée NoClip (Phase-Shift)** : Les objets magnétisés traversent fluidement les blocs pour atteindre le niveau des yeux du joueur sans s'accrocher. Voir [[Attraction & Traversée de Phase|fr_fr-26.2-Vacuum-and-Phase-Shifting]].
* **Ramassage Instantané à Latence Nulle** : L'expansion optionnelle de la boîte englobante aspire instantanément les objets lâchés dans l'inventaire avec un délai physique nul. Voir [[Mode de Ramassage Instantané|fr_fr-26.2-Instant-Pickup-Mode]].
* **État de Joueur Persistant** : Les préférences survivent à la mort, à la réapparition, aux changements de dimension et aux redémarrages serveur via NBT (`ValueOutput`/`ValueInput`) et `ServerPlayerEvents.COPY_FROM`. Voir [[Bascule Joueur & Persistance|fr_fr-26.2-Player-Toggle-and-Persistence]].
* **Suite de Commandes de Diagnostic** : Outils de diagnostic intégrés `/magnet debug` et `/magnet debug log` pour les administrateurs de serveur. Voir [[Commandes Brigadier|fr_fr-26.2-Commands]] et [[HUD & Diagnostics|fr_fr-26.2-HUD-and-Diagnostics]].

---

## 📑 Index de la Documentation 26.2

### 🎮 Gameplay & Administration
* [[Physique d'Attraction & Traversée de Phase|fr_fr-26.2-Vacuum-and-Phase-Shifting]]
* [[Ligne de Visée & Pénétration des Obstacles|fr_fr-26.2-Line-of-Sight-and-Obstruction]]
* [[Synchronisation des Orbes d'Expérience|fr_fr-26.2-Experience-Orb-Attraction]]
* [[Mode de Ramassage Instantané & Boîte AABB|fr_fr-26.2-Instant-Pickup-Mode]]
* [[Bascule Joueur, Persistance & Cycle de Vie|fr_fr-26.2-Player-Toggle-and-Persistence]]
* [[Référence des GameRules & Bornes par Défaut|fr_fr-26.2-GameRules]]
* [[Commandes Brigadier & Diagnostics en Jeu|fr_fr-26.2-Commands]]
* [[Progrès & Respect du Fonctionnement Vanilla|fr_fr-26.2-Advancements]]
* [[Interface de Configuration YACL & ModMenu|fr_fr-26.2-Configuration]]
* [[HUD sur Barre d'Action & Journalisation Dédiée|fr_fr-26.2-HUD-and-Diagnostics]]

### 💻 Référence Développeur & Ingénierie
* [[Configuration Développeur, Chaînes d'Outils & Gradle Loom|fr_fr-26.2-Developer-Setup-and-Building]]
* [[Architecture, Paquets & Injections Mixin|fr_fr-26.2-Architecture-and-Mixins]]
* [[Façades d'API, Interfaces & Crochets pour Addons|fr_fr-26.2-API-and-Addon-Integration]]
* [[Retour à la Matrice des Versions|fr_fr-Version-Compatibility]]
