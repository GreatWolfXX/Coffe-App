package com.greatwolf.coffeeapp.ui.screens.registerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.greatwolf.coffeeapp.domain.useCase.*
import com.greatwolf.coffeeapp.domain.util.Result
import com.greatwolf.coffeeapp.domain.util.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val validateFullNameUseCase: ValidateFullNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _registerScreenState: MutableStateFlow<RegisterScreenState> =
        MutableStateFlow(RegisterScreenState())

    val registerScreenState = _registerScreenState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = RegisterScreenState()
    )

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegisterScreenEvent) {
        when (event) {
            is RegisterScreenEvent.FullNameChanged -> {
                setRegistrationState(
                    state = registerScreenState.value.copy(
                        fullName = event.fullName
                    )
                )
            }
            is RegisterScreenEvent.EmailChanged -> {
                setRegistrationState(
                    state = registerScreenState.value.copy(
                        email = event.email
                    )
                )
            }
            is RegisterScreenEvent.PasswordChanged -> {
                setRegistrationState(
                    state = registerScreenState.value.copy(
                        password = event.password
                    )
                )
            }
            is RegisterScreenEvent.RepeatedPasswordChanged -> {
                setRegistrationState(
                    state = registerScreenState.value.copy(
                        repeatedPassword = event.repeatedPassword
                    )
                )
            }
            is RegisterScreenEvent.Submit -> {
                setRegistrationState(
                    state = registerScreenState.value.copy(
                        isLoading = true
                    )
                )
                submitData()
            }
        }
    }

    private fun setRegistrationState(state: RegisterScreenState) {
        _registerScreenState.update {
            state
        }
    }

    private fun submitData() {
        val fullNameResult = validateFullNameUseCase.execute(registerScreenState.value.fullName)
        val emailResult = validateEmailUseCase.execute(registerScreenState.value.email)
        val passwordResult = validatePasswordUseCase.execute(registerScreenState.value.password)
        val repeatedPasswordResult = validateRepeatedPasswordUseCase.execute(
            registerScreenState.value.password,
            registerScreenState.value.repeatedPassword
        )

        val hasError = listOf(
            fullNameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult
        ).any { !it.successful }

        if (hasError) {
            setRegistrationState(
                registerScreenState.value.copy(
                    fullNameError = fullNameResult.errorMessage,
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                    repeatedPasswordError = repeatedPasswordResult.errorMessage,
                    isLoading = false
                )
            )
            return
        }
        registrationUser()
    }

    private fun registrationUser() {
        viewModelScope.launch {
            val response = registrationUseCase.invoke(
                registerScreenState.value.email,
                registerScreenState.value.password
            )
            when (response) {
                is Result.Success -> {
                    updateProfile()
                }
                is Result.Error -> setRegistrationState(
                    state = registerScreenState.value.copy(
                        isLoading = false,
                        isError = response.exception.message
                    )
                )
            }
        }
    }

    private fun updateProfile() {
        val profileUpdates = userProfileChangeRequest {
            displayName = registerScreenState.value.fullName
        }
        viewModelScope.launch {
            val response = updateProfileUseCase.invoke(profileUpdates)
            when (response) {
                is Result.Success -> {
                    validationEventChannel.send(ValidationEvent.Success)
                }
                is Result.Error -> setRegistrationState(
                    state = registerScreenState.value.copy(
                        isLoading = false,
                        isError = response.exception.message
                    )
                )
            }
        }
    }
}