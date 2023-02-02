package com.greatwolf.coffeeapp.domain.useCase

import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.domain.util.UiText
import com.greatwolf.coffeeapp.domain.util.ValidationResult
import javax.inject.Inject

class ValidateRepeatedPasswordUseCase @Inject constructor() {

    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    resId = R.string.err_passwords_match
                )
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}