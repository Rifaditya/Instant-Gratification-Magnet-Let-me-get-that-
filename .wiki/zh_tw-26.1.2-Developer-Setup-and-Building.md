# 🛠️ 開發者環境配置與 Loom 建置 (MC 26.1.2)

> 📌 **倉庫原始碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

| 工具鏈資訊面板 | 技術參數 |
| :--- | :--- |
| **子專案目錄** | `Magnet v26.1/magnet/` |
| **Java JDK 目標** | **Java 25** (`release = 25`) |
| **Gradle Loom 外掛** | `net.fabricmc.fabric-loom` 版本 `1.15.5` |
| **Minecraft 目標版本** | `26.1.2` |
| **Fabric Loader 版本** | `0.19.1`（最低範圍：`>=0.16.10`） |
| **Fabric API 版本** | `0.145.4+26.1.2` |
| **DasikLibrary 版本** | `1.8.23` |
| **Cloth Config 版本** | `26.1.154` |

---

## 💻 環境準備與工具鏈配置

1. **Java Development Kit (JDK 25)**：
   - 本工作區中的現代 Fabric 專案統一使用 Java 25 進行編譯。
   - 請配置 `JAVA_HOME` 或在 `gradle.properties` 中指定 `org.gradle.java.home=E:/JDK25`。
2. **Git 複製專案**：
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.1/magnet"
   ```

---

## ⚙️ 屬性設定 (`gradle.properties`)

```properties
org.gradle.parallel=false
org.gradle.java.home=E:/JDK25

# Mod Properties
mod_name=Magnet, Let me get that!
mod_version=1.1.2+26.1.2
maven_group=net.instantgratification
archives_base_name=Magnet-Let-me-get-that

# Dependencies
minecraft_version=26.1.2
parchment_minecraft_version=26.1.2
parchment_version=2026.01.22

# Fabric
fabric_version=0.145.4+26.1.2
fabric_loader_version=0.19.1

# Loom
fabric.loom.suppressJavaCompatibilityChecks=true
loom.suppressJavaCompatibilityChecks=true

dasik_library_version=1.8.23
```

---

## 🔨 Gradle 建置指令

```bash
# 清理先前的建置產物
./gradlew clean

# 執行自動化測試
./gradlew test

# 編譯正式 JAR 並觸發自動歸檔
./gradlew build --no-daemon
```

---

## 📦 自動化發布歸檔機制

MC 26.1.2 的 `build.gradle` 腳本註冊了自動化的 `archiveReleaseJar` 生命週期任務：

```groovy
tasks.register('archiveReleaseJar') {
    dependsOn 'build'
    doLast {
        def archiveDir = file("${project.rootDir}/../../Archive Jar of all versions/MC 26.1.2")
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

---

## 🔗 相關 Wiki 文件
* [[🧩 架構設計與 Mixin 注入目標|zh_tw-26.1.2-Architecture-and-Mixins]]
* [[🔌 API 與附屬模組整合|zh_tw-26.1.2-API-and-Addon-Integration]]
* [[📖 返回 MC 26.1.2 文檔中心|zh_tw-26.1.2-Home]]
