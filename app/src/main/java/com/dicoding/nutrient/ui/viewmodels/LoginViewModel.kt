package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.dicoding.nutrient.api.ApiService
import com.dicoding.nutrient.data.preferences.UserPreference
import com.dicoding.nutrient.data.repository.AuthRepository

class LoginViewModel(
    private val apiService : ApiService,
    private val userPreferences : UserPreference,
    private val authRepository: AuthRepository
) : ViewModel() {

}