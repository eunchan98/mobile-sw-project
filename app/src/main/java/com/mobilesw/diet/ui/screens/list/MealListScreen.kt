package com.mobilesw.diet.ui.screens.list

import androidx.compose.runtime.Composable
import com.mobilesw.diet.ui.screens.PlaceholderScreen

/** 식사 목록 화면 (담당: 팀원 2). Figma "02 식사 목록" 참고 (달력 + 선택한 날의 끼니). */
@Composable
fun MealListScreen(
    onMealClick: (mealId: Long) -> Unit,
) {
    PlaceholderScreen(
        title = "식사 목록",
        owner = "팀원 2",
        description = "월간 달력(기록한 날 점 표시), 선택한 날짜의 열량·끼니 수 요약과 끼니 카드",
    )
}
