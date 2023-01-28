package com.greatwolf.coffeeapp.data.api

import com.greatwolf.coffeeapp.data.dto.CoffeeDto
import com.greatwolf.coffeeapp.data.dto.CoffeeListDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface CoffeeApi {
    @GET("coffee/hot")
    suspend fun getCoffees() : CoffeeListDTO

    @GET("coffee/hot/{coffeeId}")
    suspend fun getCoffeeById(
        @Path("coffeeId") coffeeId: String
    ) : CoffeeDto
}