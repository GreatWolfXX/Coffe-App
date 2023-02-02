package com.greatwolf.coffeeapp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.greatwolf.coffeeapp.domain.model.BottomNavItem
import com.greatwolf.coffeeapp.ui.theme.AlphaButtonBrownCoffee
import com.greatwolf.coffeeapp.ui.theme.ButtonBrownCoffee
import com.greatwolf.coffeeapp.ui.theme.spacing_32

@Composable
fun BottomNavBar(
    items: List<BottomNavItem>,
    navController: NavController
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = Modifier
            .shadow(
                spacing_32,
                ambientColor = Color.Black
            ),
        containerColor = Color.White,
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.route
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ButtonBrownCoffee,
                    unselectedIconColor = AlphaButtonBrownCoffee,
                    indicatorColor = Color.White
                )
            )
        }
    }
}