package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.news.NewsResponse
import com.dicoding.nutrient.data.repository.NewsArticleRepository
import kotlinx.coroutines.launch
import retrofit2.await

class BMIArticleViewModel(private val newsArticleRepository: NewsArticleRepository) : ViewModel() {
    private val _bmiArticle = MutableLiveData<Result<NewsResponse>>()
    val bmiArticle: MutableLiveData<Result<NewsResponse>> get() = _bmiArticle

    fun getBMIArticle() {
        viewModelScope.launch {
            _bmiArticle.value = Result.Loading
            try {
                val response = newsArticleRepository.getBMIArticles().await()
                if (response.articles != null) {
                    _bmiArticle.value = Result.Success(response)
                } else {
                    _bmiArticle.value = Result.ServerError("No articles found")
                }
            } catch (e: Exception) {
                _bmiArticle.value = Result.ServerError(e.message.toString())
            }
        }
    }
}