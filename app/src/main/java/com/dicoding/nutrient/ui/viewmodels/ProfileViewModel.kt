package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.myprofile.MyProfileResponse
import com.dicoding.nutrient.data.model.response.profiles.GetProfilesResponse
import com.dicoding.nutrient.data.model.response.profiles.UpdateProfileResponse
import com.dicoding.nutrient.data.preferences.UserPreference
import com.dicoding.nutrient.data.repository.UserDataRepository
import kotlinx.coroutines.launch
import retrofit2.http.Header
import java.io.File

class ProfileViewModel(
    private val userDataRepository: UserDataRepository,
    private val userPreferences: UserPreference
) : ViewModel() {

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

    val resultUpdateProfile = MediatorLiveData<Result<UpdateProfileResponse>>()

    fun updateProfile(
        token: String,
        username: String,
        birthdate: String,
        gender: Int,
        height: Int,
        weight: Int,
        image: File?,
        _method: String
    ) : LiveData<Result<UpdateProfileResponse>> {
        viewModelScope.launch {
            resultUpdateProfile.value = Result.Loading

            try {
                val response = userDataRepository.updateProfile(
                    token, username, birthdate, gender, height, weight, image, _method
                )
                userPreferences.setUsernameUser(username)
                resultUpdateProfile.value = Result.Success(response)
            } catch (e: Exception){
                resultUpdateProfile.value = Result.ServerError(e.message.toString())
            }
        }
        return resultUpdateProfile
    }

    private val _resultAllMyProfile = MutableLiveData<Result<GetProfilesResponse>>()
    val resultAllMyProfile: LiveData<Result<GetProfilesResponse>> get() = _resultAllMyProfile

    fun getAllMyProfile(token: String){
        if (_resultAllMyProfile.value == null){
            viewModelScope.launch {
                _resultAllMyProfile.value = Result.Loading

                try {
                    val response = userDataRepository.getAllMyProfile(token)
                    _resultAllMyProfile.value = Result.Success(response)
                } catch (e: Exception){
                    _resultAllMyProfile.value = Result.ServerError(e.message.toString())
                }
            }
        }
    }
}