package com.dicoding.nutrient.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dicoding.nutrient.databinding.ActivityMainBinding
import com.dicoding.nutrient.ui.viewmodels.UserPreferencesViewModel
import com.dicoding.nutrient.ui.viewmodels.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreferencesViewModel: UserPreferencesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            userPreferencesViewModel.getUserLoginStatus().observe(this@MainActivity){ isLoggedIn ->
                if(isLoggedIn){
                    val intent = Intent(this@MainActivity, DashboardWithBotNavActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    val intent = Intent(this@MainActivity, OnboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        }
    }

    private fun initViewModel(){
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@MainActivity.application)
        userPreferencesViewModel = ViewModelProvider(this, factory).get(UserPreferencesViewModel::class.java)
    }
}