package com.mobilesw.diet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobilesw.diet.data.local.entity.FoodEntity

@Dao
interface FoodDao {
    /** 이름에 [query]가 포함된 음식 (자동완성용). */
    @Query("SELECT * FROM foods WHERE name LIKE '%' || :query || '%' ORDER BY name LIMIT :limit")
    suspend fun search(query: String, limit: Int = 20): List<FoodEntity>

    @Query("SELECT * FROM foods WHERE id = :id")
    suspend fun getById(id: Long): FoodEntity?

    @Query("SELECT * FROM foods ORDER BY name")
    suspend fun getAll(): List<FoodEntity>

    @Query("SELECT COUNT(*) FROM foods")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<FoodEntity>)
}
