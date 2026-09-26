# 👁️ Linha de Visão & Mecânicas de Obstrução (MC 26.1.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox do Recurso | Parâmetros Técnicos |
| :--- | :--- |
| **Mecanismo Primário de Visão** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **Mecanismo Secundário de Visão** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **Campo de Visão Esférico** | $360.0^\circ$ (Percepção omnidirecional completa) |
| **Tolerância de Distância de Contato** | Limiar de contato com o alvo de $0.3\text{ m}$ |
| **Tag de Retenção de Memória** | `ig$isMagnetized()` / `ig$setMagnetized()` |
| **Regra de Momento** | `ig:magnet_keep_moving_if_unseen = true` |
| **Implementação de Contexto** | Registro estático `VisionContext` |

---

## 📖 Pipeline de Visão em Duplo Passe

No Minecraft 26.1.2, a linha de visão é avaliada através de um **Pipeline de Visão em Duplo Passe** sem alocações desnecessárias:

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

```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **Campo Omnidirecional**: Com um ângulo de FOV de $360^\circ$, itens dropados acima, abaixo ou atrás do jogador são puxados suavemente sem forçar o giro da câmera.
* **Tolerância de Contato Sub-Voxel**: A margem de $0.3\text{ m}$ evita rejeições indevidas quando itens descansam alinhados com paredes sólidas.

---

## 🌿 Passe 2: Travessia Granular de Blocos (`SecondaryVisionCheck`)

```java
private record VisionContext(
        Level level,
        boolean blockTransparent,
        boolean blockFlora,
        boolean blockEntities,
        CollisionContext collisionContext,
        Vec3 start,
        Vec3 end
) {}
```

* **Flora (`ig:magnet_blocked_by_flora`)**: Verifica ocorrências de `BushBlock` e `LeavesBlock`.
* **Entidades de Bloco (`ig:magnet_blocked_by_block_entities`)**: Verifica `state.hasBlockEntity()` (Baús, Caixas de Shulker, Camas).
* **Blocos Transparentes (`ig:magnet_blocked_by_transparent`)**: Testa `state.getVisualShape().clip(...)` contra Vidros, Painéis e Lajes.

---

## 🚀 Continuidade de Momento (`keepMovingIfUnseen`)

* Ao ser avistada pela primeira vez, `((IMagnetEntity) entity).ig$setMagnetized()` sinaliza a entidade na memória.
* Se `ig:magnet_keep_moving_if_unseen = true`, o item continua sendo puxado ao redor de obstáculos desde que já tenha sido magnetizado anteriormente.

---

## ⚙️ Configurações Relevantes & GameRules

| GameRule | Tipo | Padrão | Descrição |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | Exige linha de visão para atrair itens. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | Continua puxando itens magnetizados se a linha de visão for quebrada em pleno voo. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | Se verdadeiro, vidros e blocos transparentes bloqueiam a linha de visão. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | Se verdadeiro, grama e flores bloqueiam a linha de visão. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | Se verdadeiro, baús e entidades de bloco bloqueiam a linha de visão. |

---

## 🔗 Documentação da Wiki Relacionada
* [[Física de Vácuo & Deslocamento de Fase|pt_br-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Referência Completa de GameRules|pt_br-26.1.2-GameRules]]
* [[Retornar ao Portal do MC 26.1.2|pt_br-26.1.2-Home]]
