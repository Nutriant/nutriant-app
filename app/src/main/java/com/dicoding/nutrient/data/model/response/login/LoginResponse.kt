package com.dicoding.nutrient.data.model.response.login

data class LoginResponse(
    val type: String,
    val token: String,
    val data: LoginResponseData
)

data class LoginResponseData(
    val username: String,
    val email: String
)
