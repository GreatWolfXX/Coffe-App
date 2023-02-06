package com.greatwolf.coffeeapp.ui.screens.loginScreen

sealed class LoginScreenEvent {
    data class EmailChanged(val email: String) : LoginScreenEvent()
    data class PasswordChanged(val password: String) : LoginScreenEvent()
    object Submit : LoginScreenEvent()
}