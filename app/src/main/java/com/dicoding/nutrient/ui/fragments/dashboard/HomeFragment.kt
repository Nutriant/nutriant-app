package com.dicoding.nutrient.ui.fragments.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityHomeFragmentBinding
import com.dicoding.nutrient.ui.adapters.BannerAdapter
import com.dicoding.nutrient.ui.adapters.ProductBannerAdapter
import com.dicoding.nutrient.utils.Banner
import java.lang.reflect.Array

class HomeFragment : Fragment() {
    private var _binding: ActivityHomeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8, 0, 8, 0)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityHomeFragmentBinding.inflate(inflater, container, false)

        showBanner()
        showRecyclerProductBanner()
        return binding.root
    }

    private fun showBanner(){
        val bannerAdapter = BannerAdapter()
        binding.viewpager2.adapter = bannerAdapter
        bannerAdapter.submitList(Banner.getDataBanner)

        val dotsImage = Array(Banner.getDataBanner.size){
            ImageView(requireContext())
        }

        dotsImage.forEach {
            it.setImageResource(R.drawable.non_active_dot)
            binding.slideDotLL.addView(it, params)
        }

        pageChangeListener = object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                dotsImage.mapIndexed { index, imageView ->
                    if (position == index){
                        imageView.setImageResource(
                            R.drawable.active_dot
                        )
                    } else {
                        imageView.setImageResource(R.drawable.non_active_dot)
                    }
                }
                super.onPageSelected(position)
            }
        }

        binding.viewpager2.registerOnPageChangeCallback(pageChangeListener)
    }

    private fun showRecyclerProductBanner(){
        val rvProductBanner = binding.rvProductBanner
        rvProductBanner.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvProductBanner.adapter = ProductBannerAdapter(Banner.getDataProductBanner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewpager2.unregisterOnPageChangeCallback(pageChangeListener)
        _binding = null
    }
}