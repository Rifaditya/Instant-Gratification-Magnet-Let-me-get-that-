# 💻 서버 명령어 및 바닐라 클라이언트 지원 (MC 26.1.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 기능 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **명령어 클래스** | `net.instantgratification.magnet.MagnetCommand` |
| **기본 명령어 리터럴** | `/magnet` 및 `/ig_magnet` (완벽히 동일한 별칭 미러) |
| **서브커맨드** | `toggle` |
| **네트워크 동기화** | `ServerPlayNetworking.send(player, new MagnetTogglePayload(newState))` |

---

## 📖 명령어 레퍼런스

### `/magnet toggle` (또는 `/ig_magnet toggle`)
명령어를 실행한 플레이어의 개인 아이템 자석 상태를 켜거나 끕니다.

* **명령어 구문**: `/magnet toggle`
* **실행 로직**:
  ```java
  private static int toggleMagnet(CommandSourceStack source) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      boolean newState = MagnetPlayerState.toggleMagnet(player);

      if (newState) {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.enabled"), false);
      } else {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.disabled"), false);
      }

      if (ServerPlayNetworking.canSend(player, MagnetTogglePayload.TYPE)) {
          ServerPlayNetworking.send(player, new MagnetTogglePayload(newState));
      }
      return 1;
  }
  ```
* **바닐라 클라이언트 호환성**: 클라이언트 모드를 설치하지 않고 Fabric 서버에 접속한 순정 바닐라 클라이언트 사용자도 명령어를 통해 자석 기능을 자유롭게 제어할 수 있습니다.

---

## 🔗 관련 Wiki 문서
* [[플레이어 토글 및 상태 관리|ko_kr-26.1.2-Player-Toggle-and-Persistence]]
* [[GameRules 전체 참조|ko_kr-26.1.2-GameRules]]
* [[MC 26.1.2 포털로 돌아가기|ko_kr-26.1.2-Home]]
