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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nutrient.data.repository.NewsArticleRepository
import com.dicoding.nutrient.databinding.ActivityNewsFragmentBinding
import com.dicoding.nutrient.ui.activities.SearchArticlesActivity
import com.dicoding.nutrient.ui.adapters.ArticleBMIAdapter
import com.dicoding.nutrient.ui.adapters.ArticleNutritionAdapter
import com.dicoding.nutrient.ui.viewmodels.NutritionArticleViewModel
import com.dicoding.nutrient.ui.viewmodels.NutritionArticleViewModelFactory
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.news.ArticlesItem
import com.dicoding.nutrient.ui.viewmodels.BMIArticleViewModel
import com.dicoding.nutrient.ui.viewmodels.BMIArticleViewModelFactory

class NewsFragment : Fragment() {
    private var _binding: ActivityNewsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var nutritionArticleViewModel: NutritionArticleViewModel
    private lateinit var bmiArticleViewModel: BMIArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityNewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = NewsArticleRepository()
        val viewModelFactory = NutritionArticleViewModelFactory(repository)
        nutritionArticleViewModel =
            ViewModelProvider(this, viewModelFactory)[NutritionArticleViewModel::class.java]

        val bmiViewModelFactory = BMIArticleViewModelFactory(repository)
        bmiArticleViewModel =
            ViewModelProvider(this, bmiViewModelFactory)[BMIArticleViewModel::class.java]

        setupActionSearch()
        observeViewModelNutrition()
        observeViewModelBMI()
        nutritionArticleViewModel.getNutritionArticle()
        bmiArticleViewModel.getBMIArticle()
    }

    private fun observeViewModelNutrition() {
        nutritionArticleViewModel.nutritionArticle.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loadingProfile.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.loadingProfile.visibility = View.GONE
                    val articles = result.data.articles
                    if (articles != null) {
                        showListNutritionArticles(articles)
                    }
                }

                is Result.ServerError -> {
                    binding.loadingProfile.visibility = View.GONE
                }

                else -> {
                    binding.loadingProfile.visibility = View.GONE
                }
            }
        }
    }

    private fun observeViewModelBMI() {
        bmiArticleViewModel.bmiArticle.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loadingProfile.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.loadingProfile.visibility = View.GONE
                    val articles = result.data.articles
                    if (articles != null) {
                        showListBMIArticles(articles)
                    }
                }

                is Result.ServerError -> {
                    binding.loadingProfile.visibility = View.GONE
                }

                else -> {
                    binding.loadingProfile.visibility = View.GONE
                }
            }
        }
    }

    private fun setupActionSearch() {
        binding.edSearch.setOnClickListener {
            val intent = Intent(requireContext(), SearchArticlesActivity::class.java)
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireContext() as Activity,
                    Pair(binding.edSearch as View, "search")
                )
            startActivity(intent, optionsCompat.toBundle())
        }
    }

    private fun showListNutritionArticles(articles: List<ArticlesItem?>) {
        val filteredArticles = articles.filterNotNull().filter { it.urlToImage != null }
        val rvNutritionArticles = binding.rvVerticalNews
        rvNutritionArticles.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvNutritionArticles.adapter = ArticleNutritionAdapter(filteredArticles)
    }

    private fun showListBMIArticles(articles: List<ArticlesItem?>) {
        val filteredArticles = articles.filterNotNull().filter { it.urlToImage != null }
        val rvBMIArticles = binding.rvHorizontalNews
        rvBMIArticles.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvBMIArticles.adapter = ArticleBMIAdapter(filteredArticles)
    }
}