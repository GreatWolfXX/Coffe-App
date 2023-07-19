package com.greatwolf.coffeeapp.ui.screens.mapScreen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.greatwolf.coffeeapp.ui.theme.spacing_64

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    navController: NavController
) {
    Scaffold(
        content = { paddingValues ->
            BoxWithConstraints() {
                MapContent(
                    paddingValues = paddingValues
                )
            }

        })
}

@Composable
fun MapContent(
    paddingValues: PaddingValues
) {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing_64),
        cameraPositionState = cameraPositionState
    )
}