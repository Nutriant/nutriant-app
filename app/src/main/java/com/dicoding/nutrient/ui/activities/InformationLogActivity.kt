package com.dicoding.nutrient.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityInformationLogBinding

class InformationLogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInformationLogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }

        binding.btSaveHistory.setOnClickListener {
//            Logic to save the history
        }
    }
}