package com.mobilesw.diet.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = WatermelonPink,
    onPrimary = OnPrimary,
    primaryContainer = WatermelonPinkSoft,
    onPrimaryContainer = WatermelonPink,
    secondary = LimeGreen,
    onSecondary = DarkGray,
    secondaryContainer = MintSoft,
    onSecondaryContainer = DarkGray,
    tertiary = MintGreen,
    onTertiary = DarkGray,
    background = AppBackground,
    onBackground = DarkGray,
    surface = Surface,
    onSurface = DarkGray,
    surfaceVariant = AppBackground,
    onSurfaceVariant = MutedGray,
    outline = Border,
    outlineVariant = Border,
)

private val AppShapes = Shapes(
    small = RoundedCornerShape(12.dp),   // 입력 필드, 음식 행
    medium = RoundedCornerShape(16.dp),  // 카드, 버튼
    large = RoundedCornerShape(20.dp),   // 큰 카드, 사진
)

/** 앱 전체 테마. 다크 모드는 지원하지 않는다 (디자인이 라이트 전용). */
@Composable
fun DietTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}
