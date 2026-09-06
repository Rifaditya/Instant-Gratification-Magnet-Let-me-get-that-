# 👁️ Garis Pandang & Rintangan (MC 26.1.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Fitur | Parameter Teknis |
| :--- | :--- |
| **Mesin Penglihatan Utama** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **Mesin Penglihatan Sekunder** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **Bidang Pandang Sferis** | $360.0^\circ$ (Persepsi omnidireksional penuh) |
| **Toleransi Jarak Kontak** | Ambang batas kontak target $0.3\text{ m}$ |
| **Tag Retensi Memori** | `ig$isMagnetized()` / `ig$setMagnetized()` |
| **Aturan Momentum** | `ig:magnet_keep_moving_if_unseen = true` |
| **Implementasi Konteks** | Record statis `VisionContext` |

---

## 📖 Pipa Penglihatan Jalur Ganda (Dual-Pass)

Dalam Minecraft 26.1.2, garis pandang dievaluasi melalui **Pipa Penglihatan Jalur Ganda (Dual-Pass Vision Pipeline)** bebas alokasi:

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

```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **Bidang Omnidireksional**: Dengan sudut FOV $360^\circ$, item yang jatuh di atas, di bawah, atau di belakang pemain ditarik secara mulus tanpa memaksa pemutaran kamera.
* **Toleransi Kontak Sub-Voxel**: Margin $0.3\text{m}$ mencegah penolakan keliru saat item menempel rapat pada dinding padat.

---

## 🌿 Jalur 2: Penelusuran Blok Granular (`SecondaryVisionCheck`)

```java
private record VisionContext(
        Level level,
        boolean blockTransparent,
        boolean blockFlora,
        boolean blockEntities,
        CollisionContext collisionContext,
        Vec3 start,
        Vec3 end
) {}
```

* **Flora (`ig:magnet_blocked_by_flora`)**: Memeriksa `BushBlock` dan `LeavesBlock`.
* **Entitas Blok (`ig:magnet_blocked_by_block_entities`)**: Memeriksa `state.hasBlockEntity()` (Peti, Kotak Shulker, Tempat Tidur).
* **Blok Transparan (`ig:magnet_blocked_by_transparent`)**: Menguji `state.getVisualShape().clip(...)` terhadap Kaca, Panel Kaca, dan Slab.

---

## 🚀 Kontinuitas Momentum (`keepMovingIfUnseen`)

* Saat pertama kali terlihat, `((IMagnetEntity) entity).ig$setMagnetized()` menandai entitas tersebut.
* Jika `ig:magnet_keep_moving_if_unseen = true`, item terus ditarik memutari rintangan selama sebelumnya telah termagnetisasi.

---

## ⚙️ Konfigurasi & GameRules Terkait

| GameRule | Tipe | Standar | Deskripsi |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | Mewajibkan garis pandang untuk menarik item. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | Melanjutkan penarikan item yang sudah termagnetisasi jika LOS terputus di tengah jalan. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | Jika true, kaca dan blok transparan memblokir garis pandang. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | Jika true, rumput dan bunga memblokir garis pandang. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | Jika true, peti dan entitas blok memblokir garis pandang. |

---

## 🔗 Dokumentasi Wiki Terkait
* [[Fisika Penarikan Vakum & Pergeseran Fase|id_id-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Referensi Lengkap GameRules|id_id-26.1.2-GameRules]]
* [[Kembali ke Portal MC 26.1.2|id_id-26.1.2-Home]]\n