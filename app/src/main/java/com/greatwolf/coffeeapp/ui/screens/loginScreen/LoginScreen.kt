package com.greatwolf.coffeeapp.ui.screens.loginScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.domain.util.ValidationEvent
import com.greatwolf.coffeeapp.ui.Screen
import com.greatwolf.coffeeapp.ui.components.*
import com.greatwolf.coffeeapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val state = viewModel.loginScreenState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate(Screen.ListScreen.route)
                }
            }
        }
    }
    Scaffold(
        content = { paddingValues ->
            BoxWithConstraints() {
                LoginContent(
                    navController = navController,
                    paddingValues = paddingValues,
                    viewModel = viewModel,
                    state = state
                )
            }
            if (state.value.isLoading) {
                LoadingView()
            }
            if (!state.value.isError.isNullOrEmpty()) {
                Toast.makeText(context, state.value.isError, Toast.LENGTH_SHORT).show()
            }
        })
}

@Composable
fun LoginContent(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: LoginScreenViewModel,
    state: State<LoginScreenState>
) {
    Column(
        modifier = Modifier
            .padding(horizontal = spacing_32)
    ) {
        NavBar(
            onClickArrowBack = {
                navController.navigate(Screen.AuthScreen.route)
            },
            title = stringResource(id = R.string.btn_login)
        )
        Spacer(modifier = Modifier.size(spacing_32))
        Text(
            stringResource(id = R.string.welcome_back),
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Start,
            fontSize = sizing_32,
            color = TextBrownCoffee,
        )
        Spacer(modifier = Modifier.size(spacing_32))
        LoginForm(
            navController = navController,
            viewModel = viewModel,
            state = state
        )
        ButtonFormAuth(
            btnClickable = {
                viewModel.onEvent(LoginScreenEvent.Submit)
            },
            btnHint = {
                navController.navigate(Screen.RegisterScreen.route)
            },
            btnText = stringResource(id = R.string.btn_login),
            firstHint = stringResource(id = R.string.t_register),
            secondHint = stringResource(id = R.string.btn_register)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginForm(
    navController: NavController,
    viewModel: LoginScreenViewModel,
    state: State<LoginScreenState>
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
                viewModel.onEvent(LoginScreenEvent.EmailChanged((it)))
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
            singleLine = true,
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
        Spacer(modifier = Modifier.size(spacing_16))
        TextField(
            value = state.value.password,
            onValueChange = {
                viewModel.onEvent(LoginScreenEvent.PasswordChanged((it)))
            },
            isError = state.value.passwordError != null,
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedLabelColor = UnfocusedLabelTextCoffee,
                focusedLabelColor = FocusedLabelTextCoffee,
                unfocusedIndicatorColor = UnfocusedIndicatorBrownCoffee,
                focusedIndicatorColor = FocusedIndicatorBrownCoffee,
            ),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = getVisibilityPasswordIcon(passwordVisible),
                        contentDescription = getVisibilityPasswordIconDescription(passwordVisible)
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
        )
        if (state.value.passwordError != null) {
            Spacer(modifier = Modifier.size(spacing_8))
            Text(
                text = state.value.passwordError!!.asString(),
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
        Spacer(modifier = Modifier.size(spacing_20))
        Text(
            stringResource(id = R.string.t_forgot_password),
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.End,
            fontSize = sizing_14,
            color = ButtonBrownCoffee,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Screen.PasswordRecoveryScreen.route)
                }
        )
    }
}