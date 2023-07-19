package com.greatwolf.coffeeapp.ui.screens.authScreen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.ui.Screen
import com.greatwolf.coffeeapp.ui.components.LoadingView
import com.greatwolf.coffeeapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthScreenViewModel = hiltViewModel()
) {
    val state = viewModel.authScreenState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getCredential()
    }
    Scaffold(
        content = { paddingValues ->
            BoxWithConstraints() {
                AuthContent(
                    navController = navController,
                    state = state.value,
                    paddingValues = paddingValues
                )
            }
        }
    )
}

@Composable
fun AuthContent(
    navController: NavController,
    state: AuthScreenState,
    paddingValues: PaddingValues
) {
    when (state) {
        is AuthScreenState.NewAuth -> {
            Log.d("checkerBlink", "newAuth")
            AuthSuccess(
                navController = navController
            )
        }

        is AuthScreenState.SuccessAuth -> {
            Log.d("checkerBlink", "successAuth")
            navController.navigate(Screen.MapScreen.route)
        }

        is AuthScreenState.Loading -> {
            Log.d("checkerBlink", "loadingAuth")
            LoadingView()
        }
    }
}

@Composable
fun AuthSuccess(
    navController: NavController
) {
    Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.bg_coffe_time),
        contentDescription = stringResource(id = R.string.image_coffee_time),
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .fillMaxWidth()
            .height(coffee_time_280)
            .padding(top = spacing_8)
    )
    Column {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.coffe_time),
            contentDescription = stringResource(id = R.string.image_coffee_time),
            modifier = Modifier
                .size(coffee_time_280)
                .align(alignment = Alignment.CenterHorizontally)
        )
        Text(
            String.format(stringResource(id = R.string.auth_title), 2342.574563453),
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
                    top = spacing_32,
                    end = spacing_48
                )
        )
        ButtonContainer(navController = navController)
    }
}

@Composable
fun ButtonContainer(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(horizontal = spacing_32, vertical = spacing_64)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    navController.navigate(Screen.RegisterScreen.route)
                },
                colors = ButtonDefaults.buttonColors(ButtonBrownCoffee),
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    stringResource(id = R.string.btn_register),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = roboto,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = sizing_14,
                    color = Color.White,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .padding(vertical = spacing_8)
                )
            }
            Spacer(modifier = Modifier.size(spacing_15))
            Button(
                onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                colors = ButtonDefaults.buttonColors(Color.White),
                border = BorderStroke(spacing_1, ButtonBrownCoffee),
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    stringResource(id = R.string.btn_login),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = roboto,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = sizing_14,
                    color = ButtonBrownCoffee,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .padding(vertical = spacing_8)
                )
            }
        }
        Spacer(modifier = Modifier.size(spacing_20))
        Button(
            onClick = {  },
            colors = ButtonDefaults.buttonColors(Color.White),
            border = BorderStroke(spacing_1, ButtonFacebookCoffee),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_facebook),
                contentDescription = stringResource(id = R.string.btn_facebook_auth_desc),
                tint = ButtonFacebookCoffee,
                modifier = Modifier
                    .padding(end = spacing_10)
            )
            Text(
                stringResource(id = R.string.btn_facebook_auth),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = roboto,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center,
                fontSize = sizing_14,
                color = ButtonFacebookCoffee,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(vertical = spacing_8)
            )
        }
    }
}