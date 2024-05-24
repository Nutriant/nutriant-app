package com.dicoding.nutrient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.nutrient.databinding.ActivityFirstOnboardingBinding

class FirstOnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            fabNextFirstOnboarding.setOnClickListener {
                startActivity(Intent(this@FirstOnboardingActivity, SecondOnboardingActivity::class.java))
            }
        }
    }
}