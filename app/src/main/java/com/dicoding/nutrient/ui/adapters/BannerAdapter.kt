package com.dicoding.nutrient.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.nutrient.data.model.banner.DataBanner
import com.dicoding.nutrient.databinding.ItemBannerBinding

class BannerAdapter : ListAdapter<DataBanner, BannerAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(var binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getBaner = getItem(position)
        holder.binding.imageItemBanner.setImageDrawable(holder.itemView.context.getDrawable(getBaner.image))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataBanner>(){
            override fun areItemsTheSame(oldItem: DataBanner, newItem: DataBanner): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataBanner, newItem: DataBanner): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}