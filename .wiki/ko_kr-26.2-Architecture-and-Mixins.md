# 🧩 아키텍처 및 Mixin 주입 대상 (MC 26.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 아키텍처 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **루트 패키지** | `net.instantgratification.magnet` |
| **Mixin 설정 파일** | `src/main/resources/magnet.mixins.json` |
| **Access Widener** | `src/main/resources/magnet.accesswidener` |
| **호환성 레벨** | `JAVA_25` |
| **총 Mixin 클래스 수** | `2` (`MixinEntity`, `PlayerMixin`) |

---

## 🌳 패키지 아키텍처 트리

```text
net.instantgratification.magnet/
├── IMagnetEntity.java                  # NoClip 및 자화 플래그를 위한 엔티티 인터페이스
├── IMagnetPlayer.java                  # 토글 상태 조회를 위한 플레이어 인터페이스
├── MagnetCommand.java                  # Brigadier 명령어 트리 (/magnet toggle, /magnet debug)
├── MagnetDebugLogger.java              # 디스크에 기록하는 스레드 안전 파일 로깅
├── MagnetManager.java                  # 엔티티 공간 스캔 및 실행 루프
├── MagnetMod.java                      # 서버/공용 모드 엔트리포인트 및 네트워크 수신기
├── MagnetModClient.java                # 클라이언트 모드 초기화, 단축키 및 오버레이 토스트
├── MagnetMovement.java                 # 궤적 벡터 수학, Lerp 속도 보간 및 파티클
├── MagnetTogglePayload.java            # 네트워크 패킷 레코드 및 StreamCodec 복합체
├── SecondaryVisionCheck.java           # 정밀 블록 레이캐스팅 (식물, 블록 엔티티, 유리)
├── config/
│   ├── MagnetConfig.java               # JSON 설정 저장소 및 POJO 필드
│   ├── ModMenuIntegration.java         # 리플렉션 안전 ModMenu API 연동
│   └── YaclScreenHelper.java           # YetAnotherConfigLib v3 GUI 빌더
├── mixin/
│   ├── MixinEntity.java                # Entity에 NoClip 및 중력 조건부 무효화 주입
│   └── PlayerMixin.java                # Player에 NBT 영속성 및 즉시 수집 주입
├── registry/
│   └── ModGameRules.java               # DynamicGameRuleManager 등록부
└── util/
    └── ModVersionGuard.java            # 런타임 Knot ClassLoader 유효성 검사기
```

---

## 📋 Mixin 주입 대상 상세 분석표

### 1. `net.instantgratification.magnet.mixin.MixinEntity`
`net.minecraft.world.entity.Entity`를 대상으로 하며 `IMagnetEntity`를 구현합니다.

| 주입 대상 메서드 | 주입 위치 (At Point) | 동작 및 동작 방식 |
| :--- | :--- | :--- |
| `ig_magnet$decrementNoClipTicks` | `@Inject(method = "tick", at = @At("HEAD"))` | NoClip 활성화 시 매 틱마다 `noClipTicks` 카운트다운을 1씩 차감합니다. |
| `ig_magnet$preventPushOut` | `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` | 서버에서 `noClipTicks > 0`일 때 바닐라 블록 탈출 튕겨냄 속도를 취소합니다. |
| `ig_magnet$forceNoClipOnMove` | `@Inject(method = "move", at = @At("HEAD"))` | `noClipTicks > 0`일 때 `originalNoPhysics`를 캐싱하고 `entity.noPhysics = true`를 강제합니다. |
| `ig_magnet$restoreNoPhysicsAfterMove` | `@Inject(method = "move", at = @At("RETURN"))` | 이동 완료 후 `entity.noPhysics = originalNoPhysics` 원본 값으로 복원합니다. |
| `ig_magnet$conditionalCancelGravity` | `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` | 아이템이 고체 블록 내부에 겹쳐 있을 때만 중력 가속도를 취소합니다. |

---

### 2. `net.instantgratification.magnet.mixin.PlayerMixin`
`net.minecraft.world.entity.player.Player`를 대상으로 하며 `IMagnetPlayer`를 구현합니다.

| 주입 대상 메서드 | 주입 위치 (At Point) | 동작 및 동작 방식 |
| :--- | :--- | :--- |
| `ig_magnet$addAdditionalSaveData` | `@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))` | `ValueOutput`을 통해 boolean `ig_magnet_enabled`를 플레이어 NBT에 직렬화합니다. |
| `ig_magnet$readAdditionalSaveData` | `@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))` | `ValueInput.getBooleanOr()`를 통해 플레이어 NBT에서 `ig_magnet_enabled`를 역직렬화합니다. |
| `ig_magnet$tick` | `@Inject(method = "tick", at = @At("HEAD"))` | 매 게임 틱마다 서버 측에서 `MagnetManager.tick(player)`를 호출합니다. |
| `ig_magnet$expandPickupArea` | `@ModifyVariable(method = "aiStep", at = @At("STORE"), ordinal = 0)` | `ig:magnet_instant` 가 true일 때 수집 바운딩 박스를 확장합니다 (`pickupArea.inflate(range)`). |

---

## 🛡️ Knot ClassLoader 런타임 가드 (`ModVersionGuard`)

호환되지 않거나 깨진 마인크래프트 버전에서 월드 세이브가 오작동하는 것을 방지하기 위한 검증 유틸리티입니다:

```java
public final class ModVersionGuard {
    public static void checkClass(String modName, String requiredClassName) {
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        // 모드 초기화가 계속되기 전에 대상 클래스의 존재 여부를 검증...
    }
}
```

`MagnetMod.onInitialize()` 에서 호출:
```java
ModVersionGuard.checkClass("Magnet", "net.minecraft.world.entity.item.ItemEntity");
```

---

## 🔗 관련 Wiki 문서
* [[진공 이동 및 위상 전이 물리|ko_kr-26.2-Vacuum-and-Phase-Shifting]]
* [[플레이어 토글, 상태 영속성 및 생명주기|ko_kr-26.2-Player-Toggle-and-Persistence]]
* [[API 및 애드온 통합|ko_kr-26.2-API-and-Addon-Integration]]
* [[MC 26.2 포털로 돌아가기|ko_kr-26.2-Home]]
