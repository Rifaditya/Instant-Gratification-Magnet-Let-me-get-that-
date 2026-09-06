# 🔄 플레이어 토글, 상태 영속성 및 생명주기 (MC 26.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 기능 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **인터페이스 브리지** | `net.instantgratification.magnet.IMagnetPlayer` |
| **Mixin 주입 대상** | `net.minecraft.world.entity.player.Player` (`PlayerMixin.java`) |
| **기본 클라이언트 단축키** | `\` (백슬래시) — `key.ig_magnet.toggle` |
| **단축키 카테고리** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **네트워크 페이로드** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **NBT 코덱 저장소** | `ValueOutput` / `ValueInput` 의 `"ig_magnet_enabled"` 태그 |
| **생명주기 이벤트** | `ServerPlayConnectionEvents.JOIN`, `ServerPlayerEvents.COPY_FROM`, `ServerPlayerEvents.AFTER_RESPAWN` |

---

## 📖 상태 아키텍처 개요

멀티플레이어 서버와 모드팩에서는 플레이어마다 선호하는 플레이 스타일이 다릅니다. 건축가는 장식 작업 중에 아이템이 끌려오는 것을 잠시 멈추고 싶어 할 수 있고, 광부는 최대 흡인력을 원할 수 있습니다.

**Magnet, Let me get that!** 은 월드 재접속, 사망, 리스폰, 차원 이동 전반에 걸쳐 100% 보존되는 **플레이어별 개별 토글 상태**를 제공합니다.

```
                                [클라이언트 동작]
                       플레이어가 토글 단축키('\') 입력
                                       |
                                       v
                               [로컬 상태 업데이트]
                      client.player -> isEnabled = !isEnabled
                      작업표시줄 오버레이: "아이템 자석: 활성화/비활성화"
                                       |
                                       v
                             [C2S 패킷 전송 실행]
                      ClientPlayNetworking.send(MagnetTogglePayload)
                                       |
                                       v
                                [서버 수신부 처리]
                      context.server().execute(() -> {
                          ((IMagnetPlayer) player).ig_magnet$setMagnetEnabled(val);
                      })
                                       |
          +----------------------------+----------------------------+
          |                                                         |
          v                                                         v
   [NBT 데이터 영구 저장]                               [생명주기 훅 연결]
  ValueOutput.putBoolean("ig_magnet_enabled")           - JOIN: S2C 동기화
  ValueInput.getBooleanOr("ig_magnet_enabled", true)    - COPY_FROM: 사망 시 유지
                                                        - AFTER_RESPAWN: 새 엔티티 S2C 동기화
```

---

## ⌨️ 클라이언트 단축키 및 작업표시줄 오버레이

* **기본 단축키**: `GLFW_KEY_BACKSLASH` (`\`)로 매핑되어 미니맵이나 인벤토리 유틸리티 모드와의 키 충돌을 방지합니다.
* **동적 키 타입 헬퍼**: `ig_magnet$getKeyboardType()` 을 사용하여 `InputConstants.Type.KEYBOARD` 를 안전하게 확인하고, 다양한 Fabric Loader 스냅샷에 걸쳐 `KEYSYM` 으로 유연하게 대응합니다.
* **즉각적인 시각 피드백**: 토글 시 화면 하단 핫바 위에 현지화된 오버레이 토스트가 출력됩니다:
  - `chat.ig_magnet.enabled`: `"아이템 자석: 활성화됨"`
  - `chat.ig_magnet.disabled`: `"아이템 자석: 비활성화됨"`

---

## 💾 NBT 저장 및 마인크래프트 26.2 코덱 직렬화

플레이어의 토글 상태는 마인크래프트 26.2의 `ValueOutput` 및 `ValueInput` 데이터 파이프라인을 통해 플레이어의 `.dat` 월드 세이브 파일에 직접 기록됩니다:

```java
// 플레이어 NBT에 저장
@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
    output.putBoolean("ig_magnet_enabled", this.ig_magnet$enabled);
}

// 플레이어 NBT에서 로드
@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
private void ig_magnet$readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
    this.ig_magnet$enabled = input.getBooleanOr("ig_magnet_enabled", true);
}
```

---

## 🧬 Fabric 생명주기 이벤트 및 사망/리스폰 흐름

마인크래프트에서 플레이어가 사망하면 리스폰 시 완전히 새로운 `ServerPlayer` 엔티티가 생성됩니다. 모드는 상태 유실을 완벽히 방지합니다:

1. **`ServerPlayerEvents.COPY_FROM`**: 엔티티 복제 즉시 `oldPlayer` 에서 `newPlayer` 로 boolean 토글 값을 복사합니다.
2. **`ServerPlayerEvents.AFTER_RESPAWN`**: 새 플레이어 엔티티가 접속을 완료하면 클라이언트로 S2C `MagnetTogglePayload` 를 즉시 전송하여 HUD 상태를 동기화합니다.
3. **`ServerPlayConnectionEvents.JOIN`**: 전용 서버나 LAN 월드에 접속할 때 플레이어의 저장된 NBT 상태를 클라이언트로 전송합니다.

---

## 🔗 관련 Wiki 문서
* [[Brigadier 명령어 및 서버 토글|ko_kr-26.2-Commands]]
* [[아키텍처 및 Mixin 구현|ko_kr-26.2-Architecture-and-Mixins]]
* [[HUD 및 진단|ko_kr-26.2-HUD-and-Diagnostics]]
* [[MC 26.2 포털로 돌아가기|ko_kr-26.2-Home]]
