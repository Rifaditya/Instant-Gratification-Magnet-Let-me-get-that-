# 🗺️ Matriks Kompatibilitas Versi Lintas Era

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

Halaman ini mendokumentasikan rilis Minecraft yang didukung, lingkungan eksekusi Java, dependensi Fabric Loader, dan toolchain kompilasi untuk **Magnet, Let me get that!** (`ig_magnet`).

---

## 📊 Ikhtisar Siklus Hidup Multi-Versi

| Target Minecraft | Folder Subproyek | Versi Mod Aktif | Target Java | Fabric Loader | Fabric API | DasikLibrary | Penyedia GUI Konfigurasi |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | `Magnet v26.2/magnet` | `1.3.9+26.2` | Java 25 (`release = 25`) | `>=0.19.1` | `0.150.1+26.2` | `1.8.23` | YetAnotherConfigLib v3 (`3.9.5+26.2-fabric`) + ModMenu |
| **Minecraft 26.1.2** | `Magnet v26.1/magnet` | `1.1.2+26.1.2` | Java 25 (`release = 25`) | `>=0.16.10` | `0.145.4+26.1.2` | `1.8.23` | Cloth Config Fabric (`26.1.154`) + ModMenu |

---

## 🔍 Rincian Versi Terperinci

### 🟢 Minecraft 26.2 (`1.3.9+26.2`)
* **Status**: Rilis Modern Utama
* **Jalur Subproyek**: `Magnet v26.2/magnet/`
* **Output Arsip**: `Magnet-Let-me-get-that-1.3.9+26.2.jar`
* **Direktori Arsip Terpusat**: `Archive Jar of all versions/MC 26.2/`
* **Batasan Dependensi (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.19.1",
      "minecraft": ">=26.2-",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **Fitur Arsitektur Utama**:
  * Penyimpanan NBT persisten menggunakan codec `ValueOutput` dan `ValueInput` Minecraft 26.2 di dalam `PlayerMixin`.
  * Retensi status pemain otomatis saat kematian dan teleportasi dimensi melalui event lifecycle Fabric `ServerPlayerEvents.COPY_FROM` dan `ServerPlayerEvents.AFTER_RESPAWN`.
  * Tombol pintas pengalih dipetakan ke `\` (`GLFW_KEY_BACKSLASH`) dengan pembantu dinamis `ig_magnet$getKeyboardType()`.
  * Perintah diagnostik dalam game `/magnet debug` dan `/magnet debug log` dengan pencatatan file khusus (`logs/ig_magnet_debug.log`).
  * `YaclScreenHelper` modern yang memanfaatkan YetAnotherConfigLib v3.

---

### 🟢 Minecraft 26.1.2 (`1.1.2+26.1.2`)
* **Status**: Subproyek Jangkar Modern
* **Jalur Subproyek**: `Magnet v26.1/magnet/`
* **Output Arsip**: `Magnet-Let-me-get-that-1.1.2+26.1.2.jar`
* **Direktori Arsip Terpusat**: `Archive Jar of all versions/MC 26.1.2/`
* **Batasan Dependensi (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.16.10",
      "minecraft": "*",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **Fitur Arsitektur Utama**:
  * Pelacakan status sesi konkurensi melalui `MagnetPlayerState` (`Map<UUID, Boolean>`).
  * Tombol pintas pengalih dipetakan ke `Ctrl+M` (`GLFW_KEY_M` + `isControlDown()`).
  * Umpan balik hamparan actionbar bawaan melalui `client.gui.setOverlayMessage(...)`.
  * GUI konfigurasi opsional aman-refleksi melalui `ClothConfigScreenHelper`.

---

## 📦 Pengarsipan Rilis Otomatis & Penerapan Launcher

Kedua subproyek mengintegrasikan pengarsipan pasca-kompilasi otomatis dalam skrip build Gradle (`build.gradle`):

```bash
# Kompilasi dan arsipkan build MC 26.2 secara otomatis:
cd "Magnet v26.2/magnet"
./gradlew build --no-daemon

# Kompilasi dan arsipkan build MC 26.1.2 secara otomatis:
cd "Magnet v26.1/magnet"
./gradlew build --no-daemon
```

Saat `./gradlew build` dijalankan, task `archiveReleaseJar` secara otomatis menyalin JAR hasil build ke dalam folder arsip terpusat (`Archive Jar of all versions/MC <Version>/`) dan menyinkronkannya dengan profil pengujian launcher lokal Modrinth yang aktif.

---

## 🔗 Dokumentasi Wiki Terkait
* [[Penyiapan & Kompilasi Loom MC 26.2|id_id-26.2-Developer-Setup-and-Building]]
* [[Penyiapan & Kompilasi Loom MC 26.1.2|id_id-26.1.2-Developer-Setup-and-Building]]
* [[Kembali ke Portal Sakelar Pusat|id_id-Home]]
