package com.mobilesw.diet.data.seed

import com.mobilesw.diet.data.local.entity.FoodEntity

/**
 * `assets/foods.csv`를 [FoodEntity] 목록으로 바꾼다.
 *
 * 형식 (첫 줄은 헤더, `#`으로 시작하는 줄과 빈 줄은 무시):
 * ```
 * id,name,category,kcal,carbs,protein,fat
 * 1,쌀밥,밥류,143,31.7,2.5,0.3
 * ```
 * 영양소는 모두 100g 기준이다. 음식 이름에는 쉼표를 쓰지 않는다.
 */
object FoodCsvParser {
    private const val COLUMNS = 7

    fun parse(csv: String): List<FoodEntity> =
        csv.lineSequence()
            .map { it.trim() }
            .filter { it.isNotEmpty() && !it.startsWith("#") }
            .drop(1) // header
            .mapIndexed { index, line -> parseLine(line, index + 1) }
            .toList()

    private fun parseLine(line: String, lineNumber: Int): FoodEntity {
        val cols = line.split(',').map { it.trim() }
        require(cols.size == COLUMNS) { "foods.csv ${lineNumber}번째 데이터 줄: 열이 ${cols.size}개 (필요: $COLUMNS)" }
        fun num(i: Int) = cols[i].toDoubleOrNull()
            ?: throw IllegalArgumentException("foods.csv ${lineNumber}번째 데이터 줄: 숫자가 아님 '${cols[i]}'")
        return FoodEntity(
            id = cols[0].toLongOrNull()
                ?: throw IllegalArgumentException("foods.csv ${lineNumber}번째 데이터 줄: id가 숫자가 아님 '${cols[0]}'"),
            name = cols[1],
            category = cols[2],
            kcalPer100g = num(3),
            carbsPer100g = num(4),
            proteinPer100g = num(5),
            fatPer100g = num(6),
        )
    }
}
