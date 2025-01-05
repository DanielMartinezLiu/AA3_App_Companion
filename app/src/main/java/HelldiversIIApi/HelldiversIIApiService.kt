package HelldiversIIApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HelldiversIIApiService {

    @Get("v1/public/characters")
    fun get
}