# 💻 Brigadier-Befehle & In-Game-Diagnose (MC 26.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Infobox | Technische Parameter |
| :--- | :--- |
| **Befehlsklasse** | `net.instantgratification.magnet.MagnetCommand` |
| **Primäre Literale** | `/magnet` und `/ig_magnet` (Identische Alias-Spiegelungen) |
| **Registrierungs-Callback** | `CommandRegistrationCallback.EVENT` |
| **Protokolldatei-Ausgabe** | `logs/ig_magnet_debug.log` |
| **Zielberechtigungen** | Für alle Spieler verfügbar (`toggle`) und OP-Level 2 (`debug`) |

---

## 📖 Befehlsbaum-Struktur

```
/magnet (oder /ig_magnet)
  ├── toggle
  └── debug
        └── log
```

---

## 🛠️ Unterbefehls-Referenz

### 1. `/magnet toggle` (oder `/ig_magnet toggle`)
Schaltet den persönlichen Magnetstatus des ausführenden Spielers ein oder aus.

* **Verwendung**: `/magnet toggle`
* **Ausführung**: Ruft `((IMagnetPlayer) player).ig_magnet$toggleMagnet()` auf.
* **Ausgabe-Rückmeldung**:
  - Wenn aktiviert: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
  - Wenn deaktiviert: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)
* **Anwendungsfall**: Ermöglicht es Spielern, die von Vanilla-Clients (auf rein serverseitigen Setups) verbinden oder Spielern ohne Tastenbelegung, ihren Item-Magneten umzuschalten.

---

### 2. `/magnet debug` (oder `/ig_magnet debug`)
Führt eine sofortige Diagnoseprüfung in der Welt für den ausführenden Spieler und umgebende Entitäten im Umkreis von 10 Blöcken durch.

* **Verwendung**: `/magnet debug`
* **Ausgabe-Informationen**:
  ```text
  §e--- Magnet Debug Info ---
  Player Name: Steve
  Player UUID: c0b59055-ec50-4653-979d-c3c92d6ca897
  Global Master Toggle: true
  Player Toggle State: true
  Range: 12
  Instant Pickup: false
  LOS Only: true
  Spectator: false
  Dead/Dying: false
  Debug File Logger: DISABLED (Toggle with /magnet debug log)
  Nearby Items (10 blocks): 3
  Nearest Item ID: 412
  PlayerVisionTracker.canSee: true
  Vanilla Raycast canSee: true
  §e-------------------------
  ```
* **Anwendungsfall**: Diagnostiziert, warum ein Gegenstand möglicherweise nicht angezogen wird (z. B. strikte Sichtlinien-Blockierung, Spieler-Todesstatus oder globale GameRule-Überschreibungen).

---

### 3. `/magnet debug log` (oder `/ig_magnet debug log`)
Schaltet die persistente, detaillierte Diagnoseprotokollierung auf Festplatte um.

* **Verwendung**: `/magnet debug log`
* **Ausführung**: Wechselt `MagnetDebugLogger.enabled = !MagnetDebugLogger.enabled`.
* **Ausgabe**: Schreibt zeitgestempelte Tick-Ereignisse, Paketempfänge, Respawn-Handshakes und Sichtlinien-Abweisungen in `logs/ig_magnet_debug.log`.
* **Beispielhafter Protokolleintrag**:
  ```log
  [2026-08-22 21:49:15.102] MagnetMod: Server received packet from Steve (c0b59055-...) enabled=true
  [2026-08-22 21:49:15.105] MagnetMovement: Entity 412 is now magnetized by Steve (c0b59055-...).
  ```

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Spieler-Umschaltung & Zustandsspeicherung|de_de-26.2-Player-Toggle-and-Persistence]]
* [[HUD & Diagnose|de_de-26.2-HUD-and-Diagnostics]]
* [[GameRules-Referenz|de_de-26.2-GameRules]]
* [[Zurück zum MC 26.2 Portal|de_de-26.2-Home]]
