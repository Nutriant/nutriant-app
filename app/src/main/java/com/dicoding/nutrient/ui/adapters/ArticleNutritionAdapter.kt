package com.dicoding.nutrient.ui.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.nutrient.data.model.response.news.ArticlesItem
import com.dicoding.nutrient.databinding.ItemNutritionArticlesBinding

class ArticleNutritionAdapter(private var listNutritionArticle: List<ArticlesItem?>) :
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
            tvArticleTitles.text = getNutritionArticle?.title
            tvDescription.text = getNutritionArticle?.description
            tvReleaseDate.text = getNutritionArticle?.publish
            Glide.with(ivNutritionArticles.context)
                .load(getNutritionArticle?.urlToImage)
                .into(ivNutritionArticles)
        }

        holder.itemView.setOnClickListener {
            val url = getNutritionArticle?.url
            if (url != null) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    fun updateList(newList: List<ArticlesItem?>) {
        listNutritionArticle = newList
        notifyDataSetChanged()
    }
}
