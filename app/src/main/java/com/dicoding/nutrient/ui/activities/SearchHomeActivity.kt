package com.dicoding.nutrient.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivitySearchHomeBinding

class SearchHomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction(){
        binding.toolBar.setNavigationOnClickListener {
            finishAfterTransition()
        }
    }
}