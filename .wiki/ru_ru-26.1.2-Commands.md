# 💻 Серверные команды и поддержка ванильных клиентов (MC 26.1.2)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

| Инфоблок команд | Технические параметры |
| :--- | :--- |
| **Класс команд** | `net.instantgratification.magnet.MagnetCommand` |
| **Основные литералы** | `/magnet` и `/ig_magnet` (Идентичные зеркальные псевдонимы) |
| **Подкоманды** | `toggle` |
| **Сетевая синхронизация** | `ServerPlayNetworking.send(player, new MagnetTogglePayload(newState))` |

---

## 📖 Справочник команд

### `/magnet toggle` (или `/ig_magnet toggle`)
Переключает персональное состояние магнита у выполняющего команду игрока.

* **Синтаксис команды**: `/magnet toggle`
* **Логика выполнения**:
  ```java
  private static int toggleMagnet(CommandSourceStack source) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      boolean newState = MagnetPlayerState.toggleMagnet(player);

      if (newState) {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.enabled"), false);
      } else {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.disabled"), false);
      }

      if (ServerPlayNetworking.canSend(player, MagnetTogglePayload.TYPE)) {
          ServerPlayNetworking.send(player, new MagnetTogglePayload(newState));
      }
      return 1;
  }
  ```
* **Совместимость с ванильными клиентами**: Позволяет игрокам с чистым клиентом ваниллы при подключении к серверу Fabric включать и выключать магнит без обязательной установки мода на клиенте.

---

## 🔗 Связанная документация Вики
* [[Переключение игрока и управление состоянием|ru_ru-26.1.2-Player-Toggle-and-Persistence]]
* [[Справочник GameRules и стандартные границы|ru_ru-26.1.2-GameRules]]
* [[Вернуться в портал MC 26.1.2|ru_ru-26.1.2-Home]]
