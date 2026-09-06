# 🔄 Alternancia, Persistencia y Ciclo de Vida del Jugador (MC 26.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Característica | Parámetros Técnicos |
| :--- | :--- |
| **Interfaz Puente** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Objetivo Mixin** | `net.minecraft.world.entity.player.Player` (`PlayerMixin.java`) |
| **Tecla Rápida por Defecto** | `\` (Barra Invertida) — `key.ig_magnet.toggle` |
| **Categoría de Tecla** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **Payload de Red** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **Almacenamiento en Códec NBT** | `ValueOutput` / `ValueInput` bajo etiqueta `"ig_magnet_enabled"` |
| **Eventos de Ciclo de Vida** | `ServerPlayConnectionEvents.JOIN`, `ServerPlayerEvents.COPY_FROM`, `ServerPlayerEvents.AFTER_RESPAWN` |

---

## 📖 Visión General de la Arquitectura de Estado

En servidores multijugador y paquetes de mods, los jugadores tienen distintas preferencias: los constructores pueden preferir pausar la atracción mientras decoran, mientras que los mineros buscan la máxima capacidad de aspiración.

**Magnet, Let me get that!** implementa un **estado de alternancia individual por jugador** que es 100% persistente a través de recargas de mundo, muertes, reapariciones y viajes entre dimensiones.

```
                               [ACCIÓN DEL CLIENTE]
                    Jugador Pulsa Tecla de Alternancia ('\')
                                      |
                                      v
                        [ESTADO LOCAL ACTUALIZADO]
                    client.player -> isEnabled = !isEnabled
                    Mensaje Actionbar: "Item Magnet: Enabled/Disabled"
                                      |
                                      v
                        [TRANSMISIÓN DE PAQUETE C2S]
                    ClientPlayNetworking.send(MagnetTogglePayload)
                                      |
                                      v
                             [RECEPTOR SERVIDOR]
                    context.server().execute(() -> {
                        ((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(val);
                    })
                                      |
         +----------------------------+----------------------------+
         |                                                         |
         v                                                         v
 [DATOS NBT PERSISTIDOS]                               [GANCHOS DE CICLO DE VIDA]
 ValueOutput.putBoolean("ig_magnet_enabled")           - JOIN: Sincronización S2C
 ValueInput.getBooleanOr("ig_magnet_enabled", true)    - COPY_FROM: Retener tras Muerte
                                                       - AFTER_RESPAWN: S2C con Nueva Entidad
```

---

## ⌨️ Asignación de Teclas y Notificación en Actionbar

* **Tecla por Defecto**: `GLFW_KEY_BACKSLASH` (`\`), evitando conflictos de teclado con mods populares de mapas o utilidades de inventario.
* **Resolución Dinámica de Tipo de Entrada**: Utiliza `ig_magnet$getKeyboardType()` para resolver de forma segura `InputConstants.Type.KEYBOARD` con respaldo a `KEYSYM` a través de versiones de Fabric Loader.
* **Respuesta Visual Inmediata**: La alternancia activa una notificación en la barra de acciones:
  - `chat.ig_magnet.enabled`: `"Item Magnet: Enabled"`
  - `chat.ig_magnet.disabled`: `"Item Magnet: Disabled"`

---

## 💾 Almacenamiento NBT y Serialización con Códecs de MC 26.2

Las preferencias de alternancia se guardan directamente en el archivo de guardado `.dat` del jugador mediante los canales `ValueOutput` y `ValueInput` de Minecraft 26.2:

```java
// Guardando en NBT del Jugador
@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
    output.putBoolean("ig_magnet_enabled", this.ig_magnet$enabled);
}

// Cargando desde NBT del Jugador
@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
    this.ig_magnet$enabled = input.getBooleanOr("ig_magnet_enabled", true);
}
```

---

## 🧬 Eventos de Ciclo de Vida de Fabric y Flujo de Muerte/Reaparición

Al morir en Minecraft, el juego crea una entidad `ServerPlayer` completamente nueva al reaparecer. El mod garantiza cero pérdida de estado:

1. **`ServerPlayerEvents.COPY_FROM`**: Copia la bandera booleana desde `oldPlayer` a `newPlayer` inmediatamente tras la clonación de la entidad.
2. **`ServerPlayerEvents.AFTER_RESPAWN`**: Envía automáticamente el paquete S2C `MagnetTogglePayload` al cliente una vez conectada la nueva entidad, manteniendo el HUD del cliente sincronizado.
3. **`ServerPlayConnectionEvents.JOIN`**: Sincroniza el estado NBT guardado con el cliente al unirse a un servidor dedicado o mundo LAN.

---

## 🔗 Documentación Relacionada de la Wiki
* [[Comandos Brigadier y Alternancias de Servidor|es_es-26.2-Commands]]
* [[Arquitectura e Implementaciones Mixin|es_es-26.2-Architecture-and-Mixins]]
* [[HUD y Diagnósticos|es_es-26.2-HUD-and-Diagnostics]]
* [[Volver al Portal de MC 26.2|es_es-26.2-Home]]
