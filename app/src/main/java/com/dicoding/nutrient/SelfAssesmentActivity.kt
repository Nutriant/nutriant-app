package com.dicoding.nutrient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.nutrient.databinding.ActivitySelfAssesmentBinding

class SelfAssesmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelfAssesmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelfAssesmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonSelfAssesment.setOnClickListener {
                startActivity(Intent(this@SelfAssesmentActivity, LoginActivity::class.java))
            }
        }
    }
}