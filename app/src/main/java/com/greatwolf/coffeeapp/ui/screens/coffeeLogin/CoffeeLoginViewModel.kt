package com.greatwolf.coffeeapp.ui.screens.coffeeLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greatwolf.coffeeapp.domain.useCase.LoginUseCase
import com.greatwolf.coffeeapp.domain.useCase.ValidateEmailUseCase
import com.greatwolf.coffeeapp.domain.useCase.ValidatePasswordUseCase
import com.greatwolf.coffeeapp.domain.util.Result
import com.greatwolf.coffeeapp.domain.util.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeLoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _coffeeLoginState: MutableStateFlow<CoffeeLoginState> =
        MutableStateFlow(CoffeeLoginState())

    val coffeeLoginState = _coffeeLoginState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = CoffeeLoginState()
    )

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: CoffeeLoginEvent) {
        when (event) {
            is CoffeeLoginEvent.EmailChanged -> {
                setCoffeeRegistrationState(
                    state = coffeeLoginState.value.copy(
                        email = event.email
                    )
                )
            }
            is CoffeeLoginEvent.PasswordChanged -> {
                setCoffeeRegistrationState(
                    state = coffeeLoginState.value.copy(
                        password = event.password
                    )
                )
            }
            is CoffeeLoginEvent.Submit -> {
                setCoffeeRegistrationState(
                    state = coffeeLoginState.value.copy(
                        isLoading = true
                    )
                )
                submitData()
            }
        }
    }

    private fun setCoffeeRegistrationState(state: CoffeeLoginState) {
        _coffeeLoginState.update {
            state
        }
    }

    private fun submitData() {
        val emailResult = validateEmailUseCase.execute(coffeeLoginState.value.email)
        val passwordResult = validatePasswordUseCase.execute(coffeeLoginState.value.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            setCoffeeRegistrationState(
                coffeeLoginState.value.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                )
            )
            return
        }
        loginUser()
    }

    private fun loginUser() {
        viewModelScope.launch {
            val response = loginUseCase.invoke(
                coffeeLoginState.value.email,
                coffeeLoginState.value.password
            )
            when(response) {
                is Result.Success -> {
                    setCoffeeRegistrationState(
                        state = coffeeLoginState.value.copy(
                            isLoading = false
                        )
                    )
                    validationEventChannel.send(ValidationEvent.Success)
                }
                is Result.Error -> setCoffeeRegistrationState(
                    state = coffeeLoginState.value.copy(
                        isLoading = false,
                        isError = response.exception.message
                    )
                )
            }
        }
    }

}