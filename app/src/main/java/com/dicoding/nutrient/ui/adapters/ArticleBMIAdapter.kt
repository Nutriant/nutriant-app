package com.dicoding.nutrient.ui.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.nutrient.data.model.response.news.ArticlesItem
import com.dicoding.nutrient.databinding.ItemBmiArticlesBinding

class ArticleBMIAdapter(private var listBMIArticle: List<ArticlesItem?>) :
    RecyclerView.Adapter<ArticleBMIAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemBmiArticlesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBmiArticlesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listBMIArticle.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getBMIArticle = listBMIArticle[position]
        holder.binding.apply {
            tvArticleTitles.text = getBMIArticle?.title
            Glide.with(ivImageBMI.context)
                .load(getBMIArticle?.urlToImage)
                .into(ivImageBMI)
        }

        holder.itemView.setOnClickListener{
            val url = listBMIArticle[position]?.url
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun updateList(newList: List<ArticlesItem?>) {
        listBMIArticle = newList
        notifyDataSetChanged()
    }

}