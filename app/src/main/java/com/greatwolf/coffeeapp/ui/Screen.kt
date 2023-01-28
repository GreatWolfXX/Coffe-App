package com.greatwolf.coffeeapp.ui

sealed class Screen(val route: String) {
    object CoffeeListScreen: Screen("coffee_list_screen")
    object CoffeePreferencesScreen: Screen("coffee_preferences_screen")
}