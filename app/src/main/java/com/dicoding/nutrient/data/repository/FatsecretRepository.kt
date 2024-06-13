package com.dicoding.nutrient.data.repository

import com.dicoding.nutrient.api.ApiService
import com.dicoding.nutrient.data.model.response.fatsecret.GetSearchFoodResponse
import com.dicoding.nutrient.data.model.response.fatsecret.GetTokenFatsecretResponse

class FatsecretRepository(
    private val apiService: ApiService
) {

    suspend fun searchProduct(token: String, search: String) : GetSearchFoodResponse {
        return apiService.searchProduct(token, search)
    }

    suspend fun getTokenFatsecret() : GetTokenFatsecretResponse {
        return apiService.getTokenFatsecret()
    }
}