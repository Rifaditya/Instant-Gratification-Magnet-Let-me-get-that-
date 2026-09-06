# 🧲 Magnet, Let me get that! — 공식 Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

**Magnet, Let me get that!** (`ig_magnet`)의 공식 기술 및 게임플레이 Wiki에 오신 것을 환영합니다. 본 모드는 최신 마인크래프트 Fabric 환경을 위해 정밀하게 설계된 플레이어 내재형(intrinsic) 아이템 및 경험치 진공 흡인 모드입니다.

**즉각적인 만족감(Instant Gratification, IG)** 디자인 철학을 기반으로 제작된 이 모드는 방금 채굴하거나 처치하여 드롭된 아이템을 줍기 위해 5블록을 걸어가야 하는 불필요한 번거로움("굴욕의 발걸음")을 완전히 제거합니다. 플레이어의 시야에 닿는 아이템이라면 즉시 인벤토리에 들어와야 합니다.

---

## 🧭 다중 버전 전환 포털

플레이 중인 마인크래프트 버전을 선택하여 전용 게임플레이 가이드, 기술 문서, GameRules 표 및 아키텍처 레퍼런스를 확인하세요:

| 마인크래프트 버전 | 릴리스 상태 | 활성 버전 빌드 | 설정 엔진 | 포털 링크 |
| :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.2** | 🟢 최신 활성 버전 | `1.3.9+26.2` | YACL v3 + ModMenu | [[📖 26.2 개요|ko_kr-26.2-Home]] |
| **Minecraft 26.1.2** | 🟢 최신 앵커 버전 | `1.1.2+26.1.2` | Cloth Config + ModMenu | [[📖 26.1.2 개요|ko_kr-26.1.2-Home]] |

### 🚀 버전 포털 바로가기:
* 📦 **Minecraft 26.2**: [[👉 마인크래프트 26.2 문서 포털 입장|ko_kr-26.2-Home]]
* 📦 **Minecraft 26.1.2**: [[👉 마인크래프트 26.1.2 문서 포털 입장|ko_kr-26.1.2-Home]]

빌드 툴체인, 의존성 매트릭스, 아카이브 보관 위치 및 이전 버전 호환성에 대한 자세한 내용은 [[다중 버전 호환성 매트릭스|ko_kr-Version-Compatibility]] 문서를 참조하세요.

---

## ⚡ 핵심 기능 매트릭스

```
                      +-----------------------------+
                      |   플레이어 자석 진공 발생   |
                      +--------------+--------------+
                                     |
              +----------------------+----------------------+
              |                                             |
              v                                             v
  +-----------------------+                     +-----------------------+
  |    일반 끌어당김 모드  |                     |     즉시 수집 모드    |
  +-----------+-----------+                     +-----------+-----------+
              |                                             |
     [시선 감지 확인 (LOS)]                        [AABB 박스 확장]
     [360° 구형 레이캐스트]                        [이동 지연 시간 0]
     [위상 전이 NoClip 통과]                       [인벤토리 직행]
     [동적 Lerp 보간 속도]                                  |
              |                                             |
              +----------------------+----------------------+
                                     |
                                     v
                      +-----------------------------+
                      |   아이템 및 경험치 구슬 획득 |
                      +-----------------------------+
```

* **스마트 360° 진공 흡인**: 플레이어가 설정한 블록 반경 내에 떨어진 아이템과 경험치 구슬을 끌어당깁니다 (기본값: 12블록, 최대 64블록까지 지원).
* **위상 전이 (Phase Shifting / NoClip)**: 자석에 끌려오는 아이템이 고체 블록을 부드럽게 통과하여 폭발 잔해나 채굴장 틈새에 영구적으로 끼이는 현상을 방지합니다.
* **시선 감지 (Line-of-Sight, LOS) 지원**: DasikLibrary의 `PlayerVisionTracker`를 통한 기본 360° 구형 레이캐스팅과 투명 블록(유리), 식물(풀, 잎), 블록 엔티티(상자)에 대한 정밀 필터링 옵션을 제공합니다.
* **운동량 연속성 (`keepMovingIfUnseen`)**: 시선 내에서 자석에 끌리기 시작한 아이템은 도중에 장애물 뒤로 가려지더라도 지속적으로 플레이어를 향해 이동합니다.
* **즉시 수집 (Instant Pickup) 모드**: 플레이어 본래의 수집 바운딩 박스를 확장하여 비행 시간 없이 0틱 지연 시간으로 아이템을 즉시 흡수합니다.
* **단축키 및 명령어 제어**: 클라이언트 측 단축키(26.2에서는 `\`, 26.1.2에서는 `Ctrl+M`) 또는 서버 측 `/magnet toggle` 명령어를 통해 자석 기능을 손쉽게 토글할 수 있습니다.
* **인벤토리 슬롯 낭비 제로**: 100% 내재형 기능으로, 별도의 자석 아이템, 장신구(Baubles) 또는 배터리 충전이 전혀 필요하지 않습니다.

---

## 📚 백과사전식 전체 목차

### 🎮 플레이어 및 관리자 가이드
* [[MC 26.2 개요|ko_kr-26.2-Home]] & [[MC 26.1.2 개요|ko_kr-26.1.2-Home]]
* [[MC 26.2 진공 이동 및 위상 전이|ko_kr-26.2-Vacuum-and-Phase-Shifting]] & [[MC 26.1.2 진공 이동 및 위상 전이|ko_kr-26.1.2-Vacuum-and-Phase-Shifting]]
* [[MC 26.2 시선 감지 확인|ko_kr-26.2-Line-of-Sight-and-Obstruction]] & [[MC 26.1.2 시선 감지 확인|ko_kr-26.1.2-Line-of-Sight-and-Obstruction]]
* [[MC 26.2 경험치 구슬 흡인|ko_kr-26.2-Experience-Orb-Attraction]] & [[MC 26.1.2 경험치 구슬 흡인|ko_kr-26.1.2-Experience-Orb-Attraction]]
* [[MC 26.2 즉시 수집 모드|ko_kr-26.2-Instant-Pickup-Mode]] & [[MC 26.1.2 즉시 수집 모드|ko_kr-26.1.2-Instant-Pickup-Mode]]
* [[MC 26.2 플레이어 토글 및 상태 영속성|ko_kr-26.2-Player-Toggle-and-Persistence]] & [[MC 26.1.2 플레이어 토글 및 상태 유지|ko_kr-26.1.2-Player-Toggle-and-Persistence]]
* [[MC 26.2 GameRules 전체 참조|ko_kr-26.2-GameRules]] & [[MC 26.1.2 GameRules 전체 참조|ko_kr-26.1.2-GameRules]]
* [[MC 26.2 명령어 목록|ko_kr-26.2-Commands]] & [[MC 26.1.2 명령어 목록|ko_kr-26.1.2-Commands]]
* [[MC 26.2 발전 과제|ko_kr-26.2-Advancements]] & [[MC 26.1.2 발전 과제|ko_kr-26.1.2-Advancements]]
* [[MC 26.2 설정 GUI|ko_kr-26.2-Configuration]] & [[MC 26.1.2 설정 GUI|ko_kr-26.1.2-Configuration]]
* [[MC 26.2 HUD 및 진단 시스템|ko_kr-26.2-HUD-and-Diagnostics]] & [[MC 26.1.2 HUD 및 진단 시스템|ko_kr-26.1.2-HUD-and-Diagnostics]]

### 💻 개발자 및 기여자 기술 문서
* [[MC 26.2 개발 환경 설정 및 빌드|ko_kr-26.2-Developer-Setup-and-Building]] & [[MC 26.1.2 개발 환경 설정 및 빌드|ko_kr-26.1.2-Developer-Setup-and-Building]]
* [[MC 26.2 아키텍처 및 Mixin 주입 대상|ko_kr-26.2-Architecture-and-Mixins]] & [[MC 26.1.2 아키텍처 및 Mixin 주입 대상|ko_kr-26.1.2-Architecture-and-Mixins]]
* [[MC 26.2 API 및 애드온 통합|ko_kr-26.2-API-and-Addon-Integration]] & [[MC 26.1.2 API 및 애드온 통합|ko_kr-26.1.2-API-and-Addon-Integration]]
* [[다중 버전 호환성 매트릭스|ko_kr-Version-Compatibility]]

---

## ⚖️ 라이선스 및 저작권 안내

**Dasik (Rifaditya)** 가 개발하였으며 **GNU General Public License v3.0 (GPLv3)** 하에 배포됩니다. 자세한 이용 약관 및 법적 권한은 프로젝트의 `LICENSE` 파일을 확인하세요.
