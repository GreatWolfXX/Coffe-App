package com.greatwolf.coffeeapp.ui.screens.coffeeRegister

sealed class CoffeeRegistrationEvent {
    data class FullNameChanged(val fullName: String) : CoffeeRegistrationEvent()
    data class EmailChanged(val email: String) : CoffeeRegistrationEvent()
    data class PasswordChanged(val password: String) : CoffeeRegistrationEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : CoffeeRegistrationEvent()
    object Submit : CoffeeRegistrationEvent()
}