# 🔄 Alternância do Jogador & Gerenciamento de Estado (MC 26.1.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox do Recurso | Parâmetros Técnicos |
| :--- | :--- |
| **Classe de Armazenamento de Estado** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Tecla de Atalho Padrão do Cliente** | `Ctrl+M` (`GLFW.GLFW_KEY_M` + `isControlDown()`) |
| **Categoria da Tecla** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Payload de Rede** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **API da Actionbar** | `client.gui.setOverlayMessage(Component, boolean)` |

---

## 📖 Visão Geral da Arquitetura de Estado

No Minecraft 26.1.2, as preferências de alternância do jogador são gerenciadas durante a sessão ativa do servidor via `MagnetPlayerState` utilizando um `ConcurrentHashMap` thread-safe:

```java
public class MagnetPlayerState {
    private static final Map<UUID, Boolean> playerStates = new ConcurrentHashMap<>();

    public static boolean isMagnetEnabled(Player player) {
        return playerStates.getOrDefault(player.getUUID(), true);
    }

    public static void setMagnetEnabled(Player player, boolean enabled) {
        playerStates.put(player.getUUID(), enabled);
    }

    public static boolean toggleMagnet(Player player) {
        boolean newState = !isMagnetEnabled(player);
        setMagnetEnabled(player, newState);
        return newState;
    }
}
```

---

## ⌨️ Atalho do Cliente (`Ctrl+M`)

* **Combinação Padrão**: `Ctrl+M` (`GLFW_KEY_M` + `isControlDown()`), com suporte multiplataforma para a tecla Command no macOS (`GLFW_KEY_LEFT_SUPER`).
* **Feedback Visual na Actionbar**:
  - `chat.ig_magnet.enabled`: `"Item Magnet: Enabled"`
  - `chat.ig_magnet.disabled`: `"Item Magnet: Disabled"`

---

## 📡 Protocolo de Sincronização de Rede

```
[CLIENTE]                                                          [SERVIDOR]
Jogador pressiona Ctrl+M
MagnetPlayerState.setMagnetEnabled(player, newState)
ClientPlayNetworking.send(new MagnetTogglePayload(newState))
                                               ------------------> Receptor do Servidor
                                                                   MagnetPlayerState.setMagnetEnabled(player, payload.enabled())
```

---

## 🔗 Documentação da Wiki Relacionada
* [[Comandos do Servidor & Alternâncias|pt_br-26.1.2-Commands]]
* [[Arquitetura & Mixins|pt_br-26.1.2-Architecture-and-Mixins]]
* [[Retornar ao Portal do MC 26.1.2|pt_br-26.1.2-Home]]
