# 🧲 진공 이동 및 위상 전이 (MC 26.1.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 기능 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **시스템 클래스** | `net.instantgratification.magnet.MagnetMovement` |
| **트리거 이벤트** | 서버 플레이어 틱 (`PlayerMixin` $\rightarrow$ `MagnetManager.tick`) |
| **기본 끌어당김 반경** | `12` 블록 (`ig:magnet_range`) |
| **기본 종단 속도** | `80%` ($0.8\text{ blocks/tick} = 16.0\text{ m/s}$) |
| **기본 가속도** | `10%` ($0.10\text{ lerp factor/tick}$) |
| **위상 전이 (NoClip)** | 활성화 (`ig:magnet_noclip = true`) |
| **목표 벡터** | 플레이어 눈 위치 (`player.getEyePosition()`) |
| **지면 오프셋 부스트** | `entity.onGround()` 일 때 Y축으로 $+0.05\text{ m}$ 추가 |

---

## 📖 시스템 개요

마인크래프트 26.1.2에서 진공 엔진은 플레이어의 구형 반경 내에 존재하는 유효한 `ItemEntity` 인스턴스를 지속적으로 추적하고, Lerp 보간을 사용하여 플레이어의 눈높이로 직접 끌어당깁니다.

**위상 전이 (NoClip)** 가 활성화되어 있으면, 아이템이 단단한 벽과 블록 복셀을 부드럽게 통과하여 채굴이나 전투 도중 장애물 뒤에 갇히는 현상을 방지합니다.

```
+-------------+      시선 감지 통과        +----------------------+     Lerp 속도 벡터 적용       +------------------+
| 아이템 엔티티 | -------------------------> | NoClip 설정 (2틱)    | ------------------------------> | 플레이어 눈 위치  |
+-------------+                            +----------------------+                                 +------------------+
                                                       |
                                                       v
                                            [벽면 튕겨냄 방지]
                                            [블록 충돌 바이패스]
                                            [벽 내부 중력 취소]
```

---

## 🧮 물리 및 벡터 수학

### 1. 방향 단위 벡터
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|}$$

### 2. 속도 보간 (Lerp)
$$\text{속도 스칼라 } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0} = 0.8\text{ blocks/tick}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
$$\text{가속도 계수 } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0} = 0.10$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 3. 지면 마찰 방지 부스트
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 위상 전이 (NoClip 엔진)

1. **상태 활성화**: `((IMagnetEntity) entity).ig$setMagnetNoClip()` 호출로 2틱 카운트다운 활성화.
2. **물리 오버라이드 (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` 에서 `originalNoPhysics` 값을 캐싱하고 `entity.noPhysics = true` 적용.
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` 에서 `entity.noPhysics = originalNoPhysics` 복원.
3. **튕겨냄 방지**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` 주입으로 바닐라 벽 밀어내기 힘 차단.
4. **벽 내부 중력 취소**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` 주입으로 아이템이 블록 복셀 내부에 있을 때만 중력 취소.

---

## ⚙️ 관련 설정 및 GameRules

| GameRule | 타입 | 기본값 | 설명 |
| :--- | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | 진공 메커니즘을 제어하는 마스터 스위치입니다. |
| `ig:magnet_range` | Integer | `12` | 블록 단위 진공 반경 (1부터 64까지). |
| `ig:magnet_speed` | Integer | `80` | 종단 속도 백분율입니다 ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Integer | `10` | 가속도 계수 백분율입니다 ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Boolean | `true` | 흡인 도중 블록 위상 전이(벽 통과)를 허용합니다. |

---

## 🔗 관련 Wiki 문서
* [[시선 감지 및 장애물 관통|ko_kr-26.1.2-Line-of-Sight-and-Obstruction]]
* [[즉시 수집 모드|ko_kr-26.1.2-Instant-Pickup-Mode]]
* [[아키텍처 및 Mixin 구현|ko_kr-26.1.2-Architecture-and-Mixins]]
* [[MC 26.1.2 포털로 돌아가기|ko_kr-26.1.2-Home]]
