package com.dicoding.nutrient.data.model.response.foods

data class ErrorPostFoodResponse(
    val status: Int,
    val message: MessageErrorPostFood
)

data class MessageErrorPostFood(
    val name: List<String> = emptyList(),
    val calories: List<String> = emptyList(),
    val sugar: List<String> = emptyList(),
    val fat: List<String> = emptyList(),
    val protein: List<String> = emptyList(),
    val carbohydrate: List<String> = emptyList()
)
