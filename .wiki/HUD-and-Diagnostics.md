# 📊 HUD, Visuals & Overlay (MC 26.1.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Visual Infobox | Technical Parameters |
| :--- | :--- |
| **Actionbar API** | `client.gui.setOverlayMessage(Component, boolean)` |
| **Particle Type** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Particle Throttle Modulo** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **Particle Source Cap** | `ig:magnet_max_particle_sources` (Default: `5`) |

---

## 🖥️ Actionbar Overlay Feedback

When the player toggles their item magnet using the `Ctrl+M` hotkey combination, the client GUI immediately displays an actionbar notification:

* **Enabled**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Disabled**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Verified against: Gui.java (26.1.2)
if (newState) {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Electric Spark Visual Trails

Items and experience orbs being pulled emit spark particle trails:

```
[Pulled Item / XP]  --->  ✨  --->  ✨  --->  ✨  --->  [Player Eye Position]
```

* **Source Gating**: At most 5 simultaneous sources emit particles (`ig:magnet_max_particle_sources = 5`).
* **Tick Gating**: Particles spawn every 4 ticks ($5\text{ times/second}$).
* **Density Control**: `ig:magnet_particle_count = 1`.

---

## 🔗 Related Wiki Documentation
* [[Player Toggle & State Management|Player-Toggle-and-Persistence]]
* [[Experience Orb Attraction|Experience-Orb-Attraction]]
* [[Return to Home Portal|Home]]
