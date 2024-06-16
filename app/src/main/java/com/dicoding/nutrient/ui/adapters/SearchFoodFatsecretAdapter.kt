package com.dicoding.nutrient.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.model.response.fatsecret.AllFood
import com.dicoding.nutrient.data.model.response.fatsecret.GetSearchFoodResponse
import com.dicoding.nutrient.databinding.ItemSearchFoodFatsecretBinding
import com.dicoding.nutrient.ui.activities.InformationLogActivity
import com.dicoding.nutrient.utils.getDataNutritionFromDesc

class SearchFoodFatsecretAdapter
    : ListAdapter<AllFood, SearchFoodFatsecretAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(var binding: ItemSearchFoodFatsecretBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedFood = getItem(position)
                    val mapOfNutrition = clickedFood.food_description.getDataNutritionFromDesc()
                    val intent = Intent(itemView.context, InformationLogActivity::class.java)
                    // Berikan put extra disini
                    intent.apply {
                        putExtra(InformationLogActivity.DATA_CALORI, mapOfNutrition["Calories"]!!.toInt())
                        putExtra(InformationLogActivity.DATA_KARBO, mapOfNutrition["Carbs"]!!.toDouble())
                        putExtra(InformationLogActivity.DATA_PROTEIN, mapOfNutrition["Protein"]!!.toDouble())
                        putExtra(InformationLogActivity.DATA_LEMAK, mapOfNutrition["Fat"]!!.toDouble())
                        putExtra(InformationLogActivity.NAME_FOOD, clickedFood.food_name)
                    }
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
        val mapOfNutrition = getFood.food_description.getDataNutritionFromDesc()

        holder.binding.apply {
            holder.binding.tvName.text = getFood.food_name
            holder.binding.searchCalorDetail.text = holder.itemView.context.getString(R.string.calori_title, mapOfNutrition.get("Calories"))
            holder.binding.searchCarboDetail.text = holder.itemView.context.getString(R.string.carboTitle, mapOfNutrition.get("Carbs"))
            holder.binding.searchFatDetail.text = holder.itemView.context.getString(R.string.fatTitle, mapOfNutrition.get("Fat"))
            holder.binding.searchProteinDetail.text = holder.itemView.context.getString(R.string.proteinTitle, mapOfNutrition.get("Protein"))
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