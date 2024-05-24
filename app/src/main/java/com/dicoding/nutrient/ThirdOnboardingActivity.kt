package com.dicoding.nutrient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.nutrient.databinding.ActivityThirdOnboardingBinding

class ThirdOnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            fabNextFirstOnboarding.setOnClickListener {
                startActivity(Intent(this@ThirdOnboardingActivity, LoginActivity::class.java))
            }
        }
    }
}