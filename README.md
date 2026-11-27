# 식단 기록 — 학생 식당 개인 식단 관리 앱

2026 모바일 소프트웨어 학기말 프로젝트 (Jetpack Compose)

매일 먹은 음식을 사진과 함께 기록하고, 하루 및 최근 한 달의 열량과 영양 균형(탄수화물·단백질·지방)을 보여주는 안드로이드 앱입니다.

- 디자인 (Figma): https://www.figma.com/design/oG8yPE1nNpDJGceqi6ueLY
- 기능 명세: [docs/spec.md](docs/spec.md)
- 협업 규칙 (브랜치·커밋·PR): [CONTRIBUTING.md](CONTRIBUTING.md)

## 주요 기능

- **식사 기록**: 날짜·시간, 사진, 끼니(시간 기준 자동 추천), 음식 검색 후 섭취량(g) 입력, 영양소 미리보기
- **식사 목록**: 월간 달력에서 날짜를 선택하면 그날의 끼니 목록, 끼니를 누르면 상세 내용과 영양소
- **분석**: 최근 30일 총 열량, 일별 칼로리, 끼니별(조식·중식·석식·간식/음료) 영양 분석
- **설정**: 영양 데이터 출처, 앱 정보

## 설치 및 실행 방법

- 필요 환경: Android Studio (AGP 9.3), JDK 17 이상, Android SDK 37, 기기 또는 에뮬레이터 Android 9(API 28) 이상
- 기술: Kotlin, Jetpack Compose (Material 3), Navigation Compose, Room, Coil

1. 저장소 받기: `git clone https://github.com/eunchan98/mobile-sw-project.git`
2. Android Studio에서 **File → Open**으로 폴더를 열고 Gradle 동기화가 끝날 때까지 기다린다.
3. 에뮬레이터나 기기를 연결하고 **Run ▶** (`app`)

명령줄:

```bash
./gradlew :app:assembleDebug
./gradlew :app:testDebugUnitTest
./gradlew :app:connectedDebugAndroidTest
```

코드 구조와 작업 안내: [docs/foundation.md](docs/foundation.md)

## 영양 데이터 출처

> 작성 예정 (식품의약품안전처 식품영양성분 데이터베이스, 음식 약 100종)

## 역할 분담

### 팀원 1 (@eunchan98)

- **공통 기반**: 프로젝트 설정, 테마(Figma 색상·글자), Room DB(음식·끼니·음식 항목), 하단 탭 네비게이션, 공통 컴포넌트(상단바, 버튼, 끼니 칩, 식사 카드)
- **음식 데이터 수집**: 식약처 식품영양성분 DB에서 음식 약 100종의 100g당 열량·탄수화물·단백질·지방 정리
- **영양소 계산**: 음식과 섭취량(g)으로 열량·탄·단·지 계산
- **끼니 자동 추천**: 식사 시간으로 조식·중식·석식·간식/음료 추천
- **식사 기록 화면**: 사진 선택, 날짜·시간, 끼니 선택, 음식 검색(자동완성)과 섭취량 입력, 영양소 미리보기, 저장
- **홈 화면**: 오늘 섭취 열량과 탄·단·지, 식사 기록 버튼, 오늘 먹은 끼니 목록

### 팀원 2

- **식사 목록 화면**: 월간 달력(기록한 날 표시), 날짜를 고르면 그날 열량·끼니 수 요약과 끼니 카드 목록
- **식사 상세 화면**: 사진, 끼니·날짜, 총 열량과 탄·단·지, 음식별 섭취량과 영양소, 삭제
- **분석 화면**: 최근 30일 총 열량과 하루 평균, 일별 칼로리 막대 그래프, 끼니별 열량 비율과 영양소 표
- **설정 화면**: 영양 데이터 출처, 앱 정보
- **집계 로직**: 날짜별 요약, 최근 30일 합계, 끼니별 분석 계산

## 테스트

> 작성 예정 (로컬 단위 테스트 12개 이상, Compose UI 테스트 2개 이상)

## 팀원 별 vibe coding 사용 목록

- 팀원 1: [docs/prompts_eunchan.md](docs/prompts_eunchan.md)
- 팀원 2: `docs/prompts_영문이름.md` (작성 예정)
