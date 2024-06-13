package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.fatsecret.GetSearchFoodResponse
import com.dicoding.nutrient.data.model.response.fatsecret.GetTokenFatsecretResponse
import com.dicoding.nutrient.data.preferences.UserPreference
import com.dicoding.nutrient.data.repository.FatsecretRepository
import kotlinx.coroutines.launch

class FatsecretViewModel(
    private val fatsecretRepository: FatsecretRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    val resultSearch = MediatorLiveData<Result<GetSearchFoodResponse>>()

    fun searchProduct(token: String, search: String) : LiveData<Result<GetSearchFoodResponse>> {
        viewModelScope.launch {
            resultSearch.value = Result.Loading

            try {
                val response = fatsecretRepository.searchProduct(token, search)
                resultSearch.value = Result.Success(response)
            }catch (e: Exception){
                resultSearch.value = Result.ServerError(e.message.toString())
            }
        }
        return resultSearch
    }

    val resutlTokenFatsecret = MediatorLiveData<Result<GetTokenFatsecretResponse>>()

    fun getTokenFatsecret() : LiveData<Result<GetTokenFatsecretResponse>> {
        viewModelScope.launch {
            resutlTokenFatsecret.value = Result.Loading

            try {
                val response = fatsecretRepository.getTokenFatsecret()
                userPreference.setTokenFatsecret(response.access_token)
                resutlTokenFatsecret.value = Result.Success(response)
            } catch (e: Exception){
                resutlTokenFatsecret.value = Result.ServerError(e.message.toString())
            }
        }
        return resutlTokenFatsecret
    }
}