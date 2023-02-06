package com.greatwolf.coffeeapp.ui.screens.listScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greatwolf.coffeeapp.domain.model.Coffee
import com.greatwolf.coffeeapp.domain.useCase.GetCoffeesUseCase
import com.greatwolf.coffeeapp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val getCoffeesUseCase: GetCoffeesUseCase
) : ViewModel() {
    private val _ListScreenState: MutableStateFlow<ListScreenState> =
        MutableStateFlow(ListScreenState.Loading)

    val listScreenState = _ListScreenState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = ListScreenState.Loading
    )

    fun fetchCoffees() {
        viewModelScope.launch {
            val response = getCoffeesUseCase.invoke()
            handleGetCoffeesResponse(response)
        }
    }

    private fun handleGetCoffeesResponse(response: Result<List<Coffee>>) {
        when (response) {
            is Result.Success -> setCoffeeListState(ListScreenState.Success(response.data))
            is Result.Error -> setCoffeeListState(ListScreenState.Error(response.exception))
        }
    }

    private fun setCoffeeListState(state: ListScreenState) {
        _ListScreenState.update {
            state
        }
    }
}