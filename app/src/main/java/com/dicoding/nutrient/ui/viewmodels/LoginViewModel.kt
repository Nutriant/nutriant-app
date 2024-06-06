package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.api.ApiService
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.login.ErrorLoginResponse
import com.dicoding.nutrient.data.model.response.login.LoginResponse
import com.dicoding.nutrient.data.preferences.UserPreference
import com.dicoding.nutrient.data.repository.AuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(
    private val userPreferences : UserPreference,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val result = MediatorLiveData<Result<LoginResponse>>()

    fun login(email: String, password: String) : LiveData<Result<LoginResponse>> {
        viewModelScope.launch {
            result.value = Result.Loading

            try {
                val response = authRepository.login(email, password)
                userPreferences.setTokenValue(response.token)
                userPreferences.setUserLoginStatus(true)
                result.value = Result.Success(response)
            } catch (e: HttpException){
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, ErrorLoginResponse::class.java)
                result.value = Result.ErrorLogin(errorBody)
            } catch (e: Exception){
                e.printStackTrace()
                result.value = Result.ServerError(e.message.toString())
            }
        }
        return result
    }
}