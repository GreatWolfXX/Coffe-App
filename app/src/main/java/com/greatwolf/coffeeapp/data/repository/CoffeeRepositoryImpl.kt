package com.greatwolf.coffeeapp.data.repository

import com.greatwolf.coffeeapp.data.api.CoffeeApi
import com.greatwolf.coffeeapp.data.mapper.toDomain
import com.greatwolf.coffeeapp.domain.model.Coffee
import com.greatwolf.coffeeapp.domain.repository.CoffeeRepository
import com.greatwolf.coffeeapp.domain.util.Result

class CoffeeRepositoryImpl(
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
}