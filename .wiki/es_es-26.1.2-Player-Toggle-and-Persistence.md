# 🔄 Alternancia del Jugador y Gestión de Estado (MC 26.1.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Característica | Parámetros Técnicos |
| :--- | :--- |
| **Clase de Almacenamiento de Estado** | `net.instantgratification.magnet.MagnetPlayerState` |
| **Tecla Rápida por Defecto del Cliente** | `Ctrl+M` (`GLFW.GLFW_KEY_M` + `isControlDown()`) |
| **Categoría de Tecla** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Payload de Red** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **API de Actionbar** | `client.gui.setOverlayMessage(Component, boolean)` |

---

## 📖 Visión General de la Arquitectura de Estado

En Minecraft 26.1.2, las preferencias de alternancia de los jugadores se gestionan durante la sesión activa del servidor mediante `MagnetPlayerState` utilizando un `ConcurrentHashMap` seguro para subprocesos:

```java
public class MagnetPlayerState {
    private static final Map<UUID, Boolean> playerStates = new ConcurrentHashMap<>();

    public static boolean isMagnetEnabled(Player player) {
        return playerStates.getOrDefault(player.getUUID(), true);
    }

    public static void setMagnetEnabled(Player player, boolean enabled) {
        playerStates.put(player.getUUID(), enabled);
    }

    public static boolean toggleMagnet(Player player) {
        boolean newState = !isMagnetEnabled(player);
        setMagnetEnabled(player, newState);
        return newState;
    }
}
```

---

## ⌨️ Asignación de Tecla en el Cliente (`Ctrl+M`)

* **Combinación Predeterminada**: `Ctrl+M` (`GLFW_KEY_M` + `isControlDown()`), con compatibilidad multiplataforma para la tecla Comando en macOS (`GLFW_KEY_LEFT_SUPER`).
* **Respuesta Visual en Actionbar**:
  - `chat.ig_magnet.enabled`: `"Item Magnet: Enabled"`
  - `chat.ig_magnet.disabled`: `"Item Magnet: Disabled"`

---

## 📡 Protocolo de Sincronización de Red

```
[CLIENTE]                                                          [SERVIDOR]
Jugador pulsa Ctrl+M
MagnetPlayerState.setMagnetEnabled(player, newState)
ClientPlayNetworking.send(new MagnetTogglePayload(newState))
                                               ------------------> Receptor en Servidor
                                                                   MagnetPlayerState.setMagnetEnabled(player, payload.enabled())
```

---

## 🔗 Documentación Relacionada de la Wiki
* [[Comandos de Servidor y Alternancias|es_es-26.1.2-Commands]]
* [[Arquitectura y Mixins|es_es-26.1.2-Architecture-and-Mixins]]
* [[Volver al Portal de MC 26.1.2|es_es-26.1.2-Home]]
