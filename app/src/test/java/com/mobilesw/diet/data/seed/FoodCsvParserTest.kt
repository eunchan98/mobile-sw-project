package com.mobilesw.diet.data.seed

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class FoodCsvParserTest {

    @Test
    fun 헤더와_주석과_빈줄을_건너뛰고_음식을_읽는다() {
        val csv = """
            # 주석
            id,name,category,kcal,carbs,protein,fat

            1,쌀밥,밥류,143,31.7,2.5,0.3
            2,김치,김치류,30,5.0,2.0,0.5
        """.trimIndent()

        val foods = FoodCsvParser.parse(csv)

        assertEquals(2, foods.size)
        val rice = foods[0]
        assertEquals(1L, rice.id)
        assertEquals("쌀밥", rice.name)
        assertEquals("밥류", rice.category)
        assertEquals(143.0, rice.kcalPer100g, 0.0)
        assertEquals(31.7, rice.carbsPer100g, 0.0)
        assertEquals(2.5, rice.proteinPer100g, 0.0)
        assertEquals(0.3, rice.fatPer100g, 0.0)
    }

    @Test
    fun 열_개수가_맞지_않으면_예외를_던진다() {
        val csv = "id,name,category,kcal,carbs,protein,fat\n1,쌀밥,밥류,143"
        val e = assertThrows(IllegalArgumentException::class.java) { FoodCsvParser.parse(csv) }
        assertTrue(e.message!!.contains("열이 4개"))
    }

    @Test
    fun 영양소가_숫자가_아니면_예외를_던진다() {
        val csv = "id,name,category,kcal,carbs,protein,fat\n1,쌀밥,밥류,abc,31.7,2.5,0.3"
        assertThrows(IllegalArgumentException::class.java) { FoodCsvParser.parse(csv) }
    }
}
