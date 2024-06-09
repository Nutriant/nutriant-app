package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.myprofile.MyProfileResponse
import com.dicoding.nutrient.data.repository.UserDataRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val userDataRepository: UserDataRepository) : ViewModel() {

    private val _userData = MutableLiveData<Result<MyProfileResponse>>()
    val userData: LiveData<Result<MyProfileResponse>> get() = _userData

    fun getMyProfile(token: String) {
        if (_userData.value == null) {
            viewModelScope.launch {
                _userData.value = Result.Loading
                try {
                    val response = userDataRepository.getMyProfile(token)
                    _userData.value = Result.Success(response)
                } catch (e: Exception) {
                    _userData.value = Result.ServerError(e.message.toString())
                }
            }
        }
    }
}