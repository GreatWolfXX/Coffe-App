package com.greatwolf.coffeeapp.ui.screens.coffeeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greatwolf.coffeeapp.domain.model.Coffee
import com.greatwolf.coffeeapp.domain.useCase.GetCoffeesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.greatwolf.coffeeapp.domain.util.Result
import kotlinx.coroutines.flow.update

@HiltViewModel
class CoffeeListViewModel @Inject constructor(
    private val getCoffeesUseCase: GetCoffeesUseCase
): ViewModel() {
    private val _coffeeListState: MutableStateFlow<CoffeeListState> =
        MutableStateFlow(CoffeeListState.Loading)

    val coffeeListState = _coffeeListState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = CoffeeListState.Loading
    )

    fun fetchCoffees() {
        viewModelScope.launch {
            val response = getCoffeesUseCase.invoke()
            handleGetCoffeesResponse(response)
        }
    }

    private fun handleGetCoffeesResponse(response: Result<List<Coffee>>) {
        when(response) {
            is Result.Success -> setCoffeeListState(CoffeeListState.Success(response.data))
            is Result.Error -> setCoffeeListState(CoffeeListState.Error(response.exception))
        }
    }

    private fun setCoffeeListState(state: CoffeeListState) {
        _coffeeListState.update {
            state
        }
    }
}