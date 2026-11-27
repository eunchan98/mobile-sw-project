package com.mobilesw.diet.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class NutritionTest {

    @Test
    fun 두_영양정보를_더하면_항목별로_합쳐진다() {
        val a = Nutrition(kcal = 300.0, carbs = 60.0, protein = 6.0, fat = 1.0)
        val b = Nutrition(kcal = 120.0, carbs = 18.0, protein = 6.0, fat = 5.0)

        assertEquals(Nutrition(kcal = 420.0, carbs = 78.0, protein = 12.0, fat = 6.0), a + b)
    }

    @Test
    fun 빈_목록의_합계는_ZERO다() {
        assertEquals(Nutrition.ZERO, emptyList<Nutrition>().sum())
    }

    @Test
    fun 목록의_합계는_모든_항목을_더한_값이다() {
        val list = listOf(
            Nutrition(100.0, 10.0, 5.0, 2.0),
            Nutrition(200.0, 20.0, 10.0, 4.0),
            Nutrition(300.0, 30.0, 15.0, 6.0),
        )
        assertEquals(Nutrition(600.0, 60.0, 30.0, 12.0), list.sum())
    }
}
