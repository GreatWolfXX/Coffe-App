package com.greatwolf.coffeeapp.domain.useCase

import com.greatwolf.coffeeapp.domain.repository.CoffeeRepository
import javax.inject.Inject

class GetCoffeeUseCase @Inject constructor(
    private val coffeeRepository: CoffeeRepository
) {
    suspend operator fun invoke(coffeeId: String) = coffeeRepository.getCoffeeById(coffeeId)
}