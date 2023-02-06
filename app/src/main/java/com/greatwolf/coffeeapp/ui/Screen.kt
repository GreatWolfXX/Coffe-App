package com.greatwolf.coffeeapp.ui

sealed class Screen(val route: String) {
    object ListScreen: Screen("list_screen")
    object PreferencesScreen: Screen("preferences_screen")
    object AuthScreen: Screen("auth_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
}