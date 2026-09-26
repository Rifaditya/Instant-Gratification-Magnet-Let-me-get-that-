# 🏆 Conquistas & Escopo de Progressão (MC 26.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox de Progressão | Detalhes |
| :--- | :--- |
| **JSONs de Conquistas Personalizadas** | `Nenhum (Conformidade Intencional com o Vanilla)` |
| **Linha de Design** | Instant Gratification (IG) |
| **Gatilhos de Conquistas** | 100% Nativo Vanilla `Player.touch(ItemEntity)` |
| **Requisito de Bloqueio** | Nenhum (Zero barreiras arbitrárias de desbloqueio) |

---

## 📖 Política de Ausência & Escopo de Design

Em estrita conformidade com a filosofia de desenvolvimento **Instant Gratification (IG)**, o **Magnet, Let me get that!** intencionalmente **não contém árvores de conquistas personalizadas nem marcos de desbloqueio**.

O mod foi projetado como uma melhoria intrínseca de qualidade de vida para as interações centrais de sobrevivência do jogador. Os recursos estão disponíveis desde o primeiro instante em que o jogador entra no mundo, sem necessidade de cumprir árvores de missões artificiais, desbloquear tecnologias ou enfrentar bloqueios de progressão.

```
+-----------------------------------------------------------------------------------+
|                        PRINCÍPIO DE DESIGN DO INSTANT GRATIFICATION               |
|                                                                                   |
|  "A 'Caminhada da Vergonha' (andar 5 blocos para pegar um bloco minerado)         |
|   é um pecado capital contra o estado de fluxo. O IG Magnet não é um item de      |
|   árvore tecnológica ou recompensa; é uma extensão da vontade do jogador.         |
|   Se você consegue ver o item, ele deve ser seu."                                 |
+-----------------------------------------------------------------------------------+
```

---

## 🎮 Compatibilidade com Conquistas Nativas do Vanilla

Como tanto o voo de vácuo padrão quanto o [[Modo de Coleta Instantânea|pt_br-26.2-Instant-Pickup-Mode]] utilizam os pipelines nativos de coleta `ItemEntity.playerTouch()` e `ExperienceOrb.playerTouch()` do Minecraft:

1. **Disparos de Marcos do Vanilla**: Coletar diamantes, detritos ancestrais ou varas de blaze através do ímã dispara imediatamente os critérios de conquistas do vanilla (ex.: `"Diamonds!"`, `"Cover Me in Debris"`).
2. **Compatibilidade com Mods de Missões**: Mods de missões de terceiros (como FTB Quests ou Better Questing) que monitoram a coleta de itens nos inventários funcionam de forma imediata sem necessidade de pontes de compatibilidade específicas.
3. **Rastreamento de Estatísticas**: As estatísticas nativas do jogo (`stat.pickup.minecraft.*`) continuam sendo incrementadas com total precisão.

---

## 🔗 Documentação da Wiki Relacionada
* [[Modo de Coleta Instantânea|pt_br-26.2-Instant-Pickup-Mode]]
* [[Referência Completa de GameRules|pt_br-26.2-GameRules]]
* [[Retornar ao Portal do MC 26.2|pt_br-26.2-Home]]
