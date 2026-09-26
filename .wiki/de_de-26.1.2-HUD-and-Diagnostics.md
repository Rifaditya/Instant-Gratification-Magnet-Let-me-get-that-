# 📊 HUD, Visuals & Overlay (MC 26.1.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Visuelle Infobox | Technische Parameter |
| :--- | :--- |
| **Actionbar-API** | `client.gui.setOverlayMessage(Component, boolean)` |
| **Partikeltyp** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Partikeldrosselungs-Modulo** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **Partikelquellen-Obergrenze** | `ig:magnet_max_particle_sources` (Standard: `5`) |

---

## 🖥️ Actionbar-Overlay-Feedback

Wenn der Spieler seinen Item-Magneten mit der Tastenkombination `Strg+M` umschaltet, zeigt das Client-GUI sofort eine Benachrichtigung in der Actionbar an:

* **Aktiviert**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Deaktiviert**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Verifiziert gegen: Gui.java (26.1.2)
if (newState) {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Elektrische Funken-Partikelspuren

Gezogene Gegenstände und Erfahrungskugeln senden Funken-Partikelspuren aus:

```
[Gezogenes Item / XP]  --->  ✨  --->  ✨  --->  ✨  --->  [Spieler-Augenposition]
```

* **Quellen-Gating**: Maximal 5 Quellen erzeugen gleichzeitig Partikel (`ig:magnet_max_particle_sources = 5`).
* **Tick-Gating**: Partikel spawnen alle 4 Ticks ($5\text{ Mal/Sekunde}$).
* **Dichte-Steuerung**: `ig:magnet_particle_count = 1`.

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Spieler-Umschaltung & Sitzungsstatus|de_de-26.1.2-Player-Toggle-and-Persistence]]
* [[Erfahrungs-Orb-Anziehung|de_de-26.1.2-Experience-Orb-Attraction]]
* [[Zurück zum MC 26.1.2 Portal|de_de-26.1.2-Home]]
