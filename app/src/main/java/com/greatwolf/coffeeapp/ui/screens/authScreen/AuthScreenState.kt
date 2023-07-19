package com.greatwolf.coffeeapp.ui.screens.authScreen

sealed class AuthScreenState {
    object Loading: AuthScreenState()
    object SuccessAuth: AuthScreenState()
    object NewAuth: AuthScreenState()
}