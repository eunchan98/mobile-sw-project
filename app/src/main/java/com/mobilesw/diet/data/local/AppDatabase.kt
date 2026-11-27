package com.mobilesw.diet.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mobilesw.diet.data.local.dao.FoodDao
import com.mobilesw.diet.data.local.dao.MealDao
import com.mobilesw.diet.data.local.entity.FoodEntity
import com.mobilesw.diet.data.local.entity.MealEntity
import com.mobilesw.diet.data.local.entity.MealItemEntity
import com.mobilesw.diet.domain.model.MealType

class Converters {
    @TypeConverter
    fun fromMealType(type: MealType): String = type.name

    @TypeConverter
    fun toMealType(value: String): MealType = MealType.valueOf(value)
}

@Database(
    entities = [FoodEntity::class, MealEntity::class, MealItemEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "diet.db")
                    .build()
                    .also { instance = it }
            }
    }
}
