package com.dicoding.nutrient.data

import com.dicoding.nutrient.data.model.response.register.ErrorRegisterResponse

sealed class Result<out R> private constructor(){
    data class Success<out T>(val data: T) : Result<T>()
    data class ErrorRegister(val error: ErrorRegisterResponse) : Result<Nothing>()
    data class ServerError(val serverError: String) : Result<Nothing>()
    data class Error(val emptyError: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}