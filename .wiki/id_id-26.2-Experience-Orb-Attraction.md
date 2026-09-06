# ✨ Tarikan Orb Pengalaman (MC 26.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Fitur | Parameter Teknis |
| :--- | :--- |
| **Kelas Entitas Target** | `net.minecraft.world.entity.ExperienceOrb` |
| **Kelas Pengelola** | `net.instantgratification.magnet.MagnetManager` |
| **GameRule Pengaktif** | `ig:magnet_affects_xp` (Standar: `true`) |
| **Kueri Pemindaian** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **Tipe Partikel** | `ParticleTypes.ELECTRIC_SPARK` |
| **Pembatas Partikel** | Berbagi batas dengan item melalui `ig:magnet_max_particle_sources` |

---

## 📖 Mekanisme Vakum Pengalaman

Berdasarkan filosofi Instant Gratification, membiarkan orb pengalaman berserakan di tanah atau tersangkut di langit-langit gua akan merusak ritme gameplay. **Magnet, Let me get that!** menyediakan dukungan kelas satu untuk menarik entitas `ExperienceOrb` bersamaan dengan drop item.

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

Ketika `ig:magnet_affects_xp` diaktifkan, semua orb pengalaman dalam jangkauan mewarisi mekanika canggih yang sama persis seperti drop item:

1. **Pergeseran Fase (NoClip)**: Orb pengalaman menembus blok padat saat ditarik, mencegahnya berputar-putar tanpa henti atau memantul di dinding.
2. **Akselerasi Dinamis**: Orb pengalaman mengikuti perhitungan lerp kecepatan ($s = \text{speed}/100.0$) dan akselerasi ($a = \text{accel}/100.0$) yang sama.
3. **Penyaringan Garis Pandang**: Jika `ig:magnet_los_only` bernilai true, orb XP harus lolos pemeriksaan raycast sferis 360° utama maupun pemeriksaan granular sekunder.

---

## 🛡️ Performa & Pencegahan Lag (Pembatasan Partikel)

Farm monster berkepadatan tinggi atau pertarungan bos Ender Dragon dapat menghasilkan ratusan orb pengalaman secara bersamaan. Memunculkan partikel pada setiap orb per tick dapat menyebabkan penurunan drastis FPS klien.

Mod ini mencegah lag partikel melalui **Pembatasan Sumber Partikel Global**:

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

* **Batas Global**: Hanya $N$ entitas pertama (dikonfigurasi oleh `ig:magnet_max_particle_sources`, standar: `5`) yang diizinkan memancarkan partikel percikan pada tick tertentu.
* **Penjarakan Waktu (Tick Staggering)**: Partikel hanya dimunculkan saat `(entity.tickCount + entity.getId()) % 4 == 0` (setiap 4 tick = 5 kali per detik).

---

## ⚙️ Konfigurasi & GameRules Terkait

| GameRule | Tipe | Standar | Satuan / Rentang | Deskripsi |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | Menentukan apakah orb pengalaman ditarik oleh magnet. |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | Sakelar utama untuk jejak visual percikan listrik. |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | Jumlah partikel percikan yang dimunculkan per sumber aktif. |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | Jumlah maksimum entitas simultan yang boleh memancarkan partikel. |

---

## 🔗 Dokumentasi Wiki Terkait
* [[Fisika Penarikan Vakum & Pergeseran Fase|id_id-26.2-Vacuum-and-Phase-Shifting]]
* [[Referensi Lengkap GameRules|id_id-26.2-GameRules]]
* [[HUD & Diagnostik Visual|id_id-26.2-HUD-and-Diagnostics]]
* [[Kembali ke Portal MC 26.2|id_id-26.2-Home]]\n