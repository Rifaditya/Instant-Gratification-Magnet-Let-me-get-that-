# 🛠️ Developer Setup & Loom Building (MC 26.1.2)

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

| Toolchain Infobox | Technical Parameters |
| :--- | :--- |
| **Subproject Directory** | `Magnet v26.1/magnet/` |
| **Java JDK Target** | **Java 25** (`release = 25`) |
| **Gradle Loom Plugin** | `net.fabricmc.fabric-loom` version `1.15.5` |
| **Minecraft Version** | `26.1.2` |
| **Fabric Loader Version** | `0.19.1` (Min bound: `>=0.16.10`) |
| **Fabric API Version** | `0.145.4+26.1.2` |
| **DasikLibrary Version** | `1.8.23` |
| **Cloth Config Version** | `26.1.154` |

---

## 💻 Environment Setup & Toolchains

1. **Java Development Kit (JDK 25)**:
   - Modern Fabric Minecraft projects in this workspace compile against Java 25.
   - Configure `JAVA_HOME` or set `org.gradle.java.home=E:/JDK25` in `gradle.properties`.
2. **Git Clone**:
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.1/magnet"
   ```

---

## ⚙️ Properties Configuration (`gradle.properties`)

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

## 🔨 Gradle Build Commands

```bash
# Clean previous build artifacts
./gradlew clean

# Run automated tests
./gradlew test

# Compile production JAR and trigger auto-archiving
./gradlew build --no-daemon
```

---

## 📦 Automated Release Archiving

The MC 26.1.2 `build.gradle` script features an automated `archiveReleaseJar` lifecycle task that mirrors output JARs to `Archive Jar of all versions/MC 26.1.2/`.

---

## 🔗 Related Wiki Documentation
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[API & Addon Integration|API-and-Addon-Integration]]
* [[Return to Home Portal|Home]]
