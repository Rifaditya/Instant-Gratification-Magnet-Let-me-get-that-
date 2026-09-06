# ⚡ Modo de Coleta Instantânea (MC 26.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox do Recurso | Parâmetros Técnicos |
| :--- | :--- |
| **Classe do Sistema** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **GameRule de Habilitação** | `ig:magnet_instant` (Padrão: `false`) |
| **GameRule de Raio** | `ig:magnet_range` (Padrão: `12`, Intervalo: `1..64`) |
| **Ponto de Injeção** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **Variável Alvo** | Caixa de Colisão de Coleta do Jogador (`AABB pickupArea`) |
| **Lógica de Inventário** | Pipeline 100% Nativo Vanilla `Player.touch(ItemEntity)` |

---

## 📖 Visão Geral da Coleta Instantânea

Enquanto o modo de vácuo padrão puxa itens fisicamente pelo ar usando interpolação de velocidade, o **Modo de Coleta Instantânea** elimina completamente o tempo de viagem. Quando ativado, os itens dropados dentro do alcance são coletados no inventário do jogador no exato microssegundo em que surgem.

Em vez de criar loops personalizados de inserção em inventário — arriscados e sujeitos a falhas —, o **Magnet, Let me get that!** implementa a Coleta Instantânea expandindo de forma não destrutiva a caixa de colisão (bounding box) de coleta nativa do jogador dentro do método `aiStep()` do vanilla.

```
+-----------------------------------------------------------------------------------+
|                            TICK DO JOGADOR NO VANILLA: aiStep()                   |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
                +---------------------------------------------------+
                |   PlayerMixin.ig_magnet$expandPickupArea()        |
                |   pickupArea = pickupArea.inflate(range);         |
                +---------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|               EXECUÇÃO VANILLA ItemEntity.playerTouch(Player)                     |
|   - Empilhamento nativo de inventário e coletas parções                           |
|   - Animação de coleta e sons do vanilla (item.pickup / entity.experience_orb)   |
|   - Disparo de estatísticas e conquistas nativas                                  |
|   - Retenção segura de itens excedentes em caso de inventário cheio               |
+-----------------------------------------------------------------------------------+
```

---

## 🧩 Implementação Arquitetural (`PlayerMixin.java`)

```java
@ModifyVariable(
        method = "aiStep",
        at = @At(value = "STORE"),
        ordinal = 0
)
private AABB ig_magnet$expandPickupArea(AABB pickupArea) {
    Player player = (Player) (Object) this;
    if (!this.ig_magnet$isMagnetEnabled() || player.isDeadOrDying() || player.isSpectator()) {
        return pickupArea;
    }

    Level level = player.level();
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_ENABLED) && ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    return pickupArea;
}
```

### Garantias Principais de Engenharia:
1. **Portões de Segurança**: Se o jogador estiver morto/morrendo (`player.isDeadOrDying()`), no modo espectador (`player.isSpectator()`) ou com seu ímã pessoal desativado (`!isMagnetEnabled()`), a caixa de colisão nunca é expandida.
2. **Autoridade no Servidor**: A lógica de coleta é executada estritamente no servidor lógico (`!level.isClientSide()`), eliminando itens fantasmas e desincronizações de inventário.
3. **Zero Conflito de Movimentação Dupla**: Quando `ig:magnet_instant` é verdadeiro, o método `MagnetMovement.pull()` aborta automaticamente atualizações de velocidade, evitando que a física de voo e a coleta instantânea disputem o controle do item.

---

## ⚙️ Configurações Relevantes & GameRules

| GameRule | Tipo | Padrão | Descrição |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | Permite o teletransporte instantâneo para o inventário em vez do voo físico. |
| `ig:magnet_range` | Integer | `12` | O raio (em blocos) pelo qual a AABB de coleta é inflada. |
| `ig:magnet_enabled` | Boolean | `true` | Alternância principal de todo o mod. |

---

## 🔗 Documentação da Wiki Relacionada
* [[Física de Vácuo & Deslocamento de Fase|pt_br-26.2-Vacuum-and-Phase-Shifting]]
* [[Alternância do Jogador & Persistência|pt_br-26.2-Player-Toggle-and-Persistence]]
* [[Arquitetura & Mixins|pt_br-26.2-Architecture-and-Mixins]]
* [[Retornar ao Portal do MC 26.2|pt_br-26.2-Home]]
