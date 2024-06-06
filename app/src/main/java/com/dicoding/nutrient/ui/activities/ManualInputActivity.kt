package com.dicoding.nutrient.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityManualInputBinding

class ManualInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManualInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManualInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, NutritionScanFailActivity::class.java))
        }

        binding.btSaveHistory.setOnClickListener {
            // Logic to save the history
        }
    }
}