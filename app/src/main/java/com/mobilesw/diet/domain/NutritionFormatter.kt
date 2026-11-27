package com.mobilesw.diet.domain

import com.mobilesw.diet.domain.model.Nutrition
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * 영양 정보를 화면에 보여줄 문자열로 바꾼다.
 * - 열량: 정수로 반올림, 천 단위 쉼표 (예: 1,140)
 * - 그램: 소수점 한 자리로 반올림, `.0`은 생략 (예: 22.1g, 12g)
 *
 * 계산 과정의 부동소수점 오차(예: 14.7 × 1.5 = 22.049999999999997)로 반올림이 틀어지지 않도록
 * 먼저 소수점 9자리에서 오차를 정리한 뒤 원하는 자리에서 반올림(HALF_UP)한다.
 */
object NutritionFormatter {
    private val symbols = DecimalFormatSymbols(Locale.KOREA)
    private val kcalFormat = DecimalFormat("#,##0", symbols)
    private val gramFormat = DecimalFormat("#,##0.#", symbols)

    private fun round(value: Double, scale: Int): BigDecimal =
        BigDecimal(value.toString())
            .setScale(9, RoundingMode.HALF_UP)
            .setScale(scale, RoundingMode.HALF_UP)

    /** 예: 719.5 → "720", 1140.2 → "1,140" */
    fun kcal(value: Double): String = kcalFormat.format(round(value, 0))

    /** 예: 22.05 → "22.1g", 12.0 → "12g" */
    fun grams(value: Double): String = gramFormat.format(round(value, 1)) + "g"

    /** 예: "탄 98g · 단 34g · 지 21g" */
    fun macros(n: Nutrition): String =
        "탄 ${grams(n.carbs)} · 단 ${grams(n.protein)} · 지 ${grams(n.fat)}"
}
