# 🗺️ Matriz de Compatibilidade de Versões Multi-Era

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

Esta página documenta os lançamentos suportados do Minecraft, ambientes de execução Java, dependências do Fabric Loader e cadeias de compilação (toolchains) para o **Magnet, Let me get that!** (`ig_magnet`).

---

## 📊 Visão Geral do Ciclo de Vida Multi-Versão

| Minecraft Alvo | Pasta do Subprojeto | Versão Ativa do Mod | Versão Alvo do Java | Fabric Loader | Fabric API | DasikLibrary | Provedor de GUI de Config |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | `Magnet v26.2/magnet` | `1.3.9+26.2` | Java 25 (`release = 25`) | `>=0.19.1` | `0.150.1+26.2` | `1.8.23` | YetAnotherConfigLib v3 (`3.9.5+26.2-fabric`) + ModMenu |
| **Minecraft 26.1.2** | `Magnet v26.1/magnet` | `1.1.2+26.1.2` | Java 25 (`release = 25`) | `>=0.16.10` | `0.145.4+26.1.2` | `1.8.23` | Cloth Config Fabric (`26.1.154`) + ModMenu |

---

## 🔍 Detalhamento das Versões

### 🟢 Minecraft 26.2 (`1.3.9+26.2`)
* **Status**: Lançamento Moderno Principal
* **Caminho do Subprojeto**: `Magnet v26.2/magnet/`
* **Saída de Arquivos (JAR)**: `Magnet-Let-me-get-that-1.3.9+26.2.jar`
* **Diretório Central de Arquivamento**: `Archive Jar of all versions/MC 26.2/`
* **Limites de Dependência (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.19.1",
      "minecraft": ">=26.2-",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **Principais Recursos Arquiteturais**:
  * Armazenamento persistente em NBT usando codecs `ValueOutput` e `ValueInput` do Minecraft 26.2 em `PlayerMixin`.
  * Retenção automática de estado do jogador após a morte e teletransporte de dimensão através dos eventos de ciclo de vida do Fabric `ServerPlayerEvents.COPY_FROM` e `ServerPlayerEvents.AFTER_RESPAWN`.
  * Tecla de atalho mapeada para `\` (`GLFW_KEY_BACKSLASH`) com fallback dinâmico via `ig_magnet$getKeyboardType()`.
  * Comandos de diagnóstico no jogo `/magnet debug` e `/magnet debug log` com registro dedicado em arquivo (`logs/ig_magnet_debug.log`).
  * `YaclScreenHelper` moderno utilizando YetAnotherConfigLib v3.

---

### 🟢 Minecraft 26.1.2 (`1.1.2+26.1.2`)
* **Status**: Subprojeto Âncora Moderno
* **Caminho do Subprojeto**: `Magnet v26.1/magnet/`
* **Saída de Arquivos (JAR)**: `Magnet-Let-me-get-that-1.1.2+26.1.2.jar`
* **Diretório Central de Arquivamento**: `Archive Jar of all versions/MC 26.1.2/`
* **Limites de Dependência (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.16.10",
      "minecraft": "*",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **Principais Recursos Arquiteturais**:
  * Rastreamento de estado de sessão concorrente via `MagnetPlayerState` (`Map<UUID, Boolean>`).
  * Tecla de atalho mapeada para `Ctrl+M` (`GLFW_KEY_M` + `isControlDown()`).
  * Feedback nativo em overlay na barra de ações (actionbar) via `client.gui.setOverlayMessage(...)`.
  * GUI de configuração opcional segura contra falhas de reflexão via `ClothConfigScreenHelper`.

---

## 📦 Arquivamento Automático de Lançamentos & Implantação no Inicializador

Ambos os subprojetos integram tarefas automáticas de arquivamento pós-compilação em seus scripts de compilação Gradle (`build.gradle`):

```bash
# Compilar e arquivar automaticamente a versão para MC 26.2:
cd "Magnet v26.2/magnet"
./gradlew build --no-daemon

# Compilar e arquivar automaticamente a versão para MC 26.1.2:
cd "Magnet v26.1/magnet"
./gradlew build --no-daemon
```

Quando o `./gradlew build` é executado, a tarefa `archiveReleaseJar` copia automaticamente o JAR compilado para a pasta central de arquivos (`Archive Jar of all versions/MC <Version>/`) e o sincroniza com os perfis de teste de inicializadores locais do Modrinth ativos.

---

## 🔗 Documentação da Wiki Relacionada
* [[Configuração & Ferramentas do MC 26.2|pt_br-26.2-Developer-Setup-and-Building]]
* [[Configuração & Ferramentas do MC 26.1.2|pt_br-26.1.2-Developer-Setup-and-Building]]
* [[Retornar ao Portal Central|pt_br-Home]]
