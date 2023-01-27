package com.greatwolf.coffeeapp.ui.screens.coffeeLogin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeLoginScreen() {
    Scaffold(
        content = { paddingValues ->
            BoxWithConstraints() {
                CoffeeLoginContent(
                    paddingValues = paddingValues
                )
            }

        })
}

@Composable
fun CoffeeLoginContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(horizontal = spacing_32)
    ) {
        CoffeeNavBar()
        Text(
            stringResource(id = R.string.welcome_back),
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            fontSize = sizing_32,
            color = TextBrownCoffee,
            modifier = Modifier
                .padding(vertical = spacing_32)
        )
        CoffeeForm()
    }
}

@Composable
fun CoffeeNavBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = spacing_10),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(0.1f)
                .clickable {

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
            stringResource(id = R.string.btn_login),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeForm() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = "",
            onValueChange = {},
            label = {
                Text(
                    stringResource(id = R.string.tf_email),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = rubik,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = sizing_14,
                    color = UnfocusedLabelTextCoffee
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedLabelColor = UnfocusedLabelTextCoffee,
                focusedLabelColor = FocusedLabelTextCoffee,
                unfocusedIndicatorColor = UnfocusedIndicatorBrownCoffee,
                focusedIndicatorColor = FocusedIndicatorBrownCoffee,
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        TextField(
            value = "",
            onValueChange = {},
            label = {
                Text(
                    stringResource(id = R.string.tf_password),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = rubik,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = sizing_14,
                    color = UnfocusedLabelTextCoffee
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedLabelColor = UnfocusedLabelTextCoffee,
                focusedLabelColor = FocusedLabelTextCoffee,
                unfocusedIndicatorColor = UnfocusedIndicatorBrownCoffee,
                focusedIndicatorColor = FocusedIndicatorBrownCoffee,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacing_16)
        )
    }
}

@Preview
@Composable
private fun PreviewCoffeeLoginScreen() {
    CoffeeLoginScreen()
}