package com.greatwolf.coffeeapp.ui.screens.registerScreen

sealed class RegisterScreenEvent {
    data class FullNameChanged(val fullName: String) : RegisterScreenEvent()
    data class EmailChanged(val email: String) : RegisterScreenEvent()
    data class PasswordChanged(val password: String) : RegisterScreenEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : RegisterScreenEvent()
    object Submit : RegisterScreenEvent()
}