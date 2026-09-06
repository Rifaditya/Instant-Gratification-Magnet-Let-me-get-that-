# 👁️ Garis Pandang & Rintangan (MC 26.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Fitur | Parameter Teknis |
| :--- | :--- |
| **Mesin Penglihatan Utama** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **Mesin Penglihatan Sekunder** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **Bidang Pandang Sferis** | $360.0^\circ$ (Persepsi omnidireksional penuh) |
| **Toleransi Jarak Kontak** | Ambang batas kontak target $0.3\text{ m}$ |
| **Tag Retensi Memori** | `ig_magnet$isMagnetized()` / `ig_magnet$setMagnetized()` |
| **Aturan Momentum** | `ig:magnet_keep_moving_if_unseen = true` |
| **Aturan Filter Granular** | Blok transparan, Flora, Entitas Blok |

---

## 📖 Arsitektur Penglihatan Jalur Ganda (Dual-Pass)

Untuk mencegah pengumpulan item tanpa usaha melalui dinding gua atau markas tertutup sekaligus mempertahankan performa bebas lag, **Magnet, Let me get that!** menggunakan **Pipa Penglihatan Jalur Ganda (Dual-Pass Vision Pipeline)** berkecepatan tinggi:

```
                                +---------------------------+
                                |    ITEM TARGET TERDETEKSI |
                                +-------------+-------------+
                                              |
                                              v
                              +-------------------------------+
                              |    JALUR UTAMA (360° LOS)     |
                              |   PlayerVisionTracker.canSee  |
                              +---------------+---------------+
                                              |
                        +---------------------+---------------------+
                        |                                           |
                   [TERLIHAT]                                   [TERHALANG]
                        |                                           |
                        v                                           v
      +-----------------------------------+               +--------------------+
      |     JALUR SEKUNDER (GRANULAR)     |               | PEMERIKSAAN MOMENTUM|
      |     SecondaryVisionCheck.canSee   |               | keepMovingIfUnseen |
      +-----------------+-----------------+               +---------+----------+
                        |                                           |
            +-----------+-----------+                     +---------+---------+
            |                       |                     |                   |
        [LOLOS]                 [DIBLOKIR]            [TERMAGNETISASI]  [TIDAK TERMAGNETISASI]
            |                       |                     |                   |
            v                       v                     v                   v
    +---------------+       +---------------+     +---------------+   +---------------+
    |  TARIK ITEM & |       | TOLAK / HENTI |     | LANJUT TARIK  |   | TOLAK TARIK   |
    | SET MAGNETISASI|      +---------------+     +---------------+   +---------------+
    +---------------+
```

---

## 🔍 Jalur 1: Pemeriksaan Garis Pandang Sferis 360° Utama

Jalur utama memanggil mesin raycasting DasikLibrary yang sangat teroptimasi:
```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **FOV Omnidireksional**: Dengan kerucut FOV $360.0^\circ$, pemain dapat menarik item tepat di belakang kepala, di atas kepala, atau di bawah kaki mereka selama tidak terhalang oleh dinding padat.
* **Toleransi Kontak 0.3m**: Ketika item terselip rapat di sudut blok, raycasting sub-voxel dengan radius $0.3\text{m}$ memastikan item tidak ditolak secara keliru.

---

## 🌿 Jalur 2: Pemfilteran Status Blok Granular (`SecondaryVisionCheck`)

Jika jalur utama berhasil, mod mengevaluasi aturan rintangan granular opsional menggunakan `BlockGetter.traverseBlocks`:

```java
public static boolean canSee(Player player, Entity target, boolean blockTransparent, boolean blockFlora, boolean blockEntities)
```

1. **Pemfilteran Flora & Dedaunan (`ig:magnet_blocked_by_flora`)**:
   - Memeriksa apakah blok yang dilintasi merupakan turunan dari `BushBlock` (rumput tinggi, bunga, tanaman pangan, bibit) atau `LeavesBlock` (dedaunan pohon).
   - Jika diaktifkan (`true`), vegetasi bertindak sebagai penghalang buram bagi tarikan item.
2. **Entitas Blok Interaktif (`ig:magnet_blocked_by_block_entities`)**:
   - Memeriksa `state.hasBlockEntity()` di sepanjang posisi yang dilintasi.
   - Jika diaktifkan (`true`), Peti (Chest), Peti Perangkap (Trapped Chest), Tong (Barrel), Kotak Shulker (Shulker Box), Tempat Tidur, dan Dispenser akan memblokir garis pandang.
3. **Blok Transparan & Non-Penuh (`ig:magnet_blocked_by_transparent`)**:
   - Melakukan raycast terhadap `state.getVisualShape(...)`.
   - Jika diaktifkan (`true`), Kaca, Panel Kaca, Batang Besi, Pagar, Slab, dan Tangga akan memblokir pengumpulan item.

---

## 🚀 Kontinuitas Momentum (`keepMovingIfUnseen`)

Dalam penambangan cepat atau pertempuran, item yang ditarik memutari sudut sering kali memutus garis pandang untuk sementara. Alih-alih membeku di tempat atau jatuh ke lava:

1. **Tag Magnetisasi**: Ketika item terlihat dalam garis pandang, `((IMagnetEntity) entity).ig_magnet$setMagnetized()` mengatur flag boolean dalam memori.
2. **Retensi Momentum**: Jika item kehilangan garis pandang pada tick berikutnya:
   - Jika `ig:magnet_keep_moving_if_unseen = true` DAN `ig_magnet$isMagnetized() == true`: Item terus ditarik menuju pemain.
   - Jika `ig:magnet_keep_moving_if_unseen = false`: Penarikan langsung berhenti seketika garis pandang terputus.
   - Jika item **tidak pernah terlihat** sama sekali (misalnya muncul di balik dinding karena ledakan atau dispenser): Penarikan langsung ditolak sejak awal.

---

## ⚙️ Konfigurasi & GameRules Terkait

| GameRule | Tipe | Standar | Deskripsi |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | Mewajibkan visibilitas garis pandang untuk menarik item. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | Melanjutkan penarikan item yang sudah termagnetisasi jika LOS terputus di tengah jalan. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | Jika true, kaca dan blok transparan menghalangi garis pandang. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | Jika true, rumput, dedaunan, dan bunga menghalangi garis pandang. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | Jika true, peti, tempat tidur, dan entitas blok menghalangi garis pandang. |

---

## 🔗 Dokumentasi Wiki Terkait
* [[Fisika Penarikan Vakum & Pergeseran Fase|id_id-26.2-Vacuum-and-Phase-Shifting]]
* [[Referensi Lengkap GameRules|id_id-26.2-GameRules]]
* [[Arsitektur & Mixin|id_id-26.2-Architecture-and-Mixins]]
* [[Kembali ke Portal MC 26.2|id_id-26.2-Home]]\n