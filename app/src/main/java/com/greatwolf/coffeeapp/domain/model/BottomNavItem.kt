package com.greatwolf.coffeeapp.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)