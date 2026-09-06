# 📊 HUD, Visuais & Diagnósticos (MC 26.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox Visual | Parâmetros Técnicos |
| :--- | :--- |
| **API da Actionbar** | `client.gui.hud.setOverlayMessage(Component, boolean)` |
| **Tipo de Partícula** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Módulo de Controle de Partículas** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **Arquivo de Log de Depuração** | `logs/ig_magnet_debug.log` |
| **Classe do Logger** | `net.instantgratification.magnet.MagnetDebugLogger` |

---

## 🖥️ Feedback em Overlay na Actionbar

Quando o jogador alterna o estado do ímã pela tecla de atalho (`\`) ou pelo comando no servidor (`/magnet toggle`), o HUD do cliente renderiza imediatamente uma notificação discreta na barra de ações acima da barra rápida:

* **Mensagem de Ativado**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Mensagem de Desativado**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Verificado em: Hud.java (26.2+)
if (newState) {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Rastros Visuais de Faíscas Elétricas

Enquanto itens e orbes de XP são ativamente atraídos pelo ar, eles emitem rastros visuais sutis de `ParticleTypes.ELECTRIC_SPARK`:

```
[Item Puxado]  --->  ✨  --->  ✨  --->  ✨  --->  [Posição dos Olhos do Jogador]
```

### Regras de Controle de Fluxo de Partículas:
1. **Limite de Fontes**: Governado por `ig:magnet_max_particle_sources` (padrão: `5`), garantindo que grandes pilhas de minérios não causem sobrecarga de partículas.
2. **Escalonamento de Frequência**: Apenas 1 a cada 4 ticks gera partículas para uma entidade individual, distribuído pelos IDs únicos das entidades: `(entity.tickCount + entity.getId()) % 4 == 0`.
3. **Controle de Quantidade**: Controlado por fonte através de `ig:magnet_particle_count` (padrão: `1`).

---

## 📝 Gravador de Log de Depuração Dedicado (`MagnetDebugLogger`)

Para administradores de servidores e criadores de modpacks depurando limites de linha de visão ou pacotes de rede, o mod inclui um logger assíncrono thread-safe que grava em `logs/ig_magnet_debug.log`:

* **Ativação**: Execute `/magnet debug log` no jogo.
* **Formato**: `[yyyy-MM-dd HH:mm:ss.SSS] [Context] Message`
* **Exemplo de Log**:
  ```text
  [2026-08-22 21:50:00.124] MagnetMod: Server received packet from Alex (e7b1a234-...) enabled=true
  [2026-08-22 21:50:00.130] MagnetMovement: Entity 592 is now magnetized by Alex (e7b1a234-...).
  [2026-08-22 21:50:01.450] PlayerMixin: Server tick for Alex (e7b1a234-...) isMagnetEnabled=true
  ```

---

## 🔗 Documentação da Wiki Relacionada
* [[Comandos Brigadier & Diagnósticos no Jogo|pt_br-26.2-Commands]]
* [[Atração de Orbes de Experiência|pt_br-26.2-Experience-Orb-Attraction]]
* [[Retornar ao Portal do MC 26.2|pt_br-26.2-Home]]
