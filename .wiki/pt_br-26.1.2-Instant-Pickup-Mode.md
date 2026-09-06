# ⚡ Modo de Coleta Instantânea (MC 26.1.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox do Recurso | Parâmetros Técnicos |
| :--- | :--- |
| **Classe do Sistema** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **GameRule de Habilitação** | `ig:magnet_instant` (Padrão: `false`) |
| **GameRule de Raio** | `ig:magnet_range` (Padrão: `12`, Intervalo: `1..64`) |
| **Ponto de Injeção** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **Variável Alvo** | Caixa de Colisão de Coleta do Jogador (`AABB pickupArea`) |

---

## 📖 Visão Geral da Coleta Instantânea

No Minecraft 26.1.2, o **Modo de Coleta Instantânea** elimina o tempo de viagem dos itens expandindo a caixa de colisão de coleta do jogador em `Player.aiStep()`, alimentando instantaneamente os drops no inventário do jogador através dos manipuladores de coleta nativos do vanilla.

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
|   - Empilhamento nativo de inventário e coletas parciais                          |
|   - Animação de coleta e sons do vanilla (item.pickup / entity.experience_orb)   |
|   - Disparo de estatísticas e conquistas nativas                                  |
|   - Retenção de itens excedentes em caso de inventário cheio                      |
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
    Level level = player.level();
    
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    
    return pickupArea;
}
```

---

## ⚙️ Configurações Relevantes & GameRules

| GameRule | Tipo | Padrão | Descrição |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | Se verdadeiro, os itens teletransportam-se imediatamente para o jogador em vez de voar. |
| `ig:magnet_range` | Integer | `12` | Raio em blocos para a área de coleta expandida. |
| `ig:magnet_enabled` | Boolean | `true` | Alternância principal de todo o mod. |

---

## 🔗 Documentação da Wiki Relacionada
* [[Física de Vácuo & Deslocamento de Fase|pt_br-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Referência Completa de GameRules|pt_br-26.1.2-GameRules]]
* [[Retornar ao Portal do MC 26.1.2|pt_br-26.1.2-Home]]
