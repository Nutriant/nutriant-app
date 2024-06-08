package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.assestment.AssestmentResponse
import com.dicoding.nutrient.data.model.response.assestment.ErrorAssestmentResponse
import com.dicoding.nutrient.data.model.response.login.LoginResponse
import com.dicoding.nutrient.data.repository.UserDataRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AssestmentViewModel(private val userDataRepository: UserDataRepository) : ViewModel() {

    private val result = MediatorLiveData<Result<AssestmentResponse>>()

    fun fillAssestment(token: String, height: Double, weight: Double) : LiveData<Result<AssestmentResponse>> {

        viewModelScope.launch {
            result.value = Result.Loading
            try {
                val response = userDataRepository.fillAssestment(token, height, weight)
                result.value = Result.Success(response)
            } catch (e: HttpException){
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, ErrorAssestmentResponse::class.java)
                result.value = Result.ErrorAssestment(errorBody)
            } catch (e: Exception){
                e.printStackTrace()
                result.value = Result.ServerError(e.message.toString())
            }
        }
        return result
    }
}