package com.mobilesw.diet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mobilesw.diet.data.local.entity.MealEntity
import com.mobilesw.diet.data.local.entity.MealItemEntity
import com.mobilesw.diet.data.local.entity.MealWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Insert
    suspend fun insertMeal(meal: MealEntity): Long

    @Insert
    suspend fun insertItems(items: List<MealItemEntity>)

    /** 한 끼와 음식 항목들을 한 번에 저장하고, 새 끼니 id를 돌려준다. */
    @Transaction
    suspend fun insertMealWithItems(meal: MealEntity, items: List<MealItemEntity>): Long {
        val mealId = insertMeal(meal)
        insertItems(items.map { it.copy(mealId = mealId) })
        return mealId
    }

    /** [startMillis] 이상 [endMillis] 미만에 먹은 끼니 (시간순). */
    @Transaction
    @Query("SELECT * FROM meals WHERE eatenAt >= :startMillis AND eatenAt < :endMillis ORDER BY eatenAt")
    fun observeMealsBetween(startMillis: Long, endMillis: Long): Flow<List<MealWithItems>>

    @Transaction
    @Query("SELECT * FROM meals WHERE id = :mealId")
    fun observeMeal(mealId: Long): Flow<MealWithItems?>

    /** 음식 항목은 외래키 CASCADE로 함께 지워진다. */
    @Query("DELETE FROM meals WHERE id = :mealId")
    suspend fun deleteMeal(mealId: Long)
}
