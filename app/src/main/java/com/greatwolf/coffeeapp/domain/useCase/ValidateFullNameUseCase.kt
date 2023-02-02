package com.greatwolf.coffeeapp.domain.useCase

import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.domain.util.UiText
import com.greatwolf.coffeeapp.domain.util.ValidationResult
import javax.inject.Inject

class ValidateFullNameUseCase @Inject constructor() {

    fun execute(fullName: String): ValidationResult {
        if (fullName.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    resId = R.string.err_full_name_blank
                )
            )
        }
        val containsDigits = fullName.any { it.isDigit() }
        if (!containsDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    resId = R.string.err_full_name_digit
                )
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}