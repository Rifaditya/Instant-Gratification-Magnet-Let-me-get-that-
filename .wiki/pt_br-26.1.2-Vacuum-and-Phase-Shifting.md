# 🧲 Movimento de Vácuo & Deslocamento de Fase (MC 26.1.2)

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

No Minecraft 26.1.2, o mecanismo de vácuo rastreia continuamente instâncias válidas de `ItemEntity` dentro do raio esférico do jogador e as puxa diretamente para o nível dos olhos utilizando interpolação lerp.

Com o **Deslocamento de Fase (NoClip)** ativo, os itens atravessam paredes e blocos sólidos suavemente, evitando que os drops fiquem presos atrás de obstáculos durante a mineração ou combate.

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

### 1. Vetor Unitário Direcional
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|}$$

### 2. Interpolação de Velocidade
$$\text{Escalar de Velocidade } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0} = 0.8\text{ blocos/tick}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
$$\text{Fator de Aceleração } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0} = 0.10$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 3. Impulso Anti-Atrito com o Solo
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 Deslocamento de Fase (Mecanismo NoClip)

1. **Ativação do Estado**: `((IMagnetEntity) entity).ig$setMagnetNoClip()` ativa uma contagem regressiva de 2 ticks.
2. **Substituição da Física (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` armazena em cache `originalNoPhysics` e define `entity.noPhysics = true`.
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` restaura `entity.noPhysics = originalNoPhysics`.
3. **Prevenção de Empurrão para Fora**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` bloqueia as forças nativas de ejeção de paredes do vanilla.
4. **Cancelamento de Gravidade em Parede**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` cancela a gravidade *apenas* quando o item está fisicamente dentro do voxel de um bloco.

---

## ⚙️ Configurações Relevantes & GameRules

| GameRule | Tipo | Padrão | Descrição |
| :--- | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | Alternância principal para as mecânicas de vácuo. |
| `ig:magnet_range` | Integer | `12` | Raio do vácuo em blocos (1 a 64). |
| `ig:magnet_speed` | Integer | `80` | Porcentagem da velocidade terminal ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Integer | `10` | Porcentagem do fator de aceleração ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Boolean | `true` | Habilita o deslocamento de fase através de blocos durante a atração. |

---

## 🔗 Documentação da Wiki Relacionada
* [[Linha de Visão & Penetração de Obstáculos|pt_br-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Modo de Coleta Instantânea|pt_br-26.1.2-Instant-Pickup-Mode]]
* [[Implementações de Arquitetura & Mixin|pt_br-26.1.2-Architecture-and-Mixins]]
* [[Retornar ao Portal do MC 26.1.2|pt_br-26.1.2-Home]]
