package HelldiversIIApi

import models.HelldiversIIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HelldiversIIApiService {

    @GET("v1/planets")
    fun getPlanets(): Call<HelldiversIIResponse>
}