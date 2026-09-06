# 👁️ 시선 감지 및 장애물 메커니즘 (MC 26.1.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 기능 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **기본 시선 엔진** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **보조 정밀 시선 엔진** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **구형 시야각 (FOV)** | $360.0^\circ$ (완벽한 전방위 전방향 감지) |
| **접촉 거리 허용 오차** | $0.3\text{ m}$ 대상 접촉 임계값 |
| **메모리 유지 태그** | `ig$isMagnetized()` / `ig$setMagnetized()` |
| **운동량 보존 규칙** | `ig:magnet_keep_moving_if_unseen = true` |
| **컨텍스트 구현체** | `VisionContext` 정적 레코드 |

---

## 📖 이중 패스 시선 파이프라인 (Dual-Pass Vision)

마인크래프트 26.1.2에서 시선 감지는 힙 메모리 할당이 없는 고속 **이중 패스 시선 파이프라인**을 통해 평가됩니다:

```
                                +---------------------------+
                                |    대상 아이템 감지됨     |
                                +-------------+-------------+
                                              |
                                              v
                              +-------------------------------+
                              |    1차 패스 (360° LOS 확인)   |
                              |   PlayerVisionTracker.canSee  |
                              +---------------+---------------+
                                              |
                        +---------------------+---------------------+
                        |                                           |
                   [시야 확보]                                  [시야 차단]
                        |                                           |
                        v                                           v
      +-----------------------------------+               +--------------------+
      |       2차 패스 (정밀 필터링)      |               |     운동량 검사    |
      |     SecondaryVisionCheck.canSee   |               | keepMovingIfUnseen |
      +-----------------+-----------------+               +---------+----------+
                        |                                           |
            +-----------+-----------+                     +---------+---------+
            |                       |                     |                   |
        [통과]                   [차단]               [이미 자화됨]       [자화 안 됨]
            |                       |                     |                   |
            v                       v                     v                   v
    +---------------+       +---------------+     +---------------+   +---------------+
    | 아이템 당기기 &|      | 거부 / 중단   |     | 당김 유지     |   | 당김 거부     |
    | 자화 플래그 설정|     +---------------+     +---------------+   +---------------+
    +---------------+
```

---

## 🔍 패스 1: 기본 360° 구형 시선 감지 (LOS)

```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **전방향 시야각**: $360^\circ$ FOV 각도를 통해 플레이어가 시야를 돌리지 않아도 등 뒤, 머리 위, 발밑에 떨어진 아이템을 매끄럽게 끌어당깁니다.
* **서브 복셀 접촉 허용치**: $0.3\text{m}$ 여유 마진을 통해 아이템이 단단한 벽면 구석에 붙어 있을 때 잘못 감지 제외되는 현상을 방지합니다.

---

## 🌿 패스 2: 세부 블록 탐색 (`SecondaryVisionCheck`)

```java
private record VisionContext(
        Level level,
        boolean blockTransparent,
        boolean blockFlora,
        boolean blockEntities,
        CollisionContext collisionContext,
        Vec3 start,
        Vec3 end
) {}
```

* **식물 및 잎 (`ig:magnet_blocked_by_flora`)**: `BushBlock` 및 `LeavesBlock` 여부를 검사합니다.
* **블록 엔티티 (`ig:magnet_blocked_by_block_entities`)**: `state.hasBlockEntity()`(상자, 셜커 상자, 침대 등)를 확인합니다.
* **투명 블록 (`ig:magnet_blocked_by_transparent`)**: 유리, 유리판, 반블록 등에 대해 `state.getVisualShape().clip(...)` 충돌을 테스트합니다.

---

## 🚀 운동량 연속성 (`keepMovingIfUnseen`)

* 처음 시야에 들어와 감지되면 `((IMagnetEntity) entity).ig$setMagnetized()` 로 플래그를 지정합니다.
* `ig:magnet_keep_moving_if_unseen = true` 이면, 이전에 자화된 적이 있는 아이템은 장애물 뒤로 가려지더라도 지속적으로 플레이어를 향해 날아옵니다.

---

## ⚙️ 관련 설정 및 GameRules

| GameRule | 타입 | 기본값 | 설명 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | 아이템을 끌어당기기 위해 시선 감지를 요구합니다. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | 비행 도중 시야가 가려지더라도 자화된 아이템의 이동을 유지합니다. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | true 설정 시 유리 및 투명 블록이 시선을 차단합니다. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | true 설정 시 풀과 꽃이 시선을 차단합니다. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | true 설정 시 상자 및 블록 엔티티가 시선을 차단합니다. |

---

## 🔗 관련 Wiki 문서
* [[진공 이동 및 위상 전이 물리|ko_kr-26.1.2-Vacuum-and-Phase-Shifting]]
* [[GameRules 전체 참조|ko_kr-26.1.2-GameRules]]
* [[MC 26.1.2 포털로 돌아가기|ko_kr-26.1.2-Home]]
