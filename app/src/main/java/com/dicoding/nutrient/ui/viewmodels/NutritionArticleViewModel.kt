package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.model.response.news.NewsResponse
import com.dicoding.nutrient.data.repository.NewsArticleRepository
import kotlinx.coroutines.launch
import com.dicoding.nutrient.data.Result
import retrofit2.await

class NutritionArticleViewModel(private val newsArticleRepository: NewsArticleRepository) : ViewModel() {
    private val _nutritionArticle = MutableLiveData<Result<NewsResponse>>()
    val nutritionArticle: MutableLiveData<Result<NewsResponse>> get() = _nutritionArticle

    fun getNutritionArticle() {
        viewModelScope.launch {
            _nutritionArticle.value = Result.Loading
            try {
                val response = newsArticleRepository.getNutritionArticles().await()
                if (response.articles != null) {
                    _nutritionArticle.value = Result.Success(response)
                } else {
                    _nutritionArticle.value = Result.ServerError("No articles found")
                }
            } catch (e: Exception) {
                _nutritionArticle.value = Result.ServerError(e.message.toString())
            }
        }
    }
}
