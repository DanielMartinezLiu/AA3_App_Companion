package ChampionsApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ChampionsApiInstance {
    private const val BASE_URL = "https://ddragon.leagueoflegends.com/cdn/15.1.1/data/en_US/"

    val apiService: ChampionsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChampionsApiService::class.java)
    }
}