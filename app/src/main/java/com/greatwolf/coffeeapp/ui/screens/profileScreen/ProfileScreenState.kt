package com.greatwolf.coffeeapp.ui.screens.profileScreen

import com.google.firebase.auth.FirebaseUser

sealed class ProfileScreenState {
    object Loading: ProfileScreenState()
    data class Success(val user: FirebaseUser?): ProfileScreenState()
    data class Error(val exception: Exception): ProfileScreenState()
}