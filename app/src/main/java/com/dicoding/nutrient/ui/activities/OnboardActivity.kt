package com.dicoding.nutrient.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityOnboardBinding
import com.dicoding.nutrient.ui.fragments.onboards.FirstOnboardingFragment
import com.dicoding.nutrient.ui.fragments.onboards.SecondOnboardingFragment
import com.dicoding.nutrient.ui.fragments.onboards.ThirdOnboardingFragment

class OnboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardBinding
    private lateinit var fragmentCounter: MutableList<Fragment>
    var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initComponents()
        setupAction()
        loadFragment(fragmentCounter[counter])
    }

    private fun initComponents() {
        fragmentCounter = mutableListOf(
            FirstOnboardingFragment(),
            SecondOnboardingFragment(),
            ThirdOnboardingFragment()
        )
    }

    private fun setupAction(){
        binding.fab.setOnClickListener {
            if (fragmentCounter.size == counter + 1) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                counter++
                loadFragment(fragmentCounter[counter])
            }
        }
        binding.fabBack.setOnClickListener {
            if (counter > 0) {
                counter--
                loadFragmentBack(fragmentCounter[counter])
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_left_to_right)
            .replace(R.id.fragmentView, fragment)
            .commit()
        setFabVisibility(fragment)
    }

    private fun loadFragmentBack(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_right_to_left)
            .replace(R.id.fragmentView, fragment)
            .commit()
        setFabVisibility(fragment)
    }

    private fun setFabVisibility(fragment: Fragment) {
        if (fragment is FirstOnboardingFragment) {
            binding.fabBack.visibility = View.GONE
        } else {
            binding.fabBack.visibility = View.VISIBLE
        }
    }
}
