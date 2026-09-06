# 🔄 Переключение игрока и управление состоянием (MC 26.1.2)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

| Инфоблок механики | Технические параметры |
| :--- | :--- |
| **Класс хранения состояния** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Клавиша по умолчанию на клиенте** | `Ctrl+M` (`GLFW.GLFW_KEY_M` + `isControlDown()`) |
| **Категория клавиш** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Сетевой пакет** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **API панели действий** | `client.gui.setOverlayMessage(Component, boolean)` |

---

## 📖 Архитектура состояния

В Minecraft 26.1.2 настройки переключения игрока сохраняются на протяжении активной сессии сервера с помощью класса `MagnetPlayerState`, использующего потокобезопасную карту `ConcurrentHashMap`:

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

## ⌨️ Привязка клавиш на клиенте (`Ctrl+M`)

* **Комбинация по умолчанию**: `Ctrl+M` (`GLFW_KEY_M` + `isControlDown()`), с поддержкой клавиши Command на macOS (`GLFW_KEY_LEFT_SUPER`).
* **Визуальные уведомления в панели действий**:
  - `chat.ig_magnet.enabled`: `"Item Magnet: Enabled"` (Магнит предметов: Включен)
  - `chat.ig_magnet.disabled`: `"Item Magnet: Disabled"` (Магнит предметов: Отключен)

---

## 📡 Протокол сетевой синхронизации

```
[КЛИЕНТ]                                                           [СЕРВЕР]
Игрок нажимает Ctrl+M
MagnetPlayerState.setMagnetEnabled(player, newState)
ClientPlayNetworking.send(new MagnetTogglePayload(newState))
                                               ------------------> Приемник на сервере
                                                                   MagnetPlayerState.setMagnetEnabled(player, payload.enabled())
```

---

## 🔗 Связанная документация Вики
* [[Серверные команды и поддержка ванильных клиентов|ru_ru-26.1.2-Commands]]
* [[Архитектура, пакеты и точки инъекций Mixin|ru_ru-26.1.2-Architecture-and-Mixins]]
* [[Вернуться в портал MC 26.1.2|ru_ru-26.1.2-Home]]
