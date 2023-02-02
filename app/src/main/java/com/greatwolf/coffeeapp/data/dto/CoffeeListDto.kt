package com.greatwolf.coffeeapp.data.dto

class CoffeeListDTO : ArrayList<CoffeeDto>()

data class CoffeeDto(
    val description: String,
    val id: Int,
    val image: String?,
    val title: String
)