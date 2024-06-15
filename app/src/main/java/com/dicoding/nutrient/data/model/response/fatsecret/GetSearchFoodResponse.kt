package com.dicoding.nutrient.data.model.response.fatsecret

data class GetSearchFoodResponse(
    val foods: Food
)

data class Food(
    val food: ArrayList<AllFood>
)

data class AllFood(
    val brand_name: String,
    val food_description: String,
    val food_id: Long,
    val food_name: String,
    val food_type: String,
    val food_url: String
)
