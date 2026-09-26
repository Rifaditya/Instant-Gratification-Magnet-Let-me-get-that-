# 📊 HUD, 시각 효과 및 진단 시스템 (MC 26.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 시각 및 진단 정보 | 세부 파라미터 |
| :--- | :--- |
| **작업표시줄 HUD API** | `client.gui.hud.setOverlayMessage(Component, boolean)` |
| **파티클 종류** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **파티클 스로틀링 주기** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **디버그 로그 파일** | `logs/ig_magnet_debug.log` |
| **로거 클래스** | `net.instantgratification.magnet.MagnetDebugLogger` |

---

## 🖥️ 작업표시줄 오버레이 알림 피드백

플레이어가 단축키(`\`) 또는 서버 명령어(`/magnet toggle`)로 자석 기능을 켜거나 끌 때마다, 클라이언트 HUD는 핫바 바로 위에 깔끔하고 직관적인 오버레이 토스트 메시지를 표시합니다:

* **활성화 메시지**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **비활성화 메시지**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// 마인크래프트 26.2+ Hud.java 검증 완료
if (newState) {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ 전기 스파크 시각 효과 궤적

아이템과 경험치 구슬이 자석에 이끌려 비행하는 동안 은은한 `ParticleTypes.ELECTRIC_SPARK` 파티클 꼬리가 생성됩니다:

```
[끌려오는 아이템]  --->  ✨  --->  ✨  --->  ✨  --->  [플레이어 눈 위치]
```

### 파티클 방출 최적화 규칙:
1. **방출원 상한**: `ig:magnet_max_particle_sources` (기본값: `5`)로 제어되어, 대규모 채굴 시 수많은 아이템이 쏟아져도 파티클 과부하가 발생하지 않습니다.
2. **주기적 스태거링**: 엔티티 고유 ID를 기반으로 4틱마다 1회만 파티클을 방출합니다: `(entity.tickCount + entity.getId()) % 4 == 0`.
3. **입자 밀도 조절**: 방출원당 생성되는 입자 수는 `ig:magnet_particle_count` (기본값: `1`)로 제어됩니다.

---

## 📝 전용 디버그 파일 로거 (`MagnetDebugLogger`)

서버 관리자나 모드팩 제작자가 시선 감지 경계나 네트워크 패킷을 정밀하게 진단할 수 있도록 스레드 안전 비동기 파일 로거를 내장하고 있습니다 (`logs/ig_magnet_debug.log`):

* **활성화 방법**: 인게임에서 `/magnet debug log` 명령어 실행.
* **포맷 형식**: `[yyyy-MM-dd HH:mm:ss.SSS] [Context] Message`
* **로그 파일 기록 예시**:
  ```text
  [2026-08-22 21:50:00.124] MagnetMod: Server received packet from Alex (e7b1a234-...) enabled=true
  [2026-08-22 21:50:00.130] MagnetMovement: Entity 592 is now magnetized by Alex (e7b1a234-...).
  [2026-08-22 21:50:01.450] PlayerMixin: Server tick for Alex (e7b1a234-...) isMagnetEnabled=true
  ```

---

## 🔗 관련 Wiki 문서
* [[Brigadier 명령어 및 인게임 진단|ko_kr-26.2-Commands]]
* [[경험치 구슬 흡인|ko_kr-26.2-Experience-Orb-Attraction]]
* [[MC 26.2 포털로 돌아가기|ko_kr-26.2-Home]]
