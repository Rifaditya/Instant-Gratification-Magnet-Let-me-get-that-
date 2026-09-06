<p align="center">
  <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&amp;logo=fabric" alt="Requires Fabric API"></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/dasik-libary"><img src="https://img.shields.io/badge/Requires-Dasik_Library-8A2BE2?style=for-the-badge" alt="Requires Dasik Library"></a>
  <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&amp;logo=java" alt="Java 25">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License GPLv3">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

<h2>🧲 Magnet (Let Me Get That!)</h2>

<blockquote><p><strong>&ldquo;If You Can See It, You Should Have It. Pure Magnetic Suction at Your Command.&rdquo;</strong></p></blockquote>

<blockquote><p><strong>1 Jar 1 Version Policy:</strong> I build <strong>1 dedicated JAR for each Minecraft version</strong> (e.g. MC 26.2, MC 26.3). Please download the exact build that matches your Minecraft installation.<br><br><strong>Dependency Requirement:</strong> For modern Minecraft 26.x releases (26.1.2, 26.2, 26.3+), this mod requires both <strong>Fabric API</strong> and <strong>Dasik Library</strong> (<code>v1.7.4+</code>).</p></blockquote>

<p>Tired of watching your mined diamonds tumble down a dark ravine into lava? Tired of awkwardly swimming through water streams, hopping over fences, or digging through dirt piles just to retrieve scattered saplings and mob drops?</p>

<p><strong>Magnet (Let Me Get That!)</strong> gives your player intrinsic magnetic attraction. It creates a seamless, configurable vacuum field that draws ground items and experience orbs straight to you. No bulky magnetic trinkets to equip, no battery power to charge, and no artificial inventory restrictions&mdash;just pure, effortless convenience.</p>

<p>Part of the <strong>Instant Gratification Collection</strong> &mdash; mods that respect the player's time.</p>

<hr>

<h2>✨ Features</h2>

<h3>🌪️ The Intrinsic Vacuum Field</h3>
<p>Command the materials around you with surgical precision:</p>

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.53.38.png" alt="Items being vacuumed to player" width="85%"><br>
  <em>Smooth aerodynamic suction drawing dropped items directly toward player</em><br><br>
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.53.41.png" alt="Items phasing through solid obstacles" width="85%"><br>
  <em>Noclip mode enabled: items effortlessly phase through solid blocks and walls to reach you</em>
</p>

<ul>
  <li><strong>No Equipment Needed:</strong> Magnetism is an innate player capability. Keep your armor and off-hand free for shields, torches, and weapons.</li>
  <li><strong>Phase Shifting (NoClip):</strong> Items never get stuck behind trees, under stairs, or beneath ceilings. With noclip enabled, they glide directly through solid terrain straight into your inventory.</li>
  <li><strong>Line of Sight (LOS) Physics:</strong> By default, magnetism respects Line of Sight. Items behind thick dungeon walls remain undisturbed until you explore and see them.</li>
  <li><strong>Smooth Terminal Velocity &amp; Acceleration:</strong> Items don't snap awkwardly&mdash;they accelerate along smooth curved parabolic trajectories towards your player.</li>
  <li><strong>Experience Orb Vacuum:</strong> Experience orbs obey the exact same magnetic pull, ensuring zero lost XP after boss battles and farm harvests.</li>
</ul>

<h3>🎮 Personalized Player Toggles &amp; Hotkeys</h3>
<p>Full autonomy over your personal magnetic field:</p>
<ul>
  <li><strong>Dedicated Keybinding:</strong> Press <strong><code>\</code> (backslash)</strong> or rebind to <strong><code>M</code></strong> (or any key of your choice) in the standard Minecraft Controls menu to toggle suction on or off in an instant.</li>
  <li><strong>Chat Commands:</strong> Toggle your personal field anytime with <code>/magnet toggle</code> or <code>/ig_magnet toggle</code>.</li>
  <li><strong>NBT Save State Persistence:</strong> Your personal toggle state is saved directly into player data (<code>level.dat</code>). It persists through player deaths, dimension transitions (Nether, End), and server reboots.</li>
  <li><strong>Action Bar Feedback:</strong> Toggling triggers a clean HUD overlay notification confirming whether your personal magnet is active.</li>
</ul>

<h3>⚡ Instant Teleport Pickup Mode</h3>
<p>Prefer zero delay? Enable <code>ig:magnet_instant true</code> to make items instantly teleport into your inventory the microsecond they enter your magnetic radius, completely bypassing travel time.</p>

<h3>🎨 Cosmetic Particle Trails &amp; Lag Guard</h3>
<p>Customizable particle cues trace the flight path of magnetized loot:</p>

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.32.18.png" alt="Cosmetic particle trails on magnetized items" width="85%"><br>
  <em>Luminescent particle trails follow attracted loot with per-tick source throttling</em>
</p>

<ul>
  <li><strong>Source Throttling:</strong> The <code>ig:magnet_max_particle_sources</code> GameRule limits how many entities emit particles simultaneously, preventing visual clutter and GPU lag during massive quarry drops.</li>
</ul>

<h3>🔍 Advanced Line of Sight (LOS) Fine-Tuning</h3>
<p>Take granular control over what blocks your view:</p>
<ul>
  <li><strong>Transparent Blocks:</strong> Choose whether glass, tinted glass, and water block suction (<code>ig:magnet_blocked_by_transparent</code>).</li>
  <li><strong>Flora &amp; Foliage:</strong> Configure whether tall grass, flowers, and leaves obstruct attraction (<code>ig:magnet_blocked_by_flora</code>).</li>
  <li><strong>Block Entities:</strong> Toggle whether chests, barrels, and shulker boxes block line of sight (<code>ig:magnet_blocked_by_block_entities</code>).</li>
  <li><strong>Momentum Persistence:</strong> If line of sight is broken mid-flight, items keep moving along their current momentum (<code>ig:magnet_keep_moving_if_unseen</code>).</li>
</ul>

<h3>🧩 Compatibility &amp; HUD Integration</h3>
<ul>
  <li><strong>Server-Side Native:</strong> 100% compatible with vanilla clients! Vanilla players can join dedicated servers running the mod without needing to install anything on their PC.</li>
  <li><strong>Item Clumps Synergy:</strong> Automatically detected by Item Clumps to prevent item merging from interrupting active suction trajectories.</li>
  <li><strong>YetAnotherConfigLib (YACL) &amp; ModMenu:</strong> Optional in-game settings screen in singleplayer.</li>
</ul>

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/main/images/2026-02-16_11.27.56.png" alt="Native Minecraft Game Rules Screen for Magnet" width="85%"><br>
  <em>Native in-game Edit Game Rules screen showing the complete Magnet category</em>
</p>

<hr>

<h2>📊 Quick Reference &amp; Mechanics Matrix</h2>

<table>
  <thead>
    <tr>
      <th>Setting / Mechanic</th>
      <th>Default Value</th>
      <th>Valid Range</th>
      <th>Operational Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Global Master Switch</strong></td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>Central GameRule toggling the magnet system server-wide.</td>
    </tr>
    <tr>
      <td><strong>Individual Player Toggle</strong></td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>Per-player state toggled via hotkey (<code>\</code>) or <code>/magnet toggle</code>.</td>
    </tr>
    <tr>
      <td><strong>Attraction Range</strong></td>
      <td><strong><code>12</code> blocks</strong></td>
      <td><code>1</code> to <code>64</code> blocks</td>
      <td>Spherical detection radius centered on the player.</td>
    </tr>
    <tr>
      <td><strong>NoClip (Phase Shift)</strong></td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>When true, items fly directly through solid blocks.</td>
    </tr>
    <tr>
      <td><strong>Experience Orbs</strong></td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>When true, experience orbs are vacuumed alongside items.</td>
    </tr>
    <tr>
      <td><strong>Suction Velocity</strong></td>
      <td><code>80</code> (80%)</td>
      <td><code>1</code> to <code>1000</code></td>
      <td>Base velocity at which items travel toward you.</td>
    </tr>
    <tr>
      <td><strong>Suction Acceleration</strong></td>
      <td><code>10</code> (10%)</td>
      <td><code>1</code> to <code>1000</code></td>
      <td>Acceleration curve applied to flying items.</td>
    </tr>
    <tr>
      <td><strong>Instant Pickup Mode</strong></td>
      <td><code>false</code></td>
      <td><code>true / false</code></td>
      <td>Teleports items instantly to inventory instead of flying.</td>
    </tr>
    <tr>
      <td><strong>Line of Sight (LOS)</strong></td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>Requires unobstructed line of sight between eyes and item.</td>
    </tr>
    <tr>
      <td><strong>Keep Moving if Unseen</strong></td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>Continues momentum if line of sight breaks mid-travel.</td>
    </tr>
  </tbody>
</table>

<hr>

<h2>🚀 In-Game Commands &amp; Quick Start</h2>

<p>Use these commands in chat for instant control and live diagnostics:</p>

<pre><code>/magnet toggle                       &rarr; Toggle your personal magnet on or off (or press '\')
/ig_magnet toggle                    &rarr; Alternative alias for personal toggle
/magnet debug                        &rarr; View player UUID, personal toggle, range, and LOS raycast checks
/magnet debug log                    &rarr; Toggle persistent file logging to logs/ig_magnet_debug.log</code></pre>

<hr>

<h2>⚙️ Configuration (Native GameRules)</h2>

<blockquote><p><strong>💡 Config vs. In-Game GameRules:</strong> The global configuration file (<code>config/ig_magnet.json</code>) only defines default values for newly created worlds. In existing worlds, change settings in-game via the <strong>Edit Game Rules</strong> UI screen or the <code>/gamerule</code> command.</p></blockquote>

<table>
  <thead>
    <tr>
      <th>GameRule Name</th>
      <th>Type</th>
      <th>Default</th>
      <th>Valid Range</th>
      <th>Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code>ig:magnet_enabled</code></td>
      <td>Boolean</td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>Master toggle for the entire item magnet feature.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_range</code></td>
      <td>Integer</td>
      <td><code>12</code></td>
      <td><code>1</code> to <code>64</code></td>
      <td>Block radius from which items are attracted.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_noclip</code></td>
      <td>Boolean</td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>Allows items to pass through solid blocks and terrain.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_affects_xp</code></td>
      <td>Boolean</td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>Attracts experience orbs in addition to items.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_particles</code></td>
      <td>Boolean</td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>Spawns cosmetic particle trails on flying items.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_particle_count</code></td>
      <td>Integer</td>
      <td><code>1</code></td>
      <td><code>0</code> to <code>100</code></td>
      <td>Particles spawned per item per tick.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_max_particle_sources</code></td>
      <td>Integer</td>
      <td><code>5</code></td>
      <td><code>0</code> to <code>100</code></td>
      <td>Maximum items emitting particles simultaneously to prevent lag.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_speed</code></td>
      <td>Integer</td>
      <td><code>80</code></td>
      <td><code>1</code> to <code>1000</code></td>
      <td>Speed percentage at which items fly toward player.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_acceleration</code></td>
      <td>Integer</td>
      <td><code>10</code></td>
      <td><code>1</code> to <code>1000</code></td>
      <td>Acceleration percentage curve for flying items.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_instant</code></td>
      <td>Boolean</td>
      <td><code>false</code></td>
      <td><code>true / false</code></td>
      <td>Teleports items immediately into inventory upon entering radius.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_los_only</code></td>
      <td>Boolean</td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>Requires line of sight between player eyes and item.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_keep_moving_if_unseen</code></td>
      <td>Boolean</td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>Flying items maintain momentum if LOS breaks mid-flight.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_blocked_by_transparent</code></td>
      <td>Boolean</td>
      <td><code>false</code></td>
      <td><code>true / false</code></td>
      <td>Glass and transparent blocks block line of sight.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_blocked_by_flora</code></td>
      <td>Boolean</td>
      <td><code>false</code></td>
      <td><code>true / false</code></td>
      <td>Grass and flowers block line of sight.</td>
    </tr>
    <tr>
      <td><code>ig:magnet_blocked_by_block_entities</code></td>
      <td>Boolean</td>
      <td><code>false</code></td>
      <td><code>true / false</code></td>
      <td>Chests and block entities block line of sight.</td>
    </tr>
  </tbody>
</table>

<hr>

<h2>📖 In-Depth How-To &amp; Operational Playbook</h2>

<h3>1. Drop-In Setup &amp; Baseline Initialization</h3>
<ol>
  <li>Place <code>Magnet-Let-me-get-that-*.jar</code> along with <strong>Fabric API</strong> and <strong>Dasik Library</strong> into your <code>mods</code> directory.</li>
  <li>Launch Minecraft. On first startup, the mod creates <code>config/ig_magnet.json</code> with recommended defaults (<code>range: 12</code>, <code>noClip: true</code>, <code>losOnly: true</code>).</li>
</ol>

<h3>2. Live In-Game Tuning vs. Global Template</h3>
<ul>
  <li><strong>For New Worlds:</strong> Tune baseline preferences in <code>config/ig_magnet.json</code> or via ModMenu + YACL.</li>
  <li><strong>For Existing Worlds:</strong> Open <strong>Edit Game Rules</strong> &rarr; scroll to <strong>Magnet</strong>, or run <code>/gamerule ig:&lang;rule&rang; &lang;value&rang;</code> in chat.</li>
</ul>

<h3>3. Precision Mining &amp; Nether Exploration Tactics</h3>
<ul>
  <li>When mining near lava lakes in the Nether or deepslate caves, keep Magnet enabled. Ores you mine are pulled instantly to your body before they can touch lava or fall into crevices.</li>
  <li>If you find items flying through solid ground distracting, toggle <code>/gamerule ig:magnet_noclip false</code> so items follow open tunnels and corridors.</li>
</ul>

<h3>4. Customizing Keybindings &amp; Server Play</h3>
<ul>
  <li>Open <strong>Options</strong> &rarr; <strong>Controls</strong> &rarr; <strong>Key Binds</strong> &rarr; <strong>Magnet</strong> section. By default, the toggle is bound to <strong><code>\</code> (Backslash)</strong>. Rebind it to <strong><code>M</code></strong>, <strong><code>V</code></strong>, or any mouse thumb button.</li>
  <li>On multiplayer SMP servers, each player's toggle state is independent. Turning your magnet off does not disable magnetism for other players on the server.</li>
</ul>

<h3>5. Diagnostics &amp; Troubleshooting</h3>
<ul>
  <li>Use <code>/magnet debug</code> to verify whether an item is within range and check whether line-of-sight raycasting passes.</li>
  <li>If you encounter unexpected behavior, run <code>/magnet debug log</code> to enable detailed tracing in <code>logs/ig_magnet_debug.log</code>.</li>
</ul>

<hr>

<h2>🧩 Recommended Sister Mods</h2>

<p>If you enjoy <strong>Magnet (Let Me Get That!)</strong>, these companion mods from the <strong>Instant Gratification Collection</strong> plug in seamlessly:</p>

<ul>
  <li>📦 <a href="https://www.curseforge.com/minecraft/mc-mods/ig-item-clumps"><strong>Item Clumps</strong></a>: Aggressively condenses hundreds of scattered ground items into single lightweight mega-stacks, eliminating entity tick lag before the magnet vacuums them up.</li>
  <li>⛏️ <a href="https://www.curseforge.com/minecraft/mc-mods/instant-gratification-ore-amplifier"><strong>Ore Amplifier</strong></a>: Multiplies ore vein yields in newly generated chunks so your mining trips yield massive hauls with zero tedious searching.</li>
  <li>📦 <a href="https://www.curseforge.com/minecraft/mc-mods/ig-stack-size-adjuster"><strong>Stack Size Adjuster</strong></a>: Scale inventory slot limits up to 2.14 Billion, ensuring your inventory never runs out of room while vacuuming drops.</li>
</ul>

<p><em>Explore the full <a href="https://www.curseforge.com/members/dasikigaijin/projects"><strong>Instant Gratification Collection</strong></a> for more high-convenience enhancements.</em></p>

<hr>

<h2>☕ Support</h2>

<p>If you enjoy the <strong>Instant Gratification Collection</strong>, consider fueling future development!</p>

<p align="center">
  <a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&amp;logo=ko-fi&amp;logoColor=white" alt="Ko-fi"></a>
  <a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
  <a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

<blockquote><p><strong>🇮🇩 Indonesian Users:</strong> SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!</p></blockquote>

<blockquote><p><strong>💡 Dedicated Server Hosting Partner:</strong> Looking for a reliable server to play with friends? Check out <strong>BisectHosting</strong> for 1-click modpack installations, automated backups, and 24/7 dedicated customer support.</p></blockquote>

<hr>

<h2>📜 Credits &amp; Modpack Permissions</h2>

<table>
  <thead>
    <tr>
      <th>Property</th>
      <th>Information</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Creator / Author</strong></td>
      <td><strong>Dasik</strong> (Rifaditya)</td>
    </tr>
    <tr>
      <td><strong>Collection</strong></td>
      <td><a href="https://www.curseforge.com/members/dasikigaijin/projects">Instant Gratification Collection</a></td>
    </tr>
    <tr>
      <td><strong>License</strong></td>
      <td><a href="https://www.gnu.org/licenses/gpl-3.0.html">GNU General Public License v3.0 (GPLv3)</a></td>
    </tr>
    <tr>
      <td><strong>Source Code</strong></td>
      <td><a href="https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-">GitHub - Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-</a></td>
    </tr>
    <tr>
      <td><strong>Issue Tracker</strong></td>
      <td><a href="https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/issues">GitHub Issues</a></td>
    </tr>
    <tr>
      <td><strong>Documentation / Wiki</strong></td>
      <td><a href="https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-/wiki">GitHub Wiki</a></td>
    </tr>
  </tbody>
</table>

<blockquote>
  <p><strong>📦 Modpack Permissions &amp; Distribution:</strong><br>
  You are fully welcome to include this mod in any modpack on any platform! However, the mod file must be downloaded directly through official distribution channels (<strong>CurseForge</strong> or <strong>Modrinth</strong>). Re-uploading, mirroring, or redistributing the original mod JAR to third-party mirror sites, scraper portals, or unauthorized launchers is strictly prohibited.</p>
  <p><strong>⚖️ License &amp; Fork Guidelines (No Zero-Change Re-uploads):</strong><br>
  This project is open-source under the <strong>GNU GPLv3</strong>. You are fully encouraged to inspect the code, learn from it, and fork the repository to create genuine modifications, substantial feature expansions, or community ports&mdash;provided your project remains open-source under GPLv3 with proper attribution.<br>
  <strong>However, straight 1:1 re-uploads, clone forks with no meaningful functional changes, or re-publishing identical builds under different project names (e.g. to farm downloads or rewards) are strictly forbidden.</strong></p>
</blockquote>

<hr>

<p align="center">
  <strong>Made with ❤️ for the Minecraft community</strong><br>
  <em>Part of the Instant Gratification Collection</em>
</p>
