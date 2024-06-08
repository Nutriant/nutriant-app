package com.dicoding.nutrient.data.model.response.myprofile

data class MyProfileResponse(
    val status: Int,
    val data: MyProfileResponseData,
)

data class MyProfileResponseData(
    val birthdate: String,
    val weight: Int,
    val height: Int,
    val gender: String,
    val image: String?,
    val age: Int,
)
