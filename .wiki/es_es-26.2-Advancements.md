# 🏆 Progresos y Alcance de Progresión (MC 26.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Progresión | Detalles |
| :--- | :--- |
| **JSONs de Progresos Propios** | `Ninguno (Dependencia de Vanilla por Diseño)` |
| **Línea de Diseño** | Instant Gratification (IG) |
| **Disparadores de Progresos** | 100% Nativos de Vanilla `Player.touch(ItemEntity)` |
| **Requisito de Bloqueo** | Ninguno (Cero obstáculos artificiales) |

---

## 📖 Política de Ausencia y Alcance de Diseño

En estricta conformidad con la filosofía de diseño **Instant Gratification (IG)**, **Magnet, Let me get that!** intencionalmente **no contiene árboles de progresos personalizados ni logros de hitos**.

El mod está concebido como una mejora intrínseca de calidad de vida para las interacciones cotidianas de supervivencia del jugador. Las funcionalidades están disponibles desde el primer segundo en que el jugador entra al mundo, sin cadenas de misiones artificiales, árboles de investigación ni barreras de progresión.

```
+-----------------------------------------------------------------------------------+
|                     PRINCIPIO DE DISEÑO INSTANT GRATIFICATION                     |
|                                                                                   |
|  "El 'Paseo de la Vergüenza' (caminar 5 bloques para recoger un bloque recién     |
|   minado) es un pecado capital contra el flujo de juego. IG Magnet no es un ítem  |
|   de un árbol tecnológico; es una extensión de la voluntad. Si puedes verlo,     |
|   deberías tenerlo."                                                              |
+-----------------------------------------------------------------------------------+
```

---

## 🎮 Compatibilidad con Progresos Nativos de Vanilla

Debido a que tanto el vuelo estándar de aspiración como el [[Modo de Recogida Instantánea|es_es-26.2-Instant-Pickup-Mode]] utilizan los canales de recolección nativos de Minecraft `ItemEntity.playerTouch()` y `ExperienceOrb.playerTouch()`:

1. **Disparadores de Hitos de Vanilla**: Recoger diamantes, escombros ancestrales o varas de blaze mediante el imán activa de inmediato los criterios de logros de vanilla (p. ej. `"¡Diamantes!"`, `"Cúbreme de escombros"`).
2. **Compatibilidad con Mods de Misiones de Terceros**: Los mods de misiones (como FTB Quests o Better Questing) que rastrean la recolección de objetos en los inventarios funcionan sin necesidad de puentes o parches adicionales.
3. **Seguimiento de Estadísticas**: Las estadísticas nativas del juego (`stat.pickup.minecraft.*`) continúan incrementándose con total precisión.

---

## 🔗 Documentación Relacionada de la Wiki
* [[Modo de Recogida Instantánea|es_es-26.2-Instant-Pickup-Mode]]
* [[Referencia Completa de GameRules|es_es-26.2-GameRules]]
* [[Volver al Portal de MC 26.2|es_es-26.2-Home]]
