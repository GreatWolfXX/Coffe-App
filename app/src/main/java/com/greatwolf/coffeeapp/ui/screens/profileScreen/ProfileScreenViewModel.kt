package com.greatwolf.coffeeapp.ui.screens.profileScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.greatwolf.coffeeapp.common.Constants
import com.greatwolf.coffeeapp.domain.useCase.GetCurrentUserUseCase
import com.greatwolf.coffeeapp.domain.useCase.LogoutUseCase
import com.greatwolf.coffeeapp.domain.util.LogOutEvent
import com.greatwolf.coffeeapp.domain.util.Result
import com.greatwolf.coffeeapp.domain.util.writeString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val appContext: Application,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase
): AndroidViewModel(appContext) {
    private val _ProfileScreenState: MutableStateFlow<ProfileScreenState> =
        MutableStateFlow(ProfileScreenState.Loading)

    val profileScreenState = _ProfileScreenState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = ProfileScreenState.Loading
    )

    private val logOutEventChannel = Channel<LogOutEvent>()
    val logOutEvents = logOutEventChannel.receiveAsFlow()

    private fun setCoffeeProfileState(state: ProfileScreenState) {
        _ProfileScreenState.update {
            state
        }
    }

    fun onEvent(event: LogOutEvent) {
        when (event) {
            is LogOutEvent.Success -> {
                logoutUser()
            }
        }
    }
    fun fetchCurrentUser() {
        viewModelScope.launch {
            val response = getCurrentUserUseCase.invoke()
            handleGetCurrentUser(response)
        }
    }

    private fun handleGetCurrentUser(response: Result<FirebaseUser?>) {
        when(response) {
            is Result.Success -> setCoffeeProfileState(ProfileScreenState.Success(response.data))
            is Result.Error -> setCoffeeProfileState(ProfileScreenState.Error(response.exception))
        }
    }

    private fun logoutUser() {
        viewModelScope.launch {
            val response = logoutUseCase.invoke()
            handleLogOutUser(response)
        }
    }

    private fun handleLogOutUser(response: Result<Unit>) {
        when(response) {
            is Result.Success -> {
                viewModelScope.launch {
                    clearCredentials()
                    logOutEventChannel.send(LogOutEvent.Success)
                }
            }
            is Result.Error -> setCoffeeProfileState(ProfileScreenState.Error(response.exception))
        }
    }

    private fun clearCredentials() {
        viewModelScope.launch {
            appContext.writeString(Constants.CREDENTIALS_EMAIL_KEY, "")
            appContext.writeString(Constants.CREDENTIALS_PWD_KEY, "")
        }
    }
}