package com.mobilesw.diet.domain

import com.mobilesw.diet.data.local.entity.FoodEntity
import com.mobilesw.diet.data.local.entity.MealItemWithFood
import com.mobilesw.diet.data.local.entity.MealWithItems
import com.mobilesw.diet.domain.model.Nutrition
import com.mobilesw.diet.domain.model.sum

/**
 * 섭취량에 따른 영양소 계산.
 *
 * 음식 DB의 값은 모두 100g 기준이므로 `100g당 값 × 섭취량(g) / 100`으로 계산한다.
 * 열량도 탄·단·지로 다시 계산하지 않고 식약처 DB의 에너지(kcal) 값을 그대로 비례 계산한다.
 * 반올림은 하지 않는다. 화면에 보여줄 때 [NutritionFormatter]로 반올림한다.
 */
object NutritionCalculator {

    /** 음식 하나를 [grams]만큼 먹었을 때의 영양소. [grams]는 0 이상이어야 한다. */
    fun forFood(food: FoodEntity, grams: Double): Nutrition {
        require(!grams.isNaN() && grams >= 0.0) { "섭취량은 0g 이상이어야 합니다: $grams" }
        val ratio = grams / 100.0
        return Nutrition(
            kcal = food.kcalPer100g * ratio,
            carbs = food.carbsPer100g * ratio,
            protein = food.proteinPer100g * ratio,
            fat = food.fatPer100g * ratio,
        )
    }

    /** 끼니에 들어간 음식 항목 하나의 영양소. */
    fun forItem(item: MealItemWithFood): Nutrition = forFood(item.food, item.item.grams)

    /** 한 끼의 영양소 합계. 음식이 없으면 [Nutrition.ZERO]. */
    fun forMeal(meal: MealWithItems): Nutrition = meal.items.map(::forItem).sum()

    /** 여러 끼니(하루, 한 달 등)의 영양소 합계. */
    fun forMeals(meals: List<MealWithItems>): Nutrition = meals.map(::forMeal).sum()
}
