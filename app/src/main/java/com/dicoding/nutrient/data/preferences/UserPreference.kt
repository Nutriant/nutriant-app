package com.dicoding.nutrient.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStoreUser : DataStore<Preferences> by preferencesDataStore(name = "user")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    private val TOKEN_KEY = stringPreferencesKey("token")
    private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    private val EMAIL = stringPreferencesKey("email")
    private val USERNAME = stringPreferencesKey("username")
    private val FATSECRET_TOKEN = stringPreferencesKey("fatsecret_token")

    suspend fun setTokenFatsecret(token: String){
        dataStore.edit { preferences ->
            preferences[FATSECRET_TOKEN] = token
        }
    }

    fun getTokenFatsecret(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[FATSECRET_TOKEN] ?: ""
        }
    }

    suspend fun setTokenValue(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    fun getTokenValue(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    }

    fun getUsername(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USERNAME] ?: ""
        }
    }

    fun getUserLoginStatus(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN_KEY] ?: false
        }
    }

    suspend fun setDataUser(email: String, username: String){
        dataStore.edit { preferences ->
            preferences[EMAIL] = email
            preferences[USERNAME] = username
        }
    }

    suspend fun setUsernameUser(username: String){
        dataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    suspend fun setUserLoginStatus(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = isLoggedIn
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}