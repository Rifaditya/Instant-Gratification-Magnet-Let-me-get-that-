# ⚙️ Referensi Lengkap GameRules (MC 26.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Kategori | Detail |
| :--- | :--- |
| **ID Kategori** | `magnet:magnet_category` |
| **Judul Terjemahan** | `Magnet, Let me get that!` (`gamerule.category.magnet.magnet_category`) |
| **Pengelola Terdaftar** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **Kelas Registri** | `net.instantgratification.magnet.registry.ModGameRules` |
| **Total Aturan Terdaftar** | `15` Aturan Ber-namespace |

---

## 📖 Administrasi GameRules Dalam Game

Semua mekanisme global dari **Magnet, Let me get that!** diatur oleh GameRules Minecraft ber-namespace yang terdaftar di bawah tajuk `magnet:magnet_category`.

Admin dapat mengonfigurasi aturan ini secara langsung di dalam permainan menggunakan perintah `/gamerule <aturan> <nilai>` atau melalui layar bawaan "Edit Game Rules" saat membuat atau mengedit dunia.

```
/gamerule ig:magnet_enabled true
/gamerule ig:magnet_range 24
/gamerule ig:magnet_speed 120
/gamerule ig:magnet_instant true
```

---

## 📋 Tabel Referensi Lengkap GameRules

| Pengidentifikasi GameRule | Tipe | Standar | Batasan | Nama Tampilan Terjemahan | Deskripsi & Efek Gameplay |
| :--- | :---: | :---: | :---: | :--- | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | **Magnet Enabled** | Sakelar utama yang mengaktifkan atau menonaktifkan sistem vakum item secara global. |
| `ig:magnet_range` | Integer | `12` | `1..64` | **Magnet Range** | Radius blok sferis tempat pemain menarik drop item. |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | **Magnet Noclip** | Mengaktifkan pergeseran fase, memungkinkan item yang ditarik menembus blok padat secara bebas. |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | **Attract XP Orbs** | Menentukan apakah orb pengalaman ikut ditarik bersama drop item. |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | **Magnet Particles** | Memunculkan partikel percikan listrik di sepanjang lintasan entitas yang ditarik. |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | **Particle Count** | Jumlah partikel percikan yang dipancarkan per sumber aktif per tick partikel. |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | **Max Particle Sources** | Jumlah maksimum entitas simultan yang diizinkan memancarkan partikel untuk mencegah lag FPS. |
| `ig:magnet_speed` | Integer | `80` | `1..1000` | **Item Speed** | Persentase kecepatan terminal ($80 = 0.8\text{ blok/tick} = 16.0\text{ m/detik}$). |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000` | **Item Acceleration** | Faktor interpolasi akselerasi per tick ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_instant` | Boolean | `false` | `true/false` | **Instant Pickup** | Jika true, item langsung berpindah ke inventaris melalui pemekaran kotak AABB tanpa waktu terbang. |
| `ig:magnet_los_only` | Boolean | `true` | `true/false` | **Line of Sight Only** | Mewajibkan visibilitas garis pandang; mencegah penarikan item di balik rintangan tak tembus pandang. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | `true/false` | **Keep Moving if Unseen** | Memungkinkan item yang termagnetisasi saat terlihat tetap mempertahankan momentum tarikan jika pandangan terputus di tengah jalan. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | `true/false` | **Blocked by Transparent** | Jika true, kaca, panel kaca, batang besi, dan blok tembus cahaya memblokir garis pandang. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | `true/false` | **Blocked by Flora** | Jika true, rumput tinggi, tanaman pangan, bunga, dan dedaunan memblokir garis pandang. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | `true/false` | **Blocked by Block Entities** | Jika true, peti, tempat tidur, tong, dan kotak shulker memblokir garis pandang. |

---

## ⚖️ Prioritas GameRules Dibandingkan Konfigurasi JSON Global

> ⚠️ **Pemberitahuan Penting untuk Admin Server**:  
> Pengaturan dalam file konfigurasi JSON klien/server (`config/ig_magnet.json`) hanya menetapkan **nilai dasar bawaan untuk dunia yang BARU dibuat**. Dunia aktif dikendalikan secara mutlak oleh GameRules. Mengubah `ig_magnet.json` tidak akan mengubah pengaturan dalam penyimpanan dunia yang sudah ada sampai diperbarui via `/gamerule`.

---

## 🔗 Dokumentasi Wiki Terkait
* [[Fisika Penarikan Vakum & Pergeseran Fase|id_id-26.2-Vacuum-and-Phase-Shifting]]
* [[Penetrasi Rintangan & Garis Pandang|id_id-26.2-Line-of-Sight-and-Obstruction]]
* [[GUI Konfigurasi & Sinkronisasi JSON|id_id-26.2-Configuration]]
* [[Kembali ke Portal MC 26.2|id_id-26.2-Home]]\n