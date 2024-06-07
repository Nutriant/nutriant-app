package com.dicoding.nutrient.data.model.response.login

data class LoginResponse(
    val type: String,
    val token: String,
    val data: LoginResponseData,
    val new_user: Int
)

data class LoginResponseData(
    val username: String,
    val email: String
)
