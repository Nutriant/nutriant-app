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
    private var selectedFragment: Fragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardWithBotNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.background = null
        binding.bottomNavView.menu.getItem(2).isEnabled = false

        if (savedInstanceState == null) {
            // Default to HomeFragment
            replaceFragment(HomeFragment())
        } else {
            // Restore the active fragment
            val fragmentTag = savedInstanceState.getString("SELECTED_FRAGMENT_TAG")
            selectedFragment = supportFragmentManager.findFragmentByTag(fragmentTag) ?: HomeFragment()
            replaceFragment(selectedFragment)
        }

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("SELECTED_FRAGMENT_TAG", selectedFragment::class.java.name)
    }

    fun replaceFragment(fragment: Fragment) {
        selectedFragment = fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, fragment::class.java.name)
        transaction.commit()
    }
}