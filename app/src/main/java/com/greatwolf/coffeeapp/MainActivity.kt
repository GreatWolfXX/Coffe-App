package com.greatwolf.coffeeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.greatwolf.coffeeapp.domain.model.BottomNavItem
import com.greatwolf.coffeeapp.ui.Screen
import com.greatwolf.coffeeapp.ui.components.BottomNavBar
import com.greatwolf.coffeeapp.ui.screens.coffeeAuth.CoffeeAuthScreen
import com.greatwolf.coffeeapp.ui.screens.coffeeList.CoffeeListScreen
import com.greatwolf.coffeeapp.ui.screens.coffeeLogin.CoffeeLoginScreen
import com.greatwolf.coffeeapp.ui.screens.coffeePrefferences.CoffeePreferencesScreen
import com.greatwolf.coffeeapp.ui.screens.coffeeRegister.CoffeeRegisterScreen
import com.greatwolf.coffeeapp.ui.theme.CoffeeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            CoffeeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(
            route = Screen.CoffeeListScreen.route, // temporary solution, next will be a screen home
            icon = ImageVector.vectorResource(id = R.drawable.ic_home)
        ),
        BottomNavItem(
            route = Screen.CoffeeListScreen.route, // temporary solution, next will be a screen map
            icon = ImageVector.vectorResource(id = R.drawable.ic_map)
        ),
        BottomNavItem(
            route = Screen.CoffeeListScreen.route, // temporary solution, next will be a screen cart
            icon = ImageVector.vectorResource(id = R.drawable.ic_cart)
        ),
        BottomNavItem(
            route = Screen.CoffeeListScreen.route, // temporary solution, next will be a screen profile
            icon = ImageVector.vectorResource(id = R.drawable.ic_profile)
        )
    )

    var showBottomBar by rememberSaveable { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Log.d("gwolf", navBackStackEntry?.destination?.route.toString())

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        Screen.CoffeeAuthScreen.route -> false
        Screen.CoffeeRegisterScreen.route -> false
        Screen.CoffeeLoginScreen.route -> false
        Screen.CoffeePreferencesScreen.route + "/{coffeeId}" -> false
        else -> true
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = showBottomBar) {
                BottomNavBar(
                    items = bottomNavItems,
                    navController = navController
                )
            }
        }
    ) { padding ->
        NavigationHost(
            navController = navController,
            paddingValues = padding
        )
    }
}

@Composable
fun NavigationHost(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CoffeeAuthScreen.route
    ) {
        composable(route = Screen.CoffeeListScreen.route) {
            CoffeeListScreen(navController = navController)
        }
        composable(route = Screen.CoffeePreferencesScreen.route + "/{coffeeId}") {
            CoffeePreferencesScreen(navController = navController)
        }
        composable(route = Screen.CoffeeAuthScreen.route) {
            CoffeeAuthScreen(navController = navController)
        }
        composable(route = Screen.CoffeeLoginScreen.route) {
            CoffeeLoginScreen(navController = navController)
        }
        composable(route = Screen.CoffeeRegisterScreen.route) {
            CoffeeRegisterScreen(navController = navController)
        }
    }
}