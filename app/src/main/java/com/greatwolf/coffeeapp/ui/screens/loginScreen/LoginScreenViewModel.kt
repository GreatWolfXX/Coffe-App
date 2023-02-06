package com.greatwolf.coffeeapp.ui.screens.loginScreen

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
class LoginScreenViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _LoginScreenState: MutableStateFlow<LoginScreenState> =
        MutableStateFlow(LoginScreenState())

    val loginScreenState = _LoginScreenState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = LoginScreenState()
    )

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.EmailChanged -> {
                setCoffeeRegistrationState(
                    state = loginScreenState.value.copy(
                        email = event.email
                    )
                )
            }
            is LoginScreenEvent.PasswordChanged -> {
                setCoffeeRegistrationState(
                    state = loginScreenState.value.copy(
                        password = event.password
                    )
                )
            }
            is LoginScreenEvent.Submit -> {
                setCoffeeRegistrationState(
                    state = loginScreenState.value.copy(
                        isLoading = true
                    )
                )
                submitData()
            }
        }
    }

    private fun setCoffeeRegistrationState(state: LoginScreenState) {
        _LoginScreenState.update {
            state
        }
    }

    private fun submitData() {
        val emailResult = validateEmailUseCase.execute(loginScreenState.value.email)
        val passwordResult = validatePasswordUseCase.execute(loginScreenState.value.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            setCoffeeRegistrationState(
                loginScreenState.value.copy(
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
                loginScreenState.value.email,
                loginScreenState.value.password
            )
            when(response) {
                is Result.Success -> {
                    setCoffeeRegistrationState(
                        state = loginScreenState.value.copy(
                            isLoading = false
                        )
                    )
                    validationEventChannel.send(ValidationEvent.Success)
                }
                is Result.Error -> setCoffeeRegistrationState(
                    state = loginScreenState.value.copy(
                        isLoading = false,
                        isError = response.exception.message
                    )
                )
            }
        }
    }

}