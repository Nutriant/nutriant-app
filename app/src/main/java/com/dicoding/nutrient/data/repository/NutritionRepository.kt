package com.dicoding.nutrient.data.repository

import com.dicoding.nutrient.api.ApiService
import com.dicoding.nutrient.data.model.response.nutrition.GetDailyNutritionResponse

class NutritionRepository(private val apiService: ApiService) {

    suspend fun getDailyNutrition(token: String) : GetDailyNutritionResponse {
        return apiService.getDailyNutrition("Bearer $token")
    }
}