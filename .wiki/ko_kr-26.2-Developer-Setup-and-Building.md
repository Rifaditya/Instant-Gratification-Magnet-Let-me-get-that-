# 🛠️ 개발 환경 설정 및 Loom 빌드 (MC 26.2)

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

| 툴체인 정보 상자 | 기술적 세부 파라미터 |
| :--- | :--- |
| **하위 프로젝트 디렉터리** | `Magnet v26.2/magnet/` |
| **Java JDK 대상 버전** | **Java 25** (`release = 25`) |
| **Gradle Loom 플러그인** | `net.fabricmc.fabric-loom` 버전 `1.15.5` |
| **마인크래프트 버전** | `26.2` |
| **Fabric Loader 버전** | `0.19.1` |
| **Fabric API 버전** | `0.150.1+26.2` |
| **DasikLibrary 버전** | `1.8.23` |
| **YACL 버전** | `3.9.5+26.2-fabric` |

---

## 💻 사전 준비 및 개발 환경 구성

1. **Java Development Kit (JDK 25)**:
   - 최신 마인크래프트 26.x 개발에는 Java 25가 필수적입니다.
   - 환경 변수 `JAVA_HOME`을 설정하거나 `gradle.properties`에 `org.gradle.java.home=E:/JDK25` 경로를 지정합니다.
2. **Git 및 작업 공간 클론**:
   ```bash
   git clone https://github.com/Rifaditya/Instant-Gratification-Magnet-Let-me-get-that-.git
   cd "Instant-Gratification-Magnet-Let-me-get-that-/Magnet v26.2/magnet"
   ```

---

## ⚙️ 프로젝트 프로퍼티 설정 (`gradle.properties`)

```properties
org.gradle.parallel=false
org.gradle.java.home=E:/JDK25

# Mod Properties
mod_name=Magnet, Let me get that!
mod_version=1.3.9+26.2
maven_group=net.instantgratification
archives_base_name=Magnet-Let-me-get-that

# Dependencies
minecraft_version=26.2
parchment_minecraft_version=26.2
parchment_version=2026.01.22

# Fabric
fabric_version=0.150.1+26.2
fabric_loader_version=0.19.1

# Loom
fabric.loom.suppressJavaCompatibilityChecks=true
loom.suppressJavaCompatibilityChecks=true

dasik_library_version=1.8.23
```

---

## 🔨 Gradle 빌드 명령어

```bash
# 이전 빌드 캐시 초기화
./gradlew clean

# 자동화 단위 테스트 실행
./gradlew test

# 프로덕션 배포용 JAR 컴파일 및 자동 아카이빙 실행
./gradlew build --no-daemon
```

---

## 📦 자동 릴리스 아카이빙 및 Modrinth 연동

MC 26.2의 `build.gradle` 스크립트에는 컴파일 후 자동으로 실행되는 `archiveReleaseJar` 태스크가 등록되어 있습니다:

```groovy
tasks.register('archiveReleaseJar') {
    dependsOn 'build'
    doLast {
        def archiveDir = file("${project.rootDir}/../../Archive Jar of all versions/MC 26.2")
        archiveDir.mkdirs()
        def jarFile = tasks.named('jar', Jar).get().archiveFile.get().asFile
        if (jarFile.exists()) {
            copy {
                from jarFile
                into archiveDir
            }
            println "[AUTO-ARCHIVE] Successfully copied ${jarFile.name} to central Archive directory: ${archiveDir.absolutePath}"
        }
    }
}

tasks.named('build') {
    finalizedBy 'archiveReleaseJar'
}
```

빌드가 성공적으로 완료되면 생성된 JAR(`Magnet-Let-me-get-that-1.3.9+26.2.jar`)가 중앙 아카이브 디렉터리(`Archive Jar of all versions/MC 26.2/`)로 복사되며 로컬 Modrinth 런처 테스트 프로필과 자동으로 동기화됩니다.

---

## 🔗 관련 Wiki 문서
* [[아키텍처 및 Mixin 구현|ko_kr-26.2-Architecture-and-Mixins]]
* [[API 및 애드온 통합|ko_kr-26.2-API-and-Addon-Integration]]
* [[MC 26.2 포털로 돌아가기|ko_kr-26.2-Home]]
