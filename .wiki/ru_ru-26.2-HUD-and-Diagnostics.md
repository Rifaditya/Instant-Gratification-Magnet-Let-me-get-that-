# 📊 HUD, визуальные эффекты и диагностика (MC 26.2)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

| Инфоблок визуала | Технические параметры |
| :--- | :--- |
| **API панели действий** | `client.gui.hud.setOverlayMessage(Component, boolean)` |
| **Тип частиц** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Модуль частоты частиц** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **Файл лога отладки** | `logs/ig_magnet_debug.log` |
| **Класс логгера** | `net.instantgratification.magnet.MagnetDebugLogger` |

---

## 🖥️ Всплывающие уведомления панели действий

Когда игрок переключает состояние магнита горячей клавишей (`\`) или серверной командой (`/magnet toggle`), интерфейс клиента выводит аккуратное, ненавязчивое сообщение прямо над полосой быстрого доступа:

* **Сообщение о включении**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Сообщение об отключении**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Проверено для: Hud.java (26.2+)
if (newState) {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Визуальные шлейфы электрических искр

Во время активного притягивания по воздуху предметы и сферы опыта оставляют за собой мягкие следы из частиц `ParticleTypes.ELECTRIC_SPARK`:

```
[Притягиваемый предмет]  --->  ✨  --->  ✨  --->  ✨  --->  [Уровень глаз игрока]
```

### Правила ограничения частиц:
1. **Лимит источников**: Регулируется правилом `ig:magnet_max_particle_sources` (по умолчанию: `5`), исключая перегрузку видеокарты при масштабном сборе руды в карьерах.
2. **Чередование по тикам**: Каждая отдельная сущность испускает частицы лишь 1 раз за 4 тика с разнесением по уникальному ID: `(entity.tickCount + entity.getId()) % 4 == 0`.
3. **Регулировка плотности**: Задается правилом `ig:magnet_particle_count` (по умолчанию: `1`).

---

## 📝 Выделенный файловый логгер отладки (`MagnetDebugLogger`)

Для администраторов серверов и авторов сборок, отлаживающих границы видимости или сетевые пакеты, мод включает потокобезопасный асинхронный логгер, пишущий данные в `logs/ig_magnet_debug.log`:

* **Активация**: Выполните команду `/magnet debug log` в игре.
* **Формат записи**: `[yyyy-MM-dd HH:mm:ss.SSS] [Context] Message`
* **Пример лога**:
  ```text
  [2026-08-22 21:50:00.124] MagnetMod: Server received packet from Alex (e7b1a234-...) enabled=true
  [2026-08-22 21:50:00.130] MagnetMovement: Entity 592 is now magnetized by Alex (e7b1a234-...).
  [2026-08-22 21:50:01.450] PlayerMixin: Server tick for Alex (e7b1a234-...) isMagnetEnabled=true
  ```

---

## 🔗 Связанная документация Вики
* [[Команды Brigadier и внутриигровая диагностика|ru_ru-26.2-Commands]]
* [[Синхронизация сфер опыта|ru_ru-26.2-Experience-Orb-Attraction]]
* [[Вернуться в портал MC 26.2|ru_ru-26.2-Home]]
