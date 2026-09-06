# 🧲 Movimento de Vácuo & Deslocamento de Fase (MC 26.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox do Recurso | Parâmetros Técnicos |
| :--- | :--- |
| **Classe do Sistema** | `net.instantgratification.magnet.MagnetMovement` |
| **Evento de Disparo** | Tick do Jogador no Servidor (`PlayerMixin` $\rightarrow$ `MagnetManager.tick`) |
| **Alcance Padrão de Puxar** | `12` blocos (`ig:magnet_range`) |
| **Velocidade Terminal Padrão** | `80%` ($0.8\text{ blocos/tick} = 16.0\text{ m/s}$) |
| **Aceleração Padrão** | `10%` (fator lerp de $0.10\text{/tick}$) |
| **Deslocamento de Fase (NoClip)** | Habilitado (`ig:magnet_noclip = true`) |
| **Vetor Alvo** | Posição dos Olhos do Jogador (`player.getEyePosition()`) |
| **Impulso de Deslocamento do Solo** | $+0.05\text{ m}$ no eixo Y quando `entity.onGround()` |

---

## 📖 Visão Geral do Sistema

A mecânica central de vácuo em **Magnet, Let me get that!** realiza uma varredura a cada tick do servidor em busca de instâncias de `ItemEntity` dropadas dentro do raio configurado do jogador e as puxa em direção ao nível dos olhos do jogador utilizando interpolação não linear suave.

Para evitar que os itens fiquem presos em bordas de pedregulho, copas de árvores ou fendas de veios de minério, o mod ativa o **Deslocamento de Fase (NoClip)**, permitindo que os itens em voo atravessem voxels de blocos sólidos sem causar danos ou engasgos.

```
+------------------+     Linha de Visão OK     +----------------------+     Velocidade Lerp Aplicada     +------------------------+
| Entidade de Item | ------------------------> | Ativar NoClip (2 Tk) | -------------------------------> | Posição Olhos Jogador  |
+------------------+                            +----------------------+                                 +------------------------+
                                                           |
                                                           v
                                                [Cancelar Empurrão Parede]
                                                [Ignorar Colisão de Bloco]
                                                [Cancelar Gravidade Parede]
```

---

## 🧮 Física & Matemática Vetorial

Ao puxar um item, a trajetória é calculada diretamente no espaço euclidiano 3D:

### 1. Vetor até o Alvo
Seja $\vec{p}_{\text{entity}} = (x_e, y_e, z_e)$ a posição atual do item e $\vec{p}_{\text{eye}} = (x_p, y_p + h_{\text{eye}}, z_p)$ a posição dos olhos do jogador.
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|} = \frac{\vec{v}_{\text{target}}}{\sqrt{\Delta x^2 + \Delta y^2 + \Delta z^2}}$$

### 2. Velocidade Terminal Desejada
O vetor de velocidade alvo dimensiona a direção unitária $\hat{d}$ pelo parâmetro de velocidade configurado:
$$\text{Escalar de Velocidade } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
*Na configuração padrão ($80\%$), $s = 0.8\text{ blocos/tick}$. A $20\text{ ticks/s}$, a velocidade terminal é de $16.0\text{ m/s}$.*

### 3. Aceleração Não Linear (Lerp)
A velocidade é atualizada usando interpolação linear (`Vec3.lerp`) com base no fator de aceleração $a$:
$$\text{Fator de Aceleração } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0}$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 4. Impulso Anti-Atrito com o Solo
Se o item estiver apoiado sobre a superfície de um bloco (`entity.onGround() == true`), o atrito com o solo é quebrado imediatamente para evitar que ele seja arrastado pelo chão:
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 Deslocamento de Fase (Mecanismo NoClip)

Quando `ig:magnet_noclip` está habilitado, os itens recebem uma janela de NoClip de 2 ticks:

1. **Ativação do Estado**: `((IMagnetEntity) entity).ig_magnet$setMagnetNoClip()` define `noClipTicks = 2`.
2. **Interceptação de Movimento (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` salva `originalNoPhysics` e força `entity.noPhysics = true`.
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` restaura `entity.noPhysics = originalNoPhysics`.
3. **Cancelamento do Empurrão para Fora de Blocos**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` impede que a rotina nativa do vanilla ejete itens bruscamente para fora de blocos.
4. **Cancelamento Condicional de Gravidade**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` anula a gravidade para baixo *apenas* quando o item está fisicamente intersectando o voxel de um bloco (`!level.noCollision(...)`), preservando trajetórias em arco naturais em céu aberto.

---

## ⚙️ Configurações Relevantes & GameRules

| GameRule | Tipo | Padrão | Unidade / Intervalo | Descrição |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | Alternância principal de toda a lógica do vácuo. |
| `ig:magnet_range` | Integer | `12` | `1..64` blocos | Raio esférico máximo do vácuo. |
| `ig:magnet_speed` | Integer | `80` | `1..1000%` | Porcentagem da velocidade terminal ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000%` | Porcentagem de aceleração da atração ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | Permite atravessar blocos durante a atração. |

---

## 🔗 Documentação da Wiki Relacionada
* [[Linha de Visão & Penetração de Obstáculos|pt_br-26.2-Line-of-Sight-and-Obstruction]]
* [[Modo de Coleta Instantânea|pt_br-26.2-Instant-Pickup-Mode]]
* [[Implementações de Arquitetura & Mixin|pt_br-26.2-Architecture-and-Mixins]]
* [[Retornar ao Portal do MC 26.2|pt_br-26.2-Home]]
