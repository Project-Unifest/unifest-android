package com.unifest.android.feature.booth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.unifest.android.core.designsystem.R
import com.unifest.android.feature.booth.viewmodel.BoothUiState
import com.unifest.android.feature.booth.viewmodel.BoothViewModel

@Composable
fun BoothLocationRoute(
    onBackClick: () -> Unit,
    viewModel: BoothViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BoothLocationScreen(
        uiState = uiState,
        latitude = uiState.boothDetailInfo.latitude.toDouble(),
        longitude = uiState.boothDetailInfo.longitude.toDouble(),
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
@Suppress("unused")
fun BoothLocationScreen(
    uiState: BoothUiState,
    onBackClick: () -> Unit,
    latitude: Double,
    longitude: Double,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition(LatLng(latitude, longitude), 14.0)
        }

        NaverMap(cameraPositionState = cameraPositionState) {
            Marker(
                state = MarkerState(position = LatLng(latitude, longitude)),
                icon = OverlayImage.fromResource(R.drawable.ic_general),
                onClick = { true }
            )
        }
    }
}
