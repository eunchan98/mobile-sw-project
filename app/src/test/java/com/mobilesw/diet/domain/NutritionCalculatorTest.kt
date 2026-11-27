package com.mobilesw.diet.domain

import com.mobilesw.diet.data.local.entity.FoodEntity
import com.mobilesw.diet.data.local.entity.MealEntity
import com.mobilesw.diet.data.local.entity.MealItemEntity
import com.mobilesw.diet.data.local.entity.MealItemWithFood
import com.mobilesw.diet.data.local.entity.MealWithItems
import com.mobilesw.diet.domain.model.MealType
import com.mobilesw.diet.domain.model.Nutrition
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class NutritionCalculatorTest {

    // 100g 기준: 열량 143kcal, 탄 31.7g, 단 2.5g, 지 0.3g
    private val rice = FoodEntity(1, "쌀밥", "밥류", 143.0, 31.7, 2.5, 0.3)
    // 100g 기준: 열량 190kcal, 탄 8.0g, 단 14.7g, 지 10.0g
    private val pork = FoodEntity(2, "제육볶음", "볶음류", 190.0, 8.0, 14.7, 10.0)

    private fun assertNutrition(expected: Nutrition, actual: Nutrition) {
        assertEquals(expected.kcal, actual.kcal, 1e-9)
        assertEquals(expected.carbs, actual.carbs, 1e-9)
        assertEquals(expected.protein, actual.protein, 1e-9)
        assertEquals(expected.fat, actual.fat, 1e-9)
    }

    @Test
    fun 섭취량이_100g이면_100g당_영양소와_같다() {
        assertNutrition(Nutrition(143.0, 31.7, 2.5, 0.3), NutritionCalculator.forFood(rice, 100.0))
    }

    @Test
    fun 섭취량이_150g이면_영양소를_1점5배로_계산한다() {
        assertNutrition(Nutrition(285.0, 12.0, 22.05, 15.0), NutritionCalculator.forFood(pork, 150.0))
    }

    @Test
    fun 섭취량이_210g인_공기밥은_100g당_값의_2점1배다() {
        assertNutrition(Nutrition(300.3, 66.57, 5.25, 0.63), NutritionCalculator.forFood(rice, 210.0))
    }

    @Test
    fun 섭취량이_0g이면_영양소는_모두_0이다() {
        assertNutrition(Nutrition.ZERO, NutritionCalculator.forFood(rice, 0.0))
    }

    @Test
    fun 섭취량이_음수이거나_숫자가_아니면_예외를_던진다() {
        assertThrows(IllegalArgumentException::class.java) { NutritionCalculator.forFood(rice, -10.0) }
        assertThrows(IllegalArgumentException::class.java) { NutritionCalculator.forFood(rice, Double.NaN) }
    }

    @Test
    fun 한끼의_영양소는_음식별_영양소의_합이다() {
        val meal = MealWithItems(
            meal = MealEntity(id = 1, eatenAt = 0L, mealType = MealType.LUNCH),
            items = listOf(
                MealItemWithFood(MealItemEntity(mealId = 1, foodId = 1, grams = 210.0), rice),
                MealItemWithFood(MealItemEntity(mealId = 1, foodId = 2, grams = 150.0), pork),
            ),
        )
        // 쌀밥 210g (300.3, 66.57, 5.25, 0.63) + 제육볶음 150g (285, 12, 22.05, 15)
        assertNutrition(Nutrition(585.3, 78.57, 27.3, 15.63), NutritionCalculator.forMeal(meal))
    }

    @Test
    fun 음식이_없는_끼니의_영양소는_0이다() {
        val meal = MealWithItems(MealEntity(id = 1, eatenAt = 0L, mealType = MealType.SNACK), emptyList())
        assertNutrition(Nutrition.ZERO, NutritionCalculator.forMeal(meal))
    }

    @Test
    fun 여러_끼니의_영양소를_모두_더한다() {
        val lunch = MealWithItems(
            MealEntity(id = 1, eatenAt = 0L, mealType = MealType.LUNCH),
            listOf(MealItemWithFood(MealItemEntity(mealId = 1, foodId = 1, grams = 100.0), rice)),
        )
        val dinner = MealWithItems(
            MealEntity(id = 2, eatenAt = 0L, mealType = MealType.DINNER),
            listOf(MealItemWithFood(MealItemEntity(mealId = 2, foodId = 2, grams = 100.0), pork)),
        )
        assertNutrition(Nutrition(333.0, 39.7, 17.2, 10.3), NutritionCalculator.forMeals(listOf(lunch, dinner)))
    }
}
