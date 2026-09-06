# 🧲 Magnet, Let me get that! — Wiki Resmi

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

Selamat datang di wiki teknis dan gameplay resmi untuk **Magnet, Let me get that!** (`ig_magnet`), mod penarik (vakum) item dan orb pengalaman intrinsik yang dirancang untuk Minecraft modern pada Fabric.

Dibangun dengan kokoh di atas filosofi desain **Instant Gratification (IG)**, mod ini melenyapkan "jalan rasa bersalah" (walk of shame)—gesekan melelahkan ketika harus berjalan 5 blok hanya untuk mengambil item yang baru saja Anda tambang atau kalahkan. Jika Anda bisa melihatnya, Anda berhak memilikinya.

---

## 🧭 Portal Sakelar Multi-Versi

Pilih versi Minecraft target Anda untuk mengakses panduan gameplay terisolasi khusus, dokumentasi teknis, tabel referensi GameRules, dan arsitektur kode:

| Versi Minecraft | Status Rilis | Build Versi Aktif | Mesin Konfigurasi | Tautan Portal |
| :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | 🟢 Modern Aktif | `1.3.9+26.2` | YACL v3 + ModMenu | [[Ikhtisar 26.2|id_id-26.2-Home]] |
| **Minecraft 26.1.2** | 🟢 Jangkar Modern | `1.1.2+26.1.2` | Cloth Config + ModMenu | [[Ikhtisar 26.1.2|id_id-26.1.2-Home]] |

### 🚀 Portal Versi Langsung:
* 📦 **Minecraft 26.2**: [[👉 Masuk ke Portal Dokumentasi Minecraft 26.2|id_id-26.2-Home]]
* 📦 **Minecraft 26.1.2**: [[👉 Masuk ke Portal Dokumentasi Minecraft 26.1.2|id_id-26.1.2-Home]]

Untuk rincian mendalam tentang toolchain, matriks dependensi, lokasi penyimpanan arsip, dan kompatibilitas ke belakang, lihat [[Matriks Kompatibilitas Versi|id_id-Version-Compatibility]].

---

## ⚡ Matriks Fitur Inti

```
                      +-----------------------------+
                      |   PLAYER VACUUM EMITTER     |
                      +--------------+--------------+
                                     |
              +----------------------+----------------------+
              |                                             |
              v                                             v
  +-----------------------+                     +-----------------------+
  |  STANDARD PULL MODE   |                     |  INSTANT PICKUP MODE  |
  +-----------+-----------+                     +-----------+-----------+
              |                                             |
     [Line-of-Sight Check]                         [AABB Box Inflation]
     [Spherical Raycast 360°]                      [Zero Travel Latency]
     [Phase-Shift NoClip]                          [Direct Inventory]
     [Dynamic Lerp Velocity]                                |
              |                                             |
              +----------------------+----------------------+
                                     |
                                     v
                      +-----------------------------+
                      |   ITEM & XP ORB CAPTURED    |
                      +-----------------------------+
```

* **Vakum Cerdas 360°**: Menarik drop item dan orb pengalaman dalam radius blok yang dapat dikonfigurasi (standar: 12 blok, hingga 64 blok).
* **Pergeseran Fase (Phase Shifting / NoClip)**: Item yang termagnetisasi melewati dinding dan blok padat tanpa hambatan, mencegah drop terjebak permanen di dalam puing ledakan atau celah tambang.
* **Kesadaran Garis Pandang (Line-of-Sight / LOS)**: Menggunakan raycasting sferis 360° primer melalui `PlayerVisionTracker` dari DasikLibrary dan filter granular sekunder opsional terhadap blok transparan (kaca), flora (rumput tinggi, dedaunan), dan entitas blok (peti/chests).
* **Kontinuitas Momentum (`keepMovingIfUnseen`)**: Begitu termagnetisasi saat terlihat, item mempertahankan momentum pergerakannya bahkan saat berayun sementara ke balik rintangan.
* **Opsi Mode Pengambilan Instan**: Memperbesar kotak pembatas koleksi bawaan pemain untuk menyerap item seketika dengan latensi terbang nol mikrodetik.
* **Kontrol Tombol Pintas & Perintah Pemain**: Beralih status magnet di sisi klien melalui tombol pintas (`\` pada 26.2, `Ctrl+M` pada 26.1.2) atau di sisi server melalui perintah `/magnet toggle`.
* **Nol Kekacauan Inventaris**: 100% fungsionalitas intrinsik—tanpa item magnet kustom, bauble, baterai, atau slot inventaris terbuang.

---

## 📚 Navigasi Ensiklopedis

### 🎮 Panduan Pemain & Administrator
* [[Ikhtisar MC 26.2|id_id-26.2-Home]] & [[Ikhtisar MC 26.1.2|id_id-26.1.2-Home]]
* [[Pergerakan Vakum & Pergeseran Fase MC 26.2|id_id-26.2-Vacuum-and-Phase-Shifting]] & [[Pergerakan Vakum & Pergeseran Fase MC 26.1.2|id_id-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Pemeriksaan Garis Pandang MC 26.2|id_id-26.2-Line-of-Sight-and-Obstruction]] & [[Pemeriksaan Garis Pandang MC 26.1.2|id_id-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Tarikan Orb Pengalaman MC 26.2|id_id-26.2-Experience-Orb-Attraction]] & [[Tarikan Orb Pengalaman MC 26.1.2|id_id-26.1.2-Experience-Orb-Attraction]]
* [[Mode Pengambilan Instan MC 26.2|id_id-26.2-Instant-Pickup-Mode]] & [[Mode Pengambilan Instan MC 26.1.2|id_id-26.1.2-Instant-Pickup-Mode]]
* [[Sakelar Pemain & Persistensi MC 26.2|id_id-26.2-Player-Toggle-and-Persistence]] & [[Sakelar Pemain & Persistensi MC 26.1.2|id_id-26.1.2-Player-Toggle-and-Persistence]]
* [[Referensi GameRules MC 26.2|id_id-26.2-GameRules]] & [[Referensi GameRules MC 26.1.2|id_id-26.1.2-GameRules]]
* [[Perintah MC 26.2|id_id-26.2-Commands]] & [[Perintah MC 26.1.2|id_id-26.1.2-Commands]]
* [[Kemajuan MC 26.2|id_id-26.2-Advancements]] & [[Kemajuan MC 26.1.2|id_id-26.1.2-Advancements]]
* [[GUI Konfigurasi MC 26.2|id_id-26.2-Configuration]] & [[GUI Konfigurasi MC 26.1.2|id_id-26.1.2-Configuration]]
* [[HUD & Diagnostik MC 26.2|id_id-26.2-HUD-and-Diagnostics]] & [[HUD & Diagnostik MC 26.1.2|id_id-26.1.2-HUD-and-Diagnostics]]

### 💻 Dokumentasi Pengembang & Kontributor
* [[Penyiapan & Kompilasi Pengembang MC 26.2|id_id-26.2-Developer-Setup-and-Building]] & [[Penyiapan & Kompilasi Pengembang MC 26.1.2|id_id-26.1.2-Developer-Setup-and-Building]]
* [[Arsitektur & Target Mixin MC 26.2|id_id-26.2-Architecture-and-Mixins]] & [[Arsitektur & Target Mixin MC 26.1.2|id_id-26.1.2-Architecture-and-Mixins]]
* [[API & Integrasi Addon MC 26.2|id_id-26.2-API-and-Addon-Integration]] & [[API & Integrasi Addon MC 26.1.2|id_id-26.1.2-API-and-Addon-Integration]]
* [[Matriks Kompatibilitas Versi Lintas Era|id_id-Version-Compatibility]]

---

## ⚖️ Lisensi & Atribusi

Dikembangkan oleh **Dasik (Rifaditya)** di bawah lisensi **GNU General Public License v3.0 (GPLv3)**. Lihat `LICENSE` untuk ketentuan hukum dan perizinan lengkap.
