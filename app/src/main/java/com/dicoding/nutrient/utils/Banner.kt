package com.dicoding.nutrient.utils

import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.model.banner.DataBanner
import com.dicoding.nutrient.data.model.banner.DataBannerProduct
import java.util.UUID

object Banner {

    private val imageBanner = arrayOf(
        R.drawable.banner1,
        R.drawable.banner1,
        R.drawable.banner1
    )

    private val idBanner = arrayOf(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
    )

    private val productImageBanner = arrayOf(
        R.drawable.chitato,
        R.drawable.ultramilk
    )

    private val idProductBanner = arrayOf(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
    )

    private val titleProductBanner = arrayOf(
        "Chitato",
        "Ultra Milk"
    )

    val getDataBanner: ArrayList<DataBanner>
            get(){
                val listBanner = arrayListOf<DataBanner>()
                for (i in imageBanner.indices){
                    val getImageBanner = imageBanner[i]
                    val getIdBanner = idBanner[i]

                    listBanner.add(DataBanner(
                        getIdBanner,
                        getImageBanner
                    ))
                }
                return listBanner
            }

    val getDataProductBanner: ArrayList<DataBannerProduct>
        get() {
            val listProductBaner = arrayListOf<DataBannerProduct>()
            for (i in productImageBanner.indices){
                val getIdBannerProduct = idProductBanner[i]
                val getProductImageBanner = productImageBanner[i]
                val getTitleProductBanner = titleProductBanner[i]

                listProductBaner.add(DataBannerProduct(
                    getIdBannerProduct,
                    getTitleProductBanner,
                    getProductImageBanner
                ))
            }
            return listProductBaner
        }
}