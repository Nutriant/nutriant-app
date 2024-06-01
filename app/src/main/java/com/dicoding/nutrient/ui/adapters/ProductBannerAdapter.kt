package com.dicoding.nutrient.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.nutrient.data.model.banner.DataBannerProduct
import com.dicoding.nutrient.databinding.ItemProductBannerBinding

class ProductBannerAdapter(private val dataBannerProduct: ArrayList<DataBannerProduct>) :
 RecyclerView.Adapter<ProductBannerAdapter.ViewHolder>() {

     inner class ViewHolder(var binding : ItemProductBannerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataBannerProduct.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getDataBannerProduct = dataBannerProduct[position]
        holder.binding.apply {
            title.text = getDataBannerProduct.title
            imageItemBanner.setImageResource(getDataBannerProduct.image)
        }
    }

}