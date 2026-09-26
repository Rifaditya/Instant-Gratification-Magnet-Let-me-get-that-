# 📊 HUD, Visuais & Overlay (MC 26.1.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox Visual | Parâmetros Técnicos |
| :--- | :--- |
| **API da Actionbar** | `client.gui.setOverlayMessage(Component, boolean)` |
| **Tipo de Partícula** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Módulo de Controle de Partículas** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **Limite de Fontes de Partículas** | `ig:magnet_max_particle_sources` (Padrão: `5`) |

---

## 🖥️ Feedback em Overlay na Actionbar

Quando o jogador alterna o ímã de itens através da combinação de teclas `Ctrl+M`, a interface do cliente exibe imediatamente uma notificação na barra de ações:

* **Ativado**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Desativado**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Verificado em: Gui.java (26.1.2)
if (newState) {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Rastros Visuais de Faíscas Elétricas

Itens e orbes de experiência que estão sendo puxados emitem rastros de partículas de faísca:

```
[Item / XP Puxado]  --->  ✨  --->  ✨  --->  ✨  --->  [Posição dos Olhos do Jogador]
```

* **Restrição de Fontes**: No máximo 5 fontes simultâneas emitem partículas (`ig:magnet_max_particle_sources = 5`).
* **Restrição de Ticks**: As partículas são geradas a cada 4 ticks ($5\text{ vezes/segundo}$).
* **Controle de Densidade**: `ig:magnet_particle_count = 1`.

---

## 🔗 Documentação da Wiki Relacionada
* [[Alternância do Jogador & Gerenciamento de Estado|pt_br-26.1.2-Player-Toggle-and-Persistence]]
* [[Atração de Orbes de Experiência|pt_br-26.1.2-Experience-Orb-Attraction]]
* [[Retornar ao Portal do MC 26.1.2|pt_br-26.1.2-Home]]
