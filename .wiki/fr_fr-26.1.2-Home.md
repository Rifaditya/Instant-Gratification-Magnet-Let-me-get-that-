# 🧲 Magnet, Let me get that! — Portail Minecraft 26.1.2

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

Bienvenue sur le portail de documentation **Minecraft 26.1.2** de **Magnet, Let me get that!** (Version `1.1.2+26.1.2`).

Cette édition point d'ancrage offre l'expérience complète d'aspiration instantanée d'objets et d'XP selon la philosophie Instant Gratification, avec intégration de Cloth Config, gestion d'état de session simultanée et raycasting sphérique à 360°.

---

## 📋 Spécifications Rapides de Minecraft 26.1.2

| Spécification | Valeur Cible | Identifiant de Référence |
| :--- | :--- | :--- |
| **Version Cible de Minecraft** | `26.1.2` | `"minecraft": "*"` |
| **Version Active du Sous-Projet** | `1.1.2+26.1.2` | `mod_version=1.1.2+26.1.2` |
| **Chaîne d'Outils Java** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.16.10` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` | `fabric_version=0.145.4+26.1.2` |
| **Bibliothèque Partagée Centrale** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **Touche Client par Défaut** | `Ctrl+M` | `GLFW.GLFW_KEY_M` + `isControlDown()` |
| **Interface GUI de Configuration** | Cloth Config Fabric | `ClothConfigScreenHelper` + ModMenu |
| **Commandes Serveur** | `/magnet toggle`, `/ig_magnet toggle` | `MagnetCommand.java` |

---

## ⚡ Points Forts des Fonctionnalités

* **Vérification de Ligne de Visée Sphérique à 360°** : Propulsée par `PlayerVisionTracker` de DasikLibrary 1.8.23. Voir [[Ligne de Visée & Obstacles|fr_fr-26.1.2-Line-of-Sight-and-Obstruction]].
* **Physique de Traversée NoClip** : Les objets traversent le terrain solide lors de leur attraction. Voir [[Attraction & Traversée de Phase|fr_fr-26.1.2-Vacuum-and-Phase-Shifting]].
* **Mode de Ramassage Instantané** : Expansion optionnelle de la boîte de collision pour une collecte à latence 0. Voir [[Mode de Ramassage Instantané|fr_fr-26.1.2-Instant-Pickup-Mode]].
* **Bascule de Session Simultanée** : État des touches et des commandes géré via `MagnetPlayerState`. Voir [[Bascule Joueur & État de Session|fr_fr-26.1.2-Player-Toggle-and-Persistence]].
* **Interface Cloth Config** : Écran de configuration soigné en jeu avec avertissements de catégories. Voir [[Interface Cloth Config|fr_fr-26.1.2-Configuration]].

---

## 📑 Index de la Documentation 26.1.2

### 🎮 Gameplay & Administration
* [[Physique d'Attraction & Traversée de Phase|fr_fr-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Ligne de Visée & Pénétration des Obstacles|fr_fr-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Synchronisation des Orbes d'Expérience|fr_fr-26.1.2-Experience-Orb-Attraction]]
* [[Mode de Ramassage Instantané & Expansion AABB|fr_fr-26.1.2-Instant-Pickup-Mode]]
* [[Bascule Joueur, Raccourci & Stockage d'État|fr_fr-26.1.2-Player-Toggle-and-Persistence]]
* [[Référence des GameRules & Bornes par Défaut|fr_fr-26.1.2-GameRules]]
* [[Commandes Serveur & Prise en Charge Client Vanilla|fr_fr-26.1.2-Commands]]
* [[Progrès & Respect du Fonctionnement Vanilla|fr_fr-26.1.2-Advancements]]
* [[Interface Cloth Config & ModMenu|fr_fr-26.1.2-Configuration]]
* [[HUD sur Barre d'Action & Effets Visuels|fr_fr-26.1.2-HUD-and-Diagnostics]]

### 💻 Référence Développeur & Ingénierie
* [[Configuration Développeur, Chaînes d'Outils & Gradle Loom|fr_fr-26.1.2-Developer-Setup-and-Building]]
* [[Architecture, Paquets & Injections Mixin|fr_fr-26.1.2-Architecture-and-Mixins]]
* [[Façades d'API, Interfaces & Crochets pour Addons|fr_fr-26.1.2-API-and-Addon-Integration]]
* [[Retour à la Matrice des Versions|fr_fr-Version-Compatibility]]
