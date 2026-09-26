# ✨ 경험치 구슬 흡인 (MC 26.1.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 기능 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **대상 엔티티 클래스** | `net.minecraft.world.entity.ExperienceOrb` |
| **관리 클래스** | `net.instantgratification.magnet.MagnetManager` |
| **활성화 GameRule** | `ig:magnet_affects_xp` (기본값: `true`) |
| **스캔 쿼리** | `player.level().getEntitiesOfClass(ExperienceOrb.class, area, Entity::isAlive)` |
| **파티클 종류** | `ParticleTypes.ELECTRIC_SPARK` |
| **파티클 방출원 상한** | `ig:magnet_max_particle_sources` (기본값: `5`) |

---

## 📖 경험치 진공 흡인 메커니즘

즉각적인 만족감(Instant Gratification) 디자인 철학에 따르면, 경험치 구슬을 남겨두고 떠나는 것은 쾌적한 게임플레이 흐름을 방해합니다. 마인크래프트 26.1.2에서 `ExperienceOrb` 엔티티는 드롭된 아이템과 완전히 동일한 속도 및 위상 전이 물리 메커니즘으로 끌어당겨집니다.

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

1. **위상 전이 (NoClip)**: 경험치 구슬이 고체 블록을 통과하여 벽 뒤나 천장 구석에 끼이지 않습니다.
2. **속도 보간 (Lerp)**: 설정된 속도 및 가속도 백분율을 기반으로 플레이어의 눈 위치를 향해 부드럽게 가속됩니다.
3. **시선 감지 게이팅**: `ig:magnet_los_only` 가 true일 때, 경험치 구슬 역시 플레이어의 시선 내에 있거나 기존에 자화된 운동량을 유지하고 있어야 합니다.

---

## 🛡️ 렉 방지 및 파티클 풀링

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

* **최대 파티클 방출원**: 틱당 최대 $N$개의 엔티티까지만 시각적 파티클 궤적을 방출합니다.
* **클라이언트 안전 폴백**: `MagnetMovement.java` 에서 레벨이 `ServerLevel` 이 아닌 경우 `level.addParticle(...)` 로 안전하게 폴백 처리됩니다.

---

## ⚙️ 관련 설정 및 GameRules

| GameRule | 타입 | 기본값 | 설명 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_affects_xp` | Boolean | `true` | 자석이 경험치 구슬을 끌어당길지 여부입니다. |
| `ig:magnet_particles` | Boolean | `true` | 파티클 효과 전체를 켜거나 끄는 마스터 스위치입니다. |
| `ig:magnet_particle_count` | Integer | `1` | 활성 엔티티당 틱마다 생성되는 스파크 파티클 수입니다. |
| `ig:magnet_max_particle_sources` | Integer | `5` | 동시에 파티클을 방출할 수 있는 최대 엔티티 수입니다. |

---

## 🔗 관련 Wiki 문서
* [[진공 이동 및 위상 전이 물리|ko_kr-26.1.2-Vacuum-and-Phase-Shifting]]
* [[GameRules 전체 참조|ko_kr-26.1.2-GameRules]]
* [[MC 26.1.2 포털로 돌아가기|ko_kr-26.1.2-Home]]
