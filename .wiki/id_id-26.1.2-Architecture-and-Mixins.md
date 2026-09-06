# 🧩 Arsitektur & Target Mixin (MC 26.1.2)

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
├── MagnetCommand.java                  # Pendaftaran perintah server (/magnet toggle)
├── MagnetManager.java                  # Loop pemindaian entitas & eksekusi spasial
├── MagnetMod.java                      # Inisialisasi mod & penerima paket
├── MagnetModClient.java                # Inisialisasi klien, tombol Ctrl+M & toast overlay
├── MagnetMovement.java                 # Matematika vektor lintasan, kecepatan lerp & partikel
├── MagnetPlayerState.java              # Penyimpanan sakelar ConcurrentHashMap aman-utas
├── MagnetTogglePayload.java            # Record paket jaringan & komposit StreamCodec
├── SecondaryVisionCheck.java           # Raycasting blok granular (Flora, Entitas Blok, Kaca)
├── config/
│   ├── ClothConfigScreenHelper.java    # Pembangun GUI Cloth Config Fabric
│   ├── MagnetConfig.java               # Penyimpanan konfigurasi JSON & field POJO
│   └── ModMenuIntegration.java         # Entrypoint ModMenu API aman refleksi
├── mixin/
│   ├── MixinEntity.java                # Menginjeksikan NoClip & pembatalan gravitasi ke Entity
│   └── PlayerMixin.java                # Menginjeksikan eksekusi tick & pengambilan instan ke Player
└── registry/
    └── ModGameRules.java               # Registrasi DynamicGameRuleManager
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
Menargetkan `net.minecraft.world.entity.player.Player`.

| Metode Diinjeksi | Titik Target Injeksi | Aksi & Perilaku |
| :--- | :--- | :--- |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | Memanggil `MagnetManager.tick(player)` di server pada setiap game tick. |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | Memperluas kotak pembatas pengambilan (`pickupArea.inflate(range)`) saat `ig:magnet_instant` true. |

---

## 🔗 Dokumentasi Wiki Terkait
* [[Fisika Penarikan Vakum & Pergeseran Fase|id_id-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Penyiapan & Kompilasi Pengembang|id_id-26.1.2-Developer-Setup-and-Building]]
* [[Kembali ke Portal MC 26.1.2|id_id-26.1.2-Home]]\n