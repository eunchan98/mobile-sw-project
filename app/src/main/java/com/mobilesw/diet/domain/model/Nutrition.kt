package com.mobilesw.diet.domain.model

/**
 * 열량과 3대 영양소 묶음. 음식 하나, 한 끼, 하루, 한 달 합계를 모두 이 타입으로 다룬다.
 *
 * @property kcal 열량 (kcal)
 * @property carbs 탄수화물 (g)
 * @property protein 단백질 (g)
 * @property fat 지방 (g)
 */
data class Nutrition(
    val kcal: Double,
    val carbs: Double,
    val protein: Double,
    val fat: Double,
) {
    operator fun plus(other: Nutrition) = Nutrition(
        kcal = kcal + other.kcal,
        carbs = carbs + other.carbs,
        protein = protein + other.protein,
        fat = fat + other.fat,
    )

    companion object {
        val ZERO = Nutrition(0.0, 0.0, 0.0, 0.0)
    }
}

/** 여러 영양 정보의 합계. 비어 있으면 [Nutrition.ZERO]. */
fun Iterable<Nutrition>.sum(): Nutrition = fold(Nutrition.ZERO) { acc, n -> acc + n }
