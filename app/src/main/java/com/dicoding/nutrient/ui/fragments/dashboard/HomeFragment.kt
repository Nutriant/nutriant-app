package com.dicoding.nutrient.ui.fragments.dashboard

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityHomeFragmentBinding
import com.dicoding.nutrient.ui.activities.SearchHomeActivity
import com.dicoding.nutrient.ui.adapters.BannerAdapter
import com.dicoding.nutrient.ui.adapters.ListBmiAdapter
import com.dicoding.nutrient.ui.adapters.ProductBannerAdapter
import com.dicoding.nutrient.utils.Banner
import com.dicoding.nutrient.utils.Dummy
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showBanner()
        showRecyclerProductBanner()
        showRecyclerListBmi()
        setupAction()
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

    private fun showRecyclerListBmi(){
        val rvHistoryBmi = binding.rvHistoryBmi
        rvHistoryBmi.layoutManager = LinearLayoutManager(requireContext())
        rvHistoryBmi.adapter = ListBmiAdapter(Dummy.getListDataBmi)
    }

    private fun setupAction(){
        binding.edSearch.setOnClickListener {
            val intent = Intent(requireContext(), SearchHomeActivity::class.java)
            val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireContext() as Activity,
                Pair(binding.edSearch as View, "search")
            )
            // Start activity with the transition
            startActivity(intent, optionsCompat.toBundle())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewpager2.unregisterOnPageChangeCallback(pageChangeListener)
        _binding = null
    }
}