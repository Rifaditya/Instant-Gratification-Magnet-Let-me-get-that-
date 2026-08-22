# 🏆 Advancements & Progression Scope (MC 26.1.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Progression Infobox | Details |
| :--- | :--- |
| **Custom Advancements JSONs** | `None (Vanilla Reliance by Design)` |
| **Design Track** | Instant Gratification (IG) |
| **Advancement Triggers** | 100% Native Vanilla `Player.touch(ItemEntity)` |
| **Gating Requirement** | None (Zero arbitrary unlocking hurdles) |

---

## 📖 Absence Policy & Design Scope

In strict adherence to the **Instant Gratification (IG)** modding philosophy, **Magnet, Let me get that!** intentionally contains **no custom advancement trees or milestone achievements**.

The mod is engineered as an intrinsic quality-of-life augmentation to the player's core survival interactions. Features are unlocked immediately from the moment a player enters the world, with zero artificial quest grinding, research trees, or progression roadblocks.

```
+-----------------------------------------------------------------------------------+
|                        INSTANT GRATIFICATION DESIGN TENET                         |
|                                                                                   |
|  "The 'Walk of Shame' (walking 5 blocks to pick up a block you just mined) is    |
|   a cardinal sin of flow state. IG Magnet is not a tech tree item or a reward;    |
|   it is an extension of the player's will. If you can see it, you should have it."|
+-----------------------------------------------------------------------------------+
```

---

## 🎮 Native Vanilla Advancement Compatibility

Because both standard vacuum flight and [[Instant Pickup Mode|Instant-Pickup-Mode]] utilize Minecraft's native `ItemEntity.playerTouch()` and `ExperienceOrb.playerTouch()` collection pipelines:

1. **Vanilla Milestone Triggers**: Picking up diamonds, ancient debris, or blaze rods via the magnet immediately fires vanilla advancement criteria.
2. **Third-Party Questing Compatibility**: Quest mods that track item pickups in player inventories work out of the box with zero specialized compatibility bridges.
3. **Statistic Tracking**: Vanilla statistics (`stat.pickup.minecraft.*`) continue to increment accurately.

---

## 🔗 Related Wiki Documentation
* [[Instant Pickup Mode|Instant-Pickup-Mode]]
* [[GameRules Complete Reference|GameRules]]
* [[Return to Home Portal|Home]]
