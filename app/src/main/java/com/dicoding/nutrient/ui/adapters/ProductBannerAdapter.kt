package com.dicoding.nutrient.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.nutrient.data.model.banner.DataBannerProduct
import com.dicoding.nutrient.databinding.ItemProductBannerBinding
import com.dicoding.nutrient.ui.activities.DetailBannerActivity
import com.dicoding.nutrient.ui.activities.DetailBannerActivity.Companion.PRODUCT_DESCRIPTION
import com.dicoding.nutrient.ui.activities.DetailBannerActivity.Companion.PRODUCT_IMAGE
import com.dicoding.nutrient.ui.activities.DetailBannerActivity.Companion.PRODUCT_IMAGE_NUTRITION
import com.dicoding.nutrient.ui.activities.DetailBannerActivity.Companion.PRODUCT_TITLE

class ProductBannerAdapter(private val dataBannerProduct: ArrayList<DataBannerProduct>) :
    RecyclerView.Adapter<ProductBannerAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemProductBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedProduct = dataBannerProduct[position]
                    val intent = Intent(itemView.context, DetailBannerActivity::class.java)
                    intent.putExtra(PRODUCT_IMAGE, clickedProduct.image)
                    intent.putExtra(PRODUCT_TITLE, clickedProduct.title)
                    intent.putExtra(PRODUCT_DESCRIPTION, clickedProduct.description)
                    intent.putExtra(PRODUCT_IMAGE_NUTRITION, clickedProduct.imageNutrition)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemProductBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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