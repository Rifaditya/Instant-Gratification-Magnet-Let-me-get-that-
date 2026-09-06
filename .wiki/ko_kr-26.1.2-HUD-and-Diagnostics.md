# 📊 HUD, 시각 효과 및 오버레이 (MC 26.1.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 시각 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **작업표시줄 API** | `client.gui.setOverlayMessage(Component, boolean)` |
| **파티클 종류** | `net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK` |
| **파티클 스로틀링 주기** | `(entity.tickCount + entity.getId()) % 4 == 0` |
| **파티클 방출원 상한** | `ig:magnet_max_particle_sources` (기본값: `5`) |

---

## 🖥️ 작업표시줄 오버레이 알림 피드백

플레이어가 `Ctrl+M` 단축키 조합을 사용하여 아이템 자석을 토글할 때마다, 클라이언트 GUI는 작업표시줄에 즉각적인 안내 알림을 렌더링합니다:

* **활성화 메시지**: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
* **비활성화 메시지**: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)

```java
// 마인크래프트 26.1.2 Gui.java 검증 완료
if (newState) {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
} else {
    client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
}
```

---

## ⚡ 전기 스파크 시각 효과 궤적

끌려오는 아이템과 경험치 구슬은 비행 궤적을 따라 스파크 파티클을 방출합니다:

```
[끌려오는 아이템 / 경험치]  --->  ✨  --->  ✨  --->  ✨  --->  [플레이어 눈 위치]
```

* **방출원 게이팅**: 동시에 최대 5개의 방출원까지만 파티클을 생성합니다 (`ig:magnet_max_particle_sources = 5`).
* **주기 제어**: 파티클은 4틱마다($5\text{ times/second}$) 생성됩니다.
* **밀도 제어**: `ig:magnet_particle_count = 1`.

---

## 🔗 관련 Wiki 문서
* [[플레이어 토글 및 상태 관리|ko_kr-26.1.2-Player-Toggle-and-Persistence]]
* [[경험치 구슬 흡인|ko_kr-26.1.2-Experience-Orb-Attraction]]
* [[MC 26.1.2 포털로 돌아가기|ko_kr-26.1.2-Home]]
