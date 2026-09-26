# 🛠️ Penyiapan Pengembang & Kompilasi Loom (MC 26.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Toolchain | Parameter Teknis |
| :--- | :--- |
| **Direktori Subproyek** | `Magnet v26.2/magnet/` |
| **Target JDK Java** | **Java 25** (`release = 25`) |
| **Plugin Gradle Loom** | `net.fabricmc.fabric-loom` versi `1.15.5` |
| **Versi Minecraft** | `26.2` |
| **Versi Fabric Loader** | `0.19.1` |
| **Versi Fabric API** | `0.150.1+26.2` |
| **Versi DasikLibrary** | `1.8.23` |
| **Versi YACL** | `3.9.5+26.2-fabric` |

---

## 💻 Prasyarat & Penyiapan Lingkungan

1. **Java Development Kit (JDK 25)**:
   - Minecraft 26.x modern dikompilasi menggunakan Java 25.
   - Konfigurasikan `JAVA_HOME` atau tetapkan `org.gradle.java.home=E:/JDK25` di `gradle.properties`.
2. **Git & Kloning Ruang Kerja**:
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.2/magnet"
   ```

---

## ⚙️ Konfigurasi Properti (`gradle.properties`)

```properties
org.gradle.parallel=false
org.gradle.java.home=E:/JDK25

# Mod Properties
mod_name=Magnet, Let me get that!
mod_version=1.3.9+26.2
maven_group=net.instantgratification
archives_base_name=Magnet-Let-me-get-that

# Dependencies
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

## 🔨 Perintah Kompilasi Gradle

```bash
# Bersihkan cache build sebelumnya
./gradlew clean

# Jalankan pengujian unit otomatis
./gradlew test

# Kompilasi JAR produksi dan picu pengarsipan otomatis
./gradlew build --no-daemon
```

---

## 📦 Pengarsipan Rilis Otomatis & Sinkronisasi Modrinth

Skrip `build.gradle` MC 26.2 dilengkapi dengan task siklus hidup `archiveReleaseJar`:

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

Setelah kompilasi berhasil, JAR yang dibangun (`Magnet-Let-me-get-that-1.3.9+26.2.jar`) secara otomatis disalin ke `Archive Jar of all versions/MC 26.2/` dan diterapkan ke profil pengujian launcher lokal Modrinth yang aktif.

---

## 🔗 Dokumentasi Wiki Terkait
* [[Arsitektur & Implementasi Mixin|id_id-26.2-Architecture-and-Mixins]]
* [[API & Integrasi Addon|id_id-26.2-API-and-Addon-Integration]]
* [[Kembali ke Portal MC 26.2|id_id-26.2-Home]]\n