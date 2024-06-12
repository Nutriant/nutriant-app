package com.dicoding.nutrient.data.model.response.assestment

data class ErrorAssestmentResponse(
    val message: String,
    val errors: ErrorAssestmentResponseErrors
)

data class ErrorAssestmentResponseErrors(
    val weight: List<String> = emptyList(),
    val height: List<String> = emptyList()
)