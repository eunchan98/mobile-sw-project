package com.mobilesw.diet.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mobilesw.diet.data.local.entity.FoodEntity
import com.mobilesw.diet.data.local.entity.MealEntity
import com.mobilesw.diet.data.local.entity.MealItemEntity
import com.mobilesw.diet.domain.model.MealType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/** 공통 기반 DB(Room) 동작 확인용 계측 테스트. 메모리 DB를 쓴다. */
@RunWith(AndroidJUnit4::class)
class DaoTest {
    private lateinit var db: AppDatabase

    private val rice = FoodEntity(1, "쌀밥", "밥류", 143.0, 31.7, 2.5, 0.3)
    private val pork = FoodEntity(2, "제육볶음", "볶음류", 190.0, 8.0, 14.7, 10.0)
    private val porkRice = FoodEntity(3, "제육덮밥", "밥류", 165.0, 22.0, 7.0, 5.0)

    @Before
    fun setUp() = runTest {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        db.foodDao().insertAll(listOf(rice, pork, porkRice))
    }

    @After
    fun tearDown() = db.close()

    @Test
    fun 음식_이름의_일부로_검색한다() = runTest {
        val result = db.foodDao().search("제육")
        assertEquals(listOf("제육덮밥", "제육볶음"), result.map { it.name })
    }

    @Test
    fun 한끼를_음식과_함께_저장하고_범위로_조회한다() = runTest {
        val dao = db.mealDao()
        val id = dao.insertMealWithItems(
            MealEntity(eatenAt = 1_000L, mealType = MealType.LUNCH),
            listOf(MealItemEntity(foodId = 1, grams = 210.0), MealItemEntity(foodId = 2, grams = 150.0)),
        )
        dao.insertMealWithItems(MealEntity(eatenAt = 5_000L, mealType = MealType.DINNER), emptyList())

        val meals = dao.observeMealsBetween(0L, 2_000L).first()

        assertEquals(1, meals.size)
        assertEquals(id, meals[0].meal.id)
        assertEquals(MealType.LUNCH, meals[0].meal.mealType)
        assertEquals(setOf("쌀밥", "제육볶음"), meals[0].items.map { it.food.name }.toSet())
    }

    @Test
    fun 끼니를_지우면_음식_항목도_함께_지워진다() = runTest {
        val dao = db.mealDao()
        val id = dao.insertMealWithItems(
            MealEntity(eatenAt = 1_000L, mealType = MealType.BREAKFAST),
            listOf(MealItemEntity(foodId = 1, grams = 100.0)),
        )
        dao.deleteMeal(id)
        assertNull(dao.observeMeal(id).first())
    }
}
