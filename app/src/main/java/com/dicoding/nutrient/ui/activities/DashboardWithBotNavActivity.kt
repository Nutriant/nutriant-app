package com.dicoding.nutrient.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityDashboardWithBotNavBinding
import com.dicoding.nutrient.ui.fragments.dashboard.HistoryFragment
import com.dicoding.nutrient.ui.fragments.dashboard.HomeFragment
import com.dicoding.nutrient.ui.fragments.dashboard.NewsFragment
import com.dicoding.nutrient.ui.fragments.dashboard.ProfileFragment

class DashboardWithBotNavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardWithBotNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardWithBotNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.background = null
        binding.bottomNavView.menu.getItem(2).isEnabled = false

        replaceFragment(HomeFragment())

        binding.bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.news -> replaceFragment(NewsFragment())
                R.id.history -> replaceFragment(HistoryFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
            }
            true
        }

        binding.fabCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}