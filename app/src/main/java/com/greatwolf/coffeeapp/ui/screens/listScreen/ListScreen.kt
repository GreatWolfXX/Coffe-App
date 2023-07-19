package com.greatwolf.coffeeapp.ui.screens.listScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.domain.model.Coffee
import com.greatwolf.coffeeapp.ui.Screen
import com.greatwolf.coffeeapp.ui.components.*
import com.greatwolf.coffeeapp.ui.theme.*

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ListScreen(
        navController: NavController,
        viewModel: ListScreenViewModel = hiltViewModel()
    ) {
        val state = viewModel.listScreenState.collectAsState()
        LaunchedEffect(Unit) {
            viewModel.fetchCoffees()
        }
        Scaffold(
            content = { paddingValues ->
                ListContent(
                    navController,
                    state = state.value,
                    paddingValues = paddingValues
                )
            })
    }


@Composable
fun ListContent(
    navController: NavController,
    state: ListScreenState,
    paddingValues: PaddingValues
) {
    Column {
        NavBar(
            onClickArrowBack = {
                navController.navigate(Screen.AuthScreen.route)
            },
            title = stringResource(id = R.string.title_menu),
            paddingValues = PaddingValues(horizontal = spacing_32)
        )
        when (state) {
            is ListScreenState.Success -> {
                Log.d("checkerBlink", "successList")
                ListSuccess(
                    navController = navController,
                    listOfCoffees = state.listOfCoffees
                )
            }

            is ListScreenState.Loading -> {
                Log.d("checkerBlink", "loadingList")
                LoadingView()
            }

            is ListScreenState.Error -> {
                Log.d("checkerBlink", "errorList")
                ErrorView(exception = state.exception.message)
            }

        }
    }
}


@Composable
fun ListSuccess(
    navController: NavController,
    listOfCoffees: List<Coffee>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(listOfCoffees) { coffee ->
            Card(
                navController,
                coffee = coffee
            )
        }
    }
}

@Composable
fun Card(
    navController: NavController,
    coffee: Coffee
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.PreferencesScreen.route + "/${coffee.id}")
            },
        shape = RoundedCornerShape(spacing_0),
        elevation = CardDefaults.cardElevation(spacing_8)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(spacing_32, spacing_16, spacing_24, spacing_16)
        ) {
            AsyncImage(
                model = coffee.image,
                contentDescription = coffee.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(coffee_image_64)
                    .clip(RoundedCornerShape(spacing_50))
            )
            Text(
                coffee.title,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = roboto,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Start,
                fontSize = sizing_20,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = spacing_32)
            )
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.ic_arrow_back),
                tint = BrownCoffee,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .scale(1.5f)
            )
        }

    }
}
