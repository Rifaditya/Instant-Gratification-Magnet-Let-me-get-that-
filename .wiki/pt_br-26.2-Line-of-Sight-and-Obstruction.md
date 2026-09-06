# 👁️ Linha de Visão & Mecânicas de Obstrução (MC 26.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox do Recurso | Parâmetros Técnicos |
| :--- | :--- |
| **Mecanismo Primário de Visão** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **Mecanismo Secundário de Visão** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **Campo de Visão Esférico** | $360.0^\circ$ (Percepção omnidirecional completa) |
| **Tolerância de Distância de Contato** | Limiar de contato com o alvo de $0.3\text{ m}$ |
| **Tag de Retenção de Memória** | `ig_magnet$isMagnetized()` / `ig_magnet$setMagnetized()` |
| **Regra de Momento** | `ig:magnet_keep_moving_if_unseen = true` |
| **Regras de Filtro Granular** | Blocos transparentes, Flora, Entidades de Bloco |

---

## 📖 Arquitetura de Visão em Duplo Passe

Para impedir a coleta indevida de itens através de paredes de cavernas e bases protegidas mantendo desempenho com zero lag, o **Magnet, Let me get that!** emprega um **Pipeline de Visão em Duplo Passe** de alta velocidade:

```
                                +---------------------------+
                                |    ITEM ALVO DETECTADO    |
                                +-------------+-------------+
                                              |
                                              v
                              +-------------------------------+
                              |    PASSE PRIMÁRIO (LOS 360°)  |
                              |   PlayerVisionTracker.canSee  |
                              +---------------+---------------+
                                              |
                        +---------------------+---------------------+
                        |                                           |
                   [VISÍVEL]                                   [OBSTRUÍDO]
                        |                                           |
                        v                                           v
      +-----------------------------------+               +--------------------+
      |     PASSE SECUNDÁRIO (GRANULAR)   |               |CHECAGEM DE MOMENTO |
      |     SecondaryVisionCheck.canSee   |               | keepMovingIfUnseen |
      +-----------------+-----------------+               +---------+----------+
                        |                                           |
            +-----------+-----------+                     +---------+---------+
            |                       |                     |                   |
       [APROVADO]              [BLOQUEADO]           [MAGNETIZADO]      [NÃO-MAGNETIZADO]
            |                       |                     |                   |
            v                       v                     v                   v
    +---------------+       +---------------+     +---------------+   +---------------+
    |  PUXAR ITEM & |       |REJEITAR/PARAR |     |CONTINUAR ATRAÇÃO| | REJEITAR ATRAÇÃO|
    |SET MAGNETIZED |       +---------------+     +---------------+   +---------------+
    +---------------+
```

---

## 🔍 Passe 1: Checagem de Linha de Visão Primária Esférica em 360°

O passe primário invoca o mecanismo de raycasting otimizado da DasikLibrary:
```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **FOV Omnidirecional**: Com um cone de campo de visão de $360.0^\circ$, o jogador pode atrair itens posicionados diretamente atrás da sua cabeça, acima dele ou abaixo dos seus pés, desde que nenhuma parede sólida se interponha.
* **Tolerância de Contato de 0,3 m**: Quando os itens estão encostados nos cantos dos blocos, o raycasting sub-voxel com raio de $0.3\text{ m}$ garante que os itens não sejam rejeitados falsamente.

---

## 🌿 Passe 2: Filtragem Granular de Estados de Blocos (`SecondaryVisionCheck`)

Se o passe primário for bem-sucedido, o mod avalia regras opcionais de obstrução granular usando `BlockGetter.traverseBlocks`:

```java
public static boolean canSee(Player player, Entity target, boolean blockTransparent, boolean blockFlora, boolean blockEntities)
```

1. **Filtragem de Flora & Folhagem (`ig:magnet_blocked_by_flora`)**:
   - Verifica se qualquer bloco percorrido é uma instância de `BushBlock` (grama alta, flores, plantações, mudas) ou `LeavesBlock` (folhas de árvores).
   - Se habilitado (`true`), a folhagem atua como uma barreira opaca para a atração de itens.
2. **Entidades de Bloco Interativas (`ig:magnet_blocked_by_block_entities`)**:
   - Verifica `state.hasBlockEntity()` nas posições atravessadas.
   - Se habilitado (`true`), Baús, Baús com Armadilha, Barris, Caixas de Shulker, Camas e Ejetores bloqueiam a linha de visão.
3. **Blocos Transparentes & Não Completos (`ig:magnet_blocked_by_transparent`)**:
   - Executa raycast contra `state.getVisualShape(...)`.
   - Se habilitado (`true`), Vidros, Painéis de Vidro, Barras de Ferro, Cercas, Lajes e Escadas bloqueiam a atração de itens.

---

## 🚀 Continuidade de Momento (`keepMovingIfUnseen`)

Em sessões rápidas de mineração ou combate, os itens puxados ao redor de esquinas frequentemente quebram a linha de visão de forma temporária. Em vez de congelar ou cair na lava:

1. **Tag de Magnetização**: Quando um item é avistado na linha de visão, `((IMagnetEntity) entity).ig_magnet$setMagnetized()` define uma flag booleana na memória.
2. **Retenção de Momento**: Se o item perder a linha de visão (LOS) nos ticks subsequentes:
   - Se `ig:magnet_keep_moving_if_unseen = true` E `ig_magnet$isMagnetized() == true`: O item continua sendo puxado para o jogador.
   - Se `ig:magnet_keep_moving_if_unseen = false`: A atração é interrompida imediatamente após a perda de LOS.
   - Se o item **nunca foi visto** (ex.: gerado atrás de uma parede por uma explosão ou ejetor): A atração é rejeitada instantaneamente.

---

## ⚙️ Configurações Relevantes & GameRules

| GameRule | Tipo | Padrão | Descrição |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | Exige visibilidade em linha de visão para atrair itens. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | Continua puxando itens magnetizados se a linha de visão for quebrada durante o trajeto. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | Se verdadeiro, vidros e blocos transparentes obstruem a linha de visão. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | Se verdadeiro, grama, folhas e flores obstruem a linha de visão. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | Se verdadeiro, baús, camas e entidades de bloco obstruem a linha de visão. |

---

## 🔗 Documentação da Wiki Relacionada
* [[Física de Vácuo & Deslocamento de Fase|pt_br-26.2-Vacuum-and-Phase-Shifting]]
* [[Referência Completa de GameRules|pt_br-26.2-GameRules]]
* [[Arquitetura & Mixins|pt_br-26.2-Architecture-and-Mixins]]
* [[Retornar ao Portal do MC 26.2|pt_br-26.2-Home]]
