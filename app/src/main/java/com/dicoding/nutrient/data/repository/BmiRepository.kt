package com.dicoding.nutrient.data.repository

import com.dicoding.nutrient.api.ApiService
import com.dicoding.nutrient.data.model.response.bmi.GetBmisHistoryResponse

class BmiRepository(private val apiService: ApiService) {

    suspend fun getBmisHistory(token: String) : GetBmisHistoryResponse {
        return apiService.getBmisHistory("Bearer $token")
    }

}