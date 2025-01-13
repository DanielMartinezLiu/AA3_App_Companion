package GamesApi

import models.DarkSoulsGamesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesApiService {

    @GET("games?key=7edb2d0194fd4d6a8b214af51e3dca7d&search=dark+souls+iii")
    fun getCharacters(
        @Query("apiKey") apiKey: String,
        @Query("limit") limit: Int = 20
    ): Call<DarkSoulsGamesResponse>
}