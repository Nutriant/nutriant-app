package com.dicoding.nutrient.data.model.response.foods

data class GetFoodResponse(
    val status: Int,
    val data: ArrayList<DataFoods>
)

data class DataFoods(
    val id: Int,
    val name: String,
    val image: String,
    val calories: String,
    val sugar: String,
    val fat: String,
    val protein: String,
    val carbohydrate: String,
    val created_at: String
)
