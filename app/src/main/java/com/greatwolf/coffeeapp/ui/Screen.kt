package com.greatwolf.coffeeapp.ui

sealed class Screen(val route: String) {
    object CoffeeListScreen: Screen("coffee_list_screen")
    object CoffeePreferencesScreen: Screen("coffee_preferences_screen")
    object CoffeeAuthScreen: Screen("coffee_auth_screen")
    object CoffeeLoginScreen: Screen("coffee_login_screen")
    object CoffeeRegisterScreen: Screen("coffee_register_screen")
}