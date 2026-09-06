# 🎨 YACL 설정 GUI 및 ModMenu (MC 26.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 설정 정보 상자 | 세부 내용 |
| :--- | :--- |
| **설정 파일 경로** | `config/ig_magnet.json` |
| **GUI 라이브러리** | YetAnotherConfigLib v3 (`dev.isxander:yet-another-config-lib`) |
| **ModMenu 엔트리포인트** | `net.instantgratification.magnet.config.ModMenuIntegration` |
| **GUI 스크린 헬퍼** | `net.instantgratification.magnet.config.YaclScreenHelper` |
| **클래스로딩 안전성** | `GuiHelper.getOptionalFactory` 로 완전 격리 |

---

## 📖 설정 시스템 아키텍처

**Magnet, Let me get that!** 은 **YetAnotherConfigLib v3 (YACL)** 기반의 깔끔한 클라이언트 인게임 설정 GUI를 제공하며, **ModMenu** 화면을 통해 바로 열 수 있습니다.

전용 서버(Dedicated Server) 환경에서 클라이언트 전용 GUI 클래스를 불러오다 서버가 크래시되는 문제를 원천 방지하기 위해, GUI 팩토리는 **리플렉션 안전 격리 클래스로딩** 방식을 사용합니다:

```java
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
                "ig_magnet",
                "net.instantgratification.magnet.config.YaclScreenHelper",
                "createScreen"
        );
    }
}
```

---

## ⚠️ 설정 우선순위 주의 안내

> ⚠️ **중요 안내**:  
> ModMenu GUI 화면이나 `config/ig_magnet.json` 파일에서 수정한 설정은 **새로 생성되는 신규 월드의 기본값에만 영향**을 미칩니다.  
> 이미 플레이 중인 기존 월드의 설정을 변경하려면 `/gamerule` 명령어 또는 바닐라 게임 규칙 편집 화면을 통해 인게임 [[GameRules 참조|ko_kr-26.2-GameRules]] 값을 직접 변경해야 합니다.

---

## 🗂️ 설정 카테고리 및 세부 옵션 트리

```
YACL 설정 화면 ("Magnet, Let me get that! Configuration")
  ├── 일반 설정 (General Settings)
  │     ├── Magnet Enabled (기본값: true)
  │     ├── Magnet Range (기본값: 12, 범위: 1..64)
  │     ├── Instant Pickup (기본값: false)
  │     └── Magnet Noclip (기본값: true)
  ├── 속도 및 당김 메커니즘 (Speeds & Pull Heuristics)
  │     ├── Item Speed (기본값: 80%, 범위: 1..1000)
  │     └── Item Acceleration (기본값: 10%, 범위: 1..1000)
  ├── 시선 감지 (Line of Sight, LOS)
  │     ├── Line of Sight Only (기본값: true)
  │     ├── Keep Moving if Unseen (기본값: true)
  │     ├── Blocked by Transparent (기본값: false)
  │     ├── Blocked by Flora (기본값: false)
  │     └── Blocked by Block Entities (기본값: false)
  └── 시각 효과 및 성능 (Visuals & Performance)
        ├── Attract XP Orbs (기본값: true)
        ├── Magnet Particles (기본값: true)
        ├── Particle Count (기본값: 1, 범위: 0..100)
        └── Max Particle Sources (기본값: 5, 범위: 0..100)
```

---

## 📄 원시 JSON 구조 (`config/ig_magnet.json`)

```json
{
  "configVersion": 1,
  "enabled": true,
  "range": 12,
  "noClip": true,
  "affectsXp": true,
  "particles": true,
  "particleCount": 1,
  "maxParticleSources": 5,
  "speed": 80,
  "acceleration": 10,
  "instant": false,
  "losOnly": true,
  "keepMovingIfUnseen": true,
  "blockedByTransparent": false,
  "blockedByFlora": false,
  "blockedByBlockEntities": false
}
```

---

## 🔗 관련 Wiki 문서
* [[GameRules 전체 참조|ko_kr-26.2-GameRules]]
* [[개발 환경 설정 및 빌드|ko_kr-26.2-Developer-Setup-and-Building]]
* [[MC 26.2 포털로 돌아가기|ko_kr-26.2-Home]]
