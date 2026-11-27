package com.mobilesw.diet.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.mobilesw.diet.domain.model.MealType
import com.mobilesw.diet.domain.model.Nutrition

/**
 * 음식 데이터베이스 (식약처 식품영양성분 DB에서 가져온 약 100종).
 * 영양소는 모두 100g 기준이다.
 */
@Entity(tableName = "foods", indices = [Index("name")])
data class FoodEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val category: String,
    val kcalPer100g: Double,
    val carbsPer100g: Double,
    val proteinPer100g: Double,
    val fatPer100g: Double,
) {
    /** 100g 기준 영양 정보. */
    val per100g: Nutrition
        get() = Nutrition(kcalPer100g, carbsPer100g, proteinPer100g, fatPer100g)
}

/** 한 끼 기록. 사진 1장 + 음식 여러 개([MealItemEntity])로 구성된다. */
@Entity(tableName = "meals", indices = [Index("eatenAt")])
data class MealEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    /** 식사 날짜와 시간 (epoch millis, 기기 시간대 기준으로 표시). */
    val eatenAt: Long,
    val mealType: MealType,
    /** 사진 URI 문자열. 사진이 없으면 null. */
    val photoUri: String? = null,
)

/** 한 끼에 포함된 음식 하나와 섭취량. */
@Entity(
    tableName = "meal_items",
    foreignKeys = [
        ForeignKey(
            entity = MealEntity::class,
            parentColumns = ["id"],
            childColumns = ["mealId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = FoodEntity::class,
            parentColumns = ["id"],
            childColumns = ["foodId"],
        ),
    ],
    indices = [Index("mealId"), Index("foodId")],
)
data class MealItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    /** 저장 시 [com.mobilesw.diet.data.local.dao.MealDao.insertMealWithItems]가 채운다. */
    val mealId: Long = 0,
    val foodId: Long,
    /** 섭취량 (g) */
    val grams: Double,
)

/** 음식 항목 + 음식 정보 (조회용). */
data class MealItemWithFood(
    @Embedded val item: MealItemEntity,
    @Relation(parentColumn = "foodId", entityColumn = "id")
    val food: FoodEntity,
)

/** 한 끼 + 포함된 음식들 (조회용). */
data class MealWithItems(
    @Embedded val meal: MealEntity,
    @Relation(entity = MealItemEntity::class, parentColumn = "id", entityColumn = "mealId")
    val items: List<MealItemWithFood>,
)
