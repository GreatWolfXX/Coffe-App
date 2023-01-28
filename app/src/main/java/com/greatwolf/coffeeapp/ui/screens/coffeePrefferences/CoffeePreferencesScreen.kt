package com.greatwolf.coffeeapp.ui.screens.coffeePrefferences

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.domain.model.Coffee
import com.greatwolf.coffeeapp.ui.components.CoffeeError
import com.greatwolf.coffeeapp.ui.components.CoffeeNavBar
import com.greatwolf.coffeeapp.ui.components.LoadingView
import com.greatwolf.coffeeapp.ui.theme.coffee_image_128
import com.greatwolf.coffeeapp.ui.theme.spacing_32
import com.greatwolf.coffeeapp.ui.theme.spacing_50
import com.greatwolf.coffeeapp.ui.theme.spacing_64

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeePreferencesScreen(
    viewModel: CoffeePreferencesViewModel = hiltViewModel()
) {
    val state = viewModel.coffeePreferencesState.collectAsState()
    Scaffold(
        content = { paddingValues ->
            BoxWithConstraints() {
                CoffeePreferencesContent(
                    state = state.value,
                    paddingValues = paddingValues
                )
            }
        })
}

@Composable
fun CoffeePreferencesContent(state: CoffeePreferencesState, paddingValues: PaddingValues) {
    Column {
        CoffeeNavBar(
            onClickArrowBack = { /*TODO*/ },
            title = stringResource(id = R.string.title_preferences),
            paddingValues = PaddingValues(horizontal = spacing_32)
        )
        when(state) {
            is CoffeePreferencesState.Success -> CoffeePreferencesSuccess(state.coffee)
            is CoffeePreferencesState.Loading -> LoadingView()
            is CoffeePreferencesState.Error -> CoffeeError(exception = state.exception.message)
        }
    }
}

@Composable
fun CoffeePreferencesSuccess(
    coffee: Coffee
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_header),
            contentDescription = stringResource(id = R.string.header_description),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )
        AsyncImage(
            model = coffee.image,
            contentDescription = coffee.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(coffee_image_128)
                .clip(RoundedCornerShape(spacing_50))
        )
    }
}

@Preview
@Composable
private fun PreviewCoffeePreferencesScreen() {
    CoffeePreferencesScreen()
}