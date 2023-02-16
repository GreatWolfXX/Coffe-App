package com.greatwolf.coffeeapp.domain.util

sealed class LogOutEvent {
    object Success: LogOutEvent()
}