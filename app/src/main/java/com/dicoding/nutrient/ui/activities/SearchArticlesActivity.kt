package com.dicoding.nutrient.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivitySearchArticlesBinding

class SearchArticlesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchArticlesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionSearch()
    }

    private fun setupActionSearch() {
        binding.toolBar.setNavigationOnClickListener {
            finishAfterTransition()
        }
    }
}