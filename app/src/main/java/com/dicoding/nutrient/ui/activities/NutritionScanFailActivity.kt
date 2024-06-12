package com.dicoding.nutrient.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityNutritionScanFailBinding

class NutritionScanFailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNutritionScanFailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionScanFailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }
        binding.btScanAgain.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }
        binding.btFillManual.setOnClickListener {
            startActivity(Intent(this, ManualInputActivity::class.java))
        }
    }
}