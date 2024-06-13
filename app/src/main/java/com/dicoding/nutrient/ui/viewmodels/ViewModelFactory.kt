package com.dicoding.nutrient.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.nutrient.api.ApiService
import com.dicoding.nutrient.api.RetrofitClient
import com.dicoding.nutrient.data.preferences.UserPreference
import com.dicoding.nutrient.data.repository.AuthRepository
import com.dicoding.nutrient.data.repository.BmiRepository
import com.dicoding.nutrient.data.repository.FatsecretRepository
import com.dicoding.nutrient.data.repository.UserDataRepository
import com.dicoding.nutrient.di.Injection

class ViewModelFactory private constructor(
    private val instanceApiLaravel : ApiService,
    private val userPreferences : UserPreference
) : ViewModelProvider.NewInstanceFactory() {

    private val authRepository: AuthRepository = AuthRepository(instanceApiLaravel)
    private val userDataRepository: UserDataRepository = UserDataRepository(instanceApiLaravel)
    private val bmiRepository: BmiRepository = BmiRepository(instanceApiLaravel)
    private val fatsecretRepository = FatsecretRepository(instanceApiLaravel)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass){
            LoginViewModel::class.java -> LoginViewModel(userPreferences, authRepository) as T
            RegisterViewModel::class.java -> RegisterViewModel(authRepository) as T
            UserPreferencesViewModel::class.java -> UserPreferencesViewModel(userPreferences) as T
            LogoutViewModel::class.java -> LogoutViewModel(userPreferences, authRepository) as T
            UserStatusViewModel::class.java -> UserStatusViewModel(authRepository) as T
            AssestmentViewModel::class.java -> AssestmentViewModel(userDataRepository) as T
            ProfileViewModel::class.java -> ProfileViewModel(userDataRepository, userPreferences) as T
            BMIHistoryViewModel::class.java -> BMIHistoryViewModel(bmiRepository) as T
            FatsecretViewModel::class.java -> FatsecretViewModel(fatsecretRepository, userPreferences) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null){
                synchronized(ViewModelFactory::class.java){
                    INSTANCE = ViewModelFactory(
                        RetrofitClient.instanceApiLaravel,
                        Injection.provideUserPreference(application)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}