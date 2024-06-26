package com.dicoding.nutrient.data.repository

import com.dicoding.nutrient.api.ApiService
import com.dicoding.nutrient.data.model.response.login.LoginResponse
import com.dicoding.nutrient.data.model.response.register.RegisterResponse
import com.dicoding.nutrient.data.model.response.setting.ChangePasswordResponse
import com.dicoding.nutrient.data.model.response.userstatus.UserStatusResponse

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

    suspend fun login(email: String, password: String) : LoginResponse {
        return apiService.login(email, password)
    }

    suspend fun logout(token: String) : Int {
        return apiService.logout("Bearer $token")
    }

    suspend fun userStatus(token: String) : UserStatusResponse {
        return apiService.userStatus("Bearer $token")
    }

    suspend fun changePassword(
        token: String,
        password: String,
        password_confirm: String
    ) : ChangePasswordResponse {
        return apiService.changePassword("Bearer $token", password, password_confirm)
    }
}