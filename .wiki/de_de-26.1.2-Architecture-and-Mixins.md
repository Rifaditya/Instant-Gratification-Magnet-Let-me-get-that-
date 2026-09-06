# 🧩 Architektur & Mixin-Ziele (MC 26.1.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Architektur-Infobox | Technische Parameter |
| :--- | :--- |
| **Wurzel-Paket** | `net.instantgratification.magnet` |
| **Mixin-Konfiguration** | `src/main/resources/magnet.mixins.json` |
| **Access Widener** | `src/main/resources/magnet.accesswidener` |
| **Kompatibilitätsstufe** | `JAVA_25` |
| **Mixin-Klassen gesamt** | `2` (`MixinEntity`, `PlayerMixin`) |

---

## 🌳 Paket-Architekturbaum

```text
net.instantgratification.magnet/
├── IMagnetEntity.java                  # Entitäts-Schnittstelle für NoClip & Magnetisierungs-Flags
├── MagnetCommand.java                  # Server-Befehlsregistrierung (/magnet toggle)
├── MagnetManager.java                  # Entitäts-Scan & räumliche Ausführungsschleife
├── MagnetMod.java                      # Mod-Initialisierer & Paket-Empfänger
├── MagnetModClient.java                # Client-Initialisierer, Strg+M Keybind & Overlay-Toast
├── MagnetMovement.java                 # Flugbahn-Vektormathematik, Lerp-Geschwindigkeit & Partikel
├── MagnetPlayerState.java              # Threadsicherer ConcurrentHashMap Spieler-Umschaltzustand
├── MagnetTogglePayload.java            # Netzwerk-Paket-Record & StreamCodec-Zusammensetzung
├── SecondaryVisionCheck.java           # Granulares Block-Raycasting (Flora, Block-Entities, Glas)
├── config/
│   ├── ClothConfigScreenHelper.java    # Cloth Config Fabric GUI-Builder
│   ├── MagnetConfig.java               # JSON-Konfigurationsspeicher & POJO-Felder
│   └── ModMenuIntegration.java         # Reflexionssicherer ModMenu-API-Einstiegspunkt
├── mixin/
│   ├── MixinEntity.java                # Injiziert NoClip & Gravitationsabbruch in Entity
│   └── PlayerMixin.java                # Injiziert Tick-Ausführung & Sofortaufnahme in Player
└── registry/
    └── ModGameRules.java               # DynamicGameRuleManager-Registrierungen
```

---

## 📋 Aufschlüsselung der Mixin-Injektionsziele

### 1. `net.instantgratification.magnet.mixin.MixinEntity`
Zielt auf `net.minecraft.world.entity.Entity` ab und implementiert `IMagnetEntity`.

| Injizierte Methode | Injektionsziel-Punkt | Aktion & Verhalten |
| :--- | :--- | :--- |
| `ig_magnet$decrementNoClipTicks` | `@Inject(method = "tick", at = @At("HEAD"))` | Zählt den `noClipTicks`-Countdown pro Tick um 1 herunter, wenn aktiv. |
| `ig_magnet$preventPushOut` | `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` | Bricht serverseitig das Vanilla-Ausstoßen ab, wenn `noClipTicks > 0`. |
| `ig_magnet$forceNoClipOnMove` | `@Inject(method = "move", at = @At("HEAD"))` | Sichert `originalNoPhysics` und erzwingt `entity.noPhysics = true`, falls `noClipTicks > 0`. |
| `ig_magnet$restoreNoPhysicsAfterMove` | `@Inject(method = "move", at = @At("RETURN"))` | Stellt `entity.noPhysics = originalNoPhysics` nach Bewegungsabschluss wieder her. |
| `ig_magnet$conditionalCancelGravity` | `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` | Bricht Gravitationsbeschleunigung nach unten *nur dann* ab, wenn sich das Item in einem Block befindet. |

---

### 2. `net.instantgratification.magnet.mixin.PlayerMixin`
Zielt auf `net.minecraft.world.entity.player.Player` ab.

| Injizierte Methode | Injektionsziel-Punkt | Aktion & Verhalten |
| :--- | :--- | :--- |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | Führt in jedem Spieltick auf dem Server `MagnetManager.tick(player)` aus. |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | Erweitert die Aufnahme-Bounding-Box (`pickupArea.inflate(range)`), wenn `ig:magnet_instant` true ist. |

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Vakuum & Phasenverschiebung|de_de-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Entwickler-Setup & Build|de_de-26.1.2-Developer-Setup-and-Building]]
* [[Zurück zum MC 26.1.2 Portal|de_de-26.1.2-Home]]
