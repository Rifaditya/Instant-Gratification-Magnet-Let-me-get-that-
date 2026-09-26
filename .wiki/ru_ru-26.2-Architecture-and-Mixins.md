# 🧩 Архитектура и цели миксинов (MC 26.2)

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
├── IMagnetPlayer.java                  # Интерфейс игрока для состояния переключения и геттеров/сеттеров
├── MagnetCommand.java                  # Деревья команд Brigadier (/magnet toggle, /magnet debug)
├── MagnetDebugLogger.java              # Потокобезопасная запись логов отладки на диск
├── MagnetManager.java                  # Цикл сканирования сущностей и пространственной обработки
├── MagnetMod.java                      # Точка входа мода (сервер/универсальная) и сетевые обработчики
├── MagnetModClient.java                # Клиентский инициализатор, бинд клавиш и уведомления
├── MagnetMovement.java                 # Векторная математика траекторий, скорость lerp и частицы
├── MagnetTogglePayload.java            # Запись сетевого пакета и составной StreamCodec
├── SecondaryVisionCheck.java           # Тонкая проверка блоков (растительность, сундуки, стекло)
├── config/
│   ├── MagnetConfig.java               # Хранилище настроек JSON и поля POJO
│   ├── ModMenuIntegration.java         # Безопасная для рефлексии точка входа ModMenu API
│   └── YaclScreenHelper.java           # Построитель GUI YetAnotherConfigLib v3
├── mixin/
│   ├── MixinEntity.java                # Инъекции NoClip и отмены гравитации в Entity
│   └── PlayerMixin.java                # Инъекции сохранения NBT и мгновенного подбора в Player
├── registry/
│   └── ModGameRules.java               # Регистрация правил DynamicGameRuleManager
└── util/
    └── ModVersionGuard.java            # Проверка совместимости загрузчика классов Knot в рантайме
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
Воздействует на класс `net.minecraft.world.entity.player.Player` и реализует интерфейс `IMagnetPlayer`.

| Метод инъекции | Точка инъекции | Действие и поведение |
| :--- | :--- | :--- |
| `ig_magnet$addAdditionalSaveData` | `@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))` | Сохраняет булево значение `ig_magnet_enabled` в NBT игрока через `ValueOutput`. |
| `ig_magnet$readAdditionalSaveData` | `@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))` | Загружает `ig_magnet_enabled` из NBT игрока через `ValueInput.getBooleanOr()`. |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | Вызывает `MagnetManager.tick(player)` на сервере каждый игровой тик. |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | Расширяет область подбора (`pickupArea.inflate(range)`), когда включен `ig:magnet_instant`. |

---

## 🛡️ Проверка загрузчика Knot (`ModVersionGuard`)

Для защиты серверов и миров от запуска на несовместимых или поврежденных сборках Minecraft:

```java
public final class ModVersionGuard {
    public static void checkClass(String modName, String requiredClassName) {
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        // Проверяет наличие требуемого класса до завершения инициализации...
    }
}
```

Вызывается в `MagnetMod.onInitialize()`:
```java
ModVersionGuard.checkClass("Magnet", "net.minecraft.world.entity.item.ItemEntity");
```

---

## 🔗 Связанная документация Вики
* [[Физика вакуума и фазового сдвига|ru_ru-26.2-Vacuum-and-Phase-Shifting]]
* [[Переключение игрока, сохранение и жизненный цикл|ru_ru-26.2-Player-Toggle-and-Persistence]]
* [[Фасады API, интерфейсы и хуки аддонов|ru_ru-26.2-API-and-Addon-Integration]]
* [[Вернуться в портал MC 26.2|ru_ru-26.2-Home]]
