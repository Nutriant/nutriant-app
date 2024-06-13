package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.nutrient.data.preferences.UserPreference

class UserPreferencesViewModel(private val userPreference: UserPreference) : ViewModel() {
    fun getUserLoginStatus() : LiveData<Boolean> {
        return userPreference.getUserLoginStatus().asLiveData()
    }

    fun getTokenFatsecret() : LiveData<String> {
        return userPreference.getTokenFatsecret().asLiveData()
    }

    fun getTokenValue() : LiveData<String> {
        return userPreference.getTokenValue().asLiveData()
    }

    fun getUsername() : LiveData<String> {
        return userPreference.getUsername().asLiveData()
    }
}