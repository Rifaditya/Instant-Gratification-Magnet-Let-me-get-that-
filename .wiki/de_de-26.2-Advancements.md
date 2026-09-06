# 🏆 Fortschritte & Progressionsumfang (MC 26.2)

> 📌 **Haftungsausschluss für Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der aktuelle unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

| Progressions-Infobox | Details |
| :--- | :--- |
| **Eigene Fortschritts-JSONs** | `Keine (Gewolltes Vertrauen auf Vanilla)` |
| **Design-Richtung** | Instant Gratification (IG) |
| **Fortschrittsauslöser** | 100% natives Vanilla `Player.touch(ItemEntity)` |
| **Freischaltbedingungen** | Keine (Null willkürliche Fortschrittshürden) |

---

## 📖 Abwesenheitsrichtlinie & Designumfang

In strikter Übereinstimmung mit der **Instant Gratification (IG)**-Modding-Philosophie enthält **Magnet, Let me get that!** absichtlich **keine eigenen Fortschrittsbäume oder Meilenstein-Errungenschaften**.

Die Mod ist als intrinsische Quality-of-Life-Erweiterung der grundlegenden Survival-Interaktionen des Spielers konzipiert. Alle Funktionen sind ab dem ersten Betreten der Welt sofort freigeschaltet – ohne künstliches Quest-Grinding, Forschungsbäume oder Progressionsblockaden.

```
+-----------------------------------------------------------------------------------+
|                     INSTANT GRATIFICATION DESIGN-GRUNDSATZ                        |
|                                                                                   |
|  "Der 'Walk of Shame' (5 Blöcke weit zu laufen, um ein gerade abgebautes Erz     |
|   aufzuheben) ist eine Todsünde des Flow-Zustands. IG Magnet ist kein             |
|   Technologiebaum-Gegenstand oder eine Belohnung; es ist eine Erweiterung des     |
|   Willens des Spielers. Wer es sehen kann, sollte es auch sofort besitzen."       |
+-----------------------------------------------------------------------------------+
```

---

## 🎮 Native Vanilla-Fortschrittskompatibilität

Da sowohl der Standard-Vakuumflug als auch der [[Sofort-Aufnahme-Modus|de_de-26.2-Instant-Pickup-Mode]] die nativen Sammel-Pipelines `ItemEntity.playerTouch()` und `ExperienceOrb.playerTouch()` von Minecraft nutzen:

1. **Vanilla-Meilensteinauslöser**: Das Einsammeln von Diamanten, Antikem Schrott oder Lohenruten über den Magneten löst sofort die regulären Vanilla-Fortschrittskriterien aus (z. B. „Diamanten!“, „Bedecke mich mit Schrott“).
2. **Kompatibilität mit Quest-Mods**: Quest-Mods (wie FTB Quests oder Better Questing), die Gegenstandsaufnahmen im Spielerinventar verfolgen, funktionieren ohne spezielle Kompatibilitätsbrücken direkt nach der Installation.
3. **Statistik-Erfassung**: Vanilla-Statistiken (`stat.pickup.minecraft.*`) werden weiterhin präzise hochgezählt.

---

## 🔗 Verwandte Wiki-Dokumentation
* [[Sofort-Aufnahme-Modus|de_de-26.2-Instant-Pickup-Mode]]
* [[GameRules-Referenz|de_de-26.2-GameRules]]
* [[Zurück zum MC 26.2 Portal|de_de-26.2-Home]]
