package com.greatwolf.coffeeapp.ui.screens.coffeeRegister

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.ui.components.CoffeeButtonFormAuth
import com.greatwolf.coffeeapp.ui.components.CoffeeNavBar
import com.greatwolf.coffeeapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeRegisterScreen() {
    Scaffold(
        content = { paddingValues ->
            BoxWithConstraints() {
                CoffeeRegisterContent(
                    paddingValues = paddingValues
                )
            }

        })
}

@Composable
fun CoffeeRegisterContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(horizontal = spacing_32)
    ) {
        CoffeeNavBar(
            onClickArrowBack = {},
            title = stringResource(id = R.string.btn_register)
        )
        Spacer(modifier = Modifier.size(spacing_32))
        Text(
            stringResource(id = R.string.create_new_account),
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Start,
            fontSize = sizing_28,
            color = TextBrownCoffee,
        )
        Spacer(modifier = Modifier.size(spacing_32))
        CoffeeForm()
        CoffeeButtonFormAuth(
            btnClickable = {},
            btnText = stringResource(id = R.string.btn_register),
            firstHint = stringResource(id = R.string.t_login),
            secondHint = stringResource(id = R.string.btn_login)
        )
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
                    stringResource(id = R.string.tf_full_name),
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
        Spacer(modifier = Modifier.size(spacing_16))
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
        Spacer(modifier = Modifier.size(spacing_16))
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
        )
        Spacer(modifier = Modifier.size(spacing_16))
        TextField(
            value = "",
            onValueChange = {},
            label = {
                Text(
                    stringResource(id = R.string.tf_repeat_password),
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
    }
}

@Preview
@Composable
private fun PreviewCoffeeRegisterScreen() {
    CoffeeRegisterScreen()
}