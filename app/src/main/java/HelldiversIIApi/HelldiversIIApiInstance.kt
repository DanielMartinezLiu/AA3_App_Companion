package HelldiversIIApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HelldiversIIApiInstance {
    private const val BASE_URL = "https://helldiverstrainingmanual.com/api/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: HelldiversIIApiService by lazy {
        retrofit.create(HelldiversIIApiService::class.java)
    }
}