package com.greatwolf.coffeeapp.ui.screens.preferencesScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.domain.model.Coffee
import com.greatwolf.coffeeapp.ui.components.CoffeeError
import com.greatwolf.coffeeapp.ui.components.CoffeeNavBar
import com.greatwolf.coffeeapp.ui.components.LoadingView
import com.greatwolf.coffeeapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(
    navController: NavController,
    viewModel: PreferencesScreenViewModel = hiltViewModel()
) {
    val state = viewModel.preferencesScreenState.collectAsState()
    Scaffold(
        content = { paddingValues ->
            BoxWithConstraints() {
                CoffeePreferencesContent(
                    navController = navController,
                    state = state.value,
                    paddingValues = paddingValues
                )
            }
        })
}

@Composable
fun CoffeePreferencesContent(
    navController: NavController,
    state: PreferencesScreenState,
    paddingValues: PaddingValues
) {
    Column {
        CoffeeNavBar(
            onClickArrowBack = {
                navController.popBackStack()
            },
            title = stringResource(id = R.string.title_preferences),
            paddingValues = PaddingValues(horizontal = spacing_32)
        )
        when(state) {
            is PreferencesScreenState.Success -> CoffeePreferencesSuccess(state.coffee)
            is PreferencesScreenState.Loading -> LoadingView()
            is PreferencesScreenState.Error -> CoffeeError(exception = state.exception.message)
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
                .size(coffee_image_bg_128)
        )
        AsyncImage(
            model = coffee.image,
            contentDescription = coffee.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(coffee_image_96)
                .clip(RoundedCornerShape(spacing_50))
        )
    }
    Column(
        modifier = Modifier
    ) {
        FirstPreferencesCoffee(coffee)
        SecondPreferencesCoffee()
        ThirdPreferencesCoffee()
        FourthPreferencesCoffee()
        Spacer(modifier = Modifier.size(spacing_24))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing_32),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                stringResource(id = R.string.t_total),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = roboto,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Start,
                fontSize = sizing_28,
                color = TextBrownCoffee
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    "42",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = roboto,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = sizing_30,
                    color = DarkBrownCoffee
                )
                Text(
                    " EGP",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = roboto,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = sizing_20,
                    color = DarkBrownCoffee
                )
            }
        }
        Spacer(modifier = Modifier.size(spacing_32))
        Button(
            onClick = {
                      
            },
            colors = ButtonDefaults.buttonColors(ButtonBrownCoffee),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing_32)
        ) {
            Text(
                stringResource(id = R.string.btn_add_to_cart),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = roboto,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center,
                fontSize = sizing_14,
                color = Color.White,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(vertical = spacing_8)
            )
        }
    }
}

@Composable
fun FirstPreferencesCoffee(coffee: Coffee) {
    Spacer(modifier = Modifier.size(spacing_20))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing_32),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(0.5f)
        ) {
            Text(
                coffee.title,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = rubik,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.End,
                fontSize = sizing_20,
                color = DarkBrownCoffee
            )
            Spacer(modifier = Modifier.size(spacing_8))
            Text(
                "36 EGP",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = rubik,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.End,
                fontSize = sizing_18,
                color = DarkGrayCoffee
            )
        }
        Row(
            modifier = Modifier
                .weight(0.5f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                "1",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = rubik,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.End,
                fontSize = sizing_24,
                color = DarkGrayCoffee
            )
            Spacer(modifier = Modifier.size(spacing_12))
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(
                    topStart = spacing_50,
                    bottomStart = spacing_50
                ),
                border = BorderStroke(spacing_1, DarkBrownCoffee),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(spacing_0)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_remove_24),
                    contentDescription = stringResource(id = R.string.btn_minus_description),
                    tint = DarkBrownCoffee
                )
            }
            Spacer(modifier = Modifier.size(spacing_8))
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(
                    topEnd = spacing_50,
                    bottomEnd = spacing_50
                ),
                border = BorderStroke(spacing_1, DarkBrownCoffee),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(spacing_0)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.btn_add_description),
                    tint = DarkBrownCoffee
                )
            }
        }
    }
    Spacer(modifier = Modifier.size(spacing_20))
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(spacing_1)
            .background(TextBrownCoffee)
    )
}

@Composable
fun SecondPreferencesCoffee() {
    Spacer(modifier = Modifier.size(spacing_20))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing_32),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(id = R.string.t_size),
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = rubik,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Start,
            fontSize = sizing_18,
            color = LightBrownCoffee,
            modifier = Modifier
                .weight(0.5f)
        )
        Row(
            modifier = Modifier
                .weight(0.5f),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_small_coffee),
                contentDescription = stringResource(id = R.string.ic_small_coffee_description)
            )
            Spacer(modifier = Modifier.size(spacing_32))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_middle_coffee),
                contentDescription = stringResource(id = R.string.ic_middle_coffee_description)
            )
            Spacer(modifier = Modifier.size(spacing_32))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_big_coffee),
                contentDescription = stringResource(id = R.string.ic_big_coffee_description)
            )
            Spacer(modifier = Modifier.size(spacing_32))
        }
    }
    Spacer(modifier = Modifier.size(spacing_20))
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(spacing_1)
            .background(TextBrownCoffee)
    )
}

@Composable
fun ThirdPreferencesCoffee() {
    Spacer(modifier = Modifier.size(spacing_20))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing_32),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(id = R.string.t_sugar),
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = rubik,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Start,
            fontSize = sizing_18,
            color = LightBrownCoffee,
            modifier = Modifier
                .weight(0.5f)
        )
        Row(
            modifier = Modifier
                .weight(0.5f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_no_sugar),
                contentDescription = stringResource(id = R.string.ic_no_sugar_description)
            )
            Spacer(modifier = Modifier.size(spacing_32))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_one_sugar),
                contentDescription = stringResource(id = R.string.ic_one_sugar_description)
            )
            Spacer(modifier = Modifier.size(spacing_32))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_two_sugar),
                contentDescription = stringResource(id = R.string.ic_two_sugar_description)
            )
            Spacer(modifier = Modifier.size(spacing_32))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_three_sugar),
                contentDescription = stringResource(id = R.string.ic_three_sugar_description)
            )
        }
    }
    Spacer(modifier = Modifier.size(spacing_20))
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(spacing_1)
            .background(TextBrownCoffee)
    )
}

@Composable
fun FourthPreferencesCoffee() {
    Spacer(modifier = Modifier.size(spacing_20))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing_32),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(id = R.string.t_additions),
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = rubik,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Start,
            fontSize = sizing_18,
            color = LightBrownCoffee,
            modifier = Modifier
                .weight(0.5f)
        )
        Row(
            modifier = Modifier
                .weight(0.5f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_whipped_cream),
                contentDescription = stringResource(id = R.string.ic_whipped_cream_description)
            )
            Spacer(modifier = Modifier.size(spacing_32))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_vanilla),
                contentDescription = stringResource(id = R.string.ic_vanilla_description)
            )
        }
    }
    Spacer(modifier = Modifier.size(spacing_20))
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(spacing_1)
            .background(TextBrownCoffee)
    )
}