# 🧩 Arsitektur & Target Mixin (MC 26.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Arsitektur | Parameter Teknis |
| :--- | :--- |
| **Paket Induk** | `net.instantgratification.magnet` |
| **Konfigurasi Mixin** | `src/main/resources/magnet.mixins.json` |
| **Access Widener** | `src/main/resources/magnet.accesswidener` |
| **Tingkat Kompatibilitas** | `JAVA_25` |
| **Total Kelas Mixin** | `2` (`MixinEntity`, `PlayerMixin`) |

---

## 🌳 Pohon Arsitektur Paket

```text
net.instantgratification.magnet/
├── IMagnetEntity.java                  # Antarmuka entitas untuk flag NoClip & magnetisasi
├── IMagnetPlayer.java                  # Antarmuka pemain untuk status sakelar & getter/setter
├── MagnetCommand.java                  # Pohon perintah Brigadier (/magnet toggle, /magnet debug)
├── MagnetDebugLogger.java              # Pencatatan file persisten aman-utas ke disk
├── MagnetManager.java                  # Loop pemindaian entitas & eksekusi spasial
├── MagnetMod.java                      # Mod initializer server/universal & penerima jaringan
├── MagnetModClient.java                # Client initializer, tombol pintas & toast overlay
├── MagnetMovement.java                 # Matematika vektor lintasan, kecepatan lerp & partikel
├── MagnetTogglePayload.java            # Record paket jaringan & komposit StreamCodec
├── SecondaryVisionCheck.java           # Raycasting blok granular (Flora, Entitas Blok, Kaca)
├── config/
│   ├── MagnetConfig.java               # Penyimpanan konfigurasi JSON & field POJO
│   ├── ModMenuIntegration.java         # Entrypoint ModMenu API aman refleksi
│   └── YaclScreenHelper.java           # Pembangun GUI YetAnotherConfigLib v3
├── mixin/
│   ├── MixinEntity.java                # Menginjeksikan NoClip & pembatalan gravitasi ke Entity
│   └── PlayerMixin.java                # Menginjeksikan persistensi NBT & pengambilan instan ke Player
├── registry/
│   └── ModGameRules.java               # Registrasi DynamicGameRuleManager
└── util/
    └── ModVersionGuard.java            # Pemeriksaan kewarasan runtime Knot ClassLoader
```

---

## 📋 Perincian Target Injeksi Mixin

### 1. `net.instantgratification.magnet.mixin.MixinEntity`
Menargetkan `net.minecraft.world.entity.Entity` dan mengimplementasikan `IMagnetEntity`.

| Metode Diinjeksi | Titik Target Injeksi | Aksi & Perilaku |
| :--- | :--- | :--- |
| `ig_magnet$decrementNoClipTicks` | `@Inject(method = "tick", at = @At("HEAD"))` | Mengurangi hitungan mundur `noClipTicks` sebanyak 1 per tick saat aktif. |
| `ig_magnet$preventPushOut` | `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` | Membatalkan kecepatan dorong keluar vanilla jika `noClipTicks > 0` di server. |
| `ig_magnet$forceNoClipOnMove` | `@Inject(method = "move", at = @At("HEAD"))` | Menyimpan `originalNoPhysics` dan memaksa `entity.noPhysics = true` jika `noClipTicks > 0`. |
| `ig_magnet$restoreNoPhysicsAfterMove` | `@Inject(method = "move", at = @At("RETURN"))` | Mengembalikan `entity.noPhysics = originalNoPhysics` setelah perpindahan selesai. |
| `ig_magnet$conditionalCancelGravity` | `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` | Membatalkan akselerasi gravitasi ke bawah *hanya* ketika item berada di dalam blok padat. |

---

### 2. `net.instantgratification.magnet.mixin.PlayerMixin`
Menargetkan `net.minecraft.world.entity.player.Player` dan mengimplementasikan `IMagnetPlayer`.

| Metode Diinjeksi | Titik Target Injeksi | Aksi & Perilaku |
| :--- | :--- | :--- |
| `ig_magnet$addAdditionalSaveData` | `@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))` | Menyerialisasikan boolean `ig_magnet_enabled` ke NBT pemain via `ValueOutput`. |
| `ig_magnet$readAdditionalSaveData` | `@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))` | Mendeserialisasikan `ig_magnet_enabled` dari NBT pemain via `ValueInput.getBooleanOr()`. |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | Memanggil `MagnetManager.tick(player)` di server pada setiap game tick. |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | Memperluas kotak pembatas pengambilan (`pickupArea.inflate(range)`) saat `ig:magnet_instant` true. |

---

## 🛡️ Pemeriksaan Kewarasan Knot ClassLoader (`ModVersionGuard`)

Untuk melindungi data server dan dunia dari pemuatan pada versi Minecraft yang rusak, belum dirilis, atau tidak kompatibel:

```java
public final class ModVersionGuard {
    public static void checkClass(String modName, String requiredClassName) {
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        // Memverifikasi keberadaan kelas target sebelum inisialisasi mod dilanjutkan...
    }
}
```

Dipanggil dalam `MagnetMod.onInitialize()`:
```java
ModVersionGuard.checkClass("Magnet", "net.minecraft.world.entity.item.ItemEntity");
```

---

## 🔗 Dokumentasi Wiki Terkait
* [[Fisika Penarikan Vakum & Pergeseran Fase|id_id-26.2-Vacuum-and-Phase-Shifting]]
* [[Sakelar Pemain, Persistensi & Siklus Hidup|id_id-26.2-Player-Toggle-and-Persistence]]
* [[API & Integrasi Addon|id_id-26.2-API-and-Addon-Integration]]
* [[Kembali ke Portal MC 26.2|id_id-26.2-Home]]\n