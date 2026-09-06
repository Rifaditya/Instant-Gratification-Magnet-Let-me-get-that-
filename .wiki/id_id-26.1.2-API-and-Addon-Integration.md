# 🔌 API & Integrasi Addon (MC 26.1.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info API | Parameter Teknis |
| :--- | :--- |
| **Pengelola Status Pemain** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Antarmuka Entitas** | `net.instantgratification.magnet.IMagnetEntity` |
| **Fasad Pergerakan Inti** | `net.instantgratification.magnet.MagnetMovement` |
| **API GameRule** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **API Penglihatan** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Integrasi Pengembang Antar-Mod

Mod pihak ketiga dan addon pendamping Instant Gratification dapat berinteraksi langsung dengan **Magnet, Let me get that!** dalam Minecraft 26.1.2.

---

## 🧑‍💻 Pengelolaan Status Pemain (`MagnetPlayerState`)

Periksa atau modifikasi preferensi magnet pemain secara langsung melalui metode pembantu statis:

```java
package net.instantgratification.magnet;

public class MagnetPlayerState {
    public static boolean isMagnetEnabled(Player player);
    public static void setMagnetEnabled(Player player, boolean enabled);
    public static boolean toggleMagnet(Player player);
}
```

### Contoh Penggunaan:
```java
// Memeriksa apakah magnet pemain sedang aktif
if (MagnetPlayerState.isMagnetEnabled(player)) {
    // Logika kustom...
}

// Menonaktifkan magnet secara terprogram
MagnetPlayerState.setMagnetEnabled(player, false);
```

---

## 📦 Antarmuka Magnetisasi Entitas (`IMagnetEntity`)

Lakukan typecast instans `Entity` apa pun ke `IMagnetEntity` untuk memanipulasi flag pergeseran fase atau magnetisasinya:

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig$setMagnetNoClip();
    boolean ig$isMagnetNoClip();
    void ig$setMagnetized();
    boolean ig$isMagnetized();
}
```

---

## 🚀 Fasad Pergerakan Statis (`MagnetMovement.pull`)

Memicu penarikan item atau XP secara terprogram menuju pemain:

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

---

## 🔗 Dokumentasi Wiki Terkait
* [[Arsitektur & Target Mixin|id_id-26.1.2-Architecture-and-Mixins]]
* [[Penyiapan & Kompilasi Pengembang|id_id-26.1.2-Developer-Setup-and-Building]]
* [[Kembali ke Portal MC 26.1.2|id_id-26.1.2-Home]]\n