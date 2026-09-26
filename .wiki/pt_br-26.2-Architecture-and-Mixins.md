# 🧩 Arquitetura & Alvos de Mixin (MC 26.2)

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ainda não lançados ou recursos em desenvolvimento antes das compilações públicas oficiais no CurseForge e Modrinth.

| Infobox de Arquitetura | Parâmetros Técnicos |
| :--- | :--- |
| **Pacote Raiz** | `net.instantgratification.magnet` |
| **Configuração do Mixin** | `src/main/resources/magnet.mixins.json` |
| **Access Widener** | `src/main/resources/magnet.accesswidener` |
| **Nível de Compatibilidade** | `JAVA_25` |
| **Total de Classes Mixin** | `2` (`MixinEntity`, `PlayerMixin`) |

---

## 🌳 Árvore de Arquitetura de Pacotes

```text
net.instantgratification.magnet/
├── IMagnetEntity.java                  # Interface de entidade para flags de NoClip e magnetização
├── IMagnetPlayer.java                  # Interface de jogador para estado de alternância e getters/setters
├── MagnetCommand.java                  # Árvores de comandos Brigadier (/magnet toggle, /magnet debug)
├── MagnetDebugLogger.java              # Gravação thread-safe de logs detalhados persistentes em disco
├── MagnetManager.java                  # Loop de varredura espacial e execução de entidades
├── MagnetMod.java                      # Ponto de entrada do mod servidor/universal e receptores de pacotes
├── MagnetModClient.java                # Inicializador do cliente, tecla de atalho e avisos na actionbar
├── MagnetMovement.java                 # Matemática vetorial de trajetória, velocidade lerp e partículas
├── MagnetTogglePayload.java            # Registro de pacote de rede e composição com StreamCodec
├── SecondaryVisionCheck.java           # Raycasting granular de blocos (Flora, Entidades de Bloco, Vidro)
├── config/
│   ├── MagnetConfig.java               # Armazenamento da configuração JSON e campos POJO
│   ├── ModMenuIntegration.java         # Ponto de entrada seguro contra reflexão da API ModMenu
│   └── YaclScreenHelper.java           # Construtor da interface YetAnotherConfigLib v3
├── mixin/
│   ├── MixinEntity.java                # Injeta NoClip e cancelamento de gravidade em Entity
│   └── PlayerMixin.java                # Injeta persistência NBT e coleta instantânea em Player
├── registry/
│   └── ModGameRules.java               # Registros no DynamicGameRuleManager
└── util/
    └── ModVersionGuard.java            # Verificação de sanidade do Knot ClassLoader em tempo de execução
```

---

## 📋 Detalhamento dos Alvos de Injeção de Mixin

### 1. `net.instantgratification.magnet.mixin.MixinEntity`
Tem como alvo `net.minecraft.world.entity.Entity` e implementa `IMagnetEntity`.

| Método Injetado | Ponto Alvo de Injeção | Ação & Comportamento |
| :--- | :--- | :--- |
| `ig_magnet$decrementNoClipTicks` | `@Inject(method = "tick", at = @At("HEAD"))` | Decrementa a contagem regressiva de `noClipTicks` em 1 a cada tick quando ativo. |
| `ig_magnet$preventPushOut` | `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` | Cancela a velocidade de empurrão para fora do vanilla se `noClipTicks > 0` no servidor. |
| `ig_magnet$forceNoClipOnMove` | `@Inject(method = "move", at = @At("HEAD"))` | Armazena em cache `originalNoPhysics` e força `entity.noPhysics = true` se `noClipTicks > 0`. |
| `ig_magnet$restoreNoPhysicsAfterMove` | `@Inject(method = "move", at = @At("RETURN"))` | Restaura `entity.noPhysics = originalNoPhysics` após a conclusão do movimento. |
| `ig_magnet$conditionalCancelGravity` | `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` | Cancela a aceleração da gravidade para baixo *apenas* quando o item está dentro de um bloco sólido. |

---

### 2. `net.instantgratification.magnet.mixin.PlayerMixin`
Tem como alvo `net.minecraft.world.entity.player.Player` e implementa `IMagnetPlayer`.

| Método Injetado | Ponto Alvo de Injeção | Ação & Comportamento |
| :--- | :--- | :--- |
| `ig_magnet$addAdditionalSaveData` | `@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))` | Serializa o booleano `ig_magnet_enabled` para o NBT do jogador através de `ValueOutput`. |
| `ig_magnet$readAdditionalSaveData` | `@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))` | Desserializa `ig_magnet_enabled` do NBT do jogador através de `ValueInput.getBooleanOr()`. |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | Invoca `MagnetManager.tick(player)` no servidor a cada tick de jogo. |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | Expande a caixa de colisão de coleta (`pickupArea.inflate(range)`) quando `ig:magnet_instant` é verdadeiro. |

---

## 🛡️ Verificação de Sanidade do Knot ClassLoader (`ModVersionGuard`)

Para proteger mundos e saves de servidores contra carregamentos em versões quebradas, incompatíveis ou não suportadas do Minecraft:

```java
public final class ModVersionGuard {
    public static void checkClass(String modName, String requiredClassName) {
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        // Verifica a existência da classe alvo antes que a inicialização do mod continue...
    }
}
```

Chamado em `MagnetMod.onInitialize()`:
```java
ModVersionGuard.checkClass("Magnet", "net.minecraft.world.entity.item.ItemEntity");
```

---

## 🔗 Documentação da Wiki Relacionada
* [[Física de Vácuo & Deslocamento de Fase|pt_br-26.2-Vacuum-and-Phase-Shifting]]
* [[Alternância do Jogador, Persistência & Ciclo de Vida|pt_br-26.2-Player-Toggle-and-Persistence]]
* [[Integração de API & Addons|pt_br-26.2-API-and-Addon-Integration]]
* [[Retornar ao Portal do MC 26.2|pt_br-26.2-Home]]
