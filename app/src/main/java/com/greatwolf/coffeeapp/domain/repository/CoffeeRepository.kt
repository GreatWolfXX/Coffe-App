package com.greatwolf.coffeeapp.domain.repository

import com.greatwolf.coffeeapp.domain.model.Coffee
import com.greatwolf.coffeeapp.domain.util.Result

interface CoffeeRepository {
    suspend fun getCoffees(): Result<List<Coffee>>
}