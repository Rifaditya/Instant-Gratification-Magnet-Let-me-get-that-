# 🎨 GUI Konfigurasi YACL & ModMenu (MC 26.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Konfigurasi | Detail |
| :--- | :--- |
| **Jalur File Konfigurasi** | `config/ig_magnet.json` |
| **Pustaka GUI** | YetAnotherConfigLib v3 (`dev.isxander:yet-another-config-lib`) |
| **Entrypoint ModMenu** | `net.instantgratification.magnet.config.ModMenuIntegration` |
| **Pembantu Layar GUI** | `net.instantgratification.magnet.config.YaclScreenHelper` |
| **Keamanan Classloading** | Terisolasi via `GuiHelper.getOptionalFactory` |

---

## 📖 Arsitektur Sistem Konfigurasi

**Magnet, Let me get that!** menyediakan antarmuka pengguna konfigurasi sisi klien opsional yang ditenagai oleh **YetAnotherConfigLib v3 (YACL)** dan dapat diakses melalui **ModMenu**.

Untuk memastikan bahwa dedicated server tidak pernah mengalami crash saat memuat kelas GUI klien, pabrik GUI diselesaikan melalui **pemuatan kelas terisolasi yang aman dari refleksi**:

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
                "ig_magnet",
                "net.instantgratification.magnet.config.YaclScreenHelper",
                "createScreen"
        );
    }
}
```

---

## ⚠️ Peringatan Prioritas Konfigurasi

> ⚠️ **Pemberitahuan Penting**:  
> Perubahan yang dibuat dalam GUI ModMenu atau file `config/ig_magnet.json` **hanya memengaruhi nilai dasar bawaan untuk dunia BARU**.  
> Untuk mengubah pengaturan dunia aktif yang sudah dibuat, gunakan [[Referensi GameRules|id_id-26.2-GameRules]] dalam permainan via `/gamerule` atau layar edit GameRules vanilla.

---

## 🗂️ Kategori & Opsi Konfigurasi

```
Layar Konfigurasi YACL ("Magnet, Let me get that! Configuration")
  ├── General Settings (Pengaturan Umum)
  │     ├── Magnet Enabled (Standar: true)
  │     ├── Magnet Range (Standar: 12, Rentang: 1..64)
  │     ├── Instant Pickup (Standar: false)
  │     └── Magnet Noclip (Standar: true)
  ├── Speeds & Pull Heuristics (Kecepatan & Heuristik Tarikan)
  │     ├── Item Speed (Standar: 80%, Rentang: 1..1000)
  │     └── Item Acceleration (Standar: 10%, Rentang: 1..1000)
  ├── Line of Sight (LOS) (Garis Pandang)
  │     ├── Line of Sight Only (Standar: true)
  │     ├── Keep Moving if Unseen (Standar: true)
  │     ├── Blocked by Transparent (Standar: false)
  │     ├── Blocked by Flora (Standar: false)
  │     └── Blocked by Block Entities (Standar: false)
  └── Visuals & Performance (Visual & Performa)
        ├── Attract XP Orbs (Standar: true)
        ├── Magnet Particles (Standar: true)
        ├── Particle Count (Standar: 1, Rentang: 0..100)
        └── Max Particle Sources (Standar: 5, Rentang: 0..100)
```

---

## 📄 Struktur JSON Mentah (`config/ig_magnet.json`)

```json
{
  "configVersion": 1,
  "enabled": true,
  "range": 12,
  "noClip": true,
  "affectsXp": true,
  "particles": true,
  "particleCount": 1,
  "maxParticleSources": 5,
  "speed": 80,
  "acceleration": 10,
  "instant": false,
  "losOnly": true,
  "keepMovingIfUnseen": true,
  "blockedByTransparent": false,
  "blockedByFlora": false,
  "blockedByBlockEntities": false
}
```

---

## 🔗 Dokumentasi Wiki Terkait
* [[Referensi Lengkap GameRules|id_id-26.2-GameRules]]
* [[Penyiapan & Kompilasi Pengembang|id_id-26.2-Developer-Setup-and-Building]]
* [[Kembali ke Portal MC 26.2|id_id-26.2-Home]]\n