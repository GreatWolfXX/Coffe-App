package com.greatwolf.coffeeapp.domain.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.greatwolf.coffeeapp.domain.model.Coffee
import com.greatwolf.coffeeapp.domain.util.Result

interface Repository {
    suspend fun getCoffees(): Result<List<Coffee>>
    suspend fun getCoffeeById(coffeeId: String): Result<Coffee>
    suspend fun registrationUser(
        email: String,
        password: String
    ): Result<AuthResult>

    suspend fun loginUser(
        email: String,
        password: String
    ): Result<AuthResult>

    suspend fun updateProfile(userProfile: UserProfileChangeRequest): Result<Void>
    suspend fun recoveryPasswordUser(email: String): Result<Void>
    suspend fun getCurrentUser(): Result<FirebaseUser?>
    suspend fun logout(): Result<Unit>
}