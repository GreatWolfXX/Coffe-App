package com.greatwolf.coffeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.greatwolf.coffeeapp.ui.Screen
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

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.CoffeeAuthScreen.route) {
        composable(route =  Screen.CoffeeListScreen.route) {
            CoffeeListScreen(navController = navController)
        }
        composable(route =  Screen.CoffeePreferencesScreen.route + "/{coffeeId}") {
            CoffeePreferencesScreen(navController = navController)
        }
        composable(route =  Screen.CoffeeAuthScreen.route) {
            CoffeeAuthScreen(navController = navController)
        }
        composable(route =  Screen.CoffeeLoginScreen.route) {
            CoffeeLoginScreen(navController = navController)
        }
        composable(route =  Screen.CoffeeRegisterScreen.route) {
            CoffeeRegisterScreen(navController = navController)
        }
    }
}