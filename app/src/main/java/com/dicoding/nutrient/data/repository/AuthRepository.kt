package com.dicoding.nutrient.data.repository

import com.dicoding.nutrient.api.ApiService
import com.dicoding.nutrient.data.model.response.register.RegisterResponse

class AuthRepository(private val apiService: ApiService) {
    suspend fun register(
        username: String,
        email: String,
        password: String,
        password_confirm: String,
        birthdate: String,
        gender: Int,
    ) : RegisterResponse {
        return apiService.register(
            username,
            email,
            password,
            password_confirm,
            birthdate,
            gender
        )
    }
}