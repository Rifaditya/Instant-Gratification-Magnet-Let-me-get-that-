# 🧲 Magnet, Let me get that! — Portal Minecraft 26.1.2

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

Selamat datang di portal dokumentasi **Minecraft 26.1.2** untuk **Magnet, Let me get that!** (Build `1.1.2+26.1.2`).

Edisi jangkar ini menyediakan pengalaman vakum item dan XP Instant Gratification yang lengkap dengan integrasi Cloth Config, pengelolaan status sesi konkurensi, dan raycasting sferis 360°.

---

## 📋 Spesifikasi Cepat Minecraft 26.1.2

| Spesifikasi | Nilai Target | Pengidentifikasi Referensi |
| :--- | :--- | :--- |
| **Target Rilis Minecraft** | `26.1.2` | `"minecraft": "*"` |
| **Build Subproyek Aktif** | `1.1.2+26.1.2` | `mod_version=1.1.2+26.1.2` |
| **Toolchain Java** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.16.10` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` | `fabric_version=0.145.4+26.1.2` |
| **Pustaka Bersama Inti** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **Tombol Pintas Bawaan Klien** | `Ctrl+M` | `GLFW.GLFW_KEY_M` + `isControlDown()` |
| **GUI Konfigurasi** | Cloth Config Fabric | `ClothConfigScreenHelper` + ModMenu |
| **Perintah Server** | `/magnet toggle`, `/ig_magnet toggle` | `MagnetCommand.java` |

---

## ⚡ Sorotan Fitur Utama

* **Pemeriksaan Garis Pandang Sferis 360°**: Ditenagai oleh `PlayerVisionTracker` milik DasikLibrary 1.8.23. Lihat [[Garis Pandang & Rintangan|id_id-26.1.2-Line-of-Sight-and-Obstruction]].
* **Fisika NoClip Pergeseran Fase**: Item melintasi medan padat selama tarikan vakum. Lihat [[Pergerakan Vakum & Pergeseran Fase|id_id-26.1.2-Vacuum-and-Phase-Shifting]].
* **Mode Pengambilan Instan**: Pemekaran kotak pembatas opsional untuk pengambilan item tanpa latensi. Lihat [[Mode Pengambilan Instan|id_id-26.1.2-Instant-Pickup-Mode]].
* **Sakelar Sesi Konkuren**: Status tombol pintas dan perintah dikelola melalui `MagnetPlayerState`. Lihat [[Sakelar Pemain & Status Sesi|id_id-26.1.2-Player-Toggle-and-Persistence]].
* **GUI Cloth Config**: Layar pengaturan rapi dalam game dengan pemberitahuan peringatan kategori. Lihat [[GUI Cloth Config|id_id-26.1.2-Configuration]].

---

## 📑 Indeks Dokumentasi 26.1.2

### 🎮 Gameplay & Administrasi
* [[Fisika Penarikan Vakum & Pergeseran Fase|id_id-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Penetrasi Rintangan & Garis Pandang|id_id-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Sinkronisasi Orb Pengalaman|id_id-26.1.2-Experience-Orb-Attraction]]
* [[Mode Pengambilan Instan & Pemekaran Kotak Pembatas|id_id-26.1.2-Instant-Pickup-Mode]]
* [[Sakelar Pemain, Tombol Pintas & Penyimpanan Status|id_id-26.1.2-Player-Toggle-and-Persistence]]
* [[Referensi GameRules & Batas Standar|id_id-26.1.2-GameRules]]
* [[Perintah Server & Dukungan Klien Vanilla|id_id-26.1.2-Commands]]
* [[Kemajuan & Ketergantungan Vanilla|id_id-26.1.2-Advancements]]
* [[GUI Cloth Config & ModMenu|id_id-26.1.2-Configuration]]
* [[HUD Actionbar & Efek Visual|id_id-26.1.2-HUD-and-Diagnostics]]

### 💻 Referensi Pengembang & Rekayasa
* [[Penyiapan Pengembang, Toolchain & Gradle Loom|id_id-26.1.2-Developer-Setup-and-Building]]
* [[Arsitektur, Paket & Injeksi Mixin|id_id-26.1.2-Architecture-and-Mixins]]
* [[Fasad API, Antarmuka & Kait Addon|id_id-26.1.2-API-and-Addon-Integration]]
* [[Kembali ke Matriks Versi Lintas Era|id_id-Version-Compatibility]]\n