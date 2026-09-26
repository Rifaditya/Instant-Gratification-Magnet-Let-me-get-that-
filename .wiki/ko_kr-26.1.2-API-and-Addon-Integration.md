# 🔌 API 및 애드온 통합 (MC 26.1.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| API 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **플레이어 상태 관리자** | `net.instantgratification.magnet.MagnetPlayerState` |
| **엔티티 인터페이스** | `net.instantgratification.magnet.IMagnetEntity` |
| **핵심 이동 파사드** | `net.instantgratification.magnet.MagnetMovement` |
| **GameRule API** | `net.dasik.social.api.gamerule.DynamicGameRuleManager` |
| **시선 감지 API** | `net.dasik.social.api.vision.PlayerVisionTracker` |

---

## 📖 타 모드 개발자를 위한 API 연동 개요

서드파티 모드 및 Instant Gratification 동반 애드온은 마인크래프트 26.1.2에서 **Magnet, Let me get that!** 의 인터페이스를 직접 호출하여 연동할 수 있습니다.

---

## 🧑‍💻 플레이어 상태 관리 (`MagnetPlayerState`)

정적 헬퍼 메서드를 통해 플레이어의 자석 설정을 직접 조회하거나 수정할 수 있습니다:

```java
package net.instantgratification.magnet;

public class MagnetPlayerState {
    public static boolean isMagnetEnabled(Player player);
    public static void setMagnetEnabled(Player player, boolean enabled);
    public static boolean toggleMagnet(Player player);
}
```

### 활용 예제:
```java
// 플레이어의 자석이 활성화되어 있는지 확인
if (MagnetPlayerState.isMagnetEnabled(player)) {
    // 커스텀 로직 수행...
}

// 프로그래밍 방식으로 자석 끄기
MagnetPlayerState.setMagnetEnabled(player, false);
```

---

## 📦 엔티티 자화 인터페이스 (`IMagnetEntity`)

임의의 `Entity` 인스턴스를 `IMagnetEntity` 로 캐스팅하여 위상 전이 또는 자화 플래그를 조작할 수 있습니다:

```java
package net.instantgratification.magnet;

public interface IMagnetEntity {
    void ig$setMagnetNoClip();
    boolean ig$isMagnetNoClip();
    void ig$setMagnetized();
    boolean ig$isMagnetized();
}
```

---

## 🚀 정적 이동 파사드 (`MagnetMovement.pull`)

엔티티를 플레이어 쪽으로 프로그래밍 방식으로 끌어당길 수 있습니다:

```java
public static void pull(Entity entity, Player player, boolean shouldSpawnParticles);
```

---

## 🔗 관련 Wiki 문서
* [[아키텍처 및 Mixin 주입 대상|ko_kr-26.1.2-Architecture-and-Mixins]]
* [[개발 환경 설정 및 빌드|ko_kr-26.1.2-Developer-Setup-and-Building]]
* [[MC 26.1.2 포털로 돌아가기|ko_kr-26.1.2-Home]]
