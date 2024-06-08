package com.dicoding.nutrient.data.repository

import com.dicoding.nutrient.api.ApiService
import com.dicoding.nutrient.data.model.response.assestment.AssestmentResponse
import com.dicoding.nutrient.data.model.response.myprofile.MyProfileResponse

class UserDataRepository(private val apiService: ApiService) {

    suspend fun fillAssestment(token : String, height: Double, weight: Double) : AssestmentResponse{
        return apiService.fillAssestment("Bearer $token", weight, height)
    }

    suspend fun getMyProfile(token: String) : MyProfileResponse {
        return apiService.getMyProfile("Bearer $token")
    }

}