package com.dicoding.nutrient.ui.fragments.dashboard

import com.dicoding.nutrient.ui.animation.DepthPageTransformer
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.bmi.GetBmisHistoryResponse
import com.dicoding.nutrient.databinding.ActivityHomeFragmentBinding
import com.dicoding.nutrient.ui.activities.SearchHomeActivity
import com.dicoding.nutrient.ui.adapters.BannerAdapter
import com.dicoding.nutrient.ui.adapters.ListBmiAdapter
import com.dicoding.nutrient.ui.adapters.ProductBannerAdapter
import com.dicoding.nutrient.ui.viewmodels.BMIHistoryViewModel
import com.dicoding.nutrient.ui.viewmodels.UserPreferencesViewModel
import com.dicoding.nutrient.ui.viewmodels.ViewModelFactory
import com.dicoding.nutrient.utils.Banner
import com.dicoding.nutrient.utils.Dummy

class HomeFragment : Fragment() {
    private var _binding: ActivityHomeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private lateinit var userPreferencesViewModel: UserPreferencesViewModel
    private lateinit var bmiHistoryViewModel: BMIHistoryViewModel
    private var currentPage = 0
    private var handler: Handler? = null
    private lateinit var runnable: Runnable

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

        initViewModel()
        showBanner()
        showRecyclerProductBanner()
        setupAction()
        setupComponent()
    }

    private fun showBanner() {
        val bannerAdapter = BannerAdapter()
        binding.viewpager2.adapter = bannerAdapter
        bannerAdapter.submitList(Banner.getDataBanner)

        binding.viewpager2.setPageTransformer(DepthPageTransformer())

        val dotsImage = Array(Banner.getDataBanner.size) {
            ImageView(requireContext())
        }

        dotsImage.forEach {
            it.setImageResource(R.drawable.non_active_dot)
            binding.slideDotLL.addView(it, params)
        }

        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dotsImage.mapIndexed { index, imageView ->
                    if (position == index) {
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
        autoSlideBanner()
    }

    private fun autoSlideBanner() {
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                if (currentPage == Banner.getDataBanner.size) {
                    currentPage = 0
                }
                binding.viewpager2.setCurrentItem(currentPage++, true)
                handler?.postDelayed(this, 3000)
            }
        }
        handler?.postDelayed(runnable, 3000)
    }

    private fun showRecyclerProductBanner() {
        val rvProductBanner = binding.rvProductBanner
        rvProductBanner.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvProductBanner.adapter = ProductBannerAdapter(Banner.getDataProductBanner)
    }

    private fun showRecyclerListBmi(dataHistoryBmi: GetBmisHistoryResponse) {
        val rvHistoryBmi = binding.rvHistoryBmi
        rvHistoryBmi.layoutManager = LinearLayoutManager(requireContext())
//        rvHistoryBmi.adapter = ListBmiAdapter(Dummy.getListDataBmi)
        rvHistoryBmi.adapter = ListBmiAdapter(dataHistoryBmi.data)
    }

    private fun setupAction() {
        binding.edSearch.setOnClickListener {
            val intent = Intent(requireContext(), SearchHomeActivity::class.java)
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireContext() as Activity,
                    Pair(binding.edSearch as View, "search")
                )
            // Start activity with the transition
            startActivity(intent, optionsCompat.toBundle())
        }
    }

    private fun setupComponent() {
        userPreferencesViewModel.getUsername().observe(viewLifecycleOwner) { username ->
            binding.tvUsername.text = username
        }

        userPreferencesViewModel.getTokenValue().observe(viewLifecycleOwner){ token ->
            bmiHistoryViewModel.getBmisHistory(token)
            bmiHistoryViewModel.resultBmisHistory.observe(viewLifecycleOwner){ result ->
                when (result){
                    is Result.Loading -> {
                        binding.loadingHistoryBmi.visibility = View.VISIBLE
                        binding.rvHistoryBmi.visibility = View.GONE
                    }
                    is Result.Success -> {
                        binding.loadingHistoryBmi.visibility = View.GONE
                        binding.rvHistoryBmi.visibility = View.VISIBLE

                        showRecyclerListBmi(result.data)
                    }
                    is Result.ServerError -> {
                        Toast.makeText(requireContext(), result.serverError, Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler?.removeCallbacks(runnable)
        binding.viewpager2.unregisterOnPageChangeCallback(pageChangeListener)
        _binding = null
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        userPreferencesViewModel =
            ViewModelProvider(requireActivity(), factory)[UserPreferencesViewModel::class.java]
        bmiHistoryViewModel = ViewModelProvider(requireActivity(), factory)[BMIHistoryViewModel::class.java]
    }
}