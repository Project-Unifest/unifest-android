package com.unifest.android.feature.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.map.viewmodel.MapUiState
import com.unifest.android.feature.map.viewmodel.MapViewModel
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.geometry.LatLng
import com.unifest.android.core.designsystem.R

@Composable
internal fun MapRoute(
    padding: PaddingValues,
    onNavigateToBooth: (Long) -> Unit,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MapScreen(
        padding = padding,
        uiState = uiState,
        onNavigateToBooth = onNavigateToBooth,
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Suppress("unused")
@Composable
internal fun MapScreen(
    padding: PaddingValues,
    uiState: MapUiState,
    onNavigateToBooth: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = padding.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition(LatLng(37.540470588662664, 127.0765263757882), 14.0)
        }

        NaverMap(cameraPositionState = cameraPositionState) {
            uiState.boothSpots.forEach { spot ->
                Marker(
                    state = MarkerState(position = LatLng(spot.lat, spot.lng)),
                    icon = OverlayImage.fromResource(R.drawable.ic_general),
                    onClick = {
                        onNavigateToBooth(spot.id)
                        true
                    },
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
            onNavigateToBooth = {},
        )
    }
}
