package com.greatwolf.coffeeapp.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.greatwolf.coffeeapp.data.api.CoffeeApi
import com.greatwolf.coffeeapp.data.mapper.toDomain
import com.greatwolf.coffeeapp.domain.model.Coffee
import com.greatwolf.coffeeapp.domain.repository.CoffeeRepository
import com.greatwolf.coffeeapp.domain.util.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CoffeeRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val coffeeApi: CoffeeApi
) : CoffeeRepository {
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
}