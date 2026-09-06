# 💻 Команды Brigadier и внутриигровая диагностика (MC 26.2)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

| Инфоблок команд | Технические параметры |
| :--- | :--- |
| **Класс команд** | `net.instantgratification.magnet.MagnetCommand` |
| **Основные литералы** | `/magnet` и `/ig_magnet` (Идентичные зеркальные псевдонимы) |
| **Коллбэк регистрации** | `CommandRegistrationCallback.EVENT` |
| **Файл вывода логов** | `logs/ig_magnet_debug.log` |
| **Целевые права доступа** | Доступно всем игрокам (`toggle`) и OP уровня 2 (`debug`) |

---

## 📖 Структура дерева команд

```
/magnet (или /ig_magnet)
  ├── toggle
  └── debug
        └── log
```

---

## 🛠️ Справочник подкоманд

### 1. `/magnet toggle` (или `/ig_magnet toggle`)
Переключает персональное состояние магнита у выполняющего команду игрока.

* **Использование**: `/magnet toggle`
* **Выполнение**: Вызывает `((IMagnetPlayer) player).ig_magnet$toggleMagnet()`.
* **Текст отклика**:
  - Если включено: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
  - Если отключено: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)
* **Применение**: Позволяет игрокам с ванильными клиентами (при установке мода только на сервере) или игрокам без доступа к горячим клавишам управлять магнитом.

---

### 2. `/magnet debug` (или `/ig_magnet debug`)
Выполняет мгновенную диагностическую проверку игрока и окружающих сущностей в радиусе 10 блоков.

* **Использование**: `/magnet debug`
* **Выводимая информация**:
  ```text
  §e--- Magnet Debug Info ---
  Player Name: Steve
  Player UUID: c0b59055-ec50-4653-979d-c3c92d6ca897
  Global Master Toggle: true
  Player Toggle State: true
  Range: 12
  Instant Pickup: false
  LOS Only: true
  Spectator: false
  Dead/Dying: false
  Debug File Logger: DISABLED (Toggle with /magnet debug log)
  Nearby Items (10 blocks): 3
  Nearest Item ID: 412
  PlayerVisionTracker.canSee: true
  Vanilla Raycast canSee: true
  §e-------------------------
  ```
* **Применение**: Помогает диагностировать, почему предмет не притягивается (например, строгое перекрытие линии обзора, состояние гибели игрока или глобальное отключение в GameRule).

---

### 3. `/magnet debug log` (или `/ig_magnet debug log`)
Включает или выключает непрерывное подробное логирование на диск.

* **Использование**: `/magnet debug log`
* **Выполнение**: Переключает флаг `MagnetDebugLogger.enabled = !MagnetDebugLogger.enabled`.
* **Результат**: Записывает тики, полученные пакеты, события возрождения и отклонения видимости в файл `logs/ig_magnet_debug.log`.
* **Пример записи в логе**:
  ```log
  [2026-08-22 21:49:15.102] MagnetMod: Server received packet from Steve (c0b59055-...) enabled=true
  [2026-08-22 21:49:15.105] MagnetMovement: Entity 412 is now magnetized by Steve (c0b59055-...).
  ```

---

## 🔗 Связанная документация Вики
* [[Переключение игрока, сохранение и жизненный цикл|ru_ru-26.2-Player-Toggle-and-Persistence]]
* [[HUD панели действий и логирование|ru_ru-26.2-HUD-and-Diagnostics]]
* [[Справочник GameRules и стандартные границы|ru_ru-26.2-GameRules]]
* [[Вернуться в портал MC 26.2|ru_ru-26.2-Home]]
