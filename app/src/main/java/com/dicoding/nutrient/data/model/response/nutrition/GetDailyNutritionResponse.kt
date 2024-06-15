package com.dicoding.nutrient.data.model.response.nutrition

data class GetDailyNutritionResponse(
    val status: Int,
    val data: DataDailyNutrition
)

data class DataDailyNutrition(
    val id: Int,
    val max_calories: String,
    val daily_calories: String,
    val daily_sugar: String,
    val daily_fat: String,
    val daily_protein: String,
    val daily_carbohydrate: String,
)
