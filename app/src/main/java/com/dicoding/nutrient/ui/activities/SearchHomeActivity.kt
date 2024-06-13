package com.dicoding.nutrient.ui.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.databinding.ActivitySearchHomeBinding
import com.dicoding.nutrient.ui.adapters.SearchFoodFatsecretAdapter
import com.dicoding.nutrient.ui.viewmodels.FatsecretViewModel
import com.dicoding.nutrient.ui.viewmodels.UserPreferencesViewModel
import com.dicoding.nutrient.ui.viewmodels.ViewModelFactory

class SearchHomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchHomeBinding
    private lateinit var userPreferencesViewModel: UserPreferencesViewModel
    private lateinit var fatsecretViewModel: FatsecretViewModel
    private lateinit var searchFoodFatsecretAdapter: SearchFoodFatsecretAdapter
    private var isLoading = false
    private var currentQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initAdapter()

        setupAction()
    }

    private fun searchFood(search: String){
        if (isLoading) return // Jangan lakukan pencarian jika sedang loading
        isLoading = true
        currentQuery = search

        userPreferencesViewModel.getTokenFatsecret().observe(this) { token ->
            fatsecretViewModel.searchProduct(token, search).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.apply {
                            rvSearch.visibility = View.GONE
                            loadingSearch.visibility = View.VISIBLE
                        }
                    }
                    is Result.Success -> {
                        binding.apply {
                            rvSearch.visibility = View.VISIBLE
                            loadingSearch.visibility = View.GONE
                            searchFoodFatsecretAdapter.submitList(result.data.foods.food)
                        }
                        isLoading = false // Reset status loading setelah selesai
                    }
                    is Result.ServerError -> {
                        Toast.makeText(this@SearchHomeActivity, result.serverError, Toast.LENGTH_LONG).show()
                        isLoading = false // Reset status loading setelah error
                    }
                    else -> {
                        isLoading = false // Reset status loading jika ada hasil lain
                    }
                }
            }
        }
    }

    private fun initAdapter(){
        searchFoodFatsecretAdapter = SearchFoodFatsecretAdapter()
        binding.rvSearch.layoutManager = LinearLayoutManager(this)
        binding.rvSearch.adapter = searchFoodFatsecretAdapter
    }

    private fun initViewModel(){
        val factory = ViewModelFactory.getInstance(this.application)
        userPreferencesViewModel = ViewModelProvider(this, factory)[UserPreferencesViewModel::class.java]
        fatsecretViewModel = ViewModelProvider(this, factory)[FatsecretViewModel::class.java]
    }

    private fun setupAction(){
        binding.toolBar.setNavigationOnClickListener {
            finishAfterTransition()
        }

        binding.edSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query != currentQuery) {
                    searchFood(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}