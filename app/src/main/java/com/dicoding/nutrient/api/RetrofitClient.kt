package com.dicoding.nutrient.api

import com.dicoding.nutrient.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL_LARAVEL = BuildConfig.BASE_API_URL_LARAVEL
    val instanceApiLaravel: ApiService by lazy {
        createRetrofitService(BASE_URL_LARAVEL)
    }
    fun createOtherApiService(baseUrl: String): ApiService {
        return createRetrofitService(baseUrl)
    }

    private fun createRetrofitService(baseUrl: String): ApiService {
        val loggingInterceptor = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}