# 🧩 Architektur & Mixin-Ziele (MC 26.2)

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
├── IMagnetPlayer.java                  # Spieler-Schnittstelle für Umschaltstatus & Getter/Setter
├── MagnetCommand.java                  # Brigadier-Befehlsbäume (/magnet toggle, /magnet debug)
├── MagnetDebugLogger.java              # Threadsicheres permanentes Datei-Logging auf Festplatte
├── MagnetManager.java                  # Entitäts-Scan & räumliche Ausführungsschleife
├── MagnetMod.java                      # Server/Universal Mod-Einstiegspunkt & Netzwerk-Empfänger
├── MagnetModClient.java                # Client-Initialisierer, Tastenbelegung & Overlay-Toast
├── MagnetMovement.java                 # Flugbahn-Vektormathematik, Lerp-Geschwindigkeit & Partikel
├── MagnetTogglePayload.java            # Netzwerk-Paket-Record & StreamCodec-Zusammensetzung
├── SecondaryVisionCheck.java           # Granulares Block-Raycasting (Flora, Block-Entities, Glas)
├── config/
│   ├── MagnetConfig.java               # JSON-Konfigurationsspeicher & POJO-Felder
│   ├── ModMenuIntegration.java         # Reflexionssicherer ModMenu-API-Einstiegspunkt
│   └── YaclScreenHelper.java           # YetAnotherConfigLib v3 GUI-Builder
├── mixin/
│   ├── MixinEntity.java                # Injiziert NoClip & Gravitationsabbruch in Entity
│   └── PlayerMixin.java                # Injiziert NBT-Persistenz & Sofortaufnahme in Player
├── registry/
│   └── ModGameRules.java               # DynamicGameRuleManager-Registrierungen
└── util/
    └── ModVersionGuard.java            # Laufzeit-Prüfung für Knot ClassLoader
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
Zielt auf `net.minecraft.world.entity.player.Player` ab und implementiert `IMagnetPlayer`.

| Injizierte Methode | Injektionsziel-Punkt | Aktion & Verhalten |
| :--- | :--- | :--- |
| `ig_magnet$addAdditionalSaveData` | `@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))` | Serialisiert Boolean `ig_magnet_enabled` via `ValueOutput` in das Spieler-NBT. |
| `ig_magnet$readAdditionalSaveData` | `@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))` | Deserialisiert `ig_magnet_enabled` via `ValueInput.getBooleanOr()` aus dem Spieler-NBT. |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | Führt in jedem Spieltick auf dem Server `MagnetManager.tick(player)` aus. |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | Erweitert die Aufnahme-Bounding-Box (`pickupArea.inflate(range)`), wenn `ig:magnet_instant` true ist. |

---

## 🛡️ Knot ClassLoader Konsistenzprüfung (`ModVersionGuard`)

Um Server und Welten vor dem Laden auf inkompatiblen oder fehlerhaften Minecraft-Versionen zu schützen:

```java
public final class ModVersionGuard {
    public static void checkClass(String modName, String requiredClassName) {
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        // Überprüft das Vorhandensein der Zielklasse, bevor die Mod-Initialisierung fortfährt...
    }
}
```

Aufgerufen in `MagnetMod.onInitialize()`:
```java
ModVersionGuard.checkClass("Magnet", "net.minecraft.world.entity.item.ItemEntity");
```

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Vakuum & Phasenverschiebung|de_de-26.2-Vacuum-and-Phase-Shifting]]
* [[Spieler-Umschaltung & Zustandsspeicherung|de_de-26.2-Player-Toggle-and-Persistence]]
* [[API & Addon-Integration|de_de-26.2-API-and-Addon-Integration]]
* [[Zurück zum MC 26.2 Portal|de_de-26.2-Home]]
