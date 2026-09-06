# 🔌 Integração de API & Addons (MC 26.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox da API | Parâmetros Técnicos |
| :--- | :--- |
| **Interface do Jogador** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Interface de Entidade** | `net.instantgratification.magnet.IMagnetEntity` |
| **Fachada Central de Movimento** | `net.instantgratification.magnet.MagnetMovement` |
| **API de GameRules** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **API de Visão** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Integração entre Mods para Desenvolvedores

Mods de terceiros, utilitários de servidor e complementos da coleção Instant Gratification podem interagir diretamente com o **Magnet, Let me get that!** para consultar estados de ímã dos jogadores, disparar atrações programáticas ou contornar obstáculos.

---

## 🧑‍💻 Interface de Estado do Jogador (`IMagnetPlayer`)

Faça o cast de qualquer instância de `Player` ou `ServerPlayer` para `IMagnetPlayer` a fim de consultar ou alterar as preferências de ímã:

```java
package net.instantgratification.magnet;

public interface IMagnetPlayer {
    boolean ig_magnet$isMagnetEnabled();
    void ig_magnet$setMagnetEnabled(boolean enabled);
    boolean ig_magnet$toggleMagnet();
}
```

### Exemplo de Uso:
```java
// Verificar se o jogador está com o ímã ativado
if (((IMagnetPlayer) player).ig_magnet$isMagnetEnabled()) {
    // Lógica personalizada do addon...
}

// Desativar o ímã programaticamente (ex.: enquanto sentado em um trono ou em um minijogo)
((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(false);
```

---

## 📦 Interface de Magnetização de Entidades (`IMagnetEntity`)

Faça o cast de qualquer instância de `Entity` (como drops de monstros personalizados, projéteis ou orbes de XP) para `IMagnetEntity`:

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig_magnet$setMagnetNoClip();
    boolean ig_magnet$isMagnetNoClip();
    void ig_magnet$setMagnetized();
    boolean ig_magnet$isMagnetized();
}
```

### Exemplo de Uso:
```java
// Conceder deslocamento de fase (NoClip) temporário de 2 ticks a uma entidade personalizada
((IMagnetEntity) customItem).ig_magnet$setMagnetNoClip();
```

---

## 🚀 Fachada Estática de Movimento (`MagnetMovement.pull`)

Mods complementares podem puxar manualmente entidades em direção a qualquer jogador usando o mecanismo integrado de física e linha de visão:

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

```java
// Puxar entidade alvo para o jogador com rastros de partículas
MagnetMovement.pull(specialDrop, player, true);
```

---

## 🔗 Documentação da Wiki Relacionada
* [[Arquitetura & Alvos de Mixin|pt_br-26.2-Architecture-and-Mixins]]
* [[Configuração do Desenvolvedor & Compilação|pt_br-26.2-Developer-Setup-and-Building]]
* [[Retornar ao Portal do MC 26.2|pt_br-26.2-Home]]
