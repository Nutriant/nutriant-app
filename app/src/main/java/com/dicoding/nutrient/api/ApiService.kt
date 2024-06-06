package com.dicoding.nutrient.api

import com.dicoding.nutrient.data.model.response.register.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
}