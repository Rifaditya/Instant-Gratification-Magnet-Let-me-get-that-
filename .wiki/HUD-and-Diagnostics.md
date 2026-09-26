# 📊 HUD, Visuals & Diagnostics (MC 26.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Visual Infobox | Technical Parameters |
| :--- | :--- |
| **Actionbar API** | `client.gui.hud.setOverlayMessage(Component, boolean)` |
| **Particle Type** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Particle Throttle Modulo** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **Debug Log File** | `logs/ig_magnet_debug.log` |
| **Logger Class** | `net.instantgratification.magnet.MagnetDebugLogger` |

---

## 🖥️ Actionbar Overlay Feedback

When the player toggles their magnet state via keybind (`\`) or server command (`/magnet toggle`), the client HUD immediately renders an actionbar overlay message directly above the hotbar:

* **Enabled Message**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Disabled Message**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Verified against: Hud.java (26.2+)
if (newState) {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Electric Spark Visual Trails

While items and XP orbs are actively being magnetized in flight, they emit subtle `ParticleTypes.ELECTRIC_SPARK` visual trails:

```
[Pulled Item]  --->  ✨  --->  ✨  --->  ✨  --->  [Player Eye Position]
```

### Particle Throttling Rules:
1. **Source Cap**: Governed by `ig:magnet_max_particle_sources` (default: `5`).
2. **Frequency Staggering**: Only 1 in every 4 ticks spawns particles for any individual entity: `(entity.tickCount + entity.getId()) % 4 == 0`.
3. **Count Control**: Controlled per source by `ig:magnet_particle_count` (default: `1`).

---

## 📝 Dedicated Debug File Logger (`MagnetDebugLogger`)

* **Activation**: Run `/magnet debug log` in-game.
* **Output File**: `logs/ig_magnet_debug.log`
* **Log Sample**:
  ```text
  [2026-08-22 21:50:00.124] MagnetMod: Server received packet from Alex (e7b1a234-...) enabled=true
  [2026-08-22 21:50:00.130] MagnetMovement: Entity 592 is now magnetized by Alex (e7b1a234-...).
  [2026-08-22 21:50:01.450] PlayerMixin: Server tick for Alex (e7b1a234-...) isMagnetEnabled=true
  ```

---

## 🔗 Related Wiki Documentation
* [[Brigadier Commands & In-Game Diagnostics|Commands]]
* [[Experience Orb Attraction|Experience-Orb-Attraction]]
* [[Return to Home Portal|Home]]
