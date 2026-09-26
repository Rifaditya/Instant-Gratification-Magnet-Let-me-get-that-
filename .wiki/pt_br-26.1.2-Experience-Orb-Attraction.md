# ✨ Atração de Orbes de Experiência (MC 26.1.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox do Recurso | Parâmetros Técnicos |
| :--- | :--- |
| **Classe da Entidade Alvo** | `net.minecraft.world.entity.ExperienceOrb` |
| **Classe Gerenciadora** | `net.instantgratification.magnet.MagnetManager` |
| **GameRule de Habilitação** | `ig:magnet_affects_xp` (Padrão: `true`) |
| **Consulta de Varredura** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **Tipo de Partícula** | `ParticleTypes.ELECTRIC_SPARK` |
| **Limite de Fontes de Partículas** | `ig:magnet_max_particle_sources` (Padrão: `5`) |

---

## 📖 Mecânicas de Vácuo de Experiência

Sob a filosofia Instant Gratification, deixar orbes de experiência para trás prejudica o fluxo agradável do jogo. No Minecraft 26.1.2, as entidades `ExperienceOrb` são atraídas com física idêntica de velocidade e deslocamento de fase em relação aos itens dropados.

```
+------------------+     getEntitiesOfClass     +-----------------------+     MagnetMovement.pull     +--------------------+
| AABB Varredura   | -------------------------> | List<ExperienceOrb>   | --------------------------> | Coleta do Jogador  |
+------------------+                            +-----------------------+                             +--------------------+
                                                            |
                                                            v
                                                [Aplicar NoClip Deslocamento]
                                                [Aplicar Vetor Velocidade Lerp]
                                                [Faíscas com Limite de Fluxo]
```

---

## ⚡ Física Sincronizada & Deslocamento de Fase

1. **Deslocamento de Fase (NoClip)**: Os orbes de experiência atravessam blocos sólidos para evitar ficarem presos atrás de paredes ou cantos de teto.
2. **Interpolação de Velocidade**: Os orbes aceleram suavemente em direção à posição dos olhos do jogador conforme as porcentagens configuradas de velocidade e aceleração.
3. **Restrição por Linha de Visão**: Quando `ig:magnet_los_only` é verdadeiro, os orbes de XP devem estar visíveis para o jogador ou possuir momento de magnetização ativo.

---

## 🛡️ Prevenção de Lag & Agrupamento de Partículas

```java
int maxParticleSources = ModGameRules.getInt(player.level(), ModGameRules.MAGNET_MAX_PARTICLE_SOURCES);
int particleSourceCount = 0;

for (ExperienceOrb orb : orbs) {
    boolean shouldSpawnParticles = false;
    if (particleSourceCount < maxParticleSources) {
        shouldSpawnParticles = true;
        particleSourceCount++;
    }
    MagnetMovement.pull(orb, player, shouldSpawnParticles);
}
```

* **Máximo de Fontes de Partículas**: Apenas as primeiras $N$ entidades por tick geram rastros visuais.
* **Fallback Seguro no Cliente**: Em `MagnetMovement.java`, caso o nível não seja um `ServerLevel`, as partículas recorrem de forma transparente a `level.addParticle(...)`.

---

## ⚙️ Configurações Relevantes & GameRules

| GameRule | Tipo | Padrão | Descrição |
| :--- | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | Define se o ímã atrai Orbes de Experiência. |
| `ig:magnet_particles` | Boolean | `true` | Alternância principal para efeitos visuais de partículas. |
| `ig:magnet_particle_count` | Integer | `1` | Quantidade de partículas de faísca geradas por entidade a cada tick de partícula. |
| `ig:magnet_max_particle_sources` | Integer | `5` | Quantidade máxima de entidades autorizadas a emitir partículas simultaneamente. |

---

## 🔗 Documentação da Wiki Relacionada
* [[Física de Vácuo & Deslocamento de Fase|pt_br-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Referência Completa de GameRules|pt_br-26.1.2-GameRules]]
* [[Retornar ao Portal do MC 26.1.2|pt_br-26.1.2-Home]]
