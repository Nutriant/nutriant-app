package com.dicoding.nutrient.data.repository

import com.dicoding.nutrient.BuildConfig
import com.dicoding.nutrient.api.RetrofitClient
import com.dicoding.nutrient.data.model.response.news.NewsResponse
import retrofit2.Call

class NewsArticleRepository {
    private val apiService = RetrofitClient.createOtherApiService(BuildConfig.BASE_API_NEWS_URL)

    fun getNutritionArticles(): Call<NewsResponse> {
        return apiService.getTopHeadlines(
            query = "food",
            category = "health",
            language = "en",
            apiKey = BuildConfig.TOKEN_NEWS_URL
        )
    }

    fun getBMIArticles(): Call<NewsResponse> {
        return apiService.getTopHeadlines(
            query = "",
            category = "health",
            language = "en",
            apiKey = BuildConfig.TOKEN_NEWS_URL
        )
    }
}