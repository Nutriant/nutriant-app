package com.dicoding.nutrient.data.model.response.bmi

data class GetBmisHistoryResponse(
    val status: Int,
    val data: ArrayList<DataBmisHistory>
)

data class DataBmisHistory(
    val id: Int,
    val bmi: String,
    val weight: String,
    val height: String,
    val status: String
)