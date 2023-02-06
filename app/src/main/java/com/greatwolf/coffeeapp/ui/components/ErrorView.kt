package com.greatwolf.coffeeapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.greatwolf.coffeeapp.ui.theme.spacing_16

@Composable
fun ErrorView(exception: String?) {
    exception?.let {
        Column(
            modifier = Modifier.padding(spacing_16),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = it)
        }
    }
}