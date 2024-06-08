package com.dicoding.nutrient.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.nutrient.ui.fragments.detailproduct.DescriptionProductFragment
import com.dicoding.nutrient.ui.fragments.detailproduct.NutritionProductFragment

class FragmentPageDetailProdAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val productDescription: String,
    private val productTitle: String,
    private val productImageNutrtition: Int
) : FragmentStateAdapter(
    fragmentManager, lifecycle
) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            DescriptionProductFragment.newInstance(productDescription, productTitle)
        else
            NutritionProductFragment.newInstance(productImageNutrtition)
    }
}