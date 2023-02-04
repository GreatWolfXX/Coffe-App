package com.greatwolf.coffeeapp.domain.util

sealed class ValidationEvent {
    object Success: ValidationEvent()
}