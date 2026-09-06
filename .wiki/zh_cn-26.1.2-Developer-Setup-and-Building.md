# 🛠️ 开发者环境配置、工具链与 Gradle Loom (MC 26.1.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 工具链信息栏 | 技术参数 |
| :--- | :--- |
| **子项目目录** | `Magnet v26.1/magnet/` |
| **Java JDK 目标** | **Java 25** (`release = 25`) |
| **Gradle Loom 插件** | `net.fabricmc.fabric-loom` 版本 `1.15.5` |
| **Minecraft 版本** | `26.1.2` |
| **Fabric Loader 版本** | `0.19.1`（最低边界：`>=0.16.10`） |
| **Fabric API 版本** | `0.145.4+26.1.2` |
| **DasikLibrary 版本** | `1.8.23` |
| **Cloth Config 版本** | `26.1.154` |

---

## 💻 开发环境与工具链配置

1. **Java 开发工具包 (JDK 25)**：
   - 本工作区内的现代 Fabric Minecraft 项目均针对 Java 25 编译。
   - 请配置环境变量 `JAVA_HOME`，或在 `gradle.properties` 中指定 `org.gradle.java.home=E:/JDK25`。
2. **Git 克隆**：
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.1/magnet"
   ```

---

## ⚙️ 属性配置清单 (`gradle.properties`)

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

## 🔨 Gradle 构建命令

```bash
# 清理先前生成的构建产物
./gradlew clean

# 运行自动化测试
./gradlew test

# 编译正式发布版 JAR 并触发自动归档
./gradlew build --no-daemon
```

---

## 📦 自动化发布归档

MC 26.1.2 的 `build.gradle` 脚本同样配备了自动化 `archiveReleaseJar` 生命周期任务：

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

## 🔗 相关 Wiki 文档
* [[架构设计与 Mixin 实现|zh_cn-26.1.2-Architecture-and-Mixins]]
* [[API 与附属模组集成|zh_cn-26.1.2-API-and-Addon-Integration]]
* [[返回 MC 26.1.2 门户|zh_cn-26.1.2-Home]]
