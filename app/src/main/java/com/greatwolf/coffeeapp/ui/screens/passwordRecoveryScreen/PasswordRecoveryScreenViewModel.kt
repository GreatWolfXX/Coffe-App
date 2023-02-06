package com.greatwolf.coffeeapp.ui.screens.passwordRecoveryScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greatwolf.coffeeapp.domain.useCase.PasswordRecoveryUseCase
import com.greatwolf.coffeeapp.domain.useCase.ValidateEmailUseCase
import com.greatwolf.coffeeapp.domain.util.Result
import com.greatwolf.coffeeapp.domain.util.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordRecoveryScreenViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val passwordRecoveryUseCase: PasswordRecoveryUseCase
) : ViewModel() {

    private val _PasswordRecoveryScreenState: MutableStateFlow<PasswordRecoveryScreenState> =
        MutableStateFlow(PasswordRecoveryScreenState())

    val passwordRecoveryScreenState = _PasswordRecoveryScreenState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = PasswordRecoveryScreenState()
    )

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: PasswordRecoveryScreenEvent) {
        when (event) {
            is PasswordRecoveryScreenEvent.EmailChanged -> {
                setPasswordRecoveryState(
                    state = passwordRecoveryScreenState.value.copy(
                        email = event.email
                    )
                )
            }
            is PasswordRecoveryScreenEvent.Submit -> {
                setPasswordRecoveryState(
                    state = passwordRecoveryScreenState.value.copy(
                        isLoading = true
                    )
                )
                submitData()
            }
        }
    }

    private fun setPasswordRecoveryState(state: PasswordRecoveryScreenState) {
        _PasswordRecoveryScreenState.update {
            state
        }
    }

    private fun submitData() {
        val emailResult = validateEmailUseCase.execute(passwordRecoveryScreenState.value.email)

        val hasError = listOf(
            emailResult,
        ).any { !it.successful }

        if (hasError) {
            setPasswordRecoveryState(
                passwordRecoveryScreenState.value.copy(
                    emailError = emailResult.errorMessage,
                    isLoading = false
                )
            )
            return
        }
        passwordRecovery()
    }

    private fun passwordRecovery() {
        viewModelScope.launch {
            val response = passwordRecoveryUseCase.invoke(
                passwordRecoveryScreenState.value.email
            )
            when (response) {
                is Result.Success -> {
                    setPasswordRecoveryState(
                        state = passwordRecoveryScreenState.value.copy(
                            isLoading = false
                        )
                    )
                    validationEventChannel.send(ValidationEvent.Success)
                }
                is Result.Error -> setPasswordRecoveryState(
                    state = passwordRecoveryScreenState.value.copy(
                        isLoading = false,
                        isError = response.exception.message
                    )
                )
            }
        }
    }

}