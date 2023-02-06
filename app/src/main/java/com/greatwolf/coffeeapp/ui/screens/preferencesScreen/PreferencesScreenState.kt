package com.greatwolf.coffeeapp.ui.screens.preferencesScreen

import com.greatwolf.coffeeapp.domain.model.Coffee

sealed class PreferencesScreenState {
    object Loading: PreferencesScreenState()
    data class Success(val coffee: Coffee): PreferencesScreenState()
    data class Error(val exception: Exception): PreferencesScreenState()
}