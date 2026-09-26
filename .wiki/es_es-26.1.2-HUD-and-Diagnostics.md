# 📊 HUD, Elementos Visuales y Superposición (MC 26.1.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox Visual | Parámetros Técnicos |
| :--- | :--- |
| **API de Actionbar** | `client.gui.setOverlayMessage(Component, boolean)` |
| **Tipo de Partícula** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **Módulo de Limitación Temporal** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **Límite de Fuentes de Partículas** | `ig:magnet_max_particle_sources` (Por Defecto: `5`) |

---

## 🖥️ Notificación en Superposición de Actionbar

Cuando el jugador conmuta el imán mediante la combinación rápida `Ctrl+M`, la interfaz gráfica del cliente muestra de inmediato una notificación sobre la barra de acceso rápido:

* **Activado**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **Desactivado**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// Verificado contra: Gui.java (26.1.2)
if (newState) {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ Rastros Visuales de Chispas Eléctricas

Los objetos y orbes de experiencia en atracción emiten rastros visuales de partículas de chispa:

```
[Objeto / XP Atraído]  --->  ✨  --->  ✨  --->  ✨  --->  [Posición Ojos Jugador]
```

* **Control de Fuentes**: Un máximo de 5 fuentes simultáneas emiten partículas (`ig:magnet_max_particle_sources = 5`).
* **Frecuencia Temporal**: Las partículas se generan cada 4 ticks ($5\text{ veces/segundo}$).
* **Control de Densidad**: `ig:magnet_particle_count = 1`.

---

## 🔗 Documentación Relacionada de la Wiki
* [[Alternancia del Jugador y Gestión de Estado|es_es-26.1.2-Player-Toggle-and-Persistence]]
* [[Atracción de Orbes de Experiencia|es_es-26.1.2-Experience-Orb-Attraction]]
* [[Volver al Portal de MC 26.1.2|es_es-26.1.2-Home]]
