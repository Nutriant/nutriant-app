package com.dicoding.nutrient.utils

import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.model.banner.DataBMIArticle
import java.util.UUID

object DummyBMIArticle {

    private val imageBMI = arrayOf(
        R.drawable.bmidummyimages,
        R.drawable.bmidummyimages,
        R.drawable.bmidummyimages,
        R.drawable.bmidummyimages,
        R.drawable.bmidummyimages,
    )

    private val titleBMI = arrayOf(
        "NEWS 1",
        "NEWS 2",
        "NEWS 3",
        "NEWS 4",
        "NEWS 5",
    )

    private val idBMI = arrayOf(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
    )

    val getBMIArticle: ArrayList<DataBMIArticle>
        get() {
            val listArticle = arrayListOf<DataBMIArticle>()
            for (i in imageBMI.indices) {
                val getImageBMI = imageBMI[i]
                val getTitleBMI = titleBMI[i]
                val getIdBMI = idBMI[i]

                listArticle.add(DataBMIArticle(
                    getIdBMI,
                    getTitleBMI,
                    getImageBMI
                ))
            }
            return listArticle
        }


}