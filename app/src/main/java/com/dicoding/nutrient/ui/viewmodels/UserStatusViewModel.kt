package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.model.response.userstatus.UserStatusResponse
import com.dicoding.nutrient.data.repository.AuthRepository
import com.dicoding.nutrient.data.Result
import kotlinx.coroutines.launch

class UserStatusViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val result = MediatorLiveData<Result<UserStatusResponse>>()

    fun userStatus(token: String): LiveData<Result<UserStatusResponse>> {
        viewModelScope.launch {
            result.value = Result.Loading
            try {
                val response = authRepository.userStatus(token)
                result.value = Result.Success(response)
            } catch (e: Exception){
                result.value = Result.ServerError(e.message.toString())
            }
        }

        return result
    }
}