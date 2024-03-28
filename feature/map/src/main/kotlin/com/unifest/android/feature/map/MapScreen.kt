package com.unifest.android.feature.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.map.viewmodel.MapUiState
import com.unifest.android.feature.map.viewmodel.MapViewModel

@Composable
internal fun MapRoute(
    padding: PaddingValues,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MapScreen(
        padding = padding,
        uiState = uiState,
    )
}

@Composable
internal fun MapScreen(
    padding: PaddingValues,
    uiState: MapUiState,
) {
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = padding.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(37.540470588662664, 127.0765263757882), 16.5f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = uiState.properties,
            uiSettings = uiSettings,
            onMapClick = {},
        ) {
            uiState.boothSpots.forEach { spot ->
                Marker(
                    state = MarkerState(
                        position = LatLng(spot.lat, spot.lng),
                    ),
                    title = "Booth spot (${spot.lat}, ${spot.lng})",
                    snippet = "click",
                    onClick = { true },
                    icon = BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN,
                    ),
                )
            }
        }
    }
}

@DevicePreview
@Composable
fun MapScreenPreview() {
    UnifestTheme {
        MapScreen(
            padding = PaddingValues(0.dp),
            uiState = MapUiState(),
        )
    }
}
