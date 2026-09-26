# 🧲 Pergerakan Vakum & Pergeseran Fase (MC 26.1.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Fitur | Parameter Teknis |
| :--- | :--- |
| **Kelas Sistem** | `net.instantgratification.magnet.MagnetMovement` |
| **Event Pemicu** | Server Player Tick (`PlayerMixin` $\rightarrow$ `MagnetManager.tick`) |
| **Jangkauan Tarik Standar** | `12` blok (`ig:magnet_range`) |
| **Kecepatan Terminal Standar** | `80%` ($0.8\text{ blok/tick} = 16.0\text{ m/detik}$) |
| **Akselerasi Standar** | `10%` ($0.10\text{ faktor lerp/tick}$) |
| **Pergeseran Fase (NoClip)** | Diaktifkan (`ig:magnet_noclip = true`) |
| **Vektor Target** | Posisi Mata Pemain (`player.getEyePosition()`) |
| **Dorongan Offset Tanah** | $+0.05\text{ m}$ pada sumbu Y saat `entity.onGround()` |

---

## 📖 Ikhtisar Sistem

Dalam Minecraft 26.1.2, mesin vakum secara berkesinambungan melacak instans `ItemEntity` yang valid dalam radius sferis pemain dan menariknya langsung menuju ketinggian mata menggunakan interpolasi lerp.

Dengan **Pergeseran Fase (NoClip)** aktif, item melintasi dinding dan blok padat secara mulus, mencegah drop terjebak di balik rintangan selama penambangan atau pertempuran.

```
+-------------+    Garis Pandang OK    +----------------------+    Kecepatan Lerp Diterapkan    +--------------------+
| Item Entity | ---------------------> | Set NoClip (2 Ticks) | ------------------------------> | Posisi Mata Pemain |
+-------------+                        +----------------------+                                 +--------------------+
                                                  |
                                                  v
                                       [Batalkan Dorongan Dinding]
                                       [Bypass Tabrakan Blok]
                                       [Batalkan Gravitasi Dinding]
```

---

## 🧮 Fisika & Matematika Vektor

### 1. Vektor Satuan Arah
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|}$$

### 2. Interpolasi Kecepatan
$$\text{Skalar Kecepatan } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0} = 0.8\text{ blok/tick}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
$$\text{Faktor Akselerasi } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0} = 0.10$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 3. Peningkatan Anti-Gesekan Tanah
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 Pergeseran Fase (Mesin NoClip)

1. **Aktivasi Status**: `((IMagnetEntity) entity).ig$setMagnetNoClip()` mengaktifkan hitungan mundur 2 tick.
2. **Pengambilalihan Fisika (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` menyimpan `originalNoPhysics` dan mengatur `entity.noPhysics = true`.
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` mengembalikan `entity.noPhysics = originalNoPhysics`.
3. **Pencegahan Dorongan Keluar**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` memblokir gaya dorong dinding vanilla.
4. **Pembatalan Gravitasi di Dalam Dinding**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` membatalkan gravitasi *hanya* ketika item secara fisik berada di dalam voxel blok.

---

## ⚙️ Konfigurasi & GameRules Terkait

| GameRule | Tipe | Standar | Deskripsi |
| :--- | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | Sakelar utama untuk mekanisme vakum. |
| `ig:magnet_range` | Integer | `12` | Radius vakum dalam blok (1 hingga 64). |
| `ig:magnet_speed` | Integer | `80` | Persentase kecepatan terminal ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Integer | `10` | Persentase faktor akselerasi ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Boolean | `true` | Mengaktifkan pergeseran fase tembus blok saat ditarik. |

---

## 🔗 Dokumentasi Wiki Terkait
* [[Penetrasi Rintangan & Garis Pandang|id_id-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Mode Pengambilan Instan|id_id-26.1.2-Instant-Pickup-Mode]]
* [[Arsitektur & Implementasi Mixin|id_id-26.1.2-Architecture-and-Mixins]]
* [[Kembali ke Portal MC 26.1.2|id_id-26.1.2-Home]]\n