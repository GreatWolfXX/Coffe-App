package com.greatwolf.coffeeapp.ui.screens.coffeeLogin

import com.greatwolf.coffeeapp.domain.util.UiText

data class CoffeeLoginState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null
)