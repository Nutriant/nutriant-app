package com.dicoding.nutrient.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityDetailBannerBinding
import com.dicoding.nutrient.ui.adapters.FragmentPageDetailProdAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailBannerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBannerBinding
    private lateinit var adapter: FragmentPageDetailProdAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get product details from intent
        val productImage = intent.getIntExtra(PRODUCT_IMAGE, 0)
        val productTitle = intent.getStringExtra(PRODUCT_TITLE)
        val productDescription = intent.getStringExtra(PRODUCT_DESCRIPTION)
        val productNutrition = intent.getIntExtra(PRODUCT_IMAGE_NUTRITION, 0)

        // Set product image
        binding.productImageview.setImageResource(productImage)

        adapter = FragmentPageDetailProdAdapter(
            supportFragmentManager,
            lifecycle,
            productDescription ?: "",
            productTitle ?: "",
            productNutrition
        )

        binding.tabLayoutDetailProduct.addTab(
            binding.tabLayoutDetailProduct.newTab().setText("Description")
        )
        binding.tabLayoutDetailProduct.addTab(
            binding.tabLayoutDetailProduct.newTab().setText("Nutrition")
        )

        binding.viewPagerDetailProduct.adapter = adapter

        binding.tabLayoutDetailProduct.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPagerDetailProduct.currentItem = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Do nothing
            }
        })

        binding.viewPagerDetailProduct.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayoutDetailProduct.selectTab(
                    binding.tabLayoutDetailProduct.getTabAt(
                        position
                    )
                )
            }
        })
    }

    companion object {
        const val PRODUCT_IMAGE = "PRODUCT_IMAGE"
        const val PRODUCT_TITLE = "PRODUCT_TITLE"
        const val PRODUCT_DESCRIPTION = "PRODUCT_DESCRIPTION"
        const val PRODUCT_IMAGE_NUTRITION = "PRODUCT_IMAGE_NUTRITION"
    }
}