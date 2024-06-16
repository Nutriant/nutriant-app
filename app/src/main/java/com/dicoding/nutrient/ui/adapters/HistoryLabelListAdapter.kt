package com.dicoding.nutrient.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.model.history.DataHistoryLabel
import com.dicoding.nutrient.data.model.response.foods.DataFoods
import com.dicoding.nutrient.databinding.ItemHistoryLabelBinding
import com.dicoding.nutrient.utils.formatDateHistory

class HistoryLabelListAdapter(private val dataHistory: ArrayList<DataFoods>) :
    RecyclerView.Adapter<HistoryLabelListAdapter.ViewHolder>() {

    inner class ViewHolder(var binding : ItemHistoryLabelBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryLabelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataHistory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getHistory = dataHistory[position]
        holder.binding.apply {
            historyCalorDetail.text = holder.itemView.context.getString(R.string.calori_title, getHistory.calories)
            historyCarboDetail.text = holder.itemView.context.getString(R.string.carboTitle, getHistory.carbohydrate)
            historyFatDetail.text = holder.itemView.context.getString(R.string.fatTitle, getHistory.fat)
            historyProteinDetail.text = holder.itemView.context.getString(R.string.proteinTitle, getHistory.protein)
            dateLabel.text = getHistory.created_at.formatDateHistory()
            nameLabel.text = getHistory.name
            Glide.with(holder.itemView)
                .load("http://${getHistory.image}")
                .apply(RequestOptions().placeholder(R.drawable.img_label_history))
                .into(holder.binding.imgLabel)
        }
    }


}