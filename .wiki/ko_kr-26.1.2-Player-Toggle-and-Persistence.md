# 🔄 플레이어 토글 및 상태 관리 (MC 26.1.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 기능 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **상태 저장 클래스** | `net.instantgratification.magnet.MagnetPlayerState` |
| **기본 클라이언트 단축키** | `Ctrl+M` (`GLFW.GLFW_KEY_M` + `isControlDown()`) |
| **단축키 카테고리** | `key.category.ig_magnet.magnet` (`Magnet, Let me get that!`) |
| **네트워크 페이로드** | `MagnetTogglePayload` (`ig_magnet:toggle`) |
| **작업표시줄 API** | `client.gui.setOverlayMessage(Component, boolean)` |

---

## 📖 상태 아키텍처 개요

마인크래프트 26.1.2에서 플레이어의 토글 설정은 스레드 안전 `ConcurrentHashMap` 을 사용하는 `MagnetPlayerState` 를 통해 활성 서버 세션 동안 메모리에 안전하게 관리됩니다:

```java
public class MagnetPlayerState {
    private static final Map<UUID, Boolean> playerStates = new ConcurrentHashMap<>();

    public static boolean isMagnetEnabled(Player player) {
        return playerStates.getOrDefault(player.getUUID(), true);
    }

    public static void setMagnetEnabled(Player player, boolean enabled) {
        playerStates.put(player.getUUID(), enabled);
    }

    public static boolean toggleMagnet(Player player) {
        boolean newState = !isMagnetEnabled(player);
        setMagnetEnabled(player, newState);
        return newState;
    }
}
```

---

## ⌨️ 클라이언트 단축키 (`Ctrl+M`)

* **기본 단축키 조합**: `Ctrl+M` (`GLFW_KEY_M` + `isControlDown()`)이며, macOS 사용자를 위해 Command 키(`GLFW_KEY_LEFT_SUPER`)도 크로스 플랫폼으로 지원합니다.
* **시각적 작업표시줄 알림**:
  - `chat.ig_magnet.enabled`: `"아이템 자석: 활성화됨"`
  - `chat.ig_magnet.disabled`: `"아이템 자석: 비활성화됨"`

---

## 📡 네트워크 동기화 프로토콜

```
[클라이언트]                                                       [서버]
플레이어가 Ctrl+M 입력
MagnetPlayerState.setMagnetEnabled(player, newState)
ClientPlayNetworking.send(new MagnetTogglePayload(newState))
                                               ------------------> 서버 수신기
                                                                   MagnetPlayerState.setMagnetEnabled(player, payload.enabled())
```

---

## 🔗 관련 Wiki 문서
* [[서버 명령어 및 토글|ko_kr-26.1.2-Commands]]
* [[아키텍처 및 Mixin|ko_kr-26.1.2-Architecture-and-Mixins]]
* [[MC 26.1.2 포털로 돌아가기|ko_kr-26.1.2-Home]]
