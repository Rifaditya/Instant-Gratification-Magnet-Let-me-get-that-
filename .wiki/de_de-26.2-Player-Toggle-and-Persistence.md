# 🔄 Spieler-Umschaltung, Persistenz & Lebenszyklus (MC 26.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Infobox | Technische Parameter |
| :--- | :--- |
| **Schnittstellen-Brücke** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Mixin-Ziel** | `net.minecraft.world.entity.player.Player` (`PlayerMixin.java`) |
| **Standard-Client-Hotkey** | `\` (Backslash) — `key.ig_magnet.toggle` |
| **Tasten-Kategorie** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Netzwerk-Nutzlast** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **NBT-Codec-Speicherung** | `ValueOutput` / `ValueInput` unter Tag `"ig_magnet_enabled"` |
| **Lebenszyklus-Ereignisse** | `ServerPlayConnectionEvents.JOIN`, `ServerPlayerEvents.COPY_FROM`, `ServerPlayerEvents.AFTER_RESPAWN` |

---

## 📖 Übersicht der Status-Architektur

Auf Mehrspieler-Servern und in Modpacks haben einzelne Spieler oft unterschiedliche Vorlieben – Baumeister möchten die Gegenstandsanziehung während des Dekorierens pausieren, während Bergleute maximale Vakuumkraft wünschen.

**Magnet, Let me get that!** implementiert einen **individualisierten Umschaltstatus pro Spieler**, der über Welt-Neuladevorgänge, Tode, Respawns und Dimensionswechsel hinweg zu 100% persistent bleibt.

```
                                [CLIENT-AKTION]
                      Spieler drückt Hotkey ('\')
                                       |
                                       v
                           [LOKALER STATUS AKTUALISIERT]
                      client.player -> isEnabled = !isEnabled
                      Actionbar-Overlay: "Item-Magnet: Aktiviert/Deaktiviert"
                                       |
                                       v
                          [C2S-PAKETÜBERTRAGUNG]
                      ClientPlayNetworking.send(MagnetTogglePayload)
                                       |
                                       v
                                [SERVER-EMPFÄNGER]
                      context.server().execute(() -> {
                          ((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(val);
                      })
                                       |
          +----------------------------+----------------------------+
          |                                                         |
          v                                                         v
  [NBT-DATEN PERSISTIERT]                               [LEBENSZYKLUS-HOOKS VERBUNDEN]
  ValueOutput.putBoolean("ig_magnet_enabled")           - JOIN: S2C-Synchronisation
  ValueInput.getBooleanOr("ig_magnet_enabled", true)    - COPY_FROM: Erhalt beim Tod
                                                        - AFTER_RESPAWN: S2C-Sync für neue Entität
```

---

## ⌨️ Client-Tastenbelegung & Actionbar-Overlay

* **Standard-Taste**: `GLFW_KEY_BACKSLASH` (`\`), wodurch Konflikte mit gängigen Karten-Mods und Inventar-Hilfsprogrammen vermieden werden.
* **Dynamischer Tastentyp-Helfer**: Nutzt `ig_magnet$getKeyboardType()`, um `InputConstants.Type.KEYBOARD` mit sicherem Fallback auf `KEYSYM` über verschiedene Fabric-Loader-Snapshots hinweg aufzulösen.
* **Sofortiges visuelles Feedback**: Das Umschalten löst eine lokalisierte Actionbar-Nachricht aus:
  - `chat.ig_magnet.enabled`: `"Item-Magnet: Aktiviert"`
  - `chat.ig_magnet.disabled`: `"Item-Magnet: Deaktiviert"`

---

## 💾 NBT-Speicherung & Minecraft 26.2 Codec-Serialisierung

Die Umschalteinstellungen des Spielers werden direkt in der `.dat`-Speicherdatei des Spielers unter Verwendung der Minecraft 26.2 `ValueOutput`- und `ValueInput`-Datenpipelines gesichert:

```java
// Speichern im Spieler-NBT
@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
    output.putBoolean("ig_magnet_enabled", this.ig_magnet$enabled);
}

// Laden aus dem Spieler-NBT
@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
    this.ig_magnet$enabled = input.getBooleanOr("ig_magnet_enabled", true);
}
```

---

## 🧬 Fabric-Lifecycle-Events & Tod/Respawn-Ablauf

Stirbt ein Spieler in Minecraft, erzeugt das Spiel beim Wiederbeleben eine völlig neue `ServerPlayer`-Entität. Die Mod garantiert vollständigen Statuserhalt:

1. **`ServerPlayerEvents.COPY_FROM`**: Kopiert den Umschalt-Boolean beim Klonen der Entität sofort vom alten auf den neuen Spieler.
2. **`ServerPlayerEvents.AFTER_RESPAWN`**: Sendet automatisch ein S2C-`MagnetTogglePayload` an den Client, sobald die neue Spielerentität verbunden ist, um das Client-HUD synchron zu halten.
3. **`ServerPlayConnectionEvents.JOIN`**: Synchronisiert den gespeicherten NBT-Status des Spielers beim Beitreten zu einem dedizierten Server oder einer LAN-Welt.

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Brigadier-Befehle|de_de-26.2-Commands]]
* [[Architektur & Mixins|de_de-26.2-Architecture-and-Mixins]]
* [[HUD & Diagnose|de_de-26.2-HUD-and-Diagnostics]]
* [[Zurück zum MC 26.2 Portal|de_de-26.2-Home]]
