package com.greatwolf.coffeeapp.ui.screens.passwordRecoveryScreen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.domain.util.ValidationEvent
import com.greatwolf.coffeeapp.ui.Screen
import com.greatwolf.coffeeapp.ui.components.LoadingView
import com.greatwolf.coffeeapp.ui.components.NavBar
import com.greatwolf.coffeeapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordRecoveryScreen(
    navController: NavController,
    viewModel: PasswordRecoveryScreenViewModel = hiltViewModel()
) {
    val state = viewModel.passwordRecoveryScreenState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate(Screen.LoginScreen.route)
                }
            }
        }
    }
    Scaffold(
        content = { paddingValues ->
            BoxWithConstraints() {
                PasswordRecoveryContent(
                    navController = navController,
                    paddingValues = paddingValues,
                    viewModel = viewModel,
                    state = state
                )
                if (state.value.isLoading) {
                    LoadingView()
                }
                if (!state.value.isError.isNullOrEmpty()) {
                    Toast.makeText(context, state.value.isError, Toast.LENGTH_SHORT).show()
                }
            }
        })
}

@Composable
fun PasswordRecoveryContent(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: PasswordRecoveryScreenViewModel,
    state: State<PasswordRecoveryScreenState>
) {
    Column(
        modifier = Modifier
            .padding(horizontal = spacing_32)
    ) {
        NavBar(
            onClickArrowBack = {
                navController.navigate(Screen.AuthScreen.route)
            },
            title = stringResource(id = R.string.title_password_recovery)
        )
        Spacer(modifier = Modifier.size(spacing_32))
        Text(
            stringResource(id = R.string.recovery_your_password),
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Start,
            fontSize = sizing_28,
            color = TextBrownCoffee,
        )
        Spacer(modifier = Modifier.size(spacing_32))
        PasswordRecoveryForm(
            viewModel = viewModel,
            state = state
        )
        ButtonRecoveryPassword(
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PasswordRecoveryForm(
    viewModel: PasswordRecoveryScreenViewModel,
    state: State<PasswordRecoveryScreenState>
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = state.value.email,
            onValueChange = {
                viewModel.onEvent(PasswordRecoveryScreenEvent.EmailChanged((it)))
            },
            isError = state.value.emailError != null,
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
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
        if (state.value.emailError != null) {
            Spacer(modifier = Modifier.size(spacing_8))
            Text(
                text = state.value.emailError!!.asString(),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = rubik,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Start,
                fontSize = sizing_12,
                color = RedCoffee,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun ButtonRecoveryPassword(
    viewModel: PasswordRecoveryScreenViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(spacing_40))
        Button(
            onClick = {
                viewModel.onEvent(PasswordRecoveryScreenEvent.Submit)
            },
            colors = ButtonDefaults.buttonColors(ButtonBrownCoffee),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                stringResource(id = R.string.btn_recover_password),
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