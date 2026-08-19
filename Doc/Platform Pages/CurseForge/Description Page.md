<p align="center">
    <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <a href="https://www.curseforge.com/minecraft/mc-mods/dasik-libary"><img src="https://img.shields.io/badge/Requires-Dasik_Library-orange?style=for-the-badge&logo=curseforge" alt="CurseForge: Dasik Library"></a>
    <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

<h1>🧲 Magnet, Let me get that!</h1>

<blockquote><strong>"If you can see it, you should have it."</strong></blockquote>

<p><strong>Magnet, Let me get that!</strong> is an intrinsic extension of your will. It creates a constant, powerful vacuum that pulls items and XP orbs directly to you. No items to hold, no energy to charge — just pure convenience.</p>

<p>Part of the <strong>Instant Gratification Collection</strong> — mods that respect the player's time.</p>

<hr>

<h2>✨ Features</h2>

<h3>🌪️ The Vacuum Field</h3>
<p>Command the materials around you with surgical precision.</p>

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.53.38.png" width="45%" alt="Items being vacuumed to player (Particles: OFF)">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.53.41.png" width="45%" alt="Items phasing through walls (Particles: OFF)">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.32.18.png" width="45%" alt="Items being vacuumed to player (Particles: ON)">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.32.09.png" width="45%" alt="Items flowing with particle trails (Particles: ON)">
</p>

<ul>
  <li><strong>Intrinsic Magnetism</strong>: No need to craft, hold, or equip a special item. The vacuum effect is tied directly to your player.</li>
  <li><strong>Phase Shifting (NoClip)</strong>: Items don't get stuck on walls or under floors. They phase through terrain to reach your inventory.</li>
  <li><strong>LOS Awareness</strong>: Magnetism respects Line of Sight to keep exploration challenging.</li>
  <li><strong>Terminal Velocity</strong>: Items accelerate smoothly but aggressively towards you.</li>
  <li><strong>XP Sync</strong>: Experience Orbs obey the same physical laws. Leave nothing behind.</li>
</ul>

<h3>🎯 Individual Player Toggles</h3>
<p>You have full control over your personal vacuum field.</p>
<ul>
  <li><strong>Dedicated Hotkey</strong>: Press <strong><code>\</code> (backslash)</strong> to instantly toggle your magnet on or off in-game. Fully rebindable via the standard Minecraft Controls menu.</li>
  <li><strong>Chat Command</strong>: You can also use the <code>/magnet toggle</code> or <code>/ig_magnet toggle</code> command to switch your status.</li>
  <li><strong>Save State Persistence</strong>: Your preference is saved directly to your player NBT save file (<code>level.dat</code>). It persists through deaths, dimension travel (Nether/End), and server restarts.</li>
  <li><strong>Login Auto-Sync</strong>: The server automatically synchronizes your toggle status to your client upon joining, keeping HUD overlays in perfect sync.</li>
</ul>

<h3>⚙️ Pure GameRules Control</h3>
<p>Configure every aspect of the vacuum field via the vanilla <strong>Edit Game Rules</strong> screen or commands.</p>

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.27.56.png" alt="Minecraft Game Rules screen showing the Magnet category">
</p>

<ul>
  <li><strong>Radius Control</strong>: Adjust the pull distance (Default: 12 blocks).</li>
  <li><strong>Instant Warp Mode</strong>: Toggle between smooth flight or instant teleportation to inventory.</li>
  <li><strong>Visual Trails & Source Throttling</strong>: Optional cosmetic particles to track magnetized items, capped per-tick to eliminate lag on high-item drops.</li>
  <li><strong>Occlusion Settings</strong>: Configure whether the magnet is blocked by transparent blocks, flora, or interactive furniture.</li>
</ul>

<hr>

<h2>⚙️ Configuration (Native Game Rules)</h2>

<blockquote>
<strong>⚠️ Important: Config vs. In-Game GameRules</strong><br>
The global configuration file (<code>ig_magnet.json</code>) only defines <strong>default values for new worlds</strong> at creation time.<br>
If you have <strong>already created/opened a world</strong>, changing the config file will have no effect. You must change the settings in-game using the <strong>Edit Game Rules</strong> UI screen or the <code>/gamerule</code> command.
</blockquote>

<p>No messy config files. The mod uses the <strong>Native Minecraft Game Rules</strong> system. All mod parameters are grouped into a dedicated <strong>"Magnet"</strong> category in the official UI.</p>

<h3>📋 Quick Commands</h3>
<pre><code>/gamerule ig:magnet_range 24         → Double the range
/gamerule ig:magnet_instant true     → Warp items directly to inventory
/gamerule ig:magnet_los_only true    → Require line-of-sight
/gamerule ig:magnet_affects_xp false → Disable XP magnetism
/magnet toggle                       → Toggle personal magnet field (or press '\')
/magnet debug                        → View player UUID, GameRules, and LOS check details
/magnet debug log                    → Toggle persistent log files in logs/ig_magnet_debug.log</code></pre>

<hr>

<h2>💻 Server-Side Optional & Client Gating</h2>
<ul>
  <li><strong>Server-Side Optional</strong>: "Magnet, Let me get that!" is fully compatible with vanilla clients! Vanilla clients can connect to servers running the mod without installing it.</li>
  <li><strong>Dedicated Server Support</strong>: Client-only rendering logic is isolated to prevent classloading crashes on headless dedicated servers.</li>
  <li><strong>Performance Hardened</strong>: Scan algorithms are highly optimized. Heavy spatial queries are cached to ensure zero TPS impact.</li>
</ul>

<hr>

<h2>☕ Support</h2>

<p>If you enjoy the <strong>Instant Gratification</strong> collection, consider fueling the next update!</p>

<p>
<a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Ko-fi"></a>
<a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
<a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

<blockquote>
<strong>Indonesian Users:</strong> SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!
</blockquote>

<hr>

<blockquote>
    <strong>📦 Modpack Permissions & Distribution:</strong><br>
    You are free to include this mod in any modpack on any platform. However, the mod itself must be downloaded from its official distribution pages on <strong>Modrinth</strong> or <strong>CurseForge</strong>. Re-uploading or redistributing the mod jar file to third-party sites is strictly prohibited unless explicitly permitted by the creator.
    <br><br>
    <strong>License & Forks:</strong><br>
    Since the source code is licensed under <strong>GNU GPLv3</strong>, you are fully permitted to fork the repository, make modifications, build your own versions, and distribute them under the terms of the GPLv3. The prohibition on third-party redistribution applies exclusively to the official compiled releases/jars published by the original creator (Dasik/Rifaditya). Forks must be published as distinct projects, not direct re-uploads of official builds.
</blockquote>

<hr>

<h2>📜 Credits</h2>

<table>
  <thead>
    <tr>
      <th align="left">Role</th>
      <th align="left">Author</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Creator</strong></td>
      <td><strong>Dasik</strong> (Rifaditya)</td>
    </tr>
    <tr>
      <td><strong>Collection</strong></td>
      <td>Instant Gratification</td>
    </tr>
    <tr>
      <td><strong>License</strong></td>
      <td>GPLv3</td>
    </tr>
  </tbody>
</table>

<hr>

<div align="center">

<p><strong>Made with ❤️ for the Minecraft community</strong></p>

<p><em>Part of the Instant Gratification Collection</em></p>

</div>
