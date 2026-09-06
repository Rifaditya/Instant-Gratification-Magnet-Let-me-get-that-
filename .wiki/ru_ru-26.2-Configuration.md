# 🎨 Графический интерфейс конфигурации YACL и ModMenu (MC 26.2)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

| Инфоблок конфигурации | Сведения |
| :--- | :--- |
| **Путь к файлу конфигурации** | `config/ig_magnet.json` |
| **Библиотека GUI** | YetAnotherConfigLib v3 (`dev.isxander:yet-another-config-lib`) |
| **Точка входа ModMenu** | `net.instantgratification.magnet.config.ModMenuIntegration` |
| **Хелпер экрана GUI** | `net.instantgratification.magnet.config.YaclScreenHelper` |
| **Безопасность загрузки классов** | Изолировано через `GuiHelper.getOptionalFactory` |

---

## 📖 Архитектура системы конфигурации

Мод **Magnet, Let me get that!** предоставляет опциональный графический интерфейс настроек на стороне клиента, созданный на базе **YetAnotherConfigLib v3 (YACL)** и доступный через **ModMenu**.

Чтобы гарантировать, что выделенные серверы никогда не аварийно завершат работу из-за загрузки клиентских классов интерфейса, фабрика экранов создается через **безопасную изоляцию загрузчика классов**:

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
                "ig_magnet",
                "net.instantgratification.magnet.config.YaclScreenHelper",
                "createScreen"
        );
    }
}
```

---

## ⚠️ Предупреждение о приоритете настроек

> ⚠️ **Важное уведомление**:  
> Изменения, внесенные в интерфейсе ModMenu или в файле `config/ig_magnet.json`, **влияют ТОЛЬКО на базовые значения по умолчанию для НОВЫХ миров**.  
> Чтобы изменить настройки в активном, уже созданном мире, используйте внутриигровые команды [[Справочник GameRules и стандартные границы|ru_ru-26.2-GameRules]] через `/gamerule` или экран настройки правил игры.

---

## 🗂️ Категории конфигурации и параметры

```
Экран конфигурации YACL ("Настройки Magnet, Let me get that!")
  ├── General Settings (Основные настройки)
  │     ├── Magnet Enabled (По умолчанию: true)
  │     ├── Magnet Range (По умолчанию: 12, Диапазон: 1..64)
  │     ├── Instant Pickup (По умолчанию: false)
  │     └── Magnet Noclip (По умолчанию: true)
  ├── Speeds & Pull Heuristics (Скорости и эвристика притягивания)
  │     ├── Item Speed (По умолчанию: 80%, Диапазон: 1..1000)
  │     └── Item Acceleration (По умолчанию: 10%, Диапазон: 1..1000)
  ├── Line of Sight (LOS) (Линия обзора)
  │     ├── Line of Sight Only (По умолчанию: true)
  │     ├── Keep Moving if Unseen (По умолчанию: true)
  │     ├── Blocked by Transparent (По умолчанию: false)
  │     ├── Blocked by Flora (По умолчанию: false)
  │     └── Blocked by Block Entities (По умолчанию: false)
  └── Visuals & Performance (Визуал и производительность)
        ├── Attract XP Orbs (По умолчанию: true)
        ├── Magnet Particles (По умолчанию: true)
        ├── Particle Count (По умолчанию: 1, Диапазон: 0..100)
        └── Max Particle Sources (По умолчанию: 5, Диапазон: 0..100)
```

---

## 📄 Исходная структура JSON (`config/ig_magnet.json`)

```json
{
  "configVersion": 1,
  "enabled": true,
  "range": 12,
  "noClip": true,
  "affectsXp": true,
  "particles": true,
  "particleCount": 1,
  "maxParticleSources": 5,
  "speed": 80,
  "acceleration": 10,
  "instant": false,
  "losOnly": true,
  "keepMovingIfUnseen": true,
  "blockedByTransparent": false,
  "blockedByFlora": false,
  "blockedByBlockEntities": false
}
```

---

## 🔗 Связанная документация Вики
* [[Справочник GameRules и стандартные границы|ru_ru-26.2-GameRules]]
* [[Настройка среды разработки, инструментарии и Gradle Loom|ru_ru-26.2-Developer-Setup-and-Building]]
* [[Вернуться в портал MC 26.2|ru_ru-26.2-Home]]
