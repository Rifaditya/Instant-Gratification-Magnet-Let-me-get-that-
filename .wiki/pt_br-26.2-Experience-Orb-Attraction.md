# ✨ Atração de Orbes de Experiência (MC 26.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox do Recurso | Parâmetros Técnicos |
| :--- | :--- |
| **Classe da Entidade Alvo** | `net.minecraft.world.entity.ExperienceOrb` |
| **Classe Gerenciadora** | `net.instantgratification.magnet.MagnetManager` |
| **GameRule de Habilitação** | `ig:magnet_affects_xp` (Padrão: `true`) |
| **Consulta de Varredura** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **Tipo de Partícula** | `ParticleTypes.ELECTRIC_SPARK` |
| **Controle de Fluxo de Partículas** | Compartilhado com itens via `ig:magnet_max_particle_sources` |

---

## 📖 Mecânicas de Vácuo de Experiência

Sob a filosofia Instant Gratification, deixar orbes de experiência espalhados pelo chão ou presos nos tetos de cavernas quebra a fluidez da jogabilidade. O **Magnet, Let me get that!** fornece suporte de primeira classe para a atração de entidades `ExperienceOrb` junto com itens dropados.

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

Quando `ig:magnet_affects_xp` está habilitado, todos os orbes de experiência dentro do alcance herdam exatamente as mesmas mecânicas avançadas dos itens dropados:

1. **Deslocamento de Fase (NoClip)**: Os orbes de experiência atravessam blocos sólidos quando puxados, impedindo que orbitem ou colidam infinitamente contra paredes.
2. **Aceleração Dinâmica**: Os orbes de experiência seguem os mesmos cálculos de interpolação lerp de velocidade ($s = \text{speed}/100.0$) e aceleração ($a = \text{accel}/100.0$).
3. **Filtragem por Linha de Visão**: Se `ig:magnet_los_only` for verdadeiro, os orbes de XP devem passar tanto pelo raycast primário em 360° quanto pelas checagens granulares secundárias.

---

## 🛡️ Desempenho & Prevenção de Lag (Limites de Partículas)

Farms de monstros de alta densidade ou batalhas contra o Dragão do Ender podem gerar centenas de orbes de experiência simultaneamente. Emitir partículas em cada orbe a cada tick causaria quedas severas de FPS no cliente.

O mod previne o lag visual através do **Controle Global de Fontes de Partículas**:

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

* **Limite Global**: Apenas as primeiras $N$ entidades (configuradas por `ig:magnet_max_particle_sources`, padrão: `5`) têm permissão para emitir faíscas em um determinado tick.
* **Escalonamento por Tick**: As partículas só são emitidas quando `(entity.tickCount + entity.getId()) % 4 == 0` (a cada 4 ticks = 5 vezes por segundo).

---

## ⚙️ Configurações Relevantes & GameRules

| GameRule | Tipo | Padrão | Unidade / Intervalo | Descrição |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | Define se os orbes de experiência são atraídos pelo ímã. |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | Alternância principal para rastros visuais de faíscas elétricas. |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | Quantidade de faíscas emitidas por fonte ativa. |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | Número máximo de entidades simultâneas autorizadas a emitir partículas. |

---

## 🔗 Documentação da Wiki Relacionada
* [[Física de Vácuo & Deslocamento de Fase|pt_br-26.2-Vacuum-and-Phase-Shifting]]
* [[Referência Completa de GameRules|pt_br-26.2-GameRules]]
* [[HUD & Diagnósticos Visuais|pt_br-26.2-HUD-and-Diagnostics]]
* [[Retornar ao Portal do MC 26.2|pt_br-26.2-Home]]
