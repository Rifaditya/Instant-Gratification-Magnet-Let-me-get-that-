<p align="center">
    <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <a href="https://www.curseforge.com/minecraft/mc-mods/dasik-libary"><img src="https://img.shields.io/badge/Requires-Dasik_Library-orange?style=for-the-badge&logo=curseforge" alt="CurseForge: Dasik Library"></a>
    <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

# 🧲 Magnet, Let me get that!

### 🎮 Version Compatibility & Parity

This mod is active and fully supported across both major version streams:
* **Minecraft 26.2+**: Current public release — **`v1.3.4`**
* **Minecraft 26.1.2**: **Discontinued** — **`v1.0.10`**

Both versions are designed to run fully server-side with vanilla client compatibility, though newer performance fixes and thread-safe packets are standardized on the 26.2+ release.

<blockquote><strong>"If you can see it, you should have it."</strong></blockquote>

**Magnet, Let me get that!** is an intrinsic extension of your will. It creates a constant, powerful vacuum that pulls items and XP orbs directly to you. No items to hold, no energy to charge — just pure convenience.

Part of the **Instant Gratification Collection** — mods that respect the player's time.

---

## ✨ Features

### 🌪️ The Vacuum Field
Command the materials around you with surgical precision.

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.53.38.png" width="45%" alt="Items being vacuumed to player (Particles: OFF)">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.53.41.png" width="45%" alt="Items phasing through walls (Particles: OFF)">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.32.18.png" width="45%" alt="Items being vacuumed to player (Particles: ON)">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.32.09.png" width="45%" alt="Items flowing with particle trails (Particles: ON)">
</p>

* **Intrinsic Magnetism**: No need to craft, hold, or equip a special item. The vacuum effect is tied directly to your player.
* **Phase Shifting (NoClip)**: Items don't get stuck on walls or under floors. They phase through terrain to reach your inventory.
* **LOS Awareness**: Magnetism respects Line of Sight to keep exploration challenging.
* **Terminal Velocity**: Items accelerate smoothly but aggressively towards you.
* **XP Sync**: Experience Orbs obey the same physical laws. Leave nothing behind.

### 🎯 Individual Player Toggles
You have full control over your personal vacuum field.
* **Dedicated Hotkey**: Press **`\` (backslash)** to instantly toggle your magnet on or off in-game. Fully rebindable via the standard Minecraft Controls menu.
* **Chat Command**: You can also use the `/magnet toggle` command to switch your status.
* **Save State Persistence**: Your preference is saved directly to your player NBT save file (`level.dat`). It persists through deaths, dimension travel (Nether/End), and server restarts.
* **Login Auto-Sync**: The server automatically synchronizes your toggle status to your client upon joining, keeping HUD overlays in perfect sync.

### ⚙️ Pure GameRules Control
Configure every aspect of the vacuum field via the vanilla **Edit Game Rules** screen or commands.

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.27.56.png" alt="Minecraft Game Rules screen showing the Magnet, Let me get that! category">
</p>

* **Radius Control**: Adjust the pull distance (Default: 12 blocks).
* **Instant Warp Mode**: Toggle between smooth flight or instant teleportation to inventory.
* **Visual Trails**: Optional cosmetic particles to track magnetized items.
* **Occlusion Settings**: Configure whether the magnet is blocked by transparent blocks, flora, or interactive furniture.

---

## ⚙️ Configuration (Native Game Rules)


<blockquote class="warning">
<strong>âš ï¸ Important: Config vs. In-Game GameRules</strong><br>
The global configuration file only defines <strong>default values for new worlds</strong> at creation time.
If you have <strong>already created/opened a world</strong>, changing the config file will have no effect. You must change the settings in-game using the <strong>Edit Game Rules</strong> UI screen or the <code>/gamerule</code> command.
</blockquote>
No messy config files. The mod uses the **Native Minecraft Game Rules** system. All mod parameters are grouped into a dedicated **"Magnet"** category in the official UI.

### 📋 Quick Commands
```sql
/gamerule ig:magnet_range 24       → Double the range
/gamerule ig:magnet_instant true   → Warp items directly to inventory
/gamerule ig:magnet_los_only true  → Require line-of-sight
/gamerule ig:magnet_affects_xp false → Disable XP magnetism
```

---

## 💻 Server-Side Optional & Client Gating
* **Server-Side Optional**: "Magnet, Let me get that!" is fully compatible with vanilla clients! Vanilla clients can connect to servers running the mod without installing it.
* **Dedicated Server Support**: Client-only rendering logic is isolated to prevent classloading crashes on headless dedicated servers.
* **Performance Hardened**: Scan algorithms are highly optimized. Heavy spatial queries are cached to ensure zero TPS impact.

---

## ☕ Support

If you enjoy the **Instant Gratification** collection, consider fueling the next update!

<a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Ko-fi"></a>
<a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
<a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>

<blockquote>
<strong>Indonesian Users:</strong> SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!
</blockquote>

---

## 📦 Modpack Permissions

<blockquote>
<strong>Modpack Distribution Policy:</strong>
Since this mod is open-source (GPLv3), you are completely free to include it in any modpack! If you want to support my work and help fund future updates, downloading it directly through the official platform page (CurseForge) is highly appreciated.
</blockquote>

---

## 📜 Credits

| Role | Author |
| :--- | :--- |
| **Creator** | **Dasik** (Rifaditya) |
| **Collection** | Instant Gratification |
| **License** | GPLv3 |

---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Instant Gratification Collection*

</div>
