# 🔌 API e Integración de Addons (MC 26.1.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de API | Parámetros Técnicos |
| :--- | :--- |
| **Gestor de Estado del Jugador** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Interfaz de Entidad** | `net.instantgratification.magnet.IMagnetEntity` |
| **Fachada de Movimiento Principal** | `net.instantgratification.magnet.MagnetMovement` |
| **API de GameRules** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **API de Visión** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 Integración entre Mods y Desarrolladores

Los mods de terceros y los complementos de la colección Instant Gratification pueden interactuar directamente con **Magnet, Let me get that!** en Minecraft 26.1.2.

---

## 🧑‍💻 Gestión del Estado del Jugador (`MagnetPlayerState`)

Consulta o modifica las preferencias de atracción del jugador directamente mediante métodos estáticos auxiliares:

```java
package net.instantgratification.magnet;

public class MagnetPlayerState {
    public static boolean isMagnetEnabled(Player player);
    public static void setMagnetEnabled(Player player, boolean enabled);
    public static boolean toggleMagnet(Player player);
}
```

### Ejemplo de Uso:
```java
// Comprobar si el jugador tiene el imán activo
if (MagnetPlayerState.isMagnetEnabled(player)) {
    // Lógica personalizada...
}

// Desactivar programáticamente el imán
MagnetPlayerState.setMagnetEnabled(player, false);
```

---

## 📦 Interfaz de Magnetización de Entidades (`IMagnetEntity`)

Realiza un casting de cualquier instancia de `Entity` a `IMagnetEntity` para manipular sus banderas de cambio de fase o de magnetización:

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig$setMagnetNoClip();
    boolean ig$isMagnetNoClip();
    void ig$setMagnetized();
    boolean ig$isMagnetized();
}
```

---

## 🚀 Fachada Estática de Movimiento (`MagnetMovement.pull`)

Activa programáticamente la atracción de objetos o XP hacia un jugador:

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

---

## 🔗 Documentación Relacionada de la Wiki
* [[Arquitectura y Objetivos Mixin|es_es-26.1.2-Architecture-and-Mixins]]
* [[Configuración y Compilación Loom|es_es-26.1.2-Developer-Setup-and-Building]]
* [[Volver al Portal de MC 26.1.2|es_es-26.1.2-Home]]
