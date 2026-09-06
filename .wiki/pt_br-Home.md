# 🧲 Magnet, Let me get that! — Wiki Oficial

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

Boas-vindas à wiki oficial técnica e de jogabilidade do **Magnet, Let me get that!** (`ig_magnet`), um mod de vácuo intrínseco de itens e experiência projetado para versões modernas do Minecraft no Fabric.

Construído solidamente sob a filosofia de design **Instant Gratification (IG)**, este mod elimina a "caminhada da vergonha" — o atrito entediante de ter que andar 5 blocos apenas para pegar um item que você acabou de minerar ou abater. Se você consegue ver o item, ele deve ser seu.

---

## 🧭 Portal Central de Seleção de Versão

Escolha a sua versão do Minecraft para acessar guias de jogabilidade dedicados, documentação técnica, tabelas de GameRules e referências de arquitetura:

| Versão do Minecraft | Status de Lançamento | Compilação da Versão Ativa | Mecanismo de Configuração | Link do Portal |
| :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | 🟢 Moderno Ativo | `1.3.9+26.2` | YACL v3 + ModMenu | [[Visão Geral do MC 26.2|pt_br-26.2-Home]] |
| **Minecraft 26.1.2** | 🟢 Âncora Moderna | `1.1.2+26.1.2` | Cloth Config + ModMenu | [[Visão Geral do MC 26.1.2|pt_br-26.1.2-Home]] |

### 🚀 Portais Diretos de Versão:
* 📦 **Minecraft 26.2**: [[👉 Entrar no Portal de Documentação do Minecraft 26.2|pt_br-26.2-Home]]
* 📦 **Minecraft 26.1.2**: [[👉 Entrar no Portal de Documentação do Minecraft 26.1.2|pt_br-26.1.2-Home]]

Para uma análise detalhada das toolchains, matrizes de dependência, locais de arquivamento e compatibilidade retroativa, consulte [[Matriz de Compatibilidade Multi-Era|pt_br-Version-Compatibility]].

---

## ⚡ Matriz de Recursos Principais

```
                      +-----------------------------+
                      |     EMISSOR DE VÁCUO        |
                      |        DO JOGADOR           |
                      +--------------+--------------+
                                     |
              +----------------------+----------------------+
              |                                             |
              v                                             v
  +-----------------------+                     +-----------------------+
  |    MODO DE PUXAR      |                     |    MODO DE COLETA     |
  |        PADRÃO         |                     |      INSTANTÂNEA      |
  +-----------+-----------+                     +-----------+-----------+
              |                                             |
     [Checagem de Visão (LOS)]                     [Expansão da AABB]
     [Raycast Esférico 360°]                       [Zero Latência de Voo]
     [Deslocamento NoClip]                         [Direto ao Inventário]
     [Velocidade Lerp Dinâmica]                             |
              |                                             |
              +----------------------+----------------------+
                                     |
                                     v
                      +-----------------------------+
                      |   ITEM E ORBE CAPTURADOS    |
                      +-----------------------------+
```

* **Vácuo Inteligente em 360°**: Atrai itens dropados e orbes de experiência dentro de um raio de blocos configurável (padrão: 12 blocos, até 64).
* **Deslocamento de Fase (NoClip)**: Os itens magnetizados atravessam blocos sólidos sem esforço, evitando que os drops fiquem presos permanentemente em escombros de explosões ou fendas de mineração.
* **Detecção de Linha de Visão (LOS)**: Conta com raycasting esférico primário em 360° via `PlayerVisionTracker` da DasikLibrary e filtragem granular opcional contra blocos transparentes (vidro), flora (grama alta, folhas) e entidades de bloco (baús).
* **Continuidade de Momento (`keepMovingIfUnseen`)**: Uma vez magnetizados na linha de visão, os itens mantêm seu momento de atração mesmo se passarem temporariamente atrás de obstáculos.
* **Opção de Coleta Instantânea**: Expande a caixa de colisão de coleta nativa do jogador para absorver itens instantaneamente com zero latência de deslocamento.
* **Controle por Tecla de Atalho e Comando**: Alterne o magnetismo no lado do cliente via tecla de atalho (`\` no 26.2, `Ctrl+M` no 26.1.2) ou no lado do servidor via `/magnet toggle`.
* **Zero Poluição de Inventário**: Funcionalidade 100% intrínseca — não requer itens de ímã personalizados, berloques (baubles) ou baterias de energia.

---

## 📚 Navegação Enciclopédica

### 🎮 Guias para Jogadores & Administradores
* [[Visão Geral do MC 26.2|pt_br-26.2-Home]] & [[Visão Geral do MC 26.1.2|pt_br-26.1.2-Home]]
* [[Física de Vácuo & Deslocamento de Fase no MC 26.2|pt_br-26.2-Vacuum-and-Phase-Shifting]] & [[Física de Vácuo & Deslocamento de Fase no MC 26.1.2|pt_br-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Linha de Visão & Obstruções no MC 26.2|pt_br-26.2-Line-of-Sight-and-Obstruction]] & [[Linha de Visão & Obstruções no MC 26.1.2|pt_br-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Atração de Orbes de XP no MC 26.2|pt_br-26.2-Experience-Orb-Attraction]] & [[Atração de Orbes de XP no MC 26.1.2|pt_br-26.1.2-Experience-Orb-Attraction]]
* [[Modo de Coleta Instantânea no MC 26.2|pt_br-26.2-Instant-Pickup-Mode]] & [[Modo de Coleta Instantânea no MC 26.1.2|pt_br-26.1.2-Instant-Pickup-Mode]]
* [[Alternância & Persistência do Jogador no MC 26.2|pt_br-26.2-Player-Toggle-and-Persistence]] & [[Alternância & Gerenciamento de Estado no MC 26.1.2|pt_br-26.1.2-Player-Toggle-and-Persistence]]
* [[Referência de GameRules do MC 26.2|pt_br-26.2-GameRules]] & [[Referência de GameRules do MC 26.1.2|pt_br-26.1.2-GameRules]]
* [[Comandos do MC 26.2|pt_br-26.2-Commands]] & [[Comandos do Servidor no MC 26.1.2|pt_br-26.1.2-Commands]]
* [[Conquistas do MC 26.2|pt_br-26.2-Advancements]] & [[Conquistas do MC 26.1.2|pt_br-26.1.2-Advancements]]
* [[GUI de Configuração do MC 26.2|pt_br-26.2-Configuration]] & [[GUI de Configuração do MC 26.1.2|pt_br-26.1.2-Configuration]]
* [[HUD & Diagnósticos do MC 26.2|pt_br-26.2-HUD-and-Diagnostics]] & [[HUD & Diagnósticos do MC 26.1.2|pt_br-26.1.2-HUD-and-Diagnostics]]

### 💻 Documentação para Desenvolvedores & Contribuidores
* [[Configuração do Desenvolvedor no MC 26.2|pt_br-26.2-Developer-Setup-and-Building]] & [[Configuração do Desenvolvedor no MC 26.1.2|pt_br-26.1.2-Developer-Setup-and-Building]]
* [[Arquitetura & Alvos de Mixin no MC 26.2|pt_br-26.2-Architecture-and-Mixins]] & [[Arquitetura & Alvos de Mixin no MC 26.1.2|pt_br-26.1.2-Architecture-and-Mixins]]
* [[Integração de API & Addons no MC 26.2|pt_br-26.2-API-and-Addon-Integration]] & [[Integração de API & Addons no MC 26.1.2|pt_br-26.1.2-API-and-Addon-Integration]]
* [[Matriz de Compatibilidade Multi-Era|pt_br-Version-Compatibility]]

---

## ⚖️ Licença & Atribuição

Desenvolvido por **Dasik (Rifaditya)** sob a licença **GNU General Public License v3.0 (GPLv3)**. Consulte `LICENSE` para obter os termos e permissões legais completos.
