# 🔄 Переключение игрока, сохранение и жизненный цикл (MC 26.2)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

| Инфоблок механики | Технические параметры |
| :--- | :--- |
| **Интерфейсный мост** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Цель Mixin** | `net.minecraft.world.entity.player.Player` (`PlayerMixin.java`) |
| **Клавиша по умолчанию на клиенте** | `\` (Обратный слэш) — `key.ig_magnet.toggle` |
| **Категория клавиш** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Сетевой пакет** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **Кодеки сохранения NBT** | `ValueOutput` / `ValueInput` под тегом `"ig_magnet_enabled"` |
| **События жизненного цикла** | `ServerPlayConnectionEvents.JOIN`, `ServerPlayerEvents.COPY_FROM`, `ServerPlayerEvents.AFTER_RESPAWN` |

---

## 📖 Архитектура состояния игрока

На многопользовательских серверах и в сборках модов у игроков бывают разные предпочтения: строителям может потребоваться временно отключить притягивание при декорировании, тогда как шахтерам нужна максимальная мощность вакуума.

**Magnet, Let me get that!** реализует **индивидуальное состояние переключения для каждого игрока**, которое на 100% сохраняется при перезагрузках мира, гибели, возрождении и телепортации между измерениями.

```
                                [ДЕЙСТВИЕ КЛИЕНТА]
                     Игрок нажимает клавишу переключения ('\')
                                       |
                                       v
                         [ЛОКАЛЬНОЕ СОСТОЯНИЕ ОБНОВЛЕНО]
                     client.player -> isEnabled = !isEnabled
                     Уведомление в панели действий: "Item Magnet: Enabled/Disabled"
                                       |
                                       v
                         [ОТПРАВКА ПАКЕТА C2S]
                     ClientPlayNetworking.send(MagnetTogglePayload)
                                       |
                                       v
                               [СЕРВЕРНЫЙ ПРИЕМНИК]
                     context.server().execute(() -> {
                         ((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(val);
                     })
                                       |
          +----------------------------+----------------------------+
          |                                                         |
          v                                                         v
  [ДАННЫЕ NBT СОХРАНЕНЫ]                                [ПОДКЛЮЧЕНЫ СОБЫТИЯ ЦИКЛА]
  ValueOutput.putBoolean("ig_magnet_enabled")           - JOIN: Синхронизация S2C
  ValueInput.getBooleanOr("ig_magnet_enabled", true)    - COPY_FROM: Сохранение при смерти
                                                        - AFTER_RESPAWN: Синхронизация S2C для новой сущности
```

---

## ⌨️ Привязка клавиши на клиенте и панель действий

* **Клавиша по умолчанию**: `GLFW_KEY_BACKSLASH` (`\`), что исключает конфликты с популярными модами на карты и инвентарь.
* **Динамическое определение типа клавиши**: Использует `ig_magnet$getKeyboardType()` для безопасного разрешения `InputConstants.Type.KEYBOARD` с резервным переключением на `KEYSYM` на различных снапшотах Fabric Loader.
* **Мгновенный отклик в интерфейсе**: Переключение выводит локализованное всплывающее сообщение в панели действий (actionbar):
  - `chat.ig_magnet.enabled`: `"Item Magnet: Enabled"` (Магнит предметов: Включен)
  - `chat.ig_magnet.disabled`: `"Item Magnet: Disabled"` (Магнит предметов: Отключен)

---

## 💾 Хранение в NBT и сериализация кодеками Minecraft 26.2

Настройки переключателя игрока сохраняются прямо в файл данных игрока `.dat` мира с использованием современных конвейеров данных `ValueOutput` и `ValueInput` Minecraft 26.2:

```java
// Сохранение в NBT игрока
@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
    output.putBoolean("ig_magnet_enabled", this.ig_magnet$enabled);
}

// Загрузка из NBT игрока
@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
    this.ig_magnet$enabled = input.getBooleanOr("ig_magnet_enabled", true);
}
```

---

## 🧬 События жизненного цикла Fabric и логика возрождения

Когда игрок погибает в Minecraft, игра создает совершенно новую сущность `ServerPlayer` при возрождении. Мод гарантирует полное сохранение состояния:

1. **`ServerPlayerEvents.COPY_FROM`**: Копирует логическое состояние переключателя из `oldPlayer` в `newPlayer` сразу в момент клонирования сущности.
2. **`ServerPlayerEvents.AFTER_RESPAWN`**: Автоматически отправляет пакет S2C `MagnetTogglePayload` на клиент после подключения новой сущности игрока, синхронизируя клиентский интерфейс.
3. **`ServerPlayConnectionEvents.JOIN`**: Синхронизирует сохраненное состояние NBT с клиентом при входе на выделенный сервер или в локальный мир.

---

## 🔗 Связанная документация Вики
* [[Команды Brigadier и внутриигровая диагностика|ru_ru-26.2-Commands]]
* [[Архитектура и реализации Mixin|ru_ru-26.2-Architecture-and-Mixins]]
* [[HUD панели действий и логирование|ru_ru-26.2-HUD-and-Diagnostics]]
* [[Вернуться в портал MC 26.2|ru_ru-26.2-Home]]
