# 🧲 Magnet, Let me get that! — Portal do Minecraft 26.2

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

Boas-vindas ao portal de documentação do **Minecraft 26.2** para o **Magnet, Let me get that!** (Compilação `1.3.9+26.2`).

Esta edição moderna traz retenção de estado persistente em NBT, sincronização de respawn no ciclo de vida do Fabric, raycasting esférico em 360° através da DasikLibrary 1.8.23 e interface de configuração gráfica YetAnotherConfigLib v3 (YACL).

---

## 📋 Especificações Rápidas do Minecraft 26.2

| Especificação | Valor Alvo | Identificador de Referência |
| :--- | :--- | :--- |
| **Versão Alvo do Minecraft** | `26.2` | `"minecraft": ">=26.2-"` |
| **Compilação Ativa do Subprojeto** | `1.3.9+26.2` | `mod_version=1.3.9+26.2` |
| **Cadeia de Ferramentas Java** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.19.1` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.150.1+26.2` | `fabric_version=0.150.1+26.2` |
| **Biblioteca Compartilhada Central** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **Tecla de Atalho Padrão do Cliente** | `\` (Barra Invertida) | `GLFW.GLFW_KEY_BACKSLASH` |
| **GUI de Configuração** | YetAnotherConfigLib v3 | `YaclScreenHelper` + ModMenu |
| **Comandos de Diagnóstico** | `/magnet toggle`, `/magnet debug`, `/magnet debug log` | `MagnetCommand.java` |

---

## ⚡ Destaques dos Recursos Principais

* **Checagem de Linha de Visão Esférica em 360°**: Impede a atração indevida de itens através de paredes sólidas enquanto garante atração com visibilidade total em todos os ângulos ao redor do jogador. Consulte [[Linha de Visão & Penetração de Obstáculos|pt_br-26.2-Line-of-Sight-and-Obstruction]].
* **Física NoClip com Deslocamento de Fase**: Os itens magnetizados atravessam blocos com fluidez para alcançar o nível dos olhos do jogador sem ficarem presos. Consulte [[Física de Vácuo & Deslocamento de Fase|pt_br-26.2-Vacuum-and-Phase-Shifting]].
* **Coleta Instantânea com Latência Zero**: A expansão opcional da caixa de colisão absorve instantaneamente itens dropados para o inventário com zero atraso de física de voo. Consulte [[Modo de Coleta Instantânea|pt_br-26.2-Instant-Pickup-Mode]].
* **Estado Persistente do Jogador**: As preferências do jogador sobrevivem a mortes, renascimentos (respawns), mudanças de dimensão e reinicializações de servidor via NBT (`ValueOutput`/`ValueInput`) e `ServerPlayerEvents.COPY_FROM`. Consulte [[Alternância do Jogador, Persistência & Ciclo de Vida|pt_br-26.2-Player-Toggle-and-Persistence]].
* **Suíte de Comandos de Diagnóstico**: Ferramentas de diagnóstico integradas em `/magnet debug` e `/magnet debug log` para administradores de servidores. Consulte [[Comandos Brigadier & Diagnósticos no Jogo|pt_br-26.2-Commands]] e [[HUD, Visuais & Diagnósticos|pt_br-26.2-HUD-and-Diagnostics]].

---

## 📑 Índice de Documentação do 26.2

### 🎮 Jogabilidade & Administração
* [[Física de Vácuo & Deslocamento de Fase|pt_br-26.2-Vacuum-and-Phase-Shifting]]
* [[Linha de Visão & Penetração de Obstáculos|pt_br-26.2-Line-of-Sight-and-Obstruction]]
* [[Sincronização de Orbes de Experiência|pt_br-26.2-Experience-Orb-Attraction]]
* [[Modo de Coleta Instantânea & Caixa AABB|pt_br-26.2-Instant-Pickup-Mode]]
* [[Alternância do Jogador, Persistência & Ciclo de Vida|pt_br-26.2-Player-Toggle-and-Persistence]]
* [[Referência de GameRules & Limites Padrão|pt_br-26.2-GameRules]]
* [[Comandos Brigadier & Diagnósticos no Jogo|pt_br-26.2-Commands]]
* [[Conquistas & Dependência do Vanilla|pt_br-26.2-Advancements]]
* [[GUI de Configuração YACL & ModMenu|pt_br-26.2-Configuration]]
* [[HUD da Actionbar & Registro Dedicado de Logs|pt_br-26.2-HUD-and-Diagnostics]]

### 💻 Referência para Desenvolvedores & Engenharia
* [[Configuração do Desenvolvedor, Toolchains & Gradle Loom|pt_br-26.2-Developer-Setup-and-Building]]
* [[Arquitetura, Pacotes & Injeções de Mixin|pt_br-26.2-Architecture-and-Mixins]]
* [[Fachadas de API, Interfaces & Ganchos para Addons|pt_br-26.2-API-and-Addon-Integration]]
* [[Retornar à Matriz de Compatibilidade Multi-Era|pt_br-Version-Compatibility]]
