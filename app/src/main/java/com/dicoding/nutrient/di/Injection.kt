package com.dicoding.nutrient.di

import android.content.Context
import com.dicoding.nutrient.data.preferences.UserPreference
import com.dicoding.nutrient.data.preferences.dataStoreUser

object Injection {
    fun provideUserPreference(context: Context) : UserPreference {
        return UserPreference.getInstance(context.dataStoreUser)
    }
}