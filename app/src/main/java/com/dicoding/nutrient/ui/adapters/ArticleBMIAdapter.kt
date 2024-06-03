package com.dicoding.nutrient.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.nutrient.data.model.banner.DataBMIArticle
import com.dicoding.nutrient.databinding.ItemBmiArticlesBinding

class ArticleBMIAdapter(private val listBMIArticle: ArrayList<DataBMIArticle>) :
    RecyclerView.Adapter<ArticleBMIAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemBmiArticlesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBmiArticlesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listBMIArticle.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getBMIArticle = listBMIArticle[position]
        holder.binding.apply {
            tvArticleTitles.text = getBMIArticle.title
            ivImageBMI.setImageResource(getBMIArticle.image)
        }
    }

}