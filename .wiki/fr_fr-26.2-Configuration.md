# 🎨 Interface de Configuration YACL & ModMenu (MC 26.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré de Configuration | Détails |
| :--- | :--- |
| **Chemin du Fichier de Config** | `config/ig_magnet.json` |
| **Bibliothèque GUI** | YetAnotherConfigLib v3 (`dev.isxander:yet-another-config-lib`) |
| **Point d'Entrée ModMenu** | `net.instantgratification.magnet.config.ModMenuIntegration` |
| **Assistant d'Écran GUI** | `net.instantgratification.magnet.config.YaclScreenHelper` |
| **Sécurité du Chargement de Classes** | Isolé via `GuiHelper.getOptionalFactory` |

---

## 📖 Architecture du Système de Configuration

**Magnet, Let me get that!** intègre une interface graphique de configuration côté client propulsée par **YetAnotherConfigLib v3 (YACL)** et accessible via **ModMenu**.

Afin de garantir que les serveurs dédiés ne plantent jamais lors du chargement des classes d'interface graphique client, la fabrique d'écran est instanciée via un **chargement de classes isolé et sécurisé par réflexion** :

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
                "ig_magnet",
                "net.instantgratification.magnet.config.YaclScreenHelper",
                "createScreen"
        );
    }
}
```

---

## ⚠️ Avertissement sur la Préséance de Configuration

> ⚠️ **Avis Important** :  
> Les modifications effectuées dans l'interface ModMenu ou dans `config/ig_magnet.json` **n'affectent que les valeurs de référence par défaut des NOUVEAUX mondes**.  
> Pour modifier les paramètres d'un monde déjà créé et actif, utilisez la [[Référence des GameRules|fr_fr-26.2-GameRules]] en jeu via `/gamerule` ou l'écran d'édition des règles de jeu.

---

## 🗂️ Catégories & Options de Configuration

```
Écran de Configuration YACL ("Magnet, Let me get that! Configuration")
  ├── Paramètres Généraux
  │     ├── Aimant Activé (Défaut: true)
  │     ├── Portée de l'Aimant (Défaut: 12, Plage: 1..64)
  │     ├── Ramassage Instantané (Défaut: false)
  │     └── Traversée NoClip (Défaut: true)
  ├── Vitesses & Heuristiques d'Attraction
  │     ├── Vitesse des Objets (Défaut: 80%, Plage: 1..1000)
  │     └── Accélération des Objets (Défaut: 10%, Plage: 1..1000)
  ├── Ligne de Visée (LOS)
  │     ├── Ligne de Visée Uniquement (Défaut: true)
  │     ├── Conserver l'Élan Hors Vue (Défaut: true)
  │     ├── Bloqué par les Transparents (Défaut: false)
  │     ├── Bloqué par la Flore (Défaut: false)
  │     └── Bloqué par Entités de Bloc (Défaut: false)
  └── Visuels & Performances
        ├── Attirer les Orbes d'XP (Défaut: true)
        ├── Particules de l'Aimant (Défaut: true)
        ├── Nombre de Particules (Défaut: 1, Plage: 0..100)
        └── Sources Max de Particules (Défaut: 5, Plage: 0..100)
```

---

## 📄 Structure JSON Brute (`config/ig_magnet.json`)

```json
{
  "configVersion": 1,
  "enabled": true,
  "range": 12,
  "noClip": true,
  "affectsXp": true,
  "particles": true,
  "particleCount": 1,
  "maxParticleSources": 5,
  "speed": 80,
  "acceleration": 10,
  "instant": false,
  "losOnly": true,
  "keepMovingIfUnseen": true,
  "blockedByTransparent": false,
  "blockedByFlora": false,
  "blockedByBlockEntities": false
}
```

---

## 🔗 Documentation Connexe
* [[Référence Complète des GameRules|fr_fr-26.2-GameRules]]
* [[Configuration Développeur & Compilation|fr_fr-26.2-Developer-Setup-and-Building]]
* [[Retour au Portail MC 26.2|fr_fr-26.2-Home]]
