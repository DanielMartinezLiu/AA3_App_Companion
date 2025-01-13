package GamesApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GamesApiInstance {
    private const val BASE_URL = "https://api.rawg.io/api/"

    val apiService: GamesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GamesApiService::class.java)
    }
}