package com.greatwolf.coffeeapp.domain.useCase

import com.google.firebase.auth.UserProfileChangeRequest
import com.greatwolf.coffeeapp.domain.repository.Repository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(userProfile: UserProfileChangeRequest) = repository.updateProfile(userProfile)
}