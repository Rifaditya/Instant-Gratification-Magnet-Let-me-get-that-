# 🧲 Magnet, Let me get that! — Portal do Minecraft 26.1.2

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

Boas-vindas ao portal de documentação do **Minecraft 26.1.2** para o **Magnet, Let me get that!** (Compilação `1.1.2+26.1.2`).

Esta edição âncora fornece a experiência completa de vácuo de itens e XP da coleção Instant Gratification com integração ao Cloth Config, gerenciamento de estado de sessão concorrente e raycasting esférico em 360°.

---

## 📋 Especificações Rápidas do Minecraft 26.1.2

| Especificação | Valor Alvo | Identificador de Referência |
| :--- | :--- | :--- |
| **Versão Alvo do Minecraft** | `26.1.2` | `"minecraft": "*"` |
| **Compilação Ativa do Subprojeto** | `1.1.2+26.1.2` | `mod_version=1.1.2+26.1.2` |
| **Cadeia de Ferramentas Java** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.16.10` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` | `fabric_version=0.145.4+26.1.2` |
| **Biblioteca Compartilhada Central** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **Tecla de Atalho Padrão do Cliente** | `Ctrl+M` | `GLFW.GLFW_KEY_M` + `isControlDown()` |
| **GUI de Configuração** | Cloth Config Fabric | `ClothConfigScreenHelper` + ModMenu |
| **Comandos do Servidor** | `/magnet toggle`, `/ig_magnet toggle` | `MagnetCommand.java` |

---

## ⚡ Destaques dos Recursos Principais

* **Checagens de Linha de Visão Esféricas em 360°**: Alimentadas pelo `PlayerVisionTracker` da DasikLibrary 1.8.23. Consulte [[Linha de Visão & Penetração de Obstáculos|pt_br-26.1.2-Line-of-Sight-and-Obstruction]].
* **Física NoClip com Deslocamento de Fase**: Os itens atravessam terrenos sólidos durante a atração pelo vácuo. Consulte [[Física de Vácuo & Deslocamento de Fase|pt_br-26.1.2-Vacuum-and-Phase-Shifting]].
* **Modo de Coleta Instantânea**: Expansão opcional da caixa de colisão para coleta com latência zero. Consulte [[Modo de Coleta Instantânea & Expansão de Bounding Box|pt_br-26.1.2-Instant-Pickup-Mode]].
* **Alternâncias de Sessão Concorrentes**: Estado de atalho de teclado e comando gerenciado via `MagnetPlayerState`. Consulte [[Alternância do Jogador, Tecla de Atalho & Armazenamento de Estado|pt_br-26.1.2-Player-Toggle-and-Persistence]].
* **GUI do Cloth Config**: Tela de configurações intuitiva no jogo com avisos de categorias. Consulte [[GUI de Configuração Cloth Config & ModMenu|pt_br-26.1.2-Configuration]].

---

## 📑 Índice de Documentação do 26.1.2

### 🎮 Jogabilidade & Administração
* [[Física de Vácuo & Deslocamento de Fase|pt_br-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Linha de Visão & Penetração de Obstáculos|pt_br-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Sincronização de Orbes de Experiência|pt_br-26.1.2-Experience-Orb-Attraction]]
* [[Modo de Coleta Instantânea & Expansão de Bounding Box|pt_br-26.1.2-Instant-Pickup-Mode]]
* [[Alternância do Jogador, Tecla de Atalho & Armazenamento de Estado|pt_br-26.1.2-Player-Toggle-and-Persistence]]
* [[Referência de GameRules & Limites Padrão|pt_br-26.1.2-GameRules]]
* [[Comandos do Servidor & Suporte a Clientes Vanilla|pt_br-26.1.2-Commands]]
* [[Conquistas & Dependência do Vanilla|pt_br-26.1.2-Advancements]]
* [[GUI de Configuração Cloth Config & ModMenu|pt_br-26.1.2-Configuration]]
* [[HUD da Actionbar & Efeitos Visuais|pt_br-26.1.2-HUD-and-Diagnostics]]

### 💻 Referência para Desenvolvedores & Engenharia
* [[Configuração do Desenvolvedor, Toolchains & Gradle Loom|pt_br-26.1.2-Developer-Setup-and-Building]]
* [[Arquitetura, Pacotes & Injeções de Mixin|pt_br-26.1.2-Architecture-and-Mixins]]
* [[Fachadas de API, Interfaces & Ganchos para Addons|pt_br-26.1.2-API-and-Addon-Integration]]
* [[Retornar à Matriz de Compatibilidade Multi-Era|pt_br-Version-Compatibility]]
