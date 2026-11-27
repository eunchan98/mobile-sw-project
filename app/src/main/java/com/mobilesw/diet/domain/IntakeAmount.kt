package com.mobilesw.diet.domain

/**
 * 섭취량(g) 입력 검사.
 * 허용: 1g 이상 2000g 이하, 소수점은 한 자리까지 (예: "150", "72.5").
 */
object IntakeAmount {
    const val MIN_GRAMS = 1.0
    const val MAX_GRAMS = 2000.0

    private val pattern = Regex("""\d+(\.\d)?""")

    /** 올바른 입력이면 그램 값을, 아니면 null을 돌려준다. */
    fun parse(text: String): Double? {
        val trimmed = text.trim()
        if (!pattern.matches(trimmed)) return null
        val grams = trimmed.toDoubleOrNull() ?: return null
        return grams.takeIf { it in MIN_GRAMS..MAX_GRAMS }
    }
}
