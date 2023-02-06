package com.greatwolf.coffeeapp.ui.screens.listScreen

import com.greatwolf.coffeeapp.domain.model.Coffee

sealed class ListScreenState {
    object Loading: ListScreenState()
    data class Success(val listOfCoffees: List<Coffee>): ListScreenState()
    data class Error(val exception: Exception): ListScreenState()
}