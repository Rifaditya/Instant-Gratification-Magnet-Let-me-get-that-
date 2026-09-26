# ⚙️ GameRules 完整参考 (MC 26.2)

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

| 分类信息栏 | 详细参数 |
| :--- | :--- |
| **分类 ID** | `magnet:magnet_category` |
| **本地化分类名** | `Magnet, Let me get that!` (`gamerule.category.magnet.magnet_category`) |
| **动态注册管理器** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **注册表类** | `net.instantgratification.magnet.registry.ModGameRules` |
| **注册规则总数** | `15` 个带命名空间的游戏规则 |

---

## 📖 游戏内 GameRules 管理

**Magnet, Let me get that!** 的全部全局机制均由注册在 `magnet:magnet_category` 标题下的命名空间游戏规则（GameRules）统一管控。

管理员既可以在游戏内直接使用 `/gamerule <rule> <value>` 实时修改，也可以在创建或编辑世界时通过原版的“编辑游戏规则”图形界面直观配置：

```
/gamerule ig:magnet_enabled true
/gamerule ig:magnet_range 24
/gamerule ig:magnet_speed 120
/gamerule ig:magnet_instant true
```

---

## 📋 完整游戏规则参考表

| 游戏规则 Identifier | 类型 | 默认值 | 取值范围 | 本地化显示名称 | 规则描述与游戏机制效果 |
| :--- | :---: | :---: | :---: | :--- | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | **启用物品磁铁** | 全局启用或禁用物品吸附系统的主开关。 |
| `ig:magnet_range` | Integer | `12` | `1..64` | **磁铁吸引范围** | 玩家能够全方位吸引掉落物的球形方块半径。 |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | **磁铁穿相虚位移** | 允许被吸附的物品穿透实体方块飞行，避免被地形卡死。 |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | **吸附经验球** | 是否在吸附掉落物的同时吸附经验球。 |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | **磁铁轨迹粒子** | 是否在被牵引的实体轨迹上生成电火花粒子。 |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | **单源粒子数量** | 每个活跃发射源在每次粒子刻生成的火花粒子数量。 |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | **最大粒子发射源** | 允许同时生成粒子的最大实体上限，防止高密度掉落导致掉帧。 |
| `ig:magnet_speed` | Integer | `80` | `1..1000` | **物品移动速度** | 终端飞行速度百分比（$80 = 0.8\text{ 方块/tick} = 16.0\text{ m/s}$）。 |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000` | **物品吸引加速度** | 逐刻速度插值因子（$10 = 10\%\text{ lerp/tick}$）。 |
| `ig:magnet_instant` | Boolean | `false` | `true/false` | **瞬间拾取模式** | 为 true 时，通过扩展 AABB 包围盒实现零延迟瞬时拾取入包。 |
| `ig:magnet_los_only` | Boolean | `true` | `true/false` | **仅视线范围内有效** | 强制要求视线可见性；杜绝隔着实体掩体与实心墙壁吸物。 |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | `true/false` | **脱离视线保持移动** | 允许在视线内捕获的物品在绕入障碍后方时继续保持牵引动量。 |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | `true/false` | **透明方块阻隔视线** | 为 true 时，普通玻璃、玻璃板、铁栏杆及半砖楼梯等将阻隔视线。 |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | `true/false` | **植被树叶阻隔视线** | 为 true 时，草丛、花朵、树苗、农作物与树叶等将阻隔视线。 |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | `true/false` | **方块实体阻隔视线** | 为 true 时，箱子、木桶、潜影盒与床等方块实体将阻隔视线。 |

---

## ⚖️ GameRules 优先于全局 JSON 配置文件

> ⚠️ **服务器管理员重要须知**：  
> 客户端/服务端 JSON 配置文件（`config/ig_magnet.json`）仅用于定义**新建世界时的基线默认值**。  
> 已创建的活跃世界完全由存档内的 GameRules 独立控制。直接修改 `ig_magnet.json` 不会改变现有世界中的设置，必须通过 `/gamerule` 或原版游戏规则设置菜单进行修改。

---

## 🔗 相关 Wiki 文档
* [[吸引移动与穿相物理|zh_cn-26.2-Vacuum-and-Phase-Shifting]]
* [[视线检测与障碍穿透|zh_cn-26.2-Line-of-Sight-and-Obstruction]]
* [[配置界面与 JSON 同步|zh_cn-26.2-Configuration]]
* [[返回 MC 26.2 门户|zh_cn-26.2-Home]]
