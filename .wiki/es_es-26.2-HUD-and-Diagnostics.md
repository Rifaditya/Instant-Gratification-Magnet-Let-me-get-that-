# 📊 HUD, Elementos Visuales y Diagnósticos (MC 26.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox Visual | Parámetros Técnicos |
| :--- | :--- |
| **API de Actionbar** | `client.gui.hud.setOverlayMessage(Component, boolean)` |
| **Tipo de Partícula** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Módulo de Limitación Temporal** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **Archivo de Registro de Depuración** | `logs/ig_magnet_debug.log` |
| **Clase del Registrador** | `net.instantgratification.magnet.MagnetDebugLogger` |

---

## 🖥️ Notificación en Superposición de Actionbar

Cuando el jugador conmuta el estado de su imán mediante la tecla rápida (`\`) o mediante el comando de servidor (`/magnet toggle`), el HUD del cliente muestra de inmediato un aviso visual no invasivo sobre la barra de acceso rápido:

* **Mensaje Activado**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Mensaje Desactivado**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Verificado contra: Hud.java (26.2+)
if (newState) {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Rastros Visuales de Chispas Eléctricas

Mientras los objetos y orbes de experiencia están siendo atraídos en pleno vuelo, desprenden rastros visuales sutiles de `ParticleTypes.ELECTRIC_SPARK`:

```
[Objeto Atraído]  --->  ✨  --->  ✨  --->  ✨  --->  [Posición Ojos Jugador]
```

### Reglas de Control de Partículas:
1. **Límite de Fuentes**: Regulado por `ig:magnet_max_particle_sources` (por defecto: `5`), garantizando que pilas gigantescas de ítems en canteras no saturen la pantalla.
2. **Escalonamiento de Frecuencia**: Solo 1 de cada 4 ticks genera partículas por entidad individual, intercaladas según el ID: `(entity.tickCount + entity.getId()) % 4 == 0`.
3. **Densidad**: Controlada por fuente mediante `ig:magnet_particle_count` (por defecto: `1`).

---

## 📝 Registrador de Depuración en Archivo (`MagnetDebugLogger`)

Para administradores de servidores y creadores de modpacks que investigan límites de línea de visión o paquetes de red, el mod incluye un registrador asíncrono y seguro para hilos que escribe en `logs/ig_magnet_debug.log`:

* **Activación**: Ejecuta `/magnet debug log` en el juego.
* **Formato**: `[yyyy-MM-dd HH:mm:ss.SSS] [Context] Message`
* **Muestra del Registro**:
  ```text
  [2026-08-22 21:50:00.124] MagnetMod: Server received packet from Alex (e7b1a234-...) enabled=true
  [2026-08-22 21:50:00.130] MagnetMovement: Entity 592 is now magnetized by Alex (e7b1a234-...).
  [2026-08-22 21:50:01.450] PlayerMixin: Server tick for Alex (e7b1a234-...) isMagnetEnabled=true
  ```

---

## 🔗 Documentación Relacionada de la Wiki
* [[Comandos Brigadier y Diagnósticos en el Juego|es_es-26.2-Commands]]
* [[Atracción de Orbes de Experiencia|es_es-26.2-Experience-Orb-Attraction]]
* [[Volver al Portal de MC 26.2|es_es-26.2-Home]]
