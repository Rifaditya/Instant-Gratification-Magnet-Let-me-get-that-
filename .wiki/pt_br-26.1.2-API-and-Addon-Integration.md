# 🔌 Integração de API & Addons (MC 26.1.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox da API | Parâmetros Técnicos |
| :--- | :--- |
| **Gerenciador de Estado do Jogador** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Interface de Entidade** | `net.instantgratification.magnet.IMagnetEntity` |
| **Fachada Central de Movimento** | `net.instantgratification.magnet.MagnetMovement` |
| **API de GameRules** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **API de Visão** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Integração entre Mods para Desenvolvedores

Mods de terceiros e complementos da coleção Instant Gratification podem interagir diretamente com o **Magnet, Let me get that!** no Minecraft 26.1.2.

---

## 🧑‍💻 Gerenciamento de Estado do Jogador (`MagnetPlayerState`)

Consulte ou modifique as preferências de ímã do jogador diretamente através de métodos estáticos auxiliares:

```java
package net.instantgratification.magnet;

public class MagnetPlayerState {
    public static boolean isMagnetEnabled(Player player);
    public static void setMagnetEnabled(Player player, boolean enabled);
    public static boolean toggleMagnet(Player player);
}
```

### Exemplo de Uso:
```java
// Verificar se o jogador está com o ímã ativado
if (MagnetPlayerState.isMagnetEnabled(player)) {
    // Lógica personalizada...
}

// Desativar o ímã programaticamente
MagnetPlayerState.setMagnetEnabled(player, false);
```

---

## 📦 Interface de Magnetização de Entidades (`IMagnetEntity`)

Faça o cast de qualquer instância de `Entity` para `IMagnetEntity` a fim de manipular suas flags de deslocamento de fase ou magnetização:

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig$setMagnetNoClip();
    boolean ig$isMagnetNoClip();
    void ig$setMagnetized();
    boolean ig$isMagnetized();
}
```

---

## 🚀 Fachada Estática de Movimento (`MagnetMovement.pull`)

Dispare programaticamente a atração de itens ou XP em direção a um jogador:

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

---

## 🔗 Documentação da Wiki Relacionada
* [[Arquitetura & Alvos de Mixin|pt_br-26.1.2-Architecture-and-Mixins]]
* [[Configuração do Desenvolvedor & Compilação|pt_br-26.1.2-Developer-Setup-and-Building]]
* [[Retornar ao Portal do MC 26.1.2|pt_br-26.1.2-Home]]
