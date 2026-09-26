# 📊 HUD, Visual & Hamparan Layar (MC 26.1.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Visual | Parameter Teknis |
| :--- | :--- |
| **API Actionbar** | `client.gui.setOverlayMessage(Component, boolean)` |
| **Tipe Partikel** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Modulo Pembatas Partikel** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **Batas Sumber Partikel** | `ig:magnet_max_particle_sources` (Standar: `5`) |

---

## 🖥️ Umpan Balik Hamparan Actionbar

Ketika pemain mengalihkan magnet item mereka menggunakan kombinasi tombol pintas `Ctrl+M`, GUI klien langsung menampilkan pemberitahuan actionbar:

* **Aktif**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Nonaktif**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Diverifikasi terhadap: Gui.java (26.1.2)
if (newState) {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Jejak Visual Percikan Listrik

Item dan orb pengalaman yang sedang ditarik memancarkan jejak partikel percikan listrik:

```
[Item / XP Ditarik]  --->  ✨  --->  ✨  --->  ✨  --->  [Posisi Mata Pemain]
```

* **Pembatasan Sumber**: Maksimal 5 sumber simultan yang memancarkan partikel (`ig:magnet_max_particle_sources = 5`).
* **Pembatasan Tick**: Partikel muncul setiap 4 tick ($5\text{ kali/detik}$).
* **Kontrol Kerapatan**: `ig:magnet_particle_count = 1`.

---

## 🔗 Dokumentasi Wiki Terkait
* [[Sakelar Pemain & Status Sesi|id_id-26.1.2-Player-Toggle-and-Persistence]]
* [[Tarikan Orb Pengalaman|id_id-26.1.2-Experience-Orb-Attraction]]
* [[Kembali ke Portal MC 26.1.2|id_id-26.1.2-Home]]\n