# ✨ Tarikan Orb Pengalaman (MC 26.1.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Fitur | Parameter Teknis |
| :--- | :--- |
| **Kelas Entitas Target** | `net.minecraft.world.entity.ExperienceOrb` |
| **Kelas Pengelola** | `net.instantgratification.magnet.MagnetManager` |
| **GameRule Pengaktif** | `ig:magnet_affects_xp` (Standar: `true`) |
| **Kueri Pemindaian** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **Tipe Partikel** | `ParticleTypes.ELECTRIC_SPARK` |
| **Batas Sumber Partikel** | `ig:magnet_max_particle_sources` (Standar: `5`) |

---

## 📖 Mekanisme Vakum Pengalaman

Di bawah filosofi Instant Gratification, membiarkan orb pengalaman tertinggal berlawanan dengan ritme permainan yang mulus. Dalam Minecraft 26.1.2, entitas `ExperienceOrb` ditarik dengan fisika kecepatan dan pergeseran fase yang identik dengan drop item.

```
+------------------+    getEntitiesOfClass    +-----------------------+    MagnetMovement.pull    +--------------------+
| Area Pindai AABB | -----------------------> | List<ExperienceOrb>   | ------------------------> | Koleksi Pemain     |
+------------------+                          +-----------------------+                           +--------------------+
                                                          |
                                                          v
                                              [Terapkan NoClip Pergeseran Fase]
                                              [Terapkan Vektor Kecepatan Lerp]
                                              [Batasi Emisi Partikel Percikan]
```

---

## ⚡ Fisika Tersinkronisasi & Pergeseran Fase

1. **Pergeseran Fase (NoClip)**: Orb pengalaman melintasi blok padat untuk menghindari terjebak di balik dinding atau di dalam sudut langit-langit.
2. **Interpolasi Kecepatan**: Orb berakselerasi secara mulus menuju posisi mata pemain menggunakan persentase kecepatan dan akselerasi yang dikonfigurasi.
3. **Penyaringan Garis Pandang**: Ketika `ig:magnet_los_only` true, orb XP harus terlihat oleh pemain atau memiliki momentum magnetisasi aktif.

---

## 🛡️ Pencegahan Lag & Pengumpulan Partikel

```java
int maxParticleSources = ModGameRules.getInt(player.level(), ModGameRules.MAGNET_MAX_PARTICLE_SOURCES);
int particleSourceCount = 0;

for (ExperienceOrb orb : orbs) {
    boolean shouldSpawnParticles = false;
    if (particleSourceCount < maxParticleSources) {
        shouldSpawnParticles = true;
        particleSourceCount++;
    }
    MagnetMovement.pull(orb, player, shouldSpawnParticles);
}
```

* **Maks Sumber Partikel**: Hanya $N$ entitas pertama per tick yang memunculkan jejak visual.
* **Fallback Aman Klien**: Di dalam `MagnetMovement.java`, jika level bukan `ServerLevel`, pemunculan partikel beralih secara aman ke `level.addParticle(...)`.

---

## ⚙️ Konfigurasi & GameRules Terkait

| GameRule | Tipe | Standar | Deskripsi |
| :--- | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | Menentukan apakah magnet menarik Orb Pengalaman. |
| `ig:magnet_particles` | Boolean | `true` | Sakelar utama untuk efek partikel. |
| `ig:magnet_particle_count` | Integer | `1` | Partikel percikan yang dimunculkan per entitas per tick partikel. |
| `ig:magnet_max_particle_sources` | Integer | `5` | Jumlah entitas maksimum yang boleh memancarkan partikel secara bersamaan. |

---

## 🔗 Dokumentasi Wiki Terkait
* [[Fisika Penarikan Vakum & Pergeseran Fase|id_id-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Referensi Lengkap GameRules|id_id-26.1.2-GameRules]]
* [[Kembali ke Portal MC 26.1.2|id_id-26.1.2-Home]]\n