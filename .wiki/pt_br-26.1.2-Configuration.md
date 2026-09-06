# 🎨 GUI de Configuração Cloth Config & ModMenu (MC 26.1.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox de Configuração | Detalhes |
| :--- | :--- |
| **Caminho do Arquivo de Config** | `config/ig_magnet.json` |
| **Biblioteca de GUI** | Cloth Config Fabric (`me.shedaniel.cloth:cloth-config-fabric:26.1.154`) |
| **Ponto de Entrada do ModMenu** | `net.instantgratification.magnet.config.ModMenuIntegration` |
| **Auxiliar de Tela de GUI** | `net.instantgratification.magnet.config.ClothConfigScreenHelper` |
| **Segurança no Carregamento de Classes** | Isolado via `GuiHelper.getOptionalFactory` |

---

## 📖 Arquitetura de Configuração

No Minecraft 26.1.2, o **Magnet, Let me get that!** integra-se com o **Cloth Config Fabric** e o **ModMenu** para fornecer uma tela de configurações gráficas no jogo.

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
                "ig_magnet",
                "net.instantgratification.magnet.config.ClothConfigScreenHelper",
                "createFactory"
        );
    }
}
```

---

## ⚠️ Aviso de Precedência de Configuração

> ⚠️ **Aviso Importante**:  
> Alterações realizadas na interface do ModMenu ou no arquivo `config/ig_magnet.json` **afetam apenas os valores padrão iniciais para NOVOS mundos**.  
> Para alterar as configurações de um mundo ativo já existente, utilize as [[Referência de GameRules|pt_br-26.1.2-GameRules]] no jogo através do comando `/gamerule` ou pela tela nativa de edição de GameRules.

---

## 🗂️ Categorias & Opções

```
Tela do Cloth Config ("Magnet, Let me get that! Configuration")
  ├── Configurações Gerais
  │     ├── Magnet Enabled (Padrão: true)
  │     ├── Magnet Range (Padrão: 12, Intervalo: 1..64)
  │     ├── Instant Pickup (Padrão: false)
  │     └── Magnet Noclip (Padrão: true)
  ├── Velocidades & Heurísticas de Puxar
  │     ├── Item Speed (Padrão: 80, Intervalo: 1..1000)
  │     └── Item Acceleration (Padrão: 10, Intervalo: 1..1000)
  ├── Linha de Visão (LOS)
  │     ├── Line of Sight Only (Padrão: true)
  │     ├── Keep Moving if Unseen (Padrão: true)
  │     ├── Blocked by Transparent (Padrão: false)
  │     ├── Blocked by Flora (Padrão: false)
  │     └── Blocked by Block Entities (Padrão: false)
  └── Visuais & Desempenho
        ├── Attract XP Orbs (Padrão: true)
        ├── Magnet Particles (Padrão: true)
        ├── Particle Count (Padrão: 1, Intervalo: 0..100)
        └── Max Particle Sources (Padrão: 5, Intervalo: 0..100)
```

---

## 📄 Estrutura JSON Bruta (`config/ig_magnet.json`)

```json
{
  "configVersion": 1,
  "enabled": true,
  "range": 12,
  "noClip": true,
  "affectsXp": true,
  "particles": true,
  "particleCount": 1,
  "maxParticleSources": 5,
  "speed": 80,
  "acceleration": 10,
  "instant": false,
  "losOnly": true,
  "keepMovingIfUnseen": true,
  "blockedByTransparent": false,
  "blockedByFlora": false,
  "blockedByBlockEntities": false
}
```

---

## 🔗 Documentação da Wiki Relacionada
* [[Referência Completa de GameRules|pt_br-26.1.2-GameRules]]
* [[Configuração do Desenvolvedor & Compilação|pt_br-26.1.2-Developer-Setup-and-Building]]
* [[Retornar ao Portal do MC 26.1.2|pt_br-26.1.2-Home]]
