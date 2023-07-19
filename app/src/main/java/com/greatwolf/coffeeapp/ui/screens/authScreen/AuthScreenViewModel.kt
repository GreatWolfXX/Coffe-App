package com.greatwolf.coffeeapp.ui.screens.authScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.greatwolf.coffeeapp.common.Constants.CREDENTIALS_EMAIL_KEY
import com.greatwolf.coffeeapp.common.Constants.CREDENTIALS_PWD_KEY
import com.greatwolf.coffeeapp.domain.useCase.LoginUseCase
import com.greatwolf.coffeeapp.domain.util.Result
import com.greatwolf.coffeeapp.domain.util.readString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val appContext: Application,
    private val loginUseCase: LoginUseCase
) : AndroidViewModel(appContext) {
    private val _AuthScreenState: MutableStateFlow<AuthScreenState> =
        MutableStateFlow(AuthScreenState.Loading)

    val authScreenState = _AuthScreenState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = AuthScreenState.Loading
    )

    fun getCredential() {
        viewModelScope.launch {
            val emailCredential = appContext.readString(CREDENTIALS_EMAIL_KEY).first()
            val passwordCredential = appContext.readString(CREDENTIALS_PWD_KEY).first()
            handleCredentials(
                email = emailCredential,
                password = passwordCredential
            )
        }
    }

    private fun handleCredentials(
        email: String,
        password: String
    ) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            loginUser(
                email = email,
                password = password
            )
        } else {
            setAuthState(AuthScreenState.NewAuth)
        }
    }

    private fun setAuthState(state: AuthScreenState) {
        _AuthScreenState.update {
            state
        }
    }

    private fun loginUser(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            val response = loginUseCase.invoke(
                email,
                password
            )
            when (response) {
                is Result.Success -> setAuthState(
                    state = AuthScreenState.SuccessAuth
                )
                is Result.Error -> setAuthState(
                    state = AuthScreenState.NewAuth
                )
            }
        }
    }
}