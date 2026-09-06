# 💻 Comandos de Servidor y Soporte de Clientes Vanilla (MC 26.1.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Característica | Parámetros Técnicos |
| :--- | :--- |
| **Clase del Comando** | `net.instantgratification.magnet.MagnetCommand` |
| **Literales Primarios** | `/magnet` e `/ig_magnet` (Alias espejo idénticos) |
| **Subcomandos** | `toggle` |
| **Sincronización de Red** | `ServerPlayNetworking.send(player, new MagnetTogglePayload(newState))` |

---

## 📖 Referencia de Comandos

### `/magnet toggle` (o `/ig_magnet toggle`)
Alterna el estado del imán personal del jugador ejecutor entre activado y desactivado.

* **Sintaxis del Comando**: `/magnet toggle`
* **Lógica de Ejecución**:
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
* **Compatibilidad con Clientes Vanilla**: Permite que jugadores con clientes oficiales de vanilla conectados a un servidor Fabric alternen su imán sin necesidad de instalar el mod en su cliente.

---

## 🔗 Documentación Relacionada de la Wiki
* [[Alternancia del Jugador y Gestión de Estado|es_es-26.1.2-Player-Toggle-and-Persistence]]
* [[Referencia Completa de GameRules|es_es-26.1.2-GameRules]]
* [[Volver al Portal de MC 26.1.2|es_es-26.1.2-Home]]
