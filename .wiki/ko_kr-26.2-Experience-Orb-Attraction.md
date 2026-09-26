# ✨ 경험치 구슬 흡인 (MC 26.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 기능 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **대상 엔티티 클래스** | `net.minecraft.world.entity.ExperienceOrb` |
| **관리 클래스** | `net.instantgratification.magnet.MagnetManager` |
| **활성화 GameRule** | `ig:magnet_affects_xp` (기본값: `true`) |
| **스캔 쿼리** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **파티클 종류** | `ParticleTypes.ELECTRIC_SPARK` |
| **파티클 방출 조절** | `ig:magnet_max_particle_sources` 로 아이템과 공유 제어 |

---

## 📖 경험치 진공 흡인 메커니즘

즉각적인 만족감(Instant Gratification) 디자인 철학에 따르면, 바닥에 흩어지거나 동굴 천장에 달라붙은 경험치 구슬을 주우러 다니는 것은 몰입감을 방해하는 불필요한 마찰입니다. **Magnet, Let me get that!** 은 드롭된 아이템뿐만 아니라 `ExperienceOrb` 엔티티에 대해서도 완벽한 1급 흡인 지원을 제공합니다.

```
+------------------+     getEntitiesOfClass     +-----------------------+     MagnetMovement.pull     +--------------------+
| 플레이어 스캔 영역 | -------------------------> | List<ExperienceOrb>   | --------------------------> | 플레이어 수집 완료 |
+------------------+                            +-----------------------+                             +--------------------+
                                                             |
                                                             v
                                                 [NoClip 위상 전이 적용]
                                                 [Lerp 속도 벡터 적용]
                                                 [스로틀링된 스파크 파티클]
```

---

## ⚡ 동기화된 물리 및 위상 전이

`ig:magnet_affects_xp` 가 활성화되어 있으면, 반경 내의 모든 경험치 구슬은 드롭된 아이템과 완전히 동일한 고급 물리 메커니즘을 상속받습니다:

1. **위상 전이 (NoClip)**: 경험치 구슬이 끌려올 때 고체 블록을 통과하므로, 벽에 부딪혀 무한히 튕기거나 제자리를 맴도는 현상이 발생하지 않습니다.
2. **동적 가속도**: 경험치 구슬 또한 설정된 속도($s = \text{speed}/100.0$) 및 가속도($a = \text{accel}/100.0$) Lerp 계산식을 정확히 따릅니다.
3. **시선 감지 필터링**: `ig:magnet_los_only` 가 true일 경우, 경험치 구슬 역시 360° 기본 레이캐스트 및 2차 정밀 블록 검사를 통과해야 합니다.

---

## 🛡️ 성능 최적화 및 렉 방지 (파티클 상한제)

대규모 몹 팜이나 엔더 드래곤 보스전에서는 수백 개의 경험치 구슬이 한꺼번에 생성될 수 있습니다. 매 틱마다 모든 구슬에서 파티클을 뿜어내면 클라이언트 FPS가 급락할 수 있습니다.

본 모드는 **글로벌 파티클 방출원 스로틀링(Global Particle Source Throttling)** 을 통해 파티클 렉을 완벽히 차단합니다:

```java
int maxParticleSources = ModGameRules.getInt(player.level(), ModGameRules.MAGNET_MAX_PARTICLE_SOURCES);
int particleSourceCount = 0;

for (ExperienceOrb orb : orbs) {
    boolean shouldSpawnParticles = false;
    if (particleSourceCount < maxParticleSources) {
        shouldSpawnParticles = true;
        particleSourceCount++;
    }
    MagnetMovement.pull(orb, player, shouldSpawnParticles);
}
```

* **글로벌 상한선**: 매 틱마다 최대 $N$개의 엔티티(`ig:magnet_max_particle_sources`, 기본값: `5`)까지만 스파크 파티클을 생성할 수 있습니다.
* **틱 분산 (Tick Staggering)**: 파티클은 `(entity.tickCount + entity.getId()) % 4 == 0` (4틱마다 = 초당 5회) 주기로만 방출됩니다.

---

## ⚙️ 관련 설정 및 GameRules

| GameRule | 타입 | 기본값 | 단위 / 범위 | 설명 |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | `true/false` | 경험치 구슬을 자석으로 끌어당길지 여부를 결정합니다. |
| `ig:magnet_particles` | Boolean | `true` | `true/false` | 전기 스파크 시각 효과 궤적을 켜거나 끕니다. |
| `ig:magnet_particle_count` | Integer | `1` | `0..100` | 활성 방출원당 틱마다 생성되는 스파크 파티클 수입니다. |
| `ig:magnet_max_particle_sources` | Integer | `5` | `0..100` | FPS 렉을 방지하기 위해 동시에 파티클을 방출할 수 있는 최대 엔티티 수입니다. |

---

## 🔗 관련 Wiki 문서
* [[진공 이동 및 위상 전이 물리|ko_kr-26.2-Vacuum-and-Phase-Shifting]]
* [[GameRules 전체 참조|ko_kr-26.2-GameRules]]
* [[HUD 및 시각적 진단|ko_kr-26.2-HUD-and-Diagnostics]]
* [[MC 26.2 포털로 돌아가기|ko_kr-26.2-Home]]
