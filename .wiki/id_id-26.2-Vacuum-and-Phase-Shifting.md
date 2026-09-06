# 🧲 Pergerakan Vakum & Pergeseran Fase (MC 26.2)

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

Mekanisme vakum inti dalam **Magnet, Let me get that!** memindai instans `ItemEntity` yang dijatuhkan dalam radius konfigurasi pemain di setiap tick server dan menariknya menuju ketinggian mata pemain menggunakan interpolasi non-linear yang mulus.

Untuk mencegah item tersangkut di tepi batu bulat (cobblestone), tajuk pohon, atau celah urat bijih, mod mengaktifkan **Pergeseran Fase (NoClip)**, memungkinkan item yang sedang ditarik melintasi voxel blok padat tanpa hambatan.

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

Saat menarik item, lintasan dihitung secara langsung dalam ruang Euclidean 3D:

### 1. Vektor Menuju Target
Misalkan $\vec{p}_{\text{entity}} = (x_e, y_e, z_e)$ adalah posisi item saat ini, dan $\vec{p}_{\text{eye}} = (x_p, y_p + h_{\text{eye}}, z_p)$ adalah posisi mata pemain.
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|} = \frac{\vec{v}_{\text{target}}}{\sqrt{\Delta x^2 + \Delta y^2 + \Delta z^2}}$$

### 2. Kecepatan Terminal yang Diinginkan
Vektor kecepatan target menskalakan arah satuan $\hat{d}$ dengan parameter kecepatan yang dikonfigurasi:
$$\text{Skalar Kecepatan } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
*Pada pengaturan standar ($80\%$), $s = 0.8\text{ blok/tick}$. Pada $20\text{ tick/detik}$, kecepatan terminal adalah $16.0\text{ m/detik}$.*

### 3. Akselerasi Non-Linear (Lerp)
Kecepatan diperbarui menggunakan interpolasi linear (`Vec3.lerp`) berdasarkan faktor akselerasi $a$:
$$\text{Faktor Akselerasi } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0}$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 4. Peningkatan Anti-Gesekan Tanah
Jika item bertumpu pada permukaan blok (`entity.onGround() == true`), gesekan tanah langsung diputus untuk mencegah item terseret lambat di lantai:
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 Pergeseran Fase (Mesin NoClip)

Ketika `ig:magnet_noclip` diaktifkan, item ditandai dengan jendela NoClip selama 2 tick:

1. **Aktivasi Status**: `((IMagnetEntity) entity).ig_magnet$setMagnetNoClip()` mengatur `noClipTicks = 2`.
2. **Pembajakan Pergerakan (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` menyimpan `originalNoPhysics` dan memaksa `entity.noPhysics = true`.
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` mengembalikan `entity.noPhysics = originalNoPhysics`.
3. **Pembatalan Dorongan Keluar (Pushout)**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` mencegah kode vanilla mendorong item secara agresif keluar dari dalam blok.
4. **Pembatalan Gravitasi Bersyarat**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` membatalkan gravitasi ke bawah *hanya* ketika item secara fisik bersinggungan dengan voxel blok (`!level.noCollision(...)`), menjaga lengkungan lintasan alami di udara terbuka.

---

## ⚙️ Konfigurasi & GameRules Terkait

| GameRule | Tipe | Standar | Satuan / Rentang | Deskripsi |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | Sakelar utama untuk seluruh logika vakum. |
| `ig:magnet_range` | Integer | `12` | `1..64` blok | Radius vakum sferis maksimum. |
| `ig:magnet_speed` | Integer | `80` | `1..1000%` | Persentase kecepatan terminal ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000%` | Persentase akselerasi tarikan ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | Mengaktifkan pergeseran fase tembus blok saat ditarik. |

---

## 🔗 Dokumentasi Wiki Terkait
* [[Penetrasi Rintangan & Garis Pandang|id_id-26.2-Line-of-Sight-and-Obstruction]]
* [[Mode Pengambilan Instan|id_id-26.2-Instant-Pickup-Mode]]
* [[Arsitektur & Implementasi Mixin|id_id-26.2-Architecture-and-Mixins]]
* [[Kembali ke Portal MC 26.2|id_id-26.2-Home]]\n