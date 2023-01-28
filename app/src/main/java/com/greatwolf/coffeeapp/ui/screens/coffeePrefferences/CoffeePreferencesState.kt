package com.greatwolf.coffeeapp.ui.screens.coffeePrefferences

import com.greatwolf.coffeeapp.domain.model.Coffee

sealed class CoffeePreferencesState {
    object Loading: CoffeePreferencesState()
    data class Success(val coffee: Coffee): CoffeePreferencesState()
    data class Error(val exception: Exception): CoffeePreferencesState()
}