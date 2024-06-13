package com.dicoding.nutrient.data.model.response.setting

data class ErrorChangePasswordResponse(
    val status: Int,
    val message: MessageErrorChangePassword
)

data class MessageErrorChangePassword(
    val password: List<String> = emptyList()
)
