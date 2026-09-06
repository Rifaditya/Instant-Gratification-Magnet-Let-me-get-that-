# 💻 Server-Befehle & Vanilla-Client-Unterstützung (MC 26.1.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Infobox | Technische Parameter |
| :--- | :--- |
| **Befehlsklasse** | `net.instantgratification.magnet.MagnetCommand` |
| **Primäre Literale** | `/magnet` und `/ig_magnet` (Identische Alias-Spiegelungen) |
| **Unterbefehle** | `toggle` |
| **Netzwerk-Synchronisation** | `ServerPlayNetworking.send(player, new MagnetTogglePayload(newState))` |

---

## 📖 Befehlsreferenz

### `/magnet toggle` (oder `/ig_magnet toggle`)
Schaltet den Item-Magnetstatus des ausführenden Spielers ein oder aus.

* **Befehlssyntax**: `/magnet toggle`
* **Ausführungslogik**:
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
* **Vanilla-Client-Kompatibilität**: Ermöglicht es Spielern auf Vanilla-Clients, die sich mit einem Fabric-Server verbinden, ihren Magneten umzuschalten, ohne dass der Client die Mod installiert haben muss.

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Spieler-Umschaltung & Sitzungsstatus|de_de-26.1.2-Player-Toggle-and-Persistence]]
* [[GameRules-Referenz|de_de-26.1.2-GameRules]]
* [[Zurück zum MC 26.1.2 Portal|de_de-26.1.2-Home]]
