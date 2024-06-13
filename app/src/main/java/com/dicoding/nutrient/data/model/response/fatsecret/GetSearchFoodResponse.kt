package com.dicoding.nutrient.data.model.response.fatsecret

data class GetSearchFoodResponse(
    val foods: Food
)

data class Food(
    val foods: ArrayList<AllFood>
)

data class AllFood(
    val brand_name: String,
    val food_descripttion: String,
    val food_id: Long,
    val food_name: String,
    val food_type: String
)
