package com.greatwolf.coffeeapp.domain.util

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)