package com.greatwolf.coffeeapp.ui.screens.coffeeList

import com.greatwolf.coffeeapp.domain.model.Coffee

sealed class CoffeeListState {
    object Loading: CoffeeListState()
    data class Success(val listOfCoffees: List<Coffee>): CoffeeListState()
    data class Error(val exception: Exception): CoffeeListState()
}