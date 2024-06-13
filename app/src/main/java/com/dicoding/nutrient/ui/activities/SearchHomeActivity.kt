package com.dicoding.nutrient.ui.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.fatsecret.GetSearchFoodResponse
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setupAction()
    }

    private fun searchFood(search: String){
        userPreferencesViewModel.getTokenFatsecret().observe(this){ token ->
            fatsecretViewModel.searchProduct(token, search).observe(this){ result ->
                when (result){
                    is Result.Loading -> {

                    }
                    is Result.Success -> {

                    }
                    else -> {}
                }
            }
        }
    }

    private fun initAdapter(){

    }

    private fun initViewModel(){
        val factory = ViewModelFactory.getInstance(this.application)
        userPreferencesViewModel = ViewModelProvider(this, factory)[UserPreferencesViewModel::class.java]
        fatsecretViewModel = ViewModelProvider(this, factory)[fatsecretViewModel::class.java]
    }

    private fun setupAction(){
        binding.toolBar.setNavigationOnClickListener {
            finishAfterTransition()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.apply {

                }
            }

        }

        binding.edSearch.addTextChangedListener(textWatcher)
    }
}