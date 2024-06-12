package com.dicoding.nutrient.utils

import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.model.banner.DataBanner
import com.dicoding.nutrient.data.model.banner.DataBannerProduct
import java.util.UUID

object Banner {

    private val imageBanner = arrayOf(
        R.drawable.banner1,
        R.drawable.marasmus,
        R.drawable.kwashiorkor
    )

    private val idBanner = arrayOf(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
    )

    private val productImageBanner = arrayOf(
        R.drawable.chitato,
        R.drawable.ultramilk,
        R.drawable.kokokrunch,
        R.drawable.yoghurtcimory,
        R.drawable.soyjoy
    )

    private val productImageNutrition = arrayOf(
        R.drawable.chitatonutrition,
        R.drawable.ultramilknutrition,
        R.drawable.kokokrunchnutrition,
        R.drawable.yoghurtnutrition,
        R.drawable.soyjoynutrition
    )

    private val idProductBanner = arrayOf(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString()
    )

    private val titleProductBanner = arrayOf(
        "Chitato",
        "Ultra Milk",
        "Koko Krunch",
        "Yoghurt Cimory",
        "Soyjoy",
    )

    private val descriptionProductBanner = arrayOf(
        "Chitato is a snack food produced by Indofood Fritolay Makmur. Chitato was first introduced in 1990 by PT Indofood Fritolay Makmur. Chitato has various flavors that are favored by the Indonesian people.\n\nChitato products are the catalysts that encourages our consumer to live life fearlessly. The real fresh potato with crunchiness like no other, and the wavy cut that preserve the flavour. The bold taste and texture triggers them, so they don't hold back and can keep riding the wave of life.",
        "High quality fresh milk combined with natural flavors (Chocolate, Strawberry and Mocca) containing the natural balanced goodness of protein, carbohydrates, vitamins, minerals like Calcium, Magnesium, Phosphorus besides great taste, make Ultra Milk Flavor a complete & balanced nutrition for daily consumption by all family members, especially teenagers.\n\nOur Milk Flavor is known not only for its best taste among all products in the market, but also for its naturally fresh and pure milk ingredients.",
        "Koko Krunch is a breakfast cereal made from whole grain wheat and rice, coated in chocolate. It is manufactured by Nestlé and was introduced in 1993. Koko Krunch is available in most parts of the world.\n\nKoko Krunch is a delicious cereal with a rich chocolate taste that kids love. It is high in fiber and enriched with vitamins and minerals. Koko Krunch is a source of calcium, iron, and vitamin D. It is a great way to start the day and is a good source of energy.",
        "Cimory is a brand of dairy products from Indonesia. Cimory produces a variety of dairy products, including milk, yogurt, and cheese. Cimory products are made from fresh milk and are free from preservatives and artificial additives.\n\nCimory Yogurt is a delicious and healthy snack that is perfect for any time of day. It is made from fresh milk and live cultures, which help to promote good digestion and overall health. Cimory Yogurt is available in a variety of flavors, including strawberry, blueberry, and mango.",
        "Soyjoy is a brand of soy-based snack bars that are made from whole soybeans. Soyjoy bars are available in a variety of flavors, including apple, blueberry, and banana. Soyjoy bars are a good source of protein, fiber, and other essential nutrients. They are also free from artificial additives and preservatives.\n\nSoyjoy bars are a delicious and healthy snack that is perfect for any time of day. They are a great way to get the energy you need to keep going throughout the day."
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
                val getDescriptionProductBanner = descriptionProductBanner[i]
                val getProductImageNutrition = productImageNutrition[i]

                listProductBaner.add(DataBannerProduct(
                    getIdBannerProduct,
                    getTitleProductBanner,
                    getProductImageBanner,
                    getDescriptionProductBanner,
                    getProductImageNutrition
                ))
            }
            return listProductBaner
        }
}