# ⚙️ Referência Completa de GameRules (MC 26.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox da Categoria | Detalhes |
| :--- | :--- |
| **ID da Categoria** | `magnet:magnet_category` |
| **Título Localizado** | `Magnet, Let me get that!` (`gamerule.category.magnet.magnet_category`) |
| **Gerenciador Registrado** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **Classe do Registro** | `net.instantgratification.magnet.registry.ModGameRules` |
| **Total de Regras Registradas** | `15` Regras Namespaced |

---

## 📖 Administração de GameRules no Jogo

Todas as mecânicas globais do **Magnet, Let me get that!** são governadas por GameRules namespaced do Minecraft registradas sob o cabeçalho `magnet:magnet_category`.

Administradores podem configurar essas regras em tempo real no jogo usando o comando `/gamerule <regra> <valor>` ou pela tela nativa "Editar Regras do Jogo" ao criar ou editar um mundo.

```
/gamerule ig:magnet_enabled true
/gamerule ig:magnet_range 24
/gamerule ig:magnet_speed 120
/gamerule ig:magnet_instant true
```

---

## 📋 Tabela Completa de Referência de GameRules

| Identificador da GameRule | Tipo | Padrão | Limites | Nome de Exibição Localizado | Descrição & Efeito na Jogabilidade |
| :--- | :---: | :---: | :---: | :--- | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | **Magnet Enabled** | Alternância principal que ativa ou desativa globalmente o sistema de vácuo de itens. |
| `ig:magnet_range` | Integer | `12` | `1..64` | **Magnet Range** | O raio esférico em blocos a partir do qual o jogador atrai itens dropados. |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | **Magnet Noclip** | Habilita o deslocamento de fase, permitindo que itens atravessem blocos sólidos livremente. |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | **Attract XP Orbs** | Define se orbes de experiência são atraídos juntamente com os itens dropados. |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | **Magnet Particles** | Gera partículas de faíscas elétricas ao longo da trajetória de entidades puxadas. |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | **Particle Count** | Quantidade de faíscas emitidas por fonte ativa a cada tick de partícula. |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | **Max Particle Sources** | Número máximo de entidades simultâneas autorizadas a emitir partículas para evitar lag. |
| `ig:magnet_speed` | Integer | `80` | `1..1000` | **Item Speed** | Porcentagem da velocidade terminal ($80 = 0.8\text{ blocos/tick} = 16.0\text{ m/s}$). |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000` | **Item Acceleration** | Fator de interpolação de aceleração por tick ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_instant` | Boolean | `false` | `true/false` | **Instant Pickup** | Se verdadeiro, os itens teletransportam-se instantaneamente para o inventário via expansão da AABB com 0 tempo de voo. |
| `ig:magnet_los_only` | Boolean | `true` | `true/false` | **Line of Sight Only** | Exige visibilidade por linha de visão; impede puxar itens atrás de barreiras impenetráveis. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | `true/false` | **Keep Moving if Unseen** | Permite que itens magnetizados em linha de visão mantenham o momento se a visão for perdida no trajeto. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | `true/false` | **Blocked by Transparent** | Se verdadeiro, vidros, painéis, barras de ferro e blocos translúcidos bloqueiam a linha de visão. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | `true/false` | **Blocked by Flora** | Se verdadeiro, grama alta, plantações, flores e folhas bloqueiam a linha de visão. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | `true/false` | **Blocked by Block Entities** | Se verdadeiro, baús, camas, barris e caixas de shulker bloqueiam a linha de visão. |

---

## ⚖️ Precedência de GameRules sobre a Configuração JSON Global

> ⚠️ **Aviso Importante para Administradores de Servidores**:  
> As opções no arquivo de configuração JSON do cliente/servidor (`config/ig_magnet.json`) apenas estabelecem os **valores padrão iniciais para mundos RECÉM-CRIADOS**. Mundos ativos são controlados estritamente por GameRules. Modificar `ig_magnet.json` não alterará as configurações em um save existente até que sejam atualizadas via `/gamerule`.

---

## 🔗 Documentação da Wiki Relacionada
* [[Física de Vácuo & Deslocamento de Fase|pt_br-26.2-Vacuum-and-Phase-Shifting]]
* [[Linha de Visão & Penetração de Obstáculos|pt_br-26.2-Line-of-Sight-and-Obstruction]]
* [[GUI de Configuração & Sincronização JSON|pt_br-26.2-Configuration]]
* [[Retornar ao Portal do MC 26.2|pt_br-26.2-Home]]
