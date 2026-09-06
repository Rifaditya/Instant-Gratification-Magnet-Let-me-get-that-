# 🧲 Magnet, Let me get that! — Wiki Oficial

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

Bienvenido a la wiki técnica y de jugabilidad oficial de **Magnet, Let me get that!** (`ig_magnet`), un mod de aspiración intrínseca de objetos y experiencia diseñado para el Minecraft moderno en Fabric.

Construido firmemente sobre la filosofía de diseño **Instant Gratification (IG)** (Gratificación Instantánea), este mod elimina el «paseo de la vergüenza»: la tediosa fricción de caminar 5 bloques para recoger un objeto que acabas de minar o una criatura que acabas de derrotar. Si puedes verlo, deberías tenerlo.

---

## 🧭 Portal Central Multi-Versión

Elige tu versión de Minecraft de destino para acceder a guías de jugabilidad dedicadas y aisladas, documentación técnica, tablas de GameRules y referencias de arquitectura:

| Versión de Minecraft | Estado de Lanzamiento | Compilación Activa | Motor de Configuración | Enlace al Portal |
| :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | 🟢 Moderno Activo | `1.3.9+26.2` | YACL v3 + ModMenu | [[26.2-Home|es_es-26.2-Home]] |
| **Minecraft 26.1.2** | 🟢 Anclaje Moderno | `1.1.2+26.1.2` | Cloth Config + ModMenu | [[26.1.2-Home|es_es-26.1.2-Home]] |

### 🚀 Portales Directos por Versión:
* 📦 **Minecraft 26.2**: [[👉 Entrar al Portal de Documentación de Minecraft 26.2|es_es-26.2-Home]]
* 📦 **Minecraft 26.1.2**: [[👉 Entrar al Portal de Documentación de Minecraft 26.1.2|es_es-26.1.2-Home]]

Para un desglose en profundidad de cadenas de herramientas, matrices de dependencias, ubicaciones de archivo y retrocompatibilidad, consulta [[Matriz de Compatibilidad de Versiones|es_es-Version-Compatibility]].

---

## ⚡ Matriz de Características Principales

```
                      +-----------------------------+
                      |   EMISOR DE VACÍO JUGADOR   |
                      +--------------+--------------+
                                     |
              +----------------------+----------------------+
              |                                             |
              v                                             v
  +-----------------------+                     +-----------------------+
  | MODO DE TIRO ESTÁNDAR |                     |  RECOGIDA INSTANTÁNEA |
  +-----------+-----------+                     +-----------+-----------+
              |                                             |
     [Línea de Visión (LOS)]                       [Inflado Caja AABB]
     [Raycast Esférico 360°]                       [Cero Latencia Vuelo]
     [NoClip Traspaso Fases]                       [Inventario Directo]
     [Velocidad Lerp Dinám.]                                |
              |                                             |
              +----------------------+----------------------+
                                     |
                                     v
                      +-----------------------------+
                      | OBJETO / ORBE XP CAPTURADO  |
                      +-----------------------------+
```

* **Aspiración Inteligente en 360°**: Atrae objetos caídos y orbes de experiencia dentro de un radio de bloques configurable (predeterminado: 12 bloques, hasta 64).
* **Cambio de Fase (NoClip)**: Los objetos magnetizados atraviesan limpiamente paredes sólidas, evitando que las recompensas se queden atascadas permanentemente en restos de explosiones o grietas de excavación.
* **Detección de Línea de Visión (LOS)**: Cuenta con un trazado de rayos esférico de 360° primario mediante `PlayerVisionTracker` de DasikLibrary y filtrado granular opcional contra bloques transparentes (cristal), flora (hierba alta, hojas) y entidades de bloque (cofres).
* **Continuidad de Inercia (`keepMovingIfUnseen`)**: Una vez magnetizados en la línea de visión, los objetos retienen su inercia de atracción incluso si giran temporalmente detrás de algún obstáculo.
* **Opción de Recogida Instantánea**: Expande la caja delimitadora de recolección nativa del jugador para absorber objetos al instante con cero latencia de vuelo.
* **Control por Tecla y Comandos**: Alterna el magnetismo en el cliente mediante tecla (`\` en 26.2, `Ctrl+M` en 26.1.2) o en el servidor mediante `/magnet toggle`.
* **Cero Sobrecarga de Inventario**: Funcionalidad 100% intrínseca: no requiere imanes como ítems en el inventario, amuletos ni baterías de energía.

---

## 📚 Navegación Enciclopédica

### 🎮 Guías de Jugador y Administrador
* [[Visión General 26.2|es_es-26.2-Home]] y [[Visión General 26.1.2|es_es-26.1.2-Home]]
* [[Vacío y Cambio de Fase 26.2|es_es-26.2-Vacuum-and-Phase-Shifting]] y [[Vacío y Cambio de Fase 26.1.2|es_es-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Línea de Visión y Obstáculos 26.2|es_es-26.2-Line-of-Sight-and-Obstruction]] y [[Línea de Visión y Obstáculos 26.1.2|es_es-26.1.2-Line-of-Sight-and-Obstruction]]
* [[Atracción de Orbes de Experiencia 26.2|es_es-26.2-Experience-Orb-Attraction]] y [[Atracción de Orbes de Experiencia 26.1.2|es_es-26.1.2-Experience-Orb-Attraction]]
* [[Modo de Recogida Instantánea 26.2|es_es-26.2-Instant-Pickup-Mode]] y [[Modo de Recogida Instantánea 26.1.2|es_es-26.1.2-Instant-Pickup-Mode]]
* [[Alternancia y Persistencia del Jugador 26.2|es_es-26.2-Player-Toggle-and-Persistence]] y [[Alternancia y Estado de Sesión 26.1.2|es_es-26.1.2-Player-Toggle-and-Persistence]]
* [[Referencia de GameRules 26.2|es_es-26.2-GameRules]] y [[Referencia de GameRules 26.1.2|es_es-26.1.2-GameRules]]
* [[Comandos Brigadier 26.2|es_es-26.2-Commands]] y [[Comandos de Servidor 26.1.2|es_es-26.1.2-Commands]]
* [[Progresos 26.2|es_es-26.2-Advancements]] y [[Progresos 26.1.2|es_es-26.1.2-Advancements]]
* [[Interfaz de Configuración YACL 26.2|es_es-26.2-Configuration]] y [[Interfaz Cloth Config 26.1.2|es_es-26.1.2-Configuration]]
* [[HUD y Diagnósticos 26.2|es_es-26.2-HUD-and-Diagnostics]] y [[HUD y Superposición 26.1.2|es_es-26.1.2-HUD-and-Diagnostics]]

### 💻 Documentación para Desarrolladores y Colaboradores
* [[Configuración y Compilación Loom 26.2|es_es-26.2-Developer-Setup-and-Building]] y [[Configuración y Compilación Loom 26.1.2|es_es-26.1.2-Developer-Setup-and-Building]]
* [[Arquitectura y Mixins 26.2|es_es-26.2-Architecture-and-Mixins]] y [[Arquitectura y Mixins 26.1.2|es_es-26.1.2-Architecture-and-Mixins]]
* [[API e Integración de Addons 26.2|es_es-26.2-API-and-Addon-Integration]] y [[API e Integración de Addons 26.1.2|es_es-26.1.2-API-and-Addon-Integration]]
* [[Matriz de Compatibilidad de Versiones Multi-Era|es_es-Version-Compatibility]]

---

## ⚖️ Licencia y Atribución

Desarrollado por **Dasik (Rifaditya)** bajo la **Licencia Pública General de GNU v3.0 (GNU GPLv3)**. Consulta el archivo `LICENSE` para conocer los términos completos y los permisos legales.
