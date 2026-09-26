# 🛠️ Настройка среды разработки и сборка с Loom (MC 26.2)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

| Инфоблок инструментария | Технические параметры |
| :--- | :--- |
| **Каталог подпроекта** | `Magnet v26.2/magnet/` |
| **Целевая версия Java JDK** | **Java 25** (`release = 25`) |
| **Плагин Gradle Loom** | `net.fabricmc.fabric-loom` версии `1.15.5` |
| **Версия Minecraft** | `26.2` |
| **Версия Fabric Loader** | `0.19.1` |
| **Версия Fabric API** | `0.150.1+26.2` |
| **Версия DasikLibrary** | `1.8.23` |
| **Версия YACL** | `3.9.5+26.2-fabric` |

---

## 💻 Предварительные требования и настройка окружения

1. **Java Development Kit (JDK 25)**:
   - Современный Minecraft 26.x компилируется под Java 25.
   - Настройте системную переменную `JAVA_HOME` или укажите `org.gradle.java.home=E:/JDK25` в `gradle.properties`.
2. **Клонирование репозитория Git**:
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.2/magnet"
   ```

---

## ⚙️ Файл настроек свойств (`gradle.properties`)

```properties
org.gradle.parallel=false
org.gradle.java.home=E:/JDK25

# Свойства мода
mod_name=Magnet, Let me get that!
mod_version=1.3.9+26.2
maven_group=net.instantgratification
archives_base_name=Magnet-Let-me-get-that

# Зависимости
minecraft_version=26.2
parchment_minecraft_version=26.2
parchment_version=2026.01.22

# Fabric
fabric_version=0.150.1+26.2
fabric_loader_version=0.19.1

# Loom
fabric.loom.suppressJavaCompatibilityChecks=true
loom.suppressJavaCompatibilityChecks=true

dasik_library_version=1.8.23
```

---

## 🔨 Команды сборки Gradle

```bash
# Очистить кэш предыдущей сборки
./gradlew clean

# Запустить модульные тесты
./gradlew test

# Собрать релизный JAR и запустить автоматическое архивирование
./gradlew build --no-daemon
```

---

## 📦 Автоматическое архивирование релизов и синхронизация с лаунчером

Скрипт `build.gradle` подпроекта MC 26.2 включает задачу жизненного цикла `archiveReleaseJar`:

```groovy
tasks.register('archiveReleaseJar') {
    dependsOn 'build'
    doLast {
        def archiveDir = file("${project.rootDir}/../../Archive Jar of all versions/MC 26.2")
        archiveDir.mkdirs()
        def jarFile = tasks.named('jar', Jar).get().archiveFile.get().asFile
        if (jarFile.exists()) {
            copy {
                from jarFile
                into archiveDir
            }
            println "[AUTO-ARCHIVE] Successfully copied ${jarFile.name} to central Archive directory: ${archiveDir.absolutePath}"
        }
    }
}

tasks.named('build') {
    finalizedBy 'archiveReleaseJar'
}
```

После успешной компиляции собранный JAR (`Magnet-Let-me-get-that-1.3.9+26.2.jar`) автоматически копируется в `Archive Jar of all versions/MC 26.2/` и развертывается в тестовых профилях лаунчера Modrinth.

---

## 🔗 Связанная документация Вики
* [[Архитектура, пакеты и точки инъекций Mixin|ru_ru-26.2-Architecture-and-Mixins]]
* [[Фасады API, интерфейсы и хуки аддонов|ru_ru-26.2-API-and-Addon-Integration]]
* [[Вернуться в портал MC 26.2|ru_ru-26.2-Home]]
