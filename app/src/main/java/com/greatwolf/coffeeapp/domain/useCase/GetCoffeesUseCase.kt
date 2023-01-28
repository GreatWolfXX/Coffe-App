package com.greatwolf.coffeeapp.domain.useCase

import com.greatwolf.coffeeapp.domain.repository.CoffeeRepository
import javax.inject.Inject

class GetCoffeesUseCase @Inject constructor(
    private val coffeeRepository: CoffeeRepository
) {
    suspend operator fun invoke() = coffeeRepository.getCoffees()
}
