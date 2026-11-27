package com.mobilesw.diet.ui.navigation

import androidx.annotation.DrawableRes
import com.mobilesw.diet.R

/** 화면 경로. 상세 화면은 [mealDetail]로 경로를 만든다. */
object Routes {
    const val HOME = "home"
    const val LIST = "list"
    const val ANALYSIS = "analysis"
    const val SETTINGS = "settings"
    const val RECORD = "record"

    const val MEAL_ID_ARG = "mealId"
    const val MEAL_DETAIL = "meal/{$MEAL_ID_ARG}"
    fun mealDetail(mealId: Long) = "meal/$mealId"
}

/** 하단 탭 4개 (Figma: BottomNav). */
enum class BottomTab(val route: String, val label: String, @DrawableRes val icon: Int) {
    HOME(Routes.HOME, "홈", R.drawable.ic_home),
    LIST(Routes.LIST, "목록", R.drawable.ic_list),
    ANALYSIS(Routes.ANALYSIS, "분석", R.drawable.ic_chart),
    SETTINGS(Routes.SETTINGS, "설정", R.drawable.ic_settings),
}
