package com.dicoding.nutrient.ui.fragments.dashboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nutrient.data.repository.NewsArticleRepository
import com.dicoding.nutrient.databinding.ActivityNewsFragmentBinding
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
    private lateinit var articleNutritionAdapter: ArticleNutritionAdapter
    private lateinit var articleBMIAdapter: ArticleBMIAdapter
    private var allNutritionArticles: List<ArticlesItem?> = listOf()
    private var allBMIArticles: List<ArticlesItem?> = listOf()

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

        setupRecyclerViews()
        observeViewModelNutrition()
        observeViewModelBMI()
        nutritionArticleViewModel.getNutritionArticle()
        bmiArticleViewModel.getBMIArticle()

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterArticles(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupRecyclerViews() {
        articleNutritionAdapter = ArticleNutritionAdapter(listOf())
        articleBMIAdapter = ArticleBMIAdapter(listOf())

        binding.rvVerticalNews.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = articleNutritionAdapter
        }

        binding.rvHorizontalNews.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = articleBMIAdapter
        }
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
                        allNutritionArticles = articles
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
                        allBMIArticles = articles
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

    private fun showListNutritionArticles(articles: List<ArticlesItem?>) {
        val filteredArticles = articles.filterNotNull().filter { it.urlToImage != null }
        articleNutritionAdapter.updateList(filteredArticles)
    }

    private fun showListBMIArticles(articles: List<ArticlesItem?>) {
        val filteredArticles = articles.filterNotNull().filter { it.urlToImage != null }
        articleBMIAdapter.updateList(filteredArticles)
    }

    private fun filterArticles(query: String) {
        val filteredNutritionArticles = allNutritionArticles.filter {
            it?.title?.contains(query, ignoreCase = true) == true
        }
        val filteredBMIArticles = allBMIArticles.filter {
            it?.title?.contains(query, ignoreCase = true) == true
        }
        articleNutritionAdapter.updateList(filteredNutritionArticles)
        articleBMIAdapter.updateList(filteredBMIArticles)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
