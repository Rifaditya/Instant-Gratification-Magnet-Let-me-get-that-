# 📊 HUD, Visuals & Diagnose (MC 26.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Visuelle Infobox | Technische Parameter |
| :--- | :--- |
| **Actionbar-API** | `client.gui.hud.setOverlayMessage(Component, boolean)` |
| **Partikeltyp** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Partikeldrosselungs-Modulo** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **Debug-Protokolldatei** | `logs/ig_magnet_debug.log` |
| **Logger-Klasse** | `net.instantgratification.magnet.MagnetDebugLogger` |

---

## 🖥️ Actionbar-Overlay-Feedback

Wenn der Spieler seinen Magnetstatus über den Hotkey (`\`) oder den Serverbefehl (`/magnet toggle`) umschaltet, zeigt das Client-HUD sofort eine unaufdringliche Actionbar-Nachricht direkt über der Hotbar an:

* **Aktiviert-Nachricht**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Deaktiviert-Nachricht**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Verifiziert gegen: Hud.java (26.2+)
if (newState) {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Elektrische Funken-Partikelspuren

Während Gegenstände und XP-Kugeln im Flug aktiv magnetisiert werden, senden sie dezente visuelle Partikelspuren von `ParticleTypes.ELECTRIC_SPARK` aus:

```
[Gezogenes Item]  --->  ✨  --->  ✨  --->  ✨  --->  [Spieler-Augenposition]
```

### Regeln zur Partikeldrosselung:
1. **Quellen-Obergrenze**: Gesteuert über `ig:magnet_max_particle_sources` (Standard: `5`), wodurch sichergestellt wird, dass große Haufen von Abbau-Drops keine Partikelüberlastung verursachen.
2. **Frequenz-Staffelung**: Nur in jedem 4. Tick werden für eine einzelne Entität Partikel erzeugt, zeitlich versetzt über die Entitäts-IDs: `(entity.tickCount + entity.getId()) % 4 == 0`.
3. **Mengensteuerung**: Geregelt pro Quelle über `ig:magnet_particle_count` (Standard: `1`).

---

## 📝 Dedizierter Debug-Dateilogger (`MagnetDebugLogger`)

Für Server-Administratoren und Modpack-Entwickler, die Sichtliniengrenzen oder Netzwerkpakete analysieren möchten, enthält die Mod einen dedizierten, threadsicheren asynchronen Dateilogger, der nach `logs/ig_magnet_debug.log` schreibt:

* **Aktivierung**: Führe `/magnet debug log` im Spiel aus.
* **Format**: `[yyyy-MM-dd HH:mm:ss.SSS] [Kontext] Nachricht`
* **Beispielhafter Log-Auszug**:
  ```text
  [2026-08-22 21:50:00.124] MagnetMod: Server received packet from Alex (e7b1a234-...) enabled=true
  [2026-08-22 21:50:00.130] MagnetMovement: Entity 592 is now magnetized by Alex (e7b1a234-...).
  [2026-08-22 21:50:01.450] PlayerMixin: Server tick for Alex (e7b1a234-...) isMagnetEnabled=true
  ```

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Brigadier-Befehle|de_de-26.2-Commands]]
* [[Erfahrungs-Orb-Anziehung|de_de-26.2-Experience-Orb-Attraction]]
* [[Zurück zum MC 26.2 Portal|de_de-26.2-Home]]
