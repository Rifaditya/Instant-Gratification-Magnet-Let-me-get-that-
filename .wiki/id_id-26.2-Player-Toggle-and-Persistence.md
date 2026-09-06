# 🔄 Sakelar Pemain, Persistensi & Siklus Hidup (MC 26.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Fitur | Parameter Teknis |
| :--- | :--- |
| **Jembatan Antarmuka** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Target Mixin** | `net.minecraft.world.entity.player.Player` (`PlayerMixin.java`) |
| **Tombol Pintas Bawaan Klien** | `\` (Backslash) — `key.ig_magnet.toggle` |
| **Kategori Tombol** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Muatan Jaringan** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **Penyimpanan Codec NBT** | `ValueOutput` / `ValueInput` di bawah tag `"ig_magnet_enabled"` |
| **Event Siklus Hidup** | `ServerPlayConnectionEvents.JOIN`, `ServerPlayerEvents.COPY_FROM`, `ServerPlayerEvents.AFTER_RESPAWN` |

---

## 📖 Ikhtisar Arsitektur Status

Di server multiplayer dan modpack, setiap pemain memiliki preferensi berbeda—para pembangun mungkin ingin menjeda penarikan item saat mendekorasi, sementara para penambang menginginkan daya sedot magnet maksimal.

**Magnet, Let me get that!** mengimplementasikan **status sakelar individual per pemain** yang 100% persisten melewati pemuatan ulang dunia, kematian, respawn, dan teleportasi dimensi.

```
                                [AKSI KLIEN]
                    Pemain Menekan Tombol Pengalih ('\')
                                      |
                                      v
                          [STATUS LOKAL DIPERBARUI]
                    client.player -> isEnabled = !isEnabled
                    Hamparan Actionbar: "Item Magnet: Enabled/Disabled"
                                      |
                                      v
                         [TRANSMISI PAKET C2S]
                    ClientPlayNetworking.send(MagnetTogglePayload)
                                      |
                                      v
                               [PENERIMA SERVER]
                    context.server().execute(() -> {
                        ((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(val);
                    })
                                      |
         +----------------------------+----------------------------+
         |                                                         |
         v                                                         v
 [DATA NBT DIPERSISTENKAN]                             [KAIT SIKLUS HIDUP TERPASANG]
 ValueOutput.putBoolean("ig_magnet_enabled")           - JOIN: Sinkronisasi S2C
 ValueInput.getBooleanOr("ig_magnet_enabled", true)    - COPY_FROM: Retensi saat Mati
                                                       - AFTER_RESPAWN: Sinkronisasi Entitas Baru
```

---

## ⌨️ Tombol Pintas Klien & Hamparan Actionbar

* **Tombol Standar**: `GLFW_KEY_BACKSLASH` (`\`), menghindari konflik dengan mod peta umum dan utilitas inventaris.
* **Pembantu Tipe Tombol Dinamis**: Menggunakan `ig_magnet$getKeyboardType()` untuk menyelesaikan `InputConstants.Type.KEYBOARD` secara aman dengan fallback ke `KEYSYM` di berbagai rilis Fabric Loader.
* **Umpan Balik Visual Instan**: Pengalihan memicu pemberitahuan actionbar terjemahan:
  - `chat.ig_magnet.enabled`: `"Item Magnet: Enabled"`
  - `chat.ig_magnet.disabled`: `"Item Magnet: Disabled"`

---

## 💾 Penyimpanan NBT & Serialisasi Codec Minecraft 26.2

Pengaturan sakelar pemain disimpan langsung ke file simpanan dunia `.dat` pemain menggunakan pipa data `ValueOutput` dan `ValueInput` Minecraft 26.2:

```java
// Menyimpan ke NBT Pemain
@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
    output.putBoolean("ig_magnet_enabled", this.ig_magnet$enabled);
}

// Memuat dari NBT Pemain
@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
    this.ig_magnet$enabled = input.getBooleanOr("ig_magnet_enabled", true);
}
```

---

## 🧬 Event Siklus Hidup Fabric & Alur Kematian/Respawn

Saat pemain mati di Minecraft, game menciptakan entitas `ServerPlayer` yang benar-benar baru saat respawn. Mod ini menjamin nol kehilangan data:

1. **`ServerPlayerEvents.COPY_FROM`**: Menyalin nilai boolean sakelar dari `oldPlayer` ke `newPlayer` segera setelah kloning entitas.
2. **`ServerPlayerEvents.AFTER_RESPAWN`**: Mentransmisikan `MagnetTogglePayload` S2C secara otomatis ke klien begitu entitas pemain baru terhubung, menjaga HUD klien tetap sinkron.
3. **`ServerPlayConnectionEvents.JOIN`**: Menyinkronkan status NBT tersimpan milik pemain ke klien saat bergabung ke server dedicated atau dunia LAN.

---

## 🔗 Dokumentasi Wiki Terkait
* [[Perintah Brigadier & Sakelar Server|id_id-26.2-Commands]]
* [[Arsitektur & Implementasi Mixin|id_id-26.2-Architecture-and-Mixins]]
* [[HUD & Diagnostik|id_id-26.2-HUD-and-Diagnostics]]
* [[Kembali ke Portal MC 26.2|id_id-26.2-Home]]\n