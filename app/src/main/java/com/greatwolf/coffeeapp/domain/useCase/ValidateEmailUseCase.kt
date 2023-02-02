package com.greatwolf.coffeeapp.domain.useCase

import android.util.Patterns
import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.domain.util.UiText
import com.greatwolf.coffeeapp.domain.util.ValidationResult
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    resId = R.string.err_email_blank
                )
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    resId = R.string.err_not_valid_email
                )
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}