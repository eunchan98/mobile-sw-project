package com.mobilesw.diet

import android.app.Application
import android.content.Context
import com.mobilesw.diet.data.local.AppDatabase
import com.mobilesw.diet.data.repository.FoodRepository
import com.mobilesw.diet.data.repository.MealRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/** 앱 전체에서 공유하는 객체 (간단한 수동 DI). */
class AppContainer(context: Context) {
    private val database = AppDatabase.getInstance(context)

    val foodRepository = FoodRepository(database.foodDao()) {
        context.assets.open("foods.csv").bufferedReader(Charsets.UTF_8).use { it.readText() }
    }
    val mealRepository = MealRepository(database.mealDao())
}

class DietApplication : Application() {
    lateinit var container: AppContainer
        private set

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        appScope.launch { container.foodRepository.seedIfEmpty() }
    }
}
