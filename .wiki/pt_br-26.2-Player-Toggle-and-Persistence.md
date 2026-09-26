# 🔄 Alternância do Jogador, Persistência & Ciclo de Vida (MC 26.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox do Recurso | Parâmetros Técnicos |
| :--- | :--- |
| **Ponte de Interface** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Alvo de Mixin** | `net.minecraft.world.entity.player.Player` (`PlayerMixin.java`) |
| **Tecla de Atalho Padrão do Cliente** | `\` (Barra Invertida) — `key.ig_magnet.toggle` |
| **Categoria da Tecla** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Payload de Rede** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **Armazenamento de Codec NBT** | `ValueOutput` / `ValueInput` sob a tag `"ig_magnet_enabled"` |
| **Eventos de Ciclo de Vida** | `ServerPlayConnectionEvents.JOIN`, `ServerPlayerEvents.COPY_FROM`, `ServerPlayerEvents.AFTER_RESPAWN` |

---

## 📖 Visão Geral da Arquitetura de Estado

Em servidores multijogador e modpacks, jogadores individuais frequentemente têm preferências diferentes — construtores podem querer pausar a atração de itens enquanto decoram, enquanto mineradores desejam poder máximo de vácuo.

O **Magnet, Let me get that!** implementa um **estado de alternância individualizado por jogador** que é 100% persistente após recarregamentos do mundo, mortes, renascimentos e teletransportes entre dimensões.

```
                                [AÇÃO DO CLIENTE]
                    Jogador Pressiona Tecla de Atalho ('\')
                                      |
                                      v
                        [ESTADO LOCAL ATUALIZADO]
                    client.player -> isEnabled = !isEnabled
                    Aviso na Actionbar: "Ímã de Itens: Ativado/Desativado"
                                      |
                                      v
                        [TRANSMISSÃO DE PACOTE C2S]
                    ClientPlayNetworking.send(MagnetTogglePayload)
                                      |
                                      v
                             [RECEPTOR DO SERVIDOR]
                    context.server().execute(() -> {
                        ((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(val);
                    })
                                      |
         +----------------------------+----------------------------+
         |                                                         |
         v                                                         v
 [DADOS NBT PERSISTIDOS]                              [EVENTOS DE CICLO DE VIDA]
 ValueOutput.putBoolean("ig_magnet_enabled")          - JOIN: Sincronização S2C
 ValueInput.getBooleanOr("ig_magnet_enabled", true)   - COPY_FROM: Reter na Morte
                                                      - AFTER_RESPAWN: Sincronização S2C Nova Entidade
```

---

## ⌨️ Tecla de Atalho do Cliente & Overlay na Actionbar

* **Tecla Padrão**: `GLFW_KEY_BACKSLASH` (`\`), evitando conflitos de teclado com mods comuns de mapa e utilitários de inventário.
* **Auxiliar Dinâmico de Tipo de Tecla**: Usa `ig_magnet$getKeyboardType()` para resolver com segurança `InputConstants.Type.KEYBOARD` com fallback gracioso para `KEYSYM` entre diferentes snapshots do Fabric Loader.
* **Feedback Visual Instantâneo**: Alternar o ímã aciona uma notificação localizada na barra de ações (actionbar):
  - `chat.ig_magnet.enabled`: `"Item Magnet: Enabled"`
  - `chat.ig_magnet.disabled`: `"Item Magnet: Disabled"`

---

## 💾 Armazenamento NBT & Serialização de Codecs no Minecraft 26.2

As configurações de alternância do jogador são salvas diretamente no arquivo `.dat` do jogador no mundo, utilizando os pipelines de dados `ValueOutput` e `ValueInput` do Minecraft 26.2:

```java
// Salvando no NBT do Jogador
@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
    output.putBoolean("ig_magnet_enabled", this.ig_magnet$enabled);
}

// Carregando do NBT do Jogador
@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
    this.ig_magnet$enabled = input.getBooleanOr("ig_magnet_enabled", true);
}
```

---

## 🧬 Eventos de Ciclo de Vida do Fabric & Fluxo de Morte/Respawn

Quando um jogador morre no Minecraft, o jogo cria uma entidade `ServerPlayer` completamente nova ao renascer. O mod garante zero perda de estado:

1. **`ServerPlayerEvents.COPY_FROM`**: Copia o valor booleano de alternância de `oldPlayer` para `newPlayer` imediatamente na clonagem da entidade.
2. **`ServerPlayerEvents.AFTER_RESPAWN`**: Transmite automaticamente um pacote S2C `MagnetTogglePayload` para o cliente assim que a nova entidade do jogador se conecta, mantendo o HUD do cliente sincronizado.
3. **`ServerPlayConnectionEvents.JOIN`**: Sincroniza o estado NBT salvo do jogador para o cliente ao entrar em um servidor dedicado ou mundo em rede local (LAN).

---

## 🔗 Documentação da Wiki Relacionada
* [[Comandos Brigadier & Alternâncias no Servidor|pt_br-26.2-Commands]]
* [[Implementações de Arquitetura & Mixin|pt_br-26.2-Architecture-and-Mixins]]
* [[HUD & Diagnósticos|pt_br-26.2-HUD-and-Diagnostics]]
* [[Retornar ao Portal do MC 26.2|pt_br-26.2-Home]]
