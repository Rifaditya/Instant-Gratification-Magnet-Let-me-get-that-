# ⚙️ Полный справочник игровых правил (MC 26.1.2)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

| Инфоблок категории | Сведения |
| :--- | :--- |
| **ID категории** | `magnet:magnet_category` |
| **Локализованный заголовок** | `Magnet, Let me get that!` (`gamerule.category.magnet.magnet_category`) |
| **Зарегистрированный менеджер** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **Класс реестра** | `net.instantgratification.magnet.registry.ModGameRules` |
| **Всего зарегистрировано правил** | `15` правил с пространством имен |

---

## 📖 Администрирование игровых правил в игре

Все глобальные механики мода **Magnet, Let me get that!** в Minecraft 26.1.2 управляются через правила игры с пространством имен, зарегистрированные в категории `magnet:magnet_category`.

```
/gamerule ig:magnet_enabled true
/gamerule ig:magnet_range 12
/gamerule ig:magnet_speed 80
/gamerule ig:magnet_instant false
```

---

## 📋 Полная таблица игровых правил

| Идентификатор GameRule | Тип | По умолчанию | Границы | Отображаемое название | Описание и влияние на игру |
| :--- | :---: | :---: | :---: | :--- | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | **Magnet Enabled** | Главный переключатель, глобально активирующий или отключающий систему вакуума. |
| `ig:magnet_range` | Integer | `12` | `1..64` | **Magnet Range** | Сферический радиус в блоках, в пределах которого игрок притягивает выпавшие предметы. |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | **Magnet Noclip** | Включает фазовый сдвиг, позволяя притягиваемым предметам свободно проходить сквозь твердые блоки. |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | **Attract XP Orbs** | Притягиваются ли сферы опыта наряду с выброшенными предметами. |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | **Magnet Particles** | Спавнит искры частиц вдоль траектории притягиваемых сущностей. |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | **Particle Count** | Количество частиц искр на каждый активный источник за один тик частиц. |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | **Max Particle Sources** | Максимальное количество одновременных сущностей, испускающих частицы, для предотвращения лагов. |
| `ig:magnet_speed` | Integer | `80` | `1..1000` | **Item Speed** | Процент предельной скорости ($80 = 0.8\text{ блоков/тик} = 16.0\text{ м/с}$). |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000` | **Item Acceleration** | Коэффициент интерполяции ускорения за тик ($10 = 10\%\text{ lerp/тик}$). |
| `ig:magnet_instant` | Boolean | `false` | `true/false` | **Instant Pickup** | При true предметы мгновенно перемещаются в инвентарь через расширение AABB с 0 задержкой полета. |
| `ig:magnet_los_only` | Boolean | `true` | `true/false` | **Line of Sight Only** | Требует прямой видимости предмета; предотвращает сбор сквозь непроницаемые преграды. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | `true/false` | **Keep Moving if Unseen** | Позволяет намагниченным предметам сохранять импульс, если видимость прервалась в полете. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | `true/false` | **Blocked by Transparent** | Если true, стекло, стеклянные панели, железные решетки и прозрачные блоки блокируют видимость. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | `true/false` | **Blocked by Flora** | Если true, высокая трава, посевы, цветы и листва деревьев блокируют видимость. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | `true/false` | **Blocked by Block Entities** | Если true, сундуки, кровати, бочки и шалкеровые ящики блокируют видимость. |

---

## 🔗 Связанная документация Вики
* [[Физика вакуума и фазового сдвига|ru_ru-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Графический интерфейс Cloth Config и ModMenu|ru_ru-26.1.2-Configuration]]
* [[Вернуться в портал MC 26.1.2|ru_ru-26.1.2-Home]]
