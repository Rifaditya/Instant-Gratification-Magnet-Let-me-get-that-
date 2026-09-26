# 📊 HUD, визуальные эффекты и уведомления (MC 26.1.2)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

| Инфоблок визуала | Технические параметры |
| :--- | :--- |
| **API панели действий** | `client.gui.setOverlayMessage(Component, boolean)` |
| **Тип частиц** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Модуль частоты частиц** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **Лимит источников частиц** | `ig:magnet_max_particle_sources` (По умолчанию: `5`) |

---

## 🖥️ Всплывающие уведомления панели действий

Когда игрок переключает свой магнит предметов с помощью комбинации клавиш `Ctrl+M`, интерфейс клиента мгновенно отображает уведомление в панели действий:

* **Включен**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Отключен**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Проверено для: Gui.java (26.1.2)
if (newState) {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Визуальные шлейфы электрических искр

Притягиваемые предметы и сферы опыта испускают искрящиеся следы частиц:

```
[Притягиваемый предмет / опыт]  --->  ✨  --->  ✨  --->  ✨  --->  [Уровень глаз игрока]
```

* **Ограничение источников**: Не более 5 одновременных источников испускают частицы (`ig:magnet_max_particle_sources = 5`).
* **Ограничение тиков**: Частицы появляются каждые 4 тика ($5\text{ раз/секунду}$).
* **Контроль плотности**: `ig:magnet_particle_count = 1`.

---

## 🔗 Связанная документация Вики
* [[Переключение игрока и управление состоянием|ru_ru-26.1.2-Player-Toggle-and-Persistence]]
* [[Синхронизация сфер опыта|ru_ru-26.1.2-Experience-Orb-Attraction]]
* [[Вернуться в портал MC 26.1.2|ru_ru-26.1.2-Home]]
