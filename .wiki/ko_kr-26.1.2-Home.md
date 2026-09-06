# 🧲 Magnet, Let me get that! — 마인크래프트 26.1.2 포털

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

**Magnet, Let me get that!** (빌드 `1.1.2+26.1.2`)의 **마인크래프트 26.1.2** 문서 포털에 오신 것을 환영합니다.

이 앵커 에디션은 Cloth Config 통합, 동시성 세션 상태 관리, 360° 구형 레이캐스팅을 통해 완전한 Instant Gratification 아이템 및 경험치 진공 흡인 경험을 제공합니다.

---

## 📋 마인크래프트 26.1.2 빠른 사양 요약

| 규격 항목 | 목표값 | 참조 식별자 |
| :--- | :--- | :--- |
| **마인크래프트 대상 버전** | `26.1.2` | `"minecraft": "*"` |
| **활성 하위 프로젝트 빌드** | `1.1.2+26.1.2` | `mod_version=1.1.2+26.1.2` |
| **Java 툴체인** | Java 25 (`release = 25`) | `JavaLanguageVersion.of(25)` |
| **Fabric Loader** | `>=0.16.10` | `fabric_loader_version=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` | `fabric_version=0.145.4+26.1.2` |
| **공유 코어 라이브러리** | `dasik-library:1.8.23` | `net.dasik.social:dasik-library` |
| **기본 클라이언트 단축키** | `Ctrl+M` | `GLFW.GLFW_KEY_M` + `isControlDown()` |
| **설정 GUI** | Cloth Config Fabric | `ClothConfigScreenHelper` + ModMenu |
| **서버 명령어** | `/magnet toggle`, `/ig_magnet toggle` | `MagnetCommand.java` |

---

## ⚡ 핵심 기능 하이라이트

* **360° 구형 시선 감지 (LOS) 확인**: DasikLibrary 1.8.23의 `PlayerVisionTracker` 기반 작동. [[시선 감지 및 장애물 관통|ko_kr-26.1.2-Line-of-Sight-and-Obstruction]] 문서를 참조하세요.
* **위상 전이 NoClip 물리**: 진공 흡인 도중 아이템이 단단한 지형을 부드럽게 통과합니다. [[진공 이동 및 위상 전이 물리|ko_kr-26.1.2-Vacuum-and-Phase-Shifting]] 문서를 참조하세요.
* **즉시 수집 모드**: 지연 시간 0의 수집을 위한 선택적 바운딩 박스 확장 기능. [[즉시 수집 모드|ko_kr-26.1.2-Instant-Pickup-Mode]] 문서를 참조하세요.
* **동시성 세션 토글**: `MagnetPlayerState`를 통한 단축키 및 명령어 상태 관리. [[플레이어 토글, 단축키 및 상태 저장|ko_kr-26.1.2-Player-Toggle-and-Persistence]] 문서를 참조하세요.
* **Cloth Config GUI**: 카테고리 경고 안내가 포함된 깔끔한 인게임 설정 화면. [[Cloth Config GUI 및 ModMenu|ko_kr-26.1.2-Configuration]] 문서를 참조하세요.

---

## 📑 26.1.2 문서 목차

### 🎮 게임플레이 및 서버 관리
* [[진공 이동 및 위상 전이 물리|ko_kr-26.1.2-Vacuum-and-Phase-Shifting]]
* [[시선 감지 및 장애물 관통|ko_kr-26.1.2-Line-of-Sight-and-Obstruction]]
* [[경험치 구슬 동기화|ko_kr-26.1.2-Experience-Orb-Attraction]]
* [[즉시 수집 모드 및 바운딩 박스 확장|ko_kr-26.1.2-Instant-Pickup-Mode]]
* [[플레이어 토글, 단축키 및 상태 저장|ko_kr-26.1.2-Player-Toggle-and-Persistence]]
* [[GameRules 참조 및 기본 경계값|ko_kr-26.1.2-GameRules]]
* [[서버 명령어 및 바닐라 클라이언트 지원|ko_kr-26.1.2-Commands]]
* [[발전 과제 및 바닐라 연계|ko_kr-26.1.2-Advancements]]
* [[Cloth Config GUI 및 ModMenu|ko_kr-26.1.2-Configuration]]
* [[작업표시줄 HUD 및 시각 효과|ko_kr-26.1.2-HUD-and-Diagnostics]]

### 💻 개발자 및 엔지니어링 레퍼런스
* [[개발자 환경 설정, 툴체인 및 Gradle Loom|ko_kr-26.1.2-Developer-Setup-and-Building]]
* [[아키텍처, 패키지 및 Mixin 주입|ko_kr-26.1.2-Architecture-and-Mixins]]
* [[API 파사드, 인터페이스 및 애드온 훅|ko_kr-26.1.2-API-and-Addon-Integration]]
* [[다중 버전 매트릭스로 돌아가기|ko_kr-Version-Compatibility]]
