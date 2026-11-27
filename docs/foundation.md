# 공통 기반 안내 (각자 작업 시작 전에 읽기)

## 프로젝트 구조

```
app/src/main/java/com/mobilesw/diet/
├─ DietApplication.kt        앱 시작 시 DB 생성 + 음식 데이터 채우기, AppContainer(저장소 보관)
├─ MainActivity.kt
├─ domain/model/
│  ├─ MealType.kt            끼니 종류 enum (조식·중식·석식·간식/음료)
│  └─ Nutrition.kt           열량·탄·단·지 묶음, 더하기(+)와 합계(sum)
├─ data/
│  ├─ local/entity/Entities.kt   FoodEntity, MealEntity, MealItemEntity, MealWithItems
│  ├─ local/dao/                 FoodDao, MealDao
│  ├─ local/AppDatabase.kt
│  ├─ seed/FoodCsvParser.kt      assets/foods.csv → FoodEntity
│  └─ repository/                FoodRepository, MealRepository
└─ ui/
   ├─ theme/                 Color.kt, Type.kt, Theme.kt (Figma 토큰)
   ├─ components/            AppTopBar, PrimaryButton/OutlineButton, SelectChip/MealTypeSelector,
   │                         MealCard/MealTypeTag/MealPhoto, AppCard/LegendLabel
   ├─ navigation/            Routes, BottomTab, DietBottomBar, DietApp(NavHost)
   ├─ screens/               화면별 폴더 (지금은 자리 표시 화면)
   └─ AppViewModelProvider.kt  ViewModel에서 저장소를 꺼내는 함수
```

## 데이터 모델

- **FoodEntity**: 음식 DB. 영양소는 모두 **100g 기준** (`per100g`으로 `Nutrition`을 얻을 수 있음)
- **MealEntity**: 한 끼. 먹은 시각(`eatenAt`, epoch millis), 끼니 종류, 사진 URI
- **MealItemEntity**: 한 끼에 들어간 음식 하나와 섭취량(`grams`)
- **MealWithItems**: 조회용. 끼니 + (음식 항목 + 음식 정보) 목록

저장소 함수:

| 저장소 | 함수 | 용도 |
|---|---|---|
| FoodRepository | `search(query)` | 음식 이름 자동완성 (식사 기록) |
| | `getById(id)`, `getAll()` | |
| MealRepository | `saveMeal(meal, items)` | 끼니 저장 (식사 기록) |
| | `observeMealsOn(date)` | 하루치 끼니 (홈, 식사 목록) |
| | `observeMealsBetween(from, to)` | 기간 조회: 달력, 최근 30일 분석 |
| | `observeMeal(id)`, `deleteMeal(id)` | 식사 상세 |

## 영양소 계산과 표시 (`domain/`)

계산은 반올림 없이 그대로 하고, 화면에 보여줄 때만 반올림한다.

```kotlin
NutritionCalculator.forFood(food, grams = 150.0)  // 음식 하나: 100g당 값 × 섭취량 / 100
NutritionCalculator.forItem(mealItemWithFood)     // 끼니 속 음식 항목 하나 (상세 화면 음식 행)
NutritionCalculator.forMeal(mealWithItems)        // 한 끼 합계 (식사 카드, 상세 화면)
NutritionCalculator.forMeals(meals)               // 여러 끼 합계 (하루 요약, 최근 30일)

NutritionFormatter.kcal(n.kcal)    // "1,140"   정수 반올림 + 천 단위 쉼표
NutritionFormatter.grams(n.carbs)  // "22.1g"   소수점 한 자리, ".0" 생략
NutritionFormatter.macros(n)       // "탄 98g · 단 34g · 지 21g"

IntakeAmount.parse("72.5")         // 섭취량 입력 검사: 1~2000g, 소수점 한 자리까지. 틀리면 null
```

- 열량은 탄·단·지로 다시 계산하지 않고 식약처 DB의 에너지(kcal) 값을 섭취량에 비례해 쓴다.
- 반올림은 HALF_UP이며, 부동소수점 오차(예: 14.7 × 1.5 = 22.0499…)도 처리한다.

## 각자 할 일

**팀원 1: 식사 기록 기능** — `ui/screens/home`, `ui/screens/record`, `assets/foods.csv`
- ~~영양소 계산~~ ✅ 완료 (아래 "영양소 계산과 표시" 참고)
- 끼니 자동 추천: 식사 시간 → `MealType`
- 식사 기록 화면: 사진 선택(Photo Picker), 날짜·시간, 끼니, 음식 검색, 섭취량, 영양소 미리보기, 저장
- 홈 화면: 오늘 섭취 열량과 탄·단·지, 오늘 먹은 끼니
- ~~음식 데이터 약 100종으로 `foods.csv` 교체~~ ✅ 완료

**팀원 2: 식사 조회·분석 기능** — `ui/screens/list`, `detail`, `analysis`, `settings`
- 식사 목록 화면: 월간 달력, 날짜별 열량·끼니 수 요약, 끼니 카드
- 식사 상세 화면: 끼니 정보, 음식별 섭취량과 영양소, 삭제
- 분석 화면: 최근 30일 합계·하루 평균, 일별 칼로리, 끼니별 영양 분석
- 설정 화면: 데이터 출처, 앱 정보

## ViewModel 만드는 법

```kotlin
class HomeViewModel(private val mealRepository: MealRepository) : ViewModel() { ... }

@Composable
fun HomeScreen(
    ...,
    viewModel: HomeViewModel = viewModel(factory = viewModelFactory {
        initializer { HomeViewModel(appContainer().mealRepository) }
    }),
)
```

필요한 import: `androidx.lifecycle.viewmodel.compose.viewModel`, `androidx.lifecycle.viewmodel.viewModelFactory`, `androidx.lifecycle.viewmodel.initializer`, `com.mobilesw.diet.ui.appContainer`

## 실행과 테스트

```bash
./gradlew :app:assembleDebug            # 빌드
./gradlew :app:testDebugUnitTest        # 단위 테스트 (app/src/test)
./gradlew :app:connectedDebugAndroidTest  # 기기·에뮬레이터 테스트 (app/src/androidTest)
```

공통 기반에 들어 있는 테스트: `FoodCsvParserTest`(3), `NutritionTest`(3), `DaoTest`(3, 계측 테스트)

## 음식 데이터

`app/src/main/assets/foods.csv`에는 식약처 음식 데이터에서 고른 **학생식당 메뉴 100종**이 들어 있다. 만드는 방법과 값 고르는 규칙은 [food-data.md](food-data.md).
이전 임시 샘플(10종)로 이미 설치한 앱은 **앱 데이터 삭제(또는 재설치)** 후 실행해야 새 데이터가 들어간다.
