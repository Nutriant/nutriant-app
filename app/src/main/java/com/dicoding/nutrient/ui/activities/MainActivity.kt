package com.dicoding.nutrient.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.databinding.ActivityMainBinding
import com.dicoding.nutrient.ui.viewmodels.FatsecretViewModel
import com.dicoding.nutrient.ui.viewmodels.UserPreferencesViewModel
import com.dicoding.nutrient.ui.viewmodels.UserStatusViewModel
import com.dicoding.nutrient.ui.viewmodels.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreferencesViewModel: UserPreferencesViewModel
    private lateinit var userStatusViewModel: UserStatusViewModel
    private lateinit var fatsecretViewModel: FatsecretViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            userPreferencesViewModel.getUserLoginStatus().observe(this@MainActivity){ isLoggedIn ->
                fatsecretViewModel.getTokenFatsecret().observe(this@MainActivity){ tokenResult ->
                    when (tokenResult){
                        is Result.Success -> {
                            Log.d("MainActivity", "token: ${tokenResult.data.access_token}")
                            if(isLoggedIn){
                                userPreferencesViewModel.getTokenValue().observe(this@MainActivity){ token ->
                                    userStatusViewModel.userStatus(token).observe(this@MainActivity){ result ->
                                        when(result){
                                            is Result.Loading -> {

                                            }
                                            is Result.Success -> {
                                                if (result.data.new_user == 1){
                                                    val intent = Intent(this@MainActivity, SelfAssesmentActivity::class.java)
                                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                    startActivity(intent)
                                                } else {
                                                    val intent = Intent(this@MainActivity, DashboardWithBotNavActivity::class.java)
                                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                    startActivity(intent)
                                                }
                                            }
                                            is Result.ServerError -> {
                                                Toast.makeText(this@MainActivity, result.serverError, Toast.LENGTH_LONG).show()
                                            }
                                            else -> {}
                                        }
                                    }
                                }
                            } else {
                                val intent = Intent(this@MainActivity, OnboardActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun initViewModel(){
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@MainActivity.application)
        userPreferencesViewModel = ViewModelProvider(this, factory).get(UserPreferencesViewModel::class.java)
        userStatusViewModel = ViewModelProvider(this, factory).get(UserStatusViewModel::class.java)
        fatsecretViewModel = ViewModelProvider(this, factory)[FatsecretViewModel::class.java]
    }
}