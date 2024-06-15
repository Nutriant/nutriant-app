package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.nutrition.GetDailyNutritionResponse
import com.dicoding.nutrient.data.repository.NutritionRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NutritionViewModel(private val nutritionRepository: NutritionRepository) : ViewModel() {

    private val _resultGetDailyNutrition = MutableLiveData<Result<GetDailyNutritionResponse>>()
    val resultGetDailyNutrition: LiveData<Result<GetDailyNutritionResponse>> get() = _resultGetDailyNutrition

    fun getDailyNutrition(token: String){
        if (_resultGetDailyNutrition.value == null){
            viewModelScope.launch {
                _resultGetDailyNutrition.value = Result.Loading

                try {
                    val response = nutritionRepository.getDailyNutrition(token)
                    _resultGetDailyNutrition.value = Result.Success(response)
                }catch (e: Exception){
                    _resultGetDailyNutrition.value = Result.ServerError(e.message.toString())
                }
            }
        }
    }
}