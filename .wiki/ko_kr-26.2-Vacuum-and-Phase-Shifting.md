# 🧲 진공 이동 및 위상 전이 (MC 26.2)

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

**Magnet, Let me get that!** 의 핵심 진공 흡인 메커니즘은 매 서버 틱마다 플레이어의 설정된 반경 내에 존재하는 바닥에 떨어진 `ItemEntity` 인스턴스를 스캔하고, 부드러운 비선형 보간(Interpolation)을 사용하여 플레이어의 눈높이로 끌어당깁니다.

아이템이 조약돌 턱, 나뭇가지, 광맥 틈새에 끼여 멈추는 것을 방지하기 위해 모드는 **위상 전이 (NoClip)** 기능을 활성화하여 비행 중인 아이템이 고체 블록 복셀을 무해하게 통과하도록 만듭니다.

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

아이템을 끌어당길 때 궤적은 3차원 유클리드 공간에서 직접 계산됩니다:

### 1. 목표 벡터 (Vector to Target)
아이템의 현재 위치를 $\vec{p}_{\text{entity}} = (x_e, y_e, z_e)$, 플레이어의 눈 위치를 $\vec{p}_{\text{eye}} = (x_p, y_p + h_{\text{eye}}, z_p)$라고 할 때:
$$\vec{v}_{\text{target}} = \vec{p}_{\text{eye}} - \vec{p}_{\text{entity}}$$
$$\hat{d} = \frac{\vec{v}_{\text{target}}}{\|\vec{v}_{\text{target}}\|} = \frac{\vec{v}_{\text{target}}}{\sqrt{\Delta x^2 + \Delta y^2 + \Delta z^2}}$$

### 2. 목표 종단 속도 (Desired Terminal Velocity)
목표 속도 벡터는 단위 방향 벡터 $\hat{d}$에 설정된 속도 파라미터를 곱하여 스케일링합니다:
$$\text{속도 스칼라 } s = \frac{\text{GameRule}(\text{ig:magnet\_speed})}{100.0}$$
$$\vec{u}_{\text{target}} = \hat{d} \times s$$
*기본 설정값($80\%$)에서 $s = 0.8\text{ blocks/tick}$입니다. $20\text{ ticks/s}$ 기준 종단 속도는 $16.0\text{ m/s}$에 달합니다.*

### 3. 비선형 가속도 보간 (Lerp)
가속도 계수 $a$를 기반으로 선형 보간(`Vec3.lerp`)을 수행하여 속도를 업데이트합니다:
$$\text{가속도 계수 } a = \frac{\text{GameRule}(\text{ig:magnet\_acceleration})}{100.0}$$
$$\vec{v}_{\text{new}} = \vec{v}_{\text{current}} + (\vec{u}_{\text{target}} - \vec{v}_{\text{current}}) \times a$$

### 4. 지면 마찰 방지 부스트 (Ground Anti-Friction Boost)
아이템이 블록 표면에 안착해 있는 경우(`entity.onGround() == true`), 바닥 마찰력을 즉시 제거하여 지면 끌림 현상을 방지합니다:
```java
if (entity.onGround()) {
    entity.setOnGround(false);
    entity.setPos(entity.position().add(0, 0.05, 0));
}
```

---

## 🧱 위상 전이 (NoClip 엔진)

`ig:magnet_noclip` 게임 규칙이 활성화되어 있으면, 아이템에 2틱의 NoClip 유지 윈도우가 부여됩니다:

1. **상태 활성화**: `((IMagnetEntity) entity).ig_magnet$setMagnetNoClip()` 호출로 `noClipTicks = 2` 설정.
2. **이동 인터셉트 (`MixinEntity.java`)**:
   - `move(MoverType, Vec3)`: `@Inject(at = @At("HEAD"))` 에서 `originalNoPhysics` 값을 저장하고 `entity.noPhysics = true` 강제 적용.
   - `move(MoverType, Vec3)`: `@Inject(at = @At("RETURN"))` 에서 `entity.noPhysics = originalNoPhysics` 원복 복원.
3. **벽 튕겨냄 방지**: `@Inject(method = "moveTowardsClosestSpace", at = @At("HEAD"), cancellable = true)` 주입으로 바닐라 블록 탈출 로직이 아이템을 강제로 밀어내는 현상을 차단.
4. **조건부 중력 무효화**: `@Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)` 주입으로 아이템이 블록 복셀 내부와 겹쳐 있을 때만(`!level.noCollision(...)`) 아래로 작용하는 중력을 취소하여 개방된 공중에서의 자연스러운 포물선 궤적을 보존.

---

## ⚙️ 관련 설정 및 GameRules

| GameRule | 타입 | 기본값 | 단위 / 범위 | 설명 |
| :--- | :---: | :---: | :---: | :--- |
| `ig:magnet_enabled` | Boolean | `true` | `true/false` | 모든 자석 진공 로직을 제어하는 마스터 스위치입니다. |
| `ig:magnet_range` | Integer | `12` | `1..64` 블록 | 구형 진공 흡인의 최대 유효 반경입니다. |
| `ig:magnet_speed` | Integer | `80` | `1..1000%` | 종단 속도 백분율입니다 ($80 = 0.8\text{ b/t}$). |
| `ig:magnet_acceleration` | Integer | `10` | `1..1000%` | 당김 가속도 백분율입니다 ($10 = 10\%\text{ lerp/tick}$). |
| `ig:magnet_noclip` | Boolean | `true` | `true/false` | 흡인 도중 블록 위상 전이(벽 통과)를 허용합니다. |

---

## 🔗 관련 Wiki 문서
* [[시선 감지 및 장애물 관통|ko_kr-26.2-Line-of-Sight-and-Obstruction]]
* [[즉시 수집 모드|ko_kr-26.2-Instant-Pickup-Mode]]
* [[아키텍처 및 Mixin 구현|ko_kr-26.2-Architecture-and-Mixins]]
* [[MC 26.2 포털로 돌아가기|ko_kr-26.2-Home]]
