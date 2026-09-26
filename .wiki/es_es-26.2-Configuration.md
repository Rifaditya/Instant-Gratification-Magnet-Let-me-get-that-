# 🎨 Interfaz de Configuración YACL y ModMenu (MC 26.2)

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

| Infobox de Configuración | Detalles |
| :--- | :--- |
| **Ruta del Archivo de Configuración** | `config/ig_magnet.json` |
| **Biblioteca de Interfaz Gráfica** | YetAnotherConfigLib v3 (`dev.isxander:yet-another-config-lib`) |
| **Punto de Entrada de ModMenu** | `net.instantgratification.magnet.config.ModMenuIntegration` |
| **Asistente de Pantalla GUI** | `net.instantgratification.magnet.config.YaclScreenHelper` |
| **Seguridad de Classloading** | Aislado mediante `GuiHelper.getOptionalFactory` |

---

## 📖 Arquitectura del Sistema de Configuración

**Magnet, Let me get that!** ofrece una interfaz gráfica de configuración opcional para el cliente impulsada por **YetAnotherConfigLib v3 (YACL)** y accesible directamente a través de **ModMenu**.

Para garantizar que los servidores dedicados nunca sufran bloqueos ni excepciones por cargar clases gráficas de cliente, la fábrica de interfaz se resuelve mediante **carga de clases aislada y segura frente a reflexión**:

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
                "ig_magnet",
                "net.instantgratification.magnet.config.YaclScreenHelper",
                "createScreen"
        );
    }
}
```

---

## ⚠️ Aviso de Precedencia de Configuración

> ⚠️ **Aviso Importante**:  
> Los ajustes modificados en la interfaz de ModMenu o en el archivo `config/ig_magnet.json` **únicamente establecen los valores base por defecto para NUEVOS mundos**.  
> Para alterar la configuración de un mundo activo existente, utiliza la [[Referencia de GameRules|es_es-26.2-GameRules]] en el juego mediante `/gamerule` o la pantalla nativa de edición de reglas de juego.

---

## 🗂️ Categorías y Opciones de Configuración

```
Pantalla de Configuración YACL ("Magnet, Let me get that! Configuration")
  ├── General Settings (Ajustes Generales)
  │     ├── Magnet Enabled (Por Defecto: true)
  │     ├── Magnet Range (Por Defecto: 12, Rango: 1..64)
  │     ├── Instant Pickup (Por Defecto: false)
  │     └── Magnet Noclip (Por Defecto: true)
  ├── Speeds & Pull Heuristics (Velocidades y Físicas)
  │     ├── Item Speed (Por Defecto: 80%, Rango: 1..1000)
  │     └── Item Acceleration (Por Defecto: 10%, Rango: 1..1000)
  ├── Line of Sight (Línea de Visión)
  │     ├── Line of Sight Only (Por Defecto: true)
  │     ├── Keep Moving if Unseen (Por Defecto: true)
  │     ├── Blocked by Transparent (Por Defecto: false)
  │     ├── Blocked by Flora (Por Defecto: false)
  │     └── Blocked by Block Entities (Por Defecto: false)
  └── Visuals & Performance (Efectos Visuales y Rendimiento)
        ├── Attract XP Orbs (Por Defecto: true)
        ├── Magnet Particles (Por Defecto: true)
        ├── Particle Count (Por Defecto: 1, Rango: 0..100)
        └── Max Particle Sources (Por Defecto: 5, Rango: 0..100)
```

---

## 📄 Estructura JSON Cruda (`config/ig_magnet.json`)

```json
{
  "configVersion": 1,
  "enabled": true,
  "range": 12,
  "noClip": true,
  "affectsXp": true,
  "particles": true,
  "particleCount": 1,
  "maxParticleSources": 5,
  "speed": 80,
  "acceleration": 10,
  "instant": false,
  "losOnly": true,
  "keepMovingIfUnseen": true,
  "blockedByTransparent": false,
  "blockedByFlora": false,
  "blockedByBlockEntities": false
}
```

---

## 🔗 Documentación Relacionada de la Wiki
* [[Referencia Completa de GameRules|es_es-26.2-GameRules]]
* [[Configuración y Compilación Loom|es_es-26.2-Developer-Setup-and-Building]]
* [[Volver al Portal de MC 26.2|es_es-26.2-Home]]
