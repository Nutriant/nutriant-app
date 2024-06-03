package com.dicoding.nutrient.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.nutrient.data.model.banner.DataNutritionArticle
import com.dicoding.nutrient.databinding.ItemNutritionArticlesBinding

class ArticleNutritionAdapter(private val listNutritionArticle: ArrayList<DataNutritionArticle>) :
    RecyclerView.Adapter<ArticleNutritionAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemNutritionArticlesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNutritionArticlesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listNutritionArticle.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getNutritionArticle = listNutritionArticle[position]
        holder.binding.apply {
            tvArticleTitles.text = getNutritionArticle.title
            tvDescription.text = getNutritionArticle.description
            tvReleaseDate.text = getNutritionArticle.dateReleased
            ivNutritionArticles.setImageResource(getNutritionArticle.image)
        }
    }

}