package com.greatwolf.coffeeapp.ui.screens.coffeeAuth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeAuthScreen() {
    Scaffold(
        content = { paddingValues ->
            CoffeeAuthContent(
                paddingValues = paddingValues
            )
        })
}

@Composable
fun CoffeeAuthContent(paddingValues: PaddingValues) {
    Column {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.top_image_auth),
            contentDescription = stringResource(id = R.string.image_coffee_time),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth())
        Text(
            stringResource(id = R.string.auth_title),
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            fontSize = sizing_32,
            lineHeight = sizing_36,
            color = TextBrownCoffee,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(
                    start = spacing_48,
                    top = spacing_50,
                    end = spacing_48
                )
        )
    }
}

@Preview
@Composable
private fun PreviewCoffeeAuthScreen() {
    CoffeeAuthScreen()
}