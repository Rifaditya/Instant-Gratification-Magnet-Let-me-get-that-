# 👁️ 시선 감지 및 장애물 메커니즘 (MC 26.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 기능 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **기본 시선 엔진** | `net.dasik.social.api.vision.PlayerVisionTracker` |
| **보조 정밀 시선 엔진** | `net.instantgratification.magnet.SecondaryVisionCheck` |
| **구형 시야각 (FOV)** | $360.0^\circ$ (완벽한 전방위 전방향 감지) |
| **접촉 거리 허용 오차** | $0.3\text{ m}$ 대상 접촉 임계값 |
| **메모리 유지 태그** | `ig_magnet$isMagnetized()` / `ig_magnet$setMagnetized()` |
| **운동량 보존 규칙** | `ig:magnet_keep_moving_if_unseen = true` |
| **정밀 필터 규칙** | 투명 블록, 식물/잎, 블록 엔티티 |

---

## 📖 이중 패스 시선 파이프라인 (Dual-Pass Vision)

동굴 벽이나 잠긴 기지 너머로 부당하게 아이템을 획득하는 치팅을 방지하면서도 서버 렉 없는 고성능을 유지하기 위해, **Magnet, Let me get that!** 은 고속 **이중 패스 시선 파이프라인**을 사용합니다:

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

1차 패스는 DasikLibrary의 최적화된 레이캐스팅 엔진을 호출합니다:
```java
boolean canSee = net.dasik.social.api.vision.PlayerVisionTracker.canSee(player, entity, (double) range, 360.0);
```

* **전방향 시야각 (360° FOV)**: $360.0^\circ$ 시야 원뿔각을 통해 플레이어는 시선을 돌리지 않아도 뒤통수, 머리 위, 발밑에 떨어진 아이템을 완벽하게 끌어당길 수 있습니다 (단단한 벽에 막히지 않는 한).
* **0.3m 접촉 허용 오차**: 아이템이 블록 구석 틈새에 끼어 있을 때, $0.3	ext{m}$ 반경의 서브 복셀 레이캐스팅을 적용하여 억울하게 감지에서 탈락하는 현상을 방지합니다.

---

## 🌿 패스 2: 세부 블록 상태 필터링 (`SecondaryVisionCheck`)

1차 패스를 통과하면 모드는 `BlockGetter.traverseBlocks`를 사용하여 세부적인 장애물 규칙을 검사합니다:

```java
public static boolean canSee(Player player, Entity target, boolean blockTransparent, boolean blockFlora, boolean blockEntities)
```

1. **식물 및 잎 필터링 (`ig:magnet_blocked_by_flora`)**:
   - 레이캐스트 경로상의 블록이 `BushBlock` (키 큰 잔디, 꽃, 농작물, 묘목) 또는 `LeavesBlock` (나뭇잎)인지 확인합니다.
   - 활성화(`true`) 시 울창한 나뭇잎과 풀숲이 아이템 흡인을 차단하는 불투명한 장벽으로 작동합니다.
2. **인터랙티브 블록 엔티티 (`ig:magnet_blocked_by_block_entities`)**:
   - 경로상의 블록에 `state.hasBlockEntity()`가 존재하는지 검사합니다.
   - 활성화(`true`) 시 상자, 덫 상자, 통, 셜커 상자, 침대, 발사기 등이 시선을 차단합니다.
3. **투명 및 불완전 블록 (`ig:magnet_blocked_by_transparent`)**:
   - `state.getVisualShape(...)`를 기준으로 레이캐스트합니다.
   - 활성화(`true`) 시 유리, 유리판, 철창, 울타리, 반블록, 계단 등이 아이템 수집을 차단합니다.

---

## 🚀 운동량 연속성 (`keepMovingIfUnseen`)

빠른 속도로 진행되는 채굴이나 전투 도중 코너를 돌면서 끌려오던 아이템이 일시적으로 시야에서 벗어나는 경우가 자주 발생합니다. 아이템이 공중에 멈추거나 용암에 빠지는 참사를 방지하기 위해:

1. **자화(Magnetization) 태그**: 시선 내에서 아이템이 처음 감지되면 `((IMagnetEntity) entity).ig_magnet$setMagnetized()`를 통해 메모리 플래그를 설정합니다.
2. **운동량 유지 로직**: 후속 틱에서 시선이 차단되더라도:
   - `ig:magnet_keep_moving_if_unseen = true` 이고 `ig_magnet$isMagnetized() == true` 라면: 아이템은 멈추지 않고 플레이어를 향해 계속 날아옵니다.
   - `ig:magnet_keep_moving_if_unseen = false` 라면: 시야에서 사라지는 즉시 끌어당김이 중단됩니다.
   - 아이템을 **단 한 번도 본 적이 없는 경우** (예: 벽 너머 폭발이나 디스펜서로 생성된 드롭물): 즉시 당김이 거부됩니다.

---

## ⚙️ 관련 설정 및 GameRules

| GameRule | 타입 | 기본값 | 설명 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_los_only` | Boolean | `true` | 아이템을 끌어당기기 위해 직접적인 시선 확보를 요구합니다. |
| `ig:magnet_keep_moving_if_unseen` | Boolean | `true` | 비행 도중 시야가 차단되더라도 자화된 아이템의 이동을 유지합니다. |
| `ig:magnet_blocked_by_transparent` | Boolean | `false` | true일 경우 유리 및 투명 블록이 시선을 차단합니다. |
| `ig:magnet_blocked_by_flora` | Boolean | `false` | true일 경우 잔디, 나뭇잎, 꽃 등이 시선을 차단합니다. |
| `ig:magnet_blocked_by_block_entities` | Boolean | `false` | true일 경우 상자, 침대, 블록 엔티티가 시선을 차단합니다. |

---

## 🔗 관련 Wiki 문서
* [[진공 이동 및 위상 전이 물리|ko_kr-26.2-Vacuum-and-Phase-Shifting]]
* [[GameRules 전체 참조|ko_kr-26.2-GameRules]]
* [[아키텍처 및 Mixin|ko_kr-26.2-Architecture-and-Mixins]]
* [[MC 26.2 포털로 돌아가기|ko_kr-26.2-Home]]
