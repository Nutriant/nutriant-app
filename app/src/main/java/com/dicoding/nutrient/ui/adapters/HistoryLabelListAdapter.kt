package com.dicoding.nutrient.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.nutrient.data.model.history.DataHistoryLabel
import com.dicoding.nutrient.databinding.ItemHistoryLabelBinding

class HistoryLabelListAdapter(private val dataHistory: ArrayList<DataHistoryLabel>) :
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
            imgLabel.setImageResource(getHistory.image)
            nameLabel.text = getHistory.label
            dateLabel.text = getHistory.date
        }
    }


}