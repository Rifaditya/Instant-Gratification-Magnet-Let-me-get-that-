# 💻 Comandos Brigadier & Diagnósticos no Jogo (MC 26.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox do Recurso | Parâmetros Técnicos |
| :--- | :--- |
| **Classe do Comando** | `net.instantgratification.magnet.MagnetCommand` |
| **Literais Principais** | `/magnet` e `/ig_magnet` (Espelhos de apelidos idênticos) |
| **Callback de Registro** | `CommandRegistrationCallback.EVENT` |
| **Arquivo de Saída de Log** | `logs/ig_magnet_debug.log` |
| **Permissões Alvo** | Disponível para todos os jogadores (`toggle`) e Nível OP 2 (`debug`) |

---

## 📖 Estrutura da Árvore de Comandos

```
/magnet (ou /ig_magnet)
  ├── toggle
  └── debug
        └── log
```

---

## 🛠️ Referência de Subcomandos

### 1. `/magnet toggle` (ou `/ig_magnet toggle`)
Alterna o estado do ímã pessoal do jogador executor entre ativado e desativado.

* **Uso**: `/magnet toggle`
* **Execução**: Chama `((IMagnetPlayer) player).ig_magnet$toggleMagnet()`.
* **Mensagem de Retorno**:
  - Se ativado: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
  - Se desativado: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)
* **Caso de Uso**: Permite que jogadores conectando-se a partir de clientes vanilla (em servidores puramente server-side) ou jogadores sem tecla configurada alternem seu ímã de itens.

---

### 2. `/magnet debug` (ou `/ig_magnet debug`)
Executa uma varredura diagnóstica imediata no mundo ao redor do jogador executor e entidades em um raio de 10 blocos.

* **Uso**: `/magnet debug`
* **Informações de Saída**:
  ```text
  §e--- Magnet Debug Info ---
  Player Name: Steve
  Player UUID: c0b59055-ec50-4653-979d-c3c92d6ca897
  Global Master Toggle: true
  Player Toggle State: true
  Range: 12
  Instant Pickup: false
  LOS Only: true
  Spectator: false
  Dead/Dying: false
  Debug File Logger: DISABLED (Toggle with /magnet debug log)
  Nearby Items (10 blocks): 3
  Nearest Item ID: 412
  PlayerVisionTracker.canSee: true
  Vanilla Raycast canSee: true
  §e-------------------------
  ```
* **Caso de Uso**: Diagnostica o motivo de um item não estar sendo puxado (ex.: obstrução estrita de linha de visão, estado de morte do jogador ou substituição por GameRule global).

---

### 3. `/magnet debug log` (ou `/ig_magnet debug log`)
Alterna a gravação de logs de diagnóstico detalhados persistentes no disco.

* **Uso**: `/magnet debug log`
* **Execução**: Inverte `MagnetDebugLogger.enabled = !MagnetDebugLogger.enabled`.
* **Saída**: Grava eventos de ticks com carimbo de data/hora, recepção de pacotes, sincronizações de renascimento e rejeições de linha de visão em `logs/ig_magnet_debug.log`.
* **Exemplo de Entrada no Log**:
  ```log
  [2026-08-22 21:49:15.102] MagnetMod: Server received packet from Steve (c0b59055-...) enabled=true
  [2026-08-22 21:49:15.105] MagnetMovement: Entity 412 is now magnetized by Steve (c0b59055-...).
  ```

---

## 🔗 Documentação da Wiki Relacionada
* [[Alternância do Jogador, Persistência & Ciclo de Vida|pt_br-26.2-Player-Toggle-and-Persistence]]
* [[Sistema de HUD & Diagnósticos|pt_br-26.2-HUD-and-Diagnostics]]
* [[Referência Completa de GameRules|pt_br-26.2-GameRules]]
* [[Retornar ao Portal do MC 26.2|pt_br-26.2-Home]]
