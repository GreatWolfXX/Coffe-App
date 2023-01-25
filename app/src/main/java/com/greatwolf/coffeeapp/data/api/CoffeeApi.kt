package com.greatwolf.coffeeapp.data.api

import com.greatwolf.coffeeapp.data.dto.CoffeeListDTO
import retrofit2.http.GET

const val BASE_URL = "https://api.sampleapis.com/"

interface CoffeeApi {
    @GET("coffee/hot")
    suspend fun getCoffees() : CoffeeListDTO
}