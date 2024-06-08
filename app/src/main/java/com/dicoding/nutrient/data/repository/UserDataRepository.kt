package com.dicoding.nutrient.data.repository

import com.dicoding.nutrient.api.ApiService
import com.dicoding.nutrient.data.model.response.assestment.AssestmentResponse

class UserDataRepository(private val apiService: ApiService) {

    suspend fun fillAssestment(token : String, height: Double, weight: Double) : AssestmentResponse{
        return apiService.fillAssestment("Bearer $token", weight, height)
    }

}