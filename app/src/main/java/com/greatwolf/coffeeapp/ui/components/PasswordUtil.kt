package com.greatwolf.coffeeapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.greatwolf.coffeeapp.R

@Composable
fun getVisibilityPasswordIcon(passwordVisible: Boolean): ImageVector {
    return if (passwordVisible) ImageVector.vectorResource(id = R.drawable.ic_visibility_off)
    else ImageVector.vectorResource(id = R.drawable.ic_visibility)
}

@Composable
fun getVisibilityPasswordIconDescription(passwordVisible: Boolean): String {
    return if (passwordVisible) stringResource(id = R.string.desc_hide_password)
    else stringResource(id = R.string.desc_show_password)
}