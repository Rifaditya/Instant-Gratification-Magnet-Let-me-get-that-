# 🎨 GUI Cloth Config & ModMenu (MC 26.1.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Konfigurasi | Detail |
| :--- | :--- |
| **Jalur File Konfigurasi** | `config/ig_magnet.json` |
| **Pustaka GUI** | Cloth Config Fabric (`me.shedaniel.cloth:cloth-config-fabric:26.1.154`) |
| **Entrypoint ModMenu** | `net.instantgratification.magnet.config.ModMenuIntegration` |
| **Pembantu Layar GUI** | `net.instantgratification.magnet.config.ClothConfigScreenHelper` |
| **Keamanan Classloading** | Terisolasi via `GuiHelper.getOptionalFactory` |

---

## 📖 Arsitektur Konfigurasi

Dalam Minecraft 26.1.2, **Magnet, Let me get that!** terintegrasi dengan **Cloth Config Fabric** dan **ModMenu** untuk menyediakan antarmuka pengaturan grafis dalam game.

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
                "ig_magnet",
                "net.instantgratification.magnet.config.ClothConfigScreenHelper",
                "createFactory"
        );
    }
}
```

---

## ⚠️ Peringatan Prioritas Konfigurasi

> ⚠️ **Pemberitahuan Penting**:  
> Perubahan yang dibuat dalam GUI ModMenu atau file `config/ig_magnet.json` **hanya memengaruhi nilai dasar bawaan untuk dunia BARU**.  
> Untuk mengubah pengaturan dunia aktif yang sudah dibuat, gunakan [[Referensi GameRules|id_id-26.1.2-GameRules]] dalam permainan via `/gamerule` atau layar edit GameRules vanilla.

---

## 🗂️ Kategori & Opsi

```
Layar Cloth Config ("Magnet, Let me get that! Configuration")
  ├── General Settings (Pengaturan Umum)
  │     ├── Magnet Enabled (Standar: true)
  │     ├── Magnet Range (Standar: 12, Rentang: 1..64)
  │     ├── Instant Pickup (Standar: false)
  │     └── Magnet Noclip (Standar: true)
  ├── Speeds & Pull Heuristics (Kecepatan & Heuristik Tarikan)
  │     ├── Item Speed (Standar: 80, Rentang: 1..1000)
  │     └── Item Acceleration (Standar: 10, Rentang: 1..1000)
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
* [[Referensi Lengkap GameRules|id_id-26.1.2-GameRules]]
* [[Penyiapan & Kompilasi Pengembang|id_id-26.1.2-Developer-Setup-and-Building]]
* [[Kembali ke Portal MC 26.1.2|id_id-26.1.2-Home]]\n