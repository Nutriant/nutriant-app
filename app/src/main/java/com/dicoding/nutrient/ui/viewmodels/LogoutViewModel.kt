package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.preferences.UserPreference
import com.dicoding.nutrient.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LogoutViewModel(
    private val userPreference: UserPreference,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val result = MediatorLiveData<Result<Int>>()

    fun logout(token: String) : LiveData<Result<Int>> {
        viewModelScope.launch {
            result.value = Result.Loading

            try {
                val response = authRepository.logout(token)
                userPreference.setUserLoginStatus(false)
                result.value = Result.Success(response)
            } catch (e: Exception){
                result.value = Result.Error(e.message.toString())
            }
        }
        return result
    }
}