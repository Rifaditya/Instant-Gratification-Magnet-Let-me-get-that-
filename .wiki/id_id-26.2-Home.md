# 🧲 Magnet, Let me get that! — Portal Minecraft 26.2

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

Selamat datang di portal dokumentasi **Minecraft 26.2** untuk **Magnet, Let me get that!** (Build `1.3.9+26.2`).

Edisi modern ini menghadirkan retensi status NBT persisten, sinkronisasi respawn siklus hidup Fabric, raycasting sferis 360° melalui DasikLibrary 1.8.23, dan antarmuka pengguna konfigurasi YetAnotherConfigLib v3 (YACL).

---

## 📋 Spesifikasi Cepat Minecraft 26.2

| Spesifikasi | Nilai Target | Pengidentifikasi Referensi |
| :--- | :--- | :--- |
| **Target Rilis Minecraft** | `26.2` | `"minecraft": ">=26.2-"` |
| **Build Subproyek Aktif** | `1.3.9+26.2` | `mod_version=1.3.9+26.2` |
| **Toolchain Java** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.19.1` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.150.1+26.2` | `fabric_version=0.150.1+26.2` |
| **Pustaka Bersama Inti** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **Tombol Pintas Bawaan Klien** | `\` (Backslash) | `GLFW.GLFW_KEY_BACKSLASH` |
| **GUI Konfigurasi** | YetAnotherConfigLib v3 | `YaclScreenHelper` + ModMenu |
| **Perintah Diagnostik** | `/magnet toggle`, `/magnet debug`, `/magnet debug log` | `MagnetCommand.java` |

---

## ⚡ Sorotan Fitur Utama

* **Pemeriksaan Garis Pandang Sferis 360°**: Mencegah penarikan item secara curang menembus dinding padat sekaligus memberikan penarikan visibilitas penuh di seluruh sudut pandang pemain. Lihat [[Garis Pandang & Rintangan|id_id-26.2-Line-of-Sight-and-Obstruction]].
* **Pergerakan NoClip Pergeseran Fase**: Item yang termagnetisasi menembus blok secara mulus untuk mencapai ketinggian mata pemain tanpa tersangkut. Lihat [[Pergerakan Vakum & Pergeseran Fase|id_id-26.2-Vacuum-and-Phase-Shifting]].
* **Pengambilan Instan Nol Latensi**: Pemekaran kotak pembatas opsional yang menyerap drop item ke inventaris seketika dengan 0 jeda fisika. Lihat [[Mode Pengambilan Instan|id_id-26.2-Instant-Pickup-Mode]].
* **Status Pemain Persisten**: Sakelar bertahan melewati kematian pemain, respawn, perpindahan dimensi, dan mulai ulang server melalui NBT (`ValueOutput`/`ValueInput`) dan `ServerPlayerEvents.COPY_FROM`. Lihat [[Sakelar Pemain & Persistensi Status|id_id-26.2-Player-Toggle-and-Persistence]].
* **Rangkaian Perintah Diagnostik**: Alat diagnostik bawaan `/magnet debug` dan `/magnet debug log` untuk admin server. Lihat [[Perintah Brigadier|id_id-26.2-Commands]] dan [[HUD & Diagnostik|id_id-26.2-HUD-and-Diagnostics]].

---

## 📑 Indeks Dokumentasi 26.2

### 🎮 Gameplay & Administrasi
* [[Fisika Penarikan Vakum & Pergeseran Fase|id_id-26.2-Vacuum-and-Phase-Shifting]]
* [[Penetrasi Rintangan & Garis Pandang|id_id-26.2-Line-of-Sight-and-Obstruction]]
* [[Sinkronisasi Orb Pengalaman|id_id-26.2-Experience-Orb-Attraction]]
* [[Mode Pengambilan Instan & Kotak Pembatas AABB|id_id-26.2-Instant-Pickup-Mode]]
* [[Sakelar Pemain, Persistensi & Siklus Hidup|id_id-26.2-Player-Toggle-and-Persistence]]
* [[Referensi GameRules & Batas Standar|id_id-26.2-GameRules]]
* [[Perintah Brigadier & Diagnostik Dalam Game|id_id-26.2-Commands]]
* [[Kemajuan & Ketergantungan Vanilla|id_id-26.2-Advancements]]
* [[GUI Konfigurasi YACL & ModMenu|id_id-26.2-Configuration]]
* [[HUD Actionbar & Pencatatan Khusus|id_id-26.2-HUD-and-Diagnostics]]

### 💻 Referensi Pengembang & Rekayasa
* [[Penyiapan Pengembang, Toolchain & Gradle Loom|id_id-26.2-Developer-Setup-and-Building]]
* [[Arsitektur, Paket & Injeksi Mixin|id_id-26.2-Architecture-and-Mixins]]
* [[Fasad API, Antarmuka & Kait Addon|id_id-26.2-API-and-Addon-Integration]]
* [[Kembali ke Matriks Versi Lintas Era|id_id-Version-Compatibility]]\n