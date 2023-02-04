package com.greatwolf.coffeeapp.ui.screens.coffeeRegister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greatwolf.coffeeapp.domain.useCase.*
import com.greatwolf.coffeeapp.domain.util.Result
import com.greatwolf.coffeeapp.domain.util.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeRegistrationViewModel @Inject constructor(
    private val validateFullNameUseCase: ValidateFullNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase,
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {

    private val _coffeeRegistrationState: MutableStateFlow<CoffeeRegistrationState> =
        MutableStateFlow(CoffeeRegistrationState())

    val coffeeRegistrationState = _coffeeRegistrationState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = CoffeeRegistrationState()
    )

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: CoffeeRegistrationEvent) {
        when (event) {
            is CoffeeRegistrationEvent.FullNameChanged -> {
                setCoffeeRegistrationState(
                    state = coffeeRegistrationState.value.copy(
                        fullName = event.fullName
                    )
                )
            }
            is CoffeeRegistrationEvent.EmailChanged -> {
                setCoffeeRegistrationState(
                    state = coffeeRegistrationState.value.copy(
                        email = event.email
                    )
                )
            }
            is CoffeeRegistrationEvent.PasswordChanged -> {
                setCoffeeRegistrationState(
                    state = coffeeRegistrationState.value.copy(
                        password = event.password
                    )
                )
            }
            is CoffeeRegistrationEvent.RepeatedPasswordChanged -> {
                setCoffeeRegistrationState(
                    state = coffeeRegistrationState.value.copy(
                        repeatedPassword = event.repeatedPassword
                    )
                )
            }
            is CoffeeRegistrationEvent.Submit -> {
                setCoffeeRegistrationState(
                    state = coffeeRegistrationState.value.copy(
                        isLoading = true
                    )
                )
                submitData()
            }
        }
    }

    private fun setCoffeeRegistrationState(state: CoffeeRegistrationState) {
        _coffeeRegistrationState.update {
            state
        }
    }

    private fun submitData() {
        val fullNameResult = validateFullNameUseCase.execute(coffeeRegistrationState.value.email)
        val emailResult = validateEmailUseCase.execute(coffeeRegistrationState.value.email)
        val passwordResult = validatePasswordUseCase.execute(coffeeRegistrationState.value.password)
        val repeatedPasswordResult = validateRepeatedPasswordUseCase.execute(
            coffeeRegistrationState.value.password,
            coffeeRegistrationState.value.repeatedPassword
        )

        val hasError = listOf(
            fullNameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult
        ).any { !it.successful }

        if (hasError) {
            setCoffeeRegistrationState(
                coffeeRegistrationState.value.copy(
                    fullNameError = fullNameResult.errorMessage,
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                    repeatedPasswordError = repeatedPasswordResult.errorMessage
                )
            )
            return
        }
        registrationUser()
    }

    private fun registrationUser() {
        viewModelScope.launch {
            val response = registrationUseCase.invoke(
                coffeeRegistrationState.value.email,
                coffeeRegistrationState.value.password
            )
            when(response) {
                is Result.Success -> {
                    setCoffeeRegistrationState(
                        state = coffeeRegistrationState.value.copy(
                            isLoading = false
                        )
                    )
                    validationEventChannel.send(ValidationEvent.Success)
                }
                is Result.Error -> setCoffeeRegistrationState(
                    state = coffeeRegistrationState.value.copy(
                        isLoading = false,
                        isError = response.exception.message
                    )
                )
            }
        }
    }
}