package com.dicoding.nutrient.ui.fragments.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nutrient.databinding.ActivityNewsFragmentBinding
import com.dicoding.nutrient.ui.activities.SearchArticlesActivity
import com.dicoding.nutrient.ui.activities.SearchHomeActivity
import com.dicoding.nutrient.ui.adapters.ArticleBMIAdapter
import com.dicoding.nutrient.ui.adapters.ArticleNutritionAdapter
import com.dicoding.nutrient.utils.DummyBMIArticle
import com.dicoding.nutrient.utils.DummyNutritionArticle

class NewsFragment : Fragment() {
    private var _binding: ActivityNewsFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityNewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showListBMIArticles()
        showListNutritionArticles()
        setupActionSearch()
    }

    private fun setupActionSearch() {
        binding.edSearch.setOnClickListener {
            val intent = Intent(requireContext(), SearchArticlesActivity::class.java)
            val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireContext() as Activity,
                Pair(binding.edSearch as View, "search")
            )
            startActivity(intent, optionsCompat.toBundle())
        }
    }

    private fun showListNutritionArticles() {
        val rvNutritionArticles = binding.rvVerticalNews
        rvNutritionArticles.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvNutritionArticles.adapter = ArticleNutritionAdapter(DummyNutritionArticle.getNutritionArticle)
    }

    private fun showListBMIArticles() {
        val rvBMIArticles = binding.rvHorizontalNews
        rvBMIArticles.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvBMIArticles.adapter = ArticleBMIAdapter(DummyBMIArticle.getBMIArticle)
    }


}