package com.dicoding.nutrient.api

import com.dicoding.nutrient.data.model.response.assestment.AssestmentResponse
import com.dicoding.nutrient.data.model.response.login.LoginResponse
import com.dicoding.nutrient.data.model.response.myprofile.MyProfileResponse
import com.dicoding.nutrient.data.model.response.profiles.GetProfilesResponse
import com.dicoding.nutrient.data.model.response.profiles.UpdateProfileResponse
import com.dicoding.nutrient.data.model.response.register.RegisterResponse
import com.dicoding.nutrient.data.model.response.userstatus.UserStatusResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("api/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirm: String,
        @Field("birthdate") birthdate: String,
        @Field("gender") gender: Int,
    ) : RegisterResponse

    @FormUrlEncoded
    @POST("api/login")
    @Headers("Accept: application/json")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

    @POST("api/logout")
    @Headers("Accept: application/json")
    suspend fun logout(@Header("Authorization") token: String) : Int

    @GET("api/user-status")
    @Headers("Accept: application/json")
    suspend fun userStatus(@Header("Authorization") token: String) : UserStatusResponse

    @FormUrlEncoded
    @POST("api/fill-assestment")
    @Headers("Accept: application/json")
    suspend fun fillAssestment(
        @Header("Authorization") token: String,
        @Field("weight") weight: Double,
        @Field("height") height: Double
    ) : AssestmentResponse

    @GET("api/myprofile")
    @Headers("Accept: application/json")
    suspend fun getMyProfile(@Header("Authorization") token: String) : MyProfileResponse

    @Multipart
    @POST("api/profiles")
    @Headers("Accept: application/json")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Part("username") username: RequestBody,
        @Part("birthdate") birthdate: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("height") height: RequestBody,
        @Part("weight") weight: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("_method") _method: RequestBody
    ) : UpdateProfileResponse

    @GET("api/profiles")
    @Headers("Accept: application/json")
    suspend fun getAllMyProfile(@Header("Authorization") token: String) : GetProfilesResponse
}