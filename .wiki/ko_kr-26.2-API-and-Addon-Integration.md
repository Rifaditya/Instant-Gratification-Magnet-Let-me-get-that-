# 🔌 API 및 애드온 통합 (MC 26.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| API 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **플레이어 인터페이스** | `net.instantgratification.magnet.IMagnetPlayer` |
| **엔티티 인터페이스** | `net.instantgratification.magnet.IMagnetEntity` |
| **핵심 이동 파사드** | `net.instantgratification.magnet.MagnetMovement` |
| **GameRule API** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **시선 감지 API** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 타 모드 개발자를 위한 API 연동 개요

서드파티 모드, 서버 유틸리티 및 Instant Gratification 동반 애드온은 **Magnet, Let me get that!** 의 인터페이스를 직접 호출하여 플레이어 자석 상태를 조회하고, 프로그래밍 방식으로 당김을 트리거하거나, 장애물을 우회할 수 있습니다.

---

## 🧑‍💻 플레이어 상태 인터페이스 (`IMagnetPlayer`)

임의의 `Player` 또는 `ServerPlayer` 인스턴스를 `IMagnetPlayer` 로 캐스팅하여 자석 상태를 확인하거나 변경할 수 있습니다:

```java
package net.instantgratification.magnet;

public interface IMagnetPlayer {
    boolean ig_magnet$isMagnetEnabled();
    void ig_magnet$setMagnetEnabled(boolean enabled);
    boolean ig_magnet$toggleMagnet();
}
```

### 활용 예제:
```java
// 플레이어의 자석이 활성화되어 있는지 확인
if (((IMagnetPlayer) player).ig_magnet$isMagnetEnabled()) {
    // 커스텀 애드온 로직 수행...
}

// 프로그래밍 방식으로 자석 끄기 (예: 특정 의자에 앉아있거나 미니게임 진행 중일 때)
((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(false);
```

---

## 📦 엔티티 자화 인터페이스 (`IMagnetEntity`)

커스텀 몹 드롭, 투사체, 커스텀 경험치 구슬 등 임의의 `Entity` 인스턴스를 `IMagnetEntity` 로 캐스팅할 수 있습니다:

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig_magnet$setMagnetNoClip();
    boolean ig_magnet$isMagnetNoClip();
    void ig_magnet$setMagnetized();
    boolean ig_magnet$isMagnetized();
}
```

### 활용 예제:
```java
// 커스텀 엔티티에 일시적인 2틱 NoClip 위상 전이 권한 부여
((IMagnetEntity) customItem).ig_magnet$setMagnetNoClip();
```

---

## 🚀 정적 이동 파사드 (`MagnetMovement.pull`)

애드온 모드는 내장된 물리 및 시선 감지 엔진을 사용하여 엔티티를 플레이어 쪽으로 수동 흡인할 수 있습니다:

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

```java
// 대상 엔티티를 파티클 효과와 함께 플레이어 쪽으로 끌어당김
MagnetMovement.pull(specialDrop, player, true);
```

---

## 🔗 관련 Wiki 문서
* [[아키텍처 및 Mixin 주입 대상|ko_kr-26.2-Architecture-and-Mixins]]
* [[개발 환경 설정 및 빌드|ko_kr-26.2-Developer-Setup-and-Building]]
* [[MC 26.2 포털로 돌아가기|ko_kr-26.2-Home]]
