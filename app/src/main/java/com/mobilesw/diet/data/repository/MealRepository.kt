package com.mobilesw.diet.data.repository

import com.mobilesw.diet.data.local.dao.MealDao
import com.mobilesw.diet.data.local.entity.MealEntity
import com.mobilesw.diet.data.local.entity.MealItemEntity
import com.mobilesw.diet.data.local.entity.MealWithItems
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId

/** 끼니 기록 저장·조회. 날짜 범위 계산은 기기 시간대를 기준으로 한다. */
class MealRepository(
    private val mealDao: MealDao,
    private val zone: ZoneId = ZoneId.systemDefault(),
) {
    /** 한 끼를 저장하고 새 id를 돌려준다. [items]의 mealId는 무시된다. */
    suspend fun saveMeal(meal: MealEntity, items: List<MealItemEntity>): Long =
        mealDao.insertMealWithItems(meal, items)

    suspend fun deleteMeal(mealId: Long) = mealDao.deleteMeal(mealId)

    fun observeMeal(mealId: Long): Flow<MealWithItems?> = mealDao.observeMeal(mealId)

    /** [from]부터 [toInclusive]까지(양 끝 포함) 날짜의 끼니. */
    fun observeMealsBetween(from: LocalDate, toInclusive: LocalDate): Flow<List<MealWithItems>> =
        mealDao.observeMealsBetween(
            startMillis = from.atStartOfDay(zone).toInstant().toEpochMilli(),
            endMillis = toInclusive.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli(),
        )

    /** 하루치 끼니. */
    fun observeMealsOn(date: LocalDate): Flow<List<MealWithItems>> = observeMealsBetween(date, date)
}
