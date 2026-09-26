# ⚡ Mode Pengambilan Instan (MC 26.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Fitur | Parameter Teknis |
| :--- | :--- |
| **Kelas Sistem** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **GameRule Pengaktif** | `ig:magnet_instant` (Standar: `false`) |
| **GameRule Radius** | `ig:magnet_range` (Standar: `12`, Rentang: `1..64`) |
| **Titik Injeksi** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **Variabel Target** | Kotak Pembatas Koleksi Pemain (`AABB pickupArea`) |
| **Logika Inventaris** | 100% Pipa Vanilla `Player.touch(ItemEntity)` |

---

## 📖 Ikhtisar Pengambilan Instan

Sementara mode vakum standar menarik item secara fisik melintasi udara menggunakan interpolasi kecepatan, **Mode Pengambilan Instan** melenyapkan waktu tempuh sepenuhnya. Ketika diaktifkan, drop item dalam jangkauan langsung diserap ke inventaris pemain pada mikrodetik yang sama saat item tersebut muncul.

Alih-alih menulis perulangan penyisipan inventaris kustom yang berisiko merusak game atau menyebabkan crash, **Magnet, Let me get that!** mengimplementasikan Pengambilan Instan secara non-destruktif dengan memperbesar kotak pembatas koleksi bawaan pemain di dalam metode vanilla `aiStep()`.

```
+-----------------------------------------------------------------------------------+
|                           TICK aiStep() PEMAIN VANILLA                            |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
                +---------------------------------------------------+
                |   PlayerMixin.ig_magnet$expandPickupArea()        |
                |   pickupArea = pickupArea.inflate(range);         |
                +---------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|               EKSEKUSI VANILLA ItemEntity.playerTouch(Player)                     |
|   - Penumpukan Inventaris Asli & Pengambilan Sebagian                             |
|   - Animasi & Efek Suara Pengambilan Vanilla (item.pickup / entity.experience_orb)|
|   - Pemicu Kemajuan & Statistik Bawaan                                            |
|   - Penanganan Kepenuhan Wadah & Retensi Item yang Tersisa                        |
+-----------------------------------------------------------------------------------+
```

---

## 🧩 Implementasi Arsitektur (`PlayerMixin.java`)

```java
@ModifyVariable(
        method = "aiStep",
        at = @At(value = "STORE"),
        ordinal = 0
)
private AABB ig_magnet$expandPickupArea(AABB pickupArea) {
    Player player = (Player) (Object) this;
    if (!this.ig_magnet$isMagnetEnabled() || player.isDeadOrDying() || player.isSpectator()) {
        return pickupArea;
    }

    Level level = player.level();
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_ENABLED) && ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    return pickupArea;
}
```

### Jaminan Rekayasa Utama:
1. **Pengaman Status**: Jika pemain mati/sekarat (`player.isDeadOrDying()`), berada dalam mode penonton (`player.isSpectator()`), atau mematikan magnet pribadinya (`!isMagnetEnabled()`), kotak pembatas tidak akan pernah diperluas.
2. **Otoritas Sisi Server**: Logika pengambilan dieksekusi secara ketat pada server logis (`!level.isClientSide()`), melenyapkan item hantu dan desinkronisasi inventaris.
3. **Bebas Konflik Gerakan Ganda**: Ketika `ig:magnet_instant` bernilai true, `MagnetMovement.pull()` secara otomatis membatalkan pembaruan kecepatan sehingga kalkulasi fisika dan pengambilan instan tidak saling berebut kendali.

---

## ⚙️ Konfigurasi & GameRules Terkait

| GameRule | Tipe | Standar | Deskripsi |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | Mengaktifkan penyerapan instan ke inventaris tanpa waktu terbang. |
| `ig:magnet_range` | Integer | `12` | Radius (dalam blok) untuk pemekaran AABB koleksi. |
| `ig:magnet_enabled` | Boolean | `true` | Sakelar utama untuk keseluruhan mod. |

---

## 🔗 Dokumentasi Wiki Terkait
* [[Fisika Penarikan Vakum & Pergeseran Fase|id_id-26.2-Vacuum-and-Phase-Shifting]]
* [[Sakelar Pemain & Persistensi Status|id_id-26.2-Player-Toggle-and-Persistence]]
* [[Arsitektur & Mixin|id_id-26.2-Architecture-and-Mixins]]
* [[Kembali ke Portal MC 26.2|id_id-26.2-Home]]\n