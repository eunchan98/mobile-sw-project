package com.mobilesw.diet.data.repository

import com.mobilesw.diet.data.local.dao.FoodDao
import com.mobilesw.diet.data.local.entity.FoodEntity
import com.mobilesw.diet.data.seed.FoodCsvParser

/**
 * 음식 데이터 접근.
 *
 * @param loadSeedCsv `assets/foods.csv` 내용을 읽어 오는 함수 (테스트에서 바꿔 끼울 수 있게 주입).
 */
class FoodRepository(
    private val foodDao: FoodDao,
    private val loadSeedCsv: () -> String,
) {
    /** DB가 비어 있으면 foods.csv로 음식 데이터를 채운다. 앱 시작 시 한 번 호출된다. */
    suspend fun seedIfEmpty() {
        if (foodDao.count() == 0) {
            foodDao.insertAll(FoodCsvParser.parse(loadSeedCsv()))
        }
    }

    suspend fun search(query: String, limit: Int = 20): List<FoodEntity> =
        if (query.isBlank()) emptyList() else foodDao.search(query.trim(), limit)

    suspend fun getById(id: Long): FoodEntity? = foodDao.getById(id)

    suspend fun getAll(): List<FoodEntity> = foodDao.getAll()
}
