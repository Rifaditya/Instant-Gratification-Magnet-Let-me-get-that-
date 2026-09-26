# 💻 Comandos Brigadier y Diagnósticos en el Juego (MC 26.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Característica | Parámetros Técnicos |
| :--- | :--- |
| **Clase del Comando** | `net.instantgratification.magnet.MagnetCommand` |
| **Literales Primarios** | `/magnet` e `/ig_magnet` (Alias espejo idénticos) |
| **Callback de Registro** | `CommandRegistrationCallback.EVENT` |
| **Archivo de Registro de Salida** | `logs/ig_magnet_debug.log` |
| **Permisos de Destino** | Disponible para todos (`toggle`) y Nivel OP 2 (`debug`) |

---

## 📖 Estructura del Árbol de Comandos

```
/magnet (o /ig_magnet)
  ├── toggle
  └── debug
        └── log
```

---

## 🛠️ Referencia de Subcomandos

### 1. `/magnet toggle` (o `/ig_magnet toggle`)
Alterna el estado del imán personal del jugador que ejecuta el comando entre activado y desactivado.

* **Sintaxis**: `/magnet toggle`
* **Ejecución**: Invoca `((IMagnetPlayer) player).ig_magnet$toggleMagnet()`.
* **Mensaje de Salida**:
  - Si está activado: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
  - Si está desactivado: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)
* **Caso de Uso**: Permite a jugadores conectados desde clientes vanilla (en servidores dedicados que solo tienen el mod en servidor) o sin teclas asignadas alternar su imán.

---

### 2. `/magnet debug` (o `/ig_magnet debug`)
Ejecuta un diagnóstico inmediato del jugador ejecutor y de las entidades del entorno en un radio de 10 bloques.

* **Sintaxis**: `/magnet debug`
* **Información de Salida**:
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
* **Caso de Uso**: Diagnostica por qué un objeto no está siendo atraído (p. ej. obstrucción estricta de visión, jugador en estado muerto o reglas globales de GameRules).

---

### 3. `/magnet debug log` (o `/ig_magnet debug log`)
Alterna el registro persistente y detallado de depuración en el disco.

* **Sintaxis**: `/magnet debug log`
* **Ejecución**: Alterna `MagnetDebugLogger.enabled = !MagnetDebugLogger.enabled`.
* **Salida**: Escribe eventos con marca de tiempo, recepción de paquetes, sincronizaciones de reaparición y rechazos de visión en `logs/ig_magnet_debug.log`.
* **Ejemplo de Entrada en el Archivo de Registro**:
  ```log
  [2026-08-22 21:49:15.102] MagnetMod: Server received packet from Steve (c0b59055-...) enabled=true
  [2026-08-22 21:49:15.105] MagnetMovement: Entity 412 is now magnetized by Steve (c0b59055-...).
  ```

---

## 🔗 Documentación Relacionada de la Wiki
* [[Alternancia, Persistencia y Ciclo de Vida del Jugador|es_es-26.2-Player-Toggle-and-Persistence]]
* [[HUD y Sistema de Diagnósticos|es_es-26.2-HUD-and-Diagnostics]]
* [[Referencia Completa de GameRules|es_es-26.2-GameRules]]
* [[Volver al Portal de MC 26.2|es_es-26.2-Home]]
