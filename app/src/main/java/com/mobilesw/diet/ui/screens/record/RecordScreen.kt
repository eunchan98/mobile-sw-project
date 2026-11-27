package com.mobilesw.diet.ui.screens.record

import androidx.compose.runtime.Composable
import com.mobilesw.diet.ui.screens.PlaceholderScreen

/** 식사 기록(입력) 화면 (담당: 팀원 1). Figma "04 식사 기록" 참고. */
@Composable
fun RecordScreen(
    onBack: () -> Unit,
    onSaved: () -> Unit,
) {
    PlaceholderScreen(
        title = "식사 기록",
        owner = "팀원 1",
        description = "사진, 날짜·시간, 끼니(시간 기준 자동 추천), 음식 검색 + 섭취량(g), 영양소 미리보기, 저장",
        onBack = onBack,
    )
}
