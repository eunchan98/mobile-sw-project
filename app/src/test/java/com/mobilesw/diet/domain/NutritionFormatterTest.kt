package com.mobilesw.diet.domain

import com.mobilesw.diet.domain.model.Nutrition
import org.junit.Assert.assertEquals
import org.junit.Test

class NutritionFormatterTest {

    @Test
    fun 열량은_소수점_첫째자리에서_반올림하고_천단위_쉼표를_붙인다() {
        assertEquals("720", NutritionFormatter.kcal(719.5))
        assertEquals("719", NutritionFormatter.kcal(719.49))
        assertEquals("1,140", NutritionFormatter.kcal(1140.2))
    }

    @Test
    fun 그램은_소수점_둘째자리에서_반올림하고_불필요한_0은_뺀다() {
        assertEquals("22.1g", NutritionFormatter.grams(22.05))
        assertEquals("12g", NutritionFormatter.grams(12.0))
        assertEquals("0.3g", NutritionFormatter.grams(0.3))
        assertEquals("1,320g", NutritionFormatter.grams(1320.0))
    }

    @Test
    fun 계산_과정의_부동소수점_오차가_있어도_올바르게_반올림한다() {
        // 14.7 * 1.5 는 Double로 22.049999999999997 이 된다 (제육볶음 150g의 단백질)
        assertEquals("22.1g", NutritionFormatter.grams(14.7 * 1.5))
        // 143 * 2.1 = 300.29999999999995 (쌀밥 210g의 열량)
        assertEquals("300", NutritionFormatter.kcal(143.0 * 2.1))
        assertEquals("0.6g", NutritionFormatter.grams(0.3 * 2.1))
    }

    @Test
    fun 탄단지_요약_문자열을_만든다() {
        val n = Nutrition(kcal = 720.0, carbs = 98.0, protein = 34.04, fat = 21.0)
        assertEquals("탄 98g · 단 34g · 지 21g", NutritionFormatter.macros(n))
    }
}
