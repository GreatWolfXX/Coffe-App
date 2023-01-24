package com.greatwolf.coffeapp.domain.model

data class CoffeeDTOItem(
    val description: String,
    val id: Int,
    val image: String,
    val ingredients: List<String>,
    val title: String
)