package com.dicoding.nutrient.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.nutrient.data.model.response.fatsecret.AllFood
import com.dicoding.nutrient.data.model.response.fatsecret.GetSearchFoodResponse
import com.dicoding.nutrient.databinding.ItemSearchFoodFatsecretBinding
import com.dicoding.nutrient.ui.activities.InformationLogActivity

class SearchFoodFatsecretAdapter
    : ListAdapter<AllFood, SearchFoodFatsecretAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(var binding: ItemSearchFoodFatsecretBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedFood = getItem(position)
                    val intent = Intent(itemView.context, InformationLogActivity::class.java)
                    // Berikan put extra disini
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchFoodFatsecretBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getFood = getItem(position)

        holder.binding.apply {
            holder.binding.tvName.text = getFood.food_name
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AllFood>() {
            override fun areItemsTheSame(oldItem: AllFood, newItem: AllFood): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: AllFood, newItem: AllFood): Boolean {
                return oldItem.food_id == newItem.food_id
            }

        }
    }

}