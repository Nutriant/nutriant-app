package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.myprofile.MyProfileResponse
import com.dicoding.nutrient.data.repository.UserDataRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val userDataRepository: UserDataRepository) : ViewModel() {

    private val result = MediatorLiveData<Result<MyProfileResponse>>()

    fun getMyProfile(token: String) : LiveData<Result<MyProfileResponse>> {
        viewModelScope.launch {
            result.value = Result.Loading

            try {
                val response = userDataRepository.getMyProfile(token)
                result.value = Result.Success(response)
            } catch (e: Exception){
                result.value = Result.ServerError(e.message.toString())
            }
        }

        return result
    }
}