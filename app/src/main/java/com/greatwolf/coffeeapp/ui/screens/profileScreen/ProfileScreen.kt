package com.greatwolf.coffeeapp.ui.screens.profileScreen

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseUser
import com.greatwolf.coffeeapp.R
import com.greatwolf.coffeeapp.domain.util.LogOutEvent
import com.greatwolf.coffeeapp.ui.Screen
import com.greatwolf.coffeeapp.ui.components.ErrorView
import com.greatwolf.coffeeapp.ui.components.LoadingView
import com.greatwolf.coffeeapp.ui.components.NavBar
import com.greatwolf.coffeeapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val state = viewModel.profileScreenState.collectAsState()
    viewModel.fetchCurrentUser()
    val context = LocalContext.current
    LaunchedEffect(context) {
        viewModel.logOutEvents.collect { event ->
            when (event) {
                is LogOutEvent.Success -> {
                    navController.navigate(Screen.AuthScreen.route)
                }
            }
        }
    }
    Scaffold(
        content = { paddingValues ->
            BoxWithConstraints() {
                ProfileContent(
                    navController = navController,
                    viewModel = viewModel,
                    state = state.value,
                    paddingValues = paddingValues
                )
            }
        })
}

@Composable
fun ProfileContent(
    navController: NavController,
    viewModel: ProfileScreenViewModel,
    state: ProfileScreenState,
    paddingValues: PaddingValues,
) {
    Column {
        Column(
            modifier = Modifier
                .padding(horizontal = spacing_32)
        ) {
            NavBar(
                onClickArrowBack = {
                    navController.navigate(Screen.ListScreen.route)
                },
                title = stringResource(id = R.string.t_profile)
            )
            Spacer(modifier = Modifier.size(spacing_16))
        }
        when (state) {
            is ProfileScreenState.Success ->
            {
                Log.d("checkerBlink", "successProfile")
                ProfileSuccess(
                    navController = navController,
                    state = state,
                    viewModel = viewModel,
                    user = state.user
                )
            }
            is ProfileScreenState.Loading -> {
                Log.d("checkerBlink", "loadingProfile")
                LoadingView()
            }
            is ProfileScreenState.Error -> ErrorView(exception = state.exception.message)
        }
    }
}

@Composable
fun ProfileSuccess(
    navController: NavController,
    viewModel: ProfileScreenViewModel,
    state: ProfileScreenState,
    user: FirebaseUser?
) {
    Column() {
        Column(
            modifier = Modifier
                .padding(horizontal = spacing_32)
        ) {
            Text(
                stringResource(id = R.string.t_account),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = roboto,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Start,
                fontSize = sizing_24,
                color = TextBrownCoffee,
                modifier = Modifier
                    .drawBehind {
                        drawLine(
                            color = BorderCoffee,
                            start = Offset(spacing_0.toPx(), size.height + spacing_2.toPx()),
                            end = Offset(size.width, size.height),
                            strokeWidth = spacing_2.toPx()
                        )
                    }
            )
            Spacer(modifier = Modifier.size(spacing_24))
            ProfileInfo(
                user = user
            )
        }
        Spacer(modifier = Modifier.size(spacing_24))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(spacing_1)
                .background(Color.Black)
        )
        Spacer(modifier = Modifier.size(spacing_24))
        Column(
            modifier = Modifier
                .padding(horizontal = spacing_32)
        ) {
            ProfileItemMenu(
                title = stringResource(id = R.string.t_profile_details),
                icon = ImageVector.vectorResource(id = R.drawable.ic_profile),
                onClick = {}
            )
            Spacer(modifier = Modifier.size(spacing_16))
            ProfileItemMenu(
                title = stringResource(id = R.string.t_payment),
                icon = ImageVector.vectorResource(id = R.drawable.ic_payment),
                onClick = {}
            )
            Spacer(modifier = Modifier.size(spacing_16))
            ProfileItemMenu(
                title = stringResource(id = R.string.t_check_out_list),
                icon = ImageVector.vectorResource(id = R.drawable.ic_check_list),
                onClick = {}
            )
        }
        Spacer(modifier = Modifier.size(spacing_24))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(spacing_1)
                .background(Color.Black)
        )
        Spacer(modifier = Modifier.size(spacing_24))
        Column(
            modifier = Modifier
                .padding(horizontal = spacing_32)
        ) {
            ProfileItemMenu(
                title = stringResource(id = R.string.t_faqs),
                icon = ImageVector.vectorResource(id = R.drawable.ic_faqs),
                onClick = {}
            )
            Spacer(modifier = Modifier.size(spacing_16))
            ProfileItemMenu(
                title = stringResource(id = R.string.t_logout),
                icon = ImageVector.vectorResource(id = R.drawable.ic_logout),
                onClick = {
                    viewModel.onEvent(LogOutEvent.Success)
                }
            )
        }
    }
}

@Composable
fun ProfileInfo(
    user: FirebaseUser?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = stringResource(id = R.string.desc_avatar),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(avatar_image_96)
                .clip(CircleShape)
                .border(
                    border = BorderStroke(spacing_1, Color.Black),
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.size(spacing_16))
        Column() {
            Text(
                user?.displayName.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = roboto,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Start,
                fontSize = sizing_16,
                color = DarkBrownCoffee
            )
            Spacer(modifier = Modifier.size(spacing_8))
            Text(
                user?.email.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = roboto,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Start,
                fontSize = sizing_14,
                color = LightBrownCoffee
            )
        }
    }
}

@Composable
fun ProfileItemMenu(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                onClick.invoke()
            }
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(spacing_8))
                    .background(ButtonBrownCoffee)
                    .size(profile_menu_item_42)
            )
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
            )
        }
        Spacer(modifier = Modifier.size(spacing_16))
        Text(
            title,
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = roboto,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Start,
            fontSize = sizing_14,
            color = DarkBrownCoffee
        )
    }
}