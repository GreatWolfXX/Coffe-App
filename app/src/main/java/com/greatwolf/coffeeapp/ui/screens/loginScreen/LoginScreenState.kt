package com.greatwolf.coffeeapp.ui.screens.loginScreen

import com.greatwolf.coffeeapp.domain.util.UiText

data class LoginScreenState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isLoading: Boolean = false,
    val isError: String? = null
)