# 🧩 Архитектура и цели миксинов (MC 26.1.2)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

| Архитектурный инфоблок | Технические параметры |
| :--- | :--- |
| **Корневой пакет** | `net.instantgratification.magnet` |
| **Конфигурация Mixin** | `src/main/resources/magnet.mixins.json` |
| **Файл расширения доступа** | `src/main/resources/magnet.accesswidener` |
| **Уровень совместимости** | `JAVA_25` |
| **Всего классов Mixin** | `2` (`MixinEntity`, `PlayerMixin`) |

---

## 🌳 Дерево пакетов архитектуры

```text
net.instantgratification.magnet/
├── IMagnetEntity.java                  # Интерфейс сущности для флагов NoClip и намагничивания
├── MagnetCommand.java                  # Регистрация серверных команд (/magnet toggle)
├── MagnetManager.java                  # Цикл сканирования сущностей и пространственной обработки
├── MagnetMod.java                      # Инициализатор мода и приемники пакетов
├── MagnetModClient.java                # Клиентский инициализатор, хоткей Ctrl+M и всплывающие сообщения
├── MagnetMovement.java                 # Векторная математика траекторий, скорость lerp и частицы
├── MagnetPlayerState.java              # Потокобезопасное хранилище переключателей игроков в ConcurrentHashMap
├── MagnetTogglePayload.java            # Запись сетевого пакета и составной StreamCodec
├── SecondaryVisionCheck.java           # Тонкая проверка блоков (растительность, сундуки, стекло)
├── config/
│   ├── ClothConfigScreenHelper.java    # Построитель GUI на Cloth Config Fabric
│   ├── MagnetConfig.java               # Хранилище настроек JSON и поля POJO
│   └── ModMenuIntegration.java         # Безопасная для рефлексии точка входа ModMenu API
├── mixin/
│   ├── MixinEntity.java                # Инъекции NoClip и отмены гравитации в Entity
│   └── PlayerMixin.java                # Инъекции выполнения тиков и мгновенного подбора в Player
└── registry/
    └── ModGameRules.java               # Регистрация правил DynamicGameRuleManager
```

---

## 📋 Разбор целей инъекций Mixin

### 1. `net.instantgratification.magnet.mixin.MixinEntity`
Воздействует на класс `net.minecraft.world.entity.Entity` и реализует интерфейс `IMagnetEntity`.

| Метод инъекции | Точка инъекции | Действие и поведение |
| :--- | :--- | :--- |
| `ig_magnet$decrementNoClipTicks` | `@Inject(method = "tick", at = @At("HEAD"))` | Уменьшает счетчик `noClipTicks` на 1 за каждый тик активности. |
| `ig_magnet$preventPushOut` | `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` | Отменяет ванильное выталкивание из блоков, если `noClipTicks > 0` на сервере. |
| `ig_magnet$forceNoClipOnMove` | `@Inject(method = "move", at = @At("HEAD"))` | Сохраняет `originalNoPhysics` и принудительно задает `entity.noPhysics = true`, если `noClipTicks > 0`. |
| `ig_magnet$restoreNoPhysicsAfterMove` | `@Inject(method = "move", at = @At("RETURN"))` | Восстанавливает `entity.noPhysics = originalNoPhysics` после завершения перемещения. |
| `ig_magnet$conditionalCancelGravity` | `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` | Отменяет гравитацию вниз *только* в момент, когда предмет находится внутри твердого блока. |

---

### 2. `net.instantgratification.magnet.mixin.PlayerMixin`
Воздействует на класс `net.minecraft.world.entity.player.Player`.

| Метод инъекции | Точка инъекции | Действие и поведение |
| :--- | :--- | :--- |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | Вызывает `MagnetManager.tick(player)` на сервере каждый игровой тик. |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | Расширяет область подбора (`pickupArea.inflate(range)`), когда включен `ig:magnet_instant`. |

---

## 🔗 Связанная документация Вики
* [[Физика вакуума и фазового сдвига|ru_ru-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Настройка среды разработки, инструментарии и Gradle Loom|ru_ru-26.1.2-Developer-Setup-and-Building]]
* [[Вернуться в портал MC 26.1.2|ru_ru-26.1.2-Home]]
