package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.model.response.userstatus.UserStatusResponse
import com.dicoding.nutrient.data.repository.AuthRepository
import com.dicoding.nutrient.data.Result
import kotlinx.coroutines.launch

class UserStatusViewModel(private val authRepository: AuthRepository) : ViewModel() {

//    private val result = MediatorLiveData<Result<UserStatusResponse>>()
    private val _result = MutableLiveData<Result<UserStatusResponse>>()
    val result: LiveData<Result<UserStatusResponse>> get() = _result

    fun userStatus(token: String) {
        if (_result.value == null){
            viewModelScope.launch {
                _result.value = Result.Loading
                try {
                    val response = authRepository.userStatus(token)
                    _result.value = Result.Success(response)
                } catch (e: Exception){
                    _result.value = Result.ServerError(e.message.toString())
                }
            }
        }
    }
}