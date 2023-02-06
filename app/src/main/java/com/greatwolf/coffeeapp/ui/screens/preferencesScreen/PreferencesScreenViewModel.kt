package com.greatwolf.coffeeapp.ui.screens.preferencesScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greatwolf.coffeeapp.common.Constants
import com.greatwolf.coffeeapp.domain.model.Coffee
import com.greatwolf.coffeeapp.domain.useCase.GetCoffeeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.greatwolf.coffeeapp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update

@HiltViewModel
class PreferencesScreenViewModel @Inject constructor(
    private val getCoffeeUseCase: GetCoffeeUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _PreferencesScreenState: MutableStateFlow<PreferencesScreenState> =
        MutableStateFlow(PreferencesScreenState.Loading)

    val preferencesScreenState = _PreferencesScreenState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = PreferencesScreenState.Loading
    )

    init {
        savedStateHandle.get<String>(Constants.PARAM_COFFEE_ID)?.let { coffeeId ->
            fetchCoffeeById(coffeeId = coffeeId)
        }
    }

    private fun fetchCoffeeById(coffeeId: String) {
        viewModelScope.launch {
            val response = getCoffeeUseCase.invoke(coffeeId)
            handleGetCoffeeByIdResponse(response)
        }
    }

    private fun handleGetCoffeeByIdResponse(response: Result<Coffee>) {
        when(response) {
            is Result.Success -> setCoffeePreferencesState(PreferencesScreenState.Success(response.data))
            is Result.Error -> setCoffeePreferencesState(PreferencesScreenState.Error(response.exception))
        }
    }

    private fun setCoffeePreferencesState(state: PreferencesScreenState) {
        _PreferencesScreenState.update {
            state
        }
    }
}