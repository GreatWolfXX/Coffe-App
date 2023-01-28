package com.greatwolf.coffeeapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.greatwolf.coffeeapp.ui.theme.*


@Composable
fun CoffeeButtonFormAuth(
    btnClickable: () -> Unit,
    btnText: String,
    firstHint: String,
    secondHint: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(spacing_40))
        Button(
            onClick = {
                btnClickable
            },
            colors = ButtonDefaults.buttonColors(ButtonBrownCoffee),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                btnText,
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
        Row(
            modifier = Modifier
                .clickable {

                }
        ) {
            Text(
                firstHint,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = roboto,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.End,
                fontSize = sizing_14,
                color = GreyCoffee,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(vertical = spacing_16)
            )
            Text(
                secondHint,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = roboto,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Start,
                fontSize = sizing_14,
                color = ButtonBrownCoffee,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(vertical = spacing_16)
            )
        }
    }
}