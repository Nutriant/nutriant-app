package com.dicoding.nutrient.data.repository

import com.dicoding.nutrient.api.ApiService
import com.dicoding.nutrient.data.model.response.assestment.AssestmentResponse
import com.dicoding.nutrient.data.model.response.myprofile.MyProfileResponse
import com.dicoding.nutrient.data.model.response.profiles.GetProfilesResponse
import com.dicoding.nutrient.data.model.response.profiles.UpdateProfileResponse
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import java.io.File

class UserDataRepository(private val apiService: ApiService) {

    suspend fun fillAssestment(token : String, height: Double, weight: Double) : AssestmentResponse{
        return apiService.fillAssestment("Bearer $token", weight, height)
    }

    suspend fun getMyProfile(token: String) : MyProfileResponse {
        return apiService.getMyProfile("Bearer $token")
    }

    suspend fun updateProfile(
        token: String,
        username: String,
        birthdate: String,
        gender: Int,
        height: Int,
        weight: Int,
        image: File?,
        _method: String
    ) : UpdateProfileResponse {

        if (image != null){
            val mediaType = "image/*".toMediaTypeOrNull()
            val requestFile: RequestBody = RequestBody.create(mediaType, image)
            val imagePart: MultipartBody.Part = MultipartBody.Part.createFormData("image", image.name, requestFile)
            val usernamePart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), username
            )
            val birthdatePart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), birthdate
            )
            val genderPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), gender.toString()
            )
            val heightPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), height.toString()
            )
            val weightPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), weight.toString()
            )
            val methodPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), _method
            )
            return apiService.updateProfile(
                "Bearer $token",
                usernamePart,
                birthdatePart,
                genderPart,
                heightPart,
                weightPart,
                imagePart,
                methodPart
            )
        } else {
            val usernamePart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), username
            )
            val birthdatePart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), birthdate
            )
            val genderPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), gender.toString()
            )
            val heightPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), height.toString()
            )
            val weightPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), weight.toString()
            )
            val methodPart: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(), _method
            )
            return apiService.updateProfile(
                "Bearer $token",
                usernamePart,
                birthdatePart,
                genderPart,
                heightPart,
                weightPart,
                null,
                methodPart
            )
        }
    }

    suspend fun getAllMyProfile(token: String) : GetProfilesResponse {
        return apiService.getAllMyProfile("Bearer $token")
    }

}