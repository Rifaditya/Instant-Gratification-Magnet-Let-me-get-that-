# 🧲 Magnet, Let me get that! — 마인크래프트 26.2 포털

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

**Magnet, Let me get that!** (빌드 `1.3.9+26.2`)의 **마인크래프트 26.2** 문서 포털에 오신 것을 환영합니다.

이 최신 에디션은 영속적인 NBT 상태 유지, Fabric 생명주기 리스폰 동기화, DasikLibrary 1.8.23 기반의 360° 구형 레이캐스팅, YetAnotherConfigLib v3 (YACL) GUI 설정을 완벽하게 지원합니다.

---

## 📋 마인크래프트 26.2 빠른 사양 요약

| 규격 항목 | 목표값 | 참조 식별자 |
| :--- | :--- | :--- |
| **마인크래프트 대상 버전** | `26.2` | `"minecraft": ">=26.2-"` |
| **활성 하위 프로젝트 빌드** | `1.3.9+26.2` | `mod_version=1.3.9+26.2` |
| **Java 툴체인** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.19.1` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.150.1+26.2` | `fabric_version=0.150.1+26.2` |
| **공유 코어 라이브러리** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **기본 클라이언트 단축키** | `\` (백슬래시) | `GLFW.GLFW_KEY_BACKSLASH` |
| **설정 GUI** | YetAnotherConfigLib v3 | `YaclScreenHelper` + ModMenu |
| **진단 명령어** | `/magnet toggle`, `/magnet debug`, `/magnet debug log` | `MagnetCommand.java` |

---

## ⚡ 핵심 기능 하이라이트

* **360° 구형 시선 감지 (LOS) 확인**: 단단한 벽 너머의 아이템을 비정상적으로 끌어당기는 것을 방지하면서, 모든 시야 각도에서 완벽한 가시성 기반 흡인을 지원합니다. [[시선 감지 및 장애물 관통|ko_kr-26.2-Line-of-Sight-and-Obstruction]] 문서를 참조하세요.
* **위상 전이 NoClip 이동**: 자석에 끌려오는 아이템이 블록을 부드럽게 통과하여 플레이어 눈높이까지 걸림 없이 도달합니다. [[진공 이동 및 위상 전이 물리|ko_kr-26.2-Vacuum-and-Phase-Shifting]] 문서를 참조하세요.
* **지연 시간 0의 즉시 수집**: 선택적인 바운딩 박스 확장을 통해 떨어진 아이템을 물리적 비행 딜레이 없이 인벤토리로 즉시 흡수합니다. [[즉시 수집 모드|ko_kr-26.2-Instant-Pickup-Mode]] 문서를 참조하세요.
* **영속적 플레이어 상태**: NBT (`ValueOutput`/`ValueInput`) 및 `ServerPlayerEvents.COPY_FROM`을 통해 플레이어 사망, 리스폰, 차원 이동 및 서버 재부팅 후에도 토글 상태를 유지합니다. [[플레이어 토글, 상태 영속성 및 생명주기|ko_kr-26.2-Player-Toggle-and-Persistence]] 문서를 참조하세요.
* **진단 명령어 세트**: 서버 관리자를 위한 내장 `/magnet debug` 및 `/magnet debug log` 진단 도구를 제공합니다. [[Brigadier 명령어 및 인게임 진단|ko_kr-26.2-Commands]] 및 [[HUD 및 진단 시스템|ko_kr-26.2-HUD-and-Diagnostics]] 문서를 참조하세요.

---

## 📑 26.2 문서 목차

### 🎮 게임플레이 및 서버 관리
* [[진공 이동 및 위상 전이 물리|ko_kr-26.2-Vacuum-and-Phase-Shifting]]
* [[시선 감지 및 장애물 관통|ko_kr-26.2-Line-of-Sight-and-Obstruction]]
* [[경험치 구슬 동기화|ko_kr-26.2-Experience-Orb-Attraction]]
* [[즉시 수집 모드 및 AABB 바운딩 박스|ko_kr-26.2-Instant-Pickup-Mode]]
* [[플레이어 토글, 상태 영속성 및 생명주기|ko_kr-26.2-Player-Toggle-and-Persistence]]
* [[GameRules 참조 및 기본 경계값|ko_kr-26.2-GameRules]]
* [[Brigadier 명령어 및 인게임 진단|ko_kr-26.2-Commands]]
* [[발전 과제 및 바닐라 연계|ko_kr-26.2-Advancements]]
* [[YACL 설정 GUI 및 ModMenu|ko_kr-26.2-Configuration]]
* [[작업표시줄 HUD 및 전용 로깅|ko_kr-26.2-HUD-and-Diagnostics]]

### 💻 개발자 및 엔지니어링 레퍼런스
* [[개발자 환경 설정, 툴체인 및 Gradle Loom|ko_kr-26.2-Developer-Setup-and-Building]]
* [[아키텍처, 패키지 및 Mixin 주입|ko_kr-26.2-Architecture-and-Mixins]]
* [[API 파사드, 인터페이스 및 애드온 훅|ko_kr-26.2-API-and-Addon-Integration]]
* [[다중 버전 매트릭스로 돌아가기|ko_kr-Version-Compatibility]]
