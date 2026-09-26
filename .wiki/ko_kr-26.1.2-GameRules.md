# ⚙️ GameRules 전체 참조 (MC 26.1.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 카테고리 정보 | 세부 정보 |
| :--- | :--- |
| **카테고리 ID** | `magnet:magnet_category` |
| **현지화 제목** | `Magnet, Let me get that!` (`gamerule.category.magnet.magnet_category`) |
| **등록 관리자** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **등록 클래스** | `net.instantgratification.magnet.registry.ModGameRules` |
| **등록된 총 규칙 수** | `15` 개 네임스페이스 규칙 |

---

## 📖 인게임 GameRules 관리

마인크래프트 26.1.2에서 **Magnet, Let me get that!** 의 모든 전역 메커니즘은 `magnet:magnet_category` 헤더 아래 등록된 네임스페이스 GameRules를 통해 관리됩니다.

```
/gamerule ig:magnet_enabled true
/gamerule ig:magnet_range 12
/gamerule ig:magnet_speed 80
/gamerule ig:magnet_instant false
```

---

## 📋 전체 GameRules 레퍼런스 표

| GameRule 식별자 | 타입 | 기본값 | 범위 | 현지화 표시 이름 | 설명 및 게임플레이 효과 |
| :--- | :---: | :---: | :---: | :--- | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | **Magnet Enabled** | 아이템 진공 흡인 시스템 전체를 전역적으로 켜거나 끄는 마스터 스위치입니다. |
| `ig:magnet_range` | Integer | `12` | `1..64` | **Magnet Range** | 플레이어가 떨어진 아이템을 끌어당기는 구형 블록 반경입니다. |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | **Magnet Noclip** | 위상 전이를 활성화하여 끌려오는 아이템이 고체 블록을 자유롭게 통과하게 합니다. |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | **Attract XP Orbs** | 드롭된 아이템과 함께 경험치 구슬도 끌어당길지 여부입니다. |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | **Magnet Particles** | 끌려오는 엔티티의 궤적을 따라 전기 스파크 파티클을 방출합니다. |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | **Particle Count** | 파티클 틱마다 활성 방출원에서 생성되는 스파크 파티클 수입니다. |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | **Max Particle Sources** | FPS 렉을 방지하기 위해 동시에 파티클을 방출할 수 있는 최대 엔티티 수입니다. |
| `ig:magnet_speed` | Integer | `80` | `1..1000` | **Item Speed** | 종단 속도 백분율입니다 ($80 = 0.8\text{ blocks/tick} = 16.0\text{ m/s}$). |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000` | **Item Acceleration** | 틱당 가속도 보간 계수 백분율입니다 ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_instant` | Boolean | `false` | `true/false` | **Instant Pickup** | true 설정 시 AABB 바운딩 박스를 확장하여 비행 시간 없이 0틱 만에 아이템을 즉시 인벤토리로 흡수합니다. |
| `ig:magnet_los_only` | Boolean | `true` | `true/false` | **Line of Sight Only** | 직접적인 시선 확보를 강제하여 뚫을 수 없는 벽 너머의 아이템 흡인을 방지합니다. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | `true/false` | **Keep Moving if Unseen** | 시선 내에서 자화된 아이템이 비행 도중 시야에서 벗어나더라도 당김 운동량을 유지합니다. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | `true/false` | **Blocked by Transparent** | true 설정 시 유리, 유리판, 철창 및 반투명 블록이 시선을 차단합니다. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | `true/false` | **Blocked by Flora** | true 설정 시 키 큰 잔디, 농작물, 꽃, 나뭇잎이 시선을 차단합니다. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | `true/false` | **Blocked by Block Entities** | true 설정 시 상자, 침대, 통, 셜커 상자 등이 시선을 차단합니다. |

---

## 🔗 관련 Wiki 문서
* [[진공 이동 및 위상 전이 물리|ko_kr-26.1.2-Vacuum-and-Phase-Shifting]]
* [[Cloth Config GUI 및 기본값|ko_kr-26.1.2-Configuration]]
* [[MC 26.1.2 포털로 돌아가기|ko_kr-26.1.2-Home]]
