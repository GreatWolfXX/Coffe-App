package com.greatwolf.coffeeapp.ui.screens.registerScreen

import com.greatwolf.coffeeapp.domain.util.UiText

data class RegisterScreenState(
    val fullName: String = "",
    val fullNameError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: UiText? = null,
    val isLoading: Boolean = false,
    val isError: String? = null
)