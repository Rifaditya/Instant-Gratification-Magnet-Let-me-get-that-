# ⚡ Mode Pengambilan Instan (MC 26.1.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Fitur | Parameter Teknis |
| :--- | :--- |
| **Kelas Sistem** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **GameRule Pengaktif** | `ig:magnet_instant` (Standar: `false`) |
| **GameRule Radius** | `ig:magnet_range` (Standar: `12`, Rentang: `1..64`) |
| **Titik Injeksi** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **Variabel Target** | Kotak Pembatas Koleksi Pemain (`AABB pickupArea`) |

---

## 📖 Ikhtisar Pengambilan Instan

Dalam Minecraft 26.1.2, **Mode Pengambilan Instan** melenyapkan waktu terbang item dengan memperluas kotak pembatas koleksi pemain di dalam `Player.aiStep()`, langsung memasukkan drop ke inventaris pemain melalui penangan pengambilan vanilla.

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
    Level level = player.level();
    
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    
    return pickupArea;
}
```

---

## ⚙️ Konfigurasi & GameRules Terkait

| GameRule | Tipe | Standar | Deskripsi |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | Jika true, item langsung berpindah ke pemain alih-alih terbang. |
| `ig:magnet_range` | Integer | `12` | Radius dalam blok untuk area pengambilan yang diperluas. |
| `ig:magnet_enabled` | Boolean | `true` | Sakelar utama untuk seluruh mod. |

---

## 🔗 Dokumentasi Wiki Terkait
* [[Fisika Penarikan Vakum & Pergeseran Fase|id_id-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Referensi Lengkap GameRules|id_id-26.1.2-GameRules]]
* [[Kembali ke Portal MC 26.1.2|id_id-26.1.2-Home]]\n