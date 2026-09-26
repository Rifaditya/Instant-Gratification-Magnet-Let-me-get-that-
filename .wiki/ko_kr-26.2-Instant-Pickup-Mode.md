# ⚡ 즉시 수집 모드 (MC 26.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 기능 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **시스템 클래스** | `net.instantgratification.magnet.mixin.PlayerMixin` |
| **활성화 GameRule** | `ig:magnet_instant` (기본값: `false`) |
| **반경 GameRule** | `ig:magnet_range` (기본값: `12`, 범위: `1..64`) |
| **주입 지점** | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` |
| **대상 변수** | 플레이어 아이템 수집 바운딩 박스 (`AABB pickupArea`) |
| **인벤토리 로직** | 100% 바닐라 `Player.touch(ItemEntity)` 파이프라인 활용 |

---

## 📖 즉시 수집(Instant Pickup) 개요

일반 진공 모드가 속도 보간을 통해 공중으로 아이템을 물리적으로 끌어당기는 반면, **즉시 수집 모드**는 아이템의 비행 이동 시간을 완전히 없앱니다. 활성화되면 반경 내에 떨어진 아이템은 나타나는 그 순간 즉시 플레이어의 인벤토리로 흡수됩니다.

버그와 크래시 위험이 높은 커스텀 인벤토리 주입 코드를 작성하는 대신, **Magnet, Let me get that!** 은 바닐라의 `aiStep()` 메서드 내부에서 플레이어의 기본 수집 바운딩 박스를 비파괴적으로 확장하는 방식으로 즉시 수집을 구현합니다.

```
+-----------------------------------------------------------------------------------+
|                           바닐라 플레이어 aiStep() 틱 처리                         |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
                +---------------------------------------------------+
                |   PlayerMixin.ig_magnet$expandPickupArea()        |
                |   pickupArea = pickupArea.inflate(range);         |
                +---------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|              바닐라 ItemEntity.playerTouch(Player) 파이프라인 실행                 |
|   - 네이티브 인벤토리 스태킹 및 부분 수집 완벽 지원                               |
|   - 바닐라 아이템 획득 애니메이션 및 사운드 (item.pickup / entity.experience_orb)|
|   - 네이티브 통계 및 발전 과제 자동 달성                                          |
|   - 인벤토리 가득 참 방지 및 남은 아이템 안전 보존                               |
+-----------------------------------------------------------------------------------+
```

---

## 🧩 아키텍처 구현 세부사항 (`PlayerMixin.java`)

```java
@ModifyVariable(
        method = "aiStep",
        at = @At(value = "STORE"),
        ordinal = 0
)
private AABB ig_magnet$expandPickupArea(AABB pickupArea) {
    Player player = (Player) (Object) this;
    if (!this.ig_magnet$isMagnetEnabled() || player.isDeadOrDying() || player.isSpectator()) {
        return pickupArea;
    }

    Level level = player.level();
    if (!level.isClientSide()) {
        if (ModGameRules.getBoolean(level, ModGameRules.MAGNET_ENABLED) && ModGameRules.getBoolean(level, ModGameRules.MAGNET_INSTANT)) {
            int range = ModGameRules.getInt(level, ModGameRules.MAGNET_RANGE);
            if (range > 0) {
                return pickupArea.inflate(range);
            }
        }
    }
    return pickupArea;
}
```

### 핵심 엔지니어링 보장 사항:
1. **안전 가드**: 플레이어가 사망 중이거나(`player.isDeadOrDying()`), 관전자 모드이거나(`player.isSpectator()`), 개인 자석을 끈 경우(`!isMagnetEnabled()`) 바운딩 박스를 절대 확장하지 않습니다.
2. **서버 측 권한**: 수집 로직은 논리적 서버(`!level.isClientSide()`)에서 엄격히 실행되어 아이템 고스트 현상이나 인벤토리 불일치를 원천 차단합니다.
3. **이중 이동 충돌 방지**: `ig:magnet_instant` 가 true이면 `MagnetMovement.pull()` 이 자동으로 속도 업데이트를 중단하므로 물리적 비행과 즉시 수집이 제어권을 두고 경쟁하지 않습니다.

---

## ⚙️ 관련 설정 및 GameRules

| GameRule | 타입 | 기본값 | 설명 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_instant` | Boolean | `false` | 비행 이동 대신 인벤토리로 아이템을 즉시 순간이동하여 수집합니다. |
| `ig:magnet_range` | Integer | `12` | 수집 AABB 영역을 확장할 블록 반경입니다. |
| `ig:magnet_enabled` | Boolean | `true` | 모드 전체의 글로벌 마스터 스위치입니다. |

---

## 🔗 관련 Wiki 문서
* [[진공 이동 및 위상 전이 물리|ko_kr-26.2-Vacuum-and-Phase-Shifting]]
* [[플레이어 토글 및 상태 영속성|ko_kr-26.2-Player-Toggle-and-Persistence]]
* [[아키텍처 및 Mixin|ko_kr-26.2-Architecture-and-Mixins]]
* [[MC 26.2 포털로 돌아가기|ko_kr-26.2-Home]]
