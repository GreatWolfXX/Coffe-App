package com.greatwolf.coffeeapp.domain.useCase

import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.domain.util.UiText
import com.greatwolf.coffeeapp.domain.util.ValidationResult
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    fun execute(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    resId = R.string.err_password_short
                )
            )
        }
        val containsLettersAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    resId = R.string.err_password_not_contain
                )
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}