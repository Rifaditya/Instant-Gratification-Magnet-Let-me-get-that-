# 🔄 Sakelar Pemain & Status Sesi (MC 26.1.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info Fitur | Parameter Teknis |
| :--- | :--- |
| **Kelas Penyimpanan Status** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Tombol Pintas Bawaan Klien** | `Ctrl+M` (`GLFW.GLFW_KEY_M` + `isControlDown()`) |
| **Kategori Tombol** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Muatan Jaringan** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **API Actionbar** | `client.gui.setOverlayMessage(Component, boolean)` |

---

## 📖 Ikhtisar Arsitektur Status

Dalam Minecraft 26.1.2, preferensi sakelar pemain dikelola selama sesi server aktif melalui `MagnetPlayerState` menggunakan `ConcurrentHashMap` yang aman-utas:

```java
public class MagnetPlayerState {
    private static final Map<UUID, Boolean> playerStates = new ConcurrentHashMap<>();

    public static boolean isMagnetEnabled(Player player) {
        return playerStates.getOrDefault(player.getUUID(), true);
    }

    public static void setMagnetEnabled(Player player, boolean enabled) {
        playerStates.put(player.getUUID(), enabled);
    }

    public static boolean toggleMagnet(Player player) {
        boolean newState = !isMagnetEnabled(player);
        setMagnetEnabled(player, newState);
        return newState;
    }
}
```

---

## ⌨️ Tombol Pintas Klien (`Ctrl+M`)

* **Kombinasi Standar**: `Ctrl+M` (`GLFW_KEY_M` + `isControlDown()`), dengan dukungan lintas platform untuk tombol Command di macOS (`GLFW_KEY_LEFT_SUPER`).
* **Umpan Balik Visual Actionbar**:
  - `chat.ig_magnet.enabled`: `"Item Magnet: Enabled"`
  - `chat.ig_magnet.disabled`: `"Item Magnet: Disabled"`

---

## 📡 Protokol Sinkronisasi Jaringan

```
[KLIEN]                                                            [SERVER]
Pemain menekan Ctrl+M
MagnetPlayerState.setMagnetEnabled(player, newState)
ClientPlayNetworking.send(new MagnetTogglePayload(newState))
                                               ------------------> Penerima Server
                                                                   MagnetPlayerState.setMagnetEnabled(player, payload.enabled())
```

---

## 🔗 Dokumentasi Wiki Terkait
* [[Perintah Server & Pengalihan|id_id-26.1.2-Commands]]
* [[Arsitektur & Mixin|id_id-26.1.2-Architecture-and-Mixins]]
* [[Kembali ke Portal MC 26.1.2|id_id-26.1.2-Home]]\n