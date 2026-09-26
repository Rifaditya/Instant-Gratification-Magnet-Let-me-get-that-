# 🔌 API и интеграция аддонов (MC 26.1.2)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

| Инфоблок API | Технические параметры |
| :--- | :--- |
| **Менеджер состояния игрока** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Интерфейс сущности** | `net.instantgratification.magnet.IMagnetEntity` |
| **Основной фасад движения** | `net.instantgratification.magnet.MagnetMovement` |
| **API игровых правил** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **API видимости** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Интеграция для сторонних разработчиков

Сторонние моды и аддоны-компаньоны линейки Instant Gratification могут напрямую взаимодействовать с **Magnet, Let me get that!** в Minecraft 26.1.2.

---

## 🧑‍💻 Управление состоянием игрока (`MagnetPlayerState`)

Запрашивайте или изменяйте параметры магнита игрока напрямую с помощью статических вспомогательных методов:

```java
package net.instantgratification.magnet;

public class MagnetPlayerState {
    public static boolean isMagnetEnabled(Player player);
    public static void setMagnetEnabled(Player player, boolean enabled);
    public static boolean toggleMagnet(Player player);
}
```

### Пример использования:
```java
// Проверить, включен ли магнит у игрока
if (MagnetPlayerState.isMagnetEnabled(player)) {
    // Кастомная логика...
}

// Программно отключить магнит
MagnetPlayerState.setMagnetEnabled(player, false);
```

---

## 📦 Интерфейс намагничивания сущностей (`IMagnetEntity`)

Приведите любой экземпляр `Entity` к типу `IMagnetEntity`, чтобы управлять его флагами фазового сдвига или намагничивания:

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig$setMagnetNoClip();
    boolean ig$isMagnetNoClip();
    void ig$setMagnetized();
    boolean ig$isMagnetized();
}
```

---

## 🚀 Статический фасад движения (`MagnetMovement.pull`)

Программно активируйте притягивание предметов или сфер опыта к игроку:

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

---

## 🔗 Связанная документация Вики
* [[Архитектура, пакеты и точки инъекций Mixin|ru_ru-26.1.2-Architecture-and-Mixins]]
* [[Настройка среды разработки, инструментарии и Gradle Loom|ru_ru-26.1.2-Developer-Setup-and-Building]]
* [[Вернуться в портал MC 26.1.2|ru_ru-26.1.2-Home]]
