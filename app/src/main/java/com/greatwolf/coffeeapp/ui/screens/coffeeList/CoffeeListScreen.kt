package com.greatwolf.coffeeapp.ui.screens.coffeeList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.domain.model.Coffee
import com.greatwolf.coffeeapp.ui.theme.BrownCoffee
import com.greatwolf.coffeeapp.ui.theme.roboto
import com.greatwolf.coffeeapp.ui.theme.sizing_20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeListScreen(viewModel: CoffeeListViewModel = hiltViewModel()) {
    val state = viewModel.coffeeListState.collectAsState()
    viewModel.fetchCoffees()
    Scaffold(
        content = { paddingValues ->
            CoffeeListContent(
                state = state.value,
                paddingValues = paddingValues
            )
        })
}


@Composable
fun CoffeeListContent(state: CoffeeListState, paddingValues: PaddingValues) {
    when (state) {
        is CoffeeListState.Success -> CoffeeListSuccess(
            listOfCoffees = state.listOfCoffees
        )
        is CoffeeListState.Loading -> LoadingView()
        is CoffeeListState.Error -> CoffeeListError(exception = state.exception.message)
    }
}

@Composable
fun CoffeeListSuccess(listOfCoffees: List<Coffee>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(listOfCoffees) { coffee ->
            CoffeeCard(coffee = coffee)
        }
    }
}

@Composable
fun CoffeeCard(coffee: Coffee) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(spacing_0),
        elevation = CardDefaults.cardElevation(spacing_8)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(spacing_32, spacing_16, spacing_24, spacing_16)
        )  {
            AsyncImage(
                model = coffee.image,
                contentDescription = coffee.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(spacing_64)
                    .width(spacing_64)
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

@Preview
@Composable
fun CoffeeCardPreview() {
    CoffeeCard(coffee = Coffee(
        description = "none",
        id = 1,
        image = "none",
        ingredients = listOf("none"),
        title = "Black"
    ))
}

@Composable
fun CoffeeListError(exception: String?) {
    exception?.let {
        Column(
            modifier = Modifier.padding(spacing_16),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = it)
        }
    }
}

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing_16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = BrownCoffee
        )
    }
}