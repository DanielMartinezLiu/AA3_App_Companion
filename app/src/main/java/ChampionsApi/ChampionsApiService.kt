package ChampionsApi

import models.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChampionsApiService {

    @GET("champion.json")
    fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("limit") limit: Int = 20
    ): Call<CharacterResponse>
}