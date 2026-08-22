# ⚙️ GameRules Complete Reference (MC 26.1.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Category Infobox | Details |
| :--- | :--- |
| **Category ID** | `magnet:magnet_category` |
| **Localized Title** | `Magnet, Let me get that!` (`gamerule.category.magnet.magnet_category`) |
| **Registered Manager** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **Registry Class** | `net.instantgratification.magnet.registry.ModGameRules` |
| **Total Rules Registered** | `15` Namespaced Rules |

---

## 📖 In-Game GameRules Administration

All global mechanics of **Magnet, Let me get that!** in Minecraft 26.1.2 are controlled through namespaced GameRules registered under the `magnet:magnet_category` header.

```
/gamerule ig:magnet_enabled true
/gamerule ig:magnet_range 12
/gamerule ig:magnet_speed 80
/gamerule ig:magnet_instant false
```

---

## 📋 Complete GameRules Reference Table

| GameRule Identifier | Type | Default | Bounds | Localized Display Name | Description & Gameplay Effect |
| :--- | :---: | :---: | :---: | :--- | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | **Magnet Enabled** | Master toggle that globally activates or disables the item vacuum system. |
| `ig:magnet_range` | Integer | `12` | `1..64` | **Magnet Range** | The spherical block radius from which the player attracts dropped items. |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | **Magnet Noclip** | Enables phase shifting, allowing attracted items to pass freely through solid blocks. |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | **Attract XP Orbs** | Whether experience orbs are attracted alongside dropped items. |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | **Magnet Particles** | Spawns electric spark particles along the trajectory of pulled entities. |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | **Particle Count** | The number of particle sparks emitted per active source per particle tick. |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | **Max Particle Sources** | Maximum number of simultaneous entities allowed to emit particles to prevent FPS lag. |
| `ig:magnet_speed` | Integer | `80` | `1..1000` | **Item Speed** | Terminal velocity percentage ($80 = 0.8\text{ blocks/tick} = 16.0\text{ m/s}$). |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000` | **Item Acceleration** | Acceleration interpolation factor per tick ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_instant` | Boolean | `false` | `true/false` | **Instant Pickup** | When true, items instantly teleport into inventory via AABB box expansion with 0 flight time. |
| `ig:magnet_los_only` | Boolean | `true` | `true/false` | **Line of Sight Only** | Enforces line-of-sight visibility; prevents pulling items behind impenetrable barriers. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | `true/false` | **Keep Moving if Unseen** | Allows items magnetized in LOS to retain pull momentum if sight is broken mid-flight. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | `true/false` | **Blocked by Transparent** | If true, glass, glass panes, iron bars, and translucent blocks block line of sight. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | `true/false` | **Blocked by Flora** | If true, tall grass, crops, flowers, and leaves block line of sight. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | `true/false` | **Blocked by Block Entities** | If true, chests, beds, barrels, and shulker boxes block line of sight. |

---

## 🔗 Related Wiki Documentation
* [[Vacuum & Phase Shifting Physics|Vacuum-and-Phase-Shifting]]
* [[Cloth Config GUI & Defaults|Configuration]]
* [[Return to Home Portal|Home]]
