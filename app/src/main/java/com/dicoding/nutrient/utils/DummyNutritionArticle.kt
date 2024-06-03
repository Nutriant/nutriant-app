package com.dicoding.nutrient.utils

import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.model.banner.DataNutritionArticle
import java.util.UUID

object DummyNutritionArticle {
    private val imageNutritionArticle = arrayOf(
        R.drawable.bmidummyimages,
        R.drawable.bmidummyimages,
        R.drawable.bmidummyimages,
        R.drawable.bmidummyimages,
        R.drawable.bmidummyimages,
        R.drawable.bmidummyimages,
    )

    private val idNutritionArticle = arrayOf(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
    )

    private val titleNutritionArticle = arrayOf(
        "NEWS 1",
        "NEWS 2",
        "NEWS 3",
        "NEWS 4",
        "NEWS 5",
        "NEWS 6",
    )

    private val descriptionNutritionArticle = arrayOf(
        "Description 1",
        "Description 2",
        "Description 3",
        "Description 4",
        "Description 5",
        "Description 6",
    )

    private val dateNutritionArticle = arrayOf(
        "2024-05-01",
        "2024-05-02",
        "2024-05-03",
        "2024-05-04",
        "2024-05-05",
        "2024-05-06",
    )

    val getNutritionArticle: ArrayList<DataNutritionArticle>
        get() {
            val listArticle = arrayListOf<DataNutritionArticle>()
            for (i in imageNutritionArticle.indices) {
                val getImageNutritionArticle = imageNutritionArticle[i]
                val getTitleNutritionArticle = titleNutritionArticle[i]
                val getIdNutritionArticle = idNutritionArticle[i]
                val getDescriptionNutritionArticle = descriptionNutritionArticle[i]
                val getDateNutritionArticle = dateNutritionArticle[i]

                listArticle.add(
                    DataNutritionArticle(
                        getIdNutritionArticle,
                        getTitleNutritionArticle,
                        getDescriptionNutritionArticle,
                        getDateNutritionArticle,
                        getImageNutritionArticle
                    )
                )
            }
            return listArticle
        }
}