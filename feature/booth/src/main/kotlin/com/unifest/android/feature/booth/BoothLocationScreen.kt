package com.unifest.android.feature.booth

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PolygonOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.unifest.android.core.designsystem.MarkerCategory
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.ui.DevicePreview
import com.unifest.android.feature.booth.component.BoothLocationAppBar
import com.unifest.android.feature.booth.preview.BoothDetailPreviewParameterProvider
import com.unifest.android.feature.booth.viewmodel.BoothUiState
import com.unifest.android.feature.booth.viewmodel.BoothViewModel
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun BoothLocationRoute(
    popBackStack: () -> Unit,
    viewModel: BoothViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BoothLocationScreen(
        uiState = uiState,
        popBackStack = popBackStack,
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
internal fun BoothLocationScreen(
    uiState: BoothUiState,
    popBackStack: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition(LatLng(36.970898, 127.871726), 15.2)
        }
        NaverMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(
                isNightModeEnabled = isSystemInDarkTheme(),
            ),
            uiSettings = MapUiSettings(
                isZoomControlEnabled = false,
                isScaleBarEnabled = false,
                isLogoClickEnabled = false,
            ),
        ) {
            PolygonOverlay(
                coords = uiState.outerCords,
                color = Color.Gray.copy(alpha = 0.3f),
                outlineColor = Color.Gray,
                outlineWidth = 1.dp,
                holes = persistentListOf(uiState.innerHole),
            )
            Marker(
                state = MarkerState(position = LatLng(uiState.boothDetailInfo.latitude.toDouble(), uiState.boothDetailInfo.longitude.toDouble())),
                icon = MarkerCategory.fromString(uiState.boothDetailInfo.category).getMarkerIcon(false),
                onClick = { true },
            )
        }

        BoothLocationAppBar(
            onBackClick = popBackStack,
            boothName = uiState.boothDetailInfo.name,
            boothLocation = uiState.boothDetailInfo.location,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@DevicePreview
@Composable
private fun BoothLocationScreenPreview(
    @PreviewParameter(BoothDetailPreviewParameterProvider::class)
    boothUiState: BoothUiState,
) {
    UnifestTheme {
        BoothLocationScreen(
            uiState = boothUiState,
            popBackStack = {},
        )
    }
}
