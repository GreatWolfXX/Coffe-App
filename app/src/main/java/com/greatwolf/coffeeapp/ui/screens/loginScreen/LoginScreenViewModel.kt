package com.greatwolf.coffeeapp.ui.screens.loginScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.greatwolf.coffeeapp.common.Constants
import com.greatwolf.coffeeapp.domain.useCase.LoginUseCase
import com.greatwolf.coffeeapp.domain.useCase.ValidateEmailUseCase
import com.greatwolf.coffeeapp.domain.useCase.ValidatePasswordUseCase
import com.greatwolf.coffeeapp.domain.util.Result
import com.greatwolf.coffeeapp.domain.util.ValidationEvent
import com.greatwolf.coffeeapp.domain.util.writeString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val appContext: Application,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase
) : AndroidViewModel(appContext) {

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
                setRegistrationState(
                    state = loginScreenState.value.copy(
                        email = event.email
                    )
                )
            }
            is LoginScreenEvent.PasswordChanged -> {
                setRegistrationState(
                    state = loginScreenState.value.copy(
                        password = event.password
                    )
                )
            }
            is LoginScreenEvent.Submit -> {
                setRegistrationState(
                    state = loginScreenState.value.copy(
                        isLoading = true
                    )
                )
                submitData()
            }
        }
    }

    private fun setRegistrationState(state: LoginScreenState) {
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
            setRegistrationState(
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
                    setRegistrationState(
                        state = loginScreenState.value.copy(
                            isLoading = false
                        )
                    )
                    saveCredentials()
                    validationEventChannel.send(ValidationEvent.Success)
                }
                is Result.Error -> setRegistrationState(
                    state = loginScreenState.value.copy(
                        isLoading = false,
                        isError = response.exception.message
                    )
                )
            }
        }
    }

    private fun saveCredentials() {
        viewModelScope.launch {
            appContext.writeString(Constants.CREDENTIALS_EMAIL_KEY, loginScreenState.value.email)
            appContext.writeString(Constants.CREDENTIALS_PWD_KEY, loginScreenState.value.password)
        }
    }

}