# 💻 Comandos do Servidor & Suporte a Clientes Vanilla (MC 26.1.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox do Recurso | Parâmetros Técnicos |
| :--- | :--- |
| **Classe do Comando** | `net.instantgratification.magnet.MagnetCommand` |
| **Literais Principais** | `/magnet` e `/ig_magnet` (Espelhos de apelidos idênticos) |
| **Subcomandos** | `toggle` |
| **Sincronização de Rede** | `ServerPlayNetworking.send(player, new MagnetTogglePayload(newState))` |

---

## 📖 Referência de Comandos

### `/magnet toggle` (ou `/ig_magnet toggle`)
Alterna o estado do ímã de itens do jogador executor entre ativado e desativado.

* **Sintaxe do Comando**: `/magnet toggle`
* **Lógica de Execução**:
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
* **Compatibilidade com Clientes Vanilla**: Permite que jogadores em clientes vanilla conectando-se a um servidor Fabric alternem seu ímã sem a necessidade de instalar o mod no cliente.

---

## 🔗 Documentação da Wiki Relacionada
* [[Alternância do Jogador & Gerenciamento de Estado|pt_br-26.1.2-Player-Toggle-and-Persistence]]
* [[Referência Completa de GameRules|pt_br-26.1.2-GameRules]]
* [[Retornar ao Portal do MC 26.1.2|pt_br-26.1.2-Home]]
