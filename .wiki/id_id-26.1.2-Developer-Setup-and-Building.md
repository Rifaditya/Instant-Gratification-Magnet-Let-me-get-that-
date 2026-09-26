# 🛠️ Penyiapan Pengembang & Kompilasi Loom (MC 26.1.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Toolchain | Parameter Teknis |
| :--- | :--- |
| **Direktori Subproyek** | `Magnet v26.1/magnet/` |
| **Target JDK Java** | **Java 25** (`release = 25`) |
| **Plugin Gradle Loom** | `net.fabricmc.fabric-loom` versi `1.15.5` |
| **Versi Minecraft** | `26.1.2` |
| **Versi Fabric Loader** | `0.19.1` (Batas min: `>=0.16.10`) |
| **Versi Fabric API** | `0.145.4+26.1.2` |
| **Versi DasikLibrary** | `1.8.23` |
| **Versi Cloth Config** | `26.1.154` |

---

## 💻 Penyiapan Lingkungan & Toolchain

1. **Java Development Kit (JDK 25)**:
   - Proyek modern Fabric Minecraft di ruang kerja ini dikompilasi menggunakan Java 25.
   - Konfigurasikan `JAVA_HOME` atau tetapkan `org.gradle.java.home=E:/JDK25` di `gradle.properties`.
2. **Kloning Git**:
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.1/magnet"
   ```

---

## ⚙️ Konfigurasi Properti (`gradle.properties`)

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

## 🔨 Perintah Kompilasi Gradle

```bash
# Bersihkan artefak build sebelumnya
./gradlew clean

# Jalankan pengujian otomatis
./gradlew test

# Kompilasi JAR produksi dan picu pengarsipan otomatis
./gradlew build --no-daemon
```

---

## 📦 Pengarsipan Rilis Otomatis

Skrip `build.gradle` MC 26.1.2 menyertakan task siklus hidup `archiveReleaseJar`:

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

## 🔗 Dokumentasi Wiki Terkait
* [[Arsitektur & Implementasi Mixin|id_id-26.1.2-Architecture-and-Mixins]]
* [[API & Integrasi Addon|id_id-26.1.2-API-and-Addon-Integration]]
* [[Kembali ke Portal MC 26.1.2|id_id-26.1.2-Home]]\n