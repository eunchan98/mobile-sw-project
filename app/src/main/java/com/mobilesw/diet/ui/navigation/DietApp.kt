package com.mobilesw.diet.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mobilesw.diet.ui.screens.analysis.AnalysisScreen
import com.mobilesw.diet.ui.screens.detail.MealDetailScreen
import com.mobilesw.diet.ui.screens.home.HomeScreen
import com.mobilesw.diet.ui.screens.list.MealListScreen
import com.mobilesw.diet.ui.screens.record.RecordScreen
import com.mobilesw.diet.ui.screens.settings.SettingsScreen

/** 앱 루트: 하단 탭 + 화면 이동. 탭 화면에서만 하단 바를 보여준다. */
@Composable
fun DietApp(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = BottomTab.entries.any { it.route == currentRoute }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (showBottomBar) {
                androidx.compose.foundation.layout.Column {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    DietBottomBar(currentRoute = currentRoute, onTabSelected = { navController.navigateToTab(it) })
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    onAddMeal = { navController.navigate(Routes.RECORD) },
                    onMealClick = { navController.navigate(Routes.mealDetail(it)) },
                )
            }
            composable(Routes.LIST) {
                MealListScreen(onMealClick = { navController.navigate(Routes.mealDetail(it)) })
            }
            composable(Routes.ANALYSIS) { AnalysisScreen() }
            composable(Routes.SETTINGS) { SettingsScreen() }
            composable(Routes.RECORD) {
                RecordScreen(
                    onBack = { navController.popBackStack() },
                    onSaved = { navController.popBackStack() },
                )
            }
            composable(
                route = Routes.MEAL_DETAIL,
                arguments = listOf(navArgument(Routes.MEAL_ID_ARG) { type = NavType.LongType }),
            ) { entry ->
                MealDetailScreen(
                    mealId = entry.arguments?.getLong(Routes.MEAL_ID_ARG) ?: 0L,
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}

/** 탭 이동: 같은 탭을 여러 번 쌓지 않고, 탭별 상태를 유지한다. */
private fun NavHostController.navigateToTab(tab: BottomTab) {
    navigate(tab.route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
