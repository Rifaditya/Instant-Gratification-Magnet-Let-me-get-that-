# 🔌 API & Integrasi Addon (MC 26.2)

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

| Kotak Info API | Parameter Teknis |
| :--- | :--- |
| **Antarmuka Pemain** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Antarmuka Entitas** | `net.instantgratification.magnet.IMagnetEntity` |
| **Fasad Pergerakan Inti** | `net.instantgratification.magnet.MagnetMovement` |
| **API GameRule** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **API Penglihatan** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Integrasi Pengembang Antar-Mod

Mod pihak ketiga, utilitas server, dan addon pendamping Instant Gratification dapat berinteraksi langsung dengan **Magnet, Let me get that!** untuk memeriksa status magnet pemain, memicu tarikan terprogram, atau melewati rintangan.

---

## 🧑‍💻 Antarmuka Status Pemain (`IMagnetPlayer`)

Lakukan typecast instans `Player` atau `ServerPlayer` apa pun ke `IMagnetPlayer` untuk memeriksa atau memodifikasi preferensi magnet:

```java
package net.instantgratification.magnet;

public interface IMagnetPlayer {
    boolean ig_magnet$isMagnetEnabled();
    void ig_magnet$setMagnetEnabled(boolean enabled);
    boolean ig_magnet$toggleMagnet();
}
```

### Contoh Penggunaan:
```java
// Memeriksa apakah magnet pemain sedang aktif
if (((IMagnetPlayer) player).ig_magnet$isMagnetEnabled()) {
    // Logika addon kustom...
}

// Menonaktifkan magnet secara terprogram (misalnya saat duduk di takhta atau dalam minigame)
((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(false);
```

---

## 📦 Antarmuka Magnetisasi Entitas (`IMagnetEntity`)

Lakukan typecast instans `Entity` apa pun (seperti drop mob kustom, proyektil, atau orb XP) ke `IMagnetEntity`:

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig_magnet$setMagnetNoClip();
    boolean ig_magnet$isMagnetNoClip();
    void ig_magnet$setMagnetized();
    boolean ig_magnet$isMagnetized();
}
```

### Contoh Penggunaan:
```java
// Memberikan pergeseran fase sementara selama 2 tick pada entitas kustom
((IMagnetEntity) customItem).ig_magnet$setMagnetNoClip();
```

---

## 🚀 Fasad Pergerakan Statis (`MagnetMovement.pull`)

Mod addon dapat menarik entitas secara manual menuju pemain mana pun menggunakan mesin fisika dan garis pandang bawaan:

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

```java
// Tarik entitas target ke pemain dengan jejak partikel
MagnetMovement.pull(specialDrop, player, true);
```

---

## 🔗 Dokumentasi Wiki Terkait
* [[Arsitektur & Target Mixin|id_id-26.2-Architecture-and-Mixins]]
* [[Penyiapan & Kompilasi Pengembang|id_id-26.2-Developer-Setup-and-Building]]
* [[Kembali ke Portal MC 26.2|id_id-26.2-Home]]\n