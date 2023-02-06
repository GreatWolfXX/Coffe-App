package com.greatwolf.coffeeapp.ui.screens.passwordRecoveryScreen

import com.greatwolf.coffeeapp.domain.util.UiText

data class PasswordRecoveryScreenState(
    val email: String = "",
    val emailError: UiText? = null,
    val isLoading: Boolean = false,
    val isError: String? = null
)