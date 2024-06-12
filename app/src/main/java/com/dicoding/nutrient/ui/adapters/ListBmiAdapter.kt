package com.dicoding.nutrient.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.model.history.DataListBmi
import com.dicoding.nutrient.data.model.response.bmi.DataBmisHistory
import com.dicoding.nutrient.databinding.ItemListBmiBinding
import com.dicoding.nutrient.utils.formatNumber

class ListBmiAdapter(private val dataListBmi: ArrayList<DataBmisHistory>) :
 RecyclerView.Adapter<ListBmiAdapter.ViewHolder>() {

     inner class ViewHolder(var binding : ItemListBmiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBmiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataListBmi.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getDataListBmi = dataListBmi[position]

        holder.binding.apply {
            tvHeight.text = holder.itemView.context.getString(R.string.height, formatNumber(getDataListBmi.height))
            tvWeight.text = holder.itemView.context.getString(R.string.weight, formatNumber(getDataListBmi.weight))
            tvStatus.text = " " + getDataListBmi.status

            if (getDataListBmi.status == "Underweight"){
                tvStatus.setTextColor(Color.RED)
            } else if (getDataListBmi.status == "Overweight"){
                tvStatus.setTextColor(Color.YELLOW)
            } else if (getDataListBmi.status == "Obesity"){
                tvStatus.setTextColor(Color.RED)
            }
        }
    }

}