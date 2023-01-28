package com.greatwolf.coffeeapp.domain.model

data class Coffee(
    val description: String,
    val id: Int,
    val image: String,
    val ingredients: List<String>,
    val title: String
)