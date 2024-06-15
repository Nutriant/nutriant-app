package com.dicoding.nutrient.data.repository

import com.dicoding.nutrient.api.ApiService
import com.dicoding.nutrient.data.model.response.foods.PostFoodResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class FoodRepository(private val apiService: ApiService) {

    suspend fun postFood(
        token: String,
        name: String,
        calories: Int,
        sugar: Int,
        fat: Int,
        protein: Int,
        carbohydrate: Int,
        image: File?
    ) : PostFoodResponse {
        return if (image != null){
            val mediaType = "image/*".toMediaTypeOrNull()
            val requestFile: RequestBody = RequestBody.create(mediaType, image)
            val imagePart: MultipartBody.Part = MultipartBody.Part.createFormData("image", image.name, requestFile)

            val namePart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), name
            )

            val sugarPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), sugar.toString()
            )

            val fatPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), fat.toString()
            )

            val proteinPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), protein.toString()
            )

            val carbohydratePart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), carbohydrate.toString()
            )

            val caloriesPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), calories.toString()
            )

            apiService.postFood(
                "Bearer $token",
                namePart,
                caloriesPart,
                sugarPart,
                fatPart,
                proteinPart,
                carbohydratePart,
                imagePart
            )
        } else {
            val namePart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), name
            )

            val sugarPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), sugar.toString()
            )

            val fatPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), fat.toString()
            )

            val proteinPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), protein.toString()
            )

            val carbohydratePart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), carbohydrate.toString()
            )

            val caloriesPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), calories.toString()
            )

            apiService.postFood(
                "Bearer $token",
                namePart,
                caloriesPart,
                sugarPart,
                fatPart,
                proteinPart,
                carbohydratePart,
                null
            )
        }
    }
}