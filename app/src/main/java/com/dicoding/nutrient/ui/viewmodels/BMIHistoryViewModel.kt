package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.bmi.GetBmisHistoryResponse
import com.dicoding.nutrient.data.preferences.UserPreference
import com.dicoding.nutrient.data.repository.BmiRepository
import kotlinx.coroutines.launch

class BMIHistoryViewModel(
    private val bmiRepository: BmiRepository,
) : ViewModel() {

    private val _resultBmiHistory = MutableLiveData<Result<GetBmisHistoryResponse>>()
    val resultBmisHistory: LiveData<Result<GetBmisHistoryResponse>> get() = _resultBmiHistory

    fun getBmisHistory(token: String){
        if (_resultBmiHistory.value == null){
            viewModelScope.launch {
                _resultBmiHistory.value = Result.Loading

                try {
                    val response = bmiRepository.getBmisHistory(token)
                    _resultBmiHistory.value = Result.Success(response)
                } catch (e: Exception){
                    _resultBmiHistory.value = Result.ServerError(e.message.toString())
                }
            }
        }
    }
}