package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.setting.ChangePasswordResponse
import com.dicoding.nutrient.data.model.response.setting.ErrorChangePasswordResponse
import com.dicoding.nutrient.data.repository.AuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ChangePasswordViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val result = MediatorLiveData<Result<ChangePasswordResponse>>()

    fun changePassword(
        token: String,
        password: String,
        password_confirm: String
    ) : LiveData<Result<ChangePasswordResponse>> {
        viewModelScope.launch {
            result.value = Result.Loading
            try {
                val response = authRepository.changePassword(token, password, password_confirm)
                result.value = Result.Success(response)
            } catch (e: HttpException){
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, ErrorChangePasswordResponse::class.java)
                result.value = Result.ErrorChangePassword(errorBody)
            } catch (e: Exception){
                result.value = Result.ServerError(e.message.toString())
            }
        }
        return result
    }
}