# 🔌 API и интеграция аддонов (MC 26.2)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

| Инфоблок API | Технические параметры |
| :--- | :--- |
| **Интерфейс игрока** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Интерфейс сущности** | `net.instantgratification.magnet.IMagnetEntity` |
| **Основной фасад движения** | `net.instantgratification.magnet.MagnetMovement` |
| **API игровых правил** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **API видимости** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Интеграция для сторонних разработчиков

Сторонние моды, серверные плагины и аддоны коллекции Instant Gratification могут напрямую взаимодействовать с **Magnet, Let me get that!** для запроса состояния магнита игрока, программного притягивания предметов или обхода преград.

---

## 🧑‍💻 Интерфейс состояния игрока (`IMagnetPlayer`)

Приведите любой экземпляр `Player` или `ServerPlayer` к типу `IMagnetPlayer`, чтобы узнать или изменить настройки магнита:

```java
package net.instantgratification.magnet;

public interface IMagnetPlayer {
    boolean ig_magnet$isMagnetEnabled();
    void ig_magnet$setMagnetEnabled(boolean enabled);
    boolean ig_magnet$toggleMagnet();
}
```

### Пример использования:
```java
// Проверить, активен ли магнит у игрока
if (((IMagnetPlayer) player).ig_magnet$isMagnetEnabled()) {
    // Логика кастомного аддона...
}

// Программно отключить магнит (например, во время нахождения на троне или в мини-игре)
((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(false);
```

---

## 📦 Интерфейс намагничивания сущностей (`IMagnetEntity`)

Приведите любой экземпляр `Entity` (например, кастомный дроп с моба, снаряд или сферу опыта) к типу `IMagnetEntity`:

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig_magnet$setMagnetNoClip();
    boolean ig_magnet$isMagnetNoClip();
    void ig_magnet$setMagnetized();
    boolean ig_magnet$isMagnetized();
}
```

### Пример использования:
```java
// Выдать кастомной сущности фазовый сдвиг NoClip на 2 тика
((IMagnetEntity) customItem).ig_magnet$setMagnetNoClip();
```

---

## 🚀 Статический фасад движения (`MagnetMovement.pull`)

Сторонние моды могут программно притягивать любые сущности к игроку, используя встроенную физику и алгоритмы видимости:

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

```java
// Притянуть целевую сущность к игроку с генерацией шлейфа искр
MagnetMovement.pull(specialDrop, player, true);
```

---

## 🔗 Связанная документация Вики
* [[Архитектура, пакеты и точки инъекций Mixin|ru_ru-26.2-Architecture-and-Mixins]]
* [[Настройка среды разработки, инструментарии и Gradle Loom|ru_ru-26.2-Developer-Setup-and-Building]]
* [[Вернуться в портал MC 26.2|ru_ru-26.2-Home]]
