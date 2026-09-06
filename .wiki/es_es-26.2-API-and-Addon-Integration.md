# 🔌 API e Integración de Addons (MC 26.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de API | Parámetros Técnicos |
| :--- | :--- |
| **Interfaz de Jugador** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Interfaz de Entidad** | `net.instantgratification.magnet.IMagnetEntity` |
| **Fachada de Movimiento Principal** | `net.instantgratification.magnet.MagnetMovement` |
| **API de GameRules** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **API de Visión** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Integración entre Mods y Desarrolladores

Los mods de terceros, utilidades de servidor y complementos de la colección Instant Gratification pueden interactuar directamente con **Magnet, Let me get that!** para consultar el estado del imán de los jugadores, activar atracciones mediante código o gestionar la permeabilidad de obstáculos.

---

## 🧑‍💻 Interfaz de Estado del Jugador (`IMagnetPlayer`)

Realiza un casting de cualquier instancia de `Player` o `ServerPlayer` a `IMagnetPlayer` para comprobar o modificar sus preferencias de magnetismo:

```java
package net.instantgratification.magnet;

public interface IMagnetPlayer {
    boolean ig_magnet$isMagnetEnabled();
    void ig_magnet$setMagnetEnabled(boolean enabled);
    boolean ig_magnet$toggleMagnet();
}
```

### Ejemplo de Uso:
```java
// Comprobar si el jugador tiene el imán activo
if (((IMagnetPlayer) player).ig_magnet$isMagnetEnabled()) {
    // Lógica personalizada del addon...
}

// Desactivar programáticamente el imán (p. ej. sentado en un trono o durante un minijuego)
((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(false);
```

---

## 📦 Interfaz de Magnetización de Entidades (`IMagnetEntity`)

Realiza un casting de cualquier instancia de `Entity` (como drops personalizados, proyectiles u orbes de XP) a `IMagnetEntity`:

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig_magnet$setMagnetNoClip();
    boolean ig_magnet$isMagnetNoClip();
    void ig_magnet$setMagnetized();
    boolean ig_magnet$isMagnetized();
}
```

### Ejemplo de Uso:
```java
// Conceder a una entidad personalizada una fase NoClip temporal de 2 ticks
((IMagnetEntity) customItem).ig_magnet$setMagnetNoClip();
```

---

## 🚀 Fachada Estática de Movimiento (`MagnetMovement.pull`)

Los mods complementarios pueden atraer entidades manualmente hacia cualquier jugador utilizando el motor integrado de físicas y línea de visión:

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

```java
// Atraer entidad objetivo hacia el jugador con estela de partículas
MagnetMovement.pull(specialDrop, player, true);
```

---

## 🔗 Documentación Relacionada de la Wiki
* [[Arquitectura y Objetivos Mixin|es_es-26.2-Architecture-and-Mixins]]
* [[Configuración y Compilación Loom|es_es-26.2-Developer-Setup-and-Building]]
* [[Volver al Portal de MC 26.2|es_es-26.2-Home]]
