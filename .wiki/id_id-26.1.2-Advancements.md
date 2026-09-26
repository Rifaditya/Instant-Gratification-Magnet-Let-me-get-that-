# 🏆 Kemajuan & Ruang Lingkup Progresi (MC 26.1.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Progresi | Detail |
| :--- | :--- |
| **JSON Kemajuan Kustom** | `Tidak Ada (Ketergantungan Vanilla Berdasarkan Desain)` |
| **Jalur Desain** | Instant Gratification (IG) |
| **Pemicu Kemajuan** | 100% Asli Vanilla `Player.touch(ItemEntity)` |
| **Persyaratan Pembuka** | Tidak ada (Nol rintangan pembukaan buatan) |

---

## 📖 Kebijakan Ketiadaan & Ruang Lingkup Desain

Dalam kepatuhan ketat pada filosofi modding **Instant Gratification (IG)**, **Magnet, Let me get that!** secara sengaja **tidak berisi pohon kemajuan kustom atau pencapaian buatan**.

Mod ini direkayasa sebagai augmentasi kenyamanan hidup (quality-of-life) intrinsik untuk interaksi bertahan hidup inti pemain. Semua fitur langsung terbuka sejak detik pertama pemain memasuki dunia, tanpa grinding quest buatan, pohon riset, atau penghalang progresi.

```
+-----------------------------------------------------------------------------------+
|                     PRINSIP DESAIN INSTANT GRATIFICATION                          |
|                                                                                   |
|  "Jalan Rasa Bersalah (berjalan 5 blok untuk mengambil blok yang baru ditambang) |
|   adalah dosa besar terhadap ritme bermain. IG Magnet bukanlah item pohon riset   |
|   atau hadiah; melainkan perpanjangan dari kehendak pemain. Jika Anda bisa        |
|   melihatnya, Anda berhak memilikinya."                                          |
+-----------------------------------------------------------------------------------+
```

---

## 🎮 Kompatibilitas Kemajuan Vanilla Asli

Karena mode vakum standar maupun [[Mode Pengambilan Instan|id_id-26.1.2-Instant-Pickup-Mode]] memanfaatkan pipa koleksi bawaan `ItemEntity.playerTouch()` dan `ExperienceOrb.playerTouch()` milik Minecraft:

1. **Pemicu Pencapaian Vanilla**: Mengambil berlian, serpihan kuno (ancient debris), atau blaze rod melalui magnet akan langsung memicu kriteria kemajuan vanilla (misalnya `"Diamonds!"`, `"Cover Me in Debris"`).
2. **Kompatibilitas Quest Pihak Ketiga**: Mod quest yang melacak pengambilan item dalam inventaris pemain berfungsi secara langsung tanpa perlu jembatan kompatibilitas khusus.
3. **Pelacakan Statistik**: Statistik vanilla (`stat.pickup.minecraft.*`) terus bertambah secara akurat.

---

## 🔗 Dokumentasi Wiki Terkait
* [[Mode Pengambilan Instan|id_id-26.1.2-Instant-Pickup-Mode]]
* [[Referensi Lengkap GameRules|id_id-26.1.2-GameRules]]
* [[Kembali ke Portal MC 26.1.2|id_id-26.1.2-Home]]\n