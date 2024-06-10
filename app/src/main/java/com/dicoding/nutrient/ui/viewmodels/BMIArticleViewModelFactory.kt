package com.dicoding.nutrient.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.nutrient.data.repository.NewsArticleRepository

@Suppress("UNCHECKED_CAST")
class BMIArticleViewModelFactory(private val repository: NewsArticleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BMIArticleViewModel::class.java)) {
            return BMIArticleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}