package com.mobilesw.diet.ui.screens.home

import androidx.compose.runtime.Composable
import com.mobilesw.diet.ui.components.PrimaryButton
import com.mobilesw.diet.ui.screens.PlaceholderScreen

/** 홈 화면 (담당: 팀원 1). Figma "01 홈" 참고. */
@Composable
fun HomeScreen(
    onAddMeal: () -> Unit,
    onMealClick: (mealId: Long) -> Unit,
) {
    PlaceholderScreen(
        title = "오늘의 식단",
        owner = "팀원 1",
        description = "오늘 섭취 열량과 탄·단·지 합계, 빠른 기록 버튼, 오늘 먹은 끼니 카드",
    ) {
        PrimaryButton(text = "+  식사 기록하기", onClick = onAddMeal)
    }
}
