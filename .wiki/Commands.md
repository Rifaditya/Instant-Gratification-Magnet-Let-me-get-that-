# 💻 Server Commands & Vanilla Client Support (MC 26.1.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Feature Infobox | Technical Parameters |
| :--- | :--- |
| **Command Class** | `net.instantgratification.magnet.MagnetCommand` |
| **Primary Literals** | `/magnet` and `/ig_magnet` (Identical alias mirrors) |
| **Subcommands** | `toggle` |
| **Network Sync** | `ServerPlayNetworking.send(player, new MagnetTogglePayload(newState))` |

---

## 📖 Command Reference

### `/magnet toggle` (or `/ig_magnet toggle`)
Toggles the executing player's item magnet state on or off.

* **Command Syntax**: `/magnet toggle`
* **Execution Logic**:
  ```java
  private static int toggleMagnet(CommandSourceStack source) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      boolean newState = MagnetPlayerState.toggleMagnet(player);

      if (newState) {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.enabled"), false);
      } else {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.disabled"), false);
      }

      if (ServerPlayNetworking.canSend(player, MagnetTogglePayload.TYPE)) {
          ServerPlayNetworking.send(player, new MagnetTogglePayload(newState));
      }
      return 1;
  }
  ```
* **Vanilla Client Compatibility**: Enables players on vanilla clients connecting to a fabric server to toggle their magnet without requiring client mod installation.

---

## 🔗 Related Wiki Documentation
* [[Player Toggle & State Management|Player-Toggle-and-Persistence]]
* [[GameRules Complete Reference|GameRules]]
* [[Return to Home Portal|Home]]
