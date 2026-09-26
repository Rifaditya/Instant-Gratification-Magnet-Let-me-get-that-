# 🗺️ 다중 버전 호환성 매트릭스

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

이 문서는 **Magnet, Let me get that!** (`ig_magnet`)의 지원 마인크래프트 버전, Java 실행 환경, Fabric Loader 의존성 및 빌드 툴체인을 정리한 매트릭스입니다.

---

## 📊 다중 버전 생명주기 개요

| 대상 마인크래프트 | 하위 프로젝트 폴더 | 활성 모드 버전 | 대상 Java | Fabric Loader | Fabric API | DasikLibrary | 설정 GUI 라이브러리 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | `Magnet v26.2/magnet` | `1.3.9+26.2` | Java 25 (`release = 25`) | `>=0.19.1` | `0.150.1+26.2` | `1.8.23` | YetAnotherConfigLib v3 (`3.9.5+26.2-fabric`) + ModMenu |
| **Minecraft 26.1.2** | `Magnet v26.1/magnet` | `1.1.2+26.1.2` | Java 25 (`release = 25`) | `>=0.16.10` | `0.145.4+26.1.2` | `1.8.23` | Cloth Config Fabric (`26.1.154`) + ModMenu |

---

## 🔍 세부 버전별 아키텍처 분석

### 🟢 Minecraft 26.2 (`1.3.9+26.2`)
* **상태**: 주력 최신 릴리스 (Primary Modern Release)
* **하위 프로젝트 경로**: `Magnet v26.2/magnet/`
* **빌드 출력 파일**: `Magnet-Let-me-get-that-1.3.9+26.2.jar`
* **중앙 아카이브 디렉터리**: `Archive Jar of all versions/MC 26.2/`
* **의존성 범위 (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.19.1",
      "minecraft": ">=26.2-",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **핵심 아키텍처 특징**:
  * `PlayerMixin` 내부에서 마인크래프트 26.2의 `ValueOutput` 및 `ValueInput` 코덱을 사용하는 영속적 NBT 저장소.
  * Fabric 생명주기 이벤트(`ServerPlayerEvents.COPY_FROM`, `ServerPlayerEvents.AFTER_RESPAWN`)를 통한 사망 및 차원 이동 시 플레이어 상태 자동 보존.
  * 동적 `ig_magnet$getKeyboardType()` 폴백 로직이 적용된 `\` (`GLFW_KEY_BACKSLASH`) 단축키 매핑.
  * 전용 파일 로깅(`logs/ig_magnet_debug.log`)을 지원하는 인게임 진단 명령어 `/magnet debug` 및 `/magnet debug log`.
  * YetAnotherConfigLib v3 기반의 최신 `YaclScreenHelper`.

---

### 🟢 Minecraft 26.1.2 (`1.1.2+26.1.2`)
* **상태**: 최신 앵커 하위 프로젝트 (Modern Anchor Subproject)
* **하위 프로젝트 경로**: `Magnet v26.1/magnet/`
* **빌드 출력 파일**: `Magnet-Let-me-get-that-1.1.2+26.1.2.jar`
* **중앙 아카이브 디렉터리**: `Archive Jar of all versions/MC 26.1.2/`
* **의존성 범위 (`fabric.mod.json`)**:
  ```json
  "depends": {
      "fabricloader": ">=0.16.10",
      "minecraft": "*",
      "java": ">=25",
      "fabric-api": "*",
      "dasik-library": "*"
  }
  ```
* **핵심 아키텍처 특징**:
  * `MagnetPlayerState` (`Map<UUID, Boolean>`) 기반의 동시성 세션 상태 추적.
  * `Ctrl+M` (`GLFW_KEY_M` + `isControlDown()`) 단축키 토글.
  * `client.gui.setOverlayMessage(...)` 기반의 네이티브 작업표시줄 오버레이 피드백.
  * `ClothConfigScreenHelper`를 통한 리플렉션 안전 선택적 GUI 연동.

---

## 📦 자동 릴리스 아카이빙 및 런처 배포

두 하위 프로젝트 모두 Gradle 빌드 스크립트(`build.gradle`)에 컴파일 후 자동 아카이빙 작업이 내장되어 있습니다:

```bash
# MC 26.2 빌드 컴파일 및 자동 아카이빙:
cd "Magnet v26.2/magnet"
./gradlew build --no-daemon

# MC 26.1.2 빌드 컴파일 및 자동 아카이빙:
cd "Magnet v26.1/magnet"
./gradlew build --no-daemon
```

`./gradlew build` 명령을 실행하면 `archiveReleaseJar` 태스크가 빌드된 JAR 파일을 중앙 아카이브 폴더(`Archive Jar of all versions/MC <Version>/`)로 자동 복사하며, 활성화된 로컬 Modrinth 런처 테스트 프로필로 즉시 동기화합니다.

---

## 🔗 관련 Wiki 문서
* [[MC 26.2 환경 설정 및 도구|ko_kr-26.2-Developer-Setup-and-Building]]
* [[MC 26.1.2 환경 설정 및 도구|ko_kr-26.1.2-Developer-Setup-and-Building]]
* [[중앙 스위치보드 포털로 돌아가기|ko_kr-Home]]
