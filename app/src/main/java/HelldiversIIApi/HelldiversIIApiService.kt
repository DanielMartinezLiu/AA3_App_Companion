package HelldiversIIApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HelldiversIIApiService {

    @GET("/v1/planets")
    fun getPlanets( @Query("limit") limit: Int = 20 ): Call<HelldiversIIResponse>
}