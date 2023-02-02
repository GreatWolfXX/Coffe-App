package com.greatwolf.coffeeapp.ui.screens.coffeeRegister

import com.greatwolf.coffeeapp.domain.util.UiText

data class CoffeeRegistrationState(
    val fullName: String = "",
    val fullNameError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: UiText? = null
)