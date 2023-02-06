package com.greatwolf.coffeeapp.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.greatwolf.coffeeapp.data.api.CoffeeApi
import com.greatwolf.coffeeapp.data.mapper.toDomain
import com.greatwolf.coffeeapp.domain.model.Coffee
import com.greatwolf.coffeeapp.domain.repository.Repository
import com.greatwolf.coffeeapp.domain.util.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val coffeeApi: CoffeeApi
) : Repository {
    override suspend fun getCoffees(): Result<List<Coffee>> {
        return try {
            Result.Success(coffeeApi.getCoffees().toDomain())
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    override suspend fun getCoffeeById(coffeeId: String): Result<Coffee> {
        return try {
            Result.Success(coffeeApi.getCoffeeById(coffeeId).toDomain())
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    override suspend fun registrationUser(email: String, password: String): Result<AuthResult> {
        return try {
            Result.Success(firebaseAuth.createUserWithEmailAndPassword(email, password).await())
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    override suspend fun loginUser(email: String, password: String): Result<AuthResult> {
        return try {
            Result.Success(firebaseAuth.signInWithEmailAndPassword(email, password).await())
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    override suspend fun recoveryPasswordUser(email: String): Result<Void> {
        return try {
            Result.Success(firebaseAuth.sendPasswordResetEmail(email).await())
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    override suspend fun updateProfile(userProfile: UserProfileChangeRequest): Result<Void> {
        return try {
            Result.Success(firebaseAuth.currentUser!!.updateProfile(userProfile).await())
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}