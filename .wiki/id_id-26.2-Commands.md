# 💻 Perintah Brigadier & Diagnostik Dalam Game (MC 26.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Fitur | Parameter Teknis |
| :--- | :--- |
| **Kelas Perintah** | `net.instantgratification.magnet.MagnetCommand` |
| **Literal Utama** | `/magnet` dan `/ig_magnet` (Alias cermin yang identik) |
| **Callback Pendaftaran** | `CommandRegistrationCallback.EVENT` |
| **File Output Log** | `logs/ig_magnet_debug.log` |
| **Izin Target** | Tersedia untuk semua pemain (`toggle`) dan OP Tingkat 2 (`debug`) |

---

## 📖 Struktur Pohon Perintah

```
/magnet (atau /ig_magnet)
  ├── toggle
  └── debug
        └── log
```

---

## 🛠️ Referensi Sub-Perintah

### 1. `/magnet toggle` (atau `/ig_magnet toggle`)
Mengalihkan status magnet pribadi pemain yang mengeksekusi (aktif atau nonaktif).

* **Penggunaan**: `/magnet toggle`
* **Eksekusi**: Memanggil `((IMagnetPlayer) player).ig_magnet$toggleMagnet()`.
* **Umpan Balik Output**:
  - Jika aktif: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
  - Jika nonaktif: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)
* **Kasus Penggunaan**: Memungkinkan pemain yang terhubung dari klien vanilla (pada pengaturan server-side saja) atau pemain tanpa akses tombol pintas untuk mengalihkan magnet item mereka.

---

### 2. `/magnet debug` (atau `/ig_magnet debug`)
Mengeksekusi pemindaian diagnostik langsung di dunia terhadap pemain dan entitas di sekitarnya dalam radius 10 blok.

* **Penggunaan**: `/magnet debug`
* **Informasi Output**:
  ```text
  §e--- Magnet Debug Info ---
  Player Name: Steve
  Player UUID: c0b59055-ec50-4653-979d-c3c92d6ca897
  Global Master Toggle: true
  Player Toggle State: true
  Range: 12
  Instant Pickup: false
  LOS Only: true
  Spectator: false
  Dead/Dying: false
  Debug File Logger: DISABLED (Toggle with /magnet debug log)
  Nearby Items (10 blocks): 3
  Nearest Item ID: 412
  PlayerVisionTracker.canSee: true
  Vanilla Raycast canSee: true
  §e-------------------------
  ```
* **Kasus Penggunaan**: Mendiagnosis mengapa suatu item mungkin tidak tertarik (misalnya karena terhalang garis pandang ketat, status pemain mati, atau penggantian GameRule global).

---

### 3. `/magnet debug log` (atau `/ig_magnet debug log`)
Mengalihkan pencatatan diagnostik terperinci persisten ke disk.

* **Penggunaan**: `/magnet debug log`
* **Eksekusi**: Mengalihkan status `MagnetDebugLogger.enabled = !MagnetDebugLogger.enabled`.
* **Output**: Menulis event tick dengan cap waktu, penerimaan paket, pertukaran pesan respawn, dan penolakan LOS ke `logs/ig_magnet_debug.log`.
* **Contoh Entri File Log**:
  ```log
  [2026-08-22 21:49:15.102] MagnetMod: Server received packet from Steve (c0b59055-...) enabled=true
  [2026-08-22 21:49:15.105] MagnetMovement: Entity 412 is now magnetized by Steve (c0b59055-...).
  ```

---

## 🔗 Dokumentasi Wiki Terkait
* [[Sakelar Pemain, Persistensi & Siklus Hidup|id_id-26.2-Player-Toggle-and-Persistence]]
* [[Sistem HUD & Diagnostik|id_id-26.2-HUD-and-Diagnostics]]
* [[Referensi Lengkap GameRules|id_id-26.2-GameRules]]
* [[Kembali ke Portal MC 26.2|id_id-26.2-Home]]\n