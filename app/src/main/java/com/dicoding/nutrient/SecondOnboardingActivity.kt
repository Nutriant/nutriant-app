package com.dicoding.nutrient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.nutrient.databinding.ActivitySecondOnboardingBinding

class SecondOnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            fabNextFirstOnboarding.setOnClickListener {
                startActivity(Intent(this@SecondOnboardingActivity, ThirdOnboardingActivity::class.java))
            }
        }
    }
}