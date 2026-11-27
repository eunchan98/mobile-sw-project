package com.mobilesw.diet.ui.screens.detail

import androidx.compose.runtime.Composable
import com.mobilesw.diet.ui.screens.PlaceholderScreen

/** 식사 상세 화면 (담당: 팀원 2). Figma "03 식사 상세" 참고. */
@Composable
fun MealDetailScreen(
    mealId: Long,
    onBack: () -> Unit,
) {
    PlaceholderScreen(
        title = "식사 상세",
        owner = "팀원 2",
        description = "끼니 #$mealId: 사진, 끼니·날짜, 총 열량과 탄·단·지, 음식별 섭취량과 영양소",
        onBack = onBack,
    )
}
