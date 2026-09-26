# 📊 HUD, Visuels & Overlay (MC 26.1.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Visuel | Paramètres Techniques |
| :--- | :--- |
| **API Barre d'Action** | `client.gui.setOverlayMessage(Component, boolean)` |
| **Type de Particule** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Modulo de Régulation des Particules** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **Plafond de Sources de Particules** | `ig:magnet_max_particle_sources` (Défaut : `5`) |

---

## 🖥️ Retour Visuel sur la Barre d'Action (Actionbar)

Lorsque le joueur bascule son aimant d'objets à l'aide de la combinaison de touches `Ctrl+M`, le GUI client affiche immédiatement une notification sur la barre d'action :

* **Activé** : `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Désactivé** : `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Vérifié d'après : Gui.java (26.1.2)
if (newState) {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Traînées Visuelles d'Étincelles Électriques

Les objets et orbes d'expérience en cours d'attraction émettent des traînées d'étincelles :

```
[Objet / XP Attiré]  --->  ✨  --->  ✨  --->  ✨  --->  [Yeux du Joueur]
```

* **Régulation de Sources** : Au maximum 5 sources simultanées émettent des particules (`ig:magnet_max_particle_sources = 5`).
* **Régulation Temporelle** : Les particules apparaissent tous les 4 ticks ($5\text{ fois/seconde}$).
* **Contrôle de Densité** : `ig:magnet_particle_count = 1`.

---

## 🔗 Documentation Connexe
* [[Bascule Joueur & État de Session|fr_fr-26.1.2-Player-Toggle-and-Persistence]]
* [[Attraction des Orbes d'Expérience|fr_fr-26.1.2-Experience-Orb-Attraction]]
* [[Retour au Portail MC 26.1.2|fr_fr-26.1.2-Home]]
