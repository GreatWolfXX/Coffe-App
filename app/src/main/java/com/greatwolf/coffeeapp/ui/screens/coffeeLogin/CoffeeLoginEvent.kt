package com.greatwolf.coffeeapp.ui.screens.coffeeLogin

sealed class CoffeeLoginEvent {
    data class EmailChanged(val email: String) : CoffeeLoginEvent()
    data class PasswordChanged(val password: String) : CoffeeLoginEvent()
    object Submit : CoffeeLoginEvent()
}