package com.dicoding.nutrient.api

import com.dicoding.nutrient.data.model.response.assestment.AssestmentResponse
import com.dicoding.nutrient.data.model.response.login.LoginResponse
import com.dicoding.nutrient.data.model.response.register.RegisterResponse
import com.dicoding.nutrient.data.model.response.userstatus.UserStatusResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

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
}