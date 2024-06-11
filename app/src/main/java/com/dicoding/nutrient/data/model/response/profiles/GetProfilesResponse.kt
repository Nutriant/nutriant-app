package com.dicoding.nutrient.data.model.response.profiles

data class GetProfilesResponse(
    val status: Int,
    val data: DataProfiles
)

data class DataProfiles(
    val username: String,
    val email: String,
    val user_data: UserDataProfiles
)

data class UserDataProfiles(
    val birthdate: String,
    val age: Int,
    val weight: Int,
    val height: Int,
    val gender: String,
    val image: String
)
