package com.greatwolf.coffeeapp.domain.useCase

import com.greatwolf.coffeeapp.domain.repository.Repository
import javax.inject.Inject

class PasswordRecoveryUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(email: String) = repository.recoveryPasswordUser(email)
}