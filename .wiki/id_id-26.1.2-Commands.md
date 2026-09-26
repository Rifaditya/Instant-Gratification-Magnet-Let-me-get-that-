# 💻 Perintah Server & Dukungan Klien Vanilla (MC 26.1.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Fitur | Parameter Teknis |
| :--- | :--- |
| **Kelas Perintah** | `net.instantgratification.magnet.MagnetCommand` |
| **Literal Utama** | `/magnet` dan `/ig_magnet` (Alias cermin identik) |
| **Sub-Perintah** | `toggle` |
| **Sinkronisasi Jaringan** | `ServerPlayNetworking.send(player, new MagnetTogglePayload(newState))` |

---

## 📖 Referensi Perintah

### `/magnet toggle` (atau `/ig_magnet toggle`)
Mengalihkan status magnet item pemain yang mengeksekusi (aktif atau nonaktif).

* **Sintaks Perintah**: `/magnet toggle`
* **Logika Eksekusi**:
  ```java
  private static int toggleMagnet(CommandSourceStack source) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      boolean newState = MagnetPlayerState.toggleMagnet(player);

      if (newState) {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.enabled"), false);
      } else {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.disabled"), false);
      }

      if (ServerPlayNetworking.canSend(player, MagnetTogglePayload.TYPE)) {
          ServerPlayNetworking.send(player, new MagnetTogglePayload(newState));
      }
      return 1;
  }
  ```
* **Kompatibilitas Klien Vanilla**: Memungkinkan pemain pada klien vanilla yang terhubung ke server Fabric untuk mengalihkan magnet mereka tanpa memerlukan pemasangan mod di klien.

---

## 🔗 Dokumentasi Wiki Terkait
* [[Sakelar Pemain & Status Sesi|id_id-26.1.2-Player-Toggle-and-Persistence]]
* [[Referensi Lengkap GameRules|id_id-26.1.2-GameRules]]
* [[Kembali ke Portal MC 26.1.2|id_id-26.1.2-Home]]\n