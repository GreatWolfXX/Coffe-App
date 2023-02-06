package com.greatwolf.coffeeapp.ui.screens.passwordRecoveryScreen

sealed class PasswordRecoveryScreenEvent {
    data class EmailChanged(val email: String) : PasswordRecoveryScreenEvent()
    object Submit : PasswordRecoveryScreenEvent()
}