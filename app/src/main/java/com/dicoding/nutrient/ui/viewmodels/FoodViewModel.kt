package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.foods.ErrorPostFoodResponse
import com.dicoding.nutrient.data.model.response.foods.GetFoodResponse
import com.dicoding.nutrient.data.model.response.foods.PostFoodResponse
import com.dicoding.nutrient.data.repository.FoodRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File

class FoodViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    val resultPostFood = MediatorLiveData<Result<PostFoodResponse>>()

    fun resultPostFood(
        token: String,
        name: String,
        calories: Int,
        sugar: Int,
        fat: Int,
        protein: Int,
        carbohydrate: Int,
        image: File?
    ): LiveData<Result<PostFoodResponse>> {
        viewModelScope.launch {
            resultPostFood.value = Result.Loading
            try {
                val response = foodRepository.postFood(token, name, calories, sugar, fat, protein, carbohydrate, image)
                resultPostFood.value = Result.Success(response)
            } catch (e: HttpException){
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, ErrorPostFoodResponse::class.java)
                resultPostFood.value = Result.ErrorPostFood(errorBody)
            } catch (e: Exception){
                resultPostFood.value = Result.ServerError(e.message.toString())
            }
        }

        return resultPostFood
    }

    private val _resultGetFood = MutableLiveData<Result<GetFoodResponse>>()
    val resultGetFood: LiveData<Result<GetFoodResponse>> get() = _resultGetFood

    fun getFoods(token: String){
        if (_resultGetFood.value == null){
            viewModelScope.launch {
                _resultGetFood.value = Result.Loading

                try {
                    val response = foodRepository.getFoods(token)
                    _resultGetFood.value = Result.Success(response)
                } catch (e: Exception){
                    _resultGetFood.value = Result.ServerError(e.message.toString())
                }
            }
        }
    }
}