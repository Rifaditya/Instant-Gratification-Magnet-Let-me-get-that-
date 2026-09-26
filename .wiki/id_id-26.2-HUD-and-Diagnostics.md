# 📊 HUD, Visual & Diagnostik (MC 26.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Visual | Parameter Teknis |
| :--- | :--- |
| **API Actionbar** | `client.gui.hud.setOverlayMessage(Component, boolean)` |
| **Tipe Partikel** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Modulo Pembatas Partikel** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **File Log Debug** | `logs/ig_magnet_debug.log` |
| **Kelas Pencatat** | `net.instantgratification.magnet.MagnetDebugLogger` |

---

## 🖥️ Umpan Balik Hamparan Actionbar

Ketika pemain mengalihkan status magnet melalui tombol pintas (`\`) atau perintah server (`/magnet toggle`), HUD klien langsung menampilkan pesan hamparan actionbar yang bersih dan tidak mengganggu tepat di atas hotbar:

* **Pesan Aktif**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Pesan Nonaktif**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Diverifikasi terhadap: Hud.java (26.2+)
if (newState) {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Jejak Visual Percikan Listrik

Saat item dan orb XP sedang ditarik di udara, entitas tersebut memancarkan jejak partikel halus `ParticleTypes.ELECTRIC_SPARK`:

```
[Item Ditarik]  --->  ✨  --->  ✨  --->  ✨  --->  [Posisi Mata Pemain]
```

### Aturan Pembatasan Partikel:
1. **Batas Maksimal Sumber**: Diatur oleh `ig:magnet_max_particle_sources` (standar: `5`), memastikan tumpukan besar drop tambang tidak menyebabkan kelebihan beban rendering partikel.
2. **Penjarakan Frekuensi**: Hanya 1 dari setiap 4 tick yang memunculkan partikel untuk setiap entitas individual, didistribusikan merata berdasarkan ID entitas yang unik: `(entity.tickCount + entity.getId()) % 4 == 0`.
3. **Kontrol Kerapatan**: Dikendalikan per sumber oleh `ig:magnet_particle_count` (standar: `1`).

---

## 📝 Pencatat File Debug Khusus (`MagnetDebugLogger`)

Bagi administrator server dan pengembang modpack yang mendiagnosis batas garis pandang atau paket jaringan, mod menyertakan pencatat file asinkron aman-utas khusus yang menulis ke `logs/ig_magnet_debug.log`:

* **Aktivasi**: Jalankan `/magnet debug log` di dalam permainan.
* **Format**: `[yyyy-MM-dd HH:mm:ss.SSS] [Konteks] Pesan`
* **Contoh Log**:
  ```text
  [2026-08-22 21:50:00.124] MagnetMod: Server received packet from Alex (e7b1a234-...) enabled=true
  [2026-08-22 21:50:00.130] MagnetMovement: Entity 592 is now magnetized by Alex (e7b1a234-...).
  [2026-08-22 21:50:01.450] PlayerMixin: Server tick for Alex (e7b1a234-...) isMagnetEnabled=true
  ```

---

## 🔗 Dokumentasi Wiki Terkait
* [[Perintah Brigadier & Diagnostik Dalam Game|id_id-26.2-Commands]]
* [[Tarikan Orb Pengalaman|id_id-26.2-Experience-Orb-Attraction]]
* [[Kembali ke Portal MC 26.2|id_id-26.2-Home]]\n