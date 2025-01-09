package MarvelApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MarvelApiInstance {
    private const val BASE_URL = "https://gateway.marvel.com/"

    val apiService: MarvelApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelApiService::class.java)
    }
}