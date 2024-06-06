package com.dicoding.nutrient.data.model.response.register

data class ErrorRegisterResponse(
    val status: Int,
    val message: ErrorRegisterMessage
)

data class ErrorRegisterMessage(
    val username: List<String> = emptyList(),
    val email: List<String> = emptyList(),
    val password: List<String> = emptyList(),
    val birthdate: List<String> = emptyList(),
    val password_confirmation: List<String> = emptyList(),
    val gender: List<String> = emptyList()
)