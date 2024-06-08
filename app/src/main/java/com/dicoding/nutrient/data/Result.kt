package com.dicoding.nutrient.data

import com.dicoding.nutrient.data.model.response.assestment.ErrorAssestmentResponse
import com.dicoding.nutrient.data.model.response.login.ErrorLoginResponse
import com.dicoding.nutrient.data.model.response.register.ErrorRegisterResponse

sealed class Result<out R> private constructor(){
    data class Success<out T>(val data: T) : Result<T>()
    data class ErrorRegister(val error: ErrorRegisterResponse) : Result<Nothing>()
    data class ServerError(val serverError: String) : Result<Nothing>()
    data class ErrorLogin(val errorLogin: ErrorLoginResponse) : Result<Nothing>()
    data class Error(val errorMessage: String) : Result<Nothing>()
    data class ErrorAssestment(val error: ErrorAssestmentResponse) : Result<Nothing>()
    object Loading : Result<Nothing>()
}