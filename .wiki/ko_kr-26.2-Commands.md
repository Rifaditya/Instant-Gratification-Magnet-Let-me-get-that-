# 💻 Brigadier 명령어 및 인게임 진단 (MC 26.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 기능 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **명령어 클래스** | `net.instantgratification.magnet.MagnetCommand` |
| **기본 명령어 리터럴** | `/magnet` 및 `/ig_magnet` (완벽히 동일한 별칭 미러) |
| **등록 콜백** | `CommandRegistrationCallback.EVENT` |
| **로그 출력 파일** | `logs/ig_magnet_debug.log` |
| **대상 권한 수준** | 모든 일반 플레이어 (`toggle`), OP 레벨 2 이상 (`debug`) |

---

## 📖 명령어 트리 구조

```
/magnet (또는 /ig_magnet)
  ├── toggle
  └── debug
        └── log
```

---

## 🛠️ 서브커맨드 상세 레퍼런스

### 1. `/magnet toggle` (또는 `/ig_magnet toggle`)
명령어를 실행한 플레이어의 개인 자석 활성화 상태를 켜거나 끕니다.

* **사용법**: `/magnet toggle`
* **실행 로직**: `((IMagnetPlayer) player).ig_magnet$toggleMagnet()` 호출.
* **출력 피드백**:
  - 활성화 시: `§aItem Magnet: Enabled` (`chat.ig_magnet.enabled`)
  - 비활성화 시: `§cItem Magnet: Disabled` (`chat.ig_magnet.disabled`)
* **활용 사례**: 클라이언트 모드를 설치하지 않고 바닐라 클라이언트로 접속한 플레이어나, 단축키 입력이 여의치 않은 환경에서 자석을 제어할 때 유용합니다.

---

### 2. `/magnet debug` (또는 `/ig_magnet debug`)
명령어를 실행한 플레이어 본인 및 주변 10블록 이내의 엔티티 상태를 즉시 스캔하여 종합 진단 정보를 출력합니다.

* **사용법**: `/magnet debug`
* **출력 정보 예시**:
  ```text
  §e--- Magnet Debug Info ---
  Player Name: Steve
  Player UUID: c0b59055-ec50-4653-979d-c3c92d6ca897
  Global Master Toggle: true
  Player Toggle State: true
  Range: 12
  Instant Pickup: false
  LOS Only: true
  Spectator: false
  Dead/Dying: false
  Debug File Logger: DISABLED (Toggle with /magnet debug log)
  Nearby Items (10 blocks): 3
  Nearest Item ID: 412
  PlayerVisionTracker.canSee: true
  Vanilla Raycast canSee: true
  §e-------------------------
  ```
* **활용 사례**: 특정 아이템이 왜 끌려오지 않는지(예: 시선 차단 장애물, 관전자/사망 상태, 또는 전역 GameRule 비활성화 여부) 원인을 즉각 분석할 수 있습니다.

---

### 3. `/magnet debug log` (또는 `/ig_magnet debug log`)
디스크로의 지속적인 상세 진단 파일 로깅을 켜거나 끕니다.

* **사용법**: `/magnet debug log`
* **실행 로직**: `MagnetDebugLogger.enabled = !MagnetDebugLogger.enabled` 플래그 전환.
* **동작**: 매 틱별 엔티티 처리, 패킷 수신, 리스폰 핸드셰이크, LOS 감지 실패 내역을 타임스탬프와 함께 `logs/ig_magnet_debug.log` 파일에 기록합니다.
* **로그 파일 기록 샘플**:
  ```log
  [2026-08-22 21:49:15.102] MagnetMod: Server received packet from Steve (c0b59055-...) enabled=true
  [2026-08-22 21:49:15.105] MagnetMovement: Entity 412 is now magnetized by Steve (c0b59055-...).
  ```

---

## 🔗 관련 Wiki 문서
* [[플레이어 토글, 상태 영속성 및 생명주기|ko_kr-26.2-Player-Toggle-and-Persistence]]
* [[HUD 및 진단 시스템|ko_kr-26.2-HUD-and-Diagnostics]]
* [[GameRules 전체 참조|ko_kr-26.2-GameRules]]
* [[MC 26.2 포털로 돌아가기|ko_kr-26.2-Home]]
