package com.greatwolf.coffeeapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.ui.theme.TextBrownCoffee
import com.greatwolf.coffeeapp.ui.theme.roboto
import com.greatwolf.coffeeapp.ui.theme.sizing_22
import com.greatwolf.coffeeapp.ui.theme.spacing_20

@Composable
fun CoffeeNavBar(
    onClickArrowBack: () -> Unit,
    title: String
) {
    Spacer(modifier = Modifier.size(spacing_20))
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(0.1f)
                .clickable {
                    onClickArrowBack
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(id = R.string.ic_arrow_back),
                tint = TextBrownCoffee
            )
        }
        Text(
            title,
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            fontSize = sizing_22,
            color = TextBrownCoffee,
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .weight(0.8f)
        )
        Spacer(modifier = Modifier.weight(0.1f))
    }
}