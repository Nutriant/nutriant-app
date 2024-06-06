package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.nutrient.data.preferences.UserPreference

class MainViewModel(private val userPreference: UserPreference) : ViewModel() {
    fun getUserLoginStatus() : LiveData<Boolean> {
        return userPreference.getUserLoginStatus().asLiveData()
    }
}