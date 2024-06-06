package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.api.ApiService
import com.dicoding.nutrient.data.model.response.register.RegisterResponse
import com.dicoding.nutrient.data.repository.AuthRepository
import kotlinx.coroutines.launch
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.register.ErrorRegisterResponse
import com.google.gson.Gson
import retrofit2.HttpException

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val result = MediatorLiveData<Result<RegisterResponse>>()

    fun register(
        username: String,
        email: String,
        password: String,
        password_confirm: String,
        birthdate: String,
        gender: Int,
    ): LiveData<Result<RegisterResponse>> {
        viewModelScope.launch {
            
            result.value = Result.Loading

            try {
                val response = authRepository.register(
                    username,
                    email,
                    password,
                    password_confirm,
                    birthdate,
                    gender
                )
                if (response.status == 201){
                    result.value = Result.Success(response)
                }
            } catch (e: HttpException){
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, ErrorRegisterResponse::class.java)
                result.value = Result.ErrorRegister(errorBody)
            } catch (e: Exception){
                e.printStackTrace()
                result.value = Result.ServerError(e.message.toString())
            }
        }
        return result
    }
}