package com.greatwolf.coffeeapp.ui.screens.registerScreen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterScreenViewModel = hiltViewModel()
) {
    val state = viewModel.registerScreenState.collectAsState()
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
                CoffeeRegisterContent(
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
fun CoffeeRegisterContent(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: RegisterScreenViewModel,
    state: State<RegisterScreenState>
) {
    Column(
        modifier = Modifier
            .padding(horizontal = spacing_32)
    ) {
        CoffeeNavBar(
            onClickArrowBack = {
                navController.navigate(Screen.AuthScreen.route)
            },
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
        CoffeeForm(
            viewModel = viewModel,
            state = state
        )
        CoffeeButtonFormAuth(
            btnClickable = {
                viewModel.onEvent(RegisterScreenEvent.Submit)
            },
            btnHint = {
                navController.navigate(Screen.LoginScreen.route)
            },
            btnText = stringResource(id = R.string.btn_register),
            firstHint = stringResource(id = R.string.t_login),
            secondHint = stringResource(id = R.string.btn_login)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeForm(
    viewModel: RegisterScreenViewModel,
    state: State<RegisterScreenState>
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var repeatedPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = state.value.fullName,
            onValueChange = {
                viewModel.onEvent(RegisterScreenEvent.FullNameChanged((it)))
            },
            isError = state.value.fullNameError != null,
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
        if (state.value.fullNameError != null) {
            Spacer(modifier = Modifier.size(spacing_8))
            Text(
                text = state.value.fullNameError!!.asString(),
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
            value = state.value.email,
            onValueChange = {
                viewModel.onEvent(RegisterScreenEvent.EmailChanged((it)))
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
                keyboardType = KeyboardType.Email
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
        Spacer(modifier = Modifier.size(spacing_16))

        TextField(
            value = state.value.password,
            onValueChange = {
                viewModel.onEvent(RegisterScreenEvent.PasswordChanged((it)))
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
                keyboardType = KeyboardType.Password
            ),
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
        Spacer(modifier = Modifier.size(spacing_16))
        TextField(
            value = state.value.repeatedPassword,
            onValueChange = {
                viewModel.onEvent(RegisterScreenEvent.RepeatedPasswordChanged((it)))
            },
            isError = state.value.repeatedPasswordError != null,
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedLabelColor = UnfocusedLabelTextCoffee,
                focusedLabelColor = FocusedLabelTextCoffee,
                unfocusedIndicatorColor = UnfocusedIndicatorBrownCoffee,
                focusedIndicatorColor = FocusedIndicatorBrownCoffee,
            ),
            trailingIcon = {
                IconButton(onClick = { repeatedPasswordVisible = !repeatedPasswordVisible }) {
                    Icon(
                        imageVector = getVisibilityPasswordIcon(repeatedPasswordVisible),
                        contentDescription = getVisibilityPasswordIconDescription(repeatedPasswordVisible)
                    )
                }
            },
            visualTransformation = if (repeatedPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
        )
        if (state.value.repeatedPasswordError != null) {
            Spacer(modifier = Modifier.size(spacing_8))
            Text(
                text = state.value.repeatedPasswordError!!.asString(),
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