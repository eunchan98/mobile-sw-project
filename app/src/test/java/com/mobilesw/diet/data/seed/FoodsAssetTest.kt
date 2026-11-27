package com.mobilesw.diet.data.seed

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/** 실제 앱에 들어가는 assets/foods.csv가 형식에 맞는지 검사한다. */
class FoodsAssetTest {

    private val foods = FoodCsvParser.parse(File("src/main/assets/foods.csv").readText())

    @Test
    fun 음식_데이터는_100종이고_id와_이름이_겹치지_않는다() {
        assertEquals(100, foods.size)
        assertEquals(foods.size, foods.map { it.id }.toSet().size)
        assertEquals(foods.size, foods.map { it.name }.toSet().size)
    }

    @Test
    fun 모든_음식의_영양소는_0이상이고_100g당_탄단지_합이_100g을_넘지_않는다() {
        foods.forEach { food ->
            val n = food.per100g
            assertTrue("${food.name} 음수 값", n.kcal >= 0 && n.carbs >= 0 && n.protein >= 0 && n.fat >= 0)
            assertTrue("${food.name} 탄단지 합 초과", n.carbs + n.protein + n.fat <= 100.0)
        }
    }
}
